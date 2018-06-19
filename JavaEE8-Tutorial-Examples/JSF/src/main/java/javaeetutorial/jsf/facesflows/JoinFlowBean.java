package javaeetutorial.jsf.facesflows;

import java.io.Serializable;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import javax.faces.flow.FlowScoped;
import javax.faces.model.SelectItem;

/**
 * Backing bean for JoinFlow.
 */
@Named
@FlowScoped("joinFlow")
@Getter
@Setter
public class JoinFlowBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private boolean fanClub;
    private String[] newsletters;
    
    private static final SelectItem[] newsletterItems = {
        new SelectItem("Java Quarterly"),
        new SelectItem("Innovator's Almanac"),
        new SelectItem("Spring projects")
    };
    
    public JoinFlowBean() {
        this.newsletters = new String[0];
    }

    public SelectItem[] getNewsletterItems() {
        return newsletterItems;
    }
    
    public String getReturnValue() {
        return "/faces-flows-example/join-exitpage";
    }


}
