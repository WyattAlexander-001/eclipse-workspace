package Assignment_4_Onward;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class Kernel implements Runnable {
    private final Semaphore semaphore = new Semaphore(0);
    private final Scheduler scheduler = new Scheduler(); 
    private int maxPid;
    private VFS vfs;
    private FakeFileSystem fakeFileSystem; //Last
    private int[][] processDeviceMappings = new int[10][10];
    private Map<Integer, PCB> pidToPCBMap = new HashMap<>();
    private boolean[] physicalPages = new boolean[1024]; 

    public Kernel() {
        // Initialize all pages as free
        Arrays.fill(physicalPages, true);
        this.fakeFileSystem = new FakeFileSystem(); // Initialize FakeFileSystem
        this.fakeFileSystem.createSwapFile("swapfile.dat"); // Specify the swap file name
    }



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
    public PCB getPCB(int pid) {
        return pidToPCBMap.get(pid);
    }
    
//    public int allocatePhysicalPageForProcess(int pid, int virtualPage) {
//        PCB pcb = getPCB(pid);
//        if (pcb != null) {
//            int physicalPage = findFreePhysicalPage();
//            if (physicalPage != -1) {
//                // Update the PCB's page table with the new mapping
//                pcb.setPageMapping(virtualPage, physicalPage);
//                return physicalPage;
//            }
//        }
//        return -1; 
//    }
    
    public int allocatePhysicalPageForProcess(int pid, int virtualPage) {
        PCB pcb = getPCB(pid);
        if (pcb != null) {
            int physicalPage = findFreePhysicalPage();
            if (physicalPage != -1) {
                // Update the PCB's page table with the new mapping
                pcb.setPageMapping(virtualPage, physicalPage, -1);
                return physicalPage;
            }
        }
        return -1; 
    }

    
    
    
    public int findFreePhysicalPage() {
        for (int i = 0; i < physicalPages.length; i++) {
            if (physicalPages[i]) { // If true, the page is free
                physicalPages[i] = false; // Mark as used
                return i; // Return the physical page number
            }
        }
        return -1; 
    }
    
    //Last
    public void allocateVirtualMemory(int pid, int numberOfPages) {
        PCB pcb = getPCB(pid); // Retrieve the PCB using the existing method
        if (pcb != null) {
            for (int i = 0; i < numberOfPages; i++) {
                // Find the first available null entry in the page table
                for (int j = 0; j < pcb.getPageTable().length; j++) {
                    if (pcb.getPageTable()[j] == null) {
                        pcb.getPageTable()[j] = new VirtualToPhysicalMapping(); // Placeholder for future physical allocation
                        break;
                    }
                }
            }
        }
    }
    
    public void freeMemory(int pid, int virtualPage) {
        PCB pcb = getPCB(pid);
        if (pcb != null && virtualPage >= 0 && virtualPage < pcb.getPageTable().length) {
            VirtualToPhysicalMapping mapping = pcb.getPageTable()[virtualPage];
            if (mapping != null && mapping.physicalPageNumber != -1) {
                // Free the physical page
                freePhysicalPage(mapping.physicalPageNumber);
                // Set the virtual mapping to null
                pcb.getPageTable()[virtualPage] = null;
            }
        }
    }
    
    private void freePhysicalPage(int physicalPage) {
        if (physicalPage >= 0 && physicalPage < physicalPages.length) {
            physicalPages[physicalPage] = true; // Mark this page as free again
        }
    }
    
    public FakeFileSystem getFakeFileSystem() {
        return fakeFileSystem;
    }
    
    public byte[] readPhysicalMemory(int physicalPageNumber) {
        byte[] physicalMemory = new byte[1024 * 1024]; // Example: 1MB of physical memory
        byte[] data = new byte[1024]; // Data to read from the physical page
        if (physicalPageNumber >= 0 && (physicalPageNumber * 1024 + 1023) < physicalMemory.length) {
            System.arraycopy(physicalMemory, physicalPageNumber * 1024, data, 0, 1024);
        }
        return data;
    }

    
    
    
    




    
    

    

}
