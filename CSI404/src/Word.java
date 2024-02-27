public class Word {
    private Bit[] bits;

    // Word with 32 bits set to false eg: 0000 0000 0000 0000 0000 0000 0000 0000
    public Word() {
        bits = new Bit[32];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new Bit(false);
        }
    }

    // Get a new Bit that has the same value as bit at index i 
    public Bit getBit(int i) {
        return new Bit(bits[i].getValue());
    }

    // Set bit at index i's value, value should be between 0-31
    public void setBit(int i, Bit value) {
        bits[i].set(value.getValue());
    }

    // Perform bitwise AND with another Word, returning a new Word
    public Word and(Word other) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].and(other.bits[i]));
        }
        return result;
    }

    // Perform bitwise OR with another Word, returning a new Word
    public Word or(Word other) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].or(other.bits[i]));
        }
        return result;
    }

    // Perform bitwise XOR with another Word, returning a new Word
    public Word xor(Word other) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].xor(other.bits[i]));
        }
        return result;
    }

    // Negate this word, creating a new Word
    public Word not() {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            result.setBit(i, this.bits[i].not());
        }
        return result;
    }

    // Right shift this word by amount bits, creating a new Word
    public Word rightShift(int amount) {
        Word result = new Word();
        for (int i = 0; i < 32; i++) {
            if (i + amount < 32) {
                result.setBit(i, this.bits[i + amount]);
            } else {
                result.setBit(i, new Bit(false)); // Fill with false beyond the original length
            }
        }
        return result;
    }

    // Left shift this word by amount bits, creating a new Word
    public Word leftShift(int amount) {
        Word result = new Word();
        for (int i = 31; i >= 0; i--) {
            if (i - amount >= 0) {
                result.setBit(i, this.bits[i - amount]);
            } else {
                result.setBit(i, new Bit(false)); // Fill with false beyond the original length
            }
        }
        return result;
    }

    // Returns a comma-separated string of t's and f's
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(bits[i].toString());
            if (i < 31) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // Returns the value of this word as a long (unsigned)
    public long getUnsigned() {
        long value = 0;
        for (int i = 0; i < 32; i++) {
            if (bits[i].getValue()) {
                value += (1L << (31 - i));
            }
        }
        return value;
    }

    // Returns the value of this word as an int (signed)
    public int getSigned() {
        int value = 0;
        for (int i = 0; i < 32; i++) {
            if (bits[i].getValue()) {
                value += (1 << (31 - i));
            }
        }
        return value;
    }

    // Copies the values of the bits from another Word into this one
    public void copy(Word other) {
        for (int i = 0; i < 32; i++) {
            this.setBit(i, other.getBit(i));
        }
    }

    // Set the value of the bits of this Word (used for tests)
    public void set(int value) {
        for (int i = 0; i < 32; i++) {
            boolean bitValue = (value & (1 << (31 - i))) != 0;
            this.setBit(i, new Bit(bitValue));
        }
    }
    
    public void increment() {
        Bit carry = new Bit(true);
        for (int i = 31; i >= 0 && carry.getValue(); i--) {
            Bit currentBit = this.getBit(i);
            Bit newBit = currentBit.xor(carry); // XOR the current bit with the carry, simulating addition
            carry = currentBit.and(carry); // Determine the new carry
            this.setBit(i, newBit); // Update the current bit with the result of the addition
        }
    }

}
