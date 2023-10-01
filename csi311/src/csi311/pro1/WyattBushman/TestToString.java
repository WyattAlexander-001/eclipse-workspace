package csi311.pro1.WyattBushman;

import java.util.Optional;

public class TestToString {

    public static void main(String[] args) {
        // Test ConstantNode
        ConstantNode constant = new ConstantNode("Hello");
        System.out.println("ConstantNode: " + constant.toString());

        // Test PatternNode
        PatternNode pattern = new PatternNode("[a-z]");
        System.out.println("PatternNode: " + pattern.toString());

        // Test VariableReferenceNode for simple variable
        VariableReferenceNode variable = new VariableReferenceNode("x", Optional.empty());
        System.out.println("VariableReferenceNode (simple variable): " + variable.toString());

        // Test VariableReferenceNode for array reference
        ConstantNode index = new ConstantNode("3");
        VariableReferenceNode arrayReference = new VariableReferenceNode("y", Optional.of(index));
        System.out.println("VariableReferenceNode (array reference): " + arrayReference.toString());
    }
}
