package org.switch2022.project.utils.domainevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.switch2022.project.domain.valueobject.UserStoryID;

/**
 * The UserStoryEventPublisher class is responsible for publishing user story events.
 */
@Component
public class UserStoryEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructs a UserStoryEventPublisher object with the specified ApplicationEventPublisher.
     *
     * @param applicationEventPublisher the ApplicationEventPublisher instance
     */
    @Autowired
    public UserStoryEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Publishes a FinishUserStoryEvent with the given user story ID.
     *
     * @param userStoryID the user story ID associated with the event
     */
    public void publishFinishUserStoryEvent(UserStoryID userStoryID) {
        FinishUserStoryEvent finishUserStoryEvent = new FinishUserStoryEvent(userStoryID);
        applicationEventPublisher.publishEvent(finishUserStoryEvent);
    }

    /**
     * Publishes an AddUserStoryEvent with the given user story ID.
     *
     * @param userStoryID the user story ID associated with the event
     */
    public void publishAddUserStoryEvent(UserStoryID userStoryID) {
        AddUserStoryEvent addUserStoryEvent = new AddUserStoryEvent(userStoryID);
        applicationEventPublisher.publishEvent(addUserStoryEvent);
    }
}
