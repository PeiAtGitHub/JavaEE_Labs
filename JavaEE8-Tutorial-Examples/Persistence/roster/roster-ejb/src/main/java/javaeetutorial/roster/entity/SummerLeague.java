package javaeetutorial.roster.entity;

import java.io.Serializable;
import javaeetutorial.roster.util.IncorrectSportException;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class SummerLeague extends League implements Serializable {
    
    private static final long serialVersionUID = 4846138039113922695L;

    public SummerLeague(String id, String name, String sport) throws IncorrectSportException {
        this.id = id;
        this.name = name;
        if (sport.equalsIgnoreCase("swimming") 
                || sport.equalsIgnoreCase("soccer")
                || sport.equalsIgnoreCase("basketball") 
                || sport.equalsIgnoreCase("baseball")) {
            this.sport = sport;
        } else {
            throw new IncorrectSportException("Sport is not a summer sport.");
        }
    }
}
