package csi311.pro1.WyattBushman;

import java.util.Optional;

public class IfNode extends StatementNode {
    private Node condition;
    private BlockNode block;
    private IfNode elseBranch;

    public IfNode(Node condition, BlockNode block) {
        this.condition = condition;
        this.block = block;
        this.elseBranch = null;
    }

    public Node getCondition() {
        return condition;
    }

    public BlockNode getBlock() {
        return block;
    }

    public Optional<IfNode> getElseBranch() {
        return Optional.ofNullable(elseBranch);
    }

    public void setElseBranch(IfNode elseBranch) {
        this.elseBranch = elseBranch;
    }

    @Override
    public String toString() {
        StringBuilder ifRepresentation = new StringBuilder();
        ifRepresentation.append("if (").append(condition.toString()).append(") ").append(block.toString());

        if(elseBranch != null) {
            ifRepresentation.append(" else ").append(elseBranch.toString());
        }

        return ifRepresentation.toString();
    }
}
