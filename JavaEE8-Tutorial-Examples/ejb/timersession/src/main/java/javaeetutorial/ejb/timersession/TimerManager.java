package javaeetutorial.ejb.timersession;

import java.io.Serializable;

import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class TimerManager implements Serializable {

    @EJB
    private TimerSessionBean timerSessionBean;

    @Setter
    private String lastProgrammaticTimeout;
    @Setter
    private String lastAutomaticTimeout;

    public TimerManager() {
        this.lastProgrammaticTimeout = "never";
        this.lastAutomaticTimeout = "never";
    }

    public void setTimer() {
        timerSessionBean.setTimer(10000L);
    }
    
    public String getLastProgrammaticTimeout() {
        lastProgrammaticTimeout = timerSessionBean.getLastProgrammaticTimeout();
        return lastProgrammaticTimeout;
    }

    public String getLastAutomaticTimeout() {
        lastAutomaticTimeout = timerSessionBean.getLastAutomaticTimeout();
        return lastAutomaticTimeout;
    }

}
