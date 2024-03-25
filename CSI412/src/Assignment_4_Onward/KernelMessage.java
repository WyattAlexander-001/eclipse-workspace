package Assignment_4_Onward;

public class KernelMessage {
    private final int senderPid;
    private final int targetPid;
    private final int messageType;
    private final byte[] data;

    // Constructor
    public KernelMessage(int senderPid, int targetPid, int messageType, byte[] data) {
        this.senderPid = senderPid;
        this.targetPid = targetPid;
        this.messageType = messageType;
        this.data = data.clone(); // Ensure a deep copy of the data array
    }

    // Copy constructor
    public KernelMessage(KernelMessage other) {
        this.senderPid = other.senderPid;
        this.targetPid = other.targetPid;
        this.messageType = other.messageType;
        this.data = other.data.clone(); // Ensure a deep copy of the data array
    }

    @Override
    public String toString() {
        return String.format("From: %d, To: %d, Type: %d, Data: %s", 
                             senderPid, targetPid, messageType, new String(data));
    }

    public int getSenderPid() {
        return senderPid;
    }

    public int getTargetPid() {
        return targetPid;
    }

    public int getMessageType() {
        return messageType;
    }

    public byte[] getData() {
        return data.clone(); // Return a copy to preserve encapsulation
    }
}
