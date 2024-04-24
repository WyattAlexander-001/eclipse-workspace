package Assignment4;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;


class LexerTest {
	Lexer lexer;

	@BeforeEach
	void setUp() throws Exception {
		lexer = new Lexer("");
	}

	@Test
	void testKeywordRecognition() {
	    String input = "math add";
	    lexer = new Lexer(input);
	    try {
	        lexer.lex();
	        LinkedList<Token> tokens = lexer.getTokens();
	        assertEquals(TokenType.MATH, tokens.get(0).getType());
	        assertEquals(TokenType.ADD, tokens.get(1).getType());
	    } catch (Exception e) {
	        fail("Exception thrown during lexing: " + e.getMessage());
	    }
	}
	
	@Test
	void testNumberRecognition() {
	    String input = "load 100 R1";
	    lexer = new Lexer(input);
	    try {
	        lexer.lex();
	        LinkedList<Token> tokens = lexer.getTokens();
	        assertEquals(TokenType.LOAD, tokens.get(0).getType());
	        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
	        assertEquals("100", tokens.get(1).getValue());
	    } catch (Exception e) {
	        fail("Exception thrown during lexing: " + e.getMessage());
	    }
	}
	
	@Test
	void testNewlineHandling() {
	    String input = "math\nadd";
	    lexer = new Lexer(input);
	    try {
	        lexer.lex();
	        LinkedList<Token> tokens = lexer.getTokens();
	        assertEquals(TokenType.MATH, tokens.get(0).getType());
	        assertEquals(TokenType.NEWLINE, tokens.get(1).getType());
	        assertEquals(TokenType.ADD, tokens.get(2).getType());
	    } catch (Exception e) {
	        fail("Exception thrown during lexing: " + e.getMessage());
	    }
	}
	
	@Test
	void testUnrecognizedWord() {
	    String input = "unknownCommand";
	    lexer = new Lexer(input);
	    Exception exception = assertThrows(Exception.class, lexer::lex);
	    assertTrue(exception.getMessage().contains("Unrecognized keyword or identifier"));
	}
	
	@Test
	void testProcessNumber() {
	    String input = "123 45.67";
	    lexer = new Lexer(input);
	    try {
	        lexer.lex();
	        LinkedList<Token> tokens = lexer.getTokens();
	        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
	        assertEquals("123", tokens.get(0).getValue());
	        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
	        assertEquals("45.67", tokens.get(1).getValue());
	    } catch (Exception e) {
	        fail("Exception thrown during lexing numbers: " + e.getMessage());
	    }
	}
	
	@Test
	void testProcessSymbol() {
	    String input = "add less greater";
	    lexer = new Lexer(input);
	    try {
	        lexer.lex();
	        LinkedList<Token> tokens = lexer.getTokens();
	        assertEquals(TokenType.ADD, tokens.get(0).getType()); 
	        assertEquals(TokenType.LESS, tokens.get(1).getType());  
	        assertEquals(TokenType.GREATER, tokens.get(2).getType());  
	    } catch (Exception e) {
	        fail("Exception thrown during lexing symbols: " + e.getMessage());
	    }
	}

	
	@Test
	void testProcessWord() {
	    String input = "math R1 unknownCommand";
	    lexer = new Lexer(input);
	    try {
	        lexer.lex();
	        LinkedList<Token> tokens = lexer.getTokens();
	        assertEquals(TokenType.MATH, tokens.get(0).getType());
	        assertEquals(TokenType.REGISTER, tokens.get(1).getType());
	        assertEquals("R1", tokens.get(1).getValue());
	        fail("Lexer should throw an exception for unrecognized commands");
	    } catch (Exception e) {
	        assertTrue(e.getMessage().contains("Unrecognized keyword or identifier"), "Expected exception for unrecognized words");
	    }
	}


	
	
	






}
