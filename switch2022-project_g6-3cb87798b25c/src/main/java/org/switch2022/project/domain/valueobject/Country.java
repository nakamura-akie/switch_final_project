package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

import java.util.Objects;

/**
 * The Country class represents the country of a customer in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class Country implements ValueObject {

    private final String countryName;

    /**
     * Instantiates a new Country.
     *
     * @param countryName the country value
     * @throws IllegalArgumentException when country is null or blank
     */
    public Country(String countryName) {
        if (countryName == null || countryName.isBlank()) {
            throw new IllegalArgumentException("Invalid country.");
        }

        this.countryName = countryName;
    }


    /**
     * Gets the Country name.
     *
     * @return the country name value
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Checks if this Country is equal to the given object.
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
        Country that = (Country) o;
        return Objects.equals(countryName, that.countryName);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(countryName);
    }
}
