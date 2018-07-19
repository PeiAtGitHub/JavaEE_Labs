package javaeetutorial.cdi.todolist;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ConversationScoped
@Stateful
public class RequestBean {

    @Inject @UserDatabase
    EntityManager em;

    public ToDo createToDo(String inputString) {
        try {
            ToDo toDo = new ToDo(inputString, Calendar.getInstance().getTime());
            em.persist(toDo);
            return toDo;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<ToDo> getToDos() {
        try {
            return (List<ToDo>) em.createQuery("SELECT t FROM ToDo t ORDER BY t.timeCreated").getResultList();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
