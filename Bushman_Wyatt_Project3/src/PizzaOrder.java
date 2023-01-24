import java.util.ArrayList;

public class PizzaOrder extends Pizza{
    private ArrayList<Pizza> pizzas = new ArrayList<>();

    public double getPrice() {
        double price = 0;
        //Loops through each pizza and adds to total price
        for (Pizza i : pizzas) {
            price += i.getPrice();
        }
        return price;
    }

    //Display is similar to a toString() method. Inherits from Pizza class
    @Override
    public void display() {
        for (Pizza i : pizzas) {
            i.display();
            System.out.println("-------------------------------------------------");
        }

        double subtotal;
        double tax;
        double total;
        
        subtotal = getPrice();
        tax = subtotal * 0.08;
        total = subtotal + tax;

        System.out.println(String.format("SUBTOTAL: $%.2f", subtotal));
        System.out.println(String.format("TAX: $%.2f", tax));
        System.out.println(String.format("TOTAL: $%.2f", total));
        System.out.println("-------------------------------------------------");
    }
    // Add Pizza to the pizzas array list.
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

}