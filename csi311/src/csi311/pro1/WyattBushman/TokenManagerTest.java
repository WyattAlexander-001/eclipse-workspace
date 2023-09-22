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
}
