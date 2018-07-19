package javaeetutorial.billpayment.listener;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaeetutorial.billpayment.event.NoArgsConstructor;
import javaeetutorial.billpayment.event.PaymentEvent;
import javaeetutorial.billpayment.interceptor.Logged;
import javaeetutorial.billpayment.payment.Credit;
import javaeetutorial.billpayment.payment.Debit;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

@Logged
@SessionScoped
@NoArgsConstructor
public class PaymentHandler implements Serializable {

    private static final Logger logger = Logger.getLogger(PaymentHandler.class.getCanonicalName());
    private static final long serialVersionUID = 2013564481486393525L;

    public void creditPayment(@Observes @Credit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Credit Handler: {0}", event.toString());
        // call a specific Credit handler class...
    }

    public void debitPayment(@Observes @Debit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Debit Handler: {0}", event.toString());
        // call a specific Debit handler class...
    }
}
