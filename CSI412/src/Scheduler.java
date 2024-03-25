import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class Scheduler {
    private LinkedList<PCB> processes; // Now handling PCB objects
    private Timer timer;
    public PCB currentlyRunning; // Updated to handle PCB
    private Kernel kernel;
    private Clock clock = Clock.systemDefaultZone(); // System clock with millisecond accuracy
    private List<PCB> sleepingProcesses = new ArrayList<>(); // List to track sleeping processes

    public Scheduler() {
        processes = new LinkedList<>();
        timer = new Timer();
        scheduleInterrupt();
    }
    
    public Scheduler(Kernel kernel) {
        this.kernel = kernel;
        processes = new LinkedList<>();
        timer = new Timer();
        scheduleInterrupt();
    }

    private void scheduleInterrupt() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (currentlyRunning != null) {
                    currentlyRunning.getUserlandProcess().requestStop();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 250);
    }

    public void switchProcess() {
        checkAndWakeUpProcesses(); // Check and wake up any processes ready to be woken up
        if (currentlyRunning != null && !currentlyRunning.isDone()) {
        	System.out.println("Inside SWITCH PROCESS IN SCHEDULER: currentlyRunning != null && !currentlyRunning.isDone() ");
            processes.addLast(currentlyRunning); // Re-queue if not done
        }

        if (!processes.isEmpty()) {
        	System.out.println("Inside SWITCH PROCESS IN SCHEDULER: !processes.isEmpty()");
            currentlyRunning = processes.poll();
            currentlyRunning.run(); // Start the userland process of the PCB
        } else {
            currentlyRunning = null;
        }
    }

    public void createProcess(UserlandProcess up) {
        PCB pcb = new PCB(up); 
        processes.add(pcb);

        if (currentlyRunning == null) {
            switchProcess();
        }
    }

    public void sleep(int milliseconds) {
        if (currentlyRunning != null) {
            Instant wakeUpTime = clock.instant().plusMillis(milliseconds);
            currentlyRunning.setWakeUpTime(wakeUpTime); 
            sleepingProcesses.add(currentlyRunning);
            currentlyRunning = null; // Clear currentlyRunning to allow next process to run
            switchProcess(); // Immediately switch to next process
        }
    }

    private void checkAndWakeUpProcesses() {
        Instant now = clock.instant();
        List<PCB> toWakeUp = sleepingProcesses.stream()
            .filter(pcb -> pcb.getWakeUpTime().isBefore(now) || pcb.getWakeUpTime().equals(now))
            .collect(Collectors.toList());

        sleepingProcesses.removeAll(toWakeUp);
        processes.addAll(toWakeUp);
    }
    
    public PCB getCurrentlyRunningPCB() {
        return this.currentlyRunning;
    }
    
    public void closeProcessDevices(PCB pcb) {
        for (int deviceId : pcb.getDeviceIds()) {
            if (deviceId != -1) {
                kernel.Close(deviceId); 
            }
        }
    }
}
