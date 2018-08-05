package javaeetutorial.ejb.converter;

import java.math.BigDecimal;
import javax.ejb.Stateless;

@Stateless
public class ConverterBean {
    
    private final static int RESULT_SCALE = 2;
    
    private final static BigDecimal JPY_RATE = new BigDecimal("111");
    private final static BigDecimal EUR_RATE = new BigDecimal("0.85");
    
    public BigDecimal dollarToYen(BigDecimal dollars) {
        return dollars.multiply(JPY_RATE).setScale(RESULT_SCALE, BigDecimal.ROUND_UP);
    }
    
    public BigDecimal dollarToEuro(BigDecimal dollar) {
        return dollar.multiply(EUR_RATE).setScale(RESULT_SCALE, BigDecimal.ROUND_UP);
    }
    
}
