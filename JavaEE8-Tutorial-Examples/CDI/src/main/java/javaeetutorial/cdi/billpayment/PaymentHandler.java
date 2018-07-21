package javaeetutorial.cdi.billpayment;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaeetutorial.cdi.billpayment.Credit;
import javaeetutorial.cdi.billpayment.Debit;
import javaeetutorial.cdi.encoder3.Logged;
import lombok.NoArgsConstructor;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

@Logged
@SessionScoped
@NoArgsConstructor
public class PaymentHandler implements Serializable {

    private static final Logger logger = Logger.getLogger(PaymentHandler.class.getCanonicalName());
    private static final long serialVersionUID = 2013564481486393525L;

    public void creditPayment(@Observes @Credit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Processing Credit Payment: {0}", event.toString());
    }

    public void debitPayment(@Observes @Debit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Processing Debit Payment: {0}", event.toString());
    }
}
