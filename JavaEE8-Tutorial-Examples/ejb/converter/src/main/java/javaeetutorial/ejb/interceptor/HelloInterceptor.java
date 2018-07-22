package javaeetutorial.ejb.interceptor;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class HelloInterceptor {
    
    private static final Logger logger = Logger.getLogger("interceptor.ejb.HelloInterceptor");

    @AroundInvoke
    public Object modifyGreeting(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        parameters[0] = ((String) parameters[0]).toLowerCase();
        ctx.setParameters(parameters);
        try {
            return ctx.proceed();
        } catch (Exception e) {
            logger.warning("Error calling ctx.proceed in modifyGreeting()");
            return null;
        }
    }

}
