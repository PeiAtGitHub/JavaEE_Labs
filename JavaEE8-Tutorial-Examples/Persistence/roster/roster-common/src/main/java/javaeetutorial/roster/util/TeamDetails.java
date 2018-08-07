package javaeetutorial.roster.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TeamDetails implements Serializable {
    
    private static final long serialVersionUID = -1618941013515364318L;
    
    private String id;
    private String name;
    private String city;

    @Override
    public String toString() {
        return id + " " + name + " " + city;
    }

}
