package javaeetutorial.ajaxguessnumber;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class UserNumberBean implements Serializable {

    private static final long serialVersionUID = 5377607515424187221L;
    
    @Inject
    ServerNumberBean serverNumberBean;
    
    @Setter
    @Getter
    private Integer userInputNumber = null;
    
    String response = null;

    public String getResponse() {
        response = "User input number " + userInputNumber;
        
        if ((userInputNumber != null)
                && (userInputNumber.compareTo(serverNumberBean.getRandomInt()) == 0)) {
            response +=  " is EQUAL to server generated random number.";
        }else {
            response +=  " is NOT EQUAL to server generated random number.";
        }
        
        return response;
    }
}
