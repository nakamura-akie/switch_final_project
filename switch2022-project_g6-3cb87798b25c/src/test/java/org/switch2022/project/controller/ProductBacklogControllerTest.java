package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.project.ProjectFactoryImplementation;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.userstory.UserStoryFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.UserStoryRepositoryFake;
import org.switch2022.project.repository.interfaces.ProjectRepository;
import org.switch2022.project.repository.interfaces.UserStoryRepository;
import org.switch2022.project.service.ProjectService;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class ProductBacklogControllerTest {

    @SpringBootTest
    @Nested
    @ActiveProfiles("test")
    class UnitTest {
        @Autowired
        private ProductBacklogController controllerForUnitTesting;
        @MockBean
        private ProjectService productBacklogServiceDouble;
        private List<UserStoryDTO> userStoryDTOList;


        @BeforeEach
        void initMocking() {

            UserStoryDTO userStoryDTOOne = mock(UserStoryDTO.class);
            userStoryDTOOne.userStoryCode = new UserStoryCode("US001");
            UserStoryDTO userStoryDTOTwo = mock(UserStoryDTO.class);
            userStoryDTOTwo.userStoryCode = new UserStoryCode("US002");
            UserStoryDTO userStoryDTOThree = mock(UserStoryDTO.class);
            userStoryDTOThree.userStoryCode = new UserStoryCode("US003");

            this.userStoryDTOList = new ArrayList<>();
            this.userStoryDTOList.add(userStoryDTOOne);
            this.userStoryDTOList.add(userStoryDTOTwo);
            this.userStoryDTOList.add(userStoryDTOThree);

            List<UserStoryDTO> emptyList = new ArrayList<>();

            String existingProject = "P001";
            ProjectCode existentProject = new ProjectCode(existingProject);
            String nonexistentProject = "P002";
            ProjectCode nonExistentProject = new ProjectCode(nonexistentProject);

            when(productBacklogServiceDouble.createUserStoryList(existentProject)).thenReturn(userStoryDTOList);
            when(productBacklogServiceDouble.createUserStoryList(nonExistentProject)).thenReturn(emptyList);
        }

        @AfterEach
        void tearDownMocking() {
            this.controllerForUnitTesting = null;
            this.productBacklogServiceDouble = null;
        }

        @Test
        void constructor_checkIfExceptionIsThrownWhenServiceIsNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new ProductBacklogController(null, null));

            assertEquals("Product Backlog Service cannot be null", exception.getMessage());
        }


        @Test
        void userStoryList_UnitTestingReturnsUserStoryDTOListWhenProjectExists_CompleteList() {
            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(userStoryDTOList);

            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = this.controllerForUnitTesting.createUserStoryList("P001");

            assertEquals(expected, result);
        }

        @Test
        void userStoryList_UnitTestingReturnsUserStoryDTOEmptyListWhenProjectExists_ReturnsEmptyList() {
            List<UserStoryDTO> empty = new ArrayList<>();

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(empty);

            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = controllerForUnitTesting.createUserStoryList("P002");

            assertEquals(expected, result);
        }

        @Test
        void userStoryList_UnitTestingThrowsExceptionWhenProjectDoesNotExist_ThrowsException() {
            ProjectCode projectCode = new ProjectCode("P003");
            when(productBacklogServiceDouble.createUserStoryList(projectCode))
                    .thenThrow(new NoSuchElementException("Profile does not exist"));

            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body
                    (new MessageResponse("Profile does not exist"));

            ResponseEntity<Object> result = controllerForUnitTesting.createUserStoryList("P003");

            assertEquals(expected, result);
        }
    }

    @SpringBootTest
    @Nested
    @ActiveProfiles("test")
    class IntegrationTest {
        private final List<UserStoryDTO> userStoryDTOList =
                new ArrayList<>();
        private final ProjectFactory projectFactoryImplementation =
                new ProjectFactoryImplementation();
        private final ProjectCode projectCodeOne = new ProjectCode("P001");
        private final ProjectCode projectCodeTwo = new ProjectCode("P002");
        private final ProjectCode projectCodeFour = new ProjectCode("P004");
        private final ProjectCode projectCodeFive = new ProjectCode("P005");
        private final Project projectOne = projectFactoryImplementation.createProject(
                projectCodeOne,
                new ProjectName("Project One"),
                new Description("Description"),
                new TaxIdentificationNumberPortugalImplementation("224682687"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials")
        );
        private final Project projectTwo = projectFactoryImplementation.createProject(
                projectCodeTwo,
                new ProjectName("Project Two"),
                new Description("Description"),
                new TaxIdentificationNumberPortugalImplementation("224682687"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials")
        );
        private final Project projectThree = projectFactoryImplementation.createProject(
                new ProjectCode("P003"),
                new ProjectName("Project Three"),
                new Description("Description"),
                new TaxIdentificationNumberPortugalImplementation("245700943"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials")
        );
        private final Project projectFour = projectFactoryImplementation.createProject(
                projectCodeFour,
                new ProjectName("Project Four"),
                new Description("Description"),
                new TaxIdentificationNumberPortugalImplementation("224682687"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials")
        );
        private final Project projectFive = projectFactoryImplementation.createProject(
                projectCodeFive,
                new ProjectName("Project Five"),
                new Description("Description"),
                new TaxIdentificationNumberPortugalImplementation("224682687"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials")
        );
        private final UserStoryDTO userStoryDTOOne = new UserStoryDTO();
        private final UserStoryDTO userStoryDTOTwo = new UserStoryDTO();
        private final UserStoryDTO userStoryDTOThree = new UserStoryDTO();
        private final UserStoryCode userStoryCodeOne = new UserStoryCode("US001");
        private final UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        private final UserStoryCode userStoryCodeThree = new UserStoryCode("US003");
        private final UserStoryCode userStoryCodeFour = new UserStoryCode("US004");
        private final UserStoryCode userStoryCodeFive = new UserStoryCode("US005");
        UserStory userStoryOne;
        UserStory userStoryTwo;
        UserStory userStoryThree;
        UserStory userStoryFour;
        UserStory userStoryFive;
        UserStory userStorySix;
        UserStory userStorySeven;
        UserStory userStoryEight;
        @Autowired
        private ProductBacklogController controllerForIntegrationTesting;
        private UserStoryRepository userStoryRepository =
                new UserStoryRepositoryFake();
        @Autowired
        private ProjectRepository projectRepository;

        @BeforeEach
        void initIntegration() {
            UserStoryFactory userStoryFactory = new UserStoryFactoryImplementation();

            userStoryOne = userStoryFactory.createUserStory(new UserStoryID(projectCodeOne, userStoryCodeOne),
                    new Description("User Story description"));
            userStoryTwo = userStoryFactory.createUserStory(new UserStoryID(projectCodeOne, userStoryCodeTwo),
                    new Description("User Story description"));
            userStoryThree = userStoryFactory.createUserStory(new UserStoryID(projectCodeOne, userStoryCodeThree),
                    new Description("User Story description"));
            userStoryFour = userStoryFactory.createUserStory(new UserStoryID(projectCodeTwo, userStoryCodeOne),
                    new Description("User Story description"));
            userStoryFive = userStoryFactory.createUserStory(new UserStoryID(projectCodeTwo, userStoryCodeTwo),
                    new Description("User Story description"));
            userStorySix = userStoryFactory.createUserStory(new UserStoryID(projectCodeTwo, userStoryCodeThree),
                    new Description("User Story description"));

            userStorySeven = userStoryFactory.createUserStory(new UserStoryID(projectCodeFive, userStoryCodeFour),
                    new Description("User Story description"));
            userStoryEight = userStoryFactory.createUserStory(new UserStoryID(projectCodeFive, userStoryCodeFive),
                    new Description("User Story description"));

            userStorySeven.setFinished();
            userStoryEight.changeStatus(UserStoryStatus.CANCELLED);

            userStoryRepository.save(userStoryOne);
            userStoryRepository.save(userStoryTwo);
            userStoryRepository.save(userStoryThree);
            userStoryRepository.save(userStoryFour);
            userStoryRepository.save(userStoryFive);
            userStoryRepository.save(userStorySix);
            userStoryRepository.save(userStorySeven);
            userStoryRepository.save(userStoryEight);


            projectOne.addUserStoryToProductBacklog(userStoryOne.identity());
            projectOne.addUserStoryToProductBacklog(userStoryTwo.identity());
            projectOne.addUserStoryToProductBacklog(userStoryThree.identity());

            projectTwo.addUserStoryToProductBacklog(userStoryOne.identity());
            projectTwo.addUserStoryToProductBacklog(userStoryTwo.identity());
            projectTwo.addUserStoryToProductBacklog(userStoryThree.identity());

            projectFour.addUserStoryToProductBacklog(userStoryFour.identity());
            projectFour.addUserStoryToProductBacklog(userStoryFive.identity());

            projectRepository.save(projectOne);
            projectRepository.save(projectTwo);
            projectRepository.save(projectFour);
            projectRepository.save(projectFive);
            projectRepository.save(projectThree);

            userStoryDTOOne.projectCode = userStoryOne.identity().getProjectCode();
            this.userStoryDTOOne.userStoryCode = userStoryOne.identity().getUserStoryCode();
            userStoryDTOTwo.projectCode = userStoryTwo.identity().getProjectCode();
            this.userStoryDTOTwo.userStoryCode = userStoryTwo.identity().getUserStoryCode();
            this.userStoryDTOOne.description = userStoryOne.getDescription();
            this.userStoryDTOTwo.description = userStoryTwo.getDescription();
            this.userStoryDTOThree.userStoryCode = userStoryThree.identity().getUserStoryCode();
            userStoryDTOThree.projectCode = userStoryThree.identity().getProjectCode();
            this.userStoryDTOThree.description = userStoryThree.getDescription();
            userStoryDTOOne.status = userStoryOne.getUserStoryStatus();
            userStoryDTOTwo.status = userStoryTwo.getUserStoryStatus();
            userStoryDTOThree.status = userStoryThree.getUserStoryStatus();


            this.userStoryDTOList.add(userStoryDTOOne);
            this.userStoryDTOList.add(userStoryDTOTwo);
            this.userStoryDTOList.add(userStoryDTOThree);
        }

        @AfterEach
        void tearDownIntegration() {
            this.controllerForIntegrationTesting = null;
            this.userStoryRepository.deleteAll();
            this.userStoryRepository = null;
            this.projectRepository.deleteAll();
            this.projectRepository = null;
        }

        @Test
        void userStoryList_ReturnsListWhenProjectExists_CompleteList() {

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(userStoryDTOList);

            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = this.controllerForIntegrationTesting.createUserStoryList("P001");

            assertEquals(expected, result);
        }

        @Test
        void userStoryList_ReturnsEmptyListWhenBacklogIsEmpty_EmptyList() {
            List<UserStoryDTO> empty = new ArrayList<>();

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(empty);

            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = controllerForIntegrationTesting.createUserStoryList("P003");

            assertEquals(expected, result);
        }

        @Test
        void userStoryList_FilteredUnfinishedAndCanceledUserStories_ReturnsList() {
            userStoryThree.setFinished();

            List<UserStoryDTO> list = new ArrayList<>();
            list.add(userStoryDTOOne);
            list.add(userStoryDTOTwo);

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(
                    list);

            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = this.controllerForIntegrationTesting.createUserStoryList("P001");

            assertEquals(expected, result);
        }

        @Test
        void userStoryList_IntegrationTestProductBacklogHasOnlyFinishedOrCancelledUserStories_ReturnsEmptyList() {
            List<UserStoryDTO> empty = new ArrayList<>();

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(
                    empty);

            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = controllerForIntegrationTesting.createUserStoryList("P005");

            assertEquals(expected, result);
        }

        @Test
        void userStoryList_IntegrationTestingThrowsExceptionWhenProjectExists_ThrowsException() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body
                    (new MessageResponse("Project does not exist."));

            ResponseEntity<Object> result = controllerForIntegrationTesting.createUserStoryList("P020");

            assertEquals(expected, result);
        }

        @Test
        void findUserStoryByID() {

            OutputUserStoryDTO outputUserStoryDTO = new OutputUserStoryDTO();
            outputUserStoryDTO.projectCode = projectCodeOne.getProjectCodeValue();
            outputUserStoryDTO.userStoryCode = userStoryOne.getUserStoryCode().getUserStoryCodeValue();
            outputUserStoryDTO.description = userStoryOne.getDescription().getDescriptionValue();
            outputUserStoryDTO.userStoryEffort = Effort.ONE.getValue();
            outputUserStoryDTO.status = UserStoryStatus.OPEN.toString();

            EntityModel<?> entityModel = EntityModel.of(
                    outputUserStoryDTO,
                    linkTo(methodOn(ProductBacklogController.class)
                            .findUserStoryByID(projectCodeOne, userStoryCodeOne)).withSelfRel(),
                    linkTo(methodOn(ProductBacklogController.class).createUserStoryList(projectCodeOne
                            .getProjectCodeValue())).withRel("/projects/P001/user-stories")
            );

            ResponseEntity<Object> expected = ResponseEntity.ok(entityModel);
            ResponseEntity<Object> result = controllerForIntegrationTesting.findUserStoryByID
                    (projectCodeOne, userStoryCodeOne);

            assertEquals(expected, result);
        }

        @Test
        void findUserStoryByID_UserStoryDoesntExist() {

            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body
                    (new MessageResponse("User Story doesn't exist"));
            ResponseEntity<Object> result = controllerForIntegrationTesting.findUserStoryByID
                    (projectCodeTwo, new UserStoryCode("beep"));

            assertEquals(expected, result);
        }
    }

}