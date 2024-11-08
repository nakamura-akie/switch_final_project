package org.switch2022.project.datamodel.jpa.userstory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userstories")
public class UserStoryJPA {

    @EmbeddedId
    private UserStoryIdJPA userStoryIdJpa;
    private String description;
    private String userStoryStatus;

    protected UserStoryJPA() {}

    /**
     * Instantiates a new UserStoryJPA
     * @param userStoryIdJPA the user story ID JPA object
     * @param description     the description string
     * @param userStoryStatus          the status string
     */
    public UserStoryJPA(UserStoryIdJPA userStoryIdJPA, String description, String userStoryStatus) {
        this.userStoryIdJpa = userStoryIdJPA;
        this.description = description;
        this.userStoryStatus = userStoryStatus;
    }

    /**
     * Gets the user story ID JPA
     * @return the UserStoryIdJPA object
     */
    public UserStoryIdJPA getuserStoryIdJpa() {
        return userStoryIdJpa;
    }

    /**
     * Gets the description
     * @return the description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the status
     * @return the status string
     */
    public String getUserStoryStatus() {
        return userStoryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStoryJPA that = (UserStoryJPA) o;
        return Objects.equals(userStoryIdJpa, that.userStoryIdJpa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userStoryIdJpa);
    }


}
