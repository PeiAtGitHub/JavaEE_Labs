package javaeetutorial.decorators;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class CoderDecorator implements Coder {
    
    @Inject @Delegate @Any
    Coder coder;
    
    @Override
    public String codeString(String s, int tval) {
        return "\"" + s + "\" becomes " + "\"" + coder.codeString(s, tval) 
                + "\", " + s.length() + " characters in length";
    }
}
