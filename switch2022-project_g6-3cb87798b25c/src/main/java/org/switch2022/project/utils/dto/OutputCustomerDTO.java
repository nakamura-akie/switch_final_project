package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The OutputCustomerDTO class represents the data transfer object for an output customer.
 * It contains the tax identification number, customer name, and country of the customer.
 */
public class OutputCustomerDTO {
    public String taxIdentificationNumber;
    public String customerName;
    public String country;

    /**
     * Checks if this OutputCustomerDTO is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        OutputCustomerDTO that = (OutputCustomerDTO) o;
        return taxIdentificationNumber.equals(that.taxIdentificationNumber)
                && customerName.equals(that.customerName) && country.equals(that.country);
    }

    /**
     * Generates a hash code for the OutputCustomerDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(taxIdentificationNumber, customerName, country);
    }
}
