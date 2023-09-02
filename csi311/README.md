README.md

ICSI311 Lexer 1 Documentation:


# StringHandler Class:

# processWord()
This method reads a sequence of letters, digits, or underscores from the input AWK script and forms a word token. 
It stops reading when it finds a character that doesn't fit these criteria.

# processNumber()
This method reads a sequence of digits from the input AWK script to form a number token. 
It can also recognize decimal numbers and will stop reading when it encounters a non-digit character.