package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The NewCustomerDTO class represents a data transfer object that holds information about a customer.
 * It includes the tax identification number, customer name and country.
 */
public class NewCustomerDTO {
    public String taxIdentificationNumber;
    public String customerName;
    public String country;

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
        NewCustomerDTO that = (NewCustomerDTO) o;
        return taxIdentificationNumber.equals(that.taxIdentificationNumber)
                && customerName.equals(that.customerName) && country.equals(that.country);
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
