package javaeetutorial.hello;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Root resource (exposed at "helloworld")
 */
@Path("helloworld")
public class HelloWorld {
    @Context
    private UriInfo context;

    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<html lang=\"en\"><body><h1>Hello, World!!</h1></body></html>";
    }

    /**
     * PUT method for updating or creating an instance of HelloWorld
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
