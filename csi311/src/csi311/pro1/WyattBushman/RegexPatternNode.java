package csi311.pro1.WyattBushman;

public class RegexPatternNode extends Node {
    private final String value;

    public RegexPatternNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
