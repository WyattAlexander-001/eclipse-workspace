import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HW1 {

    private static final int SHIFT = 3; // Caesar Cipher shift
    private static final int MULTIPLIER = 3; // Multiplicative constant
    private static final int MODULUS = 95; // Modulus to wrap around the printable ASCII characters
    private static final String KEYWORD = "ENCRYPT"; // Keyword for transposition
    private static final Map<Character, Character> SUBSTITUTION_TABLE = createSubstitutionTable();

    public HW1() {
        // Constructor
    }

    public static String encrypt(String plaintext) {
        // Step 1: Apply Caesar cipher with a twist of substitution table
        char[] chars = plaintext.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = substituteChar(chars[i]);
        }

        // Step 2: Apply product operation only to printable characters
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 32 && chars[i] <= 126) {
                chars[i] = (char) (((chars[i] - 32) * MULTIPLIER % MODULUS) + 32);
            }
        }

        // Step 3: Transpose characters
        return transposition(new String(chars));
    }

    private static String transposition(String text) {
        // Convert keyword to indices based on alphabetical order
        Integer[] indices = new Integer[KEYWORD.length()];
        for (int i = 0; i < KEYWORD.length(); i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, Comparator.comparingInt(i -> KEYWORD.charAt(i)));

        // Calculate the number of rows for the transposition
        int numRows = (text.length() + KEYWORD.length() - 1) / KEYWORD.length();
        char[] transposed = new char[numRows * KEYWORD.length()];
        Arrays.fill(transposed, 'X'); // Padding with a printable character

        // Populate transposed array with text
        for (int i = 0; i < text.length(); i++) {
            int row = i / KEYWORD.length();
            int col = i % KEYWORD.length();
            transposed[indices[col] * numRows + row] = text.charAt(i);
        }

        // Convert transposed array back to string
        return new String(transposed);
    }

    
    public static char substituteChar(char c) {
        int shiftedIndex = (c - 32 + SHIFT) % MODULUS;
        char shiftedChar = (char) (shiftedIndex + 32);
        return SUBSTITUTION_TABLE.getOrDefault(shiftedChar, shiftedChar);
    }

    public static Map<Character, Character> createSubstitutionTable() {
        Map<Character, Character> table = new HashMap<>();
        for (int i = 0; i < MODULUS; i++) {
            char original = (char) (32 + i);
            char substitute = (char) (32 + (i + 13) % MODULUS); // ROT13 for simplicity
            table.put(original, substitute);
        }
        return table;
    }
    
    public static int findCoPrime(int modulus) {
        for (int i = 2; i < modulus; i++) {
            if (gcd(i, modulus) == 1) {
                return i; // This number is co-prime with modulus
            }
        }
        return 1; // Fallback if not found, though this should not happen
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }


//    public static void main(String[] args) {
//        String plaintext = "Your message here";
//        String ciphertext = encrypt(plaintext);
//        System.out.println("Encrypted text: " + ciphertext);
//    }
}
