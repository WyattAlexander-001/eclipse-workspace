package csi311.pro1.WyattBushman;

public class TernaryNode extends Node {
    private final Node condition; // The condition to be checked
    private final Node trueBranch; // The branch if the condition is true
    private final Node falseBranch; // The branch if the condition is false

    public TernaryNode(Node condition, Node trueBranch, Node falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public Node getCondition() {
        return condition;
    }

    public Node getTrueBranch() {
        return trueBranch;
    }

    public Node getFalseBranch() {
        return falseBranch;
    }

    @Override
    public String toString() {
        return condition + " ? " + trueBranch + " : " + falseBranch;
    }
}

