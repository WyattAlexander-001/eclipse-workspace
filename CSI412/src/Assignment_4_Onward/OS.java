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
        getKernel().start(); //This looks like it's not even getting hit
        System.out.println("JUST RAN KERNEL.START INSIDE STARTUP METHOD");
    }

    public static void createProcess(UserlandProcess up) {
    	resetParameters();
    	parameters.add(up);
    	currentCall = CallType.CREATE_PROCESS;
        switchToKernel(); // Switch control to the kernel
    }

    public static void switchProcess() {
    	currentCall = CallType.SWITCH_PROCESS;    	
        switchToKernel();
    }


    private static void switchToKernel() {
    	System.out.println("INSIDE SWITCH TO KERNEL METHOD");
        getKernel().start(); // Start or wake up the kernel thread
        System.out.println("JUST DID KERNEL.start()");
        if (getKernel().getScheduler().currentlyRunning != null) {
            getKernel().getScheduler().currentlyRunning.stop(); // Stop the current user process
        } else {
            // If nothing is running, we enter an idle state
            while (getKernel().getScheduler().currentlyRunning == null) {
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
	    PCB currentPCB = getKernel().getScheduler().getCurrentlyRunningPCB();
	    return currentPCB != null ? currentPCB.getPid() : -1;
	}

	public static int getPidByName(String name) {
	    return getKernel().getScheduler().getProcesses().stream()
	                 .filter(pcb -> name.equals(pcb.getUserlandProcess()))
	                 .findFirst()
	                 .map(PCB::getPid)
	                 .orElse(-1);
	}
	
//	public static int requestPageMapping(int virtualPage) {
//	    int pid = getPid();
//	    PCB pcb = kernel.getPCB(pid); 
//	    if (pcb != null) {
//	        return pcb.getPageMapping(virtualPage);
//	    }
//	    return -1; 
//	}
	
	public static int requestPageMapping(int virtualPage) {
	    int pid = getPid();
	    PCB pcb = getKernel().getPCB(pid); 
	    if (pcb != null) {
	        VirtualToPhysicalMapping mapping = pcb.getPageMapping(virtualPage);
	        if (mapping != null) {
	            // Return the physical page number if it's valid
	            if (mapping.physicalPageNumber != -1) {
	                return mapping.physicalPageNumber;
	            }
	        }
	    }
	    return -1; // Return -1 if no valid mapping is found
	}

	public static int allocatePhysicalPage(int virtualPage) {
	    int pid = getPid();
	    return getKernel().allocatePhysicalPageForProcess(pid, virtualPage);
	}

	public static Kernel getKernel() {
		return kernel;
	}
	

	
	

}

