package javaeetutorial.compositecomponentexample;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.validation.constraints.Size;

import org.apache.commons.math3.primes.Primes;

import lombok.Getter;
import lombok.Setter;

@Model
@Setter
@Getter
public class PrimeBean implements Serializable {

    private static final long serialVersionUID = -50939649434906127L;

    private static final String RETURN_PAGE = "index";

    @Size(min = 1, max = 9)
    private String input;

    private String response;

    public String primeCheck() {
        int inputNumber;
        try {
            inputNumber = Integer.valueOf(input);
        } catch (Exception e) {
            setResponse("Input is not a valid integer number of max 9 digits.");
            return RETURN_PAGE;
        }

        if (Primes.isPrime(inputNumber)) {
            setResponse(inputNumber + " is a prime number.");
        } else {
            setResponse(inputNumber + " is NOT a prime number. It is divisible by: "
                    + Primes.primeFactors(inputNumber).toString());
        }

        return RETURN_PAGE;

    }

}
