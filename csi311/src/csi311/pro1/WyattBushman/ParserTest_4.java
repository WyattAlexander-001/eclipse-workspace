package csi311.pro1.WyattBushman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParserTest_4 {

	@Before
	public void setUp() throws Exception {
	}


    @Test
    public void testParseIf() {
        String testInput = "if (condition) { statement; }";
        Lexer lexer = new Lexer(testInput);
        Parser parser = new Parser(lexer);

        assertDoesNotThrow(() -> {
            parser.ParseIf();
        });
}
