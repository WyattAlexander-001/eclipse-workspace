package Assignment_4_Onward;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PCB {
    private static int nextPid = 1; // Static counter to assign unique PIDs
    private int pid; // Process ID
    private UserlandProcess userlandProcess; // Associated userland process
    private Instant wakeUpTime;
    private Priority priority; // Enum for process priority
    private int timeoutCounter = 0; // Counter for timeouts leading to potential demotion
    private String processName;
    
    private final Queue<KernelMessage> messageQueue = new LinkedList<>();
    
    private int[] pageTable = new int[1024]; // Page table for virtual to physical page mappings




    
    private int[] deviceIds = new int[10];
    // Initialize the array to -1 to indicate no device is open
    {
        Arrays.fill(deviceIds, -1);
    }
    
    public PCB(UserlandProcess up) {
        this.userlandProcess = up;
        this.pid = nextPid++; 
        Arrays.fill(deviceIds, -1); // Initialize device IDs to -1
        Arrays.fill(pageTable, -1); // Initialize page table to -1 indicating no mapping
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
    
    
    public int[] getDeviceIds() {
        return deviceIds;
    }
    public void setDeviceId(int index, int deviceId) {
        if (index >= 0 && index < deviceIds.length) {
            deviceIds[index] = deviceId;
        }
    }
    
    
    
    // Method to add a received message to the queue
    public void receiveMessage(KernelMessage message) {
        messageQueue.offer(message);
    }

    // Method to check if there are any messages in the queue
    public boolean hasMessages() {
        return !messageQueue.isEmpty();
    }

    // Method to get and remove the next message from the queue
    public KernelMessage getNextMessage() {
        return messageQueue.poll();
    }
    
    public void setPageMapping(int virtualPageIndex, int physicalPageIndex) {
        if (virtualPageIndex >= 0 && virtualPageIndex < pageTable.length) {
            pageTable[virtualPageIndex] = physicalPageIndex;
        }
    }
    
    public int getPageMapping(int virtualPageIndex) {
        if (virtualPageIndex >= 0 && virtualPageIndex < pageTable.length) {
            return pageTable[virtualPageIndex];
        }
        return -1; // Return -1 if the index is out of bounds or no mapping exists
    }


    
    


}
