package org.switch2022.project.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

/**
 * The UserStoryStatus enum represents the status of a user story in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public enum UserStoryStatus implements ValueObject {
    OPEN("OPEN"),
    RUNNING("RUNNING"),
    FINISHED("FINISHED"),
    CANCELLED("CANCELLED");

    private final String newUserStoryStatus;

    /**
     * Constructs a UserStoryStatus enum value with the given status string.
     *
     * @param status the status string associated with the enum value
     */
    UserStoryStatus(String status) {
        this.newUserStoryStatus = status;
    }

    /**
     * Creates a UserStoryStatus enum value from a string representation.
     *
     * @param status the string representation of the user story status
     * @return the corresponding UserStoryStatus enum value
     * @throws InvalidConstructorArgumentException if the provided status is not a valid user story status
     */
    @JsonCreator
    public static UserStoryStatus fromString(@JsonProperty("userStoryStatus") String status) {
        try {
            return UserStoryStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new InvalidConstructorArgumentException("Invalid User Story Status");
        }
    }

    /**
     * Checks if the user story status is finished.
     *
     * @return true if the user story status is finished, false otherwise
     */
    public boolean isFinished() {
        return this == FINISHED;
    }

    /**
     * Checks if the user story status is cancelled.
     *
     * @return true if the user story status is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return this == CANCELLED;
    }
}