package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.ProjectRepository;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.utils.assembler.OutputUserStoryAssembler;
import org.switch2022.project.utils.assembler.UserStoryAssembler;
import org.switch2022.project.utils.domainevent.UserStoryEventPublisher;
import org.switch2022.project.utils.dto.NewUserStoryDTO;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserStoryServiceTest {

    @Autowired
    private UserStoryService userStoryService;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private UserStoryFactory userStoryFactory;
    @MockBean
    private UserStoryRepository userStoryRepository;
    @MockBean
    private UserStoryEventPublisher userStoryEventPublisher;
    private MockedStatic<UserStoryAssembler> userStoryAssembler;

    private MockedStatic<OutputUserStoryAssembler> outputAssembler;
    private NewUserStoryDTO newUserStoryDTO;
    private UserStoryCode userStoryCodeOne;
    private ProjectCode projectCodeOne;
    private UserStoryID userStoryIDOne;
    private UserStory userStoryOne;
    private Description descriptionOne;
    private Project projectOne;
    private UserStoryDTO savedUserStory;


    @BeforeEach
    void init() {
        projectCodeOne = new ProjectCode("P1");
        userStoryCodeOne = new UserStoryCode("US001");
        userStoryIDOne = new UserStoryID(projectCodeOne, userStoryCodeOne);
        descriptionOne = new Description("Some description");
        Effort effort = Effort.ONE;

        projectOne = mock(Project.class);
        userStoryOne = mock(UserStory.class);
        this.userStoryAssembler = mockStatic(UserStoryAssembler.class);
        this.outputAssembler = mockStatic(OutputUserStoryAssembler.class);

        newUserStoryDTO = new NewUserStoryDTO();
        newUserStoryDTO.projectCode = new ProjectCode("P1");
        newUserStoryDTO.userStoryCode = new UserStoryCode("US001");
        newUserStoryDTO.description = new Description("Some description");


        savedUserStory = new UserStoryDTO();
        savedUserStory.projectCode = new ProjectCode("P1");
        savedUserStory.userStoryCode = new UserStoryCode("US001");
        savedUserStory.description = new Description("Some description");
        savedUserStory.status = UserStoryStatus.OPEN;
    }

    @AfterEach
    void tearDown() {
        this.userStoryAssembler.close();
        this.outputAssembler.close();
    }

    @Test
    void createUserStory_CreateAlreadyExistentUserStory_False() {
        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        when(userStoryRepository.existsById(userStoryIDOne)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userStoryService.createUserStory(newUserStoryDTO));

        assertEquals("The User Story already exists",
                exception.getMessage());
    }

    @Test
    void createUserStory_CreateUserStoryToNonExistentProject_False() {
        when(projectRepository.existsById(projectCodeOne)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userStoryService.createUserStory(newUserStoryDTO));

        assertEquals("Project does not exist.",
                exception.getMessage());
    }

    @Test
    void createUserStory_CreateUserStoryToExistentProductBacklog_True() {
        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        when(projectRepository.findById(projectCodeOne)).thenReturn(Optional.of(projectOne));
        when(userStoryRepository.existsById(userStoryIDOne)).thenReturn(false);
        when(userStoryFactory.createUserStory(userStoryIDOne, descriptionOne)).thenReturn(userStoryOne);
        userStoryAssembler.when(() -> UserStoryAssembler.createUserStoryDTO(userStoryOne)).thenReturn(savedUserStory);
        when(userStoryRepository.save(userStoryOne)).thenReturn(userStoryOne);
        when(projectRepository.findById(projectCodeOne)).thenReturn(Optional.ofNullable(projectOne));
        when(projectOne.addUserStoryToProductBacklog(userStoryIDOne)).thenReturn(true);
        when(projectRepository.save(projectOne)).thenReturn(projectOne);

        UserStoryDTO result = userStoryService.createUserStory(newUserStoryDTO);

        assertEquals(savedUserStory, result);

        verify(userStoryEventPublisher).publishAddUserStoryEvent(userStoryIDOne);
    }

    @Test
    void createUserStory_CreateUserStoryToEmptyOptionalProject_False() {
        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        when(projectRepository.findById(projectCodeOne)).thenReturn(Optional.of(projectOne));
        when(userStoryRepository.existsById(userStoryIDOne)).thenReturn(false);
        when(userStoryFactory.createUserStory(userStoryIDOne, descriptionOne)).thenReturn(userStoryOne);
        userStoryAssembler.when(() -> UserStoryAssembler.createUserStoryDTO(userStoryOne)).thenReturn(savedUserStory);
        when(userStoryRepository.save(userStoryOne)).thenReturn(userStoryOne);
        when(projectRepository.findById(projectCodeOne)).thenReturn(Optional.empty());

        UserStoryDTO result = userStoryService.createUserStory(newUserStoryDTO);

        assertEquals(savedUserStory, result);
    }

    @Test
    void userStoryService_NullUserStoryRepository_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserStoryService(null, projectRepository, userStoryFactory, userStoryEventPublisher));
        assertEquals("User Story Repository cannot be null.",
                exception.getMessage());
    }

    @Test
    void userStoryService_NullProjectRepository_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserStoryService(userStoryRepository, null, userStoryFactory, userStoryEventPublisher));
        assertEquals("Project Repository cannot be null.",
                exception.getMessage());
    }

    @Test
    void userStoryService_NullUserStoryFactory_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserStoryService(userStoryRepository, projectRepository, null, userStoryEventPublisher));
        assertEquals("User Story Factory cannot be null.",
                exception.getMessage());
    }

    @Test
    void userStoryService_NullUserStoryEventPublisher_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserStoryService(userStoryRepository, projectRepository, userStoryFactory, null));
        assertEquals("User Story Event Publisher cannot be null.",
                exception.getMessage());
    }

    @Test
    void changeUserStoryStatus_HappyPath() {

        OutputUserStoryDTO userStoryDTO = new OutputUserStoryDTO();
        userStoryDTO.userStoryCode = userStoryCodeOne.getUserStoryCodeValue();
        userStoryDTO.status = UserStoryStatus.FINISHED.toString();
        userStoryDTO.description = descriptionOne.getDescriptionValue();
        userStoryDTO.projectCode = projectCodeOne.getProjectCodeValue();
        userStoryDTO.userStoryEffort = 1;

        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        when(userStoryRepository.findById(userStoryIDOne)).thenReturn(Optional.of(userStoryOne));
        when(userStoryRepository.patch(userStoryOne)).thenReturn(userStoryOne);
        userStoryAssembler.when(() -> OutputUserStoryAssembler.createOutputUserStoryDTO(userStoryOne)).thenReturn(userStoryDTO);


        OutputUserStoryDTO expected = userStoryDTO;
        OutputUserStoryDTO result =
                userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                        userStoryCodeOne, projectCodeOne);

        verify(userStoryEventPublisher,times(1)).publishFinishUserStoryEvent(any());

        assertEquals(expected, result);
        verify(userStoryOne, times(1)).changeStatus(any());
    }

    @Test
    void changeUserStoryStatus_ChangeToCancelled_OutputUserStoryDTO() {

        OutputUserStoryDTO userStoryDTO = new OutputUserStoryDTO();
        userStoryDTO.userStoryCode = userStoryCodeOne.getUserStoryCodeValue();
        userStoryDTO.status = UserStoryStatus.CANCELLED.toString();
        userStoryDTO.description = descriptionOne.getDescriptionValue();
        userStoryDTO.projectCode = projectCodeOne.getProjectCodeValue();
        userStoryDTO.userStoryEffort = 1;

        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        when(userStoryRepository.findById(userStoryIDOne)).thenReturn(Optional.of(userStoryOne));
        userStoryAssembler.when(() -> OutputUserStoryAssembler.createOutputUserStoryDTO(userStoryOne)).thenReturn(userStoryDTO);

        OutputUserStoryDTO expected = userStoryDTO;

        OutputUserStoryDTO result =
                userStoryService.changeUserStoryStatus(UserStoryStatus.CANCELLED,
                        userStoryCodeOne, projectCodeOne);

        verify(userStoryEventPublisher,times(1)).publishFinishUserStoryEvent(any());

        assertEquals(expected, result);
        verify(userStoryOne, times(1)).changeStatus(any());
    }

    @Test
    void changeUserStoryStatus_ChangeToCurrentStatus() {

        OutputUserStoryDTO userStoryDTO = new OutputUserStoryDTO();
        userStoryDTO.userStoryCode = userStoryCodeOne.getUserStoryCodeValue();
        userStoryDTO.status = UserStoryStatus.OPEN.toString();
        userStoryDTO.description = descriptionOne.getDescriptionValue();
        userStoryDTO.projectCode = projectCodeOne.getProjectCodeValue();
        userStoryDTO.userStoryEffort = Effort.ONE.getValue();

        when(userStoryRepository.findById(userStoryIDOne)).thenReturn(Optional.of(userStoryOne));
        when(userStoryRepository.patch(userStoryOne)).thenReturn(userStoryOne);
        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        outputAssembler.when(() -> OutputUserStoryAssembler.createOutputUserStoryDTO(userStoryOne)).thenReturn(userStoryDTO);


        OutputUserStoryDTO expected = userStoryDTO;
        OutputUserStoryDTO result =
                userStoryService.changeUserStoryStatus(UserStoryStatus.OPEN,
                        userStoryCodeOne, projectCodeOne);

        assertEquals(expected, result);
    }

    @Test
    void changeUserStoryStatus_ProjectDoesNotExist() {
        when(projectRepository.existsById(projectCodeOne)).thenReturn(false);

        String expected = "Project does not exist.";
        String result = assertThrows(NoSuchElementException.class, () ->
                userStoryService.changeUserStoryStatus(UserStoryStatus.OPEN,
                        userStoryCodeOne, projectCodeOne)).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void changeUserStoryStatus_UserStoryDoesNotExist() {

        when(projectRepository.existsById(projectCodeOne)).thenReturn(true);
        when(userStoryRepository.findById(userStoryIDOne)).thenReturn(Optional.empty());

        String expected = "User Story does not exist.";
        String result = assertThrows(NoSuchElementException.class, () ->
                userStoryService.changeUserStoryStatus(UserStoryStatus.OPEN,
                        userStoryCodeOne, projectCodeOne)).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void findUserStoryByID() {


        OutputUserStoryDTO userStoryDTO = new OutputUserStoryDTO();
        userStoryDTO.userStoryCode = userStoryCodeOne.getUserStoryCodeValue();
        userStoryDTO.status = UserStoryStatus.OPEN.toString();
        userStoryDTO.description = descriptionOne.getDescriptionValue();
        userStoryDTO.projectCode = projectCodeOne.getProjectCodeValue();
        userStoryDTO.userStoryEffort = Effort.ONE.getValue();

        when(userStoryRepository.findById(userStoryIDOne)).thenReturn(Optional.of(userStoryOne));
        outputAssembler.when(() -> OutputUserStoryAssembler.createOutputUserStoryDTO(userStoryOne)).thenReturn(userStoryDTO);

        OutputUserStoryDTO expected = userStoryDTO;
        OutputUserStoryDTO result = userStoryService.findUserStoryByID(userStoryIDOne);

        assertEquals(expected, result);

    }

    @Test
    void findUserStoryByID_UserStoryDoesntExist() {


        OutputUserStoryDTO userStoryDTO = new OutputUserStoryDTO();
        userStoryDTO.userStoryCode = userStoryCodeOne.getUserStoryCodeValue();
        userStoryDTO.status = UserStoryStatus.OPEN.toString();
        userStoryDTO.description = descriptionOne.getDescriptionValue();
        userStoryDTO.projectCode = projectCodeOne.getProjectCodeValue();
        userStoryDTO.userStoryEffort = Effort.ONE.getValue();

        when(userStoryRepository.findById(userStoryIDOne)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userStoryService.findUserStoryByID(userStoryIDOne));

        assertEquals("User Story doesn't exist", exception.getMessage());
    }
}
