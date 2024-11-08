package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.utils.dto.UserStoryDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserStoryAssemblerTest {
    private static UserStory userStoryDouble;
    private static ProjectCode projectCode;
    private static UserStoryCode userStoryCode;
    private static Description description;
    private static UserStoryStatus status;

    @BeforeAll
    static void setup() {
        userStoryDouble = mock(UserStory.class);

        projectCode = new ProjectCode("P1");
        userStoryCode = new UserStoryCode("US001");
        description = new Description("Description");
        status = UserStoryStatus.OPEN;

        when(userStoryDouble.getProjectCode()).thenReturn(projectCode);
        when(userStoryDouble.getUserStoryCode()).thenReturn(userStoryCode);
        when(userStoryDouble.getDescription()).thenReturn(description);
        when(userStoryDouble.getUserStoryStatus()).thenReturn(status);
    }

    @AfterAll
    static void tearDown() {
        userStoryDouble = null;
        projectCode = null;
        userStoryCode = null;
        description = null;
        status = null;
    }

    @Test
    void createOutputUserStoryDTO_SuccessfulCreation_True() {
        UserStoryDTO expected = new UserStoryDTO();
        expected.projectCode = new ProjectCode("P1");
        expected.userStoryCode = new UserStoryCode("US001");
        expected.description = new Description("Description");
        expected.status = UserStoryStatus.OPEN;

        UserStoryDTO result = UserStoryAssembler.createUserStoryDTO(userStoryDouble);

        assertEquals(expected, result);
    }
}