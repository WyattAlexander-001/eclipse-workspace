package csi311.pro1.WyattBushman;

public class ArrayMembershipNode extends Node {
    private final Node element; 
    private final Node array;

    public ArrayMembershipNode(Node element, Node array) {
        this.element = element;
        this.array = array;
    }

    public Node getElement() {
        return element;
    }

    public Node getArray() {
        return array;
    }

    @Override
    public String toString() {
        return element + " in " + array;
    }
}
