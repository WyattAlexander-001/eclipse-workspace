package csi311.pro1.WyattBushman;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Callable;
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
	
	//====================================

	
	public InterpreterDataType GetIDT(Node node, HashMap<String, InterpreterDataType> localVariables) {
	    if (node instanceof AssignmentNode) {
	        AssignmentNode assignmentNode = (AssignmentNode) node;
	        InterpreterDataType value = GetIDT(assignmentNode.getExpression(), localVariables);
	        // Ensure the target of the assignment is a variable reference node.
	        if (assignmentNode.getTarget() instanceof VariableReferenceNode) {
	            VariableReferenceNode varRefNode = (VariableReferenceNode) assignmentNode.getTarget();
	            // Use a method to set the variable's value within the appropriate scope (local or global).
	            setVariable(varRefNode, value, localVariables);
	        } else {
	            throw new IllegalArgumentException("Assignment to non-variable not supported.");
	        }
	        return value;
	    } else if (node instanceof ConstantNode) {
	        return new InterpreterDataType(((ConstantNode) node).getValue());
	    } else if (node instanceof FunctionCallNode) {
	        FunctionCallNode functionCallNode = (FunctionCallNode) node;
	        try {
	            return RunFunctionCall(functionCallNode, localVariables);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    } else if (node instanceof PatternNode) {
	        throw new UnsupportedOperationException("PatternNode cannot be directly evaluated.");
	    } else if (node instanceof TernaryNode) {
	        TernaryNode ternaryNode = (TernaryNode) node;
	        InterpreterDataType condition = GetIDT(ternaryNode.getCondition(), localVariables);
	        return condition.asBoolean() ? GetIDT(ternaryNode.getTrueBranch(), localVariables) : GetIDT(ternaryNode.getFalseBranch(), localVariables);
	    } else if (node instanceof VariableReferenceNode) {
	        return getVariable((VariableReferenceNode) node, localVariables);
	    } else if (node instanceof OperationNode) {
	        try {
				return evaluateOperation((OperationNode) node, localVariables);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    } else {
	        throw new IllegalArgumentException("Unknown node type: " + node.getClass().getSimpleName());
	    }
		return null;
	}
	
	private void setVariable(VariableReferenceNode varRefNode, InterpreterDataType value, HashMap<String, InterpreterDataType> localVariables) {
	    // If the variable reference is an array with an index expression, handle that logic here.
	    if (varRefNode.getIndexExpression().isPresent()) {
	        // Evaluate the index expression to get the index key as an integer.
	        Node indexNode = varRefNode.getIndexExpression().get();
	        InterpreterDataType indexValue = GetIDT(indexNode, localVariables);
	        
	        // Attempt to parse the index as an integer.
	        int index;
	        try {
	            index = Integer.parseInt(indexValue.getValue());
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Array index is not an integer: " + indexValue.getValue());
	        }

	        // Retrieve the array variable, ensuring it's been declared as an array.
	        InterpreterDataType arrayVariable = getVariable(varRefNode, localVariables);
	        if (!arrayVariable.isArray()) {
	            throw new IllegalArgumentException("Variable " + varRefNode.getName() + " is not an array.");
	        }

	        // Ensure the index is within the bounds of the array.
	        List<String> array = arrayVariable.getArrayValue();
	        if (index < 0 || index >= array.size()) {
	            // Optionally, you might want to automatically resize the array or handle this condition differently.
	            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for array " + varRefNode.getName());
	        }

	        // Set the value in the array at the given index.
	        array.set(index, value.getValue());
	    } else {
	        // If it's not an array, simply assign the value to the variable.
	        if (localVariables != null && localVariables.containsKey(varRefNode.getName())) {
	            localVariables.put(varRefNode.getName(), value);
	        } else {
	            globalVariables.put(varRefNode.getName(), value);
	        }
	    }
	}



		private InterpreterDataType getVariable(VariableReferenceNode varRefNode, HashMap<String, InterpreterDataType> localVariables) {
		    // Check in local variables first, then in global variables.
		    InterpreterDataType result = localVariables != null ? localVariables.get(varRefNode.getName()) : null;
		    if (result == null) {
		        result = globalVariables.get(varRefNode.getName());
		    }
		    if (result == null) {
		        result = new InterpreterDataType(); // default value for non-existent variable
		    }
		    return result;
		}


		private InterpreterDataType evaluateOperation(OperationNode opNode, HashMap<String, InterpreterDataType> localVariables) throws Exception {
		    InterpreterDataType leftValue = GetIDT(opNode.getLeft(), localVariables);
		    InterpreterDataType rightValue = opNode.getRight().isPresent() ? GetIDT(opNode.getRight().get(), localVariables) : null;

		    switch (opNode.getOperationType()) {
		        case ADDITION:
		            float addLeft = Float.parseFloat(leftValue.getValue());
		            float addRight = Float.parseFloat(rightValue.getValue());
		            return new InterpreterDataType(String.valueOf(addLeft + addRight));
		        case SUBTRACTION:
		            float subLeft = Float.parseFloat(leftValue.getValue());
		            float subRight = Float.parseFloat(rightValue.getValue());
		            return new InterpreterDataType(String.valueOf(subLeft - subRight));
		        case MULTIPLICATION:
		            float mulLeft = Float.parseFloat(leftValue.getValue());
		            float mulRight = Float.parseFloat(rightValue.getValue());
		            return new InterpreterDataType(String.valueOf(mulLeft * mulRight));
		        case DIVISION:
		            float divLeft = Float.parseFloat(leftValue.getValue());
		            float divRight = Float.parseFloat(rightValue.getValue());
		            if (divRight == 0) {
		                throw new ArithmeticException("Division by zero.");
		            }
		            return new InterpreterDataType(String.valueOf(divLeft / divRight));
		        case EQUALS:
		            if (tryParseFloat(leftValue.getValue()) && tryParseFloat(rightValue.getValue())) {
		                return new InterpreterDataType(Boolean.toString(Float.parseFloat(leftValue.getValue()) == Float.parseFloat(rightValue.getValue())));
		            } else {
		                return new InterpreterDataType(Boolean.toString(leftValue.getValue().equals(rightValue.getValue())));
		            }
		        case NOT_EQUALS:
		            if (tryParseFloat(leftValue.getValue()) && tryParseFloat(rightValue.getValue())) {
		                return new InterpreterDataType(Boolean.toString(Float.parseFloat(leftValue.getValue()) != Float.parseFloat(rightValue.getValue())));
		            } else {
		                return new InterpreterDataType(Boolean.toString(!leftValue.getValue().equals(rightValue.getValue())));
		            }
		        case LOGICAL_AND:
		            return new InterpreterDataType(Boolean.toString(leftValue.asBoolean() && rightValue.asBoolean()));
		        case LOGICAL_OR:
		            return new InterpreterDataType(Boolean.toString(leftValue.asBoolean() || rightValue.asBoolean()));
		        case FIELD_SELECTOR:
		            // Evaluate the left side as an index, then retrieve the value from the interpreter's context
		            int index = Integer.parseInt(leftValue.getValue());
		            String fieldValue = retrieveFieldValueByIndex(index, localVariables); // Implement this method
		            return new InterpreterDataType(fieldValue);
		        default:
		            throw new UnsupportedOperationException("Unsupported operation: " + opNode.getOperationType());
		    }
		}
		
		private boolean tryParseFloat(String value) {
		    try {
		        Float.parseFloat(value);
		        return true;
		    } catch (NumberFormatException e) {
		        return false;
		    }
		}

		private String retrieveFieldValueByIndex(int index, HashMap<String, InterpreterDataType> globalVariables) {
		    // Field indices in AWK are 1-based, so "$1" refers to the first field, "$2" to the second, etc.
		    String fieldKey = "$" + index;

		    // Retrieve the value from the global variables.
		    InterpreterDataType fieldValue = globalVariables.get(fieldKey);
		    if (fieldValue == null) {
		        throw new IllegalArgumentException("Field " + index + " does not exist.");
		    }

		    // Return the field's value.
		    return fieldValue.getValue();
		}

		private InterpreterDataType RunFunctionCall(FunctionCallNode node, HashMap<String, InterpreterDataType> localVariables) throws Exception {
		    Callable<InterpreterDataType> function = (Callable<InterpreterDataType>) functionDefinitions.get(node.getFunctionName());
		    if (function == null) {
		        throw new IllegalArgumentException("Function " + node.getFunctionName() + " is not defined.");
		    }

		    // Evaluate the arguments
		    List<InterpreterDataType> evaluatedArgs = new ArrayList<>();
		    for (Node arg : node.getArguments()) {
		        evaluatedArgs.add(GetIDT(arg, localVariables));
		    }

		    // Execute the function with the evaluated arguments
		    return function.call();
		}
		
	    public ReturnType processStatement(StatementNode stmt, HashMap<String, InterpreterDataType> locals) {
	        if (stmt instanceof AssignmentNode) {
	            // Handle assignment logic.
	            AssignmentNode assignmentNode = (AssignmentNode) stmt;
	            InterpreterDataType rightValue = GetIDT(assignmentNode.getExpression(), locals);
	            setVariable((VariableReferenceNode) assignmentNode.getTarget(), rightValue, locals);
	            return new ReturnType(ReturnType.Status.NORMAL); 
	        } else if (stmt instanceof BreakNode) {
	            // Handle break logic.
	            return new ReturnType(ReturnType.Status.BREAK);
	        } else if (stmt instanceof ContinueNode) {
	            // Handle continue logic.
	            return new ReturnType(ReturnType.Status.CONTINUE);
	        } else if (stmt instanceof DeleteNode) {
	            DeleteNode deleteNode = (DeleteNode) stmt;
	            InterpreterDataType arrayVariable = getVariable(new VariableReferenceNode(deleteNode.getArrayName(), Optional.empty()), locals);
	            if (arrayVariable.isArray()) {
	                if (deleteNode.getIndexExpression() != null) {
	                    // Delete specific index
	                    InterpreterDataType indexValue = GetIDT(deleteNode.getIndexExpression(), locals);
	                    arrayVariable.getArrayValue().remove(indexValue.asString());
	                } else {
	                    // Delete entire array
	                    arrayVariable.setArrayValue(new ArrayList<>());
	                }
	            } else {
	                throw new IllegalArgumentException("Variable " + deleteNode.getArrayName() + " is not an array.");
	            }
	            return new ReturnType(ReturnType.Status.NORMAL); // Placeholder return.
	        } else if (stmt instanceof DoWhileNode) {
	            DoWhileNode doWhileNode = (DoWhileNode) stmt;
	            ReturnType returnType;
	            do {
	                returnType = interpretListOfStatements(doWhileNode.getBlock().getStatements(), locals);
	                if (returnType.getStatus() == ReturnType.Status.BREAK) {
	                    // Break out of the loop.
	                    break;
	                }
	                // Handle return and continue if necessary.
	            } while (GetIDT(doWhileNode.getCondition(), locals).asBoolean());
	            
	            // After the loop, we return a normal status unless a 'return' was triggered inside the loop.
	            return returnType.getStatus() == ReturnType.Status.RETURN ? returnType : new ReturnType(ReturnType.Status.NORMAL);
	        } else if (stmt instanceof ForNode) {
	            ForNode forNode = (ForNode) stmt;

	            // Process the initialization part of the for loop, if it exists.
	            if (forNode.getInitialization() != null) {
	                processStatement((StatementNode) forNode.getInitialization(), locals);
	            }

	            // Execute the block of statements while the condition is true.
	            while (GetIDT(forNode.getCondition(), locals).asBoolean()) {
	                ReturnType blockReturnType = interpretListOfStatements(forNode.getBlock().getStatements(), locals);

	                // Handle a 'break' statement: exit the loop.
	                if (blockReturnType.getStatus() == ReturnType.Status.BREAK) {
	                    break;
	                }
	                // Handle a 'return' statement: exit the loop and return from the function.
	                else if (blockReturnType.getStatus() == ReturnType.Status.RETURN) {
	                    return blockReturnType;
	                }

	                // Process the iteration part of the for loop, if it exists.
	                if (forNode.getIteration() != null) {
	                    processStatement((StatementNode) forNode.getIteration(), locals);
	                }
	            }
	            return new ReturnType(ReturnType.Status.NORMAL);
	        } else if (stmt instanceof ForEachNode) {
	            ForEachNode forEachNode = (ForEachNode) stmt;
	            InterpreterDataType collectionVariable = getVariable(new VariableReferenceNode(forEachNode.getCollection(), Optional.empty()), locals);

	            if (!(collectionVariable instanceof InterpreterArrayDataType)) {
	                throw new IllegalArgumentException("Variable " + forEachNode.getCollection() + " is not an array.");
	            }

	            HashMap<String, InterpreterDataType> arrayData = ((InterpreterArrayDataType)collectionVariable).getArrayData();
	            for (Map.Entry<String, InterpreterDataType> entry : arrayData.entrySet()) {
	                locals.put(forEachNode.getIterator(), new InterpreterDataType(entry.getKey()));
	                ReturnType returnType = interpretListOfStatements(forEachNode.getBlock().getStatements(), locals);
	                if (returnType.getStatus() == ReturnType.Status.BREAK) {
	                    break;
	                } else if (returnType.getStatus() == ReturnType.Status.RETURN) {
	                    return returnType;
	                }
	            }
	            return new ReturnType(ReturnType.Status.NORMAL);
	        } else if (stmt instanceof FunctionCallNode) {
	            FunctionCallNode functionCallNode = (FunctionCallNode) stmt;
	            InterpreterDataType result = null;
				try {
					result = RunFunctionCall(functionCallNode, locals);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            return new ReturnType(ReturnType.Status.NORMAL, result.getValue());
	        } else if (stmt instanceof IfNode) {
	            IfNode ifNode = (IfNode) stmt;
	            if (GetIDT(ifNode.getCondition(), locals).asBoolean()) {
	                return interpretListOfStatements(ifNode.getBlock().getStatements(), locals);
	            } else if (ifNode.getElseBranch().isPresent()) {
	                return interpretListOfStatements(ifNode.getElseBranch().get().getBlock().getStatements(), locals);
	            }
	            return new ReturnType(ReturnType.Status.NORMAL);
	        } else if (stmt instanceof ReturnNode) {
	            ReturnNode returnNode = (ReturnNode) stmt;
	            InterpreterDataType value = GetIDT(returnNode.getExpression(), locals);
	            return new ReturnType(ReturnType.Status.RETURN, value.getValue());
	        } else if (stmt instanceof WhileNode) {
	            WhileNode whileNode = (WhileNode) stmt;
	            while (GetIDT(whileNode.getCondition(), locals).asBoolean()) {
	                ReturnType returnType = interpretListOfStatements(whileNode.getBlock().getStatements(), locals);
	                if (returnType.getStatus() == ReturnType.Status.BREAK) {
	                    break;
	                } else if (returnType.getStatus() == ReturnType.Status.RETURN) {
	                    return returnType;
	                }
	            }
	            return new ReturnType(ReturnType.Status.NORMAL);
	        } else {
	            // Handle unknown statement types.
	            throw new IllegalArgumentException("Unknown node type encountered: " + stmt.getClass().getSimpleName());
	        }
	    }

	    public ReturnType interpretListOfStatements(LinkedList<StatementNode> statements, HashMap<String, InterpreterDataType> locals) {
	        for (StatementNode stmt : statements) {
	            ReturnType result = processStatement(stmt, locals);
	            if (result.getStatus() != ReturnType.Status.NORMAL) {
	                return result; 
	            }
	        }
	        return new ReturnType(ReturnType.Status.NORMAL); 
	    }
	    
	    //============== Int 4 DONE ===============//
	    
	    public void interpretProgram(ProgramNode programNode) {
	        // Execute BEGIN blocks
	        for (BlockNode block : programNode.getBeginBlocks()) {
	            interpretBlock(block);
	        }

	        while (lineManager.splitAndAssign()) { // Process each line
	            for (BlockNode block : programNode.getBlocks()) {
	                interpretBlock(block);
	            }
	        }

	        // Execute END blocks
	        for (BlockNode block : programNode.getEndBlocks()) {
	            interpretBlock(block);
	        }
	    }

	    
	    public ReturnType interpretBlock(BlockNode block) {
	        if (block.getCondition().isEmpty() || GetIDT(block.getCondition().get(), globalVariables).asBoolean()) {
	            return interpretListOfStatements(block.getStatements(), globalVariables);
	        }
	        return new ReturnType(ReturnType.Status.NORMAL);
	    }
	    
	    private InterpreterDataType runFunctionCall(FunctionCallNode node, HashMap<String, InterpreterDataType> locals) throws Exception {
	        FunctionDefinitionNode functionDef = functionDefinitions.get(node.getFunctionName());
	        if (functionDef == null) {
	            throw new IllegalArgumentException("Function " + node.getFunctionName() + " is not defined.");
	        }

	        // Map parameters
	        HashMap<String, InterpreterDataType> paramMap = new HashMap<>();
	        List<String> paramNames = functionDef.getParameters();
	        List<Node> arguments = node.getArguments();

	        if (!functionDef.isVariadic() && paramNames.size() != arguments.size()) {
	            throw new IllegalArgumentException("Parameter count mismatch for function " + node.getFunctionName());
	        }

	        for (int i = 0; i < paramNames.size(); i++) {
	            paramMap.put(paramNames.get(i), GetIDT(arguments.get(i), locals));
	        }

	        // Handle variadic arguments
	        if (functionDef.isVariadic() && arguments.size() >= paramNames.size()) {
	            List<InterpreterDataType> variadicArgs = new ArrayList<>();
	            for (int i = paramNames.size(); i < arguments.size(); i++) {
	                variadicArgs.add(GetIDT(arguments.get(i), locals));
	            }
	            paramMap.put("variadic", new InterpreterArrayDataType(variadicArgs));
	        }

	     // Execute the function
	        if (functionDef instanceof BuiltInFunctionDefinitionNode) {
	            // Execute the built-in function
	            return ((BuiltInFunctionDefinitionNode) functionDef).execute(paramMap);
	        } else {
	            // Execute the user-defined function
	            ReturnType returnType = interpretListOfStatements(functionDef.getStatements(), paramMap);
	            
	            // Check if the function execution returned a value
	            if (returnType.getStatus() == ReturnType.Status.RETURN && returnType.getValue() != null) {
	                // Function returned a value, wrap it in an InterpreterDataType and return
	                return new InterpreterDataType(returnType.getValue());
	            } else {
	                // Function did not return a value, or it was a different type of return (e.g., NORMAL, BREAK, CONTINUE)
	                return new InterpreterDataType(); // Return a default or empty InterpreterDataType
	            }
	        }
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
