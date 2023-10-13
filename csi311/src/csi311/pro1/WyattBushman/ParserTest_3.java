package csi311.pro1.WyattBushman;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Optional;

public class ParserTest_3 {

    private LinkedList<Token> tokens;
    private Parser parser;
    private TokenManager tokenManager;

    @Before
    public void setUp() throws Exception {
        tokens = new LinkedList<>();
        parser = new Parser(tokens);
    }    
    @Test
    public void testParseAssignmentBasicDebug() {
        // ("x = 5")
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.ASSIGNMENT, "=", 1, 2));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 3));
        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseAssignment();

        System.out.println("Result present? " + result.isPresent()); // Check if result is present

        if(result.isPresent()) {
            System.out.println("Result class type: " + result.get().getClass().getSimpleName());  // Print the type of node returned
        }

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof AssignmentNode);  // <<< This line was originally OperationNode
        
        AssignmentNode assignmentNode = (AssignmentNode) result.get();
        System.out.println("Target node type: " + assignmentNode.getTarget().getClass().getSimpleName());  // Print the target node type
        System.out.println("Target node value: " + ((VariableReferenceNode) assignmentNode.getTarget()).getName()); // Print the target node's value
        
        assertTrue(assignmentNode.getTarget() instanceof VariableReferenceNode); //"x"
        assertEquals("x", ((VariableReferenceNode) assignmentNode.getTarget()).getName()); // Added line to check variable name
        
        Node rightNode = assignmentNode.getExpression(); // "5"
        System.out.println("Right node type: " + rightNode.getClass().getSimpleName());  // Print the right node type
        System.out.println("Right node value: " + ((ConstantNode) rightNode).getValue()); // Print the right node's value
        
        assertTrue(rightNode instanceof ConstantNode);
        assertEquals("5", ((ConstantNode) rightNode).getValue());  // Added line to check value
    }    
  @Test
  public void testParseAssignmentTrueLogic() {
      // ("x = 5")
      tokens.add(new Token(TokenType.WORD, "x", 1, 1));
      tokens.add(new Token(TokenType.ASSIGNMENT, "=", 1, 2));
      tokens.add(new Token(TokenType.NUMBER, "5", 1, 3));
      //parser.setDebugMode(true);
      Optional<Node> result = parser.ParseAssignment();

      System.out.println("Result present? " + result.isPresent()); // Check if result is present

      if(result.isPresent()) {
          System.out.println("Result class type: " + result.get().getClass().getSimpleName());  // Print the type of node returned
      }

      assertTrue(result.isPresent());
      assertTrue(result.get() instanceof AssignmentNode);  // <<< This line was originally OperationNode
      
      AssignmentNode assignmentNode = (AssignmentNode) result.get();
      System.out.println("Target node type: " + assignmentNode.getTarget().getClass().getSimpleName());  // Print the target node type
      System.out.println("Target node value: " + ((VariableReferenceNode) assignmentNode.getTarget()).getName()); // Print the target node's value
      
      assertTrue(assignmentNode.getTarget() instanceof VariableReferenceNode); //"x"
      assertEquals("x", ((VariableReferenceNode) assignmentNode.getTarget()).getName()); // Added line to check variable name
      
      Node rightNode = assignmentNode.getExpression(); // "5"
      System.out.println("Right node type: " + rightNode.getClass().getSimpleName());  // Print the right node type
      System.out.println("Right node value: " + ((ConstantNode) rightNode).getValue()); // Print the right node's value
      
      assertTrue(rightNode instanceof ConstantNode);
      assertEquals("5", ((ConstantNode) rightNode).getValue());  // Added line to check value
  }        
    @Test
    public void testParseTernaryBasicDebug() {
        // ("x ? 5 : 6")
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.QUESTION, "?", 1, 2));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 3));
        tokens.add(new Token(TokenType.COLON, ":", 1, 4));
        tokens.add(new Token(TokenType.NUMBER, "6", 1, 5));

        parser.setDebugMode(true);

        Optional<Node> result = parser.ParseTernary();

        // Print outputs for debugging
        System.out.println("Result present? " + result.isPresent());
        System.out.println("Result class type: " + result.get().getClass().getSimpleName());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof TernaryNode);

        TernaryNode ternaryNode = (TernaryNode) result.get();

        assertTrue(ternaryNode.getCondition() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) ternaryNode.getCondition()).getName());

        assertTrue(ternaryNode.getTrueBranch() instanceof ConstantNode);
        assertEquals("5", ((ConstantNode) ternaryNode.getTrueBranch()).getValue());

        assertTrue(ternaryNode.getFalseBranch() instanceof ConstantNode);
        assertEquals("6", ((ConstantNode) ternaryNode.getFalseBranch()).getValue());
    }    
    @Test
    public void testParseTernaryBasicTrueLogic() {
        // ("x ? 5 : 6")
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.QUESTION, "?", 1, 2));
        tokens.add(new Token(TokenType.NUMBER, "5", 1, 3));
        tokens.add(new Token(TokenType.COLON, ":", 1, 4));
        tokens.add(new Token(TokenType.NUMBER, "6", 1, 5));

        parser.setDebugMode(false);

        Optional<Node> result = parser.ParseTernary();

        // Print outputs for debugging
        System.out.println("Result present? " + result.isPresent());
        System.out.println("Result class type: " + result.get().getClass().getSimpleName());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof TernaryNode);

        TernaryNode ternaryNode = (TernaryNode) result.get();

        assertTrue(ternaryNode.getCondition() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) ternaryNode.getCondition()).getName());

        assertTrue(ternaryNode.getTrueBranch() instanceof ConstantNode);
        assertEquals("5", ((ConstantNode) ternaryNode.getTrueBranch()).getValue());

        assertTrue(ternaryNode.getFalseBranch() instanceof ConstantNode);
        assertEquals("6", ((ConstantNode) ternaryNode.getFalseBranch()).getValue());
    }    
    @Test
    public void testParseOrBasicDebug() {
        // ("a || b")
        tokens.add(new Token(TokenType.WORD, "a", 1, 1));
        tokens.add(new Token(TokenType.OR, "||", 1, 2));  // Assuming you have TokenType.OR for "||"
        tokens.add(new Token(TokenType.WORD, "b", 1, 3));

        parser.setDebugMode(true);

        Optional<Node> result = parser.ParseOr();

        // Print outputs for debugging
        System.out.println("Result present? " + result.isPresent());
        System.out.println("Result class type: " + result.get().getClass().getSimpleName());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OrNode);

        OrNode orNode = (OrNode) result.get();

        assertTrue(orNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("a", ((VariableReferenceNode) orNode.getLeft()).getName());

        assertTrue(orNode.getRight() instanceof VariableReferenceNode);
        assertEquals("b", ((VariableReferenceNode) orNode.getRight()).getName());
    }    
    @Test
    public void testParseOrBasicTrueLogic() {
        // Input: "a || b"
        tokens.add(new Token(TokenType.WORD, "a", 1, 1));
        tokens.add(new Token(TokenType.OR, "||", 1, 3));
        tokens.add(new Token(TokenType.WORD, "b", 1, 6));

        // Do not set the debug mode: parser.setDebugMode(false); // This line is unnecessary if false is the default.

        Optional<Node> result = parser.ParseOr();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OrNode);

        OrNode orNode = (OrNode) result.get();

        assertTrue(orNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("a", ((VariableReferenceNode) orNode.getLeft()).getName());

        assertTrue(orNode.getRight() instanceof VariableReferenceNode);
        assertEquals("b", ((VariableReferenceNode) orNode.getRight()).getName());
    }    
    @Test
    public void testParseAndBasicDebug() {
        // Assuming the input is "x && y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.AND, "&&", 1, 2));  // Assuming you have TokenType.AND for "&&"
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        parser.setDebugMode(true);

        Optional<Node> result = parser.ParseAnd();

        // Print outputs for debugging
        System.out.println("Result present? " + result.isPresent());
        System.out.println("Result class type: " + result.get().getClass().getSimpleName());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof AndNode);

        AndNode andNode = (AndNode) result.get();

        assertTrue(andNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) andNode.getLeft()).getName());

        assertTrue(andNode.getRight() instanceof VariableReferenceNode);
        assertEquals("y", ((VariableReferenceNode) andNode.getRight()).getName());
    }   
    @Test
    public void testParseAndBasicTrueLogic() {
        // Assuming the input is "x && y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.AND, "&&", 1, 2));  
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        parser.setDebugMode(true);

        Optional<Node> result = parser.ParseAnd();

        // Print outputs for debugging
        System.out.println("Result present? " + result.isPresent());
        System.out.println("Result class type: " + result.get().getClass().getSimpleName());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof AndNode);

        AndNode andNode = (AndNode) result.get();

        assertTrue(andNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) andNode.getLeft()).getName());

        assertTrue(andNode.getRight() instanceof VariableReferenceNode);
        assertEquals("y", ((VariableReferenceNode) andNode.getRight()).getName());
    }    
    @Test
    public void testParseArrayMembershipDebug() {
        // Mocking "x in arrayName"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.IN, "in", 1, 2));
        tokens.add(new Token(TokenType.WORD, "arrayName", 1, 3));
        
        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseArrayMembership();

        System.out.println("Result present? " + result.isPresent()); // Check if result is present

        if(result.isPresent()) {
            System.out.println("Result class type: " + result.get().getClass().getSimpleName());  // Print the type of node returned
        }

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ArrayMembershipNode);
      
        ArrayMembershipNode arrayMembershipNode = (ArrayMembershipNode) result.get();
        System.out.println("Element node type: " + arrayMembershipNode.getElement().getClass().getSimpleName());  
        System.out.println("Element node value: " + ((VariableReferenceNode) arrayMembershipNode.getElement()).getName());
      
        assertTrue(arrayMembershipNode.getElement() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) arrayMembershipNode.getElement()).getName());
      
        Node arrayNode = arrayMembershipNode.getArray(); 
        System.out.println("Array node type: " + arrayNode.getClass().getSimpleName());
        System.out.println("Array node value: " + ((VariableReferenceNode) arrayNode).getName());
      
        assertTrue(arrayNode instanceof VariableReferenceNode);
        assertEquals("arrayName", ((VariableReferenceNode) arrayNode).getName());
    }    
    @Test
    public void testParseArrayMembershipTrueLogic() {
        // Mocking "x in arrayName"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.IN, "in", 1, 2));
        tokens.add(new Token(TokenType.WORD, "arrayName", 1, 3));
        
        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseArrayMembership();

        System.out.println("Result present? " + result.isPresent()); // Check if result is present

        if(result.isPresent()) {
            System.out.println("Result class type: " + result.get().getClass().getSimpleName());  // Print the type of node returned
        }

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ArrayMembershipNode);
      
        ArrayMembershipNode arrayMembershipNode = (ArrayMembershipNode) result.get();
        System.out.println("Element node type: " + arrayMembershipNode.getElement().getClass().getSimpleName());  
        System.out.println("Element node value: " + ((VariableReferenceNode) arrayMembershipNode.getElement()).getName());
      
        assertTrue(arrayMembershipNode.getElement() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) arrayMembershipNode.getElement()).getName());
      
        Node arrayNode = arrayMembershipNode.getArray(); 
        System.out.println("Array node type: " + arrayNode.getClass().getSimpleName());
        System.out.println("Array node value: " + ((VariableReferenceNode) arrayNode).getName());
      
        assertTrue(arrayNode instanceof VariableReferenceNode);
        assertEquals("arrayName", ((VariableReferenceNode) arrayNode).getName());
    }  
    @Test
    public void testParseMatchDebug() {
        // Mocking "x ~ pattern"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.MATCH, "~", 1, 2));
        tokens.add(new Token(TokenType.PATTERN, "pattern", 1, 3));
        
        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseMatch();

        System.out.println("Result present? " + result.isPresent()); // Check if result is present

        if(result.isPresent()) {
            System.out.println("Result class type: " + result.get().getClass().getSimpleName());  // Print the type of node returned
        }

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof MatchNode);
      
        MatchNode matchNode = (MatchNode) result.get();
        System.out.println("Left node type: " + matchNode.getLeft().getClass().getSimpleName());  
        System.out.println("Left node value: " + ((VariableReferenceNode) matchNode.getLeft()).getName());

        assertTrue(matchNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) matchNode.getLeft()).getName());
      
        Node patternNode = matchNode.getPattern(); 
        System.out.println("Pattern node type: " + patternNode.getClass().getSimpleName());
        System.out.println("Pattern node value: " + ((RegexPatternNode) patternNode).getValue());
      
        assertTrue(patternNode instanceof RegexPatternNode);
        assertEquals("pattern", ((RegexPatternNode) patternNode).getValue());
    }    
    @Test
    public void testParseMatchTrueLogic() {
        // Mocking "x ~ pattern"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.MATCH, "~", 1, 2));
        tokens.add(new Token(TokenType.PATTERN, "pattern", 1, 3));
        
        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseMatch();

        System.out.println("Result present? " + result.isPresent()); // Check if result is present

        if(result.isPresent()) {
            System.out.println("Result class type: " + result.get().getClass().getSimpleName());  // Print the type of node returned
        }

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof MatchNode);
      
        MatchNode matchNode = (MatchNode) result.get();
        System.out.println("Left node type: " + matchNode.getLeft().getClass().getSimpleName());  
        System.out.println("Left node value: " + ((VariableReferenceNode) matchNode.getLeft()).getName());

        assertTrue(matchNode.getLeft() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) matchNode.getLeft()).getName());
      
        Node patternNode = matchNode.getPattern(); 
        System.out.println("Pattern node type: " + patternNode.getClass().getSimpleName());
        System.out.println("Pattern node value: " + ((RegexPatternNode) patternNode).getValue());
      
        assertTrue(patternNode instanceof RegexPatternNode);
        assertEquals("pattern", ((RegexPatternNode) patternNode).getValue());
    }    
    @Test
    public void testParseCompareDebug() {
        // Mocking "x >= y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.GT_EQ, ">=", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));
        
        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseCompare();

        System.out.println("Result present? " + result.isPresent());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof CompareNode);
      
        CompareNode compareNode = (CompareNode) result.get();
        assertEquals(TokenType.GT_EQ, compareNode.getComparisonType());
        assertTrue(compareNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(compareNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) compareNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) compareNode.getRight()).getName());
    }    
    @Test
    public void testParseCompareTrueLogic() {
        // Mocking "x >= y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.GT_EQ, ">=", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));
        
        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseCompare();

        System.out.println("Result present? " + result.isPresent());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof CompareNode);
      
        CompareNode compareNode = (CompareNode) result.get();
        assertEquals(TokenType.GT_EQ, compareNode.getComparisonType());
        assertTrue(compareNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(compareNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) compareNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) compareNode.getRight()).getName());
    }    
    @Test
    public void testParseConcatenationDebug() {
        // Mocking "x + y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.PLUS, "+", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));
        
        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseConcatenation();

        System.out.println("Result present? " + result.isPresent());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConcatenationNode);
      
        ConcatenationNode concatNode = (ConcatenationNode) result.get();
        assertTrue(concatNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(concatNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) concatNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) concatNode.getRight()).getName());
    }   
    @Test
    public void testParseConcatenationTrueLogic() {
        // Mocking "x + y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.PLUS, "+", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));
        
        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseConcatenation();

        System.out.println("Result present? " + result.isPresent());

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConcatenationNode);
      
        ConcatenationNode concatNode = (ConcatenationNode) result.get();
        assertTrue(concatNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(concatNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) concatNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) concatNode.getRight()).getName());
    }
    @Test
    public void testParseExpressionDebug() {
        // Mocking "x + y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.PLUS, "+", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseExpression();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof AdditionNode);

        AdditionNode additionNode = (AdditionNode) result.get();
        assertTrue(additionNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(additionNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) additionNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) additionNode.getRight()).getName());
    }    
    @Test
    public void testParseExpressionTrueLogic() {
        // Mocking "x + y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.PLUS, "+", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseExpression();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof AdditionNode);

        AdditionNode additionNode = (AdditionNode) result.get();
        assertTrue(additionNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(additionNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) additionNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) additionNode.getRight()).getName());
    }
    
    @Test
    public void testParseExpressionMinusTrueLogic() {
        // Mocking "x - y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.MINUS, "-", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseExpression();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof SubtractionNode);

        SubtractionNode subtractionNode = (SubtractionNode) result.get();
        assertTrue(subtractionNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(subtractionNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) subtractionNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) subtractionNode.getRight()).getName());
    }  
    @Test
    public void testParseTermDebug() {
        // Mocking "x * y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.ASTERISK, "*", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));
        
        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseTerm();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof MultiplicationNode);

        MultiplicationNode multiplicationNode = (MultiplicationNode) result.get();
        assertTrue(multiplicationNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(multiplicationNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) multiplicationNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) multiplicationNode.getRight()).getName());
    }    
    @Test
    public void testParseTermTrueLogic() {
        // Mocking "x * y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.ASTERISK, "*", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));
        
        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseTerm();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof MultiplicationNode);

        MultiplicationNode multiplicationNode = (MultiplicationNode) result.get();
        assertTrue(multiplicationNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(multiplicationNode.getRight() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) multiplicationNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) multiplicationNode.getRight()).getName());
    }    
    @Test
    public void testParseExponentsDebug() {
        // Mocking "x ^ y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.EXPONENT, "^", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseExponents();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);

        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.EXPONENTIATION, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(opNode.getRight().isPresent());
        assertTrue(opNode.getRight().get() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) opNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) opNode.getRight().get()).getName());
    }    
    @Test
    public void testParseExponentsTrueCode() {
        // Mocking "x ^ y"
        tokens.add(new Token(TokenType.WORD, "x", 1, 1));
        tokens.add(new Token(TokenType.EXPONENT, "^", 1, 2));
        tokens.add(new Token(TokenType.WORD, "y", 1, 3));

        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseExponents();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);

        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.EXPONENTIATION, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertTrue(opNode.getRight().isPresent());
        assertTrue(opNode.getRight().get() instanceof VariableReferenceNode);
        assertEquals("x", ((VariableReferenceNode) opNode.getLeft()).getName());
        assertEquals("y", ((VariableReferenceNode) opNode.getRight().get()).getName());
    }
    
    @Test
    public void testParsePostIncrementAndDecrementTrueCode() {
        // Mocking "a++"
        tokens.add(new Token(TokenType.WORD, "a", 1, 1));
        tokens.add(new Token(TokenType.INC, "++", 1, 2));

        parser.setDebugMode(true);
        Optional<Node> result = parser.ParsePostIncrementAndDecrement();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);

        OperationNode opNode = (OperationNode) result.get();
        assertEquals(OperationType.POSTFIX_INCREMENT, opNode.getOperationType());
        assertTrue(opNode.getLeft() instanceof VariableReferenceNode);
        assertFalse(opNode.getRight().isPresent());
        assertEquals("a", ((VariableReferenceNode) opNode.getLeft()).getName());
    }    
    @Test
    public void testParseLValueDebug() {
        // Mocking "array[x]"
        tokens.add(new Token(TokenType.WORD, "array", 1, 1));
        tokens.add(new Token(TokenType.OPEN_SQUARE, "[", 1, 2));
        tokens.add(new Token(TokenType.WORD, "x", 1, 3));
        tokens.add(new Token(TokenType.CLOSE_SQUARE, "]", 1, 4));

        parser.setDebugMode(true);
        Optional<Node> result = parser.ParseLValue();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof VariableReferenceNode);
        VariableReferenceNode varRefNode = (VariableReferenceNode) result.get();
        assertEquals("array", varRefNode.getName());
        assertTrue(varRefNode.getIndexExpression().isPresent());
        assertEquals("x", ((VariableReferenceNode) varRefNode.getIndexExpression().get()).getName());
    }   
    @Test
    public void testParseLValueTrueCode() {
        // Mocking "array[x]"
        tokens.add(new Token(TokenType.WORD, "array", 1, 1));
        tokens.add(new Token(TokenType.OPEN_SQUARE, "[", 1, 2));
        tokens.add(new Token(TokenType.WORD, "x", 1, 3));
        tokens.add(new Token(TokenType.CLOSE_SQUARE, "]", 1, 4));

        //parser.setDebugMode(true);
        Optional<Node> result = parser.ParseLValue();

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof VariableReferenceNode);
        VariableReferenceNode varRefNode = (VariableReferenceNode) result.get();
        assertEquals("array", varRefNode.getName());
        assertTrue(varRefNode.getIndexExpression().isPresent());
        assertEquals("x", ((VariableReferenceNode) varRefNode.getIndexExpression().get()).getName());
    }    
    @Test
    public void testParseBottomLevel() {
        // Testing STRINGLITERAL
        tokens.add(new Token(TokenType.STRINGLITERAL, "\"Hello\"", 1, 1));
        Optional<Node> result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConstantNode);
        assertEquals("\"Hello\"", ((ConstantNode) result.get()).getValue());

        tokens.clear();
        
        // Testing NUMBER
        tokens.add(new Token(TokenType.NUMBER, "42", 2, 1));
        result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof ConstantNode);
        assertEquals("42", ((ConstantNode) result.get()).getValue());

        tokens.clear();

        // Testing unary PLUS
        tokens.add(new Token(TokenType.PLUS, "+", 4, 1));
        tokens.add(new Token(TokenType.NUMBER, "7", 4, 2));
        result = parser.ParseBottomLevel();
        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OperationNode);
        assertEquals("7", ((ConstantNode) ((OperationNode) result.get()).getLeft()).getValue());
        assertEquals(OperationType.UNARY_POSITIVE, ((OperationNode) result.get()).getOperationType());
    }
    
    @Test
    public void testParseBottomLevelWithParenthesis() {
        // Testing with a simple enclosed number
        tokens.add(new Token(TokenType.OPEN_PAREN, "(", 5, 1));
        tokens.add(new Token(TokenType.NUMBER, "5", 5, 2));
        tokens.add(new Token(TokenType.CLOSE_PAREN, ")", 5, 3));
        
        Optional<Node> result = parser.ParseBottomLevel();
        System.out.println(result.get());

        assertTrue("Expected a result from parsing", result.isPresent());
        assertTrue("Expected a ConstantNode", result.get() instanceof ConstantNode);
        assertEquals("Expected the value to be 5", "5", ((ConstantNode) result.get()).getValue());

        tokens.clear();

        // Testing with a more complex enclosed expression (e.g., (a + b))
        tokens.add(new Token(TokenType.OPEN_PAREN, "(", 6, 1));
        tokens.add(new Token(TokenType.WORD, "a", 6, 2));
        tokens.add(new Token(TokenType.PLUS, "+", 6, 3));
        tokens.add(new Token(TokenType.WORD, "b", 6, 4));
        tokens.add(new Token(TokenType.CLOSE_PAREN, ")", 6, 5));
        
        result = parser.ParseBottomLevel();
        System.out.println(result.get());

        assertTrue("Expected a result from parsing", result.isPresent());
        assertTrue("Expected an AdditionNode", result.get() instanceof AdditionNode);
        assertEquals("Expected the left side of the addition to be 'a'", "a", ((VariableReferenceNode) ((AdditionNode) result.get()).getLeft()).getName());
        assertEquals("Expected the right side of the addition to be 'b'", "b", ((VariableReferenceNode) ((AdditionNode) result.get()).getRight()).getName());
    }


    

    



    
    




    
    


    
    
    
    
    
    







    
    

    
    

 
    
    



}
