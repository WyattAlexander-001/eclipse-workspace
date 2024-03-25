package Assignment4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Processor_Test {

    Processor processor;

    @BeforeEach
    void setUp() {
        processor = new Processor();
        // Load a halt instruction into memory. Assume "00000" as halt opcode, followed by fillers to complete 32 bits
        MainMemory.load(new String[]{"00000" + "0000000000000000000000000000"}); // Adjust the instruction as needed
    }

//    @Test
//    void testHaltCondition() throws Exception {
//        processor.run();
//        assertTrue(processor.isHalted(), "Processor did not halt as expected.");
//    }

    @Test
    void testRegisterInitialization() {
        // Assuming getUnsigned is a method you've added to Word class for test purposes
        for (int i = 0; i < processor.getRegisters().length; i++) {
            assertEquals(0, processor.getRegisters()[i].getUnsigned(), "Register " + i + " was not initialized to 0.");
        }
    }
	
	




}
