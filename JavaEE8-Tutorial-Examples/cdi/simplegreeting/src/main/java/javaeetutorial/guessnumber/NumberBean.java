package javaeetutorial.guessnumber;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@SessionScoped
@NoArgsConstructor
public class NumberBean implements Serializable {

    private static final long serialVersionUID = -7698506329160109476L;
    
    @Getter @Setter
    private Integer userNumber;
    @Getter
    private int remainingGuesses;
    
    @Inject
    @MaxNumber
    private int maxNumber;

    @Getter @Setter
    private int minimum;
    @Getter @Setter
    private int maximum;

    @Getter
    private int number;
    @Inject
    @Random
    Instance<Integer> randomInt;

    @PostConstruct
    public void reset() {
        this.minimum = 0;
        this.maximum = maxNumber;
        this.userNumber = 0;
        this.remainingGuesses = 10;
        this.number = randomInt.get();
    }

    public String check() throws InterruptedException {
        if (userNumber > number) {
            maximum = userNumber - 1;
        }
        if (userNumber < number) {
            minimum = userNumber + 1;
        }
        if (userNumber == number) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Correct!"));
        }
        remainingGuesses--;
        return null;
    }


    public void validateNumberRange(FacesContext context, UIComponent toValidate, Object value) {
        int input = (Integer) value;
        
        if (input < minimum || input > maximum) {
            ((UIInput) toValidate).setValid(false);
            context.addMessage(toValidate.getClientId(context), new FacesMessage("Invalid guess"));
        }
    }
}
