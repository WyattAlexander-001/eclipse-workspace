package csi311.pro1.WyattBushman;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class InterpreterTest2 {

    private Interpreter interpreter;

    @Before
    public void setUp() throws Exception {
        ProgramNode mockProgramNode = new ProgramNode(); 
        setInterpreter(new Interpreter(mockProgramNode, null)); 
    }

    @Test
    public void testConstructor() {
        assertNotNull(getInterpreter());
        assertTrue(getInterpreter().getLineManager().lines.isEmpty());
    }

    @Test
    public void testLineManagerLines() {
        Interpreter.LineManager lineManager = getInterpreter().new LineManager(Arrays.asList("line1", "line2", "line3"));
        assertEquals(3, lineManager.lines.size());
        assertEquals("line1", lineManager.lines.get(0));
    }

    @Test
    public void testGlobalVariablesDefaults() {
        assertEquals(" ", getInterpreter().globalVariables.get("FS").getValue());
        assertEquals("%.6g", getInterpreter().globalVariables.get("OFMT").getValue());
        assertEquals(" ", getInterpreter().globalVariables.get("OFS").getValue());
        assertEquals("\n", getInterpreter().globalVariables.get("ORS").getValue());
    }

    @Test
    public void testFunctionDefinitions() {
        assertTrue(getInterpreter().functionDefinitions.containsKey("print"));
        assertTrue(getInterpreter().functionDefinitions.containsKey("next"));
        assertTrue(getInterpreter().functionDefinitions.containsKey("toLower"));
        assertTrue(getInterpreter().functionDefinitions.containsKey("split"));
    }

	public Interpreter getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;
	}

}
