package org.switch2022.project.domain.userstory;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.*;

import java.util.Objects;

/**
 * The UserStory class represents a user story in the domain model.
 * It implements the AggregateRoot interface with the identity of the user story being the user story id.
 */
public class UserStory implements AggregateRoot<UserStoryID> {

    private final UserStoryID userStoryID;
    private final Description description;
    private UserStoryStatus userStoryStatus;
    private Effort effort;

    /**
     * Instantiates a new User story.
     *
     * @param userStoryID the user story ID, which is the project code and user story code
     * @param description the description
     * @throws IllegalArgumentException if UserStoryID or UserStoryDescription are null
     */
    protected UserStory(UserStoryID userStoryID, Description description) {
        if (userStoryID == null) {
            throw new IllegalArgumentException("User Story ID cannot be null.");
        }
        if (description == null) {
            throw new IllegalArgumentException("User Story Description cannot be null.");
        }
        this.userStoryID = userStoryID;
        this.description = description;
        this.userStoryStatus = UserStoryStatus.OPEN;
        this.effort = Effort.ONE;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Gets user story code.
     *
     * @return the user story code
     */
    public UserStoryCode getUserStoryCode() {
        return this.userStoryID.getUserStoryCode();
    }

    /**
     * Gets user project code.
     *
     * @return the project code
     */
    public ProjectCode getProjectCode() {
        return this.userStoryID.getProjectCode();
    }

    /**
     * Sets User Story finished.
     */
    public void setFinished() {
        this.userStoryStatus = UserStoryStatus.FINISHED;
    }

    /**
     * Checks if user story Has status.
     *
     * @param status the status
     * @return true if user story has the status or false otherwise.
     */
    public boolean hasStatus(UserStoryStatus status) {
        return this.userStoryStatus.equals(status);
    }

    /**
     * Defines User Story effort.
     *
     * @param effortValue the effort value
     */
    public void defineEffort(Effort effortValue) {
        this.effort = effortValue;
    }

    /**
     * Gets status
     *
     * @return status
     */
    public UserStoryStatus getUserStoryStatus() {
        return this.userStoryStatus;
    }

    /**
     * Sets status
     *
     * @param status the status to set
     */
    public void changeStatus(UserStoryStatus status) {
        this.userStoryStatus = status;
    }

    /**
     * Gets effort
     *
     * @return effort
     */
    public Effort getEffort() {
        return this.effort;
    }

    /**
     * Returns the identity of the UserStory.
     *
     * @return the UserStoryID representing the identity of the UserStory
     */
    @Override
    public UserStoryID identity() {
        return this.userStoryID;
    }

    /**
     * Checks if this UserStory is the same as the specified object.
     * Two UserStory objects are considered the same if their userStoryID, description, and status are equal.
     *
     * @param object the object to compare
     * @return true if the UserStory is the same as the specified object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof UserStory) {
            UserStory that = (UserStory) object;

            return this.userStoryID.equals(that.userStoryID) && this.description.equals(that.description)
                    && this.userStoryStatus.equals(that.userStoryStatus);
        }
        return false;
    }

    /**
     * Checks if this user story is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this user story, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStory userStory = (UserStory) o;
        return Objects.equals(userStoryID, userStory.userStoryID);
    }

    /**
     * Generates the hash code value for the user story.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(userStoryID);
    }


}
