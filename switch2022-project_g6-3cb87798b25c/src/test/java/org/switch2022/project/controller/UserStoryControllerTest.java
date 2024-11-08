package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactoryImplementation;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.ProjectRepositoryFake;
import org.switch2022.project.repository.UserStoryRepositoryFake;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.utils.*;
import org.switch2022.project.utils.dto.NewUserStoryDTO;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
class UserStoryControllerTest {

    @Autowired
    private UserStoryController controller;
    private Repository<Project, ProjectCode> projectRepository;
    private UserStoryRepository userStoryRepository;
    private NewUserStoryDTO newUserStoryDTO;
    private UserStoryDTO userStoryDTO;
    private ProjectCode projectCodeOne;
    private UserStoryCode userStoryCodeOne;
    private Description descriptionOne;
    private UserStoryStatus statusOne;

    @BeforeEach
    void init() {
        projectRepository = new ProjectRepositoryFake();
        userStoryRepository = new UserStoryRepositoryFake();
        ProjectFactoryImplementation projectFactory = new ProjectFactoryImplementation();
        UserStoryFactoryImplementation userStoryFactory =
                new UserStoryFactoryImplementation();

        projectCodeOne = new ProjectCode("P1");
        ProjectName projectName = new ProjectName("Project One");
        Description projectDescription = new Description("Project description");
        TaxIdentificationNumberPortugalImplementation customerID =
                new TaxIdentificationNumberPortugalImplementation("245700943");
        BusinessSectorName businessSector = new BusinessSectorName("Tech");
        ProjectTypologyName projectTypology = new ProjectTypologyName("A project typology");

        Effort effort = Effort.ONE;

        Project testProject = projectFactory.createProject(projectCodeOne, projectName, projectDescription,
                customerID, businessSector, projectTypology);
        projectRepository.save(testProject);

        UserStoryCode testUserStoryCode = new UserStoryCode("US999");
        UserStoryID testUserStoryID = new UserStoryID(
                projectCodeOne,
                testUserStoryCode);

        UserStory testUserStory = userStoryFactory.createUserStory(
                testUserStoryID, new Description("US to change Status"));
        userStoryRepository.save(testUserStory);

        userStoryCodeOne = new UserStoryCode("US001");
        descriptionOne = new Description("Some description");
        statusOne = UserStoryStatus.OPEN;

        newUserStoryDTO = new NewUserStoryDTO();
        newUserStoryDTO.projectCode = projectCodeOne;
        newUserStoryDTO.userStoryCode = userStoryCodeOne;
        newUserStoryDTO.description = descriptionOne;

        userStoryDTO = new UserStoryDTO();
        userStoryDTO.projectCode = projectCodeOne;
        userStoryDTO.userStoryCode = userStoryCodeOne;
        userStoryDTO.description = descriptionOne;
        userStoryDTO.status = statusOne;
    }

    @AfterEach
    void tearDown() {
        projectRepository.deleteAll();
        userStoryRepository.deleteAll();
        controller = null;
        newUserStoryDTO = null;
        userStoryDTO = null;
        projectCodeOne = null;
        userStoryCodeOne = null;
        descriptionOne = null;
        statusOne = null;
    }

    @Test
    void createUserStory_CreateUserStoryAndAddToEmptyProductBacklog_True() {
        EntityModel<UserStoryDTO> entityModel = EntityModel.of(
                userStoryDTO);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CREATED).body(entityModel);

        ResponseEntity<Object> result = controller.createUserStory(newUserStoryDTO);

        assertEquals(expected, result);
    }


    @Test
    void createUserStory_CreateUserStoryAndAddToNotEmptyProductBacklog_True() {
        controller.createUserStory(newUserStoryDTO);

        NewUserStoryDTO anotherNewUserStoryDTO = new NewUserStoryDTO();
        anotherNewUserStoryDTO.projectCode = projectCodeOne;
        anotherNewUserStoryDTO.userStoryCode = new UserStoryCode("US002");
        anotherNewUserStoryDTO.description = descriptionOne;

        UserStoryDTO anotherOutputDTO = new UserStoryDTO();
        anotherOutputDTO.projectCode = projectCodeOne;
        anotherOutputDTO.userStoryCode = new UserStoryCode("US002");
        anotherOutputDTO.description = descriptionOne;
        anotherOutputDTO.status = statusOne;

        EntityModel<UserStoryDTO> entityModel = EntityModel.of(
                anotherOutputDTO);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CREATED).body(entityModel);

        ResponseEntity<Object> result = controller.createUserStory(anotherNewUserStoryDTO);

        assertEquals(expected, result);
    }


    @Test
    void createUserStory_CreateAlreadyExistentUserStory_False() {
        controller.createUserStory(newUserStoryDTO);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body
                (new MessageResponse("The User Story already exists"));

        ResponseEntity<Object> result = controller.createUserStory(newUserStoryDTO);

        assertEquals(expected, result);
    }


    @Test
    void createUserStory_CreateUserStoryToNonExistentProject_False() {
        NewUserStoryDTO anotherNewUserStoryDTO = new NewUserStoryDTO();
        anotherNewUserStoryDTO.projectCode = new ProjectCode("P2");
        anotherNewUserStoryDTO.userStoryCode = new UserStoryCode("US002");
        anotherNewUserStoryDTO.description = descriptionOne;

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body
                (new MessageResponse("Project does not exist."));


        ResponseEntity<Object> result = controller.createUserStory(anotherNewUserStoryDTO);

        assertEquals(expected, result);
    }

    @Test
    void constructor_UserStoryServiceIsNull_ThrowsException() {
        //Arrange
        String expected = "User Story Service cannot be null.";

        //Act
        String result = assertThrows(NullConstructorArgumentException.class, () -> {
            new UserStoryController(null);
        }).getMessage();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void updateUserStoryStatus_happyPath_Equals() {
        UserStoryStatus newStatus = UserStoryStatus.valueOf("CANCELLED");

        UserStoryCode testUserStoryCode = new UserStoryCode("US999");

        OutputUserStoryDTO testOutputDTO = new OutputUserStoryDTO();
        testOutputDTO.projectCode = projectCodeOne.getProjectCodeValue();
        testOutputDTO.userStoryCode = testUserStoryCode.getUserStoryCodeValue();
        testOutputDTO.description = "US to change Status";
        testOutputDTO.status = UserStoryStatus.CANCELLED.toString();
        testOutputDTO.userStoryEffort = 1;

        EntityModel<OutputUserStoryDTO> entityModel = EntityModel.of(
                testOutputDTO,
                linkTo(methodOn(ProductBacklogController.class).findUserStoryByID(projectCodeOne, testUserStoryCode)).withSelfRel());

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.OK).body(entityModel);

        ResponseEntity<Object> result = controller.updateUserStoryStatus(
                projectCodeOne, testUserStoryCode, newStatus);

        assertEquals(expected, result);
    }

    @Test
    void updateUserStoryStatus_UserStoryDoesNotExist_Equals() {
        UserStoryStatus newStatus = UserStoryStatus.valueOf("OPEN");

        UserStoryCode testUserStoryCode = new UserStoryCode("US000");

        ResponseEntity<Object> result = controller.updateUserStoryStatus(
                projectCodeOne, testUserStoryCode, newStatus);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(result.getBody(), new MessageResponse("User Story does " +
                "not exist."));
    }
}

