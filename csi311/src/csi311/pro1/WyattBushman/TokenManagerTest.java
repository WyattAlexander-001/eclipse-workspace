package csi311.pro1.WyattBushman;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class TokenManagerTest {

    private TokenManager tokenManager;
    private LinkedList<Token> tokens;

    @Before
    public void setUp() {
        tokens = new LinkedList<>();
        tokenManager = new TokenManager(tokens);
    }

    @Test
    public void testPeekWithinBounds() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        assertEquals(TokenType.WORD, tokenManager.Peek(0).get().getType());
    }

    @Test
    public void testPeekOutOfBounds() {
        assertFalse(tokenManager.Peek(1).isPresent());
    }

    @Test
    public void testMoreTokensTrue() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        assertTrue(tokenManager.MoreTokens());
    }

    @Test
    public void testMoreTokensFalse() {
        assertFalse(tokenManager.MoreTokens());
    }

    @Test
    public void testMatchAndRemoveMatch() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        assertEquals(TokenType.WORD, tokenManager.MatchAndRemove(TokenType.WORD).get().getType());
        assertFalse(tokenManager.MoreTokens());
    }

    @Test
    public void testMatchAndRemoveNoMatch() {
        tokens.add(new Token(TokenType.NUMBER, "123", 1, 1));
        assertFalse(tokenManager.MatchAndRemove(TokenType.WORD).isPresent());
        assertTrue(tokenManager.MoreTokens());
    }
    
    @Test
    public void testPeekEmptyList() {
        assertFalse(tokenManager.Peek(0).isPresent());
    }

    @Test
    public void testMatchAndRemoveEmptyList() {
        assertFalse(tokenManager.MatchAndRemove(TokenType.WORD).isPresent());
    }
    
    @Test
    public void testConsecutiveSimilarTokens() {
        tokens.add(new Token(TokenType.WORD, "test1", 1, 1));
        tokens.add(new Token(TokenType.WORD, "test2", 1, 2));
        assertEquals("test1", tokenManager.MatchAndRemove(TokenType.WORD).get().getValue());
        assertTrue(tokenManager.MoreTokens());
        assertEquals("test2", tokenManager.MatchAndRemove(TokenType.WORD).get().getValue());
        assertFalse(tokenManager.MoreTokens());
    }
    
    @Test
    public void testDifferentTokenTypes() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "123", 1, 2));
        assertEquals(TokenType.WORD, tokenManager.MatchAndRemove(TokenType.WORD).get().getType());
        assertEquals(TokenType.NUMBER, tokenManager.MatchAndRemove(TokenType.NUMBER).get().getType());
        assertFalse(tokenManager.MoreTokens());
    }
    
    @Test
    public void testPeekAtDifferentIndices() {
        tokens.add(new Token(TokenType.WORD, "test1", 1, 1));
        tokens.add(new Token(TokenType.WORD, "test2", 1, 2));
        assertEquals("test1", tokenManager.Peek(0).get().getValue());
        assertEquals("test2", tokenManager.Peek(1).get().getValue());
    }
    
    @Test
    public void testRemoveFromMiddle() {
        tokens.add(new Token(TokenType.WORD, "test1", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "123", 1, 2));
        tokens.add(new Token(TokenType.WORD, "test2", 1, 3));
        
        assertEquals(TokenType.WORD, tokenManager.MatchAndRemove(TokenType.WORD).get().getType());
        assertEquals(TokenType.NUMBER, tokenManager.MatchAndRemove(TokenType.NUMBER).get().getType());
        assertEquals("test2", tokenManager.Peek(0).get().getValue());
    }
    
    @Test
    public void testPeekAtLastPosition() {
        tokens.add(new Token(TokenType.WORD, "test1", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "123", 1, 2));
        tokens.add(new Token(TokenType.WORD, "test2", 1, 3));
        assertEquals("test2", tokenManager.Peek(2).get().getValue());
    }

    @Test
    public void testRemoveLastToken() {
        tokens.add(new Token(TokenType.WORD, "test1", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "123", 1, 2));
        tokens.add(new Token(TokenType.WORD, "test2", 1, 3));
        tokenManager.MatchAndRemove(TokenType.WORD); // Removes "test1"
        tokenManager.MatchAndRemove(TokenType.NUMBER); // Removes "123"
        assertEquals(TokenType.WORD, tokenManager.MatchAndRemove(TokenType.WORD).get().getType());
        assertFalse(tokenManager.MoreTokens());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMatchAndRemoveNullToken() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        tokenManager.MatchAndRemove(null);
    }

    @Test
    public void testPeekWithNegativeIndex() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        assertFalse(tokenManager.Peek(-1).isPresent());
    }






}
