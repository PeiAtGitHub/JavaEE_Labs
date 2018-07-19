package javaeetutorial.cdi.billpayment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;


import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Digits;

import javaeetutorial.cdi.encoder3.Logged;
import lombok.Getter;
import lombok.Setter;

/**
 * Bean fires DEBIT and CREDIT payment events based on UI selection.
 */
@Named
@SessionScoped
public class PaymentBean implements Serializable {

    private static final Logger logger = Logger.getLogger(PaymentBean.class.getCanonicalName());
    private static final long serialVersionUID = 7130389273118012929L;

    private static final int DEBIT = 1;
    private static final int CREDIT = 2;    
    
    @Inject
    @Credit
    Event<PaymentEvent> creditEvent;

    @Inject
    @Debit
    Event<PaymentEvent> debitEvent;

    @Getter @Setter
    private int paymentOption = DEBIT;

    @Getter @Setter
    @Digits(integer = 10, fraction = 2, message = "Invalid value")
    private BigDecimal value;

    @Getter @Setter
    private Date datetime;

    /**
     * @return the response page location
     */
    @Logged
    public String pay() {
        this.setDatetime(Calendar.getInstance().getTime());
        switch (paymentOption) {
            case DEBIT:
                debitEvent.fire(new PaymentEvent("Debit", value, datetime));
                break;
            case CREDIT:
                creditEvent.fire(new PaymentEvent("Credit", value, datetime));
                break;
            default:
                logger.severe("Invalid payment option!");
        }
        return "response";
    }

    @Logged
    public void reset() {
        setPaymentOption(DEBIT);
        setValue(BigDecimal.ZERO);
    }

}
