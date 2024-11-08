package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintBacklog;
import org.switch2022.project.domain.sprint.SprintFactory;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.SprintRepository;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.utils.assembler.OutputSprintAssembler;
import org.switch2022.project.utils.assembler.UserStoryAssembler;
import org.switch2022.project.utils.dto.*;
import org.switch2022.project.utils.assembler.OpenSprintOutputAssembler;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
class SprintServiceTest {

    @Autowired
    private SprintService sprintService;
    @MockBean
    private SprintRepository sprintRepository;
    @MockBean
    private Repository<Project, ProjectCode> projectRepository;
    @MockBean
    private UserStoryRepository userStoryRepository;
    @MockBean
    private SprintFactory sprintFactory;
    private ProjectCode projectCode;
    private SprintCode sprintCode;

    @BeforeEach
    void init() {
        projectCode = new ProjectCode("P001");
        sprintCode = new SprintCode("SPT001");
    }


    @AfterEach
    void tearDown() {
        sprintService = null;
        sprintRepository.deleteAll();
        projectRepository = null;
        userStoryRepository = null;
        sprintFactory = null;
        projectCode = null;
        sprintCode = null;
    }

    @Test
    void addUserStoryToSprintBacklog_SprintIsNotOpened_False() {
        NewSprintBacklogUserStoryDTO newUserStoryDTO = new NewSprintBacklogUserStoryDTO();
        newUserStoryDTO.userStoryCode = new UserStoryCode("US001");

        UserStoryID userStoryID = new UserStoryID(
                projectCode,
                newUserStoryDTO.userStoryCode);
        SprintID sprintID = new SprintID(
                projectCode,
                sprintCode);

        when(projectRepository.existsById(projectCode))
                .thenReturn(true);
        when(userStoryRepository.existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED))
                .thenReturn(true);
        when(sprintRepository.findBySprintIdAndSprintStatusLike(sprintID, SprintStatus.OPENED))
                .thenReturn(Optional.empty());

        Exception e = assertThrows(NoSuchElementException.class, () -> sprintService.addUserStoryToSprintBacklog
                (projectCode, sprintCode, newUserStoryDTO));

        assertEquals("The selected Sprint is currently closed", e.getMessage());
    }

    @Test
    void addUserStoryToSprintBacklog_EverythingGoesWell_True() {
        MockedStatic<OutputSprintAssembler> outputAssembler = mockStatic(OutputSprintAssembler.class);

        NewSprintBacklogUserStoryDTO newUserStoryDTO = new NewSprintBacklogUserStoryDTO();
        newUserStoryDTO.userStoryCode = new UserStoryCode("US001");

        UserStoryID userStoryID = new UserStoryID(
                projectCode,
                newUserStoryDTO.userStoryCode);
        SprintID sprintID = new SprintID(
                projectCode,
                sprintCode);

        Sprint sprint = mock(Sprint.class);
        Optional<Sprint> sprintOptional = Optional.of(sprint);
        OutputSprintDTO sprintBacklogDTO = mock(OutputSprintDTO.class);

        when(projectRepository.existsById(projectCode))
                .thenReturn(true);
        when(userStoryRepository.existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED))
                .thenReturn(true);
        when(sprintRepository.findBySprintIdAndSprintStatusLike(sprintID, SprintStatus.OPENED))
                .thenReturn(sprintOptional);
        when(sprint.addToSprintBacklog(userStoryID))
                .thenReturn(true);
        when(sprintRepository.save(sprint)).thenReturn(sprint);
        outputAssembler.when(() -> OutputSprintAssembler.generateDTO(sprint)).thenReturn(sprintBacklogDTO);


        OutputSprintDTO result = sprintService.
                addUserStoryToSprintBacklog(projectCode, sprintCode, newUserStoryDTO);
        outputAssembler.close();

        assertEquals(sprintBacklogDTO, result);
    }

    @Test
    void addUserStoryToSprintBacklog_UserStoryDoesNotExist_False() {
        NewSprintBacklogUserStoryDTO newUserStoryDTO = new NewSprintBacklogUserStoryDTO();
        newUserStoryDTO.userStoryCode = new UserStoryCode("US001");

        UserStoryID userStoryID = new UserStoryID(
                projectCode,
                newUserStoryDTO.userStoryCode);

        when(projectRepository.existsById(projectCode))
                .thenReturn(true);
        when(userStoryRepository.existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED))
                .thenReturn(false);

        Exception e = assertThrows(NoSuchElementException.class, () -> sprintService.addUserStoryToSprintBacklog
                (projectCode, sprintCode, newUserStoryDTO));

        assertEquals("The selected User Story does not exist in the Product Backlog", e.getMessage());
    }

    @Test
    void addUserStoryToSprintBacklog_ProjectDoesNotExist_False() {
        NewSprintBacklogUserStoryDTO newUserStoryDTO = new NewSprintBacklogUserStoryDTO();
        newUserStoryDTO.userStoryCode = new UserStoryCode("US001");

        when(projectRepository.existsById(projectCode)).thenReturn(false);

        Exception e = assertThrows(NoSuchElementException.class, () -> sprintService.addUserStoryToSprintBacklog
                (projectCode, sprintCode, newUserStoryDTO));

        assertEquals("The selected Project does not exist", e.getMessage());
    }

    @Test
    void sprintService_NullSprintRepository_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintService(null, projectRepository, userStoryRepository, sprintFactory));
        assertEquals("Cannot have null fields", exception.getMessage());
    }

    @Test
    void sprintService_NullProjectRepository_throwsException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintService(sprintRepository, null, userStoryRepository, sprintFactory));
        assertEquals("Cannot have null fields", exception.getMessage());
    }

    @Test
    void sprintService_NullUserStoryRepository_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintService(sprintRepository, projectRepository, null, sprintFactory));
        assertEquals("Cannot have null fields", exception.getMessage());
    }

    @Test
    void sprintService_NullSprintFactory_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintService(sprintRepository, projectRepository, userStoryRepository, null));
        assertEquals("Cannot have null fields", exception.getMessage());
    }

    @Test
    void showScrumBoard_SprintDoesNotExist_throwsException() {
        //Arrange
        ProjectCode testProjectCode = new ProjectCode("P1");
        SprintCode testSprintCode = new SprintCode("S5");
        SprintID testSprintID = new SprintID(testProjectCode, testSprintCode);

        ViewSprintBacklogDTO sprintBacklogDTO = new ViewSprintBacklogDTO();
        sprintBacklogDTO.projectCode = testProjectCode;
        sprintBacklogDTO.sprintCode = testSprintCode;

        when(projectRepository.existsById(testProjectCode)).thenReturn(true);
        when(sprintRepository.findById(testSprintID)).thenReturn(Optional.empty());

        //Act
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                sprintService.showScrumBoard(sprintBacklogDTO));

        //Assert
        assertEquals("Sprint does not exist.", e.getMessage());
    }

    @Test
    void showScrumBoard_SprintExistsAndIsEmpty_EmptyList() {
        //Arrange
        ProjectCode testProjectCode = new ProjectCode("P1");
        SprintCode testSprintCode = new SprintCode("S5");
        SprintID testSprintID = new SprintID(testProjectCode, testSprintCode);
        Sprint testSprint = mock(Sprint.class);

        ViewSprintBacklogDTO sprintBacklogDTO = new ViewSprintBacklogDTO();
        sprintBacklogDTO.projectCode = testProjectCode;
        sprintBacklogDTO.sprintCode = testSprintCode;

        SprintBacklog sprintBacklog = mock(SprintBacklog.class);

        when(projectRepository.existsById(testProjectCode)).thenReturn(true);
        when(sprintRepository.findById(testSprintID)).thenReturn(Optional.of(testSprint));
        when(testSprint.getIDsOfUserStoriesInSprintBacklog()).thenReturn(new ArrayList<>());

        List<UserStoryDTO> expected = new ArrayList<>();

        //Act
        List<UserStoryDTO> result = sprintService.showScrumBoard(sprintBacklogDTO);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void showScrumBoard_SprintExistsAndHasUserStories_List() {
        //Arrange
        ProjectCode testProjectCode = new ProjectCode("P1");
        SprintCode testSprintCode = new SprintCode("S5");
        SprintID testSprintID = new SprintID(testProjectCode, testSprintCode);
        Sprint testSprint = mock(Sprint.class);
        UserStoryID firstUserStory = new UserStoryID(testProjectCode, new UserStoryCode("US001"));
        UserStoryID secondUserStory = new UserStoryID(testProjectCode, new UserStoryCode("US002"));

        List<UserStoryID> currentSprintBacklog = new ArrayList<>();
        currentSprintBacklog.add(firstUserStory);
        currentSprintBacklog.add(secondUserStory);

        UserStory userStoryOne = mock(UserStory.class);
        UserStory userStoryTwo = mock(UserStory.class);
        List<UserStory> userStoryList = new ArrayList<>();
        userStoryList.add(userStoryOne);
        userStoryList.add(userStoryTwo);

        ViewSprintBacklogDTO sprintBacklogDTO = new ViewSprintBacklogDTO();
        sprintBacklogDTO.projectCode = testProjectCode;
        sprintBacklogDTO.sprintCode = testSprintCode;

        SprintBacklog sprintBacklog = mock(SprintBacklog.class);

        when(projectRepository.existsById(testProjectCode)).thenReturn(true);
        when(sprintRepository.findById(testSprintID)).thenReturn(Optional.of(testSprint));
        when(userStoryRepository.findAllById(currentSprintBacklog)).thenReturn(userStoryList);
        when(testSprint.getIDsOfUserStoriesInSprintBacklog()).thenReturn(currentSprintBacklog);

        List<UserStoryDTO> expected = new ArrayList<>();
        UserStoryDTO firstDTO = new UserStoryDTO();
        UserStoryDTO secondDTO = new UserStoryDTO();
        expected.add(firstDTO);
        expected.add(secondDTO);

        MockedStatic<UserStoryAssembler> assembler = Mockito.mockStatic(UserStoryAssembler.class);

        assembler.when(() -> UserStoryAssembler.createUserStoryDTOList(any())).thenReturn(expected);


        //Act
        List<UserStoryDTO> result = sprintService.showScrumBoard(sprintBacklogDTO);
        assembler.close();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void createSprint_HappyPath() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2023, 4, 23);
        SprintCode sprintCode = new SprintCode("S1");
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 1);
        TimePeriod timePeriod = new TimePeriod(startDate, endDate);
        Project project = mock(Project.class);
        Sprint sprint = mock(Sprint.class);


        when(sprint.getProjectCode()).thenReturn(new ProjectCode("P1"));
        when(project.getProjectStatus()).thenReturn(ProjectStatus.PLANNED);
        when(sprint.getStartDate()).thenReturn(LocalDate.of(2023, 4, 1));
        when(sprint.getSprintDuration()).thenReturn(new SprintDuration(4));

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getPeriod()).thenReturn(Optional.of(timePeriod));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(4)));

        SprintID testSprintID = new SprintID(newSprintDTODouble.projectCode, sprintCode);
        SprintDuration sprintDuration = new SprintDuration(3);

        when(sprintRepository.existsById(testSprintID)).thenReturn(false);

        when(sprintFactory.createSprint(testSprintID, newSprintDTODouble.startDate, sprintDuration,
                LocalDate.now().plusDays(21))).thenReturn(sprint);
        when(sprintRepository.save(any())).thenReturn(sprint);

        CreatedSprintDTO result = sprintService.createSprint(newSprintDTODouble);
        CreatedSprintDTO expectedResult = new CreatedSprintDTO();
        expectedResult.sprintDuration = new SprintDuration(4);
        expectedResult.startDate = LocalDate.of(2023, 4, 1);
        expectedResult.endDate = LocalDate.of(2023, 4, 29);
        expectedResult.sprintStatus = SprintStatus.PLANNED;
        expectedResult.sprintID = new SprintID(new ProjectCode("P1"), null);

        assertEquals(expectedResult, result);
    }

    @Test
    void createSprint_ProjectDoesNotExists_IllegalArgumentException() {
        NewSprintDTO newSprintDTO = new NewSprintDTO();
        newSprintDTO.projectCode = new ProjectCode("P1");
        newSprintDTO.startDate = LocalDate.of(2023, 4, 1);

        when(projectRepository.existsById(newSprintDTO.projectCode)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTO));

        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void createSprint_ProjectStatusClosed_IllegalArgumentException() {
        NewSprintDTO newSprintDTO = new NewSprintDTO();
        newSprintDTO.projectCode = new ProjectCode("P1");
        newSprintDTO.startDate = LocalDate.of(2023, 4, 1);
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTO.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTO.projectCode)).thenReturn(Optional.ofNullable(project));
        when(project.getProjectStatus()).thenReturn(ProjectStatus.CLOSED);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTO));

        assertEquals("Invalid project status", exception.getMessage());
    }

    @Test
    void createSprint_SprintAlreadyExists_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2023, 4, 23);
        Project project = mock(Project.class);


        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.ofNullable(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(true);

        try {
            sprintService.createSprint(newSprintDTODouble);
        } catch (IllegalArgumentException e) {
            assertEquals("Sprint already exists", e.getMessage());
        }
    }

    @Test
    void createSprint_SprintStartDateNotDefined_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = null;
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("can not create a sprint with start date not defined", exception.getMessage());
    }

    @Test
    void createSprint_SprintDurationNotDefined_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("Sprint duration is not defined", exception.getMessage());
    }

    @Test
    void createSprint_ProjectTimePeriodNotDefined_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2023, 4, 23);
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("Project time period is not defined", exception.getMessage());
    }

    @Test
    void createSprint_SprintStartDateBeforeProjectDatesRange_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2021, 1, 1);
        TimePeriod timePeriod = new TimePeriod(LocalDate.of(2022, 1, 1),
                LocalDate.of(2024, 1, 1));
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));
        when(project.getPeriod()).thenReturn(Optional.of(timePeriod));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("Sprint start or/and end dates are outside the project range", exception.getMessage());
    }

    @Test
    void createSprint_SprintStartDateAfterProjectDatesRange_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2024, 1, 2);
        TimePeriod timePeriod = new TimePeriod(LocalDate.of(2022, 1, 1),
                LocalDate.of(2024, 1, 1));
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));
        when(project.getPeriod()).thenReturn(Optional.of(timePeriod));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("Sprint start or/and end dates are outside the project range", exception.getMessage());
    }

    @Test
    void createSprint_SprintEndDateOutOfProjectDatesRange_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2023, 12, 31);
        TimePeriod timePeriod = new TimePeriod(LocalDate.of(2022, 1, 1),
                LocalDate.of(2024, 1, 1));
        Project project = mock(Project.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));
        when(project.getPeriod()).thenReturn(Optional.of(timePeriod));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("Sprint start or/and end dates are outside the project range", exception.getMessage());
    }

    @Test
    void createSprint_SprintStartDateBeforePreviousSprintEndDate_IllegalArgumentException() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        newSprintDTODouble.projectCode = new ProjectCode("P1");
        newSprintDTODouble.startDate = LocalDate.of(2023, 6, 1);
        TimePeriod timePeriod = new TimePeriod(LocalDate.of(2022, 1, 1),
                LocalDate.of(2024, 1, 1));
        LocalDate previousSprintEndDate = LocalDate.of(2023, 6, 10);

        Project project = mock(Project.class);
        Sprint previousSprint = mock(Sprint.class);

        when(projectRepository.existsById(newSprintDTODouble.projectCode)).thenReturn(true);
        when(projectRepository.findById(newSprintDTODouble.projectCode)).thenReturn(Optional.of(project));
        when(project.getSprintDuration()).thenReturn(Optional.of(new SprintDuration(3)));
        when(project.getPeriod()).thenReturn(Optional.of(timePeriod));

        when(sprintRepository.existsById(new SprintID(newSprintDTODouble.projectCode,
                new SprintCode("S1")))).thenReturn(false);
        when(sprintRepository.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(newSprintDTODouble.projectCode)).
                thenReturn(Optional.ofNullable(previousSprint));
        when(previousSprint.getEndDate()).thenReturn(previousSprintEndDate);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.createSprint(newSprintDTODouble));

        assertEquals("Start date must be equal to or after the end date of the previous sprint",
                exception.getMessage());
    }

    @Test
    void changeSprintStatus_setSprintInformation_HappyPath() {
        NewSprintDTO newSprintDTODouble = new NewSprintDTO();
        SprintStatusDTO newSprintStatusDouble = new SprintStatusDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");

        newSprintStatusDouble.sprintID = new SprintID(projectCode, sprintCode);
        newSprintStatusDouble.sprintStatus = SprintStatus.PLANNED;

        Sprint sprint = mock(Sprint.class);

        when(sprintRepository.existsById(newSprintStatusDouble.sprintID)).thenReturn(true);
        when(sprintRepository.findById(newSprintStatusDouble.sprintID)).thenReturn(Optional.of(sprint));

        doReturn(SprintStatus.CLOSED).when(sprint).getSprintStatus();
        when(sprintRepository.save(any())).thenReturn(sprint);

        CreatedSprintDTO result = sprintService.changeSprintStatus(newSprintStatusDouble);

        assertEquals(SprintStatus.CLOSED, result.sprintStatus);
    }

    @Test
    void changeSprintStatus_SprintDoesNotExist_Exception() {
        SprintStatusDTO newSprintStatusDouble = new SprintStatusDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");

        newSprintStatusDouble.sprintID = new SprintID(projectCode, sprintCode);
        newSprintStatusDouble.sprintStatus = SprintStatus.PLANNED;

        when(sprintRepository.existsById(newSprintStatusDouble.sprintID)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.changeSprintStatus(newSprintStatusDouble));

        assertEquals("Sprint does not exist", exception.getMessage());
    }

    @Test
    void changeSprintStatus_SprintStatusNotChanged_Exception() {
        SprintStatusDTO newSprintStatusDouble = new SprintStatusDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");

        newSprintStatusDouble.sprintID = new SprintID(projectCode, sprintCode);
        newSprintStatusDouble.sprintStatus = SprintStatus.PLANNED;

        Sprint sprint = mock(Sprint.class);

        when(sprintRepository.existsById(newSprintStatusDouble.sprintID)).thenReturn(true);
        when(sprintRepository.findById(newSprintStatusDouble.sprintID)).thenReturn(Optional.of(sprint));
        when(sprintRepository.existsBySprintIdJPAProjectCodeAndSprintStatusLike(projectCode,
                newSprintStatusDouble.sprintStatus)).thenReturn(false);
        when(sprint.hasStatus(newSprintStatusDouble.sprintStatus)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.changeSprintStatus(newSprintStatusDouble));

        assertEquals("The sprint already has the desired status.", exception.getMessage());
    }

    @Test
    void getOpenSprint_ProjectDoesNotExist_ThrowsException() {
        ProjectCode projectCode = new ProjectCode("non-existing project");
        when(projectRepository.existsById(projectCode)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sprintService.getOpenSprint(projectCode));

        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void getOpenSprint_NoOpenSprints_ThrowsException() {
        ProjectCode projectCode = new ProjectCode("P1");
        when(projectRepository.existsById(projectCode)).thenReturn(true);
        when(sprintRepository.findOpenSprint(projectCode)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sprintService.getOpenSprint(projectCode));
        assertEquals("There are no open sprints.", exception.getMessage());
    }

    @Test
    void getOpenSprint_FindsSprint_Equal() {
        MockedStatic<OpenSprintOutputAssembler> outputAssembler = mockStatic(OpenSprintOutputAssembler.class);

        ProjectCode projectCode = new ProjectCode("existing-project");

        OpenSprintOutputDTO createdSprintDTO = mock(OpenSprintOutputDTO.class);
        Sprint sprintDouble = mock(Sprint.class);

        when(projectRepository.existsById(projectCode)).thenReturn(true);
        when(sprintRepository.findOpenSprint(projectCode)).thenReturn(Optional.of(sprintDouble));
        outputAssembler.when(() -> OpenSprintOutputAssembler.generateDTO(sprintDouble)).thenReturn(createdSprintDTO);

        OpenSprintOutputDTO result = sprintService.getOpenSprint(projectCode);

        assertEquals(createdSprintDTO, result);

        outputAssembler.close();
    }

    @Test
    void changeSprintStatus_SprintAlreadyOpen_Exception() {
        SprintStatusDTO newSprintStatusDouble = new SprintStatusDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");

        SprintID sprintID = new SprintID(
                projectCode,
                sprintCode
        );

        newSprintStatusDouble.sprintID = new SprintID(projectCode, sprintCode);
        newSprintStatusDouble.sprintStatus = SprintStatus.OPENED;

        Sprint sprint = mock(Sprint.class);

        when(sprintRepository.existsById(newSprintStatusDouble.sprintID)).thenReturn(true);
        when(sprintRepository.findById(newSprintStatusDouble.sprintID)).thenReturn(Optional.of(sprint));
        when(sprint.getSprintStatus()).thenReturn(SprintStatus.PLANNED);
        when(sprint.identity()).thenReturn(sprintID);
        when(sprintRepository.existsBySprintIdJPAProjectCodeAndSprintStatusLike(projectCode,
               SprintStatus.OPENED)).thenReturn(true);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> sprintService.changeSprintStatus(newSprintStatusDouble));

        assertEquals("There is already an open sprint.", exception.getMessage());
    }

    @Test
    void createSprintList_ReturnsCorrectList_True() {
        MockedStatic<OpenSprintOutputAssembler> outputAssembler = mockStatic(OpenSprintOutputAssembler.class);

        Sprint sprintDouble = mock(Sprint.class);

        List<Sprint> sprintList = new ArrayList<>();
        sprintList.add(sprintDouble);

        OpenSprintOutputDTO outputDTO = new OpenSprintOutputDTO();
        List<OpenSprintOutputDTO> dtoList = new ArrayList<>();
        dtoList.add(outputDTO);

        when(projectRepository.existsById(new ProjectCode("P999"))).thenReturn(true);
        when(sprintRepository.findByProjectCode(new ProjectCode("P999"))).thenReturn(sprintList);
        outputAssembler.when(() -> OpenSprintOutputAssembler.createSprintList(any())).thenReturn(dtoList);

        List<OpenSprintOutputDTO> result = sprintService.createSprintList(new ProjectCode("P999"));

        assertEquals(dtoList, result);

        outputAssembler.close();
    }

    @Test
    void createSprintList_ProjectDoesNotExist_Exception() {
        when(projectRepository.existsById(new ProjectCode("404"))).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> sprintService.createSprintList(new ProjectCode("404")));

        assertEquals("Project does not exist.", exception.getMessage());

    }
}

