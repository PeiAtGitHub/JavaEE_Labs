package javaeetutorial.json;

import java.io.*;
import java.util.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.*;
import javax.json.JsonValue.ValueType;
import javax.json.stream.JsonGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@SessionScoped
@Getter
@Setter
@NoArgsConstructor
public class ObjectModelBean implements Serializable {

    private static final long serialVersionUID = 5427226765445840012L;

    // JSON model information
    protected String documentJson;
    List<DOMTreeRow> rowList;

    // Form properties
    protected static final String PHONE_TYPE_HOME = "Home";
    protected static final String PHONE_TYPE_MOBILE = "Mobile";
    protected String firstName = "Duke";
    protected String lastName = "Java";
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

    public String getPhoneTypeHome() {
        return PHONE_TYPE_HOME;
    }

    public String getPhoneTypeMobile() {
        return PHONE_TYPE_MOBILE;
    }

    /**
     * Action method for the form in index.xhtml. 
     * Builds a JSON object model from form data.
     * 
     * @return JSF navigation
     */
    public String buildJson() {

        JsonObject model = Json.createObjectBuilder().add("firstName", firstName).add("lastName", lastName)
                .add("age", age).add("streetAddress", streetAddress).add("city", city).add("state", state)
                .add("postalCode", postalCode)
                .add("phoneNumbers",
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder().add("number", phoneNumber1).add("type", phoneType1))
                                .add(Json.createObjectBuilder().add("number", phoneNumber2).add("type", phoneType2)))
                .build();

        // Write JSON Output
//        StringWriter stWriter = new StringWriter();
//        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
//            jsonWriter.writeObject(model);
//        }

        // Write formatted JSON Output
        JsonWriterFactory factory = Json
                .createWriterFactory(Collections.singletonMap(JsonGenerator.PRETTY_PRINTING, ""));

        StringWriter stWriterF = new StringWriter();
        try (JsonWriter jsonWriterF = factory.createWriter(stWriterF)) {
            jsonWriterF.writeObject(model);
        }

        jsonTextArea = stWriterF.toString();

        return "modelcreated";
    }

    /**
     * Action method for form in modelcreated.xhtml. 
     * Parses JSON data from the textarea.
     * 
     * @return JSF navigation
     */
    public String jsonToDom() {
        // Parse the data using DOM approach
        JsonStructure parsed;
        try (JsonReader reader = Json.createReader(new StringReader(jsonTextArea))) {
            parsed = reader.readObject();
        }

        // Represent the DOM tree on a list of rows for a JSF table
        rowList = new ArrayList<>();
        printTree(parsed, 0, "");

        return "parsejson";
    }

    // Used to populate rowList to display the DOM tree on a JSF table
    private void printTree(JsonValue tree, int level, String key) {
        
        ValueType valueType = tree.getValueType();
        switch (valueType) {
        case OBJECT:
            JsonObject object = (JsonObject) tree;
            rowList.add(new DOMTreeRow(level, valueType.toString(), key, "--"));
            for (String name : object.keySet()) {
                printTree(object.get(name), level + 1, name);
            }
            break;
        case ARRAY:
            JsonArray array = (JsonArray) tree;
            rowList.add(new DOMTreeRow(level, valueType.toString(), key, "--"));
            for (JsonValue val : array) {
                printTree(val, level + 1, "");
            }
            break;
        case STRING:
            rowList.add(new DOMTreeRow(level, valueType.toString(), key, ((JsonString) tree).getString()));
            break;
        case NUMBER:
            rowList.add(new DOMTreeRow(level, valueType.toString(), key, ((JsonNumber) tree).toString()));
            break;
        case FALSE:
        case TRUE:
        case NULL:
            rowList.add(new DOMTreeRow(level, valueType.toString(), key, valueType.toString().toLowerCase()));
            break;
        }
    }

    /**
     * Represent the data of a row in the JSF table
     */
    @AllArgsConstructor
    @Getter
    public class DOMTreeRow {
        
        private int level;    // level in the DOM hierarchy
        private String type;  // JSON data type
        private String name;  // JSON data name
        private String value; // JSON data value
        
    }
    
}
