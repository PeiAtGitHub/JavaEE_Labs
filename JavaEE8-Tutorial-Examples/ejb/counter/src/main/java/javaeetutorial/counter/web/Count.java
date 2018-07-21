package javaeetutorial.counter.web;

import java.io.Serializable;
import javaeetutorial.counter.ejb.CounterBean;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@Named
@ConversationScoped
public class Count implements Serializable {
    
    @EJB
    private CounterBean counterBean;

    public int getHitCount() {
        return counterBean.getHits();
    }

}
