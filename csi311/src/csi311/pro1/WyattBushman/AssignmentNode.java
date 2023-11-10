package csi311.pro1.WyattBushman;

public class AssignmentNode extends StatementNode {
    private final Node target;  
    private final Node expression;  

    public AssignmentNode(Node target, Node expression) {
        this.target = target;
        this.expression = expression;
    }

    public Node getTarget() {
        return target;
    }

    public Node getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return target + " = " + expression;
    }
}

