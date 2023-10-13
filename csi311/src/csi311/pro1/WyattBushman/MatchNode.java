package csi311.pro1.WyattBushman;

public class MatchNode extends Node {
    private final Node left;
    private final Node pattern;

    public MatchNode(Node left, Node pattern) {
        this.left = left;
        this.pattern = pattern;
    }

    public Node getLeft() {
        return left;
    }

    public Node getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return left + " ~ " + pattern;
    }
}
