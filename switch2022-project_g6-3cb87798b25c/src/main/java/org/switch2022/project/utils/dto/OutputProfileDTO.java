package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The OutputProfileDTO class represents the data transfer object for an output profile.
 * It contains the profile name.
 */
public class OutputProfileDTO {
    public String profileName;

    /**
     * Checks if this OutputProfileDTO is equal to another object.
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
        OutputProfileDTO that = (OutputProfileDTO) o;
        return profileName.equals(that.profileName);
    }

    /**
     * Generates a hash code for the OutputProfileDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(profileName);
    }

}
