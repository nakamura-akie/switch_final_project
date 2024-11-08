package org.switch2022.project.domain.sprint;

import org.switch2022.project.ddd.DomainEntity;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a sprint backlog, which is a collection of user stories associated with a specific sprint.
 * The sprint backlog tracks the user stories that are planned to be completed during the sprint.
 */
public class SprintBacklog implements DomainEntity<SprintID> {

    private final SprintID sprintID;
    private final List<UserStoryID> userStoryCodeList;


    /**
     * Constructs a new sprint backlog with the given sprint ID.
     *
     * @param sprintID the ID of the sprint associated with the backlog
     * @throws IllegalArgumentException if the sprint ID is null
     */
    protected SprintBacklog(SprintID sprintID) {
        if (sprintID == null) {
            throw new IllegalArgumentException("Sprint ID cannot be null.");
        }

        this.sprintID = sprintID;
        userStoryCodeList = new LinkedList<>();
    }

    /**
     * Constructs a SprintBacklog object with the provided sprint ID and user story code list.
     *
     * @param sprintID         the sprint ID
     * @param userStoryCodeList the list of user story IDs
     */
    protected SprintBacklog(SprintID sprintID, List<UserStoryID> userStoryCodeList) {
        if (sprintID == null) {
            throw new IllegalArgumentException("Sprint ID cannot be null.");
        }

        this.sprintID = sprintID;
        this.userStoryCodeList = new LinkedList<>(userStoryCodeList);
    }

    /**
     * Adds a user story to the sprint backlog.
     *
     * @param userStoryID the ID of the user story to add
     * @return true if the user story was successfully added, false if it was already present
     */
    public boolean addUserStory(UserStoryID userStoryID) {
        if (userStoryCodeList.contains(userStoryID)) {
            throw new IllegalArgumentException("The selected User Story already exists in the Sprint Backlog");
        }
        return userStoryCodeList.add(userStoryID);
    }

    /**
     * Retrieves an unmodifiable view of the user story code list.
     *
     * @return a list of user story IDs in the sprint backlog
     */
    public List<UserStoryID> getUserStoryCodeList() {
        return List.copyOf(this.userStoryCodeList);
    }

    /**
     * Returns the identity of the sprint backlog, which is the sprint id.
     *
     * @return the sprint id
     */
    @Override
    public SprintID identity() {
        return sprintID;
    }

    /**
     * Checks if this Sprint Backlog is the same as the specified object.
     *
     * @param object the object to compare
     * @return true if the SprintBacklog is the same as the specified object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (!(object instanceof SprintBacklog)) {
            return false;
        }

        SprintBacklog that = (SprintBacklog) object;

        return this.userStoryCodeList.equals(that.userStoryCodeList);

    }

    /**
     * Checks if this sprint backlog is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this sprint backlog, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SprintBacklog that = (SprintBacklog) o;
        return Objects.equals(sprintID, that.sprintID);
    }

    /**
     * Generates the hash code value for the sprint backlog.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintID);
    }
}