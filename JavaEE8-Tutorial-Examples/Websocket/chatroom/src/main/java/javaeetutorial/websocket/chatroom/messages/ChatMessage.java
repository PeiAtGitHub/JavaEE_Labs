package javaeetutorial.websocket.chatroom.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChatMessage extends Message {

    private String from;
    private String to;
    private String message;
    
    @Override
    public String toString() {// For logging purposes
        return "[ChatMessage] " + from + "-" + to + "-" + message;
    }

}
