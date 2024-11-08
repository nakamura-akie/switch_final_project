package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.assembler.OutputUserStoryAssembler;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputUserStoryAssemblerTest {

    private UserStory userStoryOne;
    private UserStory userStoryTwo;
    private UserStory userStoryThree;

    private OutputUserStoryDTO DTOOne;
    private OutputUserStoryDTO DTOTwo;
    private OutputUserStoryDTO DTOThree;

    @BeforeEach
    void init() {
        userStoryOne = mock(UserStory.class);
        userStoryTwo = mock(UserStory.class);
        userStoryThree = mock(UserStory.class);

        ProjectCode projectCodeOne = new ProjectCode("P001");
        Effort effort = Effort.ONE;



        when(userStoryOne.getProjectCode()).thenReturn(projectCodeOne);
        when(userStoryOne.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryOne.getDescription()).thenReturn(new Description("One"));
        when(userStoryOne.getUserStoryCode()).thenReturn(new UserStoryCode(
                "US001"));
        when(userStoryOne.getEffort()).thenReturn(effort);

        when(userStoryTwo.getProjectCode()).thenReturn(projectCodeOne);
        when(userStoryTwo.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryTwo.getDescription()).thenReturn(new Description("Two"));
        when(userStoryTwo.getUserStoryCode()).thenReturn(new UserStoryCode(
                "US002"));
        when(userStoryTwo.getEffort()).thenReturn(effort);

        when(userStoryThree.getProjectCode()).thenReturn(projectCodeOne);
        when(userStoryThree.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryThree.getDescription()).thenReturn(new Description(
                "Three"));
        when(userStoryThree.getUserStoryCode()).thenReturn(new UserStoryCode(
                "US003"));
        when(userStoryThree.getEffort()).thenReturn(effort);

        DTOOne = new OutputUserStoryDTO();
        DTOOne.projectCode = "P001";
        DTOOne.status = "OPEN";
        DTOOne.description = "One";
        DTOOne.userStoryCode = "US001";
        DTOOne.userStoryEffort = 1;

        DTOTwo = new OutputUserStoryDTO();
        DTOTwo.projectCode = "P001";
        DTOTwo.status = "OPEN";
        DTOTwo.description = "Two";
        DTOTwo.userStoryCode = "US002";
        DTOTwo.userStoryEffort = 1;

        DTOThree = new OutputUserStoryDTO();
        DTOThree.projectCode = "P001";
        DTOThree.status = "OPEN";
        DTOThree.description = "Three";
        DTOThree.userStoryCode = "US003";
        DTOThree.userStoryEffort = 1;

    }

    @AfterEach
    void tearDown() {
        userStoryOne = null;
        userStoryTwo = null;
        userStoryThree = null;
        DTOOne = null;
        DTOTwo = null;
        DTOThree = null;
    }

    @Test
    void createOutputUserStoryDTO_ReturnEquals() {
        //Arrange

        //Act
        OutputUserStoryDTO result =
                OutputUserStoryAssembler.createOutputUserStoryDTO(userStoryOne);

        //Assert
        assertEquals(DTOOne, result);
    }

}