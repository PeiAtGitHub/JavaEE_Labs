package javaeetutorial.ejb.timersession;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javaeetutorial.ejb.timersession.TimerSessionBean;

@Named
@SessionScoped
public class TimerManager implements Serializable {
    
    private static final long TIMEOUT_INTERVAL = 3000L;
    
    @EJB
    private TimerSessionBean timerSessionBean;

    public void setTimer() {
        timerSessionBean.setTimer(TIMEOUT_INTERVAL);
    }
    
    public String getLastProgrammaticTimeout() {
        return timerSessionBean.getLastProgrammaticTimeout();
    }

    public String getLastAutomaticTimeout() {
        return timerSessionBean.getLastAutomaticTimeout();
    }

}
