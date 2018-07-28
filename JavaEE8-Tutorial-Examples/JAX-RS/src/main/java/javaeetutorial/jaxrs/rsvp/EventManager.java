package javaeetutorial.jaxrs.rsvp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Named
@SessionScoped
public class EventManager implements Serializable {

    private static final long serialVersionUID = -3240069895629955984L;
    private static final Logger logger = Logger.getLogger(EventManager.class.getName());

    @Getter
    @Setter
    protected Event currentEvent;
    @Getter
    @Setter
    private Response currentResponse;
    private Client client;
    private final String baseUri = "http://localhost:8080/JAX-RS/rsvp-webapi/status/";

    @PostConstruct
    private void init() {
        this.client = ClientBuilder.newClient();
    }

    @PreDestroy
    private void clean() {
        client.close();
    }
    

    public String retrieveEventStatus(Event event) {
        this.setCurrentEvent(event);
        return "eventStatus";
    }

    /**
     * Sets the current response and sends the navigation case
     */
    public String viewResponse(Response response) {
        this.currentResponse = response;
        return "viewResponse";
    }

    public List<Response> retrieveEventResponses() {
        if (this.currentEvent == null) {
            logger.log(Level.WARNING, "current event is null");
        }
        logger.log(Level.INFO, "getting responses for {0}", this.currentEvent.getName());
        try {
            Event event = client.target(baseUri).path(this.currentEvent.getId().toString())
                    .request(MediaType.APPLICATION_XML).get(Event.class);
            if (event == null) {
                logger.log(Level.WARNING, "returned event is null");
                return null;
            } else {
                return event.getResponses();
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "an error occurred when getting event responses.");
            return null;
        }
    }

}
