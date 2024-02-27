import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainMemory_Test {

    @BeforeEach
    void setUp() {
        // Initialize memory with zeros
        String[] initData = new String[1024];
        for (int i = 0; i < initData.length; i++) {
            initData[i] = "00000000000000000000000000000000"; // 32 zeros
        }
        MainMemory.load(initData);
    }

    @Test
    void testReadAndWrite() {
        // Write a value to a specific address
        Word address = new Word();
        address.set(10); // Set address to 10
        Word valueToWrite = new Word();
        valueToWrite.setBit(0, new Bit(true)); // Set LSB to true
        MainMemory.write(address, valueToWrite);

        // Read the value from the same address
        Word valueRead = MainMemory.read(address);
        assertEquals(valueToWrite.toString(), valueRead.toString(), "Written and read values should match.");
    }
    
    @Test
    void testLoadInitialData() {
        // Load initial data into memory
        String[] data = {"1111000011110000", "0000111100001111"};
        MainMemory.load(data);

        // Verify the first word in memory matches the first data string
        Word firstWord = MainMemory.read(new Word()); // Address is 0 by default
        for (int i = 0; i < 16; i++) {
            assertEquals(data[0].charAt(i) == '1', firstWord.getBit(i).getValue(), "Bit mismatch at position " + i);
        }
    }
    
    @Test
    void testInvalidAddress() {
        // Test reading from an invalid address
        Word invalidAddress = new Word();
        invalidAddress.set(80000085); // Set address beyond the memory size
        assertThrows(IllegalArgumentException.class, () -> MainMemory.read(invalidAddress), "Should throw IllegalArgumentException for invalid read address.");
        // Test writing to an invalid address
        assertThrows(IllegalArgumentException.class, () -> MainMemory.write(invalidAddress, new Word()), "Should throw IllegalArgumentException for invalid write address.");
    }

}
