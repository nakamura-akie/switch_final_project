package org.switch2022.project.utils.domainevent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.service.ProjectService;

import static org.mockito.Mockito.*;

class UserStoryEventHandlerTest {

    private ProjectService projectService;
    private UserStoryEventHandler userStoryEventHandler;

    @BeforeEach
    public void setUp() {
        projectService = mock(ProjectService.class);
        userStoryEventHandler = new UserStoryEventHandler(projectService);
    }

    @AfterEach
    public void tearDown() {
        projectService = null;
    }

    @Test
    public void handleCloseUserStoryEvent_shouldRemoveUserStoryFromProductBacklog() {
        FinishUserStoryEvent event = mock(FinishUserStoryEvent.class);
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        when(event.getUserStoryID()).thenReturn(userStoryIdDouble);

        userStoryEventHandler.handleFinishUserStoryEvent(event);

        verify(projectService).removeUserStoryFromProductBacklog(userStoryIdDouble);
    }

    @Test
    public void handleAddUserStoryEvent_shouldAddUserStoryToProductBacklog() {
        AddUserStoryEvent event = mock(AddUserStoryEvent.class);

        userStoryEventHandler.handleAddUserStoryEvent(event);

        verify(projectService).addUserStoryToProductBacklog(event.getUserStoryID());
    }
}