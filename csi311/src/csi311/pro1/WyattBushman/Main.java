package csi311.pro1.WyattBushman;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

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

            // Lexical analysis phase
            StringHandler stringHandler = new StringHandler(content);
            Lexer lexer = new Lexer(stringHandler);

            lexer.lex();

            LinkedList<Token> tokens = lexer.getTokens();
            
            for (Token token : tokens) {
                System.out.println(token);
            }
            
            // Parsing phase
            Parser parser = new Parser(tokens);
            ProgramNode program = parser.Parse();
            
            System.out.println("\nParsed Program:\n" + program);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}









/*
absinthe@dyn-169-226-103-103 src % javac csi311/pro1/WyattBushman/Main.java \
csi311/pro1/WyattBushman/Parser.java \
csi311/pro1/WyattBushman/Lexer.java \
csi311/pro1/WyattBushman/TokenManager.java \
csi311/pro1/WyattBushman/StringHandler.java \
csi311/pro1/WyattBushman/Token.java \
csi311/pro1/WyattBushman/TokenType.java \
csi311/pro1/WyattBushman/Node.java \
csi311/pro1/WyattBushman/OperationType.java \
csi311/pro1/WyattBushman/OperationNode.java \
csi311/pro1/WyattBushman/ProgramNode.java \
csi311/pro1/WyattBushman/FunctionDefinitionNode.java \
csi311/pro1/WyattBushman/StatementNode.java \
csi311/pro1/WyattBushman/BlockNode.java \
csi311/pro1/WyattBushman/VariableReferenceNode.java

java csi311.pro1.WyattBushman.Main /Users/absinthe/eclipse-workspace/csi311/resources/testFile.awk


  */



