package csi311.pro1.WyattBushman;

public class ForEachNode extends StatementNode {
    private String iterator;
    private String collection;
    private BlockNode block;

    public ForEachNode(String iterator, String collection, BlockNode block) {
        this.iterator = iterator;
        this.collection = collection;
        this.block = block;
    }

    public String getIterator() {
        return iterator;
    }

    public void setIterator(String iterator) {
        this.iterator = iterator;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public BlockNode getBlock() {
        return block;
    }

    public void setBlock(BlockNode block) {
        this.block = block;
    }
    @Override
    public String toString() {
        return "for (" + iterator + " in " + collection + ") " + block.toString();
    }
}
