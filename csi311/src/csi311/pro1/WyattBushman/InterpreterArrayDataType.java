package csi311.pro1.WyattBushman;

import java.util.HashMap;

public class InterpreterArrayDataType extends InterpreterDataType {
    private HashMap<String, InterpreterDataType> arrayData;

    public InterpreterArrayDataType() {
        this.arrayData = new HashMap<>();
    }

    public void addItem(String key, InterpreterDataType value) {
        arrayData.put(key, value);
    }

    public InterpreterDataType getItem(String key) {
        return arrayData.get(key);
    }

    public HashMap<String, InterpreterDataType> getArrayData() {
        return arrayData;
    }
}
