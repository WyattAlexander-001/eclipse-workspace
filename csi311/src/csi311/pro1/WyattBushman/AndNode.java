package csi311.pro1.WyattBushman;

public class AndNode extends Node {
    private final Node left;
    private final Node right;

    public AndNode(Node left, Node right) {
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
        return left + " && " + right;
    }
}
