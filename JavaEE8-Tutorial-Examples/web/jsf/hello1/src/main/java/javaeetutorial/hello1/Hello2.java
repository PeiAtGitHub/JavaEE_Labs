package javaeetutorial.hello1;

import javax.enterprise.inject.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Model
@NoArgsConstructor
@Setter
@Getter
public class Hello2 {

    private String name;

}

