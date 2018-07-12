package javaeetutorial.web.dukeetf;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/etf"}, asyncSupported=true)
public class DukeETFServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger("DukeETFServlet");
    private static final long serialVersionUID = 2114153638027156979L;
    
    private Queue<AsyncContext> requestQueue;
    
    @EJB private PriceVolumeBean pvbean; 
    
    @Override
    public void init(ServletConfig config) {
        requestQueue = new ConcurrentLinkedQueue<>();
        pvbean.registerServlet(this);
    }
    
    public void send(double price, int volume) {
        for (AsyncContext acontext : requestQueue) {// Send update to all connected clients
            try {
                PrintWriter writer = acontext.getResponse().getWriter();
                writer.write(String.format("%.2f / %d", price, volume));
                // Close the connection, The client (JavaScript) makes a new one instantly
                acontext.complete();
            } catch (IOException ex) {
                logger.log(Level.INFO, ex.toString());
            }
        }
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        // Put request in async mode.
        final AsyncContext acontext = request.startAsync();
        acontext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent ae) throws IOException {
                requestQueue.remove(acontext);
                logger.log(Level.INFO, "Connection closed.");
            }
            @Override
            public void onTimeout(AsyncEvent ae) throws IOException {
                requestQueue.remove(acontext);
                logger.log(Level.INFO, "Connection timeout.");
            }
            @Override
            public void onError(AsyncEvent ae) throws IOException {
                requestQueue.remove(acontext);
                logger.log(Level.INFO, "Connection error.");
            }
            @Override
            public void onStartAsync(AsyncEvent ae) throws IOException { }
        });
        requestQueue.add(acontext);
        logger.log(Level.INFO, "Connection open.");
    }
}
