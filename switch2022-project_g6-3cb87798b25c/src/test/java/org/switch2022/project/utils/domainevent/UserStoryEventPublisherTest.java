package org.switch2022.project.utils.domainevent;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.switch2022.project.domain.valueobject.UserStoryID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserStoryEventPublisherTest {
    private static UserStoryEventPublisher userStoryEventPublisher;
    private static ApplicationEventPublisher applicationEventPublisher;

    @BeforeAll
    static void init() {
        applicationEventPublisher = mock(ApplicationEventPublisher.class);
        userStoryEventPublisher = new UserStoryEventPublisher(applicationEventPublisher);
    }

    @AfterAll
    static void tearDown() {
        userStoryEventPublisher = null;
        applicationEventPublisher = null;
    }

    @Test
    void publishFinishUserStoryEvent() {
        UserStoryID userStoryID = mock(UserStoryID.class);

        userStoryEventPublisher.publishFinishUserStoryEvent(userStoryID);

        verify(applicationEventPublisher).publishEvent(new FinishUserStoryEvent(userStoryID));
    }

    @Test
    void publishAddUserStoryEvent() {
        UserStoryID userStoryID = mock(UserStoryID.class);

        userStoryEventPublisher.publishAddUserStoryEvent(userStoryID);

        verify(applicationEventPublisher).publishEvent(new AddUserStoryEvent(userStoryID));
    }
}