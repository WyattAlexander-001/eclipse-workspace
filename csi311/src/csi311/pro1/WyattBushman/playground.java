package csi311.pro1.WyattBushman;

import java.util.LinkedList;
import java.util.Optional;

public class playground {
    public static void main(String[] args) {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(TokenType.NUMBER, "10", 1, 1));
        tokens.add(new Token(TokenType.ASSIGNMENT, "=", 1, 3));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 4));
        
        Parser parser = new Parser(tokens);
        
        try {
            Optional<Node> result = parser.ParseAssignment();
            System.out.println(result.isPresent() ? result.get().toString() : "No Node returned");
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
