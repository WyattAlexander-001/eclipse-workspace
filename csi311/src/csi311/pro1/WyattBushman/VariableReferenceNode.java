package csi311.pro1.WyattBushman;

import java.util.Optional;

public class VariableReferenceNode extends Node {
    private String name;
    private Optional<Node> indexExpression;

    public VariableReferenceNode(String name, Optional<Node> indexExpression) {
        this.setName(name);
        this.setIndexExpression(indexExpression);
    }

    @Override
    public String toString() {
        if(indexExpression.isPresent()) {
            return name + "[" + indexExpression.get().toString() + "]";
        } else {
            return name;
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Optional<Node> getIndexExpression() {
		return indexExpression;
	}

	public void setIndexExpression(Optional<Node> indexExpression) {
		this.indexExpression = indexExpression;
	}


}
