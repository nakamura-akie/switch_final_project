package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import java.util.Objects;

/**
 * The CustomerValueObjectDTO class represents a data transfer object that holds information about a customer.
 * It includes the tax identification number, customer name and country.
 */
public class CustomerValueObjectDTO {
    public TaxIdentificationNumber taxIdentificationNumber;
    public CustomerName customerName;
    public Country country;

    /**
     * Checks whether the current object is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        CustomerValueObjectDTO that = (CustomerValueObjectDTO) o;
        return taxIdentificationNumber.equals(that.taxIdentificationNumber)
                && customerName.equals(that.customerName)
                && country.equals(that.country);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(taxIdentificationNumber, customerName, country);
    }
}
