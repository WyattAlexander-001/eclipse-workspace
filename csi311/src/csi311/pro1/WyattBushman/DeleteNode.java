package csi311.pro1.WyattBushman;

public class DeleteNode extends StatementNode {
    private String arrayName;
    private Node indexExpression;

    public DeleteNode(String arrayName, Node indexExpression) {
        this.arrayName = arrayName;
        this.indexExpression = indexExpression;
    }

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName = arrayName;
    }

    public Node getIndexExpression() {
        return indexExpression;
    }

    public void setIndexExpression(Node indexExpression) {
        this.indexExpression = indexExpression;
    }
    @Override
    public String toString() {
        return "delete " + arrayName + "[" + indexExpression.toString() + "];";
    }
}
