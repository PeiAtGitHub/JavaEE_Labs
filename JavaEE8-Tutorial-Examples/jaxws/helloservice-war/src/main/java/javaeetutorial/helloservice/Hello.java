package javaeetutorial.helloservice;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class Hello {

    @WebMethod
    public String sayHello(String name) {
        return "Hello, " + name + ".";
    }
}
