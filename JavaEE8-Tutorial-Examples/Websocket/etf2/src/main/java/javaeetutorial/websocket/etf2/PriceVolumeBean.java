package javaeetutorial.websocket.etf2;

import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;


@Startup
@Singleton
public class PriceVolumeBean {

    // Use the container's timer service
    @Resource TimerService tservice;
    private Random random;
    private volatile double price = 100.0;
    private volatile int volume = 300000;
    
    @PostConstruct
    public void init() {// Initialize the EJB and create a timer 
        random = new Random();
        tservice.createIntervalTimer(1000, 5000, new TimerConfig());
    }
    
    @Timeout
    public void timeout() {
        price += 1.0*(random.nextInt(100)-50)/100.0;
        volume += random.nextInt(5000) - 2500;
        ETFEndpoint.send(price, volume);
    }
}
