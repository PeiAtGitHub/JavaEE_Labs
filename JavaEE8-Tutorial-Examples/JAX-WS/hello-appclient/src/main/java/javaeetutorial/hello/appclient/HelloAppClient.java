package javaeetutorial.hello.appclient;

import javaeetutorial.helloservice.endpoint.HelloService;
import javax.xml.ws.WebServiceRef;

public class HelloAppClient {
    
    @WebServiceRef(wsdlLocation = "http://localhost:8080/helloservice/HelloService?WSDL")
    private static HelloService service;

    public static void main(String[] args) {
       System.out.println(service.getHelloPort().sayHello("world"));
    }
}
