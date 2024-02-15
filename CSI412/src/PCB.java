import java.time.Instant;

public class PCB {
    private static int nextPid = 1; // Static counter to assign unique PIDs
    private int pid; // Process ID
    private UserlandProcess userlandProcess; // Associated userland process
    private Instant wakeUpTime;

    public PCB(UserlandProcess up) {
        this.userlandProcess = up;
        this.pid = nextPid++; // Assign and increment the PID
    }

    public void stop() {
        userlandProcess.stop(); // Request the userland process to stop
        while (!userlandProcess.isStopped()) {
            try {
                Thread.sleep(10); // Wait until the process is confirmed stopped
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isDone() {
        return userlandProcess.isDone(); // Check if the userland process has completed execution
    }

    public void run() {
        userlandProcess.start(); // Start the userland process
    }

    // Getters for PID and UserlandProcess, if needed
    public int getPid() {
        return pid;
    }

    public UserlandProcess getUserlandProcess() {
        return userlandProcess;
    }
    
    // Method to set the wake-up time
    public void setWakeUpTime(Instant wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    // Method to get the wake-up time
    public Instant getWakeUpTime() {
        return wakeUpTime;
    }

    // Method to check if the process is sleeping
    public boolean isSleeping() {
        return wakeUpTime != null && Instant.now().isBefore(wakeUpTime);
    }
}
