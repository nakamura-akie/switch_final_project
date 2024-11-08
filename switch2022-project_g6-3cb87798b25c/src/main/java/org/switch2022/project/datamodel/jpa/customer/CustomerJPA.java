package org.switch2022.project.datamodel.jpa.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class CustomerJPA {

    @Id
    private String taxIdentificationNumber;
    private String customerName;

    private String country;

    protected CustomerJPA() {
    }

    /**
     * Constructs a new CustomerJPA object with the specified tax identification number, customer name, and country.
     *
     * @param taxIdentificationNumber the tax identification number of the customer
     * @param customerName            the name of the customer
     * @param country                 the country of the customer
     */
    public CustomerJPA(String taxIdentificationNumber, String customerName, String country) {
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.customerName = customerName;
        this.country = country;
    }

    /**
     * Returns the tax identification number of the customer.
     *
     * @return the tax identification number of the customer
     */
    public String getTaxIdentificationNumber() {
        return this.taxIdentificationNumber;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the name of the customer
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Returns the country of the customer.
     *
     * @return the country of the customer
     */
    public String getCountry() {
        return this.country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerJPA that = (CustomerJPA) o;
        return taxIdentificationNumber.equals(that.taxIdentificationNumber)
                && customerName.equals(that.customerName)
                && country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxIdentificationNumber, customerName, country);
    }
}
