import java.util.ArrayList;

public class OS {
    private static Kernel kernel; 
    private static CallType currentCall = CallType.NONE;
    private static ArrayList<Object> parameters = new ArrayList<>();
    private static Object returnValue;
    
    

    public static void startup(UserlandProcess init) {
        kernel = new Kernel();
        createProcess(init); 
        createProcess(new IdleProcess()); 
        switchToKernel(); 
    }

    public static void createProcess(UserlandProcess up) {
        setKernelCall(CallType.CREATE_PROCESS, up); // Set the current call type
        switchToKernel(); // Switch control to the kernel
    }

    public static void switchProcess() {
        setKernelCall(CallType.SWITCH_PROCESS);
        switchToKernel();
    }


    private static void setKernelCall(CallType callType, Object... params) {
        currentCall = callType;
        getParameters().clear();
        for (Object param : params) {
            getParameters().add(param);
        }
    }

    private static void switchToKernel() {
        kernel.start(); // Start or wake up the kernel thread

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
	    setKernelCall(CallType.SLEEP, milliseconds); 
	    switchToKernel(); 
	}


}

