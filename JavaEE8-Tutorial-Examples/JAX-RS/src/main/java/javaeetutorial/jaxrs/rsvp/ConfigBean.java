package javaeetutorial.jaxrs.rsvp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class ConfigBean {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {

        Event event = new Event("Boss's birthday party", "Boss's house");
        event.setEventDate(new GregorianCalendar(2010, Calendar.MAY, 23, 19, 0).getTime());
        em.persist(event);

        Person eventOwner = new Person("Boss", "OfMGM");
        em.persist(eventOwner);
        // set the relationships
        eventOwner.getOwnedEvents().add(event);
        eventOwner.getEvents().add(event);
        event.setOwner(eventOwner);
        event.getInvitees().add(eventOwner);
        Response ownersResponse = new Response(event, eventOwner, ResponseEnum.ATTENDING);
        em.persist(ownersResponse);
        event.getResponses().add(ownersResponse);
        //
        createInvitee("Tom", "Cat", event);
        createInvitee("Jerry", "Mouse", event);
    }

    private void createInvitee(String fristName, String lastName, Event event) {
        Person person = new Person(fristName, lastName);
        em.persist(person);

        event.getInvitees().add(person);
        person.getEvents().add(event);

        Response resp = new Response(event, person);
        em.persist(resp);
        event.getResponses().add(resp);
        person.getResponses().add(resp);
    }

}
