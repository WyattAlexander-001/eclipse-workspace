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
            
            // Assuming function name is the next token
            Token funcNameToken = tokenManager.MatchAndRemove(TokenType.WORD).orElseThrow(() -> new RuntimeException("Expected function name"));
            String functionName = funcNameToken.getValue();
            AcceptSeparators();

            // Parsing parameters
            LinkedList<String> parameters = new LinkedList<>();
            tokenManager.MatchAndRemove(TokenType.OPEN_PAREN);
            AcceptSeparators();

            while (tokenManager.Peek(0).get().getType() != TokenType.CLOSE_PAREN) {
                Token paramToken = tokenManager.MatchAndRemove(TokenType.WORD).orElseThrow(() -> new RuntimeException("Expected parameter name"));
                parameters.add(paramToken.getValue());
                AcceptSeparators();
                if (tokenManager.Peek(0).get().getType() == TokenType.COMMA) {
                    tokenManager.MatchAndRemove(TokenType.COMMA);
                    AcceptSeparators(); // Accept separators after each comma
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
            return Optional.empty();  // No more tokens to process
        }

        Token currentToken = tokenManager.Peek(0).orElse(null);

        if (currentToken == null) {
            return Optional.empty();  // This check might be redundant given the previous check, but it's safe
        }

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
                return Optional.of(new ConstantNode(token.getValue()));
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
                return ParseLValue();
        }
    }

    public Optional<Node> ParseOperation() {
        return ParseBottomLevel();
    }

}
