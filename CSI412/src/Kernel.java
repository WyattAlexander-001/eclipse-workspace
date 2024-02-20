import java.util.concurrent.Semaphore;

public class Kernel implements Runnable {
    private Thread thread;
    private final Semaphore semaphore;
    private Scheduler scheduler; 
    private int maxPid;

    public Kernel() {
        this.semaphore = new Semaphore(0);
        this.thread = new Thread(this);
        this.scheduler = new Scheduler(); 
        thread.start();
    }

    public void start() {
        semaphore.release();
    }

    @Override
    public void run() {
    	System.out.println("Kernel THREAD STARTED");
        while (true) {
            try {
                semaphore.acquire();
//                System.out.println("Kernel ACQUIRED SEMAPHORE");
                if (OS.getCurrentCall() != CallType.NONE) {
	                switch (OS.getCurrentCall()) {
	                    case CREATE_PROCESS:
	                        createProcess(); 
//	                        System.out.println("CREATE_PROCESS handled");
	                        break;
	                    case SWITCH_PROCESS:
	                        switchProcess(); 
//	                        System.out.println("SWITCH_PROCESS handled");
	                        break;
	                    case SLEEP: 
	                        int milliseconds = (Integer)OS.getParameters().get(0); // Retrieve sleep duration
	                        sleepProcess(milliseconds); // Method to manage the sleep request
					default:
						break;
	                }
                }

                // Run the next process from the scheduler
                if (scheduler.currentlyRunning != null) {
//                	System.out.println("Running process: " + scheduler.currentlyRunning);
                	scheduler.currentlyRunning.getUserlandProcess().start(); 
                } else {
//                	System.out.println("No process currently running");
                }
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
        }
    }
    
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    
    private void createProcess() {
        UserlandProcess newProcess = (UserlandProcess) OS.getParameters().get(0);
        scheduler.createProcess(newProcess);
    }

    private void switchProcess() {
        scheduler.switchProcess();
    }

	public int getMaxPid() {
		return maxPid;
	}

	public void setMaxPid(int maxPid) {
		this.maxPid = maxPid;
	}
	
	private PCB getCurrentPCB() {
	    return scheduler.getCurrentlyRunningPCB(); // Retrieve the currently executing PCB from the Scheduler
	}
	
	
	
	private void sleepProcess(int milliseconds) {
	    scheduler.sleep(milliseconds); // Ensure this matches the method name in Scheduler
	}


}
