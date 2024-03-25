package Assignment4;
public class Processor {
    private Word pc = new Word(); // Program Counter, initialized to 0
    private Word sp = new Word(); // Stack Pointer, initialized to 1024
    private Word currentInstruction;  // Current instruction fetched
    private Bit halted; // Processor halted status
    
    private int rdIndex;
    
    private final ALU alu = new ALU();    
    private Word[] registers = new Word[31];
    
    private boolean _2R;
    private boolean _3R;
    private boolean destOnly;
    private boolean noR;
    
    private Word result;
    private final Bit[] function = new Bit[4];
    
    
    
	private Word opcode;
	private Word rd;
	private Word rs1;
	private Word rs2;
	private Word func;
	private Word immediate;

	private Word mask3bits= new Word(); //3 bits
	private Word mask4bits = new Word(); //4 bits
	private Word mask5bits = new Word(); //5 bit
    

    // Constructor to initialize PC and SP
    public Processor() {
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Word(); // Initialize all registers to prevent null
            registers[i].set(0); // Set all register values to 0, including R0
        }
        pc.set(0);
        sp.set(1024);
        currentInstruction = new Word();
        rdIndex = -1; // Initialize rdIndex to indicate it's not yet set
        halted = new Bit(false);
    }
    
    // Fetch the next instruction
    private void fetch() {
        currentInstruction.copy(MainMemory.read(pc)); // Fetch instruction at PC
        pc.increment(); // Increment PC for next instruction
    }

    // The main run loop of the processor
    public void run() throws Exception {
        while (!halted.getValue()) {
            fetch();
            decode();
            execute();
            store();
        }
    }



    private void decode() {
    	//Declaring Mask
    	//Apply mask to current instruction to get opcode
    	//Right shift by two to get rid of extra infomation
    	//nested if else which register ref you're looking at 00, 01, 10, 11
    	Word opcode;
    	//0000-0000-0000-0000-0000-0000-0000-1111
        mask4bits.setBit(31, new Bit(true));
        mask4bits.setBit(30, new Bit(true));
        mask4bits.setBit(29, new Bit(true));
        mask4bits.setBit(28, new Bit(true));
    	//0000-0000-0000-0000-0000-0000-0001-1111
        mask5bits.setBit(31, new Bit(true));
        mask5bits.setBit(30, new Bit(true));
        mask5bits.setBit(29, new Bit(true));
        mask5bits.setBit(28, new Bit(true));
        mask5bits.setBit(27, new Bit(true));
    	
    	_2R = false;
    	_3R = false;
    	destOnly = false;
    	noR = false;
    	
        opcode = currentInstruction.and(mask5bits);
        currentInstruction = currentInstruction.rightShift(5);
        
        // Check if the opcode is 00000 (HALT condition)
        if (opcode.toString() == "00000" || opcode.toString() == null) {
            halted.set(true);
            return;
        }
        
    	if(opcode.getBit(31).getValue() == false && opcode.getBit(30).getValue() == false) {
    		noR = true;	
    		noRegInst();
    	}else if(opcode.getBit(31).getValue() == false && opcode.getBit(30).getValue() == true) {
    		destOnly = true;
    		destOnlyInst();
    	}else if(opcode.getBit(31).getValue() == true && opcode.getBit(30).getValue() == false) {
    		_3R = true;
    		reg3Inst();
    	}else if(opcode.getBit(31).getValue() == true && opcode.getBit(30).getValue() == true) {
    		_2R = true;
    		reg2Inst();
    		
    	}else {
    		System.out.println("Impossible to reach it has to be 00,01,10,11 how did you get here?");
    	}
    	
    }

    private void execute() throws Exception {    	
    	if(opcode.getBit(27).getValue() == false && opcode.getBit(28).getValue() == false && opcode.getBit(29).getValue() == false) { //Math
    		System.out.println("Doing Math 000");
    		if(_2R == true) {
//                int rdIndex = wordToIndex(rd);
//                int rs1Index = wordToIndex(rs1);
//                alu.op1 = registers[rdIndex];
//                alu.op2 = registers[rs1Index];
//                Bit[] operation2R = funcToBits(func);
//                alu.doOperation(operation2R);
//                registers[rdIndex] = alu.result;
                alu.setOp1(rd);
                alu.setOp2(rs1);
                alu.doOperation(function);
                result = alu.getResult();
    			
    		}else if(_3R == true) {
//                int rdIndex = wordToIndex(rd);
//                int rs1Index = wordToIndex(rs1);
//                int rs2Index = wordToIndex(rs2);
//                alu.op1 = registers[rs1Index];
//                alu.op2 = registers[rs2Index];
//                Bit[] operation3R = funcToBits(func);
//                alu.doOperation(operation3R);
//                registers[rdIndex] = alu.result;
    			
                alu.setOp1(rs1);
                alu.setOp2(rs2);
                alu.doOperation(function);
                result = alu.getResult();
    			
    		}else if(destOnly == true) {
    			rd = immediate;
    		}else if(noR == true) {
    			halted.toggle();
  
    		}else {
    			System.out.println("BUT HOW THOUGH?? Math fail!");
    		}
    		 		
    	}else if(opcode.getBit(27).getValue() == false && opcode.getBit(28).getValue() == false && opcode.getBit(29).getValue() == true) { //Branch
    		System.out.println("Doing Branch 001");
    		if(_2R == true) {
    		}else if(_3R == true) {
    			
    		}else if(destOnly == true) {
    			
    		}else if(noR == true) {
    			
    		}else {
    			System.out.println("BUT HOW THOUGH?? Branch fail!");
    		}
    	}else if(opcode.getBit(27).getValue() == false && opcode.getBit(28).getValue() == true && opcode.getBit(29).getValue() == false) { //Push
    		System.out.println("Doing Call 010");
    		if(_2R == true) {

    		}else if(_3R == true) {
    			
    		}else if(destOnly == true) {
    			
    		}else if(noR == true) {
    			
    		}else {
    			System.out.println("BUT HOW THOUGH?? Call fail!");
    		}
    	}else if(opcode.getBit(27).getValue() == false && opcode.getBit(28).getValue() == true && opcode.getBit(29).getValue() == true) { //Load
    		System.out.println("Doing Push 100");
    		if(_2R == true) {
    		}else if(_3R == true) {
    			
    		}else if(destOnly == true) {
    			
    		}else if(noR == true) {
    			throw new Exception("UNUSED Push BRO!");
    		}else {
    			System.out.println("BUT HOW THOUGH?? Push fail!");
    		}
    	}else if(opcode.getBit(27).getValue() == true && opcode.getBit(28).getValue() == false && opcode.getBit(29).getValue() == false) { //Store
    		System.out.println("Doing Load 100");
    		if(_2R == true) {
    		}else if(_3R == true) {
    			
    		}else if(destOnly == true) {
    			
    		}else if(noR == true) {

    		}else {
    			System.out.println("BUT HOW THOUGH?? Load fail!");
    		}
    	}else if(opcode.getBit(27).getValue() == true && opcode.getBit(28).getValue() == false && opcode.getBit(29).getValue() == true) { //Pop / Interrupt
    		System.out.println("Doing Store 101");
    		if(_2R == true) {

    		}else if(_3R == true) {
    			
    		}else if(destOnly == true) {
    			
    		}else if(noR == true) {
    			throw new Exception("UNUSED Store BRO!");
    			
    		}else {
    			System.out.println("BUT HOW THOUGH?? Store fail!");
    		}
    	}else if(opcode.getBit(27).getValue() == true && opcode.getBit(28).getValue() == true && opcode.getBit(29).getValue() == false) { //Pop / Interrupt
    		System.out.println("Doing Pop/Interrupt 110");
    		if(_2R == true) {

    		}else if(_3R == true) {

    			
    		}else if(destOnly == true) {

    			
    		}else if(noR == true) {

    			
    		}else {
    			System.out.println("BUT HOW THOUGH?? Doing Po/Interrupt fail!");
    		}

    	}else {
    		System.out.println("JUST HOW??? Did you get 111 ????");
    	}
    	
    }

//    private Bit[] extractOperationFromFunc(Word func) {
//        String operationCode = func.toString().substring(28); // Get the last 4 bits
//
//        switch (operationCode) {
//            case "0000":
//                return new Bit[]{new Bit(false), new Bit(false), new Bit(false), new Bit(false)}; // Addition
//            case "0001":
//                return new Bit[]{new Bit(false), new Bit(false), new Bit(false), new Bit(true)}; // Subtraction
//            case "0010":
//                return new Bit[]{new Bit(false), new Bit(false), new Bit(true), new Bit(false)}; // Multiplication
//            case "0011":
//                return new Bit[]{new Bit(false), new Bit(false), new Bit(true), new Bit(true)}; // Division
//            default:
//                System.err.println("Unsupported operation code: " + operationCode);
//                return new Bit[]{new Bit(false), new Bit(false), new Bit(false), new Bit(false)}; // Return a default operation or throw an exception
//        }
//    }

    private void store() {
        if (!halted.getValue()) {
            Word result = alu.result; // Get the result from the ALU operation
            
            for (int i = 0; i < 32; i++) {
                registers[rdIndex].setBit(i, new Bit(result.getBit(i).getValue()));
            }
        }
    }


    
    //HELPER METHODS:
    public void reg2Inst() {
    	
		rd = currentInstruction.and(mask5bits);
		currentInstruction = currentInstruction.rightShift(5);
			
		func = currentInstruction.and(mask4bits);
		currentInstruction = currentInstruction.rightShift(4);
		rs1 = currentInstruction.and(mask5bits);
		currentInstruction = currentInstruction.rightShift(5);
			
		immediate = currentInstruction;
		rdIndex = wordToIndex(rd);
    	
    }
    
    public void reg3Inst(){
		rd = currentInstruction.and(mask5bits);
		currentInstruction = currentInstruction.rightShift(5);
		
		func = currentInstruction.and(mask4bits);
		currentInstruction = currentInstruction.rightShift(4);
		
		rs2 = currentInstruction.and(mask5bits);
		currentInstruction = currentInstruction.rightShift(5);
		
		rs1 = currentInstruction.and(mask5bits);
		currentInstruction = currentInstruction.rightShift(5);
		
		
		immediate = currentInstruction;
		rdIndex = wordToIndex(rd);
    	
    }
    
    public void destOnlyInst() {
		rd = currentInstruction.and(mask5bits);
		currentInstruction = currentInstruction.rightShift(5);
		
		func = currentInstruction.and(mask4bits);
		currentInstruction = currentInstruction.rightShift(4);
		
		immediate = currentInstruction;
		rdIndex = wordToIndex(rd);
    
    }
    
    public void noRegInst() {
		immediate = currentInstruction;    	
    }
    
    private Bit[] funcToBits(Word func) {
        Bit[] bits = new Bit[4];
        for (int i = 0; i < 4; i++) {
            bits[i] = func.getBit(31 - i); 
        }
        return bits;
    }
    
    private int wordToIndex(Word word) {
        if (word == null) {
            System.err.println("Attempted to convert a null Word to an index.");
            return -1; 
        }
        int index = 0;
        for (int i = 0; i < 5; i++) { 
            if (word.getBit(31 - i).getValue()) {
                index += (1 << i);
            }
        }
        return index;
    }
    
    public boolean isHalted() {
        return halted.getValue();
    }

    public Word[] getRegisters() {
        return registers;
    }

    public Word getPc() {
        return pc;
    }

    
    
    
    
    
    
    
    
    
    
}
