package Assignment4;

public class ProcessorV2 {
    private Word pc = new Word();
    private final Word sp = new Word();
    private Word currentInstruction;
    private final Bit halted;
 
    private final ALU doMath = new ALU();
 
    private final Word[] registers = new Word[31];
 
    private Word opcode = new Word();
    private Word destinationregister;
    private Word sourceregister1;
    private Word sourceregister2;
    private Word immediate;
 
    private final Bit[] function = new Bit[4];
    private Word result;
 
    private final Word mask4bits = new Word();
    private final Word mask5bits = new Word();
 
    private boolean _2R;
    private boolean _3R;
    private boolean destonly;
    private boolean _0R;
 
    private int destinationaddress;
 
    /*
    This function initializes the Words used to represent a Program Counter and
    a stack pointer. The current instruction and halted bit used for the fetch()
    function are also initialized.
    */
 
    ProcessorV2(){
        pc.set(0);
        sp.set(0);
 
        registers[0] = new Word();
 
        currentInstruction = new Word();
        halted = new Bit(false);
    }
 
    /*
    The following function continuously checks for the predefined halted bit
    while continuously running the processing steps in the order given:
        -   fetch an instruction from memory
        -   get the data for the instruction
        -   execute the instruction
        -   store the results
     This function will stop once in reaches a halt.
 
     */
 
    void run() throws Exception {
        while(!halted.getValue())
        {
            fetch();
            decode();
            execute();
            store();
        }
    }
 
    void decode() throws Exception {
 
        Word operation;
 
        int source1address;
        int source2address;
 
        mask4bits.setBit(31, new Bit(true));
        mask4bits.setBit(30, new Bit(true));
        mask4bits.setBit(29, new Bit(true));
        mask4bits.setBit(28, new Bit(true));
 
        mask5bits.setBit(31, new Bit(true));
        mask5bits.setBit(30, new Bit(true));
        mask5bits.setBit(29, new Bit(true));
        mask5bits.setBit(28, new Bit(true));
        mask5bits.setBit(27, new Bit(true));
 
        _2R = false;
        _3R = false;
        destonly = false;
        _0R = false;
 
        opcode = currentInstruction.and(mask5bits);
 
        currentInstruction = currentInstruction.rightShift(5);
 
 
        if(opcode.getBit(30).getValue()) {
            if(opcode.getBit(31).getValue())
            {
                _2R = true;
 
                destinationaddress = getAddress(currentInstruction.and(mask5bits));
                destinationregister = registers[destinationaddress];
                currentInstruction = currentInstruction.rightShift(5);
 
                operation = currentInstruction.and(mask4bits);
                function[0] = operation.getBit(28);
                function[1] = operation.getBit(29);
                function[2] = operation.getBit(30);
                function[3] = operation.getBit(31);
                currentInstruction = currentInstruction.rightShift(4);
 
                source1address = getAddress(currentInstruction.and(mask5bits));
                sourceregister1 = registers[source1address];
                currentInstruction = currentInstruction.rightShift(5);
 
                immediate = currentInstruction;
            }
            else {
                _3R = true;
 
                destinationaddress = getAddress(currentInstruction.and(mask5bits));
                destinationregister = registers[destinationaddress];
                currentInstruction = currentInstruction.rightShift(5);
 
                operation = currentInstruction.and(mask4bits);
                function[0] = operation.getBit(28);
                function[1] = operation.getBit(29);
                function[2] = operation.getBit(30);
                function[3] = operation.getBit(31);
                currentInstruction = currentInstruction.rightShift(4);
 
                source1address = getAddress(currentInstruction.and(mask5bits));
                sourceregister1 = registers[source1address];
                currentInstruction = currentInstruction.rightShift(5);
 
                source2address = getAddress(currentInstruction.and(mask5bits));
                sourceregister2 = registers[source2address];
                currentInstruction = currentInstruction.rightShift(5);
 
                immediate = currentInstruction;
            }
        }
        else {
            if(opcode.getBit(31).getValue())
            {
                destonly = true;
 
                destinationaddress = getAddress(currentInstruction.and(mask5bits));
                destinationregister = registers[destinationaddress];
                currentInstruction = currentInstruction.rightShift(5);
 
                operation = currentInstruction.and(mask4bits);
                function[0] = operation.getBit(28);
                function[1] = operation.getBit(29);
                function[2] = operation.getBit(30);
                function[3] = operation.getBit(31);
                currentInstruction = currentInstruction.rightShift(4);
 
                immediate = currentInstruction;
            }
            else {
                _0R = true;
 
                immediate = currentInstruction;
            }
        }
    }
 
    void execute() throws Exception {
 
        if(opcode.getBit(27).getValue())
        {
            if (opcode.getBit(28).getValue())
            {
                if(opcode.getBit(29).getValue())
                {
                    throw new Exception("this is not a supported registry option!!!");
                }
                else {
                    if(_2R) {
                        //this will be implemented in later parts
                    }
                    if(_3R) {
                        //this will be implemented in later parts
                    }
                    if(destonly) {
                        //this will be implemented in later parts
                    }
                    if(_0R) {
                        //this will be implemented in later parts
                    }
                }
            }
            else {
                if(opcode.getBit(29).getValue())
                {
                    if(_2R) {
                        //this will be implemented in later parts
                    }
                    if(_3R) {
                        //this will be implemented in later parts
                    }
                    if(destonly) {
                        //this will be implemented in later parts
                    }
                    if(_0R) {
                        throw new Exception("This opcode is not used!!!");
                    }
                }
                else {
                    if(_2R) {
                        //this will be implemented in later parts
                    }
                    if(_3R) {
                        //this will be implemented in later parts
                    }
                    if(destonly) {
                        //this will be implemented in later parts
                    }
                    if(_0R) {
                        //this will be implemented in later parts
                    }
                }
            }
        }
        else {
            if (opcode.getBit(28).getValue())
            {
                if(opcode.getBit(29).getValue())
                {
                    if(_2R) {
                        //this will be implemented in later parts
                    }
                    if(_3R) {
                        //this will be implemented in later parts
                    }
                    if(destonly) {
                        //this will be implemented in later parts
                    }
                    if(_0R) {
                        throw new Exception("This opcode is not used!!!");
                    }
                }
                else {
                    if(_2R) {
                        //this will be implemented in later parts
                    }
                    if(_3R) {
                        //this will be implemented in later parts
                    }
                    if(destonly) {
                        //this will be implemented in later parts
                    }
                    if(_0R) {
                        //this will be implemented in later parts
                    }
                }
            }
            else {
                if(opcode.getBit(29).getValue())
                {
                    if(_2R) {
                        //this will be implemented in later parts
                    }
                    if(_3R) {
                        //this will be implemented in later parts
                    }
                    if(destonly) {
                        //this will be implemented in later parts
                    }
                    if(_0R) {
                        //this will be implemented in later parts
                    }
                }
                else {
                    if(_2R) {
                        doMath.setOp1(destinationregister);
                        doMath.setOp2(sourceregister1);
                        doMath.doOperation(function);
                        result = doMath.getResult();
                    }
                    if(_3R) {
                        doMath.setop1(sourceregister1);
                        doMath.setop2(sourceregister2);
                        doMath.doOperation(function);
                        result = doMath.getResult();
                    }
                    if(destonly) {
                        result = immediate;
                    }
                    if(_0R) {
                        halted.toggle();
                    }
                }
            }
        }
    }
 
     int getAddress(Word address) throws Exception {
        if(address.getBit(27).getValue())
        {
            if(address.getBit(28).getValue())
            {
                if(address.getBit(29).getValue())
                {
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            throw new Exception("This register does not exist!!!");
                        }
                        else {
                            return 30;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 29;
                        }
                        else {
                            return 28;
                        }
                    }
                }
                else{
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 27;
                        }
                        else {
                            return 26;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 25;
                        }
                        else {
                            return 24;
                        }
                    }
                }
            }
            else {
                if(address.getBit(29).getValue())
                {
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 23;
                        }
                        else {
                            return 22;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 21;
                        }
                        else {
                            return 20;
                        }
                    }
                }
                else{
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 19;
                        }
                        else {
                            return 18;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 17;
                        }
                        else {
                            return 16;
                        }
                    }
                }
            }
        }
        else{
            if(address.getBit(28).getValue())
            {
                if(address.getBit(29).getValue())
                {
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 15;
                        }
                        else {
                            return 14;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 13;
                        }
                        else {
                            return 12;
                        }
                    }
                }
                else{
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 11;
                        }
                        else {
                            return 10;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 9;
                        }
                        else {
                            return 8;
                        }
                    }
                }
            }
            else {
                if(address.getBit(29).getValue())
                {
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 7;
                        }
                        else {
                            return 6;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 5;
                        }
                        else {
                            return 4;
                        }
                    }
                }
                else{
                    if(address.getBit(30).getValue())
                    {
                        if(address.getBit(31).getValue())
                        {
                            return 3;
                        }
                        else {
                            return 2;
                        }
                    }
                    else {
                        if(address.getBit(31).getValue())
                        {
                            return 1;
                        }
                        else {
                            throw new Exception("Register 0 may not be accessed!!!");
                        }
                    }
                }
            }
        }
    }
 
    //refer to the logic of decode to figure this one out
    void store(){
        registers[destinationaddress] = result;
    }
 
    /*
    Using the program counter, this function gets the next instruction by
    reading from main memory. It then increments the Program Counter
     */
 
    void fetch() throws Exception {
        currentInstruction = MainMemory.read(pc);
        pc = pc.increment();
    }
 
    //Used for Unit Tests
 
    Word getRegister(int index){
         return registers[index];
    }
}