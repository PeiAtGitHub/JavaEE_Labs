package javaeetutorial.websocket.chatroom;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.common.collect.Lists;

import javaeetutorial.websocket.chatroom.decoders.MessageDecoder;
import javaeetutorial.websocket.chatroom.encoders.ChatMessageEncoder;
import javaeetutorial.websocket.chatroom.encoders.InfoMessageEncoder;
import javaeetutorial.websocket.chatroom.encoders.JoinMessageEncoder;
import javaeetutorial.websocket.chatroom.encoders.UsersMessageEncoder;
import javaeetutorial.websocket.chatroom.messages.ChatMessage;
import javaeetutorial.websocket.chatroom.messages.InfoMessage;
import javaeetutorial.websocket.chatroom.messages.JoinMessage;
import javaeetutorial.websocket.chatroom.messages.Message;
import javaeetutorial.websocket.chatroom.messages.UsersMessage;

@ServerEndpoint(value = "/chatroom", decoders = { MessageDecoder.class }, encoders = { JoinMessageEncoder.class,
        ChatMessageEncoder.class, InfoMessageEncoder.class, UsersMessageEncoder.class })
/**
 * Each connection has an EP instance
 */
public class ChatroomEndpoint {

    static final String BOT_NAME = "bot";

    private static final Logger logger = Logger.getLogger("ChatroomEndpoint");

    @Inject
    private BotBean botbean;

    @Resource(name = "comp/DefaultManagedExecutorService")
    private ManagedExecutorService mes; // for async processing

    @OnOpen
    public void openConnection(Session session) {
        logger.log(Level.INFO, "Connection opened.");
    }

    @OnMessage
    public void message(final Session session, Message msg) {

        logger.log(Level.INFO, "Received: {0}", msg.toString());
        
        if (msg instanceof JoinMessage) {
            addNewUserAndNotifyAll(session, (JoinMessage) msg);
        } else if (msg instanceof ChatMessage) {
            final ChatMessage chatMsg = (ChatMessage) msg;
            sendAll(session, chatMsg);
            if (BOT_NAME.equals(chatMsg.getTo())) {// bot replies to the message
                mes.submit(() -> sendAll(session, new ChatMessage(BOT_NAME, chatMsg.getFrom(), botbean.respond(chatMsg.getMessage()))));
            }
        }
    }

    private void addNewUserAndNotifyAll(Session session, JoinMessage jmsg) {
        session.getUserProperties().put("name", jmsg.getName());
        session.getUserProperties().put("active", true);
        sendAll(session, new InfoMessage(jmsg.getName() + " has joined the chat"));
        sendAll(session, new ChatMessage(BOT_NAME, jmsg.getName(), "Hi there!!"));
        sendAll(session, new UsersMessage(this.getUserlist(session)));
    }

    /*
     * closing browser will close the session
     */
    @OnClose
    public void closedConnection(Session session) {
        session.getUserProperties().put("active", false);
        if (session.getUserProperties().containsKey("name")) {
            String name = session.getUserProperties().get("name").toString();
            sendAll(session, new InfoMessage(name + " has left the chat"));
            sendAll(session, new UsersMessage(this.getUserlist(session)));
        }
        logger.log(Level.INFO, "Connection closed.");
    }

    @OnError
    public void error(Session session, Throwable t) {
        logger.log(Level.INFO, "Connection error ({0})", t.toString());
    }

    /**
     * Forward a message to all connected clients. The EP figures what encoder
     * to use based on the message type
     */
    public synchronized void sendAll(Session session, Object msg) {
        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(msg);
                    logger.log(Level.INFO, "Sent: {0}", msg.toString());
                }
            }
        } catch (IOException | EncodeException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    /**
     * Returns the list of users from the properties of all open sessions 
     */
    private List<String> getUserlist(Session session) {
        List<String> users = Lists.newArrayList(BOT_NAME);
        for (Session s : session.getOpenSessions()) {
            if (s.isOpen() && (boolean) s.getUserProperties().get("active"))
                users.add(s.getUserProperties().get("name").toString());
        }
        return users;
    }
    
}
