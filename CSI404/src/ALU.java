
import java.util.ArrayList;
import java.util.List;

public class ALU {
    public Word op1, op2, result;

    public ALU() {
        op1 = new Word();
        op2 = new Word();
        result = new Word();
    }

    public void doOperation(Bit[] operation) {
        // Convert Bit[] to a string for easier comparison
        String opCode = "";
        for (Bit b : operation) {
            opCode += b.getValue() ? "1" : "0";
        }

        switch (opCode) {
            case "1000":
                this.result = this.op1.and(this.op2);
                break;
            case "1001":
                this.result = this.op1.or(this.op2);
                break;
            case "1010":
                this.result = this.op1.xor(this.op2);
                break;
            case "1011":
                this.result = this.op1.not();
                break;
            case "1100":
                int leftShiftAmount = (int) (this.op2.getUnsigned() & 0b11111);
                this.result = this.op1.leftShift(leftShiftAmount);
                break;
            case "1101":
                int rightShiftAmount = (int) (this.op2.getUnsigned() & 0b11111);
                this.result = this.op1.rightShift(rightShiftAmount);
                break;
            case "1110":
                this.result = add2(this.op1, this.op2); 
                break;
            case "1111":
                // Subtract is adding the op1 with the two's complement of op2
                Word negativeOp2 = add2(this.op2.not(), intToWord(1)); // Use intToWord to create a Word for 1
                this.result = add2(this.op1, negativeOp2);
                break;
            case "0111":
                this.result = multiplyUsingAdd4(this.op1, this.op2);
                break;
            default:
                System.out.println("Operation not supported.");
                break;
        }
    }


    Word add2(Word a, Word b) {
        Word result = new Word();
        Bit carry = new Bit(false); // Initialize carry to false
        for (int i = 31; i >= 0; i--) {
            Bit aBit = a.getBit(i);
            Bit bBit = b.getBit(i);
            
            // Calculate the sum of aBit, bBit, and carry
            Bit sum = aBit.xor(bBit).xor(carry);
            result.setBit(i, sum);
            
            // Calculate the carry for the next iteration
            Bit carryOut = aBit.and(bBit).or(aBit.xor(bBit).and(carry));
            carry = carryOut;
        }
        return result;
    }


    Word add4(Word a, Word b, Word c, Word d) {
        Word result = new Word();
        Bit carry = new Bit(false);
        for (int i = 31; i >= 0; i--) {
            Bit aBit = a.getBit(i);
            Bit bBit = b.getBit(i);
            Bit cBit = c.getBit(i);
            Bit dBit = d.getBit(i);

            // First level of addition
            Bit sum1 = aBit.xor(bBit);
            Bit carry1 = aBit.and(bBit);

            Bit sum2 = cBit.xor(dBit);
            Bit carry2 = cBit.and(dBit);

            // Second level of addition
            Bit finalSum = sum1.xor(sum2).xor(carry);
            result.setBit(i, finalSum);

            
            carry = sum1.and(sum2).or(carry1.and(sum1.xor(sum2))).or(carry2.and(sum1.xor(sum2))).or(carry.and(sum1.xor(sum2)));
        }
        return result;
    }


    // Creates 1 for Two's Compliment
    Word intToWord(int value) {
        Word word = new Word();
        for (int i = 0; i < 32; i++) {
            boolean bitValue = (value & (1 << (31 - i))) != 0;
            word.setBit(i, new Bit(bitValue));
        }
        return word;
    }
    
    Word multiplyUsingAdd4(Word a, Word b) {
        // Directly return `a` if `b` is 1, which is a quick optimization for multiplication by 1.
        if (b.getUnsigned() == 1) {
            return a;
        }
        
        boolean aIsNegative = a.getBit(31).getValue();
        boolean bIsNegative = b.getBit(31).getValue();

        // Negate operands if they are negative
        if (aIsNegative) a = negate(a);
        if (bIsNegative) b = negate(b);

        List<Word> partialProducts = new ArrayList<>();

        for (int i = 0; i < 32; i++) {
            if (b.getBit(i).getValue()) {
                partialProducts.add(a.leftShift(i)); // Generate partial product
            }
        }
        while (partialProducts.size() > 1) {
            List<Word> nextRoundProducts = new ArrayList<>();

            for (int i = 0; i < partialProducts.size(); i += 4) {
                Word sum = new Word(); // Initialize to 0 for each group
                switch (partialProducts.size() - i) {
                    case 1:
                        sum = partialProducts.get(i);
                        break;
                    case 2:
                        sum = add2(partialProducts.get(i), partialProducts.get(i + 1));
                        break;
                    case 3:
                        sum = add4(partialProducts.get(i), partialProducts.get(i + 1), partialProducts.get(i + 2), new Word());
                        break;
                    default:
                        sum = add4(partialProducts.get(i), partialProducts.get(i + 1), partialProducts.get(i + 2), partialProducts.get(i + 3));
                        break;
                }
                nextRoundProducts.add(sum);
            }

            partialProducts = nextRoundProducts; // Prepare for next round
        }

        return partialProducts.isEmpty() ? new Word() : partialProducts.get(0);
    }
    
    Word negate(Word word) {
    		//Check if the word represents Integer.MAX_VALUE
//        if (word.getUnsigned() == 0x7FFFFFFFL) {
//          //Directly return a Word representing Integer.MIN_VALUE
//            return intToWord(Integer.MIN_VALUE);
//        }

        // Invert all bits
        Word inverted = word.not();
        // Add one to the inverted bits
        Word result = add2(inverted, intToWord(1));

        return result;
    }




}
