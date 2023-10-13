package csi311.pro1.WyattBushman;

public class CompareNode extends Node {
    private Node left;
    private Node right;
    private TokenType comparisonType;

    public CompareNode(Node left, Node right, TokenType comparisonType) {
        this.left = left;
        this.right = right;
        this.comparisonType = comparisonType;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public TokenType getComparisonType() {
        return comparisonType;
    }

    @Override
    public String toString() {
        return left + " " + comparisonType.name() + " " + right;
    }
}
