import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private LinkedList<UserlandProcess> processes;
    private Timer timer;
    public UserlandProcess currentlyRunning;

    // Constructor
    public Scheduler() {
        processes = new LinkedList<>();
        timer = new Timer();
        scheduleInterrupt();
    }
    
    private void scheduleInterrupt() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (currentlyRunning != null) {
//                    System.out.println("Timer requesting stop for: " + currentlyRunning);
                    currentlyRunning.requestStop();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 250);
    }

    
    public void switchProcess() {
        try {
            Thread.sleep(5); // Minimal delay for context switching simulation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (currentlyRunning != null) {
            if (!currentlyRunning.isDone()) {
                processes.addLast(currentlyRunning);
            }
        }

        // Move to the next process, if available.
        if (!processes.isEmpty()) {
            currentlyRunning = processes.poll(); // Retrieve and remove the head of the queue.
            currentlyRunning.start();
        } else {
            currentlyRunning = null;
        }
    }

    public void createProcess(UserlandProcess up) {
        // Add new process to the list.
        processes.add(up);
        // If there's no process currently running, start scheduling immediately.
        if (currentlyRunning == null && !processes.isEmpty()) {
            switchProcess();
        }
    }


}




