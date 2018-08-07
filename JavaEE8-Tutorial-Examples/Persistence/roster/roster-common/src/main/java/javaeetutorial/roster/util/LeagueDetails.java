package javaeetutorial.roster.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LeagueDetails implements Serializable {
    
    private static final long serialVersionUID = 290368886584321980L;
    private String id;
    private String name;
    private String sport;

    @Override
    public String toString() {
        return id + " " + name + " " + sport;
    }

}
