package javaeetutorial.hello1_formauth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@RequestScoped
@Getter @Setter
@NoArgsConstructor
public class Hello {

    private String name;

}
