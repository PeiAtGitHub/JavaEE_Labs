package javaeetutorial.websocket.chatroom.encoders;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import javaeetutorial.websocket.chatroom.messages.Message;

/**
 * Message object --> JSON
 */
public abstract class MessageEncoder<T extends Message> implements Encoder.Text<T> {
    
    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

    /**
     * Message object --> JSON
     */
    abstract public String encode(T msg) throws EncodeException ;
    
}
