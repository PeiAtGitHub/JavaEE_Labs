package javaeetutorial.standalone.ejb;

import javax.ejb.Stateless;

@Stateless
public class StandaloneBean {

    public String returnMessage() {
        return "Greetings!";
    }
    
}
