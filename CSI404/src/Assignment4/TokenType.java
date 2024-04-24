package Assignment4;

public enum TokenType {
    // Assembly operation codes and keywords
    MATH, 
    ADD, 
    SUBTRACT, 
    MULTIPLY, 
    AND, OR, NOT, XOR, 
    COPY, 
    HALT, 
    BRANCH, 
    JUMP, 
    CALL, 
    PUSH, 
    LOAD, 
    RETURN, 
    STORE, 
    PEEK, 
    POP, 
    INTERRUPT, 
    EQUAL, 
    UNEQUAL, 
    GREATER, 
    LESS, 
    GREATER_OR_EQUAL, 
    LESS_OR_EQUAL, 
    SHIFT, 
    LEFT, 
    RIGHT,
    
    // Data types and structural elements
    REGISTER, // For register identifiers like R1, R2, R3...
    NUMBER,   // For numeric values (integers)
    NEWLINE,  // For line breaks to separate statements

}
