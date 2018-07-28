package javaeetutorial.jaxrs.rsvp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@SessionScoped
public class StatusManager implements Serializable {

    private static final long serialVersionUID = 1;
    private static final Logger logger = Logger.getLogger(StatusManager.class.getName());
    @Getter @Setter
    private Event event;
    private List<Event> events;
    private Client client;
    private final String baseUri = "http://localhost:8080/rsvp/rsvp-webapi";

    /**
     * Default constructor creates the JAX-RS client
     */
    public StatusManager() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    private void clean() {
        client.close();
    }

    public String getEventStatus(Event event) {
        this.setEvent(event);
        return "eventStatus"; // a JSF action string
    }

    public List<Event> getEvents() {
        List<Event> returnedEvents = null;
        try {
            returnedEvents = client.target(baseUri).path("/status/all").request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<Event>>() {
                    });
            if (returnedEvents == null) {
                logger.log(Level.SEVERE, "Returned events null.");
            } else {
                logger.log(Level.INFO, "Events have been returned.");
            }
        } catch (WebApplicationException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (ResponseProcessingException ex) {
            logger.log(Level.SEVERE, "ReponseProcessingException thrown: {0}", ex.getMessage());
        } catch (ProcessingException ex) {
            logger.log(Level.SEVERE, "ProcessingException thrown: {0}", ex.getMessage());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error retrieving all events: {0}", ex.getMessage());
            logger.log(Level.SEVERE, "base URI: {0}.", baseUri);
        }
        return returnedEvents;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public ResponseEnum[] getStatusValues() {
        return ResponseEnum.values();
    }

    public String changeStatus(ResponseEnum userResponse, Person person, Event event) {
        String navigation;
        try {
            logger.log(Level.INFO, "changing status to {0} for {1} {2} for event ID {3}.", new Object[] { userResponse,
                    person.getFirstName(), person.getLastName(), event.getId().toString() });
            client.target(baseUri).path(event.getId().toString()).path(person.getId().toString())
                    .request(MediaType.APPLICATION_XML).post(Entity.xml(userResponse.getLabel()));
            navigation = "changedStatus";
        } catch (ResponseProcessingException ex) {
            logger.log(Level.WARNING, "couldn''t change status for {0} {1}",
                    new Object[] { person.getFirstName(), person.getLastName() });
            logger.log(Level.WARNING, ex.getMessage());
            navigation = "error";
        }
        return navigation;
    }
}
