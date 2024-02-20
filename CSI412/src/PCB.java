import java.time.Instant;

public class PCB {
    private static int nextPid = 1; // Static counter to assign unique PIDs
    private int pid; // Process ID
    private UserlandProcess userlandProcess; // Associated userland process
    private Instant wakeUpTime;
    private Priority priority; // Enum for process priority
    private int timeoutCounter = 0; // Counter for timeouts leading to potential demotion

    public PCB(UserlandProcess up) {
        this.userlandProcess = up;
        this.pid = nextPid++; 
    }
    
    public PCB(UserlandProcess up, Priority priority) {
        this.userlandProcess = up;
        this.priority = priority;
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
        return userlandProcess.isDone(); 
    }

    public void run() {
        userlandProcess.start(); 
    }

    public int getPid() {
        return pid;
    }

    public UserlandProcess getUserlandProcess() {
        return userlandProcess;
    }
    
    public void setWakeUpTime(Instant wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public Instant getWakeUpTime() {
        return wakeUpTime;
    }

    public boolean isSleeping() {
        return wakeUpTime != null && Instant.now().isBefore(wakeUpTime);
    }
    
    public void incrementTimeoutCounter() {
        timeoutCounter++;
    }

    public void checkAndDemote() {
        if (timeoutCounter > 5) { // Arbitrary threshold for demotion
            switch (priority) {
                case REAL_TIME:
                    priority = Priority.INTERACTIVE;
                    break;
                case INTERACTIVE:
                    priority = Priority.BACKGROUND;
                    break;
                default:
                    break;
            }
            timeoutCounter = 0; // Reset the counter after demotion
        }
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getTimeoutCounter() {
        return timeoutCounter;
    }

    public void resetTimeoutCounter() {
        timeoutCounter = 0;
    }

}
