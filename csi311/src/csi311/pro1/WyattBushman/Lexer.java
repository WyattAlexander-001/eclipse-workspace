package csi311.pro1.WyattBushman;

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
        populateSymbols();
    }
    
    public Lexer(String input) {
        this.stringHandler = new StringHandler(input);
        this.keywords = new HashMap<>();
        this.twoCharSymbols = new HashMap<>();
        this.oneCharSymbols = new HashMap<>();
        populateKeywords();
        populateSymbols();
    }
    
    private void populateSymbols() {
        twoCharSymbols.put(">=", TokenType.GT_EQ);
        twoCharSymbols.put("++", TokenType.INC);
        twoCharSymbols.put("--", TokenType.DEC);
        twoCharSymbols.put("<=", TokenType.LT_EQ);
        twoCharSymbols.put("==", TokenType.EQ_EQ);
        twoCharSymbols.put("!=", TokenType.NOT_EQ);
        twoCharSymbols.put("^=", TokenType.POW);
        twoCharSymbols.put("%=", TokenType.MOD_ASSIGN);
        twoCharSymbols.put("*=", TokenType.MUL_ASSIGN);
        twoCharSymbols.put("/=", TokenType.DIV_ASSIGN);
        twoCharSymbols.put("+=", TokenType.PLUS_ASSIGN);
        twoCharSymbols.put("-=", TokenType.MINUS_ASSIGN);
        twoCharSymbols.put("!~", TokenType.NOT_MATCH);
        twoCharSymbols.put("&&", TokenType.AND);
        twoCharSymbols.put(">>", TokenType.APPEND);
        twoCharSymbols.put("||", TokenType.OR);

        oneCharSymbols.put("=",  TokenType.ASSIGNMENT);
        oneCharSymbols.put("!",  TokenType.NOT);
        oneCharSymbols.put("<",  TokenType.LT);
        oneCharSymbols.put(">",  TokenType.GT);
        oneCharSymbols.put("{",  TokenType.OPEN_CURLY);
        oneCharSymbols.put("}",  TokenType.CLOSE_CURLY);
        oneCharSymbols.put("[",  TokenType.OPEN_SQUARE);
        oneCharSymbols.put("]",  TokenType.CLOSE_SQUARE);
        oneCharSymbols.put("(",  TokenType.OPEN_PAREN);
        oneCharSymbols.put(")",  TokenType.CLOSE_PAREN);
        oneCharSymbols.put("$",  TokenType.DOLLAR);
        oneCharSymbols.put("~",  TokenType.MATCH);
        oneCharSymbols.put("^",  TokenType.EXPONENT);
        oneCharSymbols.put("+",  TokenType.PLUS);
        oneCharSymbols.put("-",  TokenType.MINUS);
        oneCharSymbols.put("?",  TokenType.QUESTION);
        oneCharSymbols.put(":",  TokenType.COLON);
        oneCharSymbols.put("*",  TokenType.ASTERISK);
        oneCharSymbols.put("/",  TokenType.DIVIDE);
        oneCharSymbols.put("%",  TokenType.MODULO);
        oneCharSymbols.put("|",  TokenType.PIPE);
        oneCharSymbols.put(",",  TokenType.COMMA);
        oneCharSymbols.put(";",  TokenType.SEPARATOR); // AWK allows semi-colons, mapped to SEPARATOR
        oneCharSymbols.put("\n", TokenType.SEPARATOR); // Newline is also a SEPARATOR in AWK
    }


	private void populateKeywords() {
        keywords.put("while", TokenType.WHILE);
        keywords.put("if", TokenType.IF);
        keywords.put("do", TokenType.DO);
        keywords.put("for", TokenType.FOR);
        keywords.put("break", TokenType.BREAK);
        keywords.put("continue", TokenType.CONTINUE);
        keywords.put("else", TokenType.ELSE);
        keywords.put("return", TokenType.RETURN);
        keywords.put("BEGIN", TokenType.BEGIN);
        keywords.put("END", TokenType.END);
        keywords.put("print", TokenType.PRINT);
        keywords.put("printf", TokenType.PRINTF);
        keywords.put("next", TokenType.NEXT);
        keywords.put("in", TokenType.IN);
        keywords.put("delete", TokenType.DELETE);
        keywords.put("getline", TokenType.GETLINE);
        keywords.put("exit", TokenType.EXIT);
        keywords.put("nextfile", TokenType.NEXTFILE);
        keywords.put("function", TokenType.FUNCTION);
        //keywords.put("true", TokenType.TRUE);
        //keywords.put("false", TokenType.FALSE);
    }

    
	public void lex() throws Exception {
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
	            case '#':
	                while (stringHandler.peek(0) != '\n' && !stringHandler.isDone()) {
	                    stringHandler.swallow(1);
	                }
	                if (stringHandler.peek(0) == '\n') { // If it's a newline, swallow that too
	                    stringHandler.swallow(1);
	                    lineNumber++;
	                    position = 0;
	                }
	                break;
	            case ';':
	                tokens.add(new Token(TokenType.SEPARATOR, ";", lineNumber, position));
	                stringHandler.swallow(1); // Move past the semicolon
	                position++;
	                break;
	            case '"':
	                Token stringToken = handleStringLiteral();
	                tokens.add(stringToken);
	                break;
	            case '`':
	                Token patternToken = handlePattern();
	                tokens.add(patternToken);
	                break;
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
	                        break;
	                    }
	                    throw new Exception("Unexpected character: " + currentChar);
	                }
	                break;
	        }
	    }
	}

    
    public LinkedList<Token> getTokens() {
        return tokens;
    }
    
    public Token processWord() {
        StringBuilder wordBuilder = new StringBuilder();
        while (!stringHandler.isDone() && (Character.isLetter(stringHandler.peek(0)) || Character.isDigit(stringHandler.peek(0)) || stringHandler.peek(0) == '_')) {
            wordBuilder.append(stringHandler.getChar());
            position++;
        }
        String word = wordBuilder.toString();
        // Check if the word is a keyword
        if (keywords.containsKey(word)) {
            return new Token(keywords.get(word), lineNumber, position - word.length());
        }
        return new Token(TokenType.WORD, word, lineNumber, position - word.length());
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
 
    public Token handleStringLiteral() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        int startPos = position; 
        stringHandler.swallow(1);
        position++;
        
        while (true) {
            char c = stringHandler.peek(0);
            if (c == '\\') {
                stringHandler.swallow(1); 
                position++; 
                c = stringHandler.peek(0);
                if (c == 'n') {
                    c = '\n';
                } else if (c == 't') {
                    c = '\t';
                } else if (c == '"') {
                    c = '"';
                }
            } else if (c == '"') {
                stringHandler.swallow(1);
                position++;
                break;
            } else if (stringHandler.isDone() || c == '\n') {
                throw new Exception("Unclosed string literal at line " + lineNumber);
            }            
            stringBuilder.append(c);
            stringHandler.swallow(1);
            position++;
        }  
        return new Token(TokenType.STRINGLITERAL, stringBuilder.toString(), lineNumber, startPos);
    }


    
    public Token handlePattern() {
        StringBuilder stringBuilder = new StringBuilder();
        int startPos = position;
        stringHandler.swallow(1);
        position++; 
        
        while (true) {
            char c = stringHandler.peek(0);
            
            if (c == '\\') { // To handle escaped backticks
                stringHandler.swallow(1); // Swallow the backslash
                position++;
                c = stringHandler.peek(0); 
            } else if (c == '`') {
                stringHandler.swallow(1); // Swallow the closing backtick
                position++; 
                break;
            } else if (stringHandler.isDone() || c == '\n') {
                break;
            }
            
            stringBuilder.append(c);
            stringHandler.swallow(1);
            position++; 
        }
        return new Token(TokenType.PATTERN, stringBuilder.toString(), lineNumber, startPos);
    }
}
