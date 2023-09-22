package csi311.pro1.WyattBushman;

import java.util.LinkedList;

public class FunctionDefinitionNode extends Node {
    private String functionName;
    private LinkedList<String> parameters;
    private LinkedList<StatementNode> statements;

    public FunctionDefinitionNode(String name) {
        this.functionName = name;
        this.parameters = new LinkedList<>();
        this.statements = new LinkedList<>();
    }

    @Override
    public String toString() {
        StringBuilder functionRepresentation = new StringBuilder();

        // Append function name
        functionRepresentation.append("function ").append(functionName).append("(");

        // Append each parameter
        for (int i = 0; i < parameters.size(); i++) {
            functionRepresentation.append(parameters.get(i));
            if (i < parameters.size() - 1) { // If not the last parameter, append a comma
                functionRepresentation.append(", ");
            }
        }

        functionRepresentation.append(") {\n");

        // Append each statement
        for (StatementNode statement : statements) {
            functionRepresentation.append("    ").append(statement.toString()).append("\n");
        }

        functionRepresentation.append("}");

        return functionRepresentation.toString();
    }


    public String getFunctionName() {
    	return functionName;
    }
    
    public LinkedList<String> getParameters(){
    	return parameters;
    }
    
    public LinkedList<StatementNode> getStatements(){
    	return statements;
    }
}

