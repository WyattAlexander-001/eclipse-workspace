package csi311.pro1.WyattBushman;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class InterpreterTest {
    private Interpreter interpreter;
    private Interpreter.LineManager lineManager;

    @Before
    public void setUp() throws Exception {
        ProgramNode dummyNode = new ProgramNode(); 
        interpreter = new Interpreter(dummyNode, null);
        lineManager = interpreter.new LineManager(Arrays.asList("line1", "line2", "line3"));
        interpreter.setLineManager(lineManager);
    }

    @Test
    public void testBuiltInFunctions() {
        assertNotNull(interpreter.functionDefinitions.get("print"));
        assertNotNull(interpreter.functionDefinitions.get("printf"));
        assertNotNull(interpreter.functionDefinitions.get("toLower"));
        assertNotNull(interpreter.functionDefinitions.get("toUpper"));
        assertNotNull(interpreter.functionDefinitions.get("next"));
    }

    @Test
    public void testLineManagerSplitAndAssign() {
        assertTrue(lineManager.splitAndAssign());
        assertEquals("line1", interpreter.globalVariables.get("$0").getValue());
        
        assertTrue(lineManager.splitAndAssign());
        assertEquals("line2", interpreter.globalVariables.get("$0").getValue());

        assertTrue(lineManager.splitAndAssign());
        assertEquals("line3", interpreter.globalVariables.get("$0").getValue());

        assertFalse(lineManager.splitAndAssign());
    }
    
    
}
