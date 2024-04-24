package Assignment4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StringHandler {
    private String sourceCode;
    private int index;
        
    public StringHandler(String input, boolean isFilename) {
        if(isFilename) {
            try {
                Path myPath = Paths.get(input);
                this.sourceCode = new String(Files.readAllBytes(myPath));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error reading file: " + input);
            }
        } else {
            this.sourceCode = input;
        }
        this.index = 0; // Strings start indexing at 0
    }
    
    public StringHandler(String content) {
        this.sourceCode = content;
        this.index = 0;
    }


    // Peek i characters ahead without moving the index
    public char peek(int i) {
        if (index + i < sourceCode.length()) {
            return sourceCode.charAt(index + i);
        } else {
            return '\0'; 
        }
    }

    // Returns next i characters as a string without moving the index
    public String peekString(int i) {
        if (index + i <= sourceCode.length()) {
            return sourceCode.substring(index, index + i);
        } else {
            return ""; 
        }
    }

    // Gets the next character and moves the index
    public char getChar() {
        if (index < sourceCode.length()) {
            return sourceCode.charAt(index++);
        } else {
            return '\0'; 
        }
    }

    // Moves the index i positions ahead
    public void swallow(int i) {
        index += i;
        if (index > sourceCode.length()) {
            index = sourceCode.length();  // So doesn't exceed string length
        }
    }

    // Checks if the current index is at the end of the string
    public boolean isDone() {
        return index >= sourceCode.length();
    }

    // Returns the remaining portion of the string from the current index
    public String remainder() {
        return sourceCode.substring(index);
    }
    
    public int getLength() {
        return sourceCode.length();
    }
    
    public int getIndex() {
        return this.index;
    }

}
