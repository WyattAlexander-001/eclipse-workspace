// TODO: Define a class: StringInstrument that is derived from the Instrument class
public class StringInstrument extends Instrument {
   // TODO: Declare private fields: numStrings, numFrets
	private int numStrings;
	private int numFrets;

   // TODO: Define mutator methods - 
   //      setNumOfStrings(), setNumOfFrets()

   // TODO: Define accessor methods -
   //      getNumOfStrings(), getNumOfFrets()


	   public void setNumOfStrings(int numStrings) 
	   {
	       this.numStrings = numStrings;
	   }	
	   public int getNumOfStrings() 
	   {
	       return numStrings;
	   }

	   public void setNumOfFrets(int numFrets) 
	   {
	       this.numFrets = numFrets;
	   }


	   public int getNumOfFrets() 
	   {
	       return numFrets;
	   }
}

