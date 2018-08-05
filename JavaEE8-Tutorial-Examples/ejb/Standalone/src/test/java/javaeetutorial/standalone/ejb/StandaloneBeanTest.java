package javaeetutorial.standalone.ejb;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StandaloneBeanTest {

    private EJBContainer ec;
    private Context ctx;

    @Before
    public void setUp() {
        ec = EJBContainer.createEJBContainer();
        ctx = ec.getContext();
    }

    @After
    public void tearDown() {
        if (ec != null) {
            ec.close();
        }
    }

    @Test
    public void testReturnMessage() throws Exception {
        StandaloneBean instance = (StandaloneBean) ctx.lookup("java:global/classes/StandaloneBean");
        assertEquals("Greetings!", instance.returnMessage());
    }
}
