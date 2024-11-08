package org.switch2022.project.domain.customer;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import java.util.Objects;

/**
 * The Customer class represents a customer in the domain model.
 * It implements the AggregateRoot interface with the identity of the account being the tax identification number.
 */
public class Customer implements AggregateRoot<TaxIdentificationNumber> {
    private final TaxIdentificationNumber customerID;
    private final CustomerName customerName;
    private final Country country;

    /**
     * Instantiates a new Customer.
     *
     * @param customerID   the customer id, in this case the TaxIdentificationNumber
     * @param customerName the customer name
     * @throws IllegalArgumentException when customerID or customer name or country are null
     */
    protected Customer(TaxIdentificationNumber customerID, CustomerName customerName, Country country) {
        if (customerID == null || customerName == null || country == null) {
            throw new IllegalArgumentException("Tax Identification Number, Customer Name and Country cannot be null");
        }
        this.customerID = customerID;
        this.customerName = customerName;
        this.country = country;
    }

    /**
     * Gets the taxIdentificationNumber
     *
     * @return the tax identification number object
     */
    public TaxIdentificationNumber getTaxIdentificationNumber() {
        return this.customerID;
    }

    /**
     * Gets the Customer Name
     *
     * @return the customer name object
     */
    public CustomerName getCustomerName() {
        return this.customerName;
    }

    /**
     * Gets the country
     *
     * @return the country object
     */
    public Country getCountry() {
        return this.country;
    }

    /**
     * Returns the identity of the customer, which is the customerID.
     *
     * @return the customerID
     */
    @Override
    public TaxIdentificationNumber identity() {
        return this.customerID;
    }

    /**
     * Checks if this customer is the same as the given object.
     *
     * @param object the object to compare
     * @return true if the object is the same as this customer, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer that = (Customer) object;

        return (this.customerID.equals(that.customerID)
                && this.customerName.equals(that.customerName) && this.country.equals(that.country));
    }

    /**
     * Checks if this customer is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this customer, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(customerID, customer.customerID);
    }

    /**
     * Generates the hash code value for the customer.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerID);
    }
}
