package csi311.pro1.WyattBushman;

public class Token {
    private TokenType type;
    private String value;
    private int lineNumber;
    private int position;

	public Token() {

	}
	
    // Constructor for tokens that have a value like WORDS/NUMBERS
    public Token(TokenType type, String value, int lineNumber, int position) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
        this.position = position;
    }
	
    // Constructor for tokens that don't require a value like SEPARATOR/OPERATOR(S)
    public Token(TokenType type, int lineNumber, int position) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.position = position;
        this.value = null; // No value for this token
    }
    
    //May need to remove this for immutability
	public void setValue(String v) {
		this.value = v;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setType(TokenType t) {
		this.type = t;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int p) {
		this.position = p;
	}
	
	public String toString() {
        String result = "Token Type: " + getType();
        if (value != null && !value.isEmpty()) {
            result += " Value: " + getValue();
        }
        result += " || Line: " + getLineNumber() + " || Position: " + getPosition() + "\n";
        return result;
	}

}
