package javaeetutorial.helloservice.ejb;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * a WS EP implemented as a stateless session bean.
 */
@Stateless
@WebService
public class HelloServiceBean {
    
    @WebMethod
    public String sayHello(String name) {
        return "Hello, " + name + ".";
    }
}
