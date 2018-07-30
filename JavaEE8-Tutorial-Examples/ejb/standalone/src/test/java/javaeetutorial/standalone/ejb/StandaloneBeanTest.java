/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * https://github.com/javaee/tutorial-examples/LICENSE.txt
 */
package javaeetutorial.standalone.ejb;

import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class StandaloneBeanTest {

    private EJBContainer ec;
    private Context ctx;

    public StandaloneBeanTest() {
    }

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
