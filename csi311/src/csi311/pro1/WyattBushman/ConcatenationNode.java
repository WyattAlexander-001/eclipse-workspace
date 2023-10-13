package csi311.pro1.WyattBushman;

public class ConcatenationNode extends Node {
    private final Node left;
    private final Node right;

    public ConcatenationNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " + " + right;
    }
}
