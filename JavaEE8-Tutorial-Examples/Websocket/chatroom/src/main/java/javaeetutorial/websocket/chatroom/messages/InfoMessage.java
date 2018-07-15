package javaeetutorial.websocket.chatroom.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InfoMessage extends Message {
    
    private String info;
    
    @Override
    public String toString() {// For logging purposes
        return "[InfoMessage] " + info;
    }

}
