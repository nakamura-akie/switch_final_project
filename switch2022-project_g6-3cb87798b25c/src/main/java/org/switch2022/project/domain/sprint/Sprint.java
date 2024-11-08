package org.switch2022.project.domain.sprint;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * The Sprint class represents a sprint in the domain model.
 * It implements the AggregateRoot interface with the identity of the sprint being the sprint id.
 */
public class Sprint implements AggregateRoot<SprintID> {

    private final SprintID sprintID;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final SprintDuration sprintDuration;
    private final SprintBacklog sprintBacklog;
    private SprintStatus sprintStatus;

    /**
     * Constructs a Sprint object with the provided details.
     *
     * @param sprintID        the sprint ID
     * @param startDate       the start date of the sprint
     * @param sprintDuration  the duration of the sprint
     * @param endDate         the end date of the sprint
     * @param sprintBacklog   the sprint backlog
     */
    protected Sprint(SprintID sprintID, LocalDate startDate, SprintDuration sprintDuration, LocalDate endDate,
                     SprintBacklog sprintBacklog) {
        if (startDate == null) {
            throw new IllegalArgumentException("Invalid start date");
        }

        this.sprintID = sprintID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sprintDuration = sprintDuration;
        this.sprintBacklog = sprintBacklog;
        this.sprintStatus = SprintStatus.PLANNED;
    }

    /**
     * Constructs a Sprint object with the provided details, including the sprint status.
     *
     * @param sprintID        the sprint ID
     * @param startDate       the start date of the sprint
     * @param sprintDuration  the duration of the sprint
     * @param endDate         the end date of the sprint
     * @param sprintBacklog   the sprint backlog
     * @param sprintStatus    the status of the sprint
     */
    protected Sprint(SprintID sprintID, LocalDate startDate, SprintDuration sprintDuration, LocalDate endDate,
                     SprintBacklog sprintBacklog, SprintStatus sprintStatus) {
        if (startDate == null) {
            throw new IllegalArgumentException("Invalid start date");
        }
        this.sprintID = sprintID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sprintDuration = sprintDuration;
        this.sprintBacklog = sprintBacklog;
        this.sprintStatus = sprintStatus;
    }

    /**
     * Gets the start date of the sprint.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the sprint.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Gets the duration of the sprint.
     *
     * @return the sprint duration
     */
    public SprintDuration getSprintDuration() {
        return sprintDuration;
    }

    /**
     * Gets the project code associated with the sprint.
     *
     * @return the project code
     */
    public ProjectCode getProjectCode() {
        return this.sprintID.getProjectCode();
    }

    /**
     * Gets the sprint code.
     *
     * @return the sprint code
     */
    public SprintCode getSprintCode() {
        return this.sprintID.getSprintCode();
    }

    /**
     * Defines the status of the sprint.
     *
     * @param sprintStatusValue the sprint status
     */
    public void defineSprintStatus(SprintStatus sprintStatusValue) {
        sprintStatus = sprintStatusValue;
    }

    /**
     * Gets the status of the sprint.
     *
     * @return the sprint status
     */
    public SprintStatus getSprintStatus() {
        return sprintStatus;
    }

    /**
     * Checks if the sprint has the specified status.
     *
     * @param status the status to check
     * @return true if the sprint has the specified status, false otherwise
     */
    public boolean hasStatus(SprintStatus status) {
        return this.sprintStatus.equals(status);
    }

    /**
     * Adds User Story to sprint backlog.
     *
     * @param userStoryID the user story id
     * @return true if User Story is successfully added to the Sprint Backlog or false otherwise.
     */
    public boolean addToSprintBacklog(UserStoryID userStoryID) {
        return this.sprintBacklog.addUserStory(userStoryID);
    }

    /**
     * Returns the identity of the Sprint.
     *
     * @return the sprint id
     */
    @Override
    public SprintID identity() {
        return this.sprintID;
    }

    /**
     * Checks if this Sprint is the same as the specified object.
     *
     * @param object the object to compare
     * @return true if the Sprint is the same as the specified object, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof Sprint) {
            Sprint that = (Sprint) object;

            return this.sprintBacklog.equals(that.sprintBacklog)
                    && this.sprintDuration.equals(that.sprintDuration)
                    && this.startDate.equals(that.startDate);
        }

        return false;
    }

    /**
     * Checks if this sprint is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this sprint, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sprint sprint = (Sprint) o;
        return Objects.equals(sprintID, sprint.sprintID);
    }

    /**
     * Generates the hash code value for the sprint.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintID);
    }

    /**
     * Gets the list of user story id in the sprint backlog.
     *
     * @return the list of user story id
     */
    public List<UserStoryID> getIDsOfUserStoriesInSprintBacklog() {
        return sprintBacklog.getUserStoryCodeList();
    }
}
