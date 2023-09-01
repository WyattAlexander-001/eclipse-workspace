package yt;

public class Main {
	
	public static void main(String args[]) {
		
		//Oh GOD! WE MADE 4 CLASSES!
		MyIntegerClass myInt = new MyIntegerClass(1);
		MyDoubleClass myDouble = new MyDoubleClass(3.14);
		MyCharacterClass myChar = new MyCharacterClass('@');
		MyStringClass myString = new MyStringClass("Hello!!!");
		
		System.out.println(myInt.getValue());
		System.out.println(myDouble.getValue());
		System.out.println(myChar.getValue());
		System.out.println(myString.getValue());
		
		System.out.println("\n==============================\n");
		//Exactly like ArrayList!
		MyGenericClass<Integer> myOtherInt = new MyGenericClass<>(7); 
		MyGenericClass<Double> myOtherDouble = new MyGenericClass<>(3.14);
		MyGenericClass<Character> myOtherChar = new MyGenericClass<>('@');
		MyGenericClass<String> myOtherString = new MyGenericClass<>("HELLO!!!!!!");
		
		System.out.println(myOtherInt.getValue());
		System.out.println(myOtherDouble.getValue());
		System.out.println(myOtherChar.getValue());
		System.out.println(myOtherString.getValue());
		

		//Multiple Parameter Generic class <Thing1, Thing2>:
		//MyGenericClass<Integer,Double> myOtherInt = new MyGenericClass<>(7,4.567); 


		
	}

}
