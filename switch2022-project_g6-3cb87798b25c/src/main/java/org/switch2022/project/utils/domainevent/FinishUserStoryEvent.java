package org.switch2022.project.utils.domainevent;

import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.Objects;

/**
 * The FinishUserStoryEvent class represents an event that is triggered when a user story is finished.
 */
public class FinishUserStoryEvent {

    private final UserStoryID userStoryID;

    /**
     * Constructs a FinishUserStoryEvent object with the specified user story ID.
     *
     * @param userStoryID the user story ID associated with the event
     */
    public FinishUserStoryEvent(UserStoryID userStoryID) {
        this.userStoryID = userStoryID;
    }

    /**
     * Checks if this FinishUserStoryEvent object is equal to another object.
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
        FinishUserStoryEvent that = (FinishUserStoryEvent) o;
        return Objects.equals(userStoryID, that.userStoryID);
    }

    /**
     * Generates a hash code for this FinishUserStoryEvent object.
     * The hash code is based on the user story ID.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userStoryID);
    }

    /**
     * Retrieves the user story ID associated with the event.
     *
     * @return the user story ID
     */
    public UserStoryID getUserStoryID() {
        return userStoryID;
    }

}
