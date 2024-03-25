
public class Processor {
    private Word pc = new Word(); // Program Counter, initialized to 0
    private Word sp = new Word(); // Stack Pointer, initialized to 1024
    private Word currentInstruction;  // Current instruction fetched
    private Bit halted; // Processor halted status

    // Constructor to initialize PC and SP
    public Processor() {
        pc.set(0);
        sp.set(1024);
        currentInstruction = new Word();
        halted = new Bit(false);
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

    private void decode() {
    	//Declaring Mask
    	//Apply mask to current instruction to get opcode
    	//Right shift by two to get rid of extra infomation
    	//nested if else which register ref you're looking at 00, 01, 10, 11
    	
    	Word opcode;
    	Word mask = new Word();
    	
    	// Make a Word that is 0000-0000-0000-0000-0000-0000-0000-0011
    	mask.setBit(31, new Bit(true));
    	mask.setBit(30, new Bit(true));
    	

        switch (opcode) {
            case 00:
                // Opcode 00: Do something
                break;
            case 01:
                // Opcode 01: Do something else
                break;
            case 10:
                // Opcode 10: Another operation
                break;
            case 11:
                // Opcode 11: Yet another operation
                break;
            default:
                // Handle unexpected opcode
                break;
        }
    }

    private void execute() {
    }

    private void store() {
    }
}
