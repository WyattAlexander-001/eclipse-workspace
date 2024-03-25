
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
    
    @Test
    void testBoundaryConditions() {
        Word firstAddress = new Word();
        firstAddress.set(0); // First address
        Word lastAddress = new Word();
        lastAddress.set(1023); // Last address
        
        Word valueFirst = new Word();
        valueFirst.setBit(0, new Bit(true)); // Set value for first address
        Word valueLast = new Word();
        valueLast.setBit(31, new Bit(true)); // Set value for last address
        
        MainMemory.write(firstAddress, valueFirst);
        MainMemory.write(lastAddress, valueLast);
        
        assertEquals(valueFirst.toString(), MainMemory.read(firstAddress).toString(), "First address value mismatch.");
        assertEquals(valueLast.toString(), MainMemory.read(lastAddress).toString(), "Last address value mismatch.");
    }
    
    @Test
    void testOverwritingValues() {
        Word address = new Word();
        address.set(100); // Arbitrary address
        
        Word initialValue = new Word();
        initialValue.setBit(1, new Bit(true)); // Initial value
        MainMemory.write(address, initialValue);
        
        Word newValue = new Word();
        newValue.setBit(1, new Bit(false)); // New value to overwrite
        MainMemory.write(address, newValue);
        
        assertEquals(newValue.toString(), MainMemory.read(address).toString(), "Overwrite value mismatch.");
    }
    
    @Test
    void testDataIntegrityOverMultipleOperations() {
        Word addressOne = new Word();
        addressOne.set(20); // First address
        Word addressTwo = new Word();
        addressTwo.set(21); // Second address
        
        Word valueOne = new Word();
        valueOne.setBit(2, new Bit(true)); // Value for first address
        Word valueTwo = new Word();
        valueTwo.setBit(3, new Bit(true)); // Value for second address
        
        MainMemory.write(addressOne, valueOne);
        MainMemory.write(addressTwo, valueTwo);
        
        assertEquals(valueOne.toString(), MainMemory.read(addressOne).toString(), "Data integrity mismatch for first address.");
        assertEquals(valueTwo.toString(), MainMemory.read(addressTwo).toString(), "Data integrity mismatch for second address.");
    }


    
    


}
