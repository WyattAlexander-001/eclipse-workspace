package csi311.pro1.WyattBushman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterpreterDataType {
    private String value;
    private List<String> arrayValue;

    // Constructor without an initial value. Initializes the value to an empty string.
    public InterpreterDataType() {
        this.value = "";
    }

    // Constructor with an initial value for the 'value' field.
    public InterpreterDataType(String value) {
        this.value = value;
    }
    
    public static InterpreterDataType fromArray(String[] parts) {
        InterpreterDataType idt = new InterpreterDataType();
        idt.arrayValue = new ArrayList<>(Arrays.asList(parts));
        return idt;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public List<String> getArrayValue() {
		return arrayValue;
	}

	public void setArrayValue(List<String> arrayValue) {
		this.arrayValue = arrayValue;
	}
}
