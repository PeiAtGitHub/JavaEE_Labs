package javaeetutorial.producermethods;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Managed bean that calls a Coder implementation to perform a transformation on an input string
 */
@Named
@RequestScoped
@Getter @Setter
public class EncodeBean {

    private String inputString;
    private String codedString;
    
    @Min(0) @Max(26)    
    @NotNull
    private int transVal;
    
    private final static int TEST = 1;
    private final static int SHIFT = 2;
    
    private int coderType = SHIFT; // default

    /**
     * Producer method that chooses between two beans
     */
    @Produces @Chosen @RequestScoped
    public Coder getCoder() {
        switch (coderType) {
            case TEST:
                return new DummyCoder();
            case SHIFT:
                return new Shifter();
            default:
                return null;
        }
    }
    
    @Inject @Chosen @RequestScoped
    Coder coder;

    public void encodeString() {
        setCodedString(coder.codeString(inputString, transVal));
    }

    public void reset() {
        setInputString("");
        setTransVal(0);
    }

}