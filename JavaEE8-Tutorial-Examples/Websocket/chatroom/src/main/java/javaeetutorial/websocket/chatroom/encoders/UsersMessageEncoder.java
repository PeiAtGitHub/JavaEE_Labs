package javaeetutorial.websocket.chatroom.encoders;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;

import javaeetutorial.websocket.chatroom.messages.UsersMessage;

import static javaeetutorial.websocket.chatroom.messages.Message.Type.*;

public class UsersMessageEncoder extends MessageEncoder<UsersMessage> {

    @Override
    public String encode(UsersMessage usersMsg) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject().write("type", USERS).writeStartArray("userlist");
            for (String user : usersMsg.getUserlist()) {
                jsonGen.write(user);
            }
            jsonGen.writeEnd().writeEnd();
        }
        return swriter.toString();
    }
}
