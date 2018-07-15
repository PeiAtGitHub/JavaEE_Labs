package javaeetutorial.websocket.chatroom.decoders;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.common.collect.Lists;

import javaeetutorial.websocket.chatroom.messages.ChatMessage;
import javaeetutorial.websocket.chatroom.messages.JoinMessage;
import javaeetutorial.websocket.chatroom.messages.Message;

import static javaeetutorial.websocket.chatroom.messages.Message.Type.*;

/**
 * JSON --> Object
 */
public class MessageDecoder implements Decoder.Text<Message> {

    /** Stores the JSON name-value pairs as a Map */
    private Map<String, String> messageMap;

    /**
     * JSON --> a new Message object
     */
    @Override
    public Message decode(String jsonStr) throws DecodeException {
        Message msg = null;
        if (willDecode(jsonStr)) {
            switch (messageMap.get("type")) {
            case JOIN:
                msg = new JoinMessage(messageMap.get("name"));
                break;
            case CHAT:
                msg = new ChatMessage(messageMap.get("from"), messageMap.get("to"), messageMap.get("message"));
            }
        } else {
            throw new DecodeException(jsonStr, "[Message] Can't decode.");
        }
        return msg;
    }

    @Override
    public boolean willDecode(String jsonStr) {
        messageMap = jsonToMap(jsonStr);
        return isMessageDecodable(messageMap); // check if it contains required fields according to its type
    }

    private HashMap<String, String> jsonToMap(String jsonStr) {
        HashMap<String, String> result = new HashMap<>();
        JsonParser parser = Json.createParser(new StringReader(jsonStr));
        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.KEY_NAME) {
                String key = parser.getString();
                parser.next();
                String value = parser.getString();
                result.put(key, value);
            }
        }
        return result;
    }

    private boolean isMessageDecodable(Map<String, String> msgMap) {

        boolean result = false;

        Set<String> keys = msgMap.keySet();
        if (keys.contains("type")) {
            String type = msgMap.get("type");
            if (JOIN.equals(type)) {
                result = keys.contains("name");
            } else if (CHAT.equals(type)) {
                result = keys.containsAll(Lists.newArrayList("from", "to", "message"));
            }
        }
        return result;
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

}
