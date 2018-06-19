package javaeetutorial.jsf.guessnumber;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
@Getter
@Setter
public class ServerNumberBean implements Serializable {

    private static final long serialVersionUID = 6575056551121951958L;
    
    private Integer randomInt = null;
    
    private int min = 0;
    private int max = 10;

    public ServerNumberBean() {
        randomInt = (new Random().nextInt(max + 1));
        System.out.println("Server generated random number: " + randomInt);
    }

}
