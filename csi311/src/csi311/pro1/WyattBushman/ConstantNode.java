package csi311.pro1.WyattBushman;

public class ConstantNode extends Node {
    private String value;

    public ConstantNode(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
