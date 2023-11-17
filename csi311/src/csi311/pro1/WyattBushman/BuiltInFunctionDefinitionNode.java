package csi311.pro1.WyattBushman;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;

public class BuiltInFunctionDefinitionNode extends FunctionDefinitionNode {
    // Lambda function to execute the built-in functionality.
    private Function<HashMap<String, InterpreterDataType>, String> executeFunction;
    
    // Flag to determine if the function is variadic
    private boolean isVariadic;

    public BuiltInFunctionDefinitionNode(String name, Function<HashMap<String, InterpreterDataType>, String> executeFunction, boolean isVariadic) {
        super(name);
        this.executeFunction = executeFunction;
        this.isVariadic = isVariadic;
    }
   
    public InterpreterDataType execute(HashMap<String, InterpreterDataType> parameters) {
        String result = this.executeFunction.apply(parameters);
        return new InterpreterDataType(result);
    }


    public boolean isVariadic() {
        return isVariadic;
    }

    public void setVariadic(boolean isVariadic) {
        this.isVariadic = isVariadic;
    }
    
    //Lamba Implementation(s):
    
    //Print
    Function<HashMap<String, InterpreterDataType>, String> printFunction = parameters -> {
        parameters.forEach((key, value) -> System.out.print(value.toString()));
        return "";
    };
    
    //Printf
    Function<HashMap<String, InterpreterDataType>, String> printfFunction = parameters -> {
        String format = parameters.get("0").toString();
        Object[] args = parameters.values().stream().skip(1).toArray();
        System.out.printf(format, args);
        return "";
    };
    
    //Index
    Function<HashMap<String, InterpreterDataType>, String> indexFunction = parameters -> {
        String input = parameters.get("0").toString();
        String substring = parameters.get("1").toString();
        return String.valueOf(input.indexOf(substring) + 1);  // AWK's index starts at 1
    };
    
    //Length
    Function<HashMap<String, InterpreterDataType>, String> lengthFunction = parameters -> {
        return String.valueOf(parameters.get("0").toString().length());
    };
    
    //toLower
    Function<HashMap<String, InterpreterDataType>, String> toLowerFunction = parameters -> {
        return parameters.get("0").toString().toLowerCase();
    };

    //toUpper
    Function<HashMap<String, InterpreterDataType>, String> toUpperFunction = parameters -> {
        return parameters.get("0").toString().toUpperCase();
    };
    
    //gsub
    Function<HashMap<String, InterpreterDataType>, String> gsubFunction = parameters -> {
        String pattern = parameters.get("0").toString();
        String replacement = parameters.get("1").toString();
        String input = parameters.get("2").toString();
        return input.replaceAll(pattern, replacement);
    };
    
    //match
    Function<HashMap<String, InterpreterDataType>, String> matchFunction = parameters -> {
        String input = parameters.get("0").toString();
        String pattern = parameters.get("1").toString();
        return Pattern.compile(pattern).matcher(input).find() ? "1" : "0";
    };

    //sub
    Function<HashMap<String, InterpreterDataType>, String> subFunction = parameters -> {
        String pattern = parameters.get("0").toString();
        String replacement = parameters.get("1").toString();
        String input = parameters.get("2").toString();
        return input.replaceFirst(pattern, replacement);
    };
    
    //split
    Function<HashMap<String, InterpreterDataType>, String> splitFunction = parameters -> {
        String input = parameters.get("0").toString();
        String delimiter = parameters.get("1").toString();
        String[] parts = input.split(delimiter);
        return String.join(",", parts);
    };

    
    //substr
    Function<HashMap<String, InterpreterDataType>, String> substrFunction = parameters -> {
        String input = parameters.get("0").toString();
        int start = Integer.parseInt(parameters.get("1").toString()) - 1; // AWK's index starts at 1
        if (parameters.containsKey("2")) {
            int length = Integer.parseInt(parameters.get("2").toString());
            return input.substring(start, start + length);
        }
        return input.substring(start);
    };
    
    //getline
    Function<HashMap<String, InterpreterDataType>, String> getlineFunction = parameters -> {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    };

    //next:
    Function<HashMap<String, InterpreterDataType>, String> nextFunction = parameters -> {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return "";
    };
    
    

}
