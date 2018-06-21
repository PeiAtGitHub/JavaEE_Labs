package javaeetutorial.json;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.*;
import javax.json.stream.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Named
@SessionScoped
@Slf4j
@NoArgsConstructor
@Setter
@Getter
public class StreamingBean implements Serializable {

    private static final long serialVersionUID = 5587157797666076243L;

    // Form properties
    protected static final String PHONE_TYPE_HOME = "Home";
    protected static final String PHONE_TYPE_MOBILE = "Mobile";
    protected String firstName = "Bill";
    protected String lastName = "Gates";
    protected int age = 18;
    protected String streetAddress = "100 Internet Dr";
    protected String city = "JavaTown";
    protected String state = "JA";
    protected String postalCode = "12345";
    protected String phoneNumber1 = "111-111-1111";
    protected String phoneType1 = PHONE_TYPE_MOBILE;
    protected String phoneNumber2 = "222-222-2222";
    protected String phoneType2 = PHONE_TYPE_HOME;

    protected String jsonTextArea = "";

    // Other properties
    protected String fileName;
    protected List<EventRow> rowList;

    public String getPhoneTypeHome() {
        return PHONE_TYPE_HOME;
    }

    public String getPhoneTypeMobile() {
        return PHONE_TYPE_MOBILE;
    }

    /**
     * Action method for form in index.xhtml. 
     * Writes JSON data using the streaming API.
     * 
     * @return JSF Navigation
     */
    public String writeJsonToFile() {
        /*
         *  Write JSON data to a file, which is stored at glassfish5/glassfish/domains/[your domain]/config
         */
        fileName = "jsonoutput-" + System.currentTimeMillis() + ".json";
        try {
            try (JsonGenerator gen = Json.createGenerator(new FileWriter(fileName))) {
                gen.writeStartObject().write("firstName", firstName).write("lastName", lastName).write("age", age)
                        .write("streetAddress", streetAddress).write("city", city).write("state", state)
                        .write("postalCode", postalCode).writeStartArray("phoneNumbers").writeStartObject()
                        .write("number", phoneNumber1).write("type", phoneType1).writeEnd().writeStartObject()
                        .write("number", phoneNumber2).write("type", phoneType2).writeEnd().writeEnd().writeEnd();
            }
        } catch (IOException e) {
            log.warn("Error writing JSON to file {0}", fileName + "-" + e.toString());
        }

        jsonTextArea = readJsonFileToString();

        return "filewritten";
    }

    /**
     * Action method for form in filewritten.xhtml. Parse JSON and populate list of
     * parser events for JSF table
     * @return JSF Navigation 
     */
    public String parseJson() {
        try {
            int nrow = 1;
            rowList = new ArrayList<>();
            JsonParser parser = Json.createParser(new FileReader(fileName));
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    rowList.add(new EventRow(nrow++, event.toString(), "--"));
                    break;
                case KEY_NAME:
                case VALUE_STRING:
                case VALUE_NUMBER:
                    rowList.add(new EventRow(nrow++, event.toString(), parser.getString()));
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            log.warn("JSON file {0} does not exist", fileName);
        }

        return "parsed";
    }

    private String readJsonFileToString() {
        String content = "";
        try {
            String line;
            BufferedReader bread = new BufferedReader(new FileReader(fileName));
            while ((line = bread.readLine()) != null) {
                content = content + line;
            }
        } catch (FileNotFoundException e) {
            log.warn("JSON file {0} does not exist", fileName);
        } catch (IOException e) {
            log.warn("Error reading from file {0}", fileName);
        }
        return content;
    }

    /**
     *  Data in a row in JSF table 
     */
    @Getter
    @AllArgsConstructor
    public class EventRow {
        
        private int number;
        private String type;
        private String details;

    }
}
