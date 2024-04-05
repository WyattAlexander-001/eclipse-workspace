package Assignment_4_Onward;
import java.util.concurrent.Semaphore;

public abstract class UserlandProcess implements Runnable {
    private Thread thread;
    private final Semaphore semaphore;
    private boolean quantumExpired;
    private int pid;
    
    private static byte[] memory = new byte[1 << 20]; // 1MB of memory
    private static int[][] tlb = {{-1, -1}, {-1, -1}}; // TLB with 2 entries initialized to -1



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
    
 // Memory management methods

    public byte ReadMemory(int address) {
        int virtualPage = address / 1024;
        int offset = address % 1024;
        int physicalPage = CheckTLB(virtualPage);
        if (physicalPage == -1) {
            physicalPage = GetMapping(virtualPage);
            UpdateTLB(virtualPage, physicalPage);
        }
        int physicalAddress = physicalPage * 1024 + offset;
        return memory[physicalAddress];
    }

    public void WriteMemory(int address, byte value) {
        int virtualPage = address / 1024;
        int offset = address % 1024;
        int physicalPage = CheckTLB(virtualPage);
        if (physicalPage == -1) {
            physicalPage = GetMapping(virtualPage);
            UpdateTLB(virtualPage, physicalPage);
        }
        int physicalAddress = physicalPage * 1024 + offset;
        memory[physicalAddress] = value;
    }

    // TLB management methods

    private int CheckTLB(int virtualPage) {
        for (int i = 0; i < tlb.length; i++) {
            if (tlb[i][0] == virtualPage) {
                return tlb[i][1];
            }
        }
        return -1; // Not found
    }
    
    private void UpdateTLB(int virtualPage, int physicalPage) {
        // Simple replacement strategy: replace the first entry
        tlb[0][0] = virtualPage;
        tlb[0][1] = physicalPage;
    }
    
 // In UserlandProcess.java
    private int GetMapping(int virtualPage) {
        // Attempt to get the current physical page mapping for the virtual page
        int physicalPage = OS.requestPageMapping(virtualPage);

        if (physicalPage == -1) {
            // The virtual page is not mapped, request a new physical page
            physicalPage = OS.allocatePhysicalPage(virtualPage);
            
            if (physicalPage != -1) {
                // Successfully allocated a new physical page, update TLB
                UpdateTLB(virtualPage, physicalPage);
            } else {
                // Allocation failed, handle error
                System.err.println("Failed to allocate physical page for virtual page " + virtualPage);
            }
        } else {
            UpdateTLB(virtualPage, physicalPage);
        }
        
        return physicalPage;
    }


    
    
    
}

