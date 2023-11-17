package csi311.pro1.WyattBushman;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        try {
            // Paths to the AWK file and the input data file
            String awkFilePath = "/Users/absinthe/eclipse-workspace/csi311/resources/sales/minimal.awk";
            String inputDataFilePath = "/Users/absinthe/eclipse-workspace/csi311/resources/sales/sales_data.txt"; // Update or keep the path as needed

            // Read and process the AWK file
            byte[] awkFileBytes = Files.readAllBytes(Paths.get(awkFilePath));
            String awkFileContent = new String(awkFileBytes);

            // Lexical analysis phase
            StringHandler stringHandler = new StringHandler(awkFileContent);
            Lexer lexer = new Lexer(stringHandler);
            lexer.lex();
            LinkedList<Token> tokens = lexer.getTokens();

            // Parsing phase
            Parser parser = new Parser(tokens);
            ProgramNode program = parser.Parse();

            // Create the interpreter with the parsed program and input data file path
            Interpreter interpreter = new Interpreter(program, inputDataFilePath);

            // Interpret the program
            interpreter.interpretProgram(program);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
