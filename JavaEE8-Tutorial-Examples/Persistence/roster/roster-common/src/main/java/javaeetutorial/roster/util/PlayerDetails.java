package javaeetutorial.roster.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayerDetails implements Serializable {
    
    private static final long serialVersionUID = -5352446961599198526L;

    private String id;
    private String name;
    private String position;
    private double salary;

    @Override
    public String toString() {
        return id + " " + name + " " + position + " " + salary;
    }

}
