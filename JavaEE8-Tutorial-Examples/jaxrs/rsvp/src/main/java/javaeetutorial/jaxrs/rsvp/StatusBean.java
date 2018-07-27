package javaeetutorial.jaxrs.rsvp;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Named
@Path("/status")
public class StatusBean {

    private List<Event> allCurrentEvents;
    private static final Logger logger = Logger.getLogger("javaeetutorial.rsvp.ejb.StatusBean");

    @PersistenceContext
    private EntityManager em;

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{eventId}/")
    public Event getEvent(@PathParam("eventId") Long eventId) {
        Event event = em.find(Event.class, eventId);
        return event;
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("all")
    public List<Event> getAllCurrentEvents() {
        logger.info("Calling getAllCurrentEvents");
        this.allCurrentEvents = (List<Event>) em.createNamedQuery("rsvp.entity.Event.getAllUpcomingEvents")
                .getResultList();
        if (this.allCurrentEvents == null) {
            logger.warning("No current events!");
        }
        return this.allCurrentEvents;
    }

    public void setAllCurrentEvents(List<Event> events) {
        this.allCurrentEvents = events;
    }
}
