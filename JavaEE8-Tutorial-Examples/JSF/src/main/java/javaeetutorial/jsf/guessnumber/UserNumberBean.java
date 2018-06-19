package javaeetutorial.jsf.guessnumber;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
@Named
@SessionScoped
public class UserNumberBean implements Serializable {

    private static final long serialVersionUID = 5443351151396868724L;
    
    @Inject
    ServerNumberBean serverNumberBean;
    
    @Getter 
    @Setter
    private Integer userInputNumber = null;
    
    public String getResponse() {
        if (userInputNumber == null) {
            return "No input.";
        }else if (userInputNumber.compareTo(serverNumberBean.getRandomInt()) != 0) {
            return userInputNumber + " is NOT EQUAL to the server random number.";
        } else {
            return userInputNumber + " is EQUAL to the server random number.";
        }
    }

}
