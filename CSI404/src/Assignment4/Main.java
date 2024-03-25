package Assignment4;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
        String[] initialData = {
                "00000000000000000000000000000001", 
            };
            MainMemory.load(initialData);

            // Create an instance of the Processor and run it
            Processor processor = new Processor();
            processor.run(); // This will call fetch() inside the run loop
        }
	
	

}


