package Assignment4;

import java.util.LinkedList;
import java.util.Optional;

public class Parser {
    private TokenManager tokens;

    public Parser(TokenManager tokens) {
        this.tokens = tokens;
    }

    
    public LinkedList<String> parseCode() {
        LinkedList<String> codeBlocks = new LinkedList<>();
        while (tokens.MoreTokens()) { 
            System.out.println("Current token: " + tokens.Peek(0).map(Token::toString).orElse("No Token"));
            String operation = parseOperation();
            if (!operation.isEmpty()) { // Ensure only non-empty operations are added
                codeBlocks.add(operation);
            }
            System.out.println("Remaining Tokens: " + tokens.count()); 

        }
        return codeBlocks;
    }


    public String parseOperation() {
        StringBuilder operation = new StringBuilder();
        while (tokens.MoreTokens()) {
            Optional<Token> nextToken = tokens.Peek(0);
            if (!nextToken.isPresent()) {
                break;  // No more tokens to process
            }

            TokenType tokenType = nextToken.get().getType();
            if (tokenType == TokenType.NEWLINE) {
                tokens.MatchAndRemove(TokenType.NEWLINE);  // Consume the newline to move forward
                break;  // Stop processing this operation upon reaching a newline
            } else if (isKeyword(tokenType)) {
                operation.append(parseKeywords());  // This should end with a space if it adds any keywords
                if (!operation.toString().endsWith(" ")) {
                    operation.append(" ");  // Safeguard to ensure there's a space after keywords if not already present
                }
            } else {
                String immediate = parseImmediate();
                if (!immediate.isEmpty()) {
                    if (!operation.toString().endsWith(" ") && !immediate.startsWith(" ")) {
                        operation.append(" ");  // Ensure there's a space between keyword and immediate parts if needed
                    }
                    operation.append(immediate);
                }
            }
        }
        return operation.toString().trim();  // Final trim to clean up any trailing spaces
    }


    public String parseImmediate() {
        StringBuilder immediate = new StringBuilder();
        Optional<Token> nextToken = tokens.Peek(0);
        System.out.println("Initial token: " + (nextToken.isPresent() ? nextToken.get().getValue() : "None"));  // Debugging
        while (nextToken.isPresent() && isImmediateOrRegister(nextToken.get().getType())) {
            System.out.println("Processing token: " + nextToken.get().getValue());  // Debugging
        	if (immediate.length() > 0) immediate.append(" "); // Add space before each token after the first
            immediate.append(tokens.MatchAndRemove(nextToken.get().getType()).get().getValue());
            nextToken = tokens.Peek(0);
            System.out.println("Next token: " + (nextToken.isPresent() ? nextToken.get().getValue() : "None"));  // Debugging
        }
        System.out.println("Final immediate string: " + immediate.toString());  // Debugging
        return immediate.toString();
    }
    

    private boolean isImmediateOrRegister(TokenType type) {
        return type == TokenType.NUMBER || type == TokenType.REGISTER;
    }

    public String parseRegisters() {
        StringBuilder registers = new StringBuilder();
        Optional<Token> nextToken = tokens.Peek(0);
        while (nextToken.isPresent() && (nextToken.get().getType() == TokenType.REGISTER || isFunction(nextToken.get().getType()))) {
            if (registers.length() > 0) {
                registers.append(" ");  // Ensures space before adding new content if not the first entry.
            }
            
            if (nextToken.get().getType() == TokenType.REGISTER) {
                registers.append(tokens.MatchAndRemove(TokenType.REGISTER).get().getValue());
            } else { 
                registers.append(parseFunctions()); // No need to trim here; parseFunctions should handle formatting
            }
            nextToken = tokens.Peek(0); // Update nextToken for the loop condition
        }
        return registers.toString().trim(); // Final trim for the entire string
    }



    public String parseFunctions() {
        StringBuilder functions = new StringBuilder();
        Optional<Token> nextToken = tokens.Peek(0);
        if (nextToken.isPresent() && isFunction(nextToken.get().getType())) {
            functions.append(tokens.MatchAndRemove(nextToken.get().getType()).get().getValue());
            String additional = parseRegisters(); 
            if (!additional.isEmpty()) {
                functions.append(" ").append(additional);
            }
        }
        return functions.toString().trim();
    }

    private boolean isFunction(TokenType type) {
        return type == TokenType.MATH || type == TokenType.ADD || type == TokenType.SUBTRACT; // Add all function-related types
    }


    public String parseKeywords() {
        StringBuilder keywords = new StringBuilder();
        Optional<Token> nextToken = tokens.Peek(0);
        while (nextToken.isPresent() && isKeyword(nextToken.get().getType())) {
            if (keywords.length() > 0) keywords.append(" "); // Add space only if it's not the first keyword
            keywords.append(tokens.MatchAndRemove(nextToken.get().getType()).get().getValue());
            nextToken = tokens.Peek(0);
        }
        return keywords.toString().trim(); 
    }

    // Helper method to check if a token type is a keyword
    private boolean isKeyword(TokenType type) {
        switch (type) {
            case MATH:
            case ADD:
            case SUBTRACT:
            case MULTIPLY:
            case AND:
            case OR:
            case NOT:
            case XOR:
            case COPY:
            case HALT:
            case BRANCH:
            case JUMP:
            case CALL:
            case PUSH:
            case LOAD:
            case RETURN:
            case STORE:
            case PEEK:
            case POP:
            case INTERRUPT:
            case EQUAL:
            case UNEQUAL:
            case GREATER:
            case LESS:
            case GREATER_OR_EQUAL:
            case LESS_OR_EQUAL:
            case SHIFT:
            case LEFT:
            case RIGHT:
                return true;
            default:
                return false;
        }
    }
}
