package javaeetutorial.simplegreeting;

import javax.enterprise.context.Dependent;

@Informal
@Dependent
public class InformalGreeting extends Greeting {
    
    @Override
    public String greet(String name) {
        return "Hi, " + name + "!";
    }
}
