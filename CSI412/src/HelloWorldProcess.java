public class HelloWorldProcess extends UserlandProcess {
    @Override
    public void main() {
        while (!isStopped()) {
            System.out.println("Hello, World!");
            OS.Sleep(5000); // Sleep for 5 seconds as an example
            cooperate(); // Yield control back to the scheduler
        }
    }
}