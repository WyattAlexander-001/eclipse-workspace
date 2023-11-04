package csi311.pro1.WyattBushman;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Interpreter {

    HashMap<String, InterpreterDataType> globalVariables; 
    HashMap<String, FunctionDefinitionNode> functionDefinitions; 
    private LineManager lineManager;
    
    public Interpreter() {
        globalVariables = new HashMap<>();
        functionDefinitions = new HashMap<>();
    }
    
    public Interpreter(ProgramNode programNode, String filePath) throws Exception {
        globalVariables = new HashMap<>();
        functionDefinitions = new HashMap<>();

        // If a path is provided, use Files.ReadAllLines() and create a LineManager member
        List<String> fileLines = (filePath != null && !filePath.isEmpty())
                ? Files.readAllLines(Paths.get(filePath))
                : new ArrayList<>(); // Otherwise make a LineManager with an empty list of string

        setLineManager(new LineManager(fileLines));

        // Setting global variables
        globalVariables.put("FILENAME", new InterpreterDataType(filePath));
        globalVariables.put("FS", new InterpreterDataType(" "));
        globalVariables.put("OFMT", new InterpreterDataType("%.6g"));
        globalVariables.put("OFS", new InterpreterDataType(" "));
        globalVariables.put("ORS", new InterpreterDataType("\n"));

        // Populate function hash map from the functions in the ProgramNode
        for (FunctionDefinitionNode function : programNode.getFunctionDefinitions()) {
            functionDefinitions.put(function.getFunctionName(), function);
        }

        // Populate function hash map with BuiltIn's
        instantiateBuiltInFunctions(); // Call the method to populate built-in functions
    }

    
    private void instantiateBuiltInFunctions() {
        // Print
        BuiltInFunctionDefinitionNode print = new BuiltInFunctionDefinitionNode("print", 
            parameters -> {
                parameters.forEach((key, value) -> System.out.print(value.toString()));
                return "";
            }, true);
        functionDefinitions.put("print", print);

        // Printf
        BuiltInFunctionDefinitionNode printf = new BuiltInFunctionDefinitionNode("printf", 
            parameters -> {
                String format = parameters.get("0").toString();
                Object[] args = parameters.values().stream().skip(1).toArray();
                System.out.printf(format, args);
                return "";
            }, true);
        functionDefinitions.put("printf", printf);
        
        // Index
        BuiltInFunctionDefinitionNode index = new BuiltInFunctionDefinitionNode("index", 
            parameters -> {
                String input = parameters.get("0").toString();
                String substring = parameters.get("1").toString();
                return String.valueOf(input.indexOf(substring) + 1);  // AWK's index starts at 1
            }, false);
        functionDefinitions.put("index", index);

        // Length
        BuiltInFunctionDefinitionNode length = new BuiltInFunctionDefinitionNode("length", 
            parameters -> String.valueOf(parameters.get("0").toString().length()), false);
        functionDefinitions.put("length", length);
        
        // ToLower
        BuiltInFunctionDefinitionNode toLower = new BuiltInFunctionDefinitionNode("toLower", 
            parameters -> parameters.get("0").toString().toLowerCase(), false);
        functionDefinitions.put("toLower", toLower);

        // ToUpper
        BuiltInFunctionDefinitionNode toUpper = new BuiltInFunctionDefinitionNode("toUpper", 
            parameters -> parameters.get("0").toString().toUpperCase(), false);
        functionDefinitions.put("toUpper", toUpper);
        
        // Gsub
        BuiltInFunctionDefinitionNode gsub = new BuiltInFunctionDefinitionNode("gsub", 
            parameters -> {
                String pattern = parameters.get("0").toString();
                String replacement = parameters.get("1").toString();
                String input = parameters.get("2").toString();
                return input.replaceAll(pattern, replacement);
            }, false);
        functionDefinitions.put("gsub", gsub);

        // Match
        BuiltInFunctionDefinitionNode match = new BuiltInFunctionDefinitionNode("match", 
            parameters -> {
                String input = parameters.get("0").toString();
                String pattern = parameters.get("1").toString();
                return Pattern.compile(pattern).matcher(input).find() ? "1" : "0";
            }, false);
        functionDefinitions.put("match", match);

        // Sub
        BuiltInFunctionDefinitionNode sub = new BuiltInFunctionDefinitionNode("sub", 
            parameters -> {
                String pattern = parameters.get("0").toString();
                String replacement = parameters.get("1").toString();
                String input = parameters.get("2").toString();
                return input.replaceFirst(pattern, replacement);
            }, false);
        functionDefinitions.put("sub", sub);

        // Split
        BuiltInFunctionDefinitionNode split = new BuiltInFunctionDefinitionNode("split", 
            parameters -> {
                String input = parameters.get("0").toString();
                String delimiter = parameters.get("1").toString();
                String[] parts = input.split(delimiter);
                return String.join(",", parts);
            }, false);
        functionDefinitions.put("split", split);

        // Substr
        BuiltInFunctionDefinitionNode substr = new BuiltInFunctionDefinitionNode("substr", 
            parameters -> {
                String input = parameters.get("0").toString();
                int start = Integer.parseInt(parameters.get("1").toString()) - 1; 
                if (parameters.containsKey("2")) {
                	int substrLength = Integer.parseInt(parameters.get("2").toString());
                	return input.substring(start, start + substrLength);
                }
                return input.substring(start);
            }, false);
        functionDefinitions.put("substr", substr);

        // Getline
        BuiltInFunctionDefinitionNode getline = new BuiltInFunctionDefinitionNode("getline", 
            parameters -> {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    return scanner.nextLine();
                }
                return "";
            }, false);
        functionDefinitions.put("getline", getline);

        // Next
        BuiltInFunctionDefinitionNode next = new BuiltInFunctionDefinitionNode("next", 
            parameters -> {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                return "";
            }, false);
        functionDefinitions.put("next", next);
    }


    public LineManager getLineManager() {
		return lineManager;
	}

	public void setLineManager(LineManager lineManager) {
		this.lineManager = lineManager;
	}
	


	// =============  Inner class LineManager =================================
    public class LineManager {
        List<String> lines; // store the read-in input file
        private int currentLineIndex = -1;

        public LineManager(List<String> lines) {
            this.lines = lines;
        }

        public boolean splitAndAssign() {
            currentLineIndex++;

            // If there's no line to split, return false
            if (currentLineIndex >= lines.size()) {
                return false;
            }

            String currentLine = lines.get(currentLineIndex);

            // Look at global variables to find "FS" - the field separator
            InterpreterDataType fsVariable = globalVariables.get("FS");
            String fsValue = fsVariable != null ? fsVariable.getValue() : " "; // default value of FS

            String[] splitLine;
            try {
                splitLine = currentLine.split(Pattern.quote(fsValue)); // use quote to treat separator as literal
            } catch (PatternSyntaxException ex) {
                throw new IllegalArgumentException("Invalid field separator provided: " + fsValue);
            }

            // Assign $0, $1, etc.
            globalVariables.put("$0", new InterpreterDataType(currentLine));
            for (int i = 0; i < splitLine.length; i++) {
                globalVariables.put("$" + (i + 1), new InterpreterDataType(splitLine[i]));
            }

            // Set NF
            globalVariables.put("NF", new InterpreterDataType(String.valueOf(splitLine.length)));

            // Update the NR and FNR variables
            InterpreterDataType nrVariable = globalVariables.get("NR");
            int nrValue = nrVariable != null ? Integer.parseInt(nrVariable.getValue()) + 1 : 1;
            globalVariables.put("NR", new InterpreterDataType(String.valueOf(nrValue)));

            InterpreterDataType fnrVariable = globalVariables.get("FNR");
            int fnrValue = fnrVariable != null ? Integer.parseInt(fnrVariable.getValue()) + 1 : 1;
            globalVariables.put("FNR", new InterpreterDataType(String.valueOf(fnrValue)));

            return true;
        }
    }
}
