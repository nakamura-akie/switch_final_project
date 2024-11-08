package org.switch2022.project.datamodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sprints")
public class SprintJPA {

    @EmbeddedId
    private SprintIdJPA sprintIdJPA;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "projectCode", referencedColumnName = "projectCode"),
            @JoinColumn(name = "sprintCode", referencedColumnName = "sprintCode")
    })
    private SprintBacklogJPA sprintBacklogJPA;
    private String startDate;
    private Integer sprintDuration;
    private String endDate;
    private String sprintStatus;

    /**
     * Constructs a new SprintJPA object.
     *
     * @param sprintIdJPA      the embedded ID for the sprint.
     * @param startDate        the start date of the sprint.
     * @param sprintDuration   the duration of the sprint.
     * @param endDate          the end date of the sprint.
     * @param sprintBacklogJPA the sprint backlog associated with the sprint.
     */
    public SprintJPA(SprintIdJPA sprintIdJPA, String startDate,
                     Integer sprintDuration,
                     String endDate, SprintBacklogJPA sprintBacklogJPA) {
        this.sprintIdJPA = sprintIdJPA;
        this.startDate = startDate;
        this.sprintDuration = sprintDuration;
        this.endDate = endDate;
        this.sprintBacklogJPA = sprintBacklogJPA;
    }

    /**
     * Constructs a new SprintJPA object, with associated status.
     *
     * @param sprintIdJPA      the embedded ID for the sprint.
     * @param startDate        the start date of the sprint.
     * @param sprintDuration   the duration of the sprint.
     * @param endDate          the end date of the sprint.
     * @param sprintBacklogJPA the sprint backlog associated with the sprint.
     * @param sprintStatus     the status of the sprint.
     */
    public SprintJPA(SprintIdJPA sprintIdJPA, String startDate,
                     Integer sprintDuration,
                     String endDate, SprintBacklogJPA sprintBacklogJPA,
                     String sprintStatus) {
        this.sprintIdJPA = sprintIdJPA;
        this.startDate = startDate;
        this.sprintDuration = sprintDuration;
        this.endDate = endDate;
        this.sprintBacklogJPA = sprintBacklogJPA;
        this.sprintStatus = sprintStatus;
    }

    protected SprintJPA() {
    }

    /**
     * Returns the embedded ID for the sprint.
     *
     * @return the SprintIdJPA object representing the sprint ID.
     */
    public SprintIdJPA getSprintIdJPA() {
        return sprintIdJPA;
    }

    /**
     * Returns the sprint backlog associated with the sprint.
     *
     * @return SprintBacklogJPA.
     */
    public SprintBacklogJPA getSprintBacklogJPA() {
        return sprintBacklogJPA;
    }

    /**
     * Returns the start date of the sprint.
     *
     * @return the start date of the sprint.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Returns the status of the sprint.
     *
     * @return the status of the sprint
     */
    public String getSprintStatus() {
        return sprintStatus;
    }

    /**
     * Returns the end date of the sprint.
     *
     * @return the end date of the sprint.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Returns the duration of the sprint.
     *
     * @return the sprint duration.
     */
    public Integer getSprintDuration() {
        return sprintDuration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SprintJPA that = (SprintJPA) o;
        return Objects.equals(sprintIdJPA, that.sprintIdJPA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sprintIdJPA);
    }
}