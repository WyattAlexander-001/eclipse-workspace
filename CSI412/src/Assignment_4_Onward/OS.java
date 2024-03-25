package Assignment_4_Onward;
import java.util.ArrayList;
import java.util.List;

public class OS {
    private static final Kernel kernel = new Kernel(); 
    private static CallType currentCall;
    private static ArrayList<Object> parameters = new ArrayList<>();
    private static Object returnValue;
    
    

    public static void startup(UserlandProcess init) {
    	System.out.println("INSIDE TO KERNEL STARTUP METHOD");
//        kernel = new Kernel();
        createProcess(init); 
//        createProcess(new IdleProcess()); 
//        switchToKernel(); 
        kernel.start(); //This looks like it's not even getting hit
        System.out.println("JUST RAN KERNEL.START INSIDE STARTUP METHOD");
    }

    public static void createProcess(UserlandProcess up) {
    	resetParameters();
    	parameters.add(up);
//    	parameters.add(priority)
//        setKernelCall(CallType.CREATE_PROCESS, up); // Set the current call type
    	currentCall = CallType.CREATE_PROCESS;
        switchToKernel(); // Switch control to the kernel
    }

    public static void switchProcess() {
    	currentCall = CallType.SWITCH_PROCESS;    	
        switchToKernel();
    }


    private static void switchToKernel() {
    	System.out.println("INSIDE SWITCH TO KERNEL METHOD");
        kernel.start(); // Start or wake up the kernel thread
        System.out.println("JUST DID KERNEL.start()");
        if (kernel.getScheduler().currentlyRunning != null) {
            kernel.getScheduler().currentlyRunning.stop(); // Stop the current user process
        } else {
            // If nothing is running, we enter an idle state
            while (kernel.getScheduler().currentlyRunning == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    
    public static CallType getCurrentCall() {
        return currentCall;
    }

	public static ArrayList<Object> getParameters() {
		return parameters;
	}

	public static void setParameters(ArrayList<Object> parameters) {
		OS.parameters = parameters;
	}

	public static Object getReturnValue() {
		return returnValue;
	}

	public static void setReturnValue(Object returnValue) {
		OS.returnValue = returnValue;
	}
	
	public static void Sleep(int milliseconds) {
		resetParameters();
		parameters.add(milliseconds);
		currentCall = CallType.SLEEP;
	    switchToKernel(); 
	}
	
	private static void resetParameters() {
		parameters.clear();
	}
	
	public static int getPid() {
	    PCB currentPCB = kernel.getScheduler().getCurrentlyRunningPCB();
	    return currentPCB != null ? currentPCB.getPid() : -1;
	}

	public static int getPidByName(String name) {
	    return kernel.getScheduler().getProcesses().stream()
	                 .filter(pcb -> name.equals(pcb.getUserlandProcess()))
	                 .findFirst()
	                 .map(PCB::getPid)
	                 .orElse(-1);
	}
	
	

	
	


}

