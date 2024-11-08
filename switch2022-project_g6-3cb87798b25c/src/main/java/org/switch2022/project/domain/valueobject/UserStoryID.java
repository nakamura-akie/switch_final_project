package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;

import java.util.Objects;

/**
 * The UserStoryID class represents the user story id of a user story in the domain model.
 * It is a value object that implements the DomainID interface.
 */
public class UserStoryID implements  DomainID {

    private final ProjectCode projectCode;

    private final UserStoryCode userStoryCode;

    /**
     * Instantiates a new UserStoryID.
     *
     * @param projectCode    the project code
     * @param userStoryCode   the user story code
     * @throws IllegalArgumentException when either of the parameters are null
     */
    public UserStoryID(ProjectCode projectCode, UserStoryCode userStoryCode) {
        if (projectCode == null) {
            throw new IllegalArgumentException("Invalid Project Code.");
        }
        if (userStoryCode == null) {
            throw new IllegalArgumentException("The inserted User Story code is invalid");
        }
        this.projectCode = projectCode;
        this.userStoryCode = userStoryCode;
    }

    /**
     * Gets project code value.
     *
     * @return the project code
     */
    public ProjectCode getProjectCode() {
        return projectCode;
    }

    /**
     * Gets user story code.
     *
     * @return the user story code
     */
    public UserStoryCode getUserStoryCode() {
        return userStoryCode;
    }

    /**
     * Checks if this UserStoryID is equal to the given object.
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
        UserStoryID that = (UserStoryID) o;
        return this.projectCode.equals(that.projectCode) && this.userStoryCode.equals(that.userStoryCode);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode, userStoryCode);
    }

}
