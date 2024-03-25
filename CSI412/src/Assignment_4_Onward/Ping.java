package Assignment_4_Onward;

public class Ping extends UserlandProcess {
    public Ping(String name) {
        super();
    }

    @Override
    public void run() {
        int pongPid = OS.getPidByName("Pong"); // Ensure OS has this functionality
        int messageType = 1; // Just an example message type
        int count = 0;

        while (true) {
            String messageStr = "Ping " + count;
            KernelMessage message = new KernelMessage(this.getPid(), pongPid, messageType, messageStr.getBytes());
            Kernel kernel = new Kernel();
			kernel.sendMessage(message); // OS interface to send a message
            
            KernelMessage receivedMessage = new KernelMessage(0, 0, 0, null); 
            if (receivedMessage != null) {
                System.out.println("Ping received: " + new String(receivedMessage.getData()));
                count++;
            }
        }
    }

	@Override
	public void main() {
		// TODO Auto-generated method stub
		
	}
}
