package javaeetutorial.cart.ejb;

import java.util.List;
import javaeetutorial.cart.util.BookException;
import javax.ejb.Remote;

@Remote
public interface Cart {
    
    public void initialize(String person) throws BookException;

    public void initialize(String person, String id) throws BookException;

    public void addBook(String title);

    public void removeBook(String title) throws BookException;

    public List<String> getContents();

    public void remove();
}
