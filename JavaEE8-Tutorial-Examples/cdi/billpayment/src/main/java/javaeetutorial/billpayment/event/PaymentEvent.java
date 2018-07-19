package javaeetutorial.billpayment.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PaymentEvent implements Serializable {
    
    private static final long serialVersionUID = -6407967360613478424L;
    
    public String paymentType;
    public BigDecimal value;
    public Date datetime;

    @Override
    public String toString() {
        return this.paymentType
                + " = $" + this.value.toString()
                + " at " + this.datetime.toString();
    }

}
