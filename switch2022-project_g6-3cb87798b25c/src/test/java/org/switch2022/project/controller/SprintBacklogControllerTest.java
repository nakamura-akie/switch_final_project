package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintFactory;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.SprintRepositoryFake;
import org.switch2022.project.repository.UserStoryRepositoryFake;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewSprintBacklogUserStoryDTO;
import org.switch2022.project.utils.dto.OutputSprintDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class SprintBacklogControllerTest {

    @Autowired
    private SprintBacklogController controller;
    @Autowired
    private Repository<Project, ProjectCode> projectRepository;
    @Autowired
    private ProjectFactory projectFactory;
    @Autowired
    private SprintRepositoryFake sprintRepositoryFake;
    @Autowired
    private UserStoryRepositoryFake userStoryRepositoryFake;
    @Autowired
    private UserStoryFactory userStoryFactory;
    @Autowired
    private SprintFactory sprintFactory;

    private ProjectCode projectCode;
    private String userStoryCodeValue;
    private SprintCode sprintCode;
    private Sprint sprint;
    private NewSprintBacklogUserStoryDTO newUserStoryDTO;

    @BeforeEach
    void init() {
        projectCode = new ProjectCode("P001");
        String projectNameValue = "Not Fast, Just Furious";
        ProjectName projectName = new ProjectName(projectNameValue);
        String projectDescriptionValue = "A very good project";
        String customer = "238199576";
        ProjectTypologyName projectTypology = new ProjectTypologyName("Flexible");
        Description projectDescription = new Description(projectDescriptionValue);
        TaxIdentificationNumber taxIdentificationNumber = new TaxIdentificationNumberPortugalImplementation(customer);
        BusinessSectorName businessSector = new BusinessSectorName("Tech");
        SprintDuration sprintDuration = new SprintDuration(3);
        Project project = projectFactory.createProject(projectCode, projectName, projectDescription,
                taxIdentificationNumber, businessSector, projectTypology);
        projectRepository.save(project);

        userStoryCodeValue = "US001";
        sprintCode = new SprintCode("SPT001");

        SprintID sprintID = new SprintID(projectCode, sprintCode);


        sprint = sprintFactory.createSprint(sprintID, LocalDate.of(2024, 05, 04),
                sprintDuration, LocalDate.of(2024, 05, 25));
        sprint.defineSprintStatus(SprintStatus.OPENED);
        sprintRepositoryFake.save(sprint);

        UserStoryCode userStoryCode = new UserStoryCode(userStoryCodeValue);
        Description userStoryDescription = new Description("This is a US");

        UserStoryID userStoryID = new UserStoryID(projectCode, userStoryCode);

        UserStory userStory = userStoryFactory.createUserStory(userStoryID, userStoryDescription);
        userStory.changeStatus(UserStoryStatus.OPEN);
        userStoryRepositoryFake.save(userStory);

        newUserStoryDTO = new NewSprintBacklogUserStoryDTO();
        newUserStoryDTO.userStoryCode = userStoryCode;

        ProjectCode projectCodeTwo = new ProjectCode("P002");
        Project projectTwo = projectFactory.createProject(projectCodeTwo, projectName, projectDescription,
                taxIdentificationNumber, businessSector, projectTypology);
        projectRepository.save(projectTwo);

        ProjectCode projectCodeThree = new ProjectCode("P33");
        Project projectThree = projectFactory.createProject(projectCodeThree, projectName, projectDescription,
                taxIdentificationNumber, businessSector, projectTypology);
        projectRepository.save(projectThree);

        UserStoryCode userStoryCodeOne = new UserStoryCode("US001");
        UserStory userStoryOne = userStoryFactory.createUserStory(new UserStoryID(projectCodeThree, userStoryCodeOne)
                , new Description("description"));

        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        UserStory userStoryTwo = userStoryFactory.createUserStory(new UserStoryID(projectCodeThree, userStoryCodeTwo)
                , new Description("description"));

        userStoryRepositoryFake.save(userStoryOne);
        userStoryRepositoryFake.save(userStoryTwo);

        Sprint sprintOne = sprintFactory.createSprint
                (new SprintID(projectCodeThree, new SprintCode("S1")),
                        LocalDate.of(2024, 05, 04),
                        sprintDuration, LocalDate.of(2024, 05, 25));
        sprintRepositoryFake.save(sprintOne);

        sprintOne.addToSprintBacklog(new UserStoryID(projectCodeThree, userStoryCodeOne));
        sprintOne.addToSprintBacklog(new UserStoryID(projectCodeThree, userStoryCodeTwo));

        Sprint sprintTwo = sprintFactory.createSprint
                (new SprintID(projectCodeThree, new SprintCode("S2")),
                        LocalDate.of(2024, 05, 25),
                        sprintDuration, LocalDate.of(2024, 06, 10));
        sprintRepositoryFake.save(sprintTwo);
    }

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
        sprintRepositoryFake.deleteAll();
        userStoryRepositoryFake.deleteAll();
    }

    @Test
    void constructor_NullSprintService_ThrowsException() {
        String expected = "Sprint Service cannot be null.";

        String result = assertThrows(NullConstructorArgumentException.class, () -> {
            new SprintBacklogController(null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void addUserStoryToSprintBacklog_SprintIsNotOpened_False() {
        sprint.defineSprintStatus(SprintStatus.PLANNED);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse("The selected Sprint is currently closed"));

        ResponseEntity<Object> result = controller.addUserStoryToSprintBacklog(projectCode, sprintCode,
                newUserStoryDTO);

        assertEquals(expected, result);
    }

    @Test
    void addUserStoryToSprintBacklog_EverythingExists_True() {
        List<String> newList = new LinkedList<>();
        newList.add("US001");

        OutputSprintDTO sprintBacklogDTO = new OutputSprintDTO();
        sprintBacklogDTO.projectCode = sprint.getProjectCode().getProjectCodeValue();
        sprintBacklogDTO.sprintCode = sprint.getSprintCode().getSprintCodeValue();
        sprintBacklogDTO.sprintBacklog = newList;
        sprintBacklogDTO.sprintStatus = sprint.getSprintStatus().name();
        sprintBacklogDTO.sprintDuration = sprint.getSprintDuration().getSprintDurationValue();
        sprintBacklogDTO.endDate = sprint.getEndDate().toString();
        sprintBacklogDTO.startDate = sprint.getStartDate().toString();

        EntityModel<OutputSprintDTO> entityModel = EntityModel.of(sprintBacklogDTO);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.OK).body(entityModel);

        ResponseEntity<Object> result = controller.addUserStoryToSprintBacklog(projectCode, sprintCode,
                newUserStoryDTO);

        assertEquals(expected, result);
    }

    @Test
    void addUserStoryToSprintBacklog_UserStoryDoesNotExist_False() {
        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse
                ("The selected User Story does not exist in the Product Backlog"));

        newUserStoryDTO.userStoryCode = new UserStoryCode("USZ");

        ResponseEntity<Object> result = controller.addUserStoryToSprintBacklog(projectCode, sprintCode, newUserStoryDTO);

        assertEquals(expected, result);
    }

    @Test
    void addUserStoryToSprintBacklog_ProjectDoesNotExist_False() {
        projectRepository.deleteAll();

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse
                ("The selected Project does not exist"));

        ResponseEntity<Object> result = controller.addUserStoryToSprintBacklog
                (projectCode, sprintCode, newUserStoryDTO);

        assertEquals(expected, result);
    }

    @Test
    void addUserStoryToSprintBacklog_UserStoryAlreadyInSprintBacklog_False() {
        controller.addUserStoryToSprintBacklog(projectCode, sprintCode, newUserStoryDTO);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse
                ("The selected User Story already exists in the Sprint Backlog"));

        ResponseEntity<Object> result = controller.addUserStoryToSprintBacklog
                (projectCode, sprintCode, newUserStoryDTO);

        assertEquals(expected, result);
    }

    @Test
    void showScrumBoard_SprintDoesNotExist_throwException() {
        ResponseEntity<Object> expected =
                ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Sprint does not exist."));

        ResponseEntity<Object> result =
                controller.showScrumBoard("P002", "S404");

        assertEquals(expected, result);
    }

    @Test
    void showScrumBoard_ProjectDoesNotExist_throwException() {
        ResponseEntity<Object> expected =
                ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Project does not exist."));

        ResponseEntity<Object> result =
                controller.showScrumBoard("P404", "S404");

        assertEquals(expected, result);
    }


    @Test
    void showScrumBoard_notAnEmptySprint_listOfDTO() {
        UserStoryDTO userStoryOneDTO = new UserStoryDTO();
        userStoryOneDTO.projectCode = new ProjectCode("P33");
        userStoryOneDTO.userStoryCode = new UserStoryCode("US001");
        userStoryOneDTO.description = new Description("description");
        userStoryOneDTO.status = UserStoryStatus.OPEN;

        UserStoryDTO userStoryTwoDTO = new UserStoryDTO();
        userStoryTwoDTO.projectCode = new ProjectCode("P33");
        userStoryTwoDTO.userStoryCode = new UserStoryCode("US002");
        userStoryTwoDTO.description = new Description("description");
        userStoryTwoDTO.status = UserStoryStatus.OPEN;

        List<UserStoryDTO> expectedList = new ArrayList<>();
        expectedList.add(userStoryOneDTO);
        expectedList.add(userStoryTwoDTO);

        CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(expectedList);
        ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

        ResponseEntity<Object> result =
                controller.showScrumBoard("P33", "S1");

        assertEquals(expected, result);
    }

    @Test
    void showScrumBoard_EmptySprint_emptyListOfDTO() {
        //Arrange
        List<UserStoryDTO> expectedList = new ArrayList<>();

        //Act
        CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(expectedList);
        ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

        ResponseEntity<Object> result =
                controller.showScrumBoard("P33", "S2");

        //Assert
        assertEquals(expected, result);
    }
}