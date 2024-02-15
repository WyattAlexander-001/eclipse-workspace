# Wyatt Documentation Of Learning 404

Assignment 1:

I could have done this for the Bit Class:

Eg:

//And written explicitly:

public Bit and(Bit other) {
    if (this.value && other.value) {
        return new Bit(true);
    } else {
        return new Bit(false);
    }
}

// What I wrote instead:

public Bit and(Bit other) {
	return new Bit(this.value && other.value);
}

// Bit Class If I had freedom


public class Bit {
    private boolean value;

    public Bit() {
        this.value = false;
    }

    public Bit(boolean value) {
        this.value = value;
    }
    
    // Sets to true or false depending on arg
    public void set(Boolean value) {
        this.value = value;
    }
    
    // Sets the bit to true
    public void set() {
        this.value = true;
    }
    // Changes the value from true to false or false to true
    public void toggle() {
        this.value = !this.value;
    }
    
    // sets bit to false
    public void clear() {
        this.value = false;
    }

    //Regular getter (accessor)
    public Boolean getValue() {
        return this.value;
    }

    public Bit and(Bit other) {
        return new Bit(this.value && other.value);
    }

    public Bit or(Bit other) {
        return new Bit(this.value || other.value);
    }

    public Bit xor(Bit other) {
        return new Bit((this.value || other.value) && !(this.value && other.value));
    }

    public Bit not() {
        return new Bit(!this.value);
    }

    @Override
    public String toString() {
        return this.value ? "t" : "f";
    }
}



Bit Shifting:
8 4 2 1
0 0 1 1 = 3 is base
Left shift, shift all the numbers, left shift once is x2
0 1 1 0 = 6 x2 from base
1 1 0 0 = 12 x4 from base


# Personal Documentation:


# Class Bit
Variables and Constructors:
Holds a single binary value (true or false) representing a bit.
Provides two constructors: one for initializing the bit to false by default and another that allows setting the initial value.

## Methods for Manipulating Bits:

set(Boolean value): Allows changing the bit's value after it's been created.
toggle(): Flips the bit's value from true to false or vice versa.
set(): Sets the bit's value to true.
clear(): Resets the bit's value to false.
getValue(): Returns the current value of the bit (accessor method).

## Logical Operations:
Implements basic logical operations (and, or, xor, not) that return new Bit instances based on the operation's result. These methods simulate the behavior of logical gates used in digital electronics.

## Utility Methods:

toString(): Provides a string representation of the bit ("t" for true, "f" for false).
equals(Bit other): Compares the current bit with another to check if they have the same value.

# Class Word
Variables and Constructor:
Represents a 32-bit word using an array of Bit objects, initially all set to false.

Bit Manipulation:
getBit(int i): Returns a new Bit with the value of the bit at index i.
setBit(int i, Bit value): Sets the value of the bit at index i to the specified value.

Word-level Logical Operations:
Implements bitwise logical operations (and, or, xor, not) on words, returning a new Word instance as the result. Each bit in the result is computed based on the corresponding operation between the bits at the same positions in the two words.

Shift Operations:
rightShift(int amount): Shifts the word to the right by the specified number of bits, filling vacated bits with false.

leftShift(int amount): Shifts the word to the left by the specified number of bits, also filling vacated bits with false.

Utility Methods:

toString(): Returns a comma-separated string of the bits' string representations.

getUnsigned(): Computes and returns the unsigned long value represented by the word.

getSigned(): Computes and returns the signed integer value represented by the word.

copy(Word other): Copies the bit values from another Word instance into this one.

set(int value): Sets the value of the bits based on the provided integer value, useful for initialization or testing.




