package javaeetutorial.guessnumber;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
public class UserNumberBean implements Serializable {

    private static final long serialVersionUID = 5443351151396868724L;
    
    Integer serverRandomNum = null;
    
    String response  = null;
    
    @Getter 
    @Setter
    private Integer userInputNumber = null;
    
    @Getter 
    @Setter
    private int min = 0;
    
    @Getter 
    @Setter
    private int max = 10;
    
    
    public UserNumberBean() {
        
        serverRandomNum = Integer.valueOf(new Random().nextInt(max + 1));

        System.out.println("Server random number is: " + serverRandomNum);
        
    }

    
    public String getResponse() {
        
        response = "User input number " + userInputNumber;
        
        if ((userInputNumber == null) || (userInputNumber.compareTo(serverRandomNum) != 0)) {
            response += " is NOT EQUAL to server random number.";
        } else {
            response += " is EQUAL to the server random number.";
        }
        
        return response;
    }

}
