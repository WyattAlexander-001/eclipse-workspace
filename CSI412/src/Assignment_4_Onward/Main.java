package Assignment_4_Onward;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
//		  Assignment 1 Stuff:    	
        OS.startup(new HelloWorld()); 
        
        try {
        	Thread.sleep(200);
        }catch(InterruptedException e) {
        	Thread.currentThread().interrupt();
        }
        
        OS.createProcess(new GoodbyeWorld()); 
        
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		OS.switchProcess();
        	}
        },0,100);
    	
    	
        // Start up the OS and Kernel
//        OS.startup(new IdleProcess());

        // Create and start some userland processes
//        OS.createProcess(new HelloWorldProcess());
//        OS.createProcess(new GoodbyeWorldProcess());
//    	Kernel kernel = new Kernel();
//    	kernel.start();


    }
}


