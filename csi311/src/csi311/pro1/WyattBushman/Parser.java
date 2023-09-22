package csi311.pro1.WyattBushman;

import java.util.LinkedList;

public class Parser {
    private TokenManager tokenManager;

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
            if (!ParseFunction(program) && !ParseAction(program)) {
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

            // Parsing parameters
            LinkedList<String> parameters = new LinkedList<>();
            tokenManager.MatchAndRemove(TokenType.OPEN_PAREN);
            while (tokenManager.Peek(0).get().getType() != TokenType.CLOSE_PAREN) {
                Token paramToken = tokenManager.MatchAndRemove(TokenType.WORD).orElseThrow(() -> new RuntimeException("Expected parameter name"));
                parameters.add(paramToken.getValue());
                
                if (tokenManager.Peek(0).get().getType() == TokenType.COMMA) {
                    tokenManager.MatchAndRemove(TokenType.COMMA);
                }
            }
            tokenManager.MatchAndRemove(TokenType.CLOSE_PAREN);

            FunctionDefinitionNode functionNode = new FunctionDefinitionNode(functionName);
            functionNode.getParameters().addAll(parameters);

            BlockNode blockNode = ParseBlock();
            functionNode.getStatements().addAll(blockNode.getStatements());
            
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
                return true;
            } else if (type == TokenType.END) {
                tokenManager.MatchAndRemove(TokenType.END);
                BlockNode block = ParseBlock();
                program.addEndBlock(block);    
                return true;
            } else {
                // This is either a condition or a simple block
                // As the condition parsing isn't handled now, we'll just parse the block
                BlockNode block = ParseBlock();
                program.addBlock(block); 
                return true;
            }
        }
        return false;
    } 
    
    public BlockNode ParseBlock() {
    	BlockNode blockNode = new BlockNode();
    	return blockNode;
    }
    
    public TokenManager getTokenManager() {
        return tokenManager;
    }

}
