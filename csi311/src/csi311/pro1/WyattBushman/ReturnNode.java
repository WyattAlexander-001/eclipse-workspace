package csi311.pro1.WyattBushman;

public class ReturnNode extends StatementNode {
    private Node expression;

    public ReturnNode(Node expression) {
        this.expression = expression;
    }


    public Node getExpression() {
        return expression;
    }

    public void setExpression(Node expression) {
        this.expression = expression;
    }
    
    @Override
    public String toString() {
        return "return " + expression.toString() + ";";
    }
}
