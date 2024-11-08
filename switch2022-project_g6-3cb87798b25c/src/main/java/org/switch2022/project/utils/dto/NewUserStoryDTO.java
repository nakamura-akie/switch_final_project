package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;


/**
 * The NewUserStoryDTO class represents a data transfer object that holds information about a user story.
 * It includes the project code, the user story code and the description.
 */
public class NewUserStoryDTO {
    public ProjectCode projectCode;
    public UserStoryCode userStoryCode;
    public Description description;
}
