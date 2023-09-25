package csi311.pro1.WyattBushman;

import java.util.LinkedList;

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
            if (!ParseFunction(program) && !ParseAction(program)) {
                System.out.println("Failed at token: " + tokenManager.Peek(0).get().getValue());
                throw new RuntimeException("Unexpected token found.");
            }
        }
        return program; // Creates our root node
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
                BlockNode block = ParseBlock();
                program.addBeginBlock(block);   
                AcceptSeparators();
                return true;
            } else if (type == TokenType.END) {
                tokenManager.MatchAndRemove(TokenType.END);
                BlockNode block = ParseBlock();
                program.addEndBlock(block); 
                AcceptSeparators();
                return true;
            } else {
                // Todo: Handle condition parsing if necessary.
                BlockNode block = ParseBlock();
                program.addBlock(block); 
                return true;
            }
        }
        return false;
    }

    
    public BlockNode ParseBlock() {
        BlockNode blockNode = new BlockNode();

        // Expect an opening brace
        if(!tokenManager.MatchAndRemove(TokenType.OPEN_CURLY).isPresent()) {
            throw new RuntimeException("Expected opening curly brace");
        }

        while (tokenManager.MoreTokens() && tokenManager.Peek(0).get().getType() != TokenType.CLOSE_CURLY) {
            Token currentToken = tokenManager.Peek(0).get();
            if (currentToken.getType() == TokenType.WORD) {
                tokenManager.MatchAndRemove(TokenType.WORD);
            } else {
                throw new RuntimeException("Unexpected token inside block: " + currentToken.getValue());
            }
        }

        // Expect a closing brace
        if(!tokenManager.MatchAndRemove(TokenType.CLOSE_CURLY).isPresent()) {
            throw new RuntimeException("Expected closing curly brace");
        }

        return blockNode;
    }



    
    public TokenManager getTokenManager() {
        return tokenManager;
    }

}
