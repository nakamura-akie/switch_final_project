package org.switch2022.project.domain.userstory;

import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.UserStoryID;

/**
 * The UserStoryFactory interface defines methods for creating User Story objects.
 */
public interface UserStoryFactory {

    /**
     * Creates a new UserStory instance.
     *
     * @param userStoryID          the user story id
     * @param userStoryDescription the description
     * @return the created UserStory instance
     */
    UserStory createUserStory(UserStoryID userStoryID, Description userStoryDescription);
}
