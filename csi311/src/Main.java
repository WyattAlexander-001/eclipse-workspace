

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
    	System.out.println("Current Directory: " + new File("").getAbsolutePath() + "\n");
        if (args.length == 0) {
            System.out.println("Please provide the path to the AWK file.");
            return;
        }

        String filePath = args[0];
        
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            String content = new String(bytes);

            StringHandler stringHandler = new StringHandler(content);
            Lexer lexer = new Lexer(stringHandler);

            lexer.Lex();

            for (Token token : lexer.getTokens()) {
                System.out.println(token);
            }

        } catch (Exception e) {
            System.out.println("An error occurred during lexing... " + e.getMessage());
            e.printStackTrace();
        }
    }
}







