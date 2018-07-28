package javaeetutorial.jsf.hello1;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;

import lombok.Getter;
import lombok.Setter;

@Model
@RequestScoped
@Setter
@Getter
public class Hello {

    private String name;

}

