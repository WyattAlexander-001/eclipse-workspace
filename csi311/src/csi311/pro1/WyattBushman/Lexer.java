package csi311.pro1.WyattBushman;

import java.util.LinkedList;

public class Lexer {

    private StringHandler stringHandler;
    private int lineNumber = 1; //We count lines starting at 1
    private int position = 0; //Beginning of line
    private LinkedList<Token> tokens = new LinkedList<>();

    public Lexer(StringHandler stringHandler) {
        this.stringHandler = stringHandler;
    }
    
    public Lexer(String input) {
        this.stringHandler = new StringHandler(input);
    }
    
    public void Lex() throws Exception {
        while (!stringHandler.isDone()) {
            char currentChar = stringHandler.peek(0);
            switch (currentChar) {
                case ' ':
                case '\t':
                    stringHandler.swallow(1); //Skipping spaces/tabs 
                    position++;
                    break;
                case '\n':
                    tokens.add(new Token(TokenType.SEPARATOR, lineNumber, position));
                    lineNumber++;
                    position = 0;
                    stringHandler.swallow(1); // Move past the newline
                    break;
                case '\r':
                    stringHandler.swallow(1); // Ignore it
                    break;
                default:
                    if (Character.isLetter(currentChar)) {
                        Token wordToken = processWord();
                        tokens.add(wordToken);
                    } else if (Character.isDigit(currentChar)) {
                        Token numberToken = processNumber();
                        tokens.add(numberToken);
                    } else {
                        throw new Exception("Unexpected character: " + currentChar);
                    }
                    break;
            }
        }
    }
    
    public Token processWord() {
        StringBuilder wordBuilder = new StringBuilder();
        while (!stringHandler.isDone() && (Character.isLetter(stringHandler.peek(0)) || Character.isDigit(stringHandler.peek(0)) || stringHandler.peek(0) == '_')) {
            wordBuilder.append(stringHandler.getChar());
            position++;
        }
        return new Token(TokenType.WORD, wordBuilder.toString(), lineNumber, position - wordBuilder.length());
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

    public LinkedList<Token> getTokens() {
        return tokens;
    }


}
