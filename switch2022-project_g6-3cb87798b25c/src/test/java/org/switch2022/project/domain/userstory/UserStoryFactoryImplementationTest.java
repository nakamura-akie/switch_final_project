package org.switch2022.project.domain.userstory;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.UserStoryID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserStoryFactoryImplementationTest {

    @Test
    void createUserStory_CreatesUserStory_Success() {
        UserStoryFactoryImplementation factoryImplementation = new UserStoryFactoryImplementation();

        UserStoryID userStoryIDDouble = mock(UserStoryID.class);
        Description descriptionDouble = mock(Description.class);

        UserStory result = factoryImplementation.createUserStory(userStoryIDDouble, descriptionDouble);

        assertEquals(userStoryIDDouble, result.identity());
        assertEquals(descriptionDouble, result.getDescription());

    }
}