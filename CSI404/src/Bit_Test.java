
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Bit_Test {
    private Bit testBit;

    @BeforeEach
    void setUp() {
        testBit = new Bit(); // Initializes a Bit object before each test, DEFAULT = false
    }

    @Test
    void testDefaultConstructor() {
        assertFalse(testBit.getValue(), "Default constructor should be false");
    }

    @Test
    void testParameterizedConstructorTrue() {
        Bit trueBit = new Bit(true);
        assertTrue(trueBit.getValue(), "Parameterized constructor with true should initialize with true");
    }

    @Test
    void testParameterizedConstructorFalse() {
        Bit falseBit = new Bit(false);
        assertFalse(falseBit.getValue(), "Parameterized constructor with false should initialize with false");
    }

    @Test
    void testSetTrue() {
        testBit.set(true);
        assertTrue(testBit.getValue(), "Set true should change the value to true");
    }

    @Test
    void testSetFalse() {
        testBit.set(false);
        assertFalse(testBit.getValue(), "Set false should change the value to false");
    }

    @Test
    void testToggle() {
        testBit.set(true);
        testBit.toggle();
        assertFalse(testBit.getValue(), "Toggling true should result in false");

        testBit.toggle();
        assertTrue(testBit.getValue(), "Toggling false should result in true");
    }

    @Test
    void testSetMethod() {
        testBit.set();
        assertTrue(testBit.getValue(), "Set method should change the value to true");
    }

    @Test
    void testClearMethod() {
        testBit.set();
        testBit.clear();
        assertFalse(testBit.getValue(), "Clear method should change the value to false");
    }

    @Test
    void testAndOperation() {
        testBit.set(true);
        Bit other = new Bit(true);
        assertTrue(testBit.and(other).getValue(), "AND of true and true should be true");

        other.set(false);
        assertFalse(testBit.and(other).getValue(), "AND of true and false should be false");
    }

    @Test
    void testOrOperation() {
        testBit.set(false);
        Bit other = new Bit(true);
        assertTrue(testBit.or(other).getValue(), "OR of false and true should be true");

        other.set(false);
        assertFalse(testBit.or(other).getValue(), "OR of false and false should be false");
    }

    @Test
    void testXorOperation() {
        testBit.set(true);
        Bit other = new Bit(false);
        assertTrue(testBit.xor(other).getValue(), "XOR of true and false should be true");

        other.set(true);
        assertFalse(testBit.xor(other).getValue(), "XOR of true and true should be false");
    }

    @Test
    void testNotOperation() {
        testBit.set(false);
        assertTrue(testBit.not().getValue(), "NOT of false should be true");

        testBit.set(true);
        assertFalse(testBit.not().getValue(), "NOT of true should be false");
    }

    @Test
    void testToString() {
        assertEquals("f", testBit.toString(), "toString should return 'f' for false");

        testBit.set(true);
        assertEquals("t", testBit.toString(), "toString should return 't' for true");
    }

    @Test
    void testEquals() {
        Bit other = new Bit();
        assertTrue(testBit.equals(other), "Two Bits initialized with false should be equal");

        other.set(true);
        assertFalse(testBit.equals(other), "Bits with different values should not be equal");
    }
}
