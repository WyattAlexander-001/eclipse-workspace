package csi311.pro1.WyattBushman;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import csi311.pro1.WyattBushman.Interpreter.LineManager;

import java.util.LinkedList;
import java.util.Optional;

public class InterpreterTest2Expanded extends InterpreterTest2 {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    
    @Test
    public void testAssignmentAndAddition() {
        // Creating the nodes for the expression "a = 2 + 2"
        VariableReferenceNode varRefNode = new VariableReferenceNode("a", Optional.empty());
        ConstantNode constantTwo = new ConstantNode("2");
        OperationNode additionNode = new OperationNode(constantTwo, Optional.of(constantTwo), OperationType.ADDITION);
        AssignmentNode assignmentNode = new AssignmentNode(varRefNode, additionNode);

        // Execute the operation
        InterpreterDataType result = getInterpreter().GetIDT(assignmentNode, null);

        // Verify that "a" now exists with a value of "4.0" (since float operations might result in "4.0" instead of "4")
        // Convert the string value to float for comparison
        float actualValue = Float.parseFloat(getInterpreter().globalVariables.get("a").getValue());

        // Assert equality with a small delta for floating point comparisons
        assertEquals(4.0f, actualValue, 0.0001f); // Use a small delta for comparing floating point numbers

        // Also, verify the result returned from GetIDT is the expected value, again as a float
        float resultValue = Float.parseFloat(result.getValue());
        assertEquals(4.0f, resultValue, 0.0001f);
    }




    @Test
    public void testFunctionExistence() {
        assertNotNull(getInterpreter().functionDefinitions.get("print"));
        assertNotNull(getInterpreter().functionDefinitions.get("next"));
        assertNotNull(getInterpreter().functionDefinitions.get("toLower"));
        assertNotNull(getInterpreter().functionDefinitions.get("split"));
    }

    @Test
    public void testPrintFunctionStructure() {
        FunctionDefinitionNode printFunction = getInterpreter().functionDefinitions.get("print");
        assertEquals("print", printFunction.getFunctionName());
        assertTrue(printFunction instanceof BuiltInFunctionDefinitionNode);
        assertTrue(((BuiltInFunctionDefinitionNode) printFunction).isVariadic());
    }

    @Test
    public void testNextFunctionStructure() {
        FunctionDefinitionNode nextFunction = getInterpreter().functionDefinitions.get("next");
        assertEquals("next", nextFunction.getFunctionName());
        assertTrue(nextFunction.getParameters().isEmpty());
    }

    @Test
    public void testToLowerFunctionStructure() {
        FunctionDefinitionNode toLowerFunction = getInterpreter().functionDefinitions.get("toLower");
        assertEquals("toLower", toLowerFunction.getFunctionName());
        assertTrue(toLowerFunction instanceof BuiltInFunctionDefinitionNode);
    }

    @Test
    public void testSplitFunctionStructure() {
        FunctionDefinitionNode splitFunction = getInterpreter().functionDefinitions.get("split");
        assertEquals("split", splitFunction.getFunctionName());
        assertTrue(splitFunction instanceof BuiltInFunctionDefinitionNode);
    }
    
//    @Test
//    public void testInterpretProgram() throws Exception {
//        // Create a simple program with BEGIN and END blocks
//        ProgramNode programNode = new ProgramNode();
//        BlockNode beginBlock = new BlockNode();
//        BlockNode endBlock = new BlockNode();
//
//        // Add statements to BEGIN and END blocks that modify a global variable
//        beginBlock.addStatement(new AssignmentNode(new VariableReferenceNode("testBegin", Optional.empty()), new ConstantNode("1")));
//        endBlock.addStatement(new AssignmentNode(new VariableReferenceNode("testEnd", Optional.empty()), new ConstantNode("1")));
//
//        programNode.addBeginBlock(beginBlock);
//        programNode.addEndBlock(endBlock);
//
//        // Run the interpreter
//        Interpreter interpreter = new Interpreter(programNode, null);
//        interpreter.interpretProgram(programNode);
//
//        // Assert that the BEGIN and END blocks executed by checking the global variables
//        assertEquals("1", interpreter.globalVariables.get("testBegin").getValue());
//        assertEquals("1", interpreter.globalVariables.get("testEnd").getValue());
//    }



   
}
