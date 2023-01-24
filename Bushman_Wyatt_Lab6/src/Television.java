/**
 * The purpose of this class is to model a television
 * @author Wyatt Bushman
 * @date 10/17/2022
 */
public class Television {

	//Fields:
	//Constants:
	private final String MANUFACTURER; //Brand of Tv eg: Sony
	private final int SCREEN_SIZE; //Size of TV eg: 19
	
	//Non-constant:
	private boolean powerOn; // State of TV True= on/ False = off
	private int channel; // Whole number for TV station
	private int volume; // whole number for Tv's volume
	
	
	//Constructor
	/**
	 * Sets a default TV on channel 2 with a volume of 20, and is "off"
	 * brand and size are the unique parameters passed when instantiating a new TV
	 * @param brand
	 * @param size
	 */
	public Television(String brand,int size)
	{
		MANUFACTURER = brand;
		SCREEN_SIZE = size;
		
		volume = 20;
		channel = 2;
		powerOn = false;
	}
	
	//Methods:
	
	//Setters
	/**
	 * Sets channel. If input is negative integer it returns the absolute value.
	 * @param num
	 */
	public void setChannel(int num) 
	{
		//Prevents negative values and returns absolute value
		if(num < 0) 
		{
			channel = num * - 1;
		}else 
		{
			channel = num;
		}
	}
	   
	/**
	 * Uses unary operator to switch powerOn state from false/true
	 */	
	public void power() 
	{
		powerOn = !powerOn;
		/**
		 * Alternative way to write this:
		if (powerOn = false) 
		{
			powerOn = true;
		}else 
		{
			powerOn = false;
		}
		**/
	}
	
	/**
	 * Increments volume by 1 integer
	 */
	public void increaseVolume() 
	{
		volume++;
	}
	/**
	 * Decrements volume by 1 integer, used if statement to prevent negative volume
	 */
	public void decreaseVolume() 
	{
		if(volume ==0) 
		{
			volume = 0;
		}else 
		{
			volume--;
		}

	}	
	//Getters
	/**
	 * 
	 * @return Channel
	 */
	public int getChannel() 
	{
		return channel;
	}
	/**
	 * 
	 * @return Volume
	 */
	public int getVolume() 
	{
		return volume;
	}
	/**
	 * 
	 * @return Manufacturer
	 */
	public String getManufacturer() 
	{
		return MANUFACTURER;
	}
	/**
	 * 
	 * @return Screen size
	 */
	public int getScreenSize()
	{
		return SCREEN_SIZE;
	}
}
