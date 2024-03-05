public class Main {
    public static void main(String[] args) {
        OS.startup(new HelloWorld()); 
        
//        System.out.println("DUMMMMMMMY LINE!!!!!!!!!");
        OS.createProcess(new GoodbyeWorld()); 
    }
}


