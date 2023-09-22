package csi311.pro1.WyattBushman;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;
    private LinkedList<Token> tokens;

    @Before
    public void setUp() {
        tokens = new LinkedList<>();
        parser = new Parser(tokens);
    }

    @Test
    public void testAcceptSeparatorsMultipleSeparators() {
        tokens.add(new Token(TokenType.SEPARATOR, 1, 1));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 1));
        assertTrue(parser.AcceptSeparators());
    }

    @Test
    public void testAcceptSeparatorsNoSeparators() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        assertFalse(parser.AcceptSeparators());
    }
    
    @Test
    public void testParseEmptyList() {
        assertNotNull(parser.Parse());
    }

    @Test(expected = RuntimeException.class)
    public void testParseWithUnmatchedTokens() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        parser.Parse();
    }

    @Test
    public void testAcceptSeparatorsWithMultipleSeparators() {
        tokens.add(new Token(TokenType.SEPARATOR, 1, 1));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 1));
        assertTrue(parser.AcceptSeparators());
        assertFalse(parser.getTokenManager().MoreTokens()); // Makes sure all separators are consumed
    }
    
    @Test
    public void testPeekBeyondTokens() {
        tokens.add(new Token(TokenType.WORD, "word", 1, 1));
        assertFalse(parser.getTokenManager().Peek(2).isPresent());
    }
    
    @Test(expected = RuntimeException.class)
    public void testUnexpectedToken() {
        tokens.add(new Token(TokenType.WORD, "word", 1, 1));
        parser.Parse();
    }



}
