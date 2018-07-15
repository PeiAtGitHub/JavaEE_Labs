package javaeetutorial.websocket.chatroom.messages;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  Represents the list of users currently connected to the chat 
 */
@AllArgsConstructor
@Getter
public class UsersMessage extends Message {

    private List<String> userlist;
    
    @Override
    public String toString() {// For logging purposes
        return "[UsersMessage] " + userlist.toString();
    }

}
