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
            cart.addBook("Infinite Jest");
            cart.addBook("Bel Canto");
            cart.addBook("Kafka on the Shore");

            // list cart content
            System.out.println("Books in the cart: " + cart.getContents());
            
            // remove item from cart
            cart.removeBook("Gravity's Rainbow");
            cart.remove();

            System.exit(0);
        } catch (CartException ex) {
            System.err.println("Caught a BookException: " + ex.getMessage());
            System.exit(0);
        }
    }
}
