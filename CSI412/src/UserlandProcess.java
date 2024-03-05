import java.util.concurrent.Semaphore;

public abstract class UserlandProcess implements Runnable {
    private Thread thread;
    private final Semaphore semaphore;
    private boolean quantumExpired;
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
        return semaphore.availablePermits() == 0;
    }

    public boolean isDone() {
        return !thread.isAlive();
    }

    public void start() {
        if (!thread.isAlive()) {
        	semaphore.release(); // Initially allow the thread to proceed
            thread.start();
        }
    }

    public void stop() {
//        requestStop();
    	try {
    		semaphore.acquire();
    	} catch (InterruptedException e) {
    		Thread.currentThread().interrupt();
    	}
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
            OS.switchProcess();
        } 
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}

