package javaeetutorial.cdi.simplegreeting;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class Printer {

    @Inject
    @Informal
    Greeting greeting;
    
    @Setter @Getter
    private String name;

    @Getter
    private String salutation;

    public void createSalutation() {
        this.salutation = greeting.greet(name);
    }

}
