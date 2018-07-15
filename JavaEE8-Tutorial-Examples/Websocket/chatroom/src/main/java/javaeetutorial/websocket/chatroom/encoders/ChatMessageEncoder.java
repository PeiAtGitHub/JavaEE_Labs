package javaeetutorial.websocket.chatroom.encoders;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;

import javaeetutorial.websocket.chatroom.messages.ChatMessage;

import static javaeetutorial.websocket.chatroom.messages.Message.Type.*;

public class ChatMessageEncoder extends MessageEncoder<ChatMessage> {

    @Override
    public String encode(ChatMessage chatMsg) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject().write("type", CHAT).write("from", chatMsg.getFrom())
                    .write("to", chatMsg.getTo()).write("message", chatMsg.getMessage()).writeEnd();
        }
        return swriter.toString();
    }
}
