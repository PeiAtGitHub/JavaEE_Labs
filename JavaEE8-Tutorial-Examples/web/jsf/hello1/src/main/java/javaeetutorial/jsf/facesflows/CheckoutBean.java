package javaeetutorial.jsf.facesflows;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

/**
 * Backing bean for index page.
 */
@Named
@SessionScoped
@Getter
@Setter
public class CheckoutBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    int numItems = 3;
    
    double cost = 101.25;
    
}
