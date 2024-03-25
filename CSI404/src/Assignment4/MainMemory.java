package Assignment4;
public class MainMemory {
    private static final Word[] memory = new Word[1024]; // Static memory array

    // Static block for initializing the memory array
    static {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = new Word(); // Allocate a new Word for each memory slot
        }
    }

    // Method to read from memory
    public static Word read(Word address) {
        int index = (int) address.getUnsigned(); // Convert address to index
        if (index >= 0 && index < memory.length) {
            Word result = new Word();
            result.copy(memory[index]); // Copy the content from memory to a new Word object
            return result;
        }
        throw new IllegalArgumentException("Invalid memory address");
    }

    // Method to write to memory
    public static void write(Word address, Word value) {
        int index = (int) address.getUnsigned(); // Convert address to index
        if (index >= 0 && index < memory.length) {
            memory[index].copy(value); // Copy the value to the specified memory location
        } else {
            throw new IllegalArgumentException("Invalid memory address");
        }
    }

    // Method to load initial data into memory
    public static void load(String[] data) {
        for (int i = 0; i < data.length && i < memory.length; i++) {
            for (int j = 0; j < 32 && j < data[i].length(); j++) {
                boolean bitValue = data[i].charAt(j) == '1';
                memory[i].setBit(j, new Bit(bitValue)); // Set each bit according to the input string
            }
        }
    }
}
