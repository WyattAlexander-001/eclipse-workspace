package csi311.pro1.WyattBushman;

public class WhileNode extends StatementNode {
    private Node condition;
    private BlockNode block;

    public WhileNode(Node condition, BlockNode block) {
        this.condition = condition;
        this.block = block;
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
        return "while (" + condition.toString() + ") " + block.toString();
    }
}
