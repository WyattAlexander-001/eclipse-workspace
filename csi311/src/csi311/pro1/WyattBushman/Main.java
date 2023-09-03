package csi311.pro1.WyattBushman;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

		System.out.println("Current Directory: " + new File("").getAbsolutePath() + "\n");
	    String content = FileUtils.readFileContent("resources","testFile.awk");
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
}
