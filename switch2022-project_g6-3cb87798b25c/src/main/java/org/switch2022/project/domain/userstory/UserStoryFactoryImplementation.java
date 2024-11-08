package org.switch2022.project.domain.userstory;

import org.springframework.stereotype.Service;
import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.UserStoryID;

/**
 * The UserStoryFactoryImplementation class is an implementation of the UserStoryFactory interface.
 */
@Service
public class UserStoryFactoryImplementation implements UserStoryFactory {

    /**
     * Creates a new UserStory instance.
     *
     * @param userStoryID          the user story id
     * @param userStoryDescription the description
     * @return the created UserStory instance
     */
    @Override
    public UserStory createUserStory(UserStoryID userStoryID, Description userStoryDescription) {
        return new UserStory(userStoryID, userStoryDescription);
    }
}
