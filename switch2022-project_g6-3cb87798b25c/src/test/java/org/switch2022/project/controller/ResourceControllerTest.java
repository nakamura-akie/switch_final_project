package org.switch2022.project.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.interfaces.*;
import org.switch2022.project.service.*;
import org.switch2022.project.utils.dto.*;
import org.switch2022.project.utils.exception.AbstractApiException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
class ResourceControllerTest {

    @Autowired
    ProjectRepository projectRepositoryFake;
    @Autowired
    AccountRepository accountRepositoryFake;
    @Autowired
    ResourceRepository resourceRepositoryFake;
    @Autowired
    CustomerRepository customerRepositoryFake;
    @Autowired
    ProfileRepository profileRepositoryFake;
    @Autowired
    ProjectTypologyRepository projectTypologyRepositoryFake;
    @Autowired
    BusinessSectorRepository businessSectorRepositoryFake;
    @Autowired
    ResourceController resourceController;
    @Autowired
    ResourceService resourceService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ProfileService profileService;
    @Autowired
    BusinessSectorService businessSectorService;
    @Autowired
    ProjectTypologyService projectTypologyService;

    @BeforeEach
    void init() {
        CustomerValueObjectDTO newCustomerDTO = new CustomerValueObjectDTO();
        newCustomerDTO.taxIdentificationNumber = new TaxIdentificationNumberPortugalImplementation("123456789");
        newCustomerDTO.customerName = new CustomerName("Customer Name");
        newCustomerDTO.country = new Country("portugal");
        this.customerService.createCustomer(newCustomerDTO);

        NewBusinessSectorDTO businessSectorDTO = new NewBusinessSectorDTO();
        businessSectorDTO.businessSectorName = new BusinessSectorName("Tech");
        businessSectorService.createBusinessSector(businessSectorDTO);

        NewProjectTypologyDTO newProjectTypologyDTO = new NewProjectTypologyDTO();
        newProjectTypologyDTO.projectTypologyName = new ProjectTypologyName("Time And Mat");
        projectTypologyService.createProjectTypology(newProjectTypologyDTO);

        NewProfileDTO newProfileDTO = new NewProfileDTO();
        newProfileDTO.profileName = new ProfileName("user");
        profileService.createProfile(newProfileDTO);

        NewProfileDTO managerProfileDTO = new NewProfileDTO();
        managerProfileDTO.profileName = new ProfileName("manager");
        profileService.createProfile(managerProfileDTO);

        NewProjectDTO firstProjectDTO = new NewProjectDTO(
                "P1",
                "Not Fast, Just Furious",
                "A very good project",
                "123456789",
                "Tech",
                "Time And Mat",
                2,
                2,
                1000.0,
                "2022-01-01",
                "2030-02-02"
        );
        projectService.createProject(firstProjectDTO);
        Map<ProjectStatus, LocalDate> statusHistoryMap = Map.of(ProjectStatus.PLANNED, LocalDate.of(2022, 1, 1));
        projectService.importProjectStatusHistory(new ProjectCode("P1"), statusHistoryMap);

        NewProjectDTO secondProjectDTO = new NewProjectDTO(
                "P2",
                "Not Fast, Just Furious -Second Edition",
                "A very good project - times two",
                "123456789",
                "Tech",
                "Time And Mat",
                2,
                2,
                1000.0,
                "2022-01-01",
                "2030-02-02"
        );
        projectService.createProject(secondProjectDTO);
        Map<ProjectStatus, LocalDate> secondStatusHistoryMap = Map.of(ProjectStatus.PLANNED, LocalDate.of(2022, 1, 1));
        projectService.importProjectStatusHistory(new ProjectCode("P2"), secondStatusHistoryMap);

        accountService.createAccount(
                "jane",
                "jane@jane.com",
                null,
                911234567
        );

        accountService.createAccount(
                "john",
                "john@john.com",
                null,
                911234568
        );

        accountService.createAccount(
                "mario",
                "mario@mario.com",
                null,
                911234567
        );

        accountService.createAccount(
                "rita",
                "rita@rita.com",
                null,
                911234567
        );
        accountService.changeProfileOfUserAccount("rita@rita.com", "manager");

        accountService.createAccount(
                "mary",
                "mary@mary.com",
                null,
                911234567
        );

        NewResourceDTO johnResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-02-01",
                "2022-03-02",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );
        resourceService.addResourceToProject(johnResourceDTO);

        NewResourceDTO marioResourceDTO = new NewResourceDTO(
                "mario@mario.com",
                "P1",
                "2022-02-01",
                "2022-03-02",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );
        resourceService.addResourceToProject(marioResourceDTO);

        NewResourceDTO maryResourceDTO = new NewResourceDTO(
                "mary@mary.com",
                "P1",
                "2022-02-01",
                "2022-03-02",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );
        resourceService.addResourceToProject(maryResourceDTO);
    }

    @AfterEach
    void tearDown() {
        resourceRepositoryFake.deleteAll();
        accountRepositoryFake.deleteAll();
        projectRepositoryFake.deleteAll();
        customerRepositoryFake.deleteAll();
        projectTypologyRepositoryFake.deleteAll();
        businessSectorRepositoryFake.deleteAll();
        profileRepositoryFake.deleteAll();

    }

    @Test
    void addResourceToProject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ResourceController(null));
        assertEquals("Resource Service cannot be null", exception.getMessage());
    }

    @Test
    void addResourceToProject_TeamMember_DifferentTeamMemberOverlap_Created() {
        String testEmail = "jane@jane.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-01-01";
        String testEndDate = "2022-02-02";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "TEAM_MEMBER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);


        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 2, 2)
                )));
    }

    @Test
    void addResourceToProject_ProductOwner_DifferentAccountProductOwnerOverlap_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "jane@jane.com",
                "P1",
                "2022-01-01",
                "2022-02-02",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("PRODUCT_OWNER already exists in time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ScrumMAster_DifferentAccountScrumMasterOverlap_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "jane@jane.com",
                "P1",
                "2022-01-01",
                "2022-02-02",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("SCRUM_MASTER already exists in time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_NotAUserProfile_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "rita@rita.com",
                "P1",
                "2022-03-03",
                "2022-04-04",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This account does not have a User Profile.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ScrumMaster_NewAccountHappyPath_Created() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "jane@jane.com",
                "P1",
                "2022-03-03",
                "2022-04-04",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testResourceDTO.getEmail().getEmailValue(),
                testResourceDTO.getProjectCode().getProjectCodeValue(),
                testResourceDTO.getStartDate().toString(),
                testResourceDTO.getEndDate().toString(),
                testResourceDTO.getProjectRole().name(),
                testResourceDTO.getPercentageOfAllocation().getPercentageValue(),
                testResourceDTO.getCostPerHour().getCostPerHourValue());

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testResourceDTO.getEmail().getEmailValue()),
                new ProjectCode(testResourceDTO.getProjectCode().getProjectCodeValue()),
                new TimePeriod(LocalDate.of(2022, 3, 3),
                        LocalDate.of(2022, 4, 4)
                )));
    }

    @Test
    void addResourceToProject_ProductOwner_NewAccountHappyPath_ResponseEntity() {
        String testEmail = "jane@jane.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-05-05";
        String testEndDate = "2022-06-06";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "PRODUCT_OWNER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2022, 5, 5),
                        LocalDate.of(2022, 6, 6)
                )));
    }

    @Test
    void addResourceToProject_ResourceAlreadyAllocatedInSameTimePeriod_StartDateBeforeAll_EndDateInBetween_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-01-01",
                "2022-02-02",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ScrumMaster_ResourceAlreadyAllocated_InSameTimePeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mary@mary.com",
                "P1",
                "2022-01-01",
                "2022-02-01",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceProductOwnerAlreadyAllocated_InSameTimePeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mario@mario.com",
                "P1",
                "2022-02-01",
                "2022-03-01",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceTeamMemberAlreadyAllocatedInDifferentTimePeriod_EverythingAfter_Success() {
        String testEmail = "john@john.com";
        String testProjectCode = "P1";
        String testStartDate = "2023-03-03";
        String testEndDate = "2023-04-04";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "TEAM_MEMBER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2023, 3, 3),
                        LocalDate.of(2023, 4, 4)
                )));
    }

    @Test
    void addResourceToProject_ResourceScrumMasterAlreadyAllocatedInDifferentTimePeriod_EverythingAfter_Success() {
        String testEmail = "mary@mary.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-03-03";
        String testEndDate = "2022-04-04";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "SCRUM_MASTER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2022, 3, 3),
                        LocalDate.of(2022, 4, 4)
                )));
    }

    @Test
    void addResourceToProject_ResourceProductOwnerAlreadyAllocatedInDifferentTimePeriod_EverythingAfter_Success() {
        String testEmail = "mario@mario.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-03-03";
        String testEndDate = "2022-04-04";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "PRODUCT_OWNER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2022, 3, 3),
                        LocalDate.of(2022, 4, 4)
                )));
    }

    @Test
    void addResourceToProject_ResourceTeamMemberAlreadyAllocatedInDifferentTimePeriod_EverythingBefore_Success() {
        String testEmail = "john@john.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-01-01";
        String testEndDate = "2022-01-31";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "TEAM_MEMBER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.parse(testStartDate),
                        LocalDate.parse(testEndDate)
                )));
    }

    @Test
    void addResourceToProject_ResourceScrumMasterAlreadyAllocatedInDifferentTimePeriod_EverythingBefore_Success() {
        String testEmail = "mary@mary.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-01-01";
        String testEndDate = "2022-01-31";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "SCRUM_MASTER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.parse(testStartDate),
                        LocalDate.parse(testEndDate)
                )));
    }

    @Test
    void addResourceToProject_ResourceProductOwnerAlreadyAllocatedInDifferentTimePeriod_EverythingBefore_Success() {
        String testEmail = "mario@mario.com";
        String testProjectCode = "P1";
        String testStartDate = "2022-01-01";
        String testEndDate = "2022-01-31";
        int testPercentageOfAllocation = 10;
        double testCostPerHour = 2.5;
        String testRole = "PRODUCT_OWNER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.parse(testStartDate),
                        LocalDate.parse(testEndDate)
                )));
    }


    @Test
    void addResourceToProject_ResourceTeamMemberAlreadyAllocated_InBetweenPeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-02-02",
                "2022-02-20",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceScrumMasterAlreadyAllocated_InBetweenPeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mary@mary.com",
                "P1",
                "2022-02-01",
                "2022-02-20",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceProductOwnerAlreadyAllocated_InBetweenPeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mario@mario.com",
                "P1",
                "2022-02-01",
                "2022-02-20",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }


    @Test
    void addResourceToProject_ResourceTeamMemberAlreadyAllocated_StartingBeforeAndEndingInExistingTimePeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-01-01",
                "2022-02-01",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceScrumMasterAlreadyAllocated_StartingBeforeAndEndingInExistingTimePeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mary@mary.com",
                "P1",
                "2022-01-01",
                "2022-02-01",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceProductOwnerAlreadyAllocated_StartingBeforeAndEndingInExistingTimePeriod_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mario@mario.com",
                "P1",
                "2022-01-01",
                "2022-02-01",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceTeamMemberAlreadyAllocated_StartingInExistingPeriodAndEndingAfter_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-01-08",
                "2022-02-20",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceScrumMasterAlreadyAllocated_StartingInExistingPeriodAndEndingAfter_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mary@mary.com",
                "P1",
                "2022-01-08",
                "2022-02-20",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ResourceProductOwnerAlreadyAllocated_StartingInExistingPeriodAndEndingAfter_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mario@mario.com",
                "P1",
                "2022-01-08",
                "2022-02-20",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("This resource is already exists in the given time period.", exception.getMessage());
    }

    @Test
    void addResourceToProject_TeamMemberAndProjectDoesNotExist_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P3",
                "2022-01-08",
                "2022-02-20",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ScrumMasterAndProjectDoesNotExist_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mary@mary.com",
                "P3",
                "2022-01-08",
                "2022-02-20",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ProductOwnerAndProjectDoesNotExist_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mario@mario.com",
                "P3",
                "2022-01-08",
                "2022-02-20",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_TeamMemberAndAccountDoesNotExist_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P3",
                "2022-01-08",
                "2022-02-20",
                "TEAM_MEMBER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Project does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ScrumMasterAndAccountDoesNotExist_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "maria@maria.com",
                "P1",
                "2022-01-08",
                "2022-02-20",
                "SCRUM_MASTER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Account does not exist.", exception.getMessage());
    }

    @Test
    void addResourceToProject_ProductOwnerAndAccountDoesNotExist_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "mario@maria.com",
                "P1",
                "2022-01-08",
                "2022-02-20",
                "PRODUCT_OWNER",
                "10",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Account does not exist.", exception.getMessage());
    }


    @Test
    void addResourceToProject_TeamMemberAndPercentageOfAllocationSuperiorToOneHundred_ThrowsException() {
        NewResourceDTO testResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-03-08",
                "2022-04-20",
                "TEAM_MEMBER",
                "95",
                "2.5"
        );

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.addResourceToProject(testResourceDTO));
        assertEquals("Allocation exceed 100%", exception.getMessage());
    }

    @Test
    void addResourceToDifferentProject_ScrumMasterAlreadyAllocatedWithTenPercentToDifferentProject_InSameTimePeriod_False() {
        String testEmail = "mary@mary.com";
        String testProjectCode = "P2";
        String testStartDate = "2022-01-01";
        String testEndDate = "2022-02-08";
        int testPercentageOfAllocation = 20;
        double testCostPerHour = 2.5;
        String testRole = "SCRUM_MASTER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 2, 8)
                )));
    }


    @Test
    void addResourceToDifferentProject_ProductOwnerAlreadyAllocatedWithTenPercentToDifferentProject_InSameTimePeriod_False() {
        String testEmail = "mario@mario.com";
        String testProjectCode = "P2";
        String testStartDate = "2022-01-01";
        String testEndDate = "2022-02-08";
        int testPercentageOfAllocation = 20;
        double testCostPerHour = 2.5;
        String testRole = "PRODUCT_OWNER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 2, 8)
                )));
    }

    @Test
    void addResourceToDifferentProject_TeamMemberAlreadyAllocatedWithTenPercentToDifferentProject_InSameTimePeriod_True() {
        String testEmail = "john@john.com";
        String testProjectCode = "P2";
        String testStartDate = "2022-02-01";
        String testEndDate = "2022-03-02";
        int testPercentageOfAllocation = 20;
        double testCostPerHour = 2.5;
        String testRole = "TEAM_MEMBER";

        NewResourceDTO testResourceDTO = new NewResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                Integer.toString(testPercentageOfAllocation),
                Double.toString(testCostPerHour)
        );

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                testEmail,
                testProjectCode,
                testStartDate,
                testEndDate,
                testRole,
                testPercentageOfAllocation,
                testCostPerHour);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        testResourceDTO.getEmail(),
                        testResourceDTO.getProjectCode(),
                        testResourceDTO.getStartDate(),
                        testResourceDTO.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(testResourceDTO)).withRel("resources")
        );

        ResponseEntity<Object> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result =
                resourceController.addResourceToProject(testResourceDTO);

        assertEquals(expected, result);

        resourceRepositoryFake.deleteById(new ResourceID(
                new Email(testEmail),
                new ProjectCode(testProjectCode),
                new TimePeriod(LocalDate.parse(testStartDate),
                        LocalDate.parse(testEndDate)
                )));
    }

    @Test
    void getCurrentResourcesInProjectList() {
        String projectCodeString = "P1";
        String dateString = "2022-02-01";

        ProjectCode projectCode = new ProjectCode(projectCodeString);
        LocalDate date = LocalDate.parse(dateString);

        ResourceDTO johnDTO = new ResourceDTO();
        johnDTO.email = "john@john.com";
        johnDTO.projectCode = "P1";
        johnDTO.projectRole = "TEAM_MEMBER";
        johnDTO.startDate = "2022-02-01";
        johnDTO.endDate = "2022-03-02";

        ResourceDTO marioDTO = new ResourceDTO();
        marioDTO.email = "mario@mario.com";
        marioDTO.projectCode = "P1";
        marioDTO.projectRole = "PRODUCT_OWNER";
        marioDTO.startDate = "2022-02-01";
        marioDTO.endDate = "2022-03-02";

        ResourceDTO maryDTO = new ResourceDTO();
        maryDTO.email = "mary@mary.com";
        maryDTO.projectCode = "P1";
        maryDTO.projectRole = "SCRUM_MASTER";
        maryDTO.startDate = "2022-02-01";
        maryDTO.endDate = "2022-03-02";

        List<ResourceDTO> resourceDTOList = Arrays.asList(johnDTO, marioDTO, maryDTO);

        List<EntityModel<ResourceDTO>> entityModelList = resourceDTOList.stream()
                .map(resourceDTO -> {
                    LocalDate startDate = LocalDate.parse(resourceDTO.startDate);
                    LocalDate endDate = LocalDate.parse(resourceDTO.endDate);
                    Email resourceEmail = new Email(resourceDTO.email);
                    ProjectCode resourceProjectCode = new ProjectCode(resourceDTO.projectCode);

                    return EntityModel.of(
                            resourceDTO,
                            linkTo(methodOn(ResourceController.class)
                                    .findResourceByID(resourceEmail, resourceProjectCode, startDate, endDate))
                                    .withSelfRel());
                })
                .collect(Collectors.toList());


        CollectionModel<?> expected =
                CollectionModel.of(
                        entityModelList,
                        linkTo(methodOn(ResourceController.class)
                                .getCurrentResourcesInProjectList(projectCode, date)).withRel(IanaLinkRelations.SELF));

        CollectionModel<?> result = resourceController.getCurrentResourcesInProjectList(projectCode, date);

        assertEquals(expected, result);

    }

    @Test
    void findResourceByID_ResourceExists_Equals() {
        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                "john@john.com",
                "P1",
                "2022-02-01",
                "2022-03-02",
                "TEAM_MEMBER",
                10,
                2.5);

        Email email = new Email("john@john.com");
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.parse("2022-02-01");
        LocalDate endDate = LocalDate.parse("2022-03-02");

        EntityModel<?> entityModel = EntityModel.of(
                outputResourceDTO,
                linkTo(methodOn(ResourceController.class)
                        .findResourceByID(email, projectCode, startDate, endDate)).withSelfRel(),
                linkTo(ResourceController.class).slash("resources").withRel("resources")
        );

        ResponseEntity<Object> expected = ResponseEntity.ok(entityModel);
        ResponseEntity<Object> result = resourceController.findResourceByID(email, projectCode, startDate, endDate);

        assertEquals(expected, result);
    }

    @Test
    void findResourceByID_ResourceDoesNotExist_ThrowsException() {
        Email email = new Email("john@john.com");
        ProjectCode projectCode = new ProjectCode("P5");
        LocalDate startDate = LocalDate.parse("2022-01-01");
        LocalDate endDate = LocalDate.parse("2022-02-08");

        Exception exception = assertThrows(AbstractApiException.class, () ->
                resourceController.findResourceByID(email, projectCode, startDate, endDate));
        assertEquals("ResourceID does not exist.", exception.getMessage());
    }
}

