public class Bit {
    private boolean value;
    
    // Default to false
    public Bit() {
        this.value = false;
    }
    // User can assign t/f
    public Bit(boolean value) {
        this.value = value;
    }
    
    // User can assign value after creation
    public void set(Boolean value) {
        this.value = value;
    }

    // Switch between t <-> f
    public void toggle() {
        if (this.value) {
            this.value = false;
        } else {
            this.value = true;
        }
    }
    
    //
    public void set() {
        this.value = true;
    }

    public void clear() {
        this.value = false;
    }

    //Simple getter...err.. accessor
    public Boolean getValue() {
        return this.value;
    }

    public Bit and(Bit other) {
        if (this.value) { // if true
            if (other.value) { // if value == true
                return new Bit(true);
            }
        }
        return new Bit(false);
    }

    // Soup or salad or both
    public Bit or(Bit other) {
        if (this.value) {
            return new Bit(true);
        }
        if (other.value) {
            return new Bit(true);
        }
        return new Bit(false);
    }

    // Only a soup OR only a salad
    public Bit xor(Bit other) {
        if (this.value) {
            if (other.value) {
                return new Bit(false);
            } else {
                return new Bit(true);
            }
        } else {
            if (other.value) {
                return new Bit(true);
            } else {
                return new Bit(false);
            }
        }
    }
    
    // Inverter gate
    public Bit not() {
        if (this.value) { 
            return new Bit(false);
        } else {
            return new Bit(true);
        }
    }

    @Override
    public String toString() {
        if (this.value) {
            return "t";
        } else {
            return "f";
        }
    }
    
    public boolean equals(Bit other) {
        return this.value == other.value;
    }
}
