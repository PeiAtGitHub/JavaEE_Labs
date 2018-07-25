package javaeetutorial.hello.webclient;

import javaeetutorial.helloservice.endpoint.HelloService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

@WebServlet(name="HelloServlet", urlPatterns={"/HelloServlet"})
public class HelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @WebServiceRef(wsdlLocation = "http://localhost:8080/helloservice/HelloService?WSDL")
    private HelloService service;
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"en\">");
            out.println("<head><title>Servlet HelloServlet</title></head>");
            out.println("<body>");
            out.println("<h2>Servlet HelloServlet at " + request.getContextPath () + "</h2>");
            out.println("<p>" + service.getHelloPort().sayHello("WORLD!") + "</p>");
            out.println("</body></html>");
        }
    } 

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
