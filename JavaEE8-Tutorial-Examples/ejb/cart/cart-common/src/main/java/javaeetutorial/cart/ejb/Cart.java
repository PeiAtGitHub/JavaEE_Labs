package javaeetutorial.cart.ejb;

import java.util.List;
import javaeetutorial.cart.util.CartException;
import javax.ejb.Remote;

@Remote
public interface Cart {
    
    public void initialize(String person) throws CartException;

    public void initialize(String person, String id) throws CartException;

    public void addBook(String title);

    public void removeBook(String title) throws CartException;

    public List<String> getContents();

    public void remove();
}
