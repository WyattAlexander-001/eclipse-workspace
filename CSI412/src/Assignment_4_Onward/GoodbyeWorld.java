package Assignment_4_Onward;
public class GoodbyeWorld extends UserlandProcess {
    @Override
    public void main() {
        while (true) {
            System.out.println("Goodbye World");
            cooperate();
            // Debugging: Check if this part is reached
//            System.out.println("Goodbye World loop iteration complete");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            }
        }
    }
}