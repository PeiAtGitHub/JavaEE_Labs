package javaeetutorial.jsf.facesflows;

import java.io.Serializable;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

/**
 * Backing bean for checkoutFlow.
 */
@Named
@FlowScoped("checkoutFlow")
@Getter
@Setter
public class CheckoutFlowBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String ccName;
    private String ccNum;
    private String ccExpDate;
   

    public String getReturnValue() {
        return "/faces-flows-example/index";
    }
    
}
