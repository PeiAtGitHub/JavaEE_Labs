package javaeetutorial.web.dukeetf;

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
/** 
 * Updates price and volume information every second 
 */
public class PriceVolumeBean {
    
    @Resource TimerService tservice;     // the container's timer service
    private Random random;
    private DukeETFServlet servlet;
    private volatile double price = 100.0;
    private volatile int volume = 300000;
    
    @PostConstruct
    public void init() {
        random = new Random();
        tservice.createIntervalTimer(1500, 1500, new TimerConfig());
    }
    
    public void registerServlet(DukeETFServlet servlet) {
        this.servlet = servlet;
    }
    
    @Timeout
    public void timeout() {
        price += 1.0*(random.nextInt(100)-50)/100.0;
        volume += random.nextInt(5000) - 2500;
        
        if (servlet != null)
            servlet.send(price, volume);
    }
}

