package csi311.pro1.WyattBushman;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;
    private LinkedList<Token> tokens;

    @Before
    public void setUp() {
        tokens = new LinkedList<>();
        parser = new Parser(tokens);
    }

    @Test
    public void testAcceptSeparatorsMultipleSeparators() {
        tokens.add(new Token(TokenType.SEPARATOR, 1, 1));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 1));
        assertTrue(parser.AcceptSeparators());
    }

    @Test
    public void testAcceptSeparatorsNoSeparators() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        assertFalse(parser.AcceptSeparators());
    }
    
    @Test
    public void testParseEmptyList() {
        assertNotNull(parser.Parse());
    }

    @Test(expected = RuntimeException.class)
    public void testParseWithUnmatchedTokens() {
        tokens.add(new Token(TokenType.WORD, "test", 1, 1));
        parser.Parse();
    }


    @Test
    public void testAcceptSeparatorsWithMultipleSeparators() {
        tokens.add(new Token(TokenType.SEPARATOR, 1, 1));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 1));
        assertTrue(parser.AcceptSeparators());
        assertFalse(parser.getTokenManager().MoreTokens()); // Makes sure all separators are consumed
    }
    
    @Test
    public void testPeekBeyondTokens() {
        tokens.add(new Token(TokenType.WORD, "word", 1, 1));
        assertFalse(parser.getTokenManager().Peek(2).isPresent());
    }
    
    
    @Test(expected = RuntimeException.class)
    public void testUnexpectedToken() {
        tokens.add(new Token(TokenType.WORD, "word", 1, 1));
        parser.Parse();
    }

    @Test(expected = RuntimeException.class)
    public void testParseFunctionMissingFunctionName() {
        tokens.add(new Token(TokenType.FUNCTION, 1, 1));
        parser.ParseFunction(new ProgramNode());
    }

    @Test(expected = RuntimeException.class)
    public void testParseFunctionMissingParameterName() {
        tokens.add(new Token(TokenType.FUNCTION, 1, 1));
        tokens.add(new Token(TokenType.WORD, "myFunction", 2, 1));
        tokens.add(new Token(TokenType.OPEN_PAREN, 3, 1));
        tokens.add(new Token(TokenType.COMMA, 4, 1));
        parser.ParseFunction(new ProgramNode());
    }
    
    @Test
    public void testParseActionBegin() {
        tokens.add(new Token(TokenType.BEGIN, 1, 1));
        tokens.add(new Token(TokenType.OPEN_CURLY, 2, 1));
        tokens.add(new Token(TokenType.CLOSE_CURLY, 3, 1));
        assertTrue(parser.ParseAction(new ProgramNode()));
    }

    @Test
    public void testParseActionEnd() {
        tokens.add(new Token(TokenType.END, 1, 1));
        tokens.add(new Token(TokenType.OPEN_CURLY, 2, 1));
        tokens.add(new Token(TokenType.CLOSE_CURLY, 3, 1));
        assertTrue(parser.ParseAction(new ProgramNode()));
    }

    @Test(expected = RuntimeException.class)
    public void testParseActionUnmatchedBeginBlock() {
        tokens.add(new Token(TokenType.BEGIN, 1, 1));
        parser.ParseAction(new ProgramNode());
    }
    
    @Test
    public void testParseBlockBasic() {
        tokens.add(new Token(TokenType.OPEN_CURLY, 1, 1));
        tokens.add(new Token(TokenType.CLOSE_CURLY, 2, 1));
        assertNotNull(parser.ParseBlock());
    }

    @Test(expected = RuntimeException.class)
    public void testParseBlockUnmatched() {
        tokens.add(new Token(TokenType.OPEN_CURLY, 1, 1));
        parser.ParseBlock();
    }
    
    @Test
    public void testParseBlockBasicBraces() {
        tokens.add(new Token(TokenType.OPEN_CURLY, 1, 1));
        tokens.add(new Token(TokenType.CLOSE_CURLY, 2, 1));
        BlockNode blockNode = parser.ParseBlock();
        assertNotNull(blockNode);
    }
    
    @Test
    public void testParseBlockWithContents() {
        tokens.add(new Token(TokenType.OPEN_CURLY, 1, 1));
        tokens.add(new Token(TokenType.WORD, "test", 2, 2));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 7));
        tokens.add(new Token(TokenType.CLOSE_CURLY, 3, 1));
        BlockNode blockNode = parser.ParseBlock();
        assertNotNull(blockNode);
    }
    
    @Test(expected = RuntimeException.class)
    public void testParseBlockWithoutClosingBrace() {
        tokens.add(new Token(TokenType.OPEN_CURLY, 1, 1));
        tokens.add(new Token(TokenType.WORD, "test", 2, 2));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 7));
        parser.ParseBlock();
    }
    
    @Test(expected = RuntimeException.class)
    public void testParseBlockWithoutOpeningBrace() {
        tokens.add(new Token(TokenType.WORD, "test", 2, 2));
        tokens.add(new Token(TokenType.SEPARATOR, 2, 7));
        tokens.add(new Token(TokenType.CLOSE_CURLY, 3, 1));
        parser.ParseBlock();
    }
    
    @Test
    public void testParseLValueVariable() {
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        Optional<Node> result = parser.ParseLValue();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof VariableReferenceNode);
        VariableReferenceNode varNode = (VariableReferenceNode) result.get();
        assertEquals("x", varNode.getName());
        assertFalse(varNode.getIndexExpression().isPresent());
    }
    
    @Test
    public void testParseLValueDollar() {
        tokens.add(new Token(TokenType.DOLLAR, 1, 1));
        tokens.add(new Token(TokenType.WORD, "z", 2, 2));
        Optional<Node> result = parser.ParseLValue();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.FIELD_SELECTOR, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        VariableReferenceNode varNode = (VariableReferenceNode) opNode.getLeft();
        assertEquals("z", varNode.getName());
        assertFalse(varNode.getIndexExpression().isPresent());

    }

    @Test
    public void testParseLValueArray() {
        tokens.add(new Token(TokenType.WORD, "y", 1, 1));
        tokens.add(new Token(TokenType.OPEN_SQUARE, 2, 1));
        tokens.add(new Token(TokenType.NUMBER, "3", 3, 1)); 
        tokens.add(new Token(TokenType.CLOSE_SQUARE, 4, 1));
        Optional<Node> result = parser.ParseLValue();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof VariableReferenceNode);
        VariableReferenceNode varNode = (VariableReferenceNode) result.get();
        assertEquals("y", varNode.getName());
        assertTrue(varNode.getIndexExpression().isPresent());
        assertTrue(varNode.getIndexExpression().get() instanceof ConstantNode); 
        ConstantNode indexNode = (ConstantNode) varNode.getIndexExpression().get();
        assertEquals("3", indexNode.getValue());
    }
    
    @Test
    public void testParseBottomLevelStringLiteral() {
        tokens.add(new Token(TokenType.STRINGLITERAL, "\"Hello, world!\"", 1, 1));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConstantNode);
        ConstantNode stringNode = (ConstantNode) result.get();
        assertEquals("\"Hello, world!\"", stringNode.getValue());
    }
    
    @Test
    public void testParseBottomLevelNumber() {
        tokens.add(new Token(TokenType.NUMBER, "42", 1, 1));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConstantNode);
        ConstantNode numberNode = (ConstantNode) result.get();
        assertEquals("42", numberNode.getValue());
    }
    
    @Test
    public void testParseBottomLevelPattern() {
        tokens.add(new Token(TokenType.PATTERN, "/pattern/", 1, 1));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof PatternNode);
        PatternNode patternNode = (PatternNode) result.get();
        assertEquals("/pattern/", patternNode.getPattern());
    }
    
    @Test
    public void testParseBottomLevelParenthesizedNumber() {
        tokens.add(new Token(TokenType.OPEN_PAREN, "(", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 2));
        tokens.add(new Token(TokenType.CLOSE_PAREN, ")", 1, 3));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConstantNode);
        ConstantNode numberNode = (ConstantNode) result.get();
        assertEquals("5", numberNode.getValue());
    }
    
    @Test
    public void testParseBottomLevelNot() {
        tokens.add(new Token(TokenType.NOT, "!", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "0", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.LOGICAL_NOT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof ConstantNode);
        assertEquals("0", ((ConstantNode) opNode.getLeft()).getValue());
    }
    
    @Test
    public void testParseBottomLevelUnaryPositive() {
        tokens.add(new Token(TokenType.PLUS, "+", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.UNARY_POSITIVE, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof ConstantNode);
        assertEquals("5", ((ConstantNode) opNode.getLeft()).getValue());
    }
    
    @Test
    public void testParseBottomLevelUnaryNegative() {
        tokens.add(new Token(TokenType.MINUS, "-", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "3", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.UNARY_NEGATIVE, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof ConstantNode);
        assertEquals("3", ((ConstantNode) opNode.getLeft()).getValue());
    }
    
    @Test
    public void testParseBottomLevelPrefixIncrement() {
        tokens.add(new Token(TokenType.INC, "++", 1, 1));
        tokens.add(new Token(TokenType.WORD, "x", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.PREFIX_INCREMENT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) opNode.getLeft()).getName());
    }
    
    @Test
    public void testParseBottomLevelPrefixDecrement() {
        tokens.add(new Token(TokenType.DEC, "--", 1, 1));
        tokens.add(new Token(TokenType.WORD, "y", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.PREFIX_DECREMENT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("y", ((VariableReferenceNode) opNode.getLeft()).getName());
    }
    
    @Test
    public void testParseBottomLevelNestedUnaries() {
        tokens.add(new Token(TokenType.MINUS, "-", 1, 1));
        tokens.add(new Token(TokenType.PLUS, "+", 1, 2));
        tokens.add(new Token(TokenType.NUMBER, "4", 1, 3));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode outerOpNode = (OperationNode) result.get();
        assertEquals(OperationType.UNARY_NEGATIVE, outerOpNode.getOperationType());
        
        assertTrue(outerOpNode.getLeft() instanceof OperationNode);
        OperationNode innerOpNode = (OperationNode) outerOpNode.getLeft();
        assertEquals(OperationType.UNARY_POSITIVE, innerOpNode.getOperationType());
        
        assertTrue(innerOpNode.getLeft() instanceof ConstantNode);
        assertEquals("4", ((ConstantNode) innerOpNode.getLeft()).getValue());
    }
    
    @Test
    public void testParseBottomLevelNestedParens() {
        tokens.add(new Token(TokenType.OPEN_PAREN, "(", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 2));
        tokens.add(new Token(TokenType.CLOSE_PAREN, ")", 1, 3));
        tokens.add(new Token(TokenType.OPEN_PAREN, "(", 1, 4));
        tokens.add(new Token(TokenType.NUMBER, "6", 1, 5));
        tokens.add(new Token(TokenType.CLOSE_PAREN, ")", 1, 6));
        
        Optional<Node> firstResult = parser.ParseBottomLevel();
        Optional<Node> secondResult = parser.ParseBottomLevel();
        
        assertTrue(firstResult.isPresent());
        assertTrue(firstResult.get() instanceof ConstantNode);
        assertEquals("5", ((ConstantNode) firstResult.get()).getValue());
        
        assertTrue(secondResult.isPresent());
        assertTrue(secondResult.get() instanceof ConstantNode);
        assertEquals("6", ((ConstantNode) secondResult.get()).getValue());
    }
    
    @Test
    public void testParseBottomLevelNotOperator() {
        tokens.add(new Token(TokenType.NOT, "!", 1, 1));
        tokens.add(new Token(TokenType.NUMBER, "7", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.LOGICAL_NOT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof ConstantNode);
        assertEquals("7", ((ConstantNode) opNode.getLeft()).getValue());
    }

    
    @Test
    public void testParseBottomLevelUnaryPlusWithString() {
        tokens.add(new Token(TokenType.PLUS, "+", 1, 1));
        tokens.add(new Token(TokenType.STRINGLITERAL, "hello", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        //System.out.println(result);
        assertFalse(result.isPresent());
    }


    @Test
    public void testParseBottomLevelUnaryMinusWithString() {
        tokens.add(new Token(TokenType.MINUS, "-", 1, 1));
        tokens.add(new Token(TokenType.STRINGLITERAL, "world", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertFalse(result.isPresent());  // Unary minus shouldn't be valid with a string literal.
    }
    
    @Test
    public void testParseBottomLevelPostIncrement() {
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.INC, "++", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.POSTFIX_INCREMENT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) opNode.getLeft()).getName());
    }

    @Test
    public void testParseBottomLevelPostDecrement() {
        tokens.add(new Token(TokenType.WORD, "y", 1, 1));
        tokens.add(new Token(TokenType.DEC, "--", 1, 2));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.POSTFIX_DECREMENT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("y", ((VariableReferenceNode) opNode.getLeft()).getName());
    }

    @Test(expected = RuntimeException.class)
    public void testParseBottomLevelInvalidPostIncrement() {
        tokens.add(new Token(TokenType.STRINGLITERAL, "\"Hello\"", 1, 1));
        tokens.add(new Token(TokenType.INC, "++", 1, 8));
        parser.ParseBottomLevel();
    }
    
    @Test
    public void testParseAssignmentBasic() {
        // ("x = 5")
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.ASSIGNMENT, "=", 1, 2));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 3));
        Optional<Node> result = parser.ParseAssignment();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        OperationNode opNode = (OperationNode) result.get();
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode); //"x"
        assertEquals(OperationType.EQUALS, opNode.getOperationType()); // "="
        Node rightNode = opNode.getRight().orElse(null); // "5"
        assertTrue(rightNode instanceof ConstantNode);
    }
 
    @Test(expected = RuntimeException.class)
    public void testParseAssignmentNonVariableLHS() {
        tokens.add(new Token(TokenType.NUMBER, "15", 1, 1));
        tokens.add(new Token(TokenType.ASSIGNMENT, "=", 1, 3));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 4));
        
        try {
            Optional<Node> result = parser.ParseAssignment();
            System.out.println(result.isPresent() ? result.get().toString() : "No Node returned");
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
    
    
    
    


}
