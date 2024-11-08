package org.switch2022.project.utils.domainevent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.service.ProjectService;

/**
 * The UserStoryEventHandler class is responsible for handling user story events.
 */
@Component
public class UserStoryEventHandler {

    private final ProjectService projectService;

    /**
     * Constructs a UserStoryEventHandler object with the specified ProjectService.
     *
     * @param projectService the ProjectService instance
     */
    public UserStoryEventHandler(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Handles the FinishUserStoryEvent by removing the corresponding user story from the product backlog.
     *
     * @param event the FinishUserStoryEvent to handle
     */
    @EventListener
    public void handleFinishUserStoryEvent(FinishUserStoryEvent event) {
        UserStoryID userStoryID = event.getUserStoryID();
        projectService.removeUserStoryFromProductBacklog(userStoryID);
    }

    /**
     * Handles the AddUserStoryEvent by adding the corresponding user story to the product backlog.
     *
     * @param event the AddUserStoryEvent to handle
     */
    @EventListener
    public void handleAddUserStoryEvent(AddUserStoryEvent event) {
        UserStoryID userStoryID = event.getUserStoryID();
        projectService.addUserStoryToProductBacklog(userStoryID);
    }
}
