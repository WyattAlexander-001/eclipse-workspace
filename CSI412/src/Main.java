public class Main {
    public static void main(String[] args) {
//		  Assignment 1 Stuff:    	
//        OS.startup(new HelloWorld()); 
//        OS.createProcess(new GoodbyeWorld()); 
    	
        // Start up the OS and Kernel
        OS.startup(new IdleProcess());

        // Create and start some userland processes
        OS.createProcess(new HelloWorldProcess());
        OS.createProcess(new GoodbyeWorldProcess());

    }
}


