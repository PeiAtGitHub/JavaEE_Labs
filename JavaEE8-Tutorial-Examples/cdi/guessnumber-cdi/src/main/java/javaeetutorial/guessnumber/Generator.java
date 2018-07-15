package javaeetutorial.guessnumber;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class Generator implements Serializable {

   private static final long serialVersionUID = -7213673465118041882L;

   private final java.util.Random random = new java.util.Random();

   @Produces 
   @Random 
   int next() {
       return random.nextInt(101);
   }

   @Produces 
   @MaxNumber 
   int getMaxNumber() {
       return 100;
   }

}

