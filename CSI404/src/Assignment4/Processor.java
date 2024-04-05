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
    private Bit[] function = new Bit[4];
    
    
    
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
    	    System.out.println("Executing Branch 001");
    	    
    	    // Initially, there is no branch
    	    boolean branchCondition = false;
            int rs1Index = wordToIndex(rs1);
            int rs2Index = wordToIndex(rs2);
    	    
    	    // Set up ALU for subtraction to simulate comparison
    	    alu.setOp1(registers[rs1Index]);
    	    alu.setOp2(registers[rs2Index]); 
    	    // Perform subtraction
    	    alu.doOperation(new Bit[]{new Bit(true), new Bit(true), new Bit(true), new Bit(true)}); 
    	    
    	    // Interpret the result of the subtraction
    	    long result = alu.getResult().getSigned();
    	    // Example condition: proceed with branch if result of subtraction is less than 0 (rs1 < rs2)
    	    branchCondition = (result < 0); 
    	    
    	    if (branchCondition) {
    	        // Calculate the branch address based on immediate value
    	        Word branchAddress = alu.add2(pc, immediate);
    	        pc.copy(branchAddress);
    	    } else if (destOnly) {
    	        // Unconditional jump, increment PC by immediate
    	        Word branchAddress = alu.add2(pc, immediate);
    	        pc.copy(branchAddress);
    	    } else if (noR) {
    	        // Unconditional jump to immediate address
    	        pc.copy(immediate);
    	    }
    	}else if(opcode.getBit(27).getValue() == false && opcode.getBit(28).getValue() == true && opcode.getBit(29).getValue() == false) { 
    		System.out.println("Doing Call 010");
    		// Evaluate the branch condition for 2R and 3R format
    	    boolean branchCondition = false;
    	    if (_2R || _3R) {
    	        function = funcToBits(func); // Get the operation bits
    	        // Set rs1 and rs2 for the ALU
    	        int rs1Index = wordToIndex(rs1);
    	        int rs2Index = _2R ? rdIndex : wordToIndex(rs2); // For 2R, the second operand is 'rd', for 3R it is 'rs2'
    	        alu.setOp1(registers[rs1Index]);
    	        alu.setOp2(registers[rs2Index]);
    	        branchCondition = evaluateBranchCondition(registers[rs1Index], registers[rs2Index], function);
    	    }
    	    
    	    // Push the current PC onto the stack if the condition is true for 2R/3R or unconditionally for DestOnly/NoR
    	    if ((branchCondition && (_2R || _3R)) || destOnly || noR) {
    	        push(pc);
    	    }

    	    // Calculate the new PC value
    	    if (_2R || _3R) {
    	        if (branchCondition) {
    	            // Add immediate to PC if the branch condition is true
    	            Word offset = immediate; // Immediate is the offset
    	            Word newPc = alu.add2(pc, offset);
    	            pc.copy(newPc);
    	        } // else, PC remains the same
    	    } else if (destOnly) {
    	        // In DestOnly, add immediate to Rd
    	        alu.setOp1(registers[rdIndex]);
    	        alu.setOp2(immediate);
    	        alu.doOperation(new Bit[]{new Bit(false), new Bit(true), new Bit(true), new Bit(false)}); // This should be the equivalent of ALU add operation
    	        Word newPc = alu.getResult();
    	        pc.copy(newPc);
    	    } else if (noR) {
    	        // In NoR, PC becomes the immediate value
    	        pc.copy(immediate);
    	    }else {
    	    	throw new IllegalStateException("Invalid Call instruction format");
    	    }
    	}else if(opcode.getBit(27).getValue() == false && opcode.getBit(28).getValue() == true && opcode.getBit(29).getValue() == true) { 
    		System.out.println("Doing Push 011");
    	    System.out.println("Doing Push 011");
    	    if (_2R) {
    	        // mem[--sp] <- Rd MOP Rs
    	        int rdIndex = wordToIndex(rd); // Convert 'rd' Word to index.
    	        int rs1Index = wordToIndex(rs1); // Convert 'rs1' Word to index.
    	        alu.setOp1(registers[rdIndex]); // Set 'Rd' as the first operand.
    	        alu.setOp2(registers[rs1Index]); // Set 'Rs' as the second operand.
    	        alu.doOperation(function); // Perform the MOP.
    	        sp.decrement(); // Decrement the stack pointer.
    	        MainMemory.write(sp, alu.getResult()); // Store the result of MOP into the stack.
    	    } else if (_3R) {
    	        // mem[--sp] <- Rs1 MOP Rs2
    	        int rs1Index = wordToIndex(rs1); // Convert 'rs1' Word to index.
    	        int rs2Index = wordToIndex(rs2); // Convert 'rs2' Word to index.
    	        alu.setOp1(registers[rs1Index]); // Set 'Rs1' as the first operand.
    	        alu.setOp2(registers[rs2Index]); // Set 'Rs2' as the second operand.
    	        alu.doOperation(function); // Perform the MOP.
    	        sp.decrement(); // Decrement the stack pointer.
    	        MainMemory.write(sp, alu.getResult()); // Store the result of MOP into the stack.
    	    } else if (destOnly) {
    	    	// Perform the MOP operation between Rd and immediate
    	        function = funcToBits(func); // Get the operation bits
    	        alu.setOp1(registers[rdIndex]); // Set Rd as the first operand
    	        alu.setOp2(immediate); // Immediate value as the second operand
    	        alu.doOperation(function); // Perform the operation
    	        Word result = alu.getResult(); // Get the result of the operation

    	        sp.decrement(); // Decrement the stack pointer
    	        MainMemory.write(sp, result); // Write the result to the stack
    	    } else if (noR) {
    	        // NoR format is not used for the push operation.
    	        System.err.println("NoR format is not applicable for Push operation.");
    	    } else {
    	        System.out.println("Invalid format for Push operation!");
    	    }
    	}else if(opcode.getBit(27).getValue() == true && opcode.getBit(28).getValue() == false && opcode.getBit(29).getValue() == false) { 
    		System.out.println("Doing Load 100");
    	    if (_2R) {
    	        // Rd <- mem[Rs + imm]
    	        int rsIndex = wordToIndex(rs1); // Rs is the base register for the address.
    	        Word address = alu.add2(registers[rsIndex], immediate); // Calculate the effective address.
    	        load(registers[rdIndex], address); // Load the value into Rd.
    	    } else if (_3R) {
    	        // Rd <- mem[Rs1 + Rs2]
    	        int rs1Index = wordToIndex(rs1); // Rs1 is the first part of the address.
    	        int rs2Index = wordToIndex(rs2); // Rs2 is the second part of the address.
    	        Word address = alu.add2(registers[rs1Index], registers[rs2Index]); // Calculate the effective address.
    	        load(registers[rdIndex], address); // Load the value into Rd.
    	    } else if (destOnly) {
    	        // Rd <- mem[Rd + imm]
    	        Word address = alu.add2(registers[rdIndex], immediate); // Calculate the effective address.
    	        load(registers[rdIndex], address); // Load the value into Rd.
    	    } else if (noR) {
    	        // Return (pc <- pop)
    	        ret(); // Perform the return operation.
    	    } else {
    	        System.out.println("Invalid format for Load operation!");
    	    }
    	}else if(opcode.getBit(27).getValue() == true && opcode.getBit(28).getValue() == false && opcode.getBit(29).getValue() == true) {
    		System.out.println("Doing Store 101");
    	    if (_2R) {
    	        // mem[Rd + imm] <- Rs
    	        int rdIndex = wordToIndex(rd); // Rd is the base register for the address.
    	        int rsIndex = wordToIndex(rs1); 
    	        // Calculate the address by adding the immediate value to the contents of Rd.
    	        Word address = alu.add2(registers[rdIndex], immediate);
    	        // Perform the store operation.
    	        MainMemory.write(address, registers[rsIndex]);
    	    } else if (_3R) {
    	        // Mem[Rd + Rs1] <- Rs2
    	        int rdIndex = wordToIndex(rd); // Rd is the base register for the address.
    	        int rs1Index = wordToIndex(rs1); // Rs1 is the offset register.
    	        int rs2Index = wordToIndex(rs2); // Rs2 is the source register containing the data to store.
    	        // Calculate the address by adding the contents of Rs1 to the contents of Rd.
    	        Word address = alu.add2(registers[rdIndex], registers[rs1Index]);
    	        // Perform the store operation.
    	        MainMemory.write(address, registers[rs2Index]);
    	    } else if (destOnly) {
    	        // This format is not supported for store operation.
    	        throw new Exception("DestOnly format is not supported for Store operation.");
    	    } else if (noR) {
    	        // Return (pc <- pop)
    	        ret(); // Perform the return operation by popping the top of the stack into the PC.
    	    } else {
    	        System.out.println("Invalid format for Store operation!");
    	    }

    	}else if(opcode.getBit(27).getValue() == true && opcode.getBit(28).getValue() == true && opcode.getBit(29).getValue() == false) { 
    		System.out.println("Doing Pop/Interrupt 110");
    	    if(_2R) {
    	        // Peek with immediate as offset: Rd <- mem[sp - (Rs + imm)]
    	        int rsIndex = wordToIndex(rs1);
    	        Word rsValue = registers[rsIndex];
    	        Word offset = alu.add2(rsValue, immediate); // Calculate the effective address.
    	        Word peekValue = peek(offset); // Perform the peek operation.
    	        registers[rdIndex].copy(peekValue); // Store the peeked value into Rd.
    	    } else if(_3R) {
    	        // Peek with Rs1 and Rs2 as offset: Rd <- mem[sp - (Rs1 + Rs2)]
    	        int rs1Index = wordToIndex(rs1);
    	        int rs2Index = wordToIndex(rs2);
    	        Word offset = alu.add2(registers[rs1Index], registers[rs2Index]); // Calculate the effective address.
    	        Word peekValue = peek(offset); // Perform the peek operation.
    	        registers[rdIndex].copy(peekValue); // Store the peeked value into Rd.
    	    } else if(destOnly) {
    	        // Pop: Rd <- mem[sp++]
    	        pop(registers[rdIndex]); // Popping value from stack to Rd.
    	    } else if(noR) {
    	        push(pc); 
    	        pc.copy(immediate); // Jumping to interrupt vector address.
    	    } else {
    	        System.out.println("Invalid format for Pop/Interrupt operation.");
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
    
    private boolean evaluateBranchCondition(Word rs1, Word rs2, Bit[] bopBits) {
        String bopStr = "" + (bopBits[0].getValue() ? "1" : "0") +
                            (bopBits[1].getValue() ? "1" : "0") +
                            (bopBits[2].getValue() ? "1" : "0") +
                            (bopBits[3].getValue() ? "1" : "0");

        long rs1Value = rs1.getUnsigned();
        long rs2Value = rs2.getUnsigned();
        int rs1Signed = rs1.getSigned();
        int rs2Signed = rs2.getSigned();

        switch (bopStr) {
            case "0000": // Equals
                return rs1Value == rs2Value;
            case "0001": // Not equals
                return rs1Value != rs2Value;
            case "0010": // Less than
                return rs1Signed < rs2Signed;
            case "0011": // Greater than or equal
                return rs1Signed >= rs2Signed;
            case "0100": // Greater than
                return rs1Signed > rs2Signed;
            case "0101": // Less than or equal
                return rs1Signed <= rs2Signed;
            // Add additional cases for other BOP codes as necessary
            default:
                throw new IllegalArgumentException("Unsupported BOP: " + bopStr);
        }
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
    
    private void call() {
        // Push current PC to the stack
        sp.decrement();
        MainMemory.write(sp, pc);

        // Set PC to the new address (based on Rd or immediate value)
        if (_3R || _2R) {
            pc.copy(registers[rdIndex]);
        } else if (destOnly) {
            pc.copy(immediate);
        }
        // _3R and _2R versions use Rd to calculate the new PC address, while destOnly uses immediate value
    }
    
    private void ret() {
        // Pop the top of the stack into PC
        pc.copy(MainMemory.read(sp));
        sp.increment();
    }
    
    private void push(Word value) {
        sp.decrement();
        MainMemory.write(sp, value);
    }
    
    private void pop(Word destination) {
        destination.copy(MainMemory.read(sp));
        sp.increment();
    }
    
    private void load(Word destination, Word offset) {
        // Set the operands for the ALU
        alu.setOp1(registers[rdIndex]);
        alu.setOp2(offset);

        // Perform the addition
        alu.doOperation(new Bit[]{new Bit(false), new Bit(true), new Bit(true), new Bit(false)});
        // Read the value from memory at the calculated address and copy it into the destination register
        destination.copy(MainMemory.read(alu.getResult()));
    }
    
    //Bypass version of add2
//    private void load(Word destination, Word offset) {
//        // Calculate the effective address by adding the base register and the offset
//        Word address = alu.add2(registers[rdIndex], offset);
//
//        // Use the effective address to load the value into the destination register
//        destination.copy(MainMemory.read(address));
//    }
    
    private void store(Word source, Word offset) {
        // Set operands for the ALU
        alu.setOp1(registers[rdIndex]);
        alu.setOp2(offset);
        
        // Perform the addition to get the effective address
        alu.doOperation(new Bit[]{new Bit(false), new Bit(true), new Bit(true), new Bit(false)});
        
        // Store the value into the calculated memory address
        MainMemory.write(alu.getResult(), source);
    }
    
    private Word peek(Word offset) {
        // Set operands for the ALU
        alu.setOp1(sp);
        alu.setOp2(offset);
        
        // Perform the addition to get the effective address
        alu.doOperation(new Bit[]{new Bit(false), new Bit(true), new Bit(true), new Bit(false)});
        
        // Return the value from the calculated memory address
        return MainMemory.read(alu.getResult());
    }
    
    
}
