package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

/**
 * The ProjectStatus class represents the user story code of a user story in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class UserStoryCode implements ValueObject {
    private final String userStoryCodeValue;

    /**
     * Instantiates a new UserStoryCode
     * @param userStoryCode the value for the new user story code
     * @throws IllegalArgumentException when the user story code is null or blank
     */
    public UserStoryCode(String userStoryCode) {
        if (userStoryCode == null) {
            throw new NullConstructorArgumentException("The inserted User Story code is invalid");
        }
        if (userStoryCode.isBlank()) {
            throw new EmptyConstructorArgumentException("The inserted User Story code is invalid");
        }
        this.userStoryCodeValue = userStoryCode;
    }

    /**
     * Gets User Story Code value
     * @return the user story code value
     */
    public String getUserStoryCodeValue() {
        return userStoryCodeValue;
    }

    @Override
    public String toString() {

        return userStoryCodeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStoryCode that = (UserStoryCode) o;
        return this.userStoryCodeValue.equals(that.userStoryCodeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userStoryCodeValue);
    }
}

