
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Word_Test {
    private Word word;

    @BeforeEach
    void setUp() {
        word = new Word(); 
    }
    
    @Test
    void testDefaultConstructor() {
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", word.toString(), "Default constructor should initialize with all bits set to false");
    }

    @Test
    void testGetAndSetBit() {
        Bit testBit = new Bit(true);
        word.setBit(5, testBit);
        assertTrue(word.getBit(5).getValue(), "Bit at index 5 should be true after setting");

        testBit.set(false);
        word.setBit(10, testBit);
        assertFalse(word.getBit(10).getValue(), "Bit at index 10 should be false after setting");
    }

    @Test
    void testAndOperation() {
        word.setBit(5, new Bit(true));
        Word otherWord = new Word();
        otherWord.setBit(5, new Bit(true));


        Word result = word.and(otherWord);
        assertTrue(result.getBit(5).getValue(), "AND operation should result in true at index 5");

        result = word.and(new Word()); // All false
        assertFalse(result.getBit(5).getValue(), "AND operation with all false word should result in false");
    }

    @Test
    void testOrOperation() {
        word.setBit(10, new Bit(true));
        Word otherWord = new Word();
        otherWord.setBit(5, new Bit(true));


        Word result = word.or(otherWord);
        assertTrue(result.getBit(5).getValue(), "OR operation should result in true at index 5");
        assertTrue(result.getBit(10).getValue(), "OR operation should result in true at index 10");
    }

    @Test
    void testXorOperation() {
        word.setBit(5, new Bit(true));
        Word otherWord = new Word();
        otherWord.setBit(5, new Bit(true));


        Word result = word.xor(otherWord);
        assertFalse(result.getBit(5).getValue(), "XOR operation should result in false at index 5 where both bits are true");
    }

    @Test
    void testNotOperation() {
        word.setBit(5, new Bit(true));
        Word result = word.not();
        assertFalse(result.getBit(5).getValue(), "NOT operation should invert bit at index 5 to false");
    }
    
    @Test
    void testRightShiftOperation() {
        word.setBit(5, new Bit(true));
        Word result = word.rightShift(2);
        assertTrue(result.getBit(3).getValue(), "Right shift by 2 should move true bit from index 5 to 3");
        
        word.setBit(31, new Bit(true));
        result = word.rightShift(2);
        assertTrue(result.getBit(29).getValue(), "Right shift by 2 should move true bit from index 31 to 29");
        
        word.setBit(9, new Bit(true));
        result = word.rightShift(4);
        assertTrue(result.getBit(5).getValue(), "Right shift by 4 should move true bit from index 9 to 4");
        
        word.setBit(9, new Bit(true));
        result = word.rightShift(3);
        assertTrue(result.getBit(6).getValue(), "Right shift by 3 should move true bit from index 9 to 6");
    }
    
    @Test
    void testLeftShiftOperation() {
        word.setBit(5, new Bit(true));
        Word result = word.leftShift(2);
        assertTrue(result.getBit(7).getValue(), "Left shift by 2 should move true bit from index 5 to 7");
        
        word.setBit(12, new Bit(true));
        result = word.leftShift(4);
        assertTrue(result.getBit(16).getValue(), "Left shift by 4 should move true bit from index 12 to 16");
    }

    @Test
    void testToString() {
        word.set(13); // Binary representation of 13 is 0000 0000 0000 0000 0000 0000 0000 1101
        String binary13 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,f,t";
        assertEquals(binary13, word.toString(), "toString should correctly represent the state of the word for the binary value 13");
        word.set(0);
        String binary0 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f";
        assertEquals(binary0, word.toString(), "toString should correctly represent the state of the word for the binary value 0");
        word.set(1);
        String binary1 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t";
        assertEquals(binary1, word.toString(), "toString should correctly represent the state of the word for the binary value 0");
        word.set(64);
        String binary64 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,f,f,f";
        assertEquals(binary64, word.toString(), "toString should correctly represent the state of the word for the binary value 64");

        // Testing binary representation of 128
        word.set(128);
        String binary128 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,f,f,f,f";
        assertEquals(binary128, word.toString(), "toString should correctly represent the state of the word for the binary value 128");

        // Testing binary representation of 256
        word.set(256);
        String binary256 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,f,f,f,f,f";
        assertEquals(binary256, word.toString(), "toString should correctly represent the state of the word for the binary value 256");

        // Testing binary representation of 666
        word.set(666);
        String binary666 = "f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,t,f,f,t,t,f,t,f";
        assertEquals(binary666, word.toString(), "toString should correctly represent the state of the word for the binary value 666");

        // Testing binary representation of Integer.MAX_VALUE (2,147,483,647) Signed
        word.set(Integer.MAX_VALUE);
        String binaryMaxInt = "f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t";
        assertEquals(binaryMaxInt, word.toString(), "toString should correctly represent the state of the word for the binary value Integer.MAX_VALUE");
    }

    @Test
    void testGetUnsigned() {
        // Test with all bits set to false (0)
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(false));
        }
        assertEquals(0, word.getUnsigned(), "getUnsigned should be 0 for all bits false");

        // Test with all bits set to true (Maximum unsigned 32-bit integer value)
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true));
        }
        assertEquals(4294967295L, word.getUnsigned(), "getUnsigned should be 4294967295 for all bits true");

        // Test with a mixed pattern
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit((i % 2) == 0)); // Set alternating bits
        }
        long expectedValue = 0L;
        for (int i = 0; i < 32; i++) {
            if ((i % 2) == 0) {
                expectedValue |= (1L << (31 - i));
            }
        }
        assertEquals(expectedValue, word.getUnsigned(), "getUnsigned should match the pattern for alternating bits");
        assertEquals(2863311530L, word.getUnsigned(), "getUnsigned should match the pattern for alternating bits");
        
        word.set(12345);
        assertEquals(12345, word.getUnsigned(), "getUnsigned should correctly represent the unsigned 32-bit integer value for 12345");        
    }



    @Test
    void testGetSigned() {
        // Test with a positive value within the range of signed 32-bit integers
        int testValue = Integer.MAX_VALUE;
        word.set(testValue);
        assertEquals(testValue, word.getSigned(), "getSigned should correctly represent the signed 32-bit integer value");

        // Test with a negative value
        testValue = Integer.MIN_VALUE;
        word.set(testValue);
        assertEquals(testValue, word.getSigned(), "getSigned should correctly represent the signed 32-bit integer value");

        // Test with a smaller positive value
        testValue = 123456;
        word.set(testValue);
        assertEquals(testValue, word.getSigned(), "getSigned should correctly represent the signed 32-bit integer value");
        
        // Test with a small negative value -50
        testValue = -50;
        word.set(testValue);
        assertEquals(testValue, word.getSigned(), "getSigned should correctly represent the signed 32-bit integer value for -50");

        // Test with a larger negative value -777
        testValue = -777;
        word.set(testValue);
        assertEquals(testValue, word.getSigned(), "getSigned should correctly represent the signed 32-bit integer value for -777");

        // Test with zero
        testValue = 0;
        word.set(testValue);
        assertEquals(testValue, word.getSigned(), "getSigned should correctly represent the signed 32-bit integer value for 0");
    }


    @Test
    void testCopy() {
        Word otherWord = new Word();
        otherWord.setBit(13, new Bit(true));
        word.copy(otherWord);
        assertTrue(word.getBit(13).getValue(), "copy should correctly copy the state from another word");
    }

    @Test
    void testSet() {
        word.set(1); 
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t", word.toString(), "set should correctly set the word value from an integer");
        word.set(2); 
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f", word.toString(), "set should correctly set the word value from an integer");
        word.set(8);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f", word.toString(), "set should correctly set the word value from an integer");
        
        word.set(9);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,t", word.toString(), "set should correctly set the word value from an integer");
        word.set(9); // Sets the value to 9
        assertEquals(9, word.getUnsigned(), "getUnsigned should return 9 when the word is set with 9");
        
        word.set(16);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,f", word.toString(), "set should correctly set the word value for 16");
        
        // Test with a negative value -1 (all bits set)
        word.set(-1);
        String allTrue = String.join(",", Collections.nCopies(32, "t"));
        assertEquals(allTrue, word.toString(), "set should correctly set the word value for -1");
        word.set(-1); // Sets all bits to true, as -1 in two's complement is represented by all 1s
        String allTrueLongWay = "t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t";
        assertEquals(allTrueLongWay, word.toString(), "set should correctly set the word value for -1");
        word.set(-1); // Sets all bits to true, which represents -1 in two's complement
        assertEquals(-1, word.getSigned(), "getSigned should return -1 when all bits are set to true");
        
        // Test with 0 
        word.set(0);
        String allFalse = String.join(",", Collections.nCopies(32, "f"));
        assertEquals(allFalse, word.toString(), "set should correctly set the word value for 0");
        word.set(0);
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", word.toString(), "set should correctly set the word value from an integer");
    }
    
    @Test
    void testSetWithZero() {
        word.set(0); // Sets all bits to false
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", word.toString(), "set with 0 should correctly set all bits to false");
    }
    
    @Test
    void testIncrement() {
        // Test incrementing from 0
        word.set(0); // Initialize word to 0
        word.increment();
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t", word.toString(), "Incrementing from 0 should result in 1");

        // Test incrementing a mid-range value
        word.set(8); // Set word to represent the value 8
        word.increment();
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,t", word.toString(), "Incrementing from 8 should result in 9");
        
        // Test incrementing with carry
        word.set(15); // 0b...1111
        word.increment();
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,f,f,f,f", word.toString(), "Incrementing from 15 should result in 16");

        // Test the edge case of overflow (e.g., from 0xFFFFFFFF to 0x00000000)
        word.set(-1); // -1 represents all bits set in two's complement, equivalent to 0xFFFFFFFF
        word.increment();
        assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", word.toString(), "Incrementing from 0xFFFFFFFF should wrap around to 0");
    }
    
    @Test
    void testIncrementOverflows() {
        // Set word to maximum value before overflow
        for (int i = 0; i < 32; i++) {
            word.setBit(i, new Bit(true)); // 0xFFFFFFFF
        }
        word.increment(); // This should wrap to 0
        
        // Build expected result string for 0
        String expectedResult = String.join(",", Collections.nCopies(32, "f"));
        
        assertEquals(expectedResult, word.toString(), "Incrementing maximum unsigned value should wrap to 0");
    }
    
    

}

