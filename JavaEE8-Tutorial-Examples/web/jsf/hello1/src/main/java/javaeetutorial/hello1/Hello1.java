package javaeetutorial.hello1;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@RequestScoped
@NoArgsConstructor
@Setter
@Getter
public class Hello1 {

    private String name;

}

