package javaeetutorial.cart.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javaeetutorial.cart.util.CartException;
import javaeetutorial.cart.util.IdVerifier;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class CartBean implements Cart, Serializable {

    String customerId;
    String customerName;
    List<String> contents;

    @Override
    public void initialize(String person) throws CartException {
        if (person == null) {
            throw new CartException("Person is Null.");
        } else {
            customerName = person;
        }
        customerId = "0";
        contents = new ArrayList<>();
    }

    @Override
    public void initialize(String person, String id) throws CartException {
        if (person == null) {
            throw new CartException("Person is Null.");
        } else {
            customerName = person;
        }

        IdVerifier idChecker = new IdVerifier();
        if (idChecker.validate(id)) {
            customerId = id;
        } else {
            throw new CartException("Invalid id: " + id);
        }

        contents = new ArrayList<>();
    }

    @Override
    public void addItem(String name) {
        contents.add(name);
    }

    @Override
    public void removeItem(String name) throws CartException {
        if (contents.remove(name) == false) {
            throw new CartException("\"" + name + "\" not in cart.");
        }
    }

    @Override
    public List<String> getContents() {
        return contents;
    }

    @Remove()
    @Override
    public void remove() {
        contents = null;
    }
}
