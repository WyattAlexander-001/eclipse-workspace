public class VeggieLoversPizza extends Pizza {

	//Veggies Lovers is ALWAYS going to be a large with onions and peppers
    public VeggieLoversPizza() {
        setSize(Size.LARGE);
        addTopping(Topping.ONIONS);
        addTopping(Topping.PEPPERS);
    }

    //Default price is $17
    @Override
    public double getPrice() {
        return 17;
    }

    @Override
    public void display() {
        System.out.println("Veggie Lovers Pizza");
        //Parent display method
        super.display();
    }
}