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
    
    @Test(expected = Throwable.class)
    public void testIncorrectChar() {
        String input = "#";
        StringHandler stringHandler = new StringHandler(input);
        Lexer lexer = new Lexer(stringHandler);
        lexer.Lex(); 
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
    
   
    @Test
    public void testProcessWord() {
        StringHandler handler = new StringHandler("hello", false);
        Lexer lexer = new Lexer(handler);
        Token result = lexer.processWord();
        assertEquals(TokenType.WORD, result.getType());
        assertEquals("hello", result.getValue());
    }
    
    @Test
    public void testProcessWordWithSpaces() {
        StringHandler handler = new StringHandler("hello Wyatt t h e r e are alot of Sp ac es", false);
        Lexer lexer = new Lexer(handler);
        Token result = lexer.processWord();
        assertEquals(TokenType.WORD, result.getType());
        assertEquals("hello", result.getValue());
    }

    @Test
    public void testProcessWordWithNumbers() {
        StringHandler handler = new StringHandler("hello123", false);
        Lexer lexer = new Lexer(handler);
        Token result = lexer.processWord();
        assertEquals(TokenType.WORD, result.getType());
        assertEquals("hello123", result.getValue());
    }

    @Test
    public void testProcessNumberWithDecimal() {
        StringHandler handler = new StringHandler("123.45", false);
        Lexer lexer = new Lexer(handler);
        Token result = lexer.processNumber();
        assertEquals(TokenType.NUMBER, result.getType());
        assertEquals("123.45", result.getValue());
    }

    @Test
    public void testProcessNumberNoDecimal() {
        StringHandler handler = new StringHandler("123", false);
        Lexer lexer = new Lexer(handler);
        Token result = lexer.processNumber();
        assertEquals(TokenType.NUMBER, result.getType());
        assertEquals("123", result.getValue());
    }

    @Test
    public void testProcessNumberMultipleDecimals() {
        StringHandler handler = new StringHandler("123.45.67", false);
        Lexer lexer = new Lexer(handler);
        Token result = lexer.processNumber();
        assertEquals(TokenType.NUMBER, result.getType());
        assertEquals("123.45", result.getValue());  // Assuming the lexer stops at the second decimal point
    }
    
    @Test
    public void testRemainderAtStart() {
        StringHandler handler = new StringHandler("Hello", false);
        assertEquals("Hello", handler.remainder());
    }

    @Test
    public void testRemainderInMiddle() {
        StringHandler handler = new StringHandler("Hello", false);
        handler.swallow(2);  // Move index to 2
        assertEquals("llo", handler.remainder());
    }

    @Test
    public void testRemainderAtEnd() {
        StringHandler handler = new StringHandler("Hello", false);
        handler.swallow(10);  // Move index to 10
        assertEquals("", handler.remainder());
    }
    
    @Test
    public void testGetLength() {
        StringHandler handler = new StringHandler("Hello", false);
        assertEquals(5, handler.getLength());
    }
    
    @Test
    public void testGetIndex() {
        StringHandler handler = new StringHandler("Hello", false);
        assertEquals(0, handler.getIndex());
        
        handler.swallow(2);  // Move index to 2
        assertEquals(2, handler.getIndex());
        
        handler.swallow(3);  // Move index to 5
        assertEquals(5, handler.getIndex());
    }

    
    

}
