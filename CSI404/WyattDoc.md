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

