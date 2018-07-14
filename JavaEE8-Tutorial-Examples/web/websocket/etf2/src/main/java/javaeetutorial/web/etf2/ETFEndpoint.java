package javaeetutorial.web.etf2;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/etf")
public class ETFEndpoint {
    
    private static final Logger logger = Logger.getLogger("ETFEndpoint");

    static Queue<Session> sessionsQueue = new ConcurrentLinkedQueue<>();
    
    /**
     * 
     */
    public static void send(double price, int volume) {
        try {
            for (Session session : sessionsQueue) {
                session.getBasicRemote().sendText(String.format("%.2f / %d", price, volume));
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        sessionsQueue.add(session);
    }
    
    @OnClose
    public void closedConnection(Session session) {
        sessionsQueue.remove(session);
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        sessionsQueue.remove(session);
    }
    
}
