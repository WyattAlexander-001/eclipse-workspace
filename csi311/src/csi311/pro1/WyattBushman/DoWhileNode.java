package csi311.pro1.WyattBushman;

public class DoWhileNode extends StatementNode {
    private Node condition;
    private BlockNode block;

    public DoWhileNode(BlockNode block, Node condition) {
        this.block = block;
        this.condition = condition;
    }

    public Node getCondition() {
        return condition;
    }

    public void setCondition(Node condition) {
        this.condition = condition;
    }

    public BlockNode getBlock() {
        return block;
    }

    public void setBlock(BlockNode block) {
        this.block = block;
    }
    @Override
    public String toString() {
        return "do " + block.toString() + " while (" + condition.toString() + ");";
    }
}
