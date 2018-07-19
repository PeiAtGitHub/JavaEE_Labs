package javaeetutorial.decorators;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Managed bean that calls a Coder implementation to perform a transformation
 * on an input string
 */
@Named
@RequestScoped
public class Encoder3Bean {

    @Getter @Setter
    private String inputString;
    @Getter @Setter
    private String codedString;

    @Getter @Setter
    @Min(0) @Max(26) @NotNull
    private int transVal;
    
    @Inject
    Coder coder;

    @Logged
    public void encodeString() {
        setCodedString(coder.codeString(inputString, transVal));
    }

    public void reset() {
        setInputString("");
        setTransVal(0);
    }

}
