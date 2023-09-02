package csi311.pro1.WyattBushman;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

		System.out.println("Current Directory: " + new File("").getAbsolutePath() + "\n");
	    String content = readFileContent("testFile.awk");
	    StringHandler stringHandler = new StringHandler(content);
	    Lexer lexer = new Lexer(stringHandler);
	    
	    try {
	        lexer.Lex();
	    } catch (Exception e) {
	    	System.out.println("An error occurred during lexical analysis: " + e.getMessage());
	        e.printStackTrace();
	        return;
	    }
	    for (Token token : lexer.getTokens()) {
	        System.out.println(token);
	    }
	}
	
	//Dump your files in the resource folder!
	private static String readFileContent(String filename) {
	    try {
	        Path filePath = Paths.get("resources", filename);
	        String content = new String(Files.readAllBytes(filePath));
	        return content;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error reading file: " + filename);
	    }
	}


}
