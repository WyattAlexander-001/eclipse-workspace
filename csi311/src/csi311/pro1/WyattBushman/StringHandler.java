package csi311.pro1.WyattBushman;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StringHandler {
    private String AWKFile;
    private int index;
        
    public StringHandler(String input, boolean isFilename) {
        if(isFilename) {
            try {
                Path myPath = Paths.get(input);
                this.AWKFile = new String(Files.readAllBytes(myPath));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error reading file: " + input);
            }
        } else {
            this.AWKFile = input;
        }
        this.index = 0; // Strings start indexing at 0
    }
    
    // Constructor that accepts raw AWK content as a string
    public StringHandler(String content) {
        this.AWKFile = content;
        this.index = 0;
    }


    // Peek i characters ahead without moving the index
    public char peek(int i) {
        if (index + i < AWKFile.length()) {
            return AWKFile.charAt(index + i);
        } else {
            return '\0'; 
        }
    }

    // Returns next i characters as a string without moving the index
    public String peekString(int i) {
        if (index + i <= AWKFile.length()) {
            return AWKFile.substring(index, index + i);
        } else {
            return ""; 
        }
    }

    // Gets the next character and moves the index
    public char getChar() {
        if (index < AWKFile.length()) {
            return AWKFile.charAt(index++);
        } else {
            return '\0'; 
        }
    }

    // Moves the index i positions ahead
    public void swallow(int i) {
        index += i;
        if (index > AWKFile.length()) {
            index = AWKFile.length();  // So doesn't exceed string length
        }
    }

    // Checks if the current index is at the end of the string
    public boolean isDone() {
        return index >= AWKFile.length();
    }

    // Returns the remaining portion of the string from the current index
    public String remainder() {
        return AWKFile.substring(index);
    }
    
    public int getLength() {
        return AWKFile.length();
    }
    
    public int getIndex() {
        return this.index;
    }

}
