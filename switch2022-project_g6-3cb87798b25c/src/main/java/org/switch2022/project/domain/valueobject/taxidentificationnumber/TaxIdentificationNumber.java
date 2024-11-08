package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.switch2022.project.ddd.DomainID;

/**
 * The TaxIdentificationNumber interface represents the id of a customer in the domain model.
 * It is a value object that implements the DomainID interface.
 */
public interface TaxIdentificationNumber extends DomainID {

    /**
     * Validates the tax identification number.
     *
     * @param taxIdentificationNumberValue the value to be validated
     * @return true if the tax identification number is valid, false otherwise
     */
    boolean taxIdentificationNumberValidation(String taxIdentificationNumberValue);

    /**
     * Retrieves the tax identification number.
     * @return the tax identification number
     */
    String getTaxIdentificationNumber();
}
