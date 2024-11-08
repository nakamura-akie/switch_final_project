package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The TaxIdentificationNumberSpainImplementation class represents an implementation of the
 * TaxIdentificationNumber interface for a spanish tax identification number.
 * It also implements the DomainID interface.
 */
public class TaxIdentificationNumberSpainImplementation implements DomainID, TaxIdentificationNumber {

    private final String spanishTaxIdentificationNumberValue;

    /**
     * Constructs a new TaxIdentificationNumberSpainImplementation object with the provided
     tax identification number value.

     * @param taxIdentificationNumberValue the tax identification number value to be set
     * @throws InvalidConstructorArgumentException if the provided tax identification number is invalid
     */
    public TaxIdentificationNumberSpainImplementation(String taxIdentificationNumberValue) {
        validateValue(taxIdentificationNumberValue);
        this.spanishTaxIdentificationNumberValue = taxIdentificationNumberValue;
    }

    private void validateValue(String taxIdentificationNumberValue) {
        if (!taxIdentificationNumberValidation(taxIdentificationNumberValue)) {
            throw new InvalidConstructorArgumentException("Invalid Spanish Tax Identification Number");
        }
    }

    /**
     * adapted from https://es.wikipedia.org/wiki/N%C3%BAmero_de_identificaci%C3%B3n_fiscal
     *
     * The code validates a Spanish NIF number by performing various checks and calculations.
     * It checks the length, converts the NIF to uppercase, extracts the number part,
     * replaces the initial letter if it is a NIE (Foreigner Identification Number),
     * calculates the index based on the number,
     * and compares the last letter with the letter obtained from the letterSequence.
     * If they match, the NIF is considered valid.
     */
    @Override
    public boolean taxIdentificationNumberValidation(String taxIdentificationNumber) {
        final int CORRECT_TIN_LENGTH = 9;

        //confirm that it has 9 elements
        if (taxIdentificationNumberIsNotCorrectLength(taxIdentificationNumber, CORRECT_TIN_LENGTH)) {
            return false;
        }

        String letterSequence = "TRWAGMYFPDXBNJZSQVHLCKE";
        String tinToValidate = taxIdentificationNumber.toUpperCase();

        String numberTIN = tinToValidate.substring(0, tinToValidate.length() - 1);

        //If it is a NIE, replace the initial letter with its numeric value
        numberTIN = numberTIN.replace("X", "0").replace("Y", "1").replace("Z",
                "2");

        if (!numberTIN.matches("[0-9]+")) {
            return false;
        }

        //Obtain the letter with a charAt that will also serve as the index for the letterSequence
        final int INDEX_TO_OBTAIN_LETTER = 8;
        final int MODULO_DIVISOR = 23;

        char letterNIF = tinToValidate.charAt(INDEX_TO_OBTAIN_LETTER);
        int i = Integer.parseInt(numberTIN) % MODULO_DIVISOR;
        return letterNIF == letterSequence.charAt(i);
    }


    private static boolean taxIdentificationNumberIsNotCorrectLength(String taxIdentificationNumber,
                                                                     int correctTinLength) {
        return taxIdentificationNumber.length() != correctTinLength;
    }

    /**
     * Gets tax identification number.
     *
     * @return the spanish tax identification number value
     */
    @Override
    public String getTaxIdentificationNumber() {
        return spanishTaxIdentificationNumberValue;
    }

    /**
     * Checks if this TaxIdentificationNumber is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaxIdentificationNumberSpainImplementation that = (TaxIdentificationNumberSpainImplementation) o;
        return Objects.equals(spanishTaxIdentificationNumberValue, that.spanishTaxIdentificationNumberValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(spanishTaxIdentificationNumberValue);
    }
}
