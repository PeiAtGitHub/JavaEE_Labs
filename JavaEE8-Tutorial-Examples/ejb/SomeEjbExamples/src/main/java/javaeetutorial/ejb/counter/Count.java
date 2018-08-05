package javaeetutorial.ejb.counter;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@Named
@ConversationScoped
public class Count implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @EJB
    private CounterBean counterBean;

    public int getHitCount() {
        return counterBean.getHits();
    }

}
