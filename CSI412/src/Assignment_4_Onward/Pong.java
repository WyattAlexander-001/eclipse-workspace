package Assignment_4_Onward;

public class Pong extends UserlandProcess {
    public Pong(String name) {
        super();
    }

    @Override
    public void run() {
        int pingPid = OS.getPidByName("Ping");
        
        while (true) {
            KernelMessage receivedMessage = new KernelMessage(0, 0, 0, null);
            System.out.println("Received in Pong: " + new String(receivedMessage.getData()));
            
            String messageContent = "Pong " + new String(receivedMessage.getData()).split(" ")[1]; // Echo back with "Pong"
            byte[] data = messageContent.getBytes();
            KernelMessage message = new KernelMessage(this.getPid(), pingPid, 0, data);
            Kernel kernel = new Kernel();
			kernel.sendMessage(message);
            System.out.println("Sent from Pong: " + messageContent);
        }
    }

	@Override
	public void main() {
		// TODO Auto-generated method stub
		
	}
}
