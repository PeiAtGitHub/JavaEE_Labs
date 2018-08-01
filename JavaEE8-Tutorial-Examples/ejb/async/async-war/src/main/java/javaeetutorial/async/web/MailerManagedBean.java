package javaeetutorial.async.web;

import java.io.Serializable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javaeetutorial.async.ejb.MailerBean;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MailerManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(MailerManagedBean.class.getName());
    
    @EJB
    protected MailerBean mailerBean;
    
    @Setter @Getter
    protected String email;
    @Setter
    protected String status;
    
    private Future<String> mailStatus;

    
    public String getStatus() {
        if (mailStatus.isDone()) {
            try {
                setStatus(mailStatus.get());
            } catch (ExecutionException | CancellationException | InterruptedException ex) {
                setStatus(ex.getCause().toString());
            }
        }
        return status;
    }

    public String send() {
        try {
            mailStatus = mailerBean.sendMessage(getEmail());
            setStatus("Processing... (refresh to check again)");
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        return "response?faces-redirect=true";
    }

}
