// Java Program to illustrate Busy Spinning as Wait
// Strategy

// Importing input output classes
import java.io.*;
// Importing List class from java.util package
import java.util.List;
import java.util.ArrayList;

// Class 1
// Helper class
class Pizza {

    private String base;
    public String getBase() { return base; }

    public void setBase(String base) { this.base = base; }

    public List<String> getToppings() { return toppings; }

    public void setToppings(List<String> toppings)
    {
        this.toppings = toppings;
    }

    private List<String> toppings;

    public Pizza(String base, List<String> toppings)
    {
        super();
        this.base = base;
        this.toppings = toppings;
    }

    public void make()
    {
        System.out.println("Making pizza");
    }
}

// Class 2
// Helper class
class Customer implements Runnable {

    PizzaMaker pizzaMaker;

    public Customer(PizzaMaker pizzaMaker)
    {
        this.pizzaMaker = pizzaMaker;
    }

    public void run()
    {
        while (this.pizzaMaker.isInProgress) {
            System.out.println(Thread.currentThread().getName() + ":-Pizza order complete??");
            System.out.println("--Busy Spinning---");
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Received the ordered pizza:-" + Thread.currentThread().getName());
        System.out.println("Base of the pizza is : " + pizzaMaker.pizza.getBase());
        System.out.println( "Toppings are : " + pizzaMaker.pizza.getToppings());
    }
}

// Class 3
// Helper class
class PizzaMaker implements Runnable {

    Pizza pizza;
    boolean isInProgress;
    public PizzaMaker(Pizza pizza)
    {
        this.pizza = pizza;
        this.isInProgress = true;
    }

    public void run()
    {
        System.out.println("Pizza order in progress");
        pizza.make();
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(
                " Making of pizza with base :"
                        + this.pizza.getBase() + " and toppings as "
                        + this.pizza.getToppings() + " is complete :- "
                        + Thread.currentThread().getName());
        this.isInProgress = false;
    }
}

// Class 4
// Main class
class GFG {

    // Main driver method
    public static void main(String[] args)
    {

        String base = "thick crust base";

        List<String> toppings = new ArrayList<>();

        toppings.add("tomato");
        toppings.add("corn");
        toppings.add("cheese");
        toppings.add("olive");

        Pizza pizza = new Pizza(base, toppings);

        PizzaMaker pizzaMaker = new PizzaMaker(pizza);
        Customer customer = new Customer(pizzaMaker);
        Thread pizzaMakerThread
                = new Thread(pizzaMaker, "pizzaMakerThread");
        Thread customerThread
                = new Thread(customer, "customerThread");

        pizzaMakerThread.start();
        customerThread.start();
    }
}
