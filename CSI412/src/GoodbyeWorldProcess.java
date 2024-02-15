public class GoodbyeWorldProcess extends UserlandProcess {
    @Override
    public void main() {
        while (!isStopped()) {
            System.out.println("Goodbye, World!");
            cooperate(); // Yield control back to the scheduler
        }
    }
}