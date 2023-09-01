package csi311.pro1.WyattBushman;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

		System.out.println(new File("").getAbsolutePath());
		
		//Works For strings
		/*
	    String testInput = """
	    		Anni Wyatt Jim Terry Davis
	    		Carl 55 33 342234.4
	    		Jimmy John
	    		
	    		""";
	    
	    StringHandler stringHandler = new StringHandler(testInput);
	    Lexer lexer = new Lexer(stringHandler);
	    
	    try {
			lexer.Lex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    for (Token token : lexer.getTokens()) {
	        System.out.println(token);
	    }
	    */
	    
		
		/*
	    String content = readFileContent("testFile.awk");
	    StringHandler stringHandler = new StringHandler(content);
	    Lexer lexer = new Lexer(stringHandler);
	    
	    try {
	        lexer.Lex();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    for (Token token : lexer.getTokens()) {
	        System.out.println(token);
	    }
	    */
		
        // 1. Test with isFilename as true:
        try {
            String filename = "resources/testFile.awk";
            StringHandler handlerUsingFilename = new StringHandler(filename, true);
            System.out.println("Created StringHandler with file from my RESOURCES folder.");
        } catch (Exception e) {
            System.out.println("Error encountered when trying to read from file: " + e.getMessage());
        }

        // 2. Test with isFilename as false:
        try {
            String directContent = "This is direct AWK content like from a website or something or gibberish.";
            StringHandler handlerUsingContent = new StringHandler(directContent, false);
            System.out.println("Created StringHandler with direct content.");
            System.out.println(handlerUsingContent.peek(0)); //T from This is d....

        } catch (Exception e) {
            System.out.println("Error encountered when setting direct content: " + e.getMessage());
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
