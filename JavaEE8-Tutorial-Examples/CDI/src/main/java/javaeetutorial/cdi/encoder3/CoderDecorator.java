package javaeetutorial.cdi.encoder3;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import javaeetutorial.cdi.producermethods.Coder;
import javaeetutorial.cdi.producermethods.DummyCoder;
import javaeetutorial.cdi.producermethods.Shifter;

@Decorator
public abstract class CoderDecorator implements Coder {

    @Inject
    @Delegate
    @Any
    Coder coder;

    @Override
    public String codeString(String s, int tval) {
        return "I am Coder Decorator, my injected Delegate Coder is " + getCoderClassName()
                + ", which returns: " + coder.codeString(s, tval);
    }

    private String getCoderClassName() {
        if (coder instanceof DummyCoder) {
            return "DummyCoder";
        } else if (coder instanceof Shifter) {
            return "Shifter";
        } else if (coder instanceof CoderDecorator) {
            return "CoderDecorator";
        } else {
            return "UNKNOWN!";
        }
    }
}
