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
    
    String response  = null;
    
    public String getResponse() {
        
        response = "User input number " + userInputNumber;
        
        if ((userInputNumber == null) || (userInputNumber.compareTo(serverNumberBean.getRandomInt()) != 0)) {
            response += " is NOT EQUAL to server random number.";
        } else {
            response += " is EQUAL to the server random number.";
        }
        
        return response;
    }

}
