package javaeetutorial.jsonbmodel.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    
    private String name;
    private String profession;
    private List<PhoneNumber> phoneNumbers;

}
