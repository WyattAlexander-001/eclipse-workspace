package csi311.pro1.WyattBushman;

import java.util.List;

public class FunctionCallNode extends StatementNode {
    private String functionName;
    private List<Node> arguments;

    public FunctionCallNode(String functionName, List<Node> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<Node> getArguments() {
        return arguments;
    }

    public void setArguments(List<Node> arguments) {
        this.arguments = arguments;
    }
    
    @Override
    public String toString() {
        StringBuilder callRepresentation = new StringBuilder(functionName + "(");
        for (int i = 0; i < arguments.size(); i++) {
            callRepresentation.append(arguments.get(i).toString());
            if (i < arguments.size() - 1) {
                callRepresentation.append(", ");
            }
        }
        callRepresentation.append(");");
        return callRepresentation.toString();
    }
}
