package csi311.pro1.WyattBushman;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class LexerTest {

    @Test
    public void testNumbers() throws Exception {
        String input = "5.23 8.5 3 5.5";
        Lexer lexer = new Lexer(new StringHandler(input));
        lexer.Lex();
        List<Token> tokens = lexer.getTokens();
        
        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("5.23", tokens.get(0).getValue());
        
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals("8.5", tokens.get(1).getValue());
        
        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals("3", tokens.get(2).getValue());
        
        assertEquals(TokenType.NUMBER, tokens.get(3).getType());
        assertEquals("5.5", tokens.get(3).getValue());
    }
    
    //Testing the Lexer's behavior using JUnit's built-in exception handling mechanism.
    @Test
    public void shouldThrowExceptionForInvalidNumber() {
        String input = "5..23";
        StringHandler stringHandler = new StringHandler(input);
        Lexer lexer = new Lexer(stringHandler);
        try {
            lexer.Lex();
            Token token = lexer.getTokens().get(0);
            assertFalse(token.getType() == TokenType.NUMBER); //Asserts that the token type is not a number
            System.out.println("FAKE PASS!");
            fail("Expected an Exception due to invalid number format.");
        } catch (Exception e) {
        	System.out.println("EXPECTED FAIL!");
        	assertEquals("Unexpected character: .", e.getMessage()); 
        }
    }
    
    
    //Test to ensure Lexer throws an exception for invalid number format (e.g., "5..23").
    @Test(expected = Throwable.class)
    public void testIncorrectNumbers() {
        String input = "5..23";
        StringHandler stringHandler = new StringHandler(input);
        Lexer lexer = new Lexer(stringHandler);
        lexer.Lex(); //Purposely did not use try/catch to avoid JUnit's built in exception handling
    }


    @Test
    public void testWords() throws Exception {
        String input = "Anni Wyatt Wyatt123";
        Lexer lexer = new Lexer(new StringHandler(input));
        lexer.Lex();
        List<Token> tokens = lexer.getTokens();
        
        assertEquals(TokenType.WORD, tokens.get(0).getType());
        assertEquals("Anni", tokens.get(0).getValue());
        
        assertEquals(TokenType.WORD, tokens.get(1).getType());
        assertEquals("Wyatt", tokens.get(1).getValue());
        
        assertEquals(TokenType.WORD, tokens.get(2).getType());
        assertEquals("Wyatt123", tokens.get(2).getValue());
    }

    @Test
    public void testSeparators() throws Exception {
        String input = "\n";
        Lexer lexer = new Lexer(new StringHandler(input));
        lexer.Lex();
        List<Token> tokens = lexer.getTokens();
        
        assertEquals(TokenType.SEPARATOR, tokens.get(0).getType());
    }

}
