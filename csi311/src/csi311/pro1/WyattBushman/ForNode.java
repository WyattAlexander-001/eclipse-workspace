package csi311.pro1.WyattBushman;

public class ForNode extends StatementNode {
    private Node initialization;
    private Node condition;
    private Node iteration;
    private BlockNode block;

    public ForNode(Node initialization, Node condition, Node iteration, BlockNode block) {
        this.initialization = initialization;
        this.condition = condition;
        this.iteration = iteration;
        this.block = block;
    }

    public Node getInitialization() {
        return initialization;
    }

    public void setInitialization(Node initialization) {
        this.initialization = initialization;
    }

    public Node getCondition() {
        return condition;
    }

    public void setCondition(Node condition) {
        this.condition = condition;
    }

    public Node getIteration() {
        return iteration;
    }

    public void setIteration(Node iteration) {
        this.iteration = iteration;
    }

    public BlockNode getBlock() {
        return block;
    }

    public void setBlock(BlockNode block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "for (" + initialization.toString() + "; " + condition.toString() + "; " + iteration.toString() + ") " + block.toString();
    }
}
