package Assignment_4_Onward;
public class IdleProcess extends UserlandProcess {
    @Override
    public void main() {
        while (true) {
            cooperate();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Set the interrupt flag again
                break; // Optional: Exit loop if interrupted
            }
        }
    }
}