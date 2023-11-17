package csi311.pro1.WyattBushman;

import java.util.HashMap;
import java.util.List;

public class InterpreterArrayDataType extends InterpreterDataType {
    private HashMap<String, InterpreterDataType> arrayData;

    public InterpreterArrayDataType() {
        this.arrayData = new HashMap<>();
    }
    
    public InterpreterArrayDataType(List<InterpreterDataType> list) {
        this();
        for (int i = 0; i < list.size(); i++) {
            arrayData.put(String.valueOf(i), list.get(i));
        }
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
