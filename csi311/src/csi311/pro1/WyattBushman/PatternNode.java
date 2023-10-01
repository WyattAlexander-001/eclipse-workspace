package csi311.pro1.WyattBushman;

public class PatternNode extends Node {
    private String pattern;

    public PatternNode(String pattern) {
        this.setPattern(pattern);
    }

    @Override
    public String toString() {
        return pattern;
    }

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
