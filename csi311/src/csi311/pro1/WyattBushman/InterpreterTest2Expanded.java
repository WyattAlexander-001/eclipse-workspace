package csi311.pro1.WyattBushman;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.LinkedList;

public class InterpreterTest2Expanded extends InterpreterTest2 {

    @Before
    public void setUp() throws Exception {
        super.setUp();
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
}
