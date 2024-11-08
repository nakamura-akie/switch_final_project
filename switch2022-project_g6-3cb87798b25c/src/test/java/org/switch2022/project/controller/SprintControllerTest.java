package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactoryImplementation;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintFactoryImplementation;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.service.SprintService;
import org.switch2022.project.utils.*;
import org.switch2022.project.utils.assembler.CreatedSprintAssembler;
import org.switch2022.project.utils.dto.CreatedSprintDTO;
import org.switch2022.project.utils.dto.NewSprintDTO;
import org.switch2022.project.utils.dto.SprintStatusDTO;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class SprintControllerTest {

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class UnitTest {

        @Autowired
        private SprintController controller;

        @MockBean
        private SprintService sprintServiceDouble;

        String projectCodeValue;
        LocalDate startDate;
        LocalDate startDateTwo;
        Sprint sprintDouble;
        NewSprintDTO newSprintDTO;
        CreatedSprintDTO createdSprintDTO;

        @BeforeEach
        void init() {
            projectCodeValue = "P1";

            startDate = LocalDate.of(2025, 5, 4);
            startDateTwo = LocalDate.of(2021, 5, 4);

            sprintDouble = mock(Sprint.class);

            newSprintDTO = new NewSprintDTO();
            newSprintDTO.projectCode = new ProjectCode(projectCodeValue);
            newSprintDTO.startDate = startDate;

            createdSprintDTO = new CreatedSprintDTO();

        }

        @AfterEach
        void tearDown() {
            projectCodeValue = null;
            startDate = null;
        }

        @Test
        void createSprint_Successful() {
            NewSprintDTO newSprintDTO = new NewSprintDTO();
            CreatedSprintDTO createdSprintDTO = new CreatedSprintDTO();

            MockedStatic<CreatedSprintAssembler> createsSprintAssembler = mockStatic(CreatedSprintAssembler.class);
            createsSprintAssembler.when(() -> CreatedSprintAssembler.generateDTO(any(Sprint.class)))
                    .thenCallRealMethod();

            when(sprintServiceDouble.createSprint(any(NewSprintDTO.class))).thenReturn(createdSprintDTO);

            ResponseEntity<Object> response = controller.createSprintREST(newSprintDTO);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());

            EntityModel<CreatedSprintDTO> entityModel = EntityModel.of(
                    createdSprintDTO,
                    linkTo(methodOn(SprintController.class).createSprintREST(newSprintDTO)).withSelfRel());
            assertNotNull(entityModel);
            assertEquals(createdSprintDTO, entityModel.getContent());
            createsSprintAssembler.close();
        }

        @Test
        public void testChangeSprintStatusREST_Success() {
            SprintCode sprintCodeOne = new SprintCode("S1");
            ProjectCode projectCodeOne = new ProjectCode("P1");
            SprintID sprintIDOne = new SprintID(projectCodeOne, sprintCodeOne);
            SprintStatus sprintStatus = SprintStatus.PLANNED;

            SprintStatusDTO sprintStatusDTO = new SprintStatusDTO();
            sprintStatusDTO.sprintID = sprintIDOne;
            sprintStatusDTO.sprintStatus = sprintStatus;

            CreatedSprintDTO createdSprintDTO = new CreatedSprintDTO();
            when(sprintServiceDouble.changeSprintStatus(sprintStatusDTO)).thenReturn(createdSprintDTO);

            ResponseEntity<Object> responseEntity = controller.changeSprintStatusREST(sprintStatusDTO);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

            EntityModel<CreatedSprintDTO> entityModel = EntityModel.of(
                    createdSprintDTO,
                    linkTo(methodOn(SprintController.class).changeSprintStatusREST(sprintStatusDTO)).withSelfRel());
            assertNotNull(entityModel);
            assertEquals(createdSprintDTO, entityModel.getContent());
        }

        @Test
        public void testChangeSprintStatusREST_Failure() {
            SprintCode sprintCodeOne = new SprintCode("S1");
            ProjectCode projectCodeOne = new ProjectCode("P1");
            SprintID sprintIDOne = new SprintID(projectCodeOne, sprintCodeOne);
            SprintStatus sprintStatus = SprintStatus.PLANNED;

            SprintStatusDTO sprintStatusDTO = new SprintStatusDTO();
            sprintStatusDTO.sprintID = sprintIDOne;
            sprintStatusDTO.sprintStatus = sprintStatus;

            String errorMessage = "Failed to change sprint status";
            when(sprintServiceDouble.changeSprintStatus(sprintStatusDTO)).thenThrow(new RuntimeException(errorMessage));

            ResponseEntity<Object> responseEntity = controller.changeSprintStatusREST(sprintStatusDTO);

            assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

            EntityModel<CreatedSprintDTO> entityModel = EntityModel.of(
                    createdSprintDTO,
                    linkTo(methodOn(SprintController.class).changeSprintStatusREST(sprintStatusDTO)).withSelfRel());
            assertNotNull(entityModel);
            assertEquals(createdSprintDTO, entityModel.getContent());
        }
            @Test
        void getOpenSprint_ReturnsSprint_True() {
            ProjectCode projectCode = new ProjectCode("P1");

            OpenSprintOutputDTO createdSprintDTO = new OpenSprintOutputDTO();
            EntityModel<?> entityModel = EntityModel.of(createdSprintDTO);

            when(sprintServiceDouble.getOpenSprint(projectCode)).thenReturn(createdSprintDTO);

            ResponseEntity expected = ResponseEntity.ok(entityModel);

            ResponseEntity<Object> result = controller.getOpenSprint("P1");

            assertEquals(expected, result);
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class IntegrationTest {

        @Autowired
        Repository<Sprint, SprintID> sprintRepository;
        @Autowired
        Repository<Project, ProjectCode> projectRepository;
        LocalDate startDateInvalid;
        @Autowired
        SprintFactoryImplementation sprintFactory;
        @Autowired
        SprintService sprintService;
        Project testProject;
        Project testProjectTwo;
        Sprint sprintTwo;

        @Autowired
        ProjectFactoryImplementation projectFactoryImplementation;
        @Autowired
        private SprintController controller;
        @Autowired
        private Repository<UserStory, UserStoryID> userStoryRepository;

        @BeforeEach
        void init() {
            LocalDate startDateValid = LocalDate.of(2023, 06, 16);
            LocalDate endDateValid = LocalDate.of(2023, 07, 07);
            startDateInvalid = LocalDate.of(2023, 04, 02);

            SprintCode sprintCode = new SprintCode("S1");
            ProjectCode projectCode = new ProjectCode("P1");
            ProjectCode projectCodeTwo = new ProjectCode("P2");
            SprintID sprintID = new SprintID(projectCode, sprintCode);
            SprintDuration sprintDurationOne = new SprintDuration(4);
            LocalDate startDate = LocalDate.of(2023, 1, 1);

            ProjectFactoryImplementation projectFactory = new ProjectFactoryImplementation();

            ProjectName projectName = new ProjectName("Project Name");
            Description description = new Description("description");
            TaxIdentificationNumber customerID = new TaxIdentificationNumberPortugalImplementation("123456789");
            BusinessSectorName businessSectorName = new BusinessSectorName("business sector name");
            ProjectTypologyName projectTypologyName = new ProjectTypologyName("project typology name");
            testProject = projectFactory.createProject(projectCode, projectName, description,
                    customerID, businessSectorName, projectTypologyName);
            testProject.setSprintDuration(new SprintDuration(3));
            projectRepository.save(testProject);

            testProjectTwo = projectFactory.createProject(projectCodeTwo, projectName, description,
                    customerID, businessSectorName, projectTypologyName);
            projectRepository.save(testProjectTwo);


            Project project = projectFactoryImplementation.createProject(new ProjectCode("P123"),
                    new ProjectName("projectName"),
                    new Description("description"),
                    new TaxIdentificationNumberPortugalImplementation("123456789"),
                    new BusinessSectorName("businessSector"),
                    new ProjectTypologyName("projectTypology"),
                    new SprintDuration(3),
                    new NumberPlannedSprints(5),
                    new Budget(10D),
                    new TimePeriod(LocalDate.of(2022, 05, 05), LocalDate.of(2023, 07, 07)));

            projectRepository.save(project);

            Project projectTwo = projectFactoryImplementation.createProject(new ProjectCode("P7"),
                    new ProjectName("projectName"),
                    new Description("description"),
                    new TaxIdentificationNumberPortugalImplementation("123456789"),
                    new BusinessSectorName("businessSector"),
                    new ProjectTypologyName("projectTypology"),
                    new SprintDuration(3),
                    new NumberPlannedSprints(5),
                    new Budget(10D),
                    new TimePeriod(LocalDate.of(2022, 05, 05), LocalDate.of(2023, 07, 07)));

            projectRepository.save(projectTwo);

            Optional<SprintDuration> sprintDuration = testProject.getSprintDuration();

            Sprint sprint = sprintFactory.createSprint(sprintID, startDateValid, sprintDuration.get(), endDateValid);
            sprint.defineSprintStatus(SprintStatus.CLOSED);
            sprintRepository.save(sprint);

            SprintID sprintIDTwo = new SprintID(new ProjectCode("P7"), new SprintCode("S1"));
            LocalDate startDateValidTwo = LocalDate.of(2024,03,02);
            LocalDate endDateValidTwo = LocalDate.of(2024,03,18);
            sprintTwo = sprintFactory.createSprint(sprintIDTwo, startDateValidTwo, sprintDuration.get(), endDateValidTwo);
            sprintTwo.defineSprintStatus(SprintStatus.OPENED);
            sprintRepository.save(sprintTwo);

        }

        @AfterEach
        void tearDown() {
            projectRepository.deleteAll();
            sprintRepository.deleteAll();
            userStoryRepository.deleteAll();
            sprintService = null;
            sprintFactory = null;
        }


        @Test
        void constructor_NullService_ThrowException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SprintController(null));
            assertEquals("Sprint Service cannot be null.", exception.getMessage());
        }

        @Test
        void testConstructorWithNullSprintService() {
            assertThrows(IllegalArgumentException.class, () -> new SprintController(null));
        }


        @Test
        void createSprint_HappyPath() {
            NewSprintDTO newSprintDTO = new NewSprintDTO();
            newSprintDTO.projectCode = new ProjectCode("P123");
            newSprintDTO.startDate = LocalDate.of(2022, 5, 6);
            ResponseEntity<Object> response = controller.createSprintREST(newSprintDTO);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertTrue(response.hasBody());
        }

        @Test
        void createSprint_CreateSprintWhenProjectDoNotExists_False() {
            NewSprintDTO newSprintDTO = new NewSprintDTO();
            newSprintDTO.projectCode = new ProjectCode("P5");
            newSprintDTO.startDate = LocalDate.of(2025, 12, 12);

            String expected = "Project does not exist.";
            ResponseEntity<Object> actualResponse = controller.createSprintREST(newSprintDTO);

            assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
            assertEquals(expected, ((MessageResponse) actualResponse.getBody()).getMessage());
        }

        @Test
        void createSprint_WithNullProjectDuration_ThrowsException() {
            NewSprintDTO newSprintDTO = new NewSprintDTO();
            newSprintDTO.projectCode = new ProjectCode("P2");
            newSprintDTO.startDate = LocalDate.now();

            String expected = "Sprint duration is not defined";

            ResponseEntity<Object> actualResponse = controller.createSprintREST(newSprintDTO);

            assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
            assertEquals(expected, ((MessageResponse) actualResponse.getBody()).getMessage());
        }

        @Test
        void createSprint_WithNullStartDate_ThrowsException() {
            NewSprintDTO newSprintDTO = new NewSprintDTO();
            newSprintDTO.projectCode = new ProjectCode("P1");
            newSprintDTO.startDate = null;

            String expected = "can not create a sprint with start date not defined";

            ResponseEntity<Object> actualResponse = controller.createSprintREST(newSprintDTO);

            assertEquals(HttpStatus.CONFLICT, actualResponse.getStatusCode());
            assertEquals(expected, ((MessageResponse) actualResponse.getBody()).getMessage());
        }

        @Test
        void getOpenSprint_SprintExists_True() {

            OpenSprintOutputDTO outputDTO = new OpenSprintOutputDTO();
            outputDTO.sprintCode = "S1";
            outputDTO.startDate = "2024-03-02";
            outputDTO.endDate = "2024-03-18";
            outputDTO.sprintStatus = "OPENED";

            EntityModel<?> entityModel = EntityModel.of(outputDTO);

            ResponseEntity<Object> expected = ResponseEntity.ok(entityModel);
            ResponseEntity<Object> result = controller.getOpenSprint("P7");

            assertEquals(expected, result);

        }

        @Test
        void getOpenSprint_ProjectDoesNotExist_StatusNotFound() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Project does not exist."));

            ResponseEntity<Object> result = controller.getOpenSprint("I DO NOT EXIST");

            assertEquals(expected, result);
        }

        @Test
        void getOpenSprint_NoOpenSprints_StatusNotFound() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("There are no open sprints."));

            ResponseEntity<Object> result = controller.getOpenSprint("P2");

            assertEquals(expected, result);
        }

        @Test
        void createSprintList_GetsSprints() {

            OpenSprintOutputDTO openSprintOutputDTO = new OpenSprintOutputDTO();
            openSprintOutputDTO.sprintCode = "S1";
            openSprintOutputDTO.startDate = "2023-06-16";
            openSprintOutputDTO.endDate = "2023-07-07";
            openSprintOutputDTO.sprintStatus = "CLOSED";

            ArrayList<OpenSprintOutputDTO> listOfSprintDTOs = new ArrayList<>();
            listOfSprintDTOs.add(openSprintOutputDTO);

            CollectionModel<OpenSprintOutputDTO> collectionModel = CollectionModel.of(listOfSprintDTOs);
            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = controller.createSprintList("P1");
            assertEquals(expected, result);
        }

        @Test
        void createSprintList_ProjectDoesNotExist_Exception() {
            ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Project does not exist."));

            ResponseEntity<Object> result = controller.createSprintList("P777");
            assertEquals(expected, result);
        }

        @Test
        void createSprintList_ZeroSprints_EmptyList() {

            ArrayList<OpenSprintOutputDTO> listOfSprintDTOs = new ArrayList<>();

            CollectionModel<OpenSprintOutputDTO> collectionModel = CollectionModel.of(listOfSprintDTOs);
            ResponseEntity<Object> expected = ResponseEntity.ok(collectionModel);

            ResponseEntity<Object> result = controller.createSprintList("P123");
            assertEquals(expected, result);
        }
    }
}
