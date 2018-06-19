package javaeetutorial.jsf.reservation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
@Getter
@Setter
public class ReservationBean implements Serializable {

    private static final long serialVersionUID = -7258677092121565610L;

    private String name = "";
    private String totalValue = "120.00";
    private String email = "";
    private String emailAgain = "";
    private String date = "";
    private String tickets = "1";
    private String price = "120";
    private Map<String, Object> ticketAttrs;

    public ReservationBean() {
        this.ticketAttrs = new HashMap<>();
        this.ticketAttrs.put("type", "number");
        this.ticketAttrs.put("min", "1");
        this.ticketAttrs.put("max", "4");
        this.ticketAttrs.put("required", "required");
        this.ticketAttrs.put("title", "Enter a number between 1 and 4 inclusive.");
    }

    public void calculateTotal(AjaxBehaviorEvent event) throws AbortProcessingException {
        int ticketsNum = 1;
        int ticketPrice = 0;

        if (tickets.trim().length() > 0) {
            ticketsNum = Integer.parseInt(tickets);
        }
        if (price.trim().length() > 0) {
            ticketPrice = Integer.parseInt(price);
        }
       totalValue = String.valueOf((ticketsNum * ticketPrice)) + ".00";
    }

    public void clear(AjaxBehaviorEvent event) throws AbortProcessingException {
        name = "";
        email = "";
        emailAgain = "";
        date = "";
        price = "120.00";
        totalValue = "120.00";
        tickets = "1";
    }
}
