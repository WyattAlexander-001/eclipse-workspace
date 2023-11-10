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

	public boolean asBoolean() {
        // Trim the value to ignore leading and trailing whitespace
        String trimmedValue = value.trim();

        // Check if the value is "true" or "false" directly (if you want to support these as valid booleans)
        if ("true".equalsIgnoreCase(trimmedValue)) {
            return true;
        } else if ("false".equalsIgnoreCase(trimmedValue)) {
            return false;
        }

        // Attempt to parse the value as a float
        try {
            float num = Float.parseFloat(trimmedValue);
            // Any non-zero number should return true
            return num != 0;
        } catch (NumberFormatException e) {
            // If it cannot be parsed to a float, check if it's a non-empty string
            return !trimmedValue.isEmpty();
        }
    }
	
    public boolean isArray() {
        return arrayValue != null;
    }
    
    public String asString() {
        return value; 
    }
}
