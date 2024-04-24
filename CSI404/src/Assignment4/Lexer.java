package Assignment4;

import java.util.HashMap;
import java.util.LinkedList;

public class Lexer {

    private StringHandler stringHandler;
    private int lineNumber = 1; //We count lines starting at 1
    private int position = 0; //Beginning of line
    private LinkedList<Token> tokens = new LinkedList<>();
    private final HashMap<String, TokenType> keywords;
    private HashMap<String, TokenType> twoCharSymbols;
    private HashMap<String, TokenType> oneCharSymbols;


    public Lexer(StringHandler stringHandler) {
        this.stringHandler = stringHandler;
        this.keywords = new HashMap<>();
        this.twoCharSymbols = new HashMap<>();
        this.oneCharSymbols = new HashMap<>();
        populateKeywords();
    }
    
    public Lexer(String input) {
        this.stringHandler = new StringHandler(input);
        this.keywords = new HashMap<>();
        this.twoCharSymbols = new HashMap<>();
        this.oneCharSymbols = new HashMap<>();
        populateKeywords();
    }
    
    private void populateKeywords() {
        // Assembly operation codes and keywords
        keywords.put("math", TokenType.MATH);
        keywords.put("add", TokenType.ADD);
        keywords.put("subtract", TokenType.SUBTRACT);
        keywords.put("multiply", TokenType.MULTIPLY);
        keywords.put("and", TokenType.AND);
        keywords.put("or", TokenType.OR);
        keywords.put("not", TokenType.NOT);
        keywords.put("xor", TokenType.XOR);
        keywords.put("copy", TokenType.COPY);
        keywords.put("halt", TokenType.HALT);
        keywords.put("branch", TokenType.BRANCH);
        keywords.put("jump", TokenType.JUMP);
        keywords.put("call", TokenType.CALL);
        keywords.put("push", TokenType.PUSH);
        keywords.put("load", TokenType.LOAD);
        keywords.put("return", TokenType.RETURN);
        keywords.put("store", TokenType.STORE);
        keywords.put("peek", TokenType.PEEK);
        keywords.put("pop", TokenType.POP);
        keywords.put("interrupt", TokenType.INTERRUPT);
        keywords.put("equal", TokenType.EQUAL);
        keywords.put("unequal", TokenType.UNEQUAL);
        keywords.put("greater", TokenType.GREATER);
        keywords.put("less", TokenType.LESS);
        keywords.put("greaterOrEqual", TokenType.GREATER_OR_EQUAL);
        keywords.put("lessOrEqual", TokenType.LESS_OR_EQUAL);
        keywords.put("shift", TokenType.SHIFT);
        keywords.put("left", TokenType.LEFT);
        keywords.put("right", TokenType.RIGHT);
    }


    
    public void lex() throws Exception {
        while (!stringHandler.isDone()) {
            char currentChar = stringHandler.peek(0);
            switch (currentChar) {
                case ' ':
                case '\t':
                    stringHandler.swallow(1); // Skipping spaces/tabs 
                    position++;
                    break;
                case '\n':
                    tokens.add(new Token(TokenType.NEWLINE, lineNumber, position)); // Updated to NEWLINE
                    lineNumber++;
                    position = 0;
                    stringHandler.swallow(1); // Move past the newline
                    break;
                case '\r':
                    stringHandler.swallow(1); // Ignore carriage returns
                    break;
                // Removed case for ';'
                default:
                    if (Character.isLetter(currentChar)) {
                        Token wordToken = processWord();
                        tokens.add(wordToken);
                    } else if (Character.isDigit(currentChar)) {
                        Token numberToken = processNumber();
                        tokens.add(numberToken);
                    } else {
                        Token symbolToken = processSymbol(lineNumber, position);
                        if (symbolToken != null) {
                            tokens.add(symbolToken);
                            position = symbolToken.getPosition();
                        } else {
                            throw new Exception("Unexpected character: " + currentChar);
                        }
                    }
                    break;
            }
        }
    }


    
    public LinkedList<Token> getTokens() {
        return tokens;
    }
    
    public Token processWord() throws Exception {
        StringBuilder wordBuilder = new StringBuilder();
        while (!stringHandler.isDone() && (Character.isLetter(stringHandler.peek(0)) || Character.isDigit(stringHandler.peek(0)) || stringHandler.peek(0) == '_')) {
            wordBuilder.append(stringHandler.getChar());
            position++;
        }
        String word = wordBuilder.toString().toLowerCase(); // Assuming case insensitivity for simplicity.
        
        // Check if the word is a keyword
        if (keywords.containsKey(word)) {
            return new Token(keywords.get(word), word, lineNumber, position - word.length());
        } 
        // Check if the word matches the pattern of a register identifier
        else if (word.matches("r\\d+")) { 
            return new Token(TokenType.REGISTER, word.toUpperCase(), lineNumber, position - word.length()); 
        } 
        else {
            throw new Exception("Unrecognized keyword or identifier: " + word + " at line " + lineNumber);
        }
    }



    public Token processNumber() {
        StringBuilder numberBuilder = new StringBuilder();
        boolean hasDot = false;
        while (!stringHandler.isDone() && (Character.isDigit(stringHandler.peek(0)) || (stringHandler.peek(0) == '.' && !hasDot))) {
            if (stringHandler.peek(0) == '.') hasDot = true;
            numberBuilder.append(stringHandler.getChar());
            position++;
        }
        return new Token(TokenType.NUMBER, numberBuilder.toString(), lineNumber, position - numberBuilder.length());
    }
    
    public Token processSymbol(int lineNumber, int position) {
        String twoCharSymbol = stringHandler.peekString(2);
        TokenType twoCharTokenType = twoCharSymbols.get(twoCharSymbol);
        
        if (twoCharTokenType != null) {
            stringHandler.swallow(2);
            return new Token(twoCharTokenType, lineNumber, position += 2);
        }

        String oneCharSymbol = stringHandler.peekString(1);
        TokenType oneCharTokenType = oneCharSymbols.get(oneCharSymbol);
        
        if (oneCharTokenType != null) {
            stringHandler.swallow(1);
            return new Token(oneCharTokenType, lineNumber, position += 1);
        }
        
        return null;
    }
 

}
