package javaeetutorial.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class manages the data from a JSF form, creates Object and serialize
 * this object to JSON. This class also deserialize JSON to object.
 */
@Named
@SessionScoped
@Data
@NoArgsConstructor
public class JsonbBean implements Serializable {

    private static final long serialVersionUID = 5427226765445840012L;

    /*
     * The fields pre-set values are defaults, ease testing and demo. 
     */
    protected String phoneTypeHome = "Home";
    protected String phoneTypeWork = "Work";

    private String name = "Jason Bourne";
    private String profession = "Machinery Engineer";

    private String phoneType1 = phoneTypeHome;
    private String number1 = "123-456-789";

    private String phoneType2 = phoneTypeWork;
    private String number2 = "123-555-555";

    protected String jsonTextArea = "";

    private final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

    /**
     * Action method for the form in index.xhtml. Creates Person object and creates
     * formatted JSON
     * 
     * @return is the JSF navigation
     */
    public String createJson() {
        Person person = new Person(this.name, this.profession, new ArrayList<>());
        person.getPhoneNumbers().add(new PhoneNumber(this.phoneType1, this.number1));
        person.getPhoneNumbers().add(new PhoneNumber(this.phoneType2, this.number2));

        // Serialization, Obj-->JSON
        this.jsonTextArea = jsonb.toJson(person);

        return "jsongenerated";
    }

    /**
     * Action method for form in jsongenerated.xhtml. Deserialize JSON from the
     * textarea to Person object and fills these data to form.
     * 
     * @return is the JSF navigation
     */
    public String parseJson() {

        clearFields();

        // Deserialization, JSON --> Object
        Person person = jsonb.fromJson(this.jsonTextArea, Person.class);

        this.name = person.getName();
        this.profession = person.getProfession();

        List<PhoneNumber> phoneNumbers = person.getPhoneNumbers();
        if (phoneNumbers != null) {
            if (phoneNumbers.get(0) != null) {
                this.number1 = phoneNumbers.get(0).getNumber();
                this.phoneType1 = phoneNumbers.get(0).getType();
            }
            if (phoneNumbers.get(1) != null) {
                this.number2 = phoneNumbers.get(1).getNumber();
                this.phoneType2 = phoneNumbers.get(1).getType();
            }
        }

        return "index";
    }

    private void clearFields() {
        this.name = "";
        this.profession = "";
        this.number1 = "";
        this.phoneType1 = "";
        this.number2 = "";
        this.phoneType2 = "";
    }

}
