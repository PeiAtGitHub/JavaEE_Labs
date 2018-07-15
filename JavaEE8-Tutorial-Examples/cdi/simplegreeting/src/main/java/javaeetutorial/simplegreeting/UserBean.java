package javaeetutorial.simplegreeting;

import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@SessionScoped
@NoArgsConstructor
@Getter
@Setter
public class UserBean implements Serializable {

    protected String firstName = "Duke";
    protected String lastName = "Java";
    protected Date dob;
    protected String sex = "Unknown";
    protected String email;
    protected String serviceLevel = "medium";

    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String emailStr = (String) value;
        if (!emailStr.contains("@")) {
            throw new ValidatorException(new FacesMessage("Invalid email address"));
        }
    }

    public String addConfirmedUser() {
        // This method would call a DB or other service and add the confirmed user info.
        // For now, we just place an informative message in request scope
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successfully added new user"));
        return "done";
    }
}
