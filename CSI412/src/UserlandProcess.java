import java.util.concurrent.Semaphore;

public abstract class UserlandProcess implements Runnable {
    private Thread thread;
    private final Semaphore semaphore;
    private boolean quantumExpired;
    private int pid;

    public UserlandProcess() {
        this.semaphore = new Semaphore(1); // Initialize to 1 to allow the thread to proceed immediately
        this.quantumExpired = false;
        this.thread = new Thread(this);
        thread.start();
    }

    public abstract void main();

    public void requestStop() {
        quantumExpired = true;
    }

    public boolean isStopped() {
        return !thread.isAlive() || semaphore.availablePermits() == 0;
    }

    public boolean isDone() {
        return !thread.isAlive();
    }

    public void start() {
//        if (!thread.isAlive()) {
//        	semaphore.release(); // Initially allow the thread to proceed
//            thread.start();
//        }
        
        semaphore.release();
    }

    public void stop() {
        requestStop();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); 
            if (!quantumExpired) {
                main();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        } finally {
            semaphore.release(); 
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

