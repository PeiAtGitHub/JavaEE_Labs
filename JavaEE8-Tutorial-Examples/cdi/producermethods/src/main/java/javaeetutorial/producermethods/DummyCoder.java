package javaeetutorial.producermethods;

/**
 * This dummy coder does nothing but display the arguments.
 */
public class DummyCoder implements Coder {

    @Override
    public String codeString(String s, int tval) {
        return ("Input string is " + s + ", Transform value is " + tval);
    }
}
