package javaeetutorial.rsvp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResponseEnum {
    
    ATTENDING("Attending"),
    NOT_ATTENDING("Not attending"),
    MAYBE_ATTENDING("Maybe"),
    NOT_RESPONDED("No response yet");
    
    @Getter
    private final String label;
    
}
