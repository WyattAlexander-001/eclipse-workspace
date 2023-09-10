package csi311.pro1.WyattBushman;

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

            lexer.lex();

            for (Token token : lexer.getTokens()) {
                System.out.println(token);
            }

        } catch (Exception e) {
            System.out.println("An error occurred during lexical analysis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/*
How I ran this on local machine

(base) Wyatts-MacBook-Pro:src absinthe$ javac csi311/pro1/WyattBushman/Main.java csi311/pro1/WyattBushman/StringHandler.java csi311/pro1/WyattBushman/Lexer.java
(base) Wyatts-MacBook-Pro:src absinthe$ 
(base) Wyatts-MacBook-Pro:src absinthe$ java csi311.pro1.WyattBushman.Main
Current Directory: /Users/absinthe/eclipse-workspace/csi311/src

Please provide the path to the AWK file.
(base) Wyatts-MacBook-Pro:src absinthe$ java csi311.pro1.WyattBushman.Main /Users/absinthe/eclipse-workspace/csi311/resources/testFile.awk
Current Directory: /Users/absinthe/eclipse-workspace/csi311/src

Token Type: SEPARATOR || Line: 1 || Position: 0

Token Type: NUMBER Value: 5 || Line: 2 || Position: 0

Token Type: WORD Value: goodbye || Line: 2 || Position: 2

Token Type: SEPARATOR || Line: 2 || Position: 9

Token Type: NUMBER Value: 5.23 || Line: 3 || Position: 0

Token Type: NUMBER Value: 8.5 || Line: 3 || Position: 5

Token Type: NUMBER Value: 3 || Line: 3 || Position: 9

Token Type: SEPARATOR || Line: 3 || Position: 10

Token Type: SEPARATOR || Line: 4 || Position: 0

(base) Wyatts-MacBook-Pro:src absinthe$ 

  */



