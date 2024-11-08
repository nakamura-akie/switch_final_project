package org.switch2022.project.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

/**
 * The ProjectRole enum represents the project role of a resource in a project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public enum ProjectRole implements ValueObject{
    PRODUCT_OWNER("PRODUCT_OWNER"),
    SCRUM_MASTER("SCRUM_MASTER"),
    TEAM_MEMBER("TEAM_MEMBER"),
    PROJECT_MANAGER("PROJECT_MANAGER");

    private final String role;

    ProjectRole(String role) {
        this.role = role;
    }

    @JsonCreator
    public static ProjectRole fromString(@JsonProperty("projectRole") String role) {
        try {
            return ProjectRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw  new InvalidConstructorArgumentException("Invalid role.");
        }
    }

    /**
     * Checks if the role of a Resource is Product Owner.
     *
     * @return boolean
     */
    public boolean isProductOwner () {
        return this == PRODUCT_OWNER;
    }

    /**
     * Checks if the role of a Resource is Scrum Master.
     *
     * @return boolean
     */
    public boolean isScrumMaster () {
        return this == SCRUM_MASTER;
    }

    /**
     * Checks if the role of a Resource is Team Member.
     *
     * @return boolean
     */
    public boolean isTeamMember () {
        return this == TEAM_MEMBER;
    }
}

