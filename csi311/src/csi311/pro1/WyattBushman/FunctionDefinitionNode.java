package csi311.pro1.WyattBushman;

import java.util.LinkedList;

public class FunctionDefinitionNode extends Node {
    private String functionName;
    private LinkedList<String> parameters;
    private LinkedList<StatementNode> statements;
    private boolean isVariadic;

    public FunctionDefinitionNode(String name) {
        this.functionName = name;
        this.parameters = new LinkedList<>();
        this.statements = new LinkedList<>();
    }
    
    public FunctionDefinitionNode(String name, boolean isVariadic) {
        this.functionName = name;
        this.parameters = new LinkedList<>();
        this.statements = new LinkedList<>();
        this.isVariadic = isVariadic;
    }
    
    public boolean isVariadic() {
        return isVariadic;
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
    
    public void addStatement(StatementNode statement) {
        if (statement == null) {
            throw new IllegalArgumentException("Statement cannot be null");
        }
        this.statements.add(statement);
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

