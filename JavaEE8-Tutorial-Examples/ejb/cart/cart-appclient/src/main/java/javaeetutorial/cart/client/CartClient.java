package javaeetutorial.cart.client;

import javax.ejb.EJB;

import javaeetutorial.cart.ejb.Cart;
import javaeetutorial.cart.util.CartException;

public class CartClient {

    @EJB
    private static Cart cart;

    public static void main(String[] args) {
        doTest();
    }

    public static void doTest() {
        try {
            // initialize
            cart.initialize("SomeBody", "123");
            cart.addItem("Beef");
            cart.addItem("Potato");
            cart.addItem("Tomato");

            // list cart items
            System.out.println("Items in the cart: " + cart.getContents());
            
            // remove item from cart
            cart.removeItem("Cucumber");
            cart.remove();

            System.exit(0);
        } catch (CartException ex) {
            System.err.println("Caught a BookException: " + ex.getMessage());
            System.exit(0);
        }
    }
}
