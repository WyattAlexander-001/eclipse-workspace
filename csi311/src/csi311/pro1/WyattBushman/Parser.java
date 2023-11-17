package csi311.pro1.WyattBushman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class Parser {
    private TokenManager tokenManager;
    
    private boolean debugMode = false;

    public Parser(LinkedList<Token> tokens, boolean debugMode) {
        this.tokenManager = new TokenManager(tokens);
        this.debugMode = debugMode;
    }

    public Parser(LinkedList<Token> tokens) {
        this.tokenManager = new TokenManager(tokens);
    }
    
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

//    public BlockNode ParseBlock() {
//        BlockNode blockNode = new BlockNode();
//        AcceptSeparators();
//        if(!tokenManager.MatchAndRemove(TokenType.OPEN_CURLY).isPresent()) {
//            throw new RuntimeException("Expected opening curly brace");
//        }
//        
//        // Handle empty block
//        if (tokenManager.Peek(0).get().getType() == TokenType.CLOSE_CURLY) {
//            tokenManager.MatchAndRemove(TokenType.CLOSE_CURLY);
//            return blockNode;
//        }
//
//        while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() != TokenType.CLOSE_CURLY) {
//            Token currentToken = tokenManager.Peek(0).get();
//            if (currentToken.getType() == TokenType.WORD) {
//                tokenManager.MatchAndRemove(TokenType.WORD);
//            } else if(currentToken.getType() == TokenType.SEPARATOR) {
//                tokenManager.MatchAndRemove(TokenType.SEPARATOR);
//            } else {
//                throw new RuntimeException("Unexpected token inside block: " + currentToken.getValue());
//            }
//        }
//
//        AcceptSeparators();
//
//        if(!tokenManager.MatchAndRemove(TokenType.CLOSE_CURLY).isPresent()) {
//            throw new RuntimeException("Expected closing curly brace");
//        }
//
//        return blockNode;
//    }

    public BlockNode ParseBlock() {
        BlockNode blockNode = new BlockNode();
        AcceptSeparators();
        if (!tokenManager.MatchAndRemove(TokenType.OPEN_CURLY).isPresent()) {
            throw new RuntimeException("Expected opening curly brace");
        }
        
        while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() != TokenType.CLOSE_CURLY) {
            Optional<Node> statementNode = ParseOperation(); // Attempt to parse a statement
            if (!statementNode.isPresent()) {
                throw new RuntimeException("Expected a valid statement inside block");
            }
            blockNode.addStatement((StatementNode)statementNode.get()); // Add parsed statement to the block
            AcceptSeparators();
        }

        if (!tokenManager.MatchAndRemove(TokenType.CLOSE_CURLY).isPresent()) {
            throw new RuntimeException("Expected closing curly brace");
        }

        return blockNode;
    }

    
    public TokenManager getTokenManager() {
        return tokenManager;
    }
    
    //Helper, no need to do tokenManage.MatchAndRemove()...Need to clean up alot of code...
    private Optional<Token> matchAndRemove(TokenType type) {
        return tokenManager.MatchAndRemove(type);
    }

    private void throwParseException(String message) {
        throw new RuntimeException(message);
    }
    
    /*
    public Optional<Node> ParseString() {
        if (!tokenManager.MoreTokens()) {
            return Optional.empty();
        }
        return matchAndRemove(TokenType.STRINGLITERAL).map(token -> new ConstantNode(token.getValue()));
    }
    */
    
    //--------------------------------
 
    /*
	ParseOperation <- Start Here
	ParseAssignment
	ParseTernary
	ParseOr
	ParseAnd
	ParseArrayMembership
	ParseMatch
	ParseCompare
	ParseConcatenation
	ParseExpression
	ParseTerm
	ParseFactor
	ParseExponents
	ParsePostIncrementAndDecrement
	ParseLValue
	ParseBottomLevel

     */

    public Optional<Node> ParseOperation() {
        Optional<Node> node = ParseAssignment();
        if(node.isPresent()) {
            return node;
        }

        return ParseBottomLevel();
    }

        
    public Optional<Node> ParseAssignment() {
        if (debugMode) {
            if (!tokenManager.MoreTokens()) {
                return Optional.empty(); // Early exit if there are no more tokens.
            }
            
            Token currentToken = tokenManager.Peek(0).orElse(null);
            
            // If the current token is a word (variable name)
            if (currentToken != null && currentToken.getType() == TokenType.WORD) {
                Optional<Node> left = Optional.of(new VariableReferenceNode(currentToken.getValue(), Optional.empty()));
                matchAndRemove(TokenType.WORD); // Consume the token.

                if (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.ASSIGNMENT) {
                    matchAndRemove(TokenType.ASSIGNMENT);

                    if (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.NUMBER) {
                        Token numToken = matchAndRemove(TokenType.NUMBER).orElse(null);
                        Optional<Node> right = Optional.of(new ConstantNode(numToken.getValue()));

                        // Create the AssignmentNode with the left and right sides.
                        return Optional.of(new AssignmentNode(left.get(), right.get()));
                    }
                }
            }
            return Optional.empty(); // No match.
        } else {
            Optional<Node> left = ParseTernary();
            
            if(tokenManager.MoreTokens()) {
                Token currentToken = tokenManager.Peek(0).orElse(null);
                
                // If the current token is an assignment
                if (currentToken != null && currentToken.getType() == TokenType.ASSIGNMENT) {
                    matchAndRemove(TokenType.ASSIGNMENT);
                    
                    // Parse the right-hand side of the assignment
                    Optional<Node> right = ParseOperation();
                    if(!right.isPresent()) {
                        throwParseException("Expected an expression after assignment");
                    }
                    
                    // Created AssignmentNode with the left and right sides
                    return Optional.of(new AssignmentNode(left.get(), right.get()));
                }
            }
            
            return left; // If it's not an assignment, it will just chain to ParseTernary result.
        }
    }
    

    public Optional<Node> ParseTernary() {
        if (debugMode) {
            // Check if there's a condition, using a word
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node condition = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check if the next token is a '?'
            if (!tokenManager.MoreTokens() || tokenManager.MatchAndRemove(TokenType.QUESTION).isEmpty()) {
                throwParseException("Expected '?' after the ternary condition.");
            }

            // Parse the true expression just using a number....
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.NUMBER) {
                throwParseException("Expected expression for true branch in ternary operation");
            }
            Node trueBranch = new ConstantNode(tokenManager.MatchAndRemove(TokenType.NUMBER).get().getValue());

            // Check if the next token is a ':'
            if (!tokenManager.MoreTokens() || tokenManager.MatchAndRemove(TokenType.COLON).isEmpty()) {
                throwParseException("Expected ':' in the ternary operation.");
            }

            // Parse the false expression, just using a Number...
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.NUMBER) {
                throwParseException("Expected expression for false branch in ternary operation");
            }
            Node falseBranch = new ConstantNode(tokenManager.MatchAndRemove(TokenType.NUMBER).get().getValue());

            return Optional.of(new TernaryNode(condition, trueBranch, falseBranch));
        } else {
            Optional<Node> condition = ParseOr();
            if (!condition.isPresent()) {
                return Optional.empty(); // No valid condition, so it's not a ternary
            }
            
            // Check for the '?' token
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.QUESTION) {
                return condition; // If no '?', then it's just the condition
            }
            tokenManager.MatchAndRemove(TokenType.QUESTION); // Consume the '?' token
            
            // Parse the true branch (can be any expression)
            Optional<Node> trueBranch = ParseExpression(); // Assuming ParseExpression() is a method that handles generic expressions
            if (!trueBranch.isPresent()) {
                throwParseException("Expected expression for true branch in ternary operation");
            }
            
            // Check for the ':' token
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.COLON) {
                throwParseException("Expected ':' in the ternary operation.");
            }
            tokenManager.MatchAndRemove(TokenType.COLON); // Consume the ':' token
            
            // Parse the false branch (can be any expression)
            Optional<Node> falseBranch = ParseExpression(); 
            if (!falseBranch.isPresent()) {
                throwParseException("Expected expression for false branch in ternary operation");
            }
            
            return Optional.of(new TernaryNode(condition.get(), trueBranch.get(), falseBranch.get()));
        }
    }



    public Optional<Node> ParseOr() {
        if (debugMode) {
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node leftOperand = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check if the next token is a logical '||' operator
            if (!tokenManager.MoreTokens() || tokenManager.MatchAndRemove(TokenType.OR).isEmpty()) {  // Assuming you have TokenType.OR for "||"
                return Optional.of(leftOperand);  // If there's no '||', just return the left operand as the result
            }

            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected operand after '||' operator");
            }
            Node rightOperand = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            return Optional.of(new OrNode(leftOperand, rightOperand));
        } else {
            Optional<Node> left = ParseAnd(); // Start with a higher precedence operation

            while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.OR) {
                matchAndRemove(TokenType.OR);

                Optional<Node> right = ParseAnd(); // Parsing the right side of the operation
                if (!right.isPresent()) {
                    throwParseException("Expected an expression after '||' operator");
                }

                left = Optional.of(new OrNode(left.get(), right.get()));
            }

            return left;
        }
    }


    public Optional<Node> ParseAnd() {
        if (debugMode) {
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check if the next token is a '&&'
            if (!tokenManager.MoreTokens() || tokenManager.MatchAndRemove(TokenType.AND).isEmpty()) {
                throwParseException("Expected '&&' for logical AND operation.");
            }
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected right operand for logical AND operation");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            return Optional.of(new AndNode(left, right));
        } else {
            Optional<Node> left = ParseArrayMembership();  // Parsing the left side with the next highest precedence function

            // Continuously check for consecutive AND operations (allowing for chaining like x && y && z)
            while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.AND) {
                tokenManager.MatchAndRemove(TokenType.AND);  // Consume the '&&' token

                Optional<Node> right = ParseOr();  // Parsing the right side

                if (!right.isPresent()) {
                    throwParseException("Expected right operand for logical AND operation");
                }

                left = Optional.of(new AndNode(left.get(), right.get())); 
            }

            return left;
        }
    }


    public Optional<Node> ParseArrayMembership() {
        if (debugMode) {
            // Check for left operand, which is the expr part of "expr in array"
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the 'IN' token
            if (!tokenManager.MoreTokens() || tokenManager.MatchAndRemove(TokenType.IN).isEmpty()) {
                return ParseMatch();
            }

            // Parse the right operand, which represents the array
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected array name after 'IN' token.");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            return Optional.of(new ArrayMembershipNode(left, right));
        } else {
            Optional<Node> left = ParseMatch();

            // Check for 'IN' token
            if (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.IN) {
                tokenManager.MatchAndRemove(TokenType.IN);  // Consume the 'IN' token

                Optional<Node> right = ParseMatch();

                if (!right.isPresent()) {
                    throwParseException("Expected array name after 'IN' token.");
                }

                left = Optional.of(new ArrayMembershipNode(left.get(), right.get()));
            }

            return left;
        }
    }


    public Optional<Node> ParseMatch() {
        if (debugMode) {
            // Check for left operand before the `~` or `!~`
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the `~` or `!~` token for match
            TokenType matchTokenType = tokenManager.Peek(0).get().getType();
            if (!tokenManager.MoreTokens() || (matchTokenType != TokenType.MATCH && matchTokenType != TokenType.NOT_MATCH)) {
                return ParseCompare();
            }
            tokenManager.MatchAndRemove(matchTokenType);  // Consume the `~` or `!~` token

            // Parse the right operand, which represents the regex pattern
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.PATTERN) {
                throwParseException("Expected regex pattern after 'MATCH' token.");
            }
            Node right = new RegexPatternNode(tokenManager.MatchAndRemove(TokenType.PATTERN).get().getValue());

            if (matchTokenType == TokenType.MATCH) {
                return Optional.of(new MatchNode(left, right));
            } else {
                return Optional.of(new NotMatchNode(left, right));
            }
        } else {
            // Start by trying to parse the left operand before the `~` or `!~`
            Optional<Node> left = ParseCompare();
            if (!left.isPresent()) {
                return Optional.empty();
            }

            // If no more tokens or if the next token isn't a match-related token, return the left operand as is
            if (!tokenManager.MoreTokens() || 
                (tokenManager.Peek(0).get().getType() != TokenType.MATCH && 
                 tokenManager.Peek(0).get().getType() != TokenType.NOT_MATCH)) {
                return left;
            }

            // Capture the match token type and consume it
            TokenType matchTokenType = tokenManager.Peek(0).get().getType();
            tokenManager.MatchAndRemove(matchTokenType);

            // Parse the right operand, which represents the regex pattern
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.PATTERN) {
                throwParseException("Expected regex pattern after 'MATCH' token.");
            }
            Node right = new RegexPatternNode(tokenManager.MatchAndRemove(TokenType.PATTERN).get().getValue());

            // Return the appropriate node based on the match token type
            if (matchTokenType == TokenType.MATCH) {
                return Optional.of(new MatchNode(left.get(), right));
            } else {
                return Optional.of(new NotMatchNode(left.get(), right));
            }
        }
    }
    
    public Optional<Node> ParseCompare() {
        if (debugMode) {
            // Check for left operand before the comparison operators
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the comparison operators
            TokenType compareTokenType = tokenManager.Peek(0).get().getType();
            if (!tokenManager.MoreTokens() || 
                !(compareTokenType == TokenType.LT || compareTokenType == TokenType.LT_EQ ||
                  compareTokenType == TokenType.GT || compareTokenType == TokenType.GT_EQ ||
                  compareTokenType == TokenType.EQ_EQ || compareTokenType == TokenType.NOT_EQ)) {
                return ParseConcatenation();
            }
            tokenManager.MatchAndRemove(compareTokenType);  // Consume the comparison token

            // Parse the right operand
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected operand after comparison operator.");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            return Optional.of(new CompareNode(left, right, compareTokenType));
        } else {
            Optional<Node> left = ParseConcatenation();  // Begin by parsing the left operand

            if (tokenManager.MoreTokens()) {
                TokenType compareTokenType = tokenManager.Peek(0).get().getType();
                
                if (compareTokenType == TokenType.LT || compareTokenType == TokenType.LT_EQ ||
                    compareTokenType == TokenType.GT || compareTokenType == TokenType.GT_EQ ||
                    compareTokenType == TokenType.EQ_EQ || compareTokenType == TokenType.NOT_EQ) {
                    
                    tokenManager.MatchAndRemove(compareTokenType);  // Consume the comparison token

                    // Parse the right operand
                    Optional<Node> right = ParseConcatenation();
                    if (!right.isPresent()) {
                        throwParseException("Expected operand after comparison operator.");
                    }

                    return Optional.of(new CompareNode(left.get(), right.get(), compareTokenType));
                }
            }
            return left;
        }
    }
    
    //Concat is weird...Concating every other language is simple "hello" + "world"
    /*
    
    # Awk code: 
    BEGIN {
    string1 = "Hello"
    string2 = "World"
    result = string1 " " string2
    print result
	} 
     */
    

    public Optional<Node> ParseConcatenation() {
        if (debugMode) {
            // Check for the first operand of the concatenation
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // For the sake of this example, we'll assume concatenation is represented by a `+` token
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.PLUS) {
                return ParseExpression();
            }
            tokenManager.MatchAndRemove(TokenType.PLUS);  // Consume the `+` token

            // Parse the second operand
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected another operand after + for concatenation.");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            return Optional.of(new ConcatenationNode(left, right));
        } else {
            Optional<Node> left = ParseExpression();  
            if (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() == TokenType.WORD) {
                Optional<Node> right = ParseExpression();

                if (!right.isPresent()) {
                    throwParseException("Expected operand for concatenation.");
                }

                return Optional.of(new ConcatenationNode(left.get(), right.get()));
            }
            return left;
        }
    }


    public Optional<Node> ParseExpression() {
        if (debugMode) {
            // Check for left operand before the '+' or '-'
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the '+' or '-' operators
            TokenType operatorType = tokenManager.Peek(0).get().getType();
            if (!tokenManager.MoreTokens() || !(operatorType == TokenType.PLUS || operatorType == TokenType.MINUS)) {
                return ParseTerm();
            }
            tokenManager.MatchAndRemove(operatorType);  // Consume the operator token

            // Parse the right operand
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected operand after '+' or '-' operator.");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            if (operatorType == TokenType.PLUS) {
                return Optional.of(new AdditionNode(left, right));
            } else {
                return Optional.of(new SubtractionNode(left, right));
            }
        } else {
            Optional<Node> left = ParseTerm();

            while (tokenManager.MoreTokens()) {
                TokenType operatorType = tokenManager.Peek(0).get().getType();
                if (operatorType == TokenType.PLUS || operatorType == TokenType.MINUS) {
                    tokenManager.MatchAndRemove(operatorType);  // Consume the operator token

                    Optional<Node> right = ParseTerm();
                    if (!right.isPresent()) {
                        throwParseException("Expected operand after '+' or '-' operator.");
                    }

                    if (operatorType == TokenType.PLUS) {
                        left = Optional.of(new AdditionNode(left.get(), right.get()));
                    } else {
                        left = Optional.of(new SubtractionNode(left.get(), right.get()));
                    }
                } else {
                    break;  // Exit the loop if we don't find an addition or subtraction operation
                }
            }
            return left;
        }
    }


    public Optional<Node> ParseTerm() {
        if (debugMode) {
            // Check for left operand before the `*`, `/`, or `%` operators
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the operators
            TokenType termTokenType = tokenManager.Peek(0).get().getType();
            if (!tokenManager.MoreTokens() || 
                !(termTokenType == TokenType.ASTERISK || termTokenType == TokenType.DIVIDE || termTokenType == TokenType.MODULO)) {
                return ParseFactor();
            }
            tokenManager.MatchAndRemove(termTokenType);  // Consume the token

            // Parse the right operand
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected operand after term operator.");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            switch (termTokenType) {
            case ASTERISK:
                return Optional.of(new MultiplicationNode(left, right));
            case DIVIDE:
                return Optional.of(new DivisionNode(left, right));
            case MODULO:
                return Optional.of(new ModuloNode(left, right));
            default:
                throwParseException("Unexpected token type in ParseTerm.");
                break;
            }	

        } else {
            Optional<Node> left = ParseFactor();  // Begin by parsing the left operand

            if (tokenManager.MoreTokens()) {
                TokenType termTokenType = tokenManager.Peek(0).get().getType();
                
                if (termTokenType == TokenType.ASTERISK || termTokenType == TokenType.DIVIDE || termTokenType == TokenType.MODULO) {
                    
                    tokenManager.MatchAndRemove(termTokenType);  // Consume the term token

                    // Parse the right operand
                    Optional<Node> right = ParseFactor();
                    if (!right.isPresent()) {
                        throwParseException("Expected operand after term operator.");
                    }

                    switch (termTokenType) {
                        case ASTERISK:
                            return Optional.of(new MultiplicationNode(left.get(), right.get()));
                        case DIVIDE:
                            return Optional.of(new DivisionNode(left.get(), right.get()));
                        case MODULO:
                            return Optional.of(new ModuloNode(left.get(), right.get()));
                        default:
                            throwParseException("Unexpected token type in ParseTerm.");
                            break;
                    }
                }
            }
            return left;
        }
        return Optional.empty();
    }


    public Optional<Node> ParseFactor() {
        return ParseExponents();
    }

    public Optional<Node> ParseExponents() {
        if (debugMode) {
            // Check for left operand before the '^' operator
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node left = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the '^' operator
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.EXPONENT) {
                return ParsePostIncrementAndDecrement();
            }
            tokenManager.MatchAndRemove(TokenType.EXPONENT);  // Consume the '^' token

            // Parse the right operand
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                throwParseException("Expected operand after '^' operator.");
            }
            Node right = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            return Optional.of(new OperationNode(left, Optional.of(right), OperationType.EXPONENTIATION));
        } else {
            Optional<Node> left = ParsePostIncrementAndDecrement();  // Begin by parsing the left operand

            if (tokenManager.MoreTokens()) {
                if (tokenManager.Peek(0).get().getType() == TokenType.EXPONENT) {
                    tokenManager.MatchAndRemove(TokenType.EXPONENT);  // Consume the '^' token

                    // Parse the right operand
                    Optional<Node> right = ParsePostIncrementAndDecrement();
                    if (!right.isPresent()) {
                        throwParseException("Expected operand after '^' operator.");
                    }

                    return Optional.of(new OperationNode(left.get(), right, OperationType.EXPONENTIATION));
                }
            }
            return left;
        }
    }


    public Optional<Node> ParsePostIncrementAndDecrement() {
        if (debugMode) {
            if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                return Optional.empty();
            }
            Node operand = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());

            // Check for the postfix operators
            TokenType postOpTokenType = tokenManager.Peek(0).get().getType();
            if (postOpTokenType != TokenType.INC && postOpTokenType != TokenType.DEC) {
                return ParseLValue();
            }
            tokenManager.MatchAndRemove(postOpTokenType);  // Consume the post-operator token

            OperationType opType = (postOpTokenType == TokenType.INC) ? OperationType.POSTFIX_INCREMENT : OperationType.POSTFIX_DECREMENT;
            return Optional.of(new OperationNode(operand, Optional.empty(), opType));
        } else {
            Optional<Node> operand = ParseLValue();

            if (tokenManager.MoreTokens()) {
                TokenType postOpTokenType = tokenManager.Peek(0).get().getType();
                
                if (postOpTokenType == TokenType.INC || postOpTokenType == TokenType.DEC) {
                    tokenManager.MatchAndRemove(postOpTokenType);  // Consume the post-operator token

                    OperationType opType = (postOpTokenType == TokenType.INC) ? OperationType.POSTFIX_INCREMENT : OperationType.POSTFIX_DECREMENT;
                    return Optional.of(new OperationNode(operand.get(), Optional.empty(), opType));
                }
            }
            return operand;
        }
    }

//Original:    
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
                Optional<Node> internalOpt = ParseOperation(); 
                if (!internalOpt.isPresent()) {
                    throw new RuntimeException("Expected expression inside parentheses");
                }
                Node internal = internalOpt.get();
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
                        Optional<Node> postOpNode = ParsePostIncrementAndDecrement();
                        if(postOpNode.isPresent()) {
                            return postOpNode;
                        }
                    }
                    return Optional.of(bottomLevelResult);
                }
        }
        return Optional.empty();
    }
    
    public Optional<Node> ParseLValue() {
        if (debugMode) {
            if (!tokenManager.MoreTokens()) {
                return Optional.empty();
            }
            
            Token currentToken = tokenManager.Peek(0).orElse(null);
            if (currentToken == null) {
                return Optional.empty();
            }

            // Handle $ reference
            if (currentToken.getType() == TokenType.DOLLAR) {
                tokenManager.MatchAndRemove(TokenType.DOLLAR);
                if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                    throwParseException("Expected valid expression after $");
                }
                Node bottomLevelNode = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());
                return Optional.of(new OperationNode(bottomLevelNode, Optional.empty(), OperationType.FIELD_SELECTOR));
            }

            // Handle arrays
            Optional<Token> nextTokenOptional = tokenManager.Peek(1);
            if (currentToken.getType() == TokenType.WORD && nextTokenOptional.isPresent() && nextTokenOptional.get().getType() == TokenType.OPEN_SQUARE) {
                String varName = currentToken.getValue();
                tokenManager.MatchAndRemove(TokenType.WORD);
                tokenManager.MatchAndRemove(TokenType.OPEN_SQUARE);
                if (!tokenManager.MoreTokens() || tokenManager.Peek(0).get().getType() != TokenType.WORD) {
                    throwParseException("Expected valid index expression for array");
                }
                Node indexExpr = new VariableReferenceNode(tokenManager.MatchAndRemove(TokenType.WORD).get().getValue(), Optional.empty());
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
        } else {
            if (!tokenManager.MoreTokens()) {
                return Optional.empty();
            }

            Token currentToken = tokenManager.Peek(0).orElse(null);
            if (currentToken == null) {
                return Optional.empty();
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

            // Handle string literal with postfix operations
            if (currentToken.getType() == TokenType.STRINGLITERAL 
                    && tokenManager.Peek(1).isPresent() 
                    && (tokenManager.Peek(1).get().getType() == TokenType.INC 
                        || tokenManager.Peek(1).get().getType() == TokenType.DEC)) {
                throw new RuntimeException("Postfix increment/decrement is not valid for constants");
            }

            return Optional.empty();
        }
    }
    
    public void setDebugMode(boolean mode) {
        this.debugMode = mode;
    }
    
    //===============Parser 4 ================
    
    public Optional<Node> ParseStatement() {
        List<Supplier<Optional<? extends Node>>> parsers = Arrays.asList(
            this::ParseIf,
            this::ParseFor,
            this::ParseContinue,
            this::ParseBreak,
            this::ParseDelete,
            this::ParseWhile,
            this::ParseDoWhile,
            this::ParseReturn,
            this::ParseFunctionCall
        );
        
        for (Supplier<Optional<? extends Node>> parser : parsers) {
            Optional<? extends Node> node = parser.get();
            if (node.isPresent()) {
                return (Optional<Node>) node;
            }
        }
        return Optional.empty();
    }

    public Optional<ContinueNode> ParseContinue() {
        if (tokenManager.MatchAndRemove(TokenType.CONTINUE).isPresent()) {
            return Optional.of(new ContinueNode());
        }
        return Optional.empty();
    }

    public Optional<BreakNode> ParseBreak() {
        if (tokenManager.MatchAndRemove(TokenType.BREAK).isPresent()) {
            return Optional.of(new BreakNode());
        }
        return Optional.empty();
    }
    
    public Optional<Node> ParseBlockOrStatement() {
        Optional<Node> blockOpt = Optional.ofNullable(ParseBlock());
        if (blockOpt.isPresent()) {
            return blockOpt;
        } else {
            return ParseStatement();
        }
    }
    
    public Optional<IfNode> ParseIf() {
        if (!matchAndRemove(TokenType.IF).isPresent()) {
            return Optional.empty();
        }

        // Expecting an open parenthesis after 'if'
        if (!matchAndRemove(TokenType.OPEN_PAREN).isPresent()) {
            throwParseException("Expected '(' after 'if'");
        }

        // Parse the condition inside the parentheses
        Optional<Node> conditionOpt = ParseExpression();
        if (!conditionOpt.isPresent()) {
            throwParseException("Expected a condition expression after 'if('");
        }
        Node condition = conditionOpt.get();

        // Expecting a close parenthesis after the condition
        if (!matchAndRemove(TokenType.CLOSE_PAREN).isPresent()) {
            throwParseException("Expected ')' after 'if' condition");
        }

        Optional<Node> trueBranchOpt = ParseBlockOrStatement();
        if (!trueBranchOpt.isPresent()) {
            throwParseException("Expected a statement or block after 'if' condition");
        }

        if (!(trueBranchOpt.get() instanceof BlockNode)) {
            throwParseException("Expected a block node after 'if' condition");
        }
        BlockNode trueBranch = (BlockNode) trueBranchOpt.get();

        // Construct the IfNode with the parsed data
        IfNode ifNode = new IfNode(condition, trueBranch);

        // Check if there's an 'else' branch
        if (matchAndRemove(TokenType.ELSE).isPresent()) {
            Optional<IfNode> falseBranchOpt = ParseIf();
            if (!falseBranchOpt.isPresent()) {
                throwParseException("Expected an 'if' node after 'else'");
            }
            ifNode.setElseBranch(falseBranchOpt.get());
        }

        return Optional.of(ifNode);
    }


    public Optional<ForNode> ParseFor() {
        if (!tokenManager.MatchAndRemove(TokenType.FOR).isPresent()) {
            return Optional.empty();
        }

        // Expecting an open parenthesis after 'for'
        if (!tokenManager.MatchAndRemove(TokenType.OPEN_PAREN).isPresent()) {
            throwParseException("Expected '(' after 'for'");
        }

        // Parse initialization part of the for loop
        Optional<Node> initializationOpt = ParseExpression();
        if (!initializationOpt.isPresent()) {
            throwParseException("Expected an initialization expression in 'for' loop");
        }
        Node initialization = initializationOpt.get();

        // Expecting a semicolon after initialization
        if (!tokenManager.MatchAndRemove(TokenType.SEPARATOR).isPresent()) {
            throwParseException("Expected ';' after initialization in 'for' loop");
        }

        // Parse condition part of the for loop
        Optional<Node> conditionOpt = ParseExpression();
        if (!conditionOpt.isPresent()) {
            throwParseException("Expected a condition expression in 'for' loop");
        }
        Node condition = conditionOpt.get();

        // Expecting a semicolon after condition
        if (!tokenManager.MatchAndRemove(TokenType.SEPARATOR).isPresent()) {
            throwParseException("Expected ';' after condition in 'for' loop");
        }

        // Parse iteration part of the for loop
        Optional<Node> iterationOpt = ParseExpression();
        if (!iterationOpt.isPresent()) {
            throwParseException("Expected an iteration expression in 'for' loop");
        }
        Node iteration = iterationOpt.get();

        // Expecting a close parenthesis after iteration
        if (!tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN).isPresent()) {
            throwParseException("Expected ')' after iteration in 'for' loop");
        }

        // Parse the block of the for loop
        Optional<Node> blockOpt = ParseBlockOrStatement();
        if (!blockOpt.isPresent() || !(blockOpt.get() instanceof BlockNode)) {
            throwParseException("Expected a block after 'for' loop");
        }
        BlockNode block = (BlockNode) blockOpt.get();

        // Construct the ForNode with the parsed data
        ForNode forNode = new ForNode(initialization, condition, iteration, block);

        return Optional.of(forNode);
    }

    
//    public Optional<ForEachNode> ParseForEach(){
//        if (!tokenManager.MatchAndRemove(TokenType.FOR).isPresent()) {
//            return Optional.empty();
//        }
//
//        // (rest of your ParseFor logic)
//        // Ensure to throw clear exceptions for unexpected tokens
//
//        return Optional.of(forNode);
//    }

    public Optional<WhileNode> ParseWhile() {
        // Match the 'while' keyword
        if (!tokenManager.MatchAndRemove(TokenType.WHILE).isPresent()) {
            return Optional.empty();
        }

        // Expecting an open parenthesis after 'while'
        if (!tokenManager.MatchAndRemove(TokenType.OPEN_PAREN).isPresent()) {
            throwParseException("Expected '(' after 'while'");
        }

        // Parse the condition inside the parentheses
        Optional<Node> conditionOpt = ParseExpression();
        if (!conditionOpt.isPresent()) {
            throwParseException("Expected a condition expression after 'while('");
        }
        Node condition = conditionOpt.get();

        // Expecting a close parenthesis after the condition
        if (!tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN).isPresent()) {
            throwParseException("Expected ')' after 'while' condition");
        }
        Optional<Node> blockOpt = ParseBlockOrStatement();
        if (!blockOpt.isPresent() || !(blockOpt.get() instanceof BlockNode)) {
            throwParseException("Expected a block or statement after 'while' condition");
        }
        BlockNode block = (BlockNode) blockOpt.get();
        WhileNode whileNode = new WhileNode(condition, block);
        return Optional.of(whileNode);
    }
    
    public Optional<DoWhileNode> ParseDoWhile() {
        // Match the 'do' keyword
        if (!tokenManager.MatchAndRemove(TokenType.DO).isPresent()) {
            return Optional.empty();
        }

        Optional<Node> blockOpt = ParseBlockOrStatement();
        if (!blockOpt.isPresent() || !(blockOpt.get() instanceof BlockNode)) {
            throwParseException("Expected a block or statement after 'do'");
        }
        BlockNode block = (BlockNode) blockOpt.get();

        // Match the 'while' keyword after the block
        if (!tokenManager.MatchAndRemove(TokenType.WHILE).isPresent()) {
            throwParseException("Expected 'while' after 'do' block or statement");
        }

        // Expecting an open parenthesis after 'while'
        if (!tokenManager.MatchAndRemove(TokenType.OPEN_PAREN).isPresent()) {
            throwParseException("Expected '(' after 'while'");
        }

        // Parse the condition inside the parentheses
        Optional<Node> conditionOpt = ParseExpression();
        if (!conditionOpt.isPresent()) {
            throwParseException("Expected a condition expression after 'while('");
        }
        Node condition = conditionOpt.get();

        // Expecting a close parenthesis after the condition
        if (!tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN).isPresent()) {
            throwParseException("Expected ')' after 'do-while' condition");
        }

        // Expecting a semicolon (or separator, based on your setup) after the condition
        if (!tokenManager.MatchAndRemove(TokenType.SEPARATOR).isPresent()) {
            throwParseException("Expected ';' after 'do-while' loop");
        }

        DoWhileNode doWhileNode = new DoWhileNode(block, condition);
        return Optional.of(doWhileNode);
    }


    public Optional<DeleteNode> ParseDelete() {
        Optional<Token> arrayNameToken = tokenManager.MatchAndRemove(TokenType.DELETE);
        if (!arrayNameToken.isPresent()) {
            throwParseException("Expected array name after 'delete'");
        }
        String arrayName = arrayNameToken.get().getValue();

        // Expecting an open bracket '[' after the array name
        if (!tokenManager.MatchAndRemove(TokenType.OPEN_SQUARE).isPresent()) {
            throwParseException("Expected '[' after array name");
        }

        // Parse the index expression inside the brackets
        Optional<Node> indexExpressionOpt = ParseExpression();
        if (!indexExpressionOpt.isPresent()) {
            throwParseException("Expected an index expression after array name and '['");
        }
        Node indexExpression = indexExpressionOpt.get();

        // Expecting a close bracket ']' after the index expression
        if (!tokenManager.MatchAndRemove(TokenType.CLOSE_SQUARE).isPresent()) {
            throwParseException("Expected ']' after index expression");
        }

        // Expecting a semicolon (or separator, based on your setup) after the closing bracket
        if (!tokenManager.MatchAndRemove(TokenType.SEPARATOR).isPresent()) {
            throwParseException("Expected ';' after 'delete' statement");
        }

        // Construct the DeleteNode with the parsed data and return it
        DeleteNode deleteNode = new DeleteNode(arrayName, indexExpression);
        return Optional.of(deleteNode);
    }


    public Optional<ReturnNode> ParseReturn() {
        // Already matched and removed 'RETURN' token at the start

        // Parse the expression that should follow the 'return' keyword
        Optional<Node> expressionOpt = ParseExpression();
        if (!expressionOpt.isPresent()) {
            throwParseException("Expected an expression after 'return'");
        }
        Node expression = expressionOpt.get();

        // Expecting a semicolon after the expression
        if (!tokenManager.MatchAndRemove(TokenType.SEPARATOR).isPresent()) {
            throwParseException("Expected ';' after 'return' expression");
        }
        ReturnNode returnNode = new ReturnNode(expression);
        return Optional.of(returnNode);
    }


    public Optional<FunctionCallNode> ParseFunctionCall() {
        Optional<Token> optionalFunctionNameToken = tokenManager.MatchAndRemove(TokenType.WORD);
        if (!optionalFunctionNameToken.isPresent()) {
            throwParseException("Expected function name");
        }
        Token functionNameToken = optionalFunctionNameToken.get();
        
        // Define the special functions that can be called without parentheses.
        Set<TokenType> specialFunctions = EnumSet.of(
            TokenType.GETLINE,
            TokenType.PRINT,
            TokenType.PRINTF,
            TokenType.EXIT,
            TokenType.NEXTFILE,
            TokenType.NEXT
        );

        List<Node> arguments = new ArrayList<>();

        if (specialFunctions.contains(functionNameToken.getType())) {
            // If it's a special function and doesn't have an open parenthesis following, treat as no-arg function call
            if (tokenManager.Peek(0).isEmpty() || tokenManager.Peek(0).get().getType() != TokenType.OPEN_PAREN) {
                return Optional.of(new FunctionCallNode(functionNameToken.getValue(), arguments));
            } else {
                // It's a special function with an open parenthesis, so parse the arguments.
                tokenManager.MatchAndRemove(TokenType.OPEN_PAREN); // Assuming the next token is OPEN_PAREN
            }
        } else {
            // It's a regular function, so expect an open parenthesis.
            if (!tokenManager.MatchAndRemove(TokenType.OPEN_PAREN).isPresent()) {
                throwParseException("Expected '(' after function name in function call");
            }
        }

        while (tokenManager.Peek(0).isPresent() && tokenManager.Peek(0).get().getType() != TokenType.CLOSE_PAREN) {
            Optional<Node> argument = ParseExpression();
            if (!argument.isPresent()) {
                throwParseException("Expected an expression as an argument in function call");
            }
            arguments.add(argument.get());

            if (tokenManager.Peek(0).isPresent() && tokenManager.Peek(0).get().getType() == TokenType.COMMA) {
                tokenManager.MatchAndRemove(TokenType.COMMA);
            }
        }

        if (!tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN).isPresent()) {
            throwParseException("Expected ')' to close the arguments list in function call");
        }

        return Optional.of(new FunctionCallNode(functionNameToken.getValue(), arguments));
    }

    
}
