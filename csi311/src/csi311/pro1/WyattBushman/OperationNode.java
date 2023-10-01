package csi311.pro1.WyattBushman;

import java.util.Optional;

public class OperationNode extends Node {
    private Node left;
    private Optional<Node> right;  
    private OperationType operationType;
	
    public OperationNode(Node left, Optional<Node> right, OperationType operationType) {
        this.left = left;
        this.right = right;
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Append the left node
        result.append(left.toString());

        if (right.isPresent()) {
            result.append(" ").append(operationType).append(" ").append(right.get().toString());
        } else {
            // If there's no right node, it's a unary operation
            switch (operationType) {
                case LOGICAL_NOT:
                case UNARY_NEGATIVE:
                case UNARY_POSITIVE:
                case PREFIX_INCREMENT:
                case PREFIX_DECREMENT:
                    result.insert(0, operationType + " ");
                    break;
                case POSTFIX_INCREMENT:
                case POSTFIX_DECREMENT:
                    result.append(" " + operationType);
                    break;
                default:
                    throw new IllegalStateException("Unknown unary operation: " + operationType);
            }
        }
        return result.toString();
    }

	public Optional<Node> getRight() {
		return right;
	}
	public void setRight(Optional<Node> right) {
		this.right = right;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}


}

