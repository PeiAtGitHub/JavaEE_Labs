package javaeetutorial.cdi.alternatives;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import javaeetutorial.cdi.producermethods.Coder;
import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
public class Encoder2Bean {

    @Getter @Setter
    private String inputString;
    @Getter @Setter
    private String codedString;

    @Getter @Setter
    @Min(0) @Max(26) @NotNull
    private int transVal;

    @Inject
    Coder coder;

    public void encodeString() {
        setCodedString(coder.codeString(inputString, transVal));
    }

    public void reset() {
        setInputString("");
        setTransVal(0);
    }

}
