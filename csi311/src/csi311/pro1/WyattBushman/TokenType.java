package csi311.pro1.WyattBushman;

public enum TokenType {   
    WORD,
    NUMBER,
    SEPARATOR,

    // Common Programming Element Keywords
    WHILE,
    IF,
    DO,
    FOR,
    BREAK,
    CONTINUE,
    ELSE,
    RETURN,
    BEGIN,
    END,
    PRINT,
    PRINTF,
    NEXT,
    IN,
    DELETE,
    GETLINE,
    EXIT,
    NEXTFILE,
    FUNCTION,
    //I made TRUE/FALSE TOKENS FOR NOTHING!
    //TRUE,
    //FALSE,

    // Literal Types
    STRINGLITERAL,
    PATTERN,

    // Two-character symbols
    GT_EQ,  // >=
    INC,    // ++
    DEC,    // --
    LT_EQ,  // <=
    EQ_EQ,  // ==
    NOT_EQ, // !=
    POW,    // ^=
    MOD_ASSIGN, // %=
    MUL_ASSIGN, // *=
    DIV_ASSIGN, // /=
    PLUS_ASSIGN, // +=
    MINUS_ASSIGN, // -=
    NOT_MATCH,    // !~
    AND,          // &&
    APPEND,       // >>
    OR,           // ||

    
    // Single-character symbols
    OPEN_CURLY,  // {
    CLOSE_CURLY, // }
    OPEN_SQUARE, // [
    CLOSE_SQUARE,// ]
    OPEN_PAREN,  // (
    CLOSE_PAREN, // )
    DOLLAR,      // $
    MATCH,       // ~
    ASSIGNMENT,  // =
    LT,          // <
    GT,          // >
    NOT,         // !
    PLUS,        // +
    EXPONENT,    // ^ (since ^ is also used in two-character symbols)
    MINUS,       // -
    QUESTION,    // ?
    COLON,       // :
    ASTERISK,    // *
    DIVIDE,      // /
    MODULO,      // %
    PIPE,        // |
    COMMA,      // ,

}
