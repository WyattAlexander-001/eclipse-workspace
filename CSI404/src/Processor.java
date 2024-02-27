public class Processor {
    private Word pc = new Word(); // Program Counter, initialized to 0
    private Word sp = new Word(); // Stack Pointer, initialized to 1024
    private Word currentInstruction = new Word(); // Current instruction fetched
    private Bit halted = new Bit(false); // Processor halted status

    // Constructor to initialize PC and SP
    public Processor() {
        pc.set(0); // Assuming set() method sets the whole Word to represent the given integer
        sp.set(1024);
    }

    // The main run loop of the processor
    public void run() {
        while (!halted.getValue()) {
            fetch();
            decode();
            execute();
            store();
        }
    }

    // Fetch the next instruction
    private void fetch() {
        currentInstruction.copy(MainMemory.read(pc)); // Fetch instruction at PC
        pc.increment(); // Increment PC for next instruction
    }

    // Placeholder methods for decode, execute, and store operations
    private void decode() {
        // Implementation to be added
    }

    private void execute() {
        // Implementation to be added
    }

    private void store() {
        // Implementation to be added
    }
}
