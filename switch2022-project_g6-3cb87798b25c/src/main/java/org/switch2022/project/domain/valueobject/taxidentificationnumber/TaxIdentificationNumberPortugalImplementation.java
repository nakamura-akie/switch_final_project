package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The TaxIdentificationNumberPortugalImplementation class represents an implementation of the
 * TaxIdentificationNumber interface for a portuguese tax identification number.
 * It also implements the DomainID interface.
 */
public class TaxIdentificationNumberPortugalImplementation implements DomainID, TaxIdentificationNumber {
    private final String portugueseTaxIdentificationNumberValue;

    /**
     * Constructs a new TaxIdentificationNumberPortugalImplementation object with the provided
     tax identification number value.

     * @param taxIdentificationNumberValue the tax identification number value to be set
     * @throws InvalidConstructorArgumentException if the provided tax identification number is invalid
     */
    public TaxIdentificationNumberPortugalImplementation(String taxIdentificationNumberValue) {
        validateValue(taxIdentificationNumberValue);
        this.portugueseTaxIdentificationNumberValue = taxIdentificationNumberValue;
    }

    private void validateValue(String taxIdentificationNumberValue) {
        if (!taxIdentificationNumberValidation(taxIdentificationNumberValue)) {
            throw new InvalidConstructorArgumentException("Invalid Portuguese Tax Identification Number");
        }
    }
    
    /**
     * @author Marco Lopes <marcolopespt@gmail.com>
     * adapted from https://github.com/marcolopes/dma/blob/master/org.dma.services
     * .vies/src/org/dma/services/vies/VatNumber.java
     */
    @Override
    public boolean taxIdentificationNumberValidation(String tinToValidate) {
        final int CORRECT_TIN_LENGTH = 9;
        //check if is numeric and has 9 numbers
        if (!taxIdentificationNumberOnlyContainsDigits(tinToValidate) ||
                taxIdentificationNumberIsNotCorrectLength(tinToValidate, CORRECT_TIN_LENGTH)) {
            return false;
        }
        int checkSum = 0;
        //calculate checkSum
        for (int i = 0; i < CORRECT_TIN_LENGTH - 1; i++) {
            checkSum += (tinToValidate.charAt(i)) * (CORRECT_TIN_LENGTH - i);
        }

        final int ELEVEN = 11;
        final int CHECK_DIGIT_LIMIT = 9;

        int checkDigit = ELEVEN - (checkSum % ELEVEN);
        //if checkDigit is higher than 9 set it to zero
        if (checkDigit > CHECK_DIGIT_LIMIT) {
            checkDigit = 0;
        }
        //compare checkDigit with the last number of NIF
        return checkDigit == tinToValidate.charAt(CORRECT_TIN_LENGTH - 1) - '0';
    }

    private static boolean taxIdentificationNumberIsNotCorrectLength(String tinToValidate, int correctTinLength) {
        return tinToValidate.length() != correctTinLength;
    }

    private static boolean taxIdentificationNumberOnlyContainsDigits(String tinToValidate) {
        return tinToValidate.matches("[0-9]+");
    }

    /**
     * Gets tax identification number.
     *
     * @return the portuguese tax identification number value
     */
    @Override
    public String getTaxIdentificationNumber() {
        return portugueseTaxIdentificationNumberValue;
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
        TaxIdentificationNumberPortugalImplementation that = (TaxIdentificationNumberPortugalImplementation) o;
        return Objects.equals(portugueseTaxIdentificationNumberValue, that.portugueseTaxIdentificationNumberValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(portugueseTaxIdentificationNumberValue);
    }
}
