package yt;
//Multi-parameter generics: We can have multiple Things as like Thing2, Thing3....INF
//Bounded Types: Say we want to have ONLY Numbers, Thing extends Number
public class MyGenericClass <Thing>{ 
	Thing x;
	
	public MyGenericClass(Thing x) {
		this.x = x;
	}
	
	public Thing getValue() {
		return x;
	}

}
