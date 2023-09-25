package csi311.pro1.WyattBushman;

import java.util.LinkedList;
import java.util.Optional;

public class TokenManager {
    private LinkedList<Token> tokens;

    public TokenManager(LinkedList<Token> tokens) {
        this.tokens = tokens;
    }

    public Optional<Token> Peek(int j) {
        if (j >= 0 && j < tokens.size()) {
            return Optional.of(tokens.get(j));
        }
        return Optional.empty();
    }

    public boolean MoreTokens() {
        return !tokens.isEmpty();
    }

    public Optional<Token> MatchAndRemove(TokenType t) {
        if (t == null) {
            throw new IllegalArgumentException("TokenType cannot be null");
        }
        if (!tokens.isEmpty() && tokens.getFirst().getType() == t) {
            return Optional.of(tokens.removeFirst());
        }
        return Optional.empty();
    }
}
