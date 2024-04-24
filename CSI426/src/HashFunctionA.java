import java.math.BigInteger;

public class HashFunctionA {
    /**
     * Computes the hash of a given input x using the hash function h(x) = x^2 mod n.
     * The function uses modular arithmetic where n is the product of two large primes p and q.
     *
     * @param x The input to be hashed.
     * @param n The modulus, product of two primes, used in the hash function.
     * @return The hash value as BigInteger.
     */

    public static BigInteger hash(BigInteger x, BigInteger n) {
        // Compute x^2 % n
        return x.pow(2).mod(n);
    }

    public static void main(String[] args) {
        // BigInteger n = p.multiply(q), where p and q are primes
        // For demonstration, ATTACKER knows n.
        BigInteger p = new BigInteger("982451653"); // a large prime number
        BigInteger q = new BigInteger("961748927"); // another large prime number
        
        BigInteger n = p.multiply(q); // n = p * q

        // Value to hash
        BigInteger x = new BigInteger("123456789");

        // Calculate the hash
        BigInteger hashValueX = hash(x, n);
        System.out.println("Hash of " + x + " is " + hashValueX);
        
        // Demonstrating lack of weak collision resistance:
        // Compute n - x, which effectively is -x mod n
        BigInteger negativeX = n.subtract(x); 
        BigInteger hashValueNegativeX = hash(negativeX, n);
        System.out.println("Hash of " + negativeX + " is " + hashValueNegativeX);

        // Check if both hashes are the same
        if (hashValueX.equals(hashValueNegativeX)) {
            System.out.println("Collision found: Hash of " + x + " is the same as hash of " + negativeX);
            // This proves the hash function lacks strong and weak collision resistance
            // because it is easy to find another input (negativeX) that results in the same hash output.
        } else {
            System.out.println("No collision found.");
        }
        
        //Double Check
        BigInteger p2 = new BigInteger("961748927"); //Swapped positions
        BigInteger q2 = new BigInteger("982451653");
        BigInteger n2 = p2.multiply(q2);
        BigInteger x2 = new BigInteger("123456789"); //Same X
        BigInteger hashValueX2 = hash(x2, n2);
        System.out.println("Hash of " + x2 + " is " + hashValueX2);
        
        //Triple Check
        BigInteger p3 = new BigInteger("317");
        BigInteger q3 = new BigInteger("113");
        BigInteger n3 = p3.multiply(q3); //Different n value
        BigInteger x3 = new BigInteger("123456789");
        BigInteger hashValueX3 = hash(x3, n3);
        System.out.println("Hash of " + x3 + " is " + hashValueX3);
 
        BigInteger p4 = new BigInteger("317");
        BigInteger q4 = new BigInteger("113");
        BigInteger n4 = p4.multiply(q4);
        BigInteger x4 = new BigInteger("-123456789");
        BigInteger hashValueX4 = hash(x4, n4);
        System.out.println("Hash of " + x4 + " is " + hashValueX4);       
      
    }
    
    
    
    
}


/* Notes:
 Is it One way? yes because, it requires the attacker to know p and q
 Which is hard when n is the product of two large PRIME numbers and n is unknown.
 
 Weak and Strong collision resistance? No, because you can flip the x to negative.
 Or do n-x and get the same hash value. 
 
 * */
