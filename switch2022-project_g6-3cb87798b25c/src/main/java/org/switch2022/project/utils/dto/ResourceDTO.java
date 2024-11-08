package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The ResourceDTO class represents the data transfer object (DTO) with the resource information.
 * It contains the email, project code, start date, end date and project role.
 */
public class ResourceDTO {
    public String email;
    public String projectCode;
    public String startDate;
    public String endDate;
    public String projectRole;

    /**
     * Checks whether this ResourceDTO is equal to another object.
     *
     * @param o the object to compare with
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
        ResourceDTO that = (ResourceDTO) o;
        return this.email.equals(that.email)
                && this.projectCode.equals(that.projectCode)
                && this.startDate.equals(that.startDate)
                && this.endDate.equals(that.endDate)
                && this.projectRole.equals(that.projectRole);
    }

    /**
     * Generates a hash code for this ResourceDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, projectCode, startDate, endDate, projectRole);
    }
}
