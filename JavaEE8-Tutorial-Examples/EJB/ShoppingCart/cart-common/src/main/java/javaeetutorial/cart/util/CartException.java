package javaeetutorial.cart.util;

public class CartException extends Exception {
    
    private static final long serialVersionUID = 6274585742564840905L;
    
    public CartException() {
    }

    public CartException(String msg) {
        super(msg);
    }
}
