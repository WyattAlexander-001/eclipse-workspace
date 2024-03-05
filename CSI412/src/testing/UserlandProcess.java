import java.util.concurrent.Semaphore;

public abstract class UserlandProcess implements Runnable {
    private Thread thread;
    private final Semaphore semaphore;
    private boolean quantumExpired;
    private int pid;

    public UserlandProcess() {
        this.semaphore = new Semaphore(1);
        this.thread = new Thread(this);
        this.quantumExpired = false;

    }

    public abstract void main();
    
    // Requests to stop process
    public void requestStop() {
//        System.out.println("Requesting stop: " + this);
        quantumExpired = true;
    }

    // Checks if process is stopped
    public boolean isStopped() {
        return semaphore.availablePermits() == 0;
    }

    // Checks if process is done
    public boolean isDone() {
        return !thread.isAlive();
    }

    // Starts process
    public void start() {
//        System.out.println("Starting process: " + this);
        semaphore.release(); // Make sure this is releasing the semaphore
        if (!thread.isAlive()) {
            thread.start(); // Start the thread if it's not already running
        }
    }

    // Stop the process
    public void stop() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Run method from the Runnable interface
    @Override
    public void run() {
        try {
            semaphore.acquire();
            main();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    //Debugging    
//    public void cooperate() {
//        if (quantumExpired == true) {
////            System.out.println("Cooperating (quantum expired): " + this);
//            quantumExpired = false;
//            OS.switchProcess();
//        }
//    }
    
    public void cooperate() {
        if (quantumExpired) {
            quantumExpired = false;
            semaphore.release(); // Indicate this process is yielding control.
            OS.switchProcess(); // Ask the OS to switch to the next process.
        }
    }

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
}
