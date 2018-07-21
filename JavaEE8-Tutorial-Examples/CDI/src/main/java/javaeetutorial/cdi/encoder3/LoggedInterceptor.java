package javaeetutorial.cdi.encoder3;

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import lombok.NoArgsConstructor;

@Logged
@Interceptor
@NoArgsConstructor
public class LoggedInterceptor implements Serializable {
    
    private static final long serialVersionUID = -2019240634188419271L;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        System.out.println("Entering method: "
                + invocationContext.getMethod().getName() + " in class "
                + invocationContext.getMethod().getDeclaringClass().getName());

        return invocationContext.proceed();
    }
}
