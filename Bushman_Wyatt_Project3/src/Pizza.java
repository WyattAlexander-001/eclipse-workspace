import java.util.ArrayList;

public class Pizza {

    private Size size;
    private ArrayList<Topping> toppings = new ArrayList<>();

    public void setSize(Size size) {
        this.size = size;
    }

    public double getPrice() {
        double price = 0;

        switch (this.size) {
            case SMALL:
                price += 10;
                break;
            case MEDIUM:
                price += 15;
                break;
            case LARGE:
                price += 20;
                break;
        }

        for (Topping i : toppings) {
            price += 1;
        }
        return price;
    }

    public void display() {
        StringBuilder toppers = new StringBuilder();

        // Loops the toppings array list.        
        for (Topping i : toppings) {
            toppers.append(i.toString()).append("\n");
        }

        System.out.println("Size: " + this.size.toString());
        System.out.println("Toppings: \n" + toppers);
        System.out.println(String.format("Item Price: $%.2f" , getPrice()));
    }


    public void addTopping(Topping topping) {
        toppings.add(topping);
    }
}