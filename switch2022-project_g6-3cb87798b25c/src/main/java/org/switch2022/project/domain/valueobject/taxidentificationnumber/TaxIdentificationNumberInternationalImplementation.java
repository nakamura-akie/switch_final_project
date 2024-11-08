package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The TaxIdentificationNumberInternationalImplementation class represents an implementation of the
 * TaxIdentificationNumber interface for an international tax identification number.
 * It also implements the DomainID interface.
 */
public class TaxIdentificationNumberInternationalImplementation implements DomainID,
        TaxIdentificationNumber {

    private final String internationalTaxIdentificationNumberValue;

    /**
     * Constructs a new TaxIdentificationNumberInternationalImplementation object with the provided
     tax identification number value.

     * @param taxIdentificationNumberValue the tax identification number value to be set
     * @throws InvalidConstructorArgumentException if the provided tax identification number is invalid
     */
    public TaxIdentificationNumberInternationalImplementation(String taxIdentificationNumberValue) {
        validateValue(taxIdentificationNumberValue);
        this.internationalTaxIdentificationNumberValue = taxIdentificationNumberValue;
    }

    private void validateValue(String taxIdentificationNumberValue) {
        if (!taxIdentificationNumberValidation(taxIdentificationNumberValue)) {
            throw new InvalidConstructorArgumentException("Invalid International Tax Identification Number");
        }
    }

    /**
     * Validates the tax identification number.
     *
     * @param tinToValidate the tax identification number to validate
     * @return true if the tax identification number is valid, false otherwise
     */
    @Override
    public boolean taxIdentificationNumberValidation(String tinToValidate) {
        return taxIdentificationNumberHasCorrectLength(tinToValidate)
                && taxIdentificationNumberOnlyContainsDigits(tinToValidate);
    }

    private static boolean taxIdentificationNumberHasCorrectLength(String tinToValidate) {
        final int CORRECT_TIN_LENGTH = 9;
        return tinToValidate.length() == CORRECT_TIN_LENGTH;
    }

    private static boolean taxIdentificationNumberOnlyContainsDigits(String tinToValidate) {
        return tinToValidate.matches("[0-9]+");
    }

    /**
     * Gets tax identification number.
     *
     * @return the international tax identification number value
     */
    @Override
    public String getTaxIdentificationNumber() {
        return internationalTaxIdentificationNumberValue;
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
        TaxIdentificationNumberInternationalImplementation that =
                (TaxIdentificationNumberInternationalImplementation) o;
        return Objects.equals(internationalTaxIdentificationNumberValue,
                that.internationalTaxIdentificationNumberValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(internationalTaxIdentificationNumberValue);
    }
}
