package javaeetutorial.websocket.chatroom.encoders;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;

import javaeetutorial.websocket.chatroom.messages.InfoMessage;

import static javaeetutorial.websocket.chatroom.messages.Message.Type.*;

public class InfoMessageEncoder extends MessageEncoder<InfoMessage> {

    @Override
    public String encode(InfoMessage infoMsg) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject().write("type", INFO).write("info", infoMsg.getInfo()).writeEnd();
        }
        return swriter.toString();
    }
}
