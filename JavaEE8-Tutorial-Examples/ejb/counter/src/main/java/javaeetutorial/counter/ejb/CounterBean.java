package javaeetutorial.counter.ejb;

import javax.ejb.Singleton;

/**
 * A simple singleton session bean that records the number of hits to a web page.
 */
@Singleton
public class CounterBean {

    private int hits = 1;

    public int getHits() {
        return hits++;
    }
}
