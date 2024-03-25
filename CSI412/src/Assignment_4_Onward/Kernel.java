package Assignment_4_Onward;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Kernel implements Runnable {
    private final Semaphore semaphore = new Semaphore(0);
    private final Scheduler scheduler = new Scheduler(); 
    private int maxPid;
    private VFS vfs;
    private int[][] processDeviceMappings = new int[10][10];
    private Map<Integer, PCB> pidToPCBMap = new HashMap<>();



    public void start() {
        semaphore.release();
    	new Thread(this).start();
    }
    
    

    @Override
    public void run() {
    	System.out.println("Kernel THREAD STARTED");
        while (true) {
        	System.out.println("WE ARE INSIDE THE WHILE LOOP OF KERNEL RUN");
            try {
            	System.out.println("BEFORE THE ACQUIRE!");
            	System.out.println("OUR CURRENT CALL IS: " + OS.getCurrentCall());
                semaphore.acquire();
                System.out.println("Kernel ACQUIRED SEMAPHORE");
	                switch (OS.getCurrentCall()) {
	                    case CREATE_PROCESS:
	                        createProcess(); 
	                        System.out.println("CREATE_PROCESS handled");
	                        break;
	                    case SWITCH_PROCESS:
	                        switchProcess(); 
	                        System.out.println("SWITCH_PROCESS handled");
	                        break;
	                    case SLEEP: 
	                        int milliseconds = (Integer)OS.getParameters().get(0); // Retrieve sleep duration
	                        sleepProcess(milliseconds); // Method to manage the sleep request
	                    default:
	                    	break;
	                }
                // Run the next process from the scheduler
                if (scheduler.currentlyRunning != null) {
                	System.out.println("Running process: " + scheduler.currentlyRunning);
                	scheduler.currentlyRunning.getUserlandProcess().start(); 
                } else {
                	System.out.println("No process currently running");
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
	    scheduler.sleep(milliseconds); 
	}
	
	//Assignment 3:
    public int Open(String s) {
        PCB currentPCB = getScheduler().getCurrentlyRunningPCB();
        if (currentPCB == null) return -1; // No currently running process

        int index = -1;
        for (int i = 0; i < currentPCB.getDeviceIds().length; i++) {
            if (currentPCB.getDeviceIds()[i] == -1) { // Find an empty spot
                index = i;
                break;
            }
        }
        if (index == -1) return -1; // No empty spot found

        int vfsId = vfs.Open(s); 
        if (vfsId == -1) return -1; // VFS open failed

        currentPCB.setDeviceId(index, vfsId); // Map VFS ID to process-specific ID
        return index; // Return the process-specific device ID
    }
	
	
    public void Close(int id) {
        PCB currentPCB = getCurrentPCB();
        if (currentPCB != null && id >= 0 && id < currentPCB.getDeviceIds().length) {
            int vfsId = currentPCB.getDeviceIds()[id];
            if (vfsId != -1) {
                vfs.Close(vfsId); // Close the device in VFS
                currentPCB.setDeviceId(id, -1); // Reset the device ID in PCB
            }
        }
    }
    
    public byte[] Read(int id, int size) {
        PCB currentPCB = getCurrentPCB();
        if (currentPCB == null || id < 0 || id >= currentPCB.getDeviceIds().length) return new byte[0];
        
        int vfsId = currentPCB.getDeviceIds()[id];
        if (vfsId == -1) return new byte[0]; // No corresponding VFS device found
        
        return vfs.Read(vfsId, size);
    }

    public void Seek(int id, int to) {
        PCB currentPCB = getCurrentPCB();
        if (currentPCB == null || id < 0 || id >= currentPCB.getDeviceIds().length) return;
        
        int vfsId = currentPCB.getDeviceIds()[id];
        if (vfsId == -1) return; // No corresponding VFS device found
        
        vfs.Seek(vfsId, to);
    }

    public int Write(int id, byte[] data) {
        PCB currentPCB = getCurrentPCB();
        if (currentPCB == null || id < 0 || id >= currentPCB.getDeviceIds().length) return 0;
        
        int vfsId = currentPCB.getDeviceIds()[id];
        if (vfsId == -1) return 0; // No corresponding VFS device found
        
        return vfs.Write(vfsId, data);
    }
    
    public void sendMessage(KernelMessage km) {
        PCB recipientPCB = pidToPCBMap.get(km.getTargetPid());
        if (recipientPCB != null) {
            recipientPCB.receiveMessage(new KernelMessage(km)); // Utilizing the copy constructor
        }
    }
    
    public void registerProcess(PCB pcb) {
        pidToPCBMap.put(pcb.getPid(), pcb);
    }
    
    public void unregisterProcess(int pid) {
        pidToPCBMap.remove(pid);
    }
    
    public KernelMessage waitForMessage() {
        PCB currentPCB = getScheduler().getCurrentlyRunningPCB();
        if (currentPCB != null && currentPCB.hasMessages()) {
            return currentPCB.getNextMessage();
        }
        return null;
    }
    
    

}
