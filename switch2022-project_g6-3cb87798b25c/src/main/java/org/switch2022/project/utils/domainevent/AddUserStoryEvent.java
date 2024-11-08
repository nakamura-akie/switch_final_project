package org.switch2022.project.utils.domainevent;

import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.Objects;
/**
 * The AddUserStoryEvent class represents an event that is triggered when a user story is added.
 */
public class AddUserStoryEvent {

    private final UserStoryID userStoryID;

    /**
     * Constructs an AddUserStoryEvent object with the specified user story ID.
     *
     * @param userStoryID the user story ID associated with the event
     */
    public AddUserStoryEvent(UserStoryID userStoryID) {
        this.userStoryID = userStoryID;
    }

    /**
     * Retrieves the user story ID associated with the event.
     *
     * @return the user story ID
     */
    public UserStoryID getUserStoryID() {
        return userStoryID;
    }

    /**
     * Checks if this AddUserStoryEvent object is equal to another object.
     * Two events are considered equal if they have the same user story ID.
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
        AddUserStoryEvent that = (AddUserStoryEvent) o;
        return Objects.equals(userStoryID, that.userStoryID);
    }

    /**
     * Generates a hash code for this AddUserStoryEvent object.
     * The hash code is based on the user story ID.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userStoryID);
    }

}
