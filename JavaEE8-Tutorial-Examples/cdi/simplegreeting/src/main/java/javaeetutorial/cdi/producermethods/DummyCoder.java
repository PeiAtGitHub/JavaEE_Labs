package javaeetutorial.cdi.producermethods;

import javax.enterprise.inject.Alternative;

/**
 * This dummy coder does nothing but display the arguments.
 */
@Alternative
public class DummyCoder implements Coder {

    @Override
    public String codeString(String s, int tval) {
        return ("Input string is " + s + ", Transform value is " + tval);
    }
}
