package javaeetutorial.websocket.chatroom;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.inject.Named;

@Named
public class BotBean {
    
    GregorianCalendar botBirthDay = new GregorianCalendar(1995, Calendar.MAY, 23);
    
    public String respond(String msg) {

        String response = "";
        
        msg = msg.toLowerCase();        
        
        if (msg.contains("how are you")) {
            response = "I'm doing great, thank you!";
        } else if (msg.contains("how old are you")) {
            response = String.format("I'm %d years old.", 
                    GregorianCalendar.getInstance().get(Calendar.YEAR) - botBirthDay.get(Calendar.YEAR));
        } else if (msg.contains("when is your birthday")) {
            response = "My birthday is: " + botBirthDay.getTime().toString();
        } else if (msg.contains("your favorite color")) {
            response = "My favorite color is blue. What's yours?";
        } else {
            response = "Sorry, I did not understand what you said. "
                   + "You can ask me how I'm doing today; how old I am; or "
                   + "what my favorite color is.";
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) { 
            
        }
        return response;
    }
    
}
