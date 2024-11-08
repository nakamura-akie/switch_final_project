package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;

import java.util.Objects;

/**
 * The ProfileName class represents the profile name of a profile in the domain model.
 * It is a value object that implements the DomainID interface.
 */
public class ProfileName implements DomainID {

    private final String profileNameValue;

    /**
     * Instantiates a profile name.
     *
     * @param profileName the profile name
     * @throws IllegalArgumentException when profile name is null or blank
     */
    public ProfileName(String profileName) {
        if (profileName == null) {
            throw new IllegalArgumentException("Profile name cannot be null");
        }
        if (profileName.isBlank()) {
            throw new IllegalArgumentException("Profile name cannot be blank");
        }
        this.profileNameValue = profileName.toLowerCase();
    }

    /**
     * Gets the profile name value.
     *
     * @return the profile name value
     */
    public String getProfileNameValue() {
        return profileNameValue;
    }

    /**
     * Checks if this ProfileName is equal to the given object.
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
        ProfileName that = (ProfileName) o;
        return profileNameValue.equals(that.profileNameValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(profileNameValue);
    }

}
