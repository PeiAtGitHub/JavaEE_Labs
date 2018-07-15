package javaeetutorial.websocket.chatroom.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinMessage extends Message {    

    private String name;
    
    @Override
    public String toString() {// For logging purposes
        return "[JoinMessage] " + name;
    }

}
