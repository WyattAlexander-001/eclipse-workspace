import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class HashFunctionB {
    // Splits the binary message into blocks of specified bit lengths.
    public static List<BigInteger> splitIntoBlocks(String message, int bitLength) {
        List<BigInteger> blocks = new ArrayList<>();
        int index = 0;
        // Loop through the entire message, slicing it into blocks.
        while (index < message.length()) {
            int end = Math.min(index + bitLength, message.length());
            String blockString = message.substring(index, end);
            while (blockString.length() < bitLength) {
                blockString += "0";  // Padding with zeros to right
            }
            // Convert the binary string block to a BigInteger and add to blocks list.
            BigInteger block = new BigInteger(blockString, 2);
            blocks.add(block);
            index += bitLength;
        }
        return blocks;
    }
    
    // Computes the hash of blocks by XORing them together.
    public static BigInteger hash(List<BigInteger> blocks) {
        BigInteger result = BigInteger.ZERO;
        for (BigInteger block : blocks) {
            result = result.xor(block); // XOR all blocks together
        }
        return result;
    }

    public static void main(String[] args) {
    	//Use this website to generate a 320bit number: https://www.browserling.com/tools/random-bin
        String binaryMessage = "00011100100000010111110101101000110110111111111011011011000101010111110101100110001111011100000000111101000000110110010100010100000111010000010101101000110011101110101011011111111000001100011111010111010000011000110001011100000011000010001100110010011000100100001011111011011100101110101000100010110101111011110011011010";// 320 bits for demonstration
        List<BigInteger> blocks = splitIntoBlocks(binaryMessage, 160);// Splitting the message into 160-bit blocks.
        BigInteger hashValue = hash(blocks); // Hashing the split blocks.
        System.out.println("Hash value: " + hashValue.toString(2));
        
        
        // Demonstration of reversing the hash, NOT ONE-WAY
        BigInteger targetHash = new BigInteger("1010101010101", 2);  // Simple target hash for demonstration
        List<BigInteger> fakeBlocks = new ArrayList<>();
        fakeBlocks.add(targetHash);  // Start with the target as the first block
        fakeBlocks.add(BigInteger.ZERO);  // Add a block that will not change the result

        BigInteger reconstructedHash = hash(fakeBlocks);
        System.out.println("Reconstructed Hash: " + reconstructedHash.toString(2));
        
        if (targetHash.equals(reconstructedHash)) {
            System.out.println("Successfully reversed the hash to match the target.");
        }
        
        
        //Proving NOT Weak or Strong Collision Resistance
        String message1 = "10100101"; // Message split into "1010" and "0101"
        String message2 = "01011010"; // Message split into "0101" and "1010"

        List<BigInteger> blocks1 = splitIntoBlocks(message1, 4); // Splitting into smaller blocks
        List<BigInteger> blocks2 = splitIntoBlocks(message2, 4);

        BigInteger hash1 = hash(blocks1);
        BigInteger hash2 = hash(blocks2);

        System.out.println("Hash value 1: " + hash1.toString(2));
        System.out.println("Hash value 2: " + hash2.toString(2));

        if (hash1.equals(hash2)) {
            System.out.println("Collision found: Both messages hash to the same value.");
        } else {
            System.out.println("No collision found.");
        }
    }
}

//One way?
//No,XOR operations are easily reversible if ANY part of the input is known. 

//Weak Collision Resistance? 
// No, XOR is a linear operation, an attacker can easily find ANOTHER block that when XORed with a given message, produces the same hash

//Strong Collision Resistance?
//No, XOR operations do not provide weak or strong collision resistance, finding two distinct sets of blocks that XOR to the same hash, can be easy because the inputs can be manipulated to cancel each other out
