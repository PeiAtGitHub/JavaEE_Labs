package javaeetutorial.websocket.chatroom.encoders;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;

import javaeetutorial.websocket.chatroom.messages.JoinMessage;

import static javaeetutorial.websocket.chatroom.messages.Message.Type.*;

public class JoinMessageEncoder extends MessageEncoder<JoinMessage> {

    @Override
    public String encode(JoinMessage joinMsg) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject().write("type", JOIN).write("name", joinMsg.getName()).writeEnd();
        }
        return swriter.toString();
    }
}
