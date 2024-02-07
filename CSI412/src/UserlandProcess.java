import java.util.concurrent.Semaphore;

public abstract class UserlandProcess implements Runnable {
    private Thread thread;
    private final Semaphore semaphore;
    private volatile boolean quantumExpired;
    private int pid;

    public UserlandProcess() {
        this.semaphore = new Semaphore(0); // Start with semaphore unavailable to enforce explicit control
        this.thread = new Thread(this);
        this.quantumExpired = false;
    }

    public abstract void main();

    public void requestStop() {
        quantumExpired = true;
        semaphore.release(); // Allow run() to proceed and check quantumExpired
    }

    public boolean isStopped() {
        return !thread.isAlive();
    }

    public boolean isDone() {
        return !thread.isAlive();
    }

    public void start() {
        if (!thread.isAlive()) {
            thread.start();
            semaphore.release(); // Initially allow the thread to proceed
        }
    }

    public void stop() {
        requestStop(); // Leverage requestStop for stopping logic
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                semaphore.acquire(); // Wait for permission to proceed
                if (quantumExpired) {
                    break; // Exit if stop has been requested
                }
                main();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    public void cooperate() {
        if (quantumExpired) {
            quantumExpired = false; // Reset the flag
            // Note: Assuming OS.switchProcess() effectively manages the switch without requiring semaphore release here
            OS.switchProcess();
        } else {
            semaphore.release(); // Allow the thread to continue or another to proceed
        }
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}

