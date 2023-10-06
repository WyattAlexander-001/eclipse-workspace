package csi311.pro1.WyattBushman;

import java.util.LinkedList;
import java.util.Optional;

public class Parser {
    private TokenManager tokenManager;

    public Parser(LinkedList<Token> tokens) {
        this.tokenManager = new TokenManager(tokens);
    }
    
    // Removes consecutive separators, returning true if found.
    public boolean AcceptSeparators() {
        boolean found = false;
        while (tokenManager.MoreTokens() && (tokenManager.Peek(0).get().getType() == TokenType.SEPARATOR)) {
            tokenManager.MatchAndRemove(TokenType.SEPARATOR);
            found = true;
        }
        return found;
    }

    public ProgramNode Parse() {
        ProgramNode program = new ProgramNode();
        while (tokenManager.MoreTokens()) {
            if (ParseFunction(program) || ParseAction(program)) {
                AcceptSeparators();
            } else {
                System.out.println("Failed at token: " + tokenManager.Peek(0).get().getValue());
                throw new RuntimeException("Unexpected token found.");
            }
        }
        return program;
    }

    public boolean ParseFunction(ProgramNode program) {
        if (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.FUNCTION) {
            tokenManager.MatchAndRemove(TokenType.FUNCTION);
            
            Token funcNameToken = tokenManager.MatchAndRemove(TokenType.WORD).orElseThrow(() -> new RuntimeException("Expected function name"));
            String functionName = funcNameToken.getValue();
            AcceptSeparators();

            LinkedList<String> parameters = new LinkedList<>();
            tokenManager.MatchAndRemove(TokenType.OPEN_PAREN);
            AcceptSeparators();

            while (tokenManager.Peek(0).get().getType() != TokenType.CLOSE_PAREN) {
                Token paramToken = tokenManager.MatchAndRemove(TokenType.WORD).orElseThrow(() -> new RuntimeException("Expected parameter name"));
                parameters.add(paramToken.getValue());
                AcceptSeparators();
                if (tokenManager.Peek(0).get().getType() == TokenType.COMMA) {
                    tokenManager.MatchAndRemove(TokenType.COMMA);
                    AcceptSeparators();
                }
            }
            tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN);
            AcceptSeparators(); 

            FunctionDefinitionNode functionNode = new FunctionDefinitionNode(functionName);
            functionNode.getParameters().addAll(parameters);

            BlockNode blockNode = ParseBlock();
            for (StatementNode stmt : blockNode.getStatements()) {
                functionNode.addStatement(stmt);
            }
  
            program.addFunctionDefinition(functionNode);
            return true;
        }
        return false;
    }


    public boolean ParseAction(ProgramNode program) {
        if (tokenManager.MoreTokens()) {
            TokenType type = tokenManager.Peek(0).get().getType();
            if (type == TokenType.BEGIN) {
                tokenManager.MatchAndRemove(TokenType.BEGIN);
                AcceptSeparators();  
                BlockNode block = ParseBlock();
                program.addBeginBlock(block);   
                AcceptSeparators();
                return true;
            } else if (type == TokenType.END) {
                tokenManager.MatchAndRemove(TokenType.END);
                AcceptSeparators();  
                BlockNode block = ParseBlock();
                program.addEndBlock(block); 
                AcceptSeparators();
                return true;
            } else if (type == TokenType.OPEN_CURLY || isPossiblePattern(type)) {
                if (type != TokenType.OPEN_CURLY) {
                    tokenManager.MatchAndRemove(type);
                    AcceptSeparators();
                }
                BlockNode block = ParseBlock();
                program.addBlock(block); 
                return true;
            }
        }
        return false;
    }


    private boolean isPossiblePattern(TokenType type) {
        return type == TokenType.WORD;
    }

    public BlockNode ParseBlock() {
        BlockNode blockNode = new BlockNode();
        AcceptSeparators();
        if(!tokenManager.MatchAndRemove(TokenType.OPEN_CURLY).isPresent()) {
            throw new RuntimeException("Expected opening curly brace");
        }
        
        // Handle empty block
        if (tokenManager.Peek(0).get().getType() == TokenType.CLOSE_CURLY) {
            tokenManager.MatchAndRemove(TokenType.CLOSE_CURLY);
            return blockNode;
        }

        while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() != TokenType.CLOSE_CURLY) {
            Token currentToken = tokenManager.Peek(0).get();
            if (currentToken.getType() == TokenType.WORD) {
                tokenManager.MatchAndRemove(TokenType.WORD);
            } else if(currentToken.getType() == TokenType.SEPARATOR) {
                tokenManager.MatchAndRemove(TokenType.SEPARATOR);
            } else {
                throw new RuntimeException("Unexpected token inside block: " + currentToken.getValue());
            }
        }

        AcceptSeparators();

        if(!tokenManager.MatchAndRemove(TokenType.CLOSE_CURLY).isPresent()) {
            throw new RuntimeException("Expected closing curly brace");
        }

        return blockNode;
    }
   
    public TokenManager getTokenManager() {
        return tokenManager;
    }
    
    public Optional<Node> ParseLValue() {
        if (!tokenManager.MoreTokens()) {
            return Optional.empty();  
        }

        Token currentToken = tokenManager.Peek(0).orElse(null);

        if (currentToken == null) {
            return Optional.empty();  // This check might be redundant given the previous check, but it's safe
        }
        
        
        
        //This was a stupid idea but I needed it for debugging....
        //Numbers aren't valid L values
        /*
        if (currentToken.getType() == TokenType.NUMBER) {
            return Optional.of(new ConstantNode(currentToken.getValue()));
        }
        */
        


        // Handle $ reference
        if (currentToken.getType() == TokenType.DOLLAR) {
            tokenManager.MatchAndRemove(TokenType.DOLLAR);
            Node bottomLevelNode = ParseBottomLevel().orElseThrow(() -> new RuntimeException("Expected valid expression after $"));
            return Optional.of(new OperationNode(bottomLevelNode, Optional.empty(), OperationType.FIELD_SELECTOR));
        }

        // Handle arrays
        Optional<Token> nextTokenOptional = tokenManager.Peek(1);
        if (currentToken.getType() == TokenType.WORD && nextTokenOptional.isPresent() && nextTokenOptional.get().getType() == TokenType.OPEN_SQUARE) {
            String varName = currentToken.getValue();
            tokenManager.MatchAndRemove(TokenType.WORD);
            tokenManager.MatchAndRemove(TokenType.OPEN_SQUARE);
            Node indexExpr = ParseOperation().orElseThrow(() -> new RuntimeException("Expected valid index expression for array"));
            tokenManager.MatchAndRemove(TokenType.CLOSE_SQUARE);
            return Optional.of(new VariableReferenceNode(varName, Optional.of(indexExpr)));
        }

        // Handle simple variables
        if (currentToken.getType() == TokenType.WORD) {
            String varName = currentToken.getValue();
            tokenManager.MatchAndRemove(TokenType.WORD);
            return Optional.of(new VariableReferenceNode(varName, Optional.empty()));
        }
        
        // Handle string literal
        if (currentToken.getType() == TokenType.STRINGLITERAL 
        	    && tokenManager.Peek(1).isPresent() 
        	    && (tokenManager.Peek(1).get().getType() == TokenType.INC 
        	        || tokenManager.Peek(1).get().getType() == TokenType.DEC)) {
        	    throw new RuntimeException("Postfix increment/decrement is not valid for constants");
        }

        return Optional.empty();
    }

   
    
    public Optional<Node> ParseBottomLevel() {
        if (!tokenManager.MoreTokens()) {
            return Optional.empty(); 
        }
        
        Token token = tokenManager.Peek(0).get();
        switch(token.getType()) {
        case STRINGLITERAL:
            tokenManager.MatchAndRemove(TokenType.STRINGLITERAL);
            Node constantNode = new ConstantNode(token.getValue());
            if (tokenManager.MoreTokens()) {
                Token nextToken = tokenManager.Peek(0).get();
                if (nextToken.getType() == TokenType.INC || nextToken.getType() == TokenType.DEC) {
                    throw new RuntimeException("Postfix increment/decrement is not valid for constants");
                }
            }
            return Optional.of(constantNode);
            case NUMBER:
                tokenManager.MatchAndRemove(TokenType.NUMBER);
                return Optional.of(new ConstantNode(token.getValue()));
            case PATTERN:
                tokenManager.MatchAndRemove(TokenType.PATTERN);
                return Optional.of(new PatternNode(token.getValue()));
            case OPEN_PAREN:
                tokenManager.MatchAndRemove(TokenType.OPEN_PAREN);
                Node internal = ParseOperation().orElseThrow(() -> new RuntimeException("Expected expression inside parentheses"));
                if (!tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN).isPresent()) {
                    throw new RuntimeException("Mismatched parentheses");
                }
                return Optional.of(internal);
            case NOT:
                tokenManager.MatchAndRemove(TokenType.NOT);
                Node notChild = ParseBottomLevel().orElseThrow(() -> new RuntimeException("Expected expression after NOT"));
                return Optional.of(new OperationNode(notChild, Optional.empty(), OperationType.LOGICAL_NOT));
            case PLUS:
                tokenManager.MatchAndRemove(TokenType.PLUS);
                Token nextTokenForPlus = tokenManager.Peek(0).orElseThrow(() -> new RuntimeException("Expected expression after +"));
                if(nextTokenForPlus.getType() == TokenType.STRINGLITERAL) {
                    return Optional.empty();
                }
                Node unaryPosChild = ParseBottomLevel().orElseThrow(() -> new RuntimeException("Expected expression after +"));
                return Optional.of(new OperationNode(unaryPosChild, Optional.empty(), OperationType.UNARY_POSITIVE));

            case MINUS:
                tokenManager.MatchAndRemove(TokenType.MINUS);
                Token nextTokenForMinus = tokenManager.Peek(0).orElseThrow(() -> new RuntimeException("Expected expression after -"));
                if(nextTokenForMinus.getType() == TokenType.STRINGLITERAL) {
                    return Optional.empty();
                }
                Node unaryNegChild = ParseBottomLevel().orElseThrow(() -> new RuntimeException("Expected expression after -"));
                return Optional.of(new OperationNode(unaryNegChild, Optional.empty(), OperationType.UNARY_NEGATIVE));
            case INC:
                tokenManager.MatchAndRemove(TokenType.INC);
                Node preIncChild = ParseBottomLevel().orElseThrow(() -> new RuntimeException("Expected expression after ++"));
                return Optional.of(new OperationNode(preIncChild, Optional.empty(), OperationType.PREFIX_INCREMENT));
            case DEC:
                tokenManager.MatchAndRemove(TokenType.DEC);
                Node preDecChild = ParseBottomLevel().orElseThrow(() -> new RuntimeException("Expected expression after --"));
                return Optional.of(new OperationNode(preDecChild, Optional.empty(), OperationType.PREFIX_DECREMENT));
            default:
                Node bottomLevelResult = ParseLValue().orElse(null);
                if (bottomLevelResult != null) {
                    if (tokenManager.MoreTokens()) {
                        Token nextToken = tokenManager.Peek(0).get();
                        if (nextToken.getType() == TokenType.INC) {
                            if (bottomLevelResult instanceof ConstantNode) {
                                throw new RuntimeException("Postfix increment is not valid for constants");
                            }
                            tokenManager.MatchAndRemove(TokenType.INC);
                            return Optional.of(new OperationNode(bottomLevelResult, Optional.empty(), OperationType.POSTFIX_INCREMENT));
                        } else if (nextToken.getType() == TokenType.DEC) {
                            tokenManager.MatchAndRemove(TokenType.DEC);
                            return Optional.of(new OperationNode(bottomLevelResult, Optional.empty(), OperationType.POSTFIX_DECREMENT));
                        }
                    }
                    return Optional.of(bottomLevelResult);
                }
        }
        return Optional.empty();
    }
    

    
    //Helper, no need to do tokenManage.MatchAndRemove()...Need to clean up alot of code...
    private Optional<Token> matchAndRemove(TokenType type) {
        return tokenManager.MatchAndRemove(type);
    }

    private void throwParseException(String message) {
        throw new RuntimeException(message);
    }
 

    public Optional<Node> ParseOperation() {
        return ParseAssignment();
    }
    


    public Optional<Node> ParseAssignment() {
        Optional<Node> potentialLValue = ParseLValue();
     
        if (potentialLValue.isPresent() && tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.ASSIGNMENT) {
            // Ensure our left value is a valid VariableReferenceNode
            if (!(potentialLValue.get() instanceof VariableReferenceNode)) {
                throw new RuntimeException("Invalid left-hand side for assignment");
            }
            matchAndRemove(TokenType.ASSIGNMENT);

            // Parse the right-hand side.
            Optional<Node> rValue = ParseExpression();
            if (!rValue.isPresent()) {
                throwParseException("Expected a value after assignment");
            }

            return Optional.of(new OperationNode(potentialLValue.get(), rValue, OperationType.EQUALS));
        }

        // If it's not an assignment, parse as expression
        return ParseExpression();
    }
    
    public Optional<Node> ParseExpression() {
        Optional<Node> left = ParseTerm();
        while (tokenManager.MoreTokens()) {
            TokenType nextTokenType = tokenManager.Peek(0).get().getType();
            OperationType operationType;
            switch (nextTokenType) {
                case PLUS:
                    operationType = OperationType.ADDITION;
                    break;
                case MINUS:
                    operationType = OperationType.SUBTRACTION;
                    break;
                default:
                    return left;
            }
            matchAndRemove(nextTokenType);
            Optional<Node> right = ParseTerm();
            if (!right.isPresent()) {
                throwParseException("Expected term after addition or subtraction operator");
            }
            left = Optional.of(new OperationNode(left.get(), right, operationType));
        }
        return left;
    }
    
    public Optional<Node> ParseTernary() {
        Optional<Node> condition = ParseLogicalOr();
        if (!condition.isPresent()) {
            return Optional.empty();
        }

        if (tokenManager.MoreTokens()) {
            Token nextToken = tokenManager.Peek(0).get();
            if (nextToken.getType() == TokenType.QUESTION) {
                matchAndRemove(TokenType.QUESTION);
                Optional<Node> trueCase = ParseOperation();
                if (!trueCase.isPresent()) {
                    throwParseException("Expected value for true case of ternary operation");
                }
                matchAndRemove(TokenType.COLON);
                Optional<Node> falseCase = ParseOperation();
                if (!falseCase.isPresent()) {
                    throwParseException("Expected value for false case of ternary operation");
                }
                return Optional.of(new OperationNode(condition.get(), trueCase, OperationType.TERNARY_CONDITION));  // Adjust the OperationType as needed
            }
        }

        return condition;
    }

    
    public Optional<Node> ParseLogicalOr() {
        Optional<Node> left = ParseLogicalAnd();
        while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.OR) {
            matchAndRemove(TokenType.OR);
            Optional<Node> right = ParseLogicalAnd();
            if (!right.isPresent()) {
                throwParseException("Expected expression after logical OR");
            }
            left = Optional.of(new OperationNode(left.get(), right, OperationType.LOGICAL_OR));
        }
        return left;
    }
    
    public Optional<Node> ParseLogicalAnd() {
        Optional<Node> left = ParseComparison();
        while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.AND) {
            matchAndRemove(TokenType.AND);
            Optional<Node> right = ParseComparison();
            if (!right.isPresent()) {
                throwParseException("Expected expression after logical AND");
            }
            left = Optional.of(new OperationNode(left.get(), right, OperationType.LOGICAL_AND));
        }
        return left;
    }
    
    public Optional<Node> ParseComparison() {
        Optional<Node> left = ParseExpression();
        while (tokenManager.MoreTokens()) {
            TokenType nextTokenType = tokenManager.Peek(0).get().getType();
            OperationType operationType;
            switch (nextTokenType) {
                case LT:
                    operationType = OperationType.LESS_THAN;
                    break;
                case GT:
                    operationType = OperationType.GREATER_THAN;
                    break;
                case LT_EQ:
                    operationType = OperationType.LESS_THAN_OR_EQUALS;
                    break;
                case GT_EQ:
                    operationType = OperationType.GREATER_THAN_OR_EQUALS;
                    break;
                case EQ_EQ:
                    operationType = OperationType.EQUALS;
                    break;
                case NOT_EQ:
                    operationType = OperationType.NOT_EQUALS;
                    break;
                default:
                    return left;
            }
            matchAndRemove(nextTokenType);
            Optional<Node> right = ParseExpression();
            if (!right.isPresent()) {
                throwParseException("Expected expression after comparison operator");
            }
            left = Optional.of(new OperationNode(left.get(), right, operationType));
        }
        return left;
    }
    

    
    public Optional<Node> ParseTerm() {
        Optional<Node> left = ParseFactor();
        while (tokenManager.MoreTokens()) {
            TokenType nextTokenType = tokenManager.Peek(0).get().getType();
            OperationType operationType;
            switch (nextTokenType) {
                case ASTERISK:
                    operationType = OperationType.MULTIPLICATION;
                    break;
                case DIVIDE:
                    operationType = OperationType.DIVISION;
                    break;
                case MODULO:
                    operationType = OperationType.MODULUS;
                    break;
                default:
                    return left;
            }
            matchAndRemove(nextTokenType);
            Optional<Node> right = ParseFactor();
            if (!right.isPresent()) {
                throwParseException("Expected factor after multiplication, division, or modulus operator");
            }
            left = Optional.of(new OperationNode(left.get(), right, operationType));
        }
        return left;
    }
    
    public Optional<Node> ParseFactor() {
        Optional<Node> base = ParseBottomLevel();
        if (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.EXPONENT) {
            matchAndRemove(TokenType.EXPONENT);
            Optional<Node> exponent = ParseFactor();  // recursion to handle right-associativity
            if (!exponent.isPresent()) {
                throwParseException("Expected expression after '^'");
            }
            return Optional.of(new OperationNode(base.get(), exponent, OperationType.EXPONENTIATION));
        }
        return base;
    }
    
    public Optional<Node> ParseExponentiation() {
        Optional<Node> base = ParseBottomLevel();
        if (!base.isPresent()) {
            return Optional.empty();
        }
        
        if (!tokenManager.MoreTokens()) {
            return base;
        }
        
        Token nextToken = tokenManager.Peek(0).get();
        if (nextToken.getType() == TokenType.EXPONENT) {
            tokenManager.MatchAndRemove(TokenType.EXPONENT);
            Optional<Node> exponent = ParseExponentiation();
            if (!exponent.isPresent()) {
                throw new RuntimeException("Expected an expression after '^'");
            }
            return Optional.of(new OperationNode(base.get(), exponent, OperationType.EXPONENTIATION));
        }
        
        return base;
    }


 
  
}
