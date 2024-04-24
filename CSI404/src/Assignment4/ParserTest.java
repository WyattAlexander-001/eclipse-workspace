package Assignment4;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserTest {
	
    private Parser parser;
    private TokenManager tokenManager;

    @BeforeEach
    void setUp() {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(TokenType.LOAD, "load", 1, 0));
        tokens.add(new Token(TokenType.NUMBER, "100", 1, 5));
        tokens.add(new Token(TokenType.REGISTER, "R1", 1, 9));
        tokenManager = new TokenManager(tokens);
        parser = new Parser(tokenManager);
    }

    @Test
    void testParseCode() {
        LinkedList<String> codeBlocks = parser.parseCode();
        assertNotNull(codeBlocks);
        assertFalse(codeBlocks.isEmpty());
        assertEquals("load 100 R1", codeBlocks.getFirst());
    }
    

    @Test
    void testComplexOperationParsing() {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(TokenType.LOAD, "load", 1, 0));
        tokens.add(new Token(TokenType.NUMBER, "100", 1, 5));
        tokens.add(new Token(TokenType.REGISTER, "R1", 1, 9));
        tokens.add(new Token(TokenType.NEWLINE, "\n", 1, 12));
        tokens.add(new Token(TokenType.STORE, "store", 2, 0));
        tokens.add(new Token(TokenType.REGISTER, "R1", 2, 6));
        tokens.add(new Token(TokenType.NUMBER, "200", 2, 9));
        tokenManager = new TokenManager(tokens);
        parser = new Parser(tokenManager);

        LinkedList<String> codeBlocks = parser.parseCode();
        assertEquals(2, codeBlocks.size());
        assertEquals("load 100 R1", codeBlocks.get(0));
        assertEquals("store R1 200", codeBlocks.get(1));
    }
    
    @Test
    void testParseImmediate() {
        LinkedList<Token> tokensForTest = new LinkedList<>();
        tokensForTest.add(new Token(TokenType.NUMBER, "100", 1, 0));
        tokensForTest.add(new Token(TokenType.REGISTER, "R1", 1, 4));
        tokensForTest.add(new Token(TokenType.NUMBER, "200", 1, 8));
        tokensForTest.add(new Token(TokenType.REGISTER, "R2", 1, 12));

        TokenManager localTokenManager = new TokenManager(tokensForTest);
        Parser localParser = new Parser(localTokenManager);

        String result = localParser.parseImmediate();
        assertEquals("100 R1 200 R2", result, "parseImmediate should correctly parse and space numbers and registers.");
    }
    
    @Test
    void testParseRegisters() {
        LinkedList<Token> registerTokens = new LinkedList<>();
        registerTokens.add(new Token(TokenType.REGISTER, "R1", 1, 0));
        registerTokens.add(new Token(TokenType.MATH, "add", 1, 3));
        registerTokens.add(new Token(TokenType.REGISTER, "R2", 1, 7));

        TokenManager localTokenManager = new TokenManager(registerTokens);
        Parser localParser = new Parser(localTokenManager);

        // Execute the method under test
        String result = localParser.parseRegisters();
        assertEquals("R1 add R2", result, "parseRegisters should correctly parse registers and functions.");
    }
    
    @Test
    void testParseFunctions() {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(TokenType.ADD, "add", 1, 0));  // Function token
        tokens.add(new Token(TokenType.REGISTER, "R1", 1, 4));  // Register token following a function
        tokens.add(new Token(TokenType.MATH, "multiply", 1, 8));  // Another function token
        tokens.add(new Token(TokenType.REGISTER, "R2", 1, 17));  // Another register token

        TokenManager localTokenManager = new TokenManager(tokens);
        Parser localParser = new Parser(localTokenManager);

        String result = localParser.parseFunctions();
        assertEquals("add R1 multiply R2", result, "parseFunctions should correctly parse and space functions and registers.");
    }
    
    @Test
    void testParseKeywords() {
        // Set up specific tokens for testing parseKeywords
        LinkedList<Token> keywordTokens = new LinkedList<>();
        keywordTokens.add(new Token(TokenType.LOAD, "load", 1, 0));
        keywordTokens.add(new Token(TokenType.HALT, "halt", 1, 5));
        keywordTokens.add(new Token(TokenType.RETURN, "return", 1, 10));
        keywordTokens.add(new Token(TokenType.STORE, "store", 1, 16));

        TokenManager localTokenManager = new TokenManager(keywordTokens);
        Parser localParser = new Parser(localTokenManager);

        String result = localParser.parseKeywords();
        assertEquals("load halt return store", result, "parseKeywords should correctly parse and space keywords.");
    }




    



}
