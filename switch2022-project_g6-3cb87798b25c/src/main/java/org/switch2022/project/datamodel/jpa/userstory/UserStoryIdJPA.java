package org.switch2022.project.datamodel.jpa.userstory;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserStoryIdJPA implements Serializable {

    private String projectCode;
    private String userStoryCode;

    /**
     * Instantiates a new UserStoryIdJPA
     * @param projectCode    the project code string
     * @param userStoryCode  the user story code string
     */
    public UserStoryIdJPA(String projectCode, String userStoryCode) {
        this.projectCode = projectCode;
        this.userStoryCode = userStoryCode;
    }

    public UserStoryIdJPA() {}

    /**
     * Gets the ProjectCode value
     * @return the project code string
     */
    public String getProjectCodeValue() {
        return projectCode;
    }

    /**
     * Gets the User Story value
     * @return the user story string
     */
    public String getUserStoryCode() {
        return userStoryCode;
    }

    /**
     * Sets the project code
     * @param projectCode the project code string
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Sets the user story code
     * @param userStoryCode the user story code string
     */
    public void setUserStoryCode(String userStoryCode) {
        this.userStoryCode = userStoryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStoryIdJPA that = (UserStoryIdJPA) o;
        return Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(userStoryCode, that.userStoryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, userStoryCode);
    }

}