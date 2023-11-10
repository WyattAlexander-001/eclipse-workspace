package csi311.pro1.WyattBushman;


public class ReturnType {

    public enum Status {
        NORMAL, // Used when the statement is executed without altering the flow
        BREAK,  // Used to signal a break out of the closest enclosing loop
        CONTINUE, // Used to signal the start of the next iteration in the closest enclosing loop
        RETURN // Used to signal a return out of the current function/method
    }

    private final Status status;
    private final String value;  


    public ReturnType(Status status) {
        this.status = status;
        this.value = null; // No value associated with this return type
    }


    public ReturnType(Status status, String value) {
        this.status = status;
        this.value = value;
    }

    public Status getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ReturnType{" +
                "status=" + status +
                ", value='" + value + '\'' +
                '}';
    }
}
