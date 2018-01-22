package pei.jee.lab.jsf;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named
@RequestScoped
@Setter @Getter @NoArgsConstructor
public class Hello {
    private String name;
}

