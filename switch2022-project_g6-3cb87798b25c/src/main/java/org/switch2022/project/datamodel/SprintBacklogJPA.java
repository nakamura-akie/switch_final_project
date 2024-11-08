package org.switch2022.project.datamodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sprintBacklog")
public class SprintBacklogJPA {

    @EmbeddedId
    private SprintIdJPA sprintIdJPA;
    private String userStoryCodeList;

    protected SprintBacklogJPA() {}

    /**
     * Constructs a new SprintBacklogJPA object with the specified SprintIdJPA and user story code list.
     *
     * @param sprintIdJPA        the SprintIdJPA associated with the SprintBacklogJPA
     * @param userStoryCodeList  the user story code list
     */
    public SprintBacklogJPA(SprintIdJPA sprintIdJPA, String userStoryCodeList) {
        this.sprintIdJPA = sprintIdJPA;
        this.userStoryCodeList = userStoryCodeList;
    }

    /**
     * Returns the SprintIdJPA associated with the SprintBacklogJPA.
     *
     * @return the SprintIdJPA associated with the SprintBacklogJPA
     */
    public SprintIdJPA getSprintIdJPA() {
        return sprintIdJPA;
    }

    /**
     * Returns the user story code list of the SprintBacklogJPA.
     *
     * @return the user story code list of the SprintBacklogJPA
     */
    public String getUserStoryCodeList() {
        return userStoryCodeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SprintBacklogJPA that = (SprintBacklogJPA) o;
        return this.sprintIdJPA.equals(that.sprintIdJPA) &&
                this.userStoryCodeList.equals(that.userStoryCodeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSprintIdJPA(), userStoryCodeList);
    }
}