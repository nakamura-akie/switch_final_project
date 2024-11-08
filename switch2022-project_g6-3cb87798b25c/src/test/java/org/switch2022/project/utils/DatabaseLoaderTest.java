package org.switch2022.project.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.repository.jpa.ProjectRepositoryJPA;
import org.switch2022.project.service.*;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.NewResourceDTO;
import org.switch2022.project.utils.dto.NewSprintDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DatabaseLoaderTest {

    private static DatabaseLoader databaseLoader;
    private static ProfileService profileService;
    private static ProjectTypologyService projectTypologyService;
    private static CustomerService customerService;
    private static BusinessSectorService businessSectorService;
    private static ProjectService projectService;
    private static AccountService accountService;
    private static UserStoryService userStoryService;
    private static SprintService sprintService;
    private static ResourceService resourceService;
    private static ProjectRepositoryJPA projectRepository;

    private static final String PORTUGAL = "portugal";
    private static final Integer TWENTY_TWO = 2022;
    private static final Integer TWENTY_THREE = 2023;
    private static final Integer TWENTY_FOUR = 2024;
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;
    private static final Integer FOUR = 4;
    private static final Integer FIVE = 5;
    private static final Integer SIX = 6;
    private static final Integer SEVEN = 7;
    private static final Integer EIGHT = 8;
    private static final Integer NINE = 9;
    private static final Integer TEN = 10;
    private static final Integer TWELVE = 12;
    private static final Integer FOURTEEN = 14;
    private static final Integer FIFTEEN = 15;
    private static final Integer EIGHTEEN = 18;
    private static final Integer NINETEEN = 19;
    private static final Integer TWENTY = 20;
    private static final Integer NUMBER_TWENTY_ONE = 21;
    private static final Integer NUMBER_TWENTY_TWO = 22;
    private static final Integer NUMBER_TWENTY_FOUR = 24;
    private static final Integer NUMBER_TWENTY_FIVE = 25;
    private static final Integer NUMBER_TWENTY_SIX = 26;
    private static final Integer TWENTY_NINE = 29;
    private static final Integer THIRTY = 30;
    private static final Integer THIRTY_ONE = 31;
    private static final Integer THIRTY_FIVE = 35;
    private static final Integer FORTY_TWO = 42;
    private static final Integer ONE_HUNDRED = 100;
    private static final Double BUDGET_ONE = 150000.00;
    private static final Double BUDGET_TWO = 350000.00;
    private static final Double BUDGET_THREE = 750000.00;

    @BeforeAll
    static void init() {
        profileService = mock(ProfileService.class);
        projectTypologyService = mock(ProjectTypologyService.class);
        customerService = mock(CustomerService.class);
        businessSectorService = mock(BusinessSectorService.class);
        projectService = mock(ProjectService.class);
        accountService = mock(AccountService.class);
        userStoryService = mock(UserStoryService.class);
        sprintService = mock(SprintService.class);
        resourceService = mock(ResourceService.class);
        projectRepository = mock(ProjectRepositoryJPA.class);
        databaseLoader = new DatabaseLoader(profileService,
                projectTypologyService, customerService, businessSectorService, projectService,
                accountService, userStoryService, sprintService, resourceService);
    }

    @AfterAll
    static void tearDown() {
        databaseLoader = null;
        profileService = null;
        projectTypologyService = null;
    }

    @Test
    void constructor_NullProfileService_ThrowsException() {
        String expected = "Profile Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(null, projectTypologyService, customerService, businessSectorService,
                    projectService, accountService, userStoryService, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullProjectTypologyService_ThrowsException() {
        String expected = "Project Typology Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, null, customerService, businessSectorService,
                    projectService, accountService, userStoryService, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullCustomerService_ThrowsException() {
        String expected = "Customer Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, null, businessSectorService,
                    projectService, accountService, userStoryService, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullBusinessSectorService_ThrowsException() {
        String expected = "Business Sector Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, customerService, null,
                    projectService, accountService, userStoryService, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullProjectService_ThrowsException() {
        String expected = "Project Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, customerService, businessSectorService,
                    null, accountService, userStoryService, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullAccountService_ThrowsException() {
        String expected = "Account Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, customerService, businessSectorService,
                    projectService, null, userStoryService, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullUserStoryService_ThrowsException() {
        String expected = "User Story Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, customerService, businessSectorService,
                    projectService, accountService, null, sprintService, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullSprintService_ThrowsException() {
        String expected = "Sprint Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, customerService, businessSectorService,
                    projectService, accountService, userStoryService, null, resourceService);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullResourceService_ThrowsException() {
        String expected = "Resource Service cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new DatabaseLoader(profileService, projectTypologyService, customerService, businessSectorService,
                    projectService, accountService, userStoryService, sprintService, null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void addData() throws Exception {

        ProjectCode projectCode = new ProjectCode("A1");
        ProjectCode projectCodeTwo = new ProjectCode("A2");
        
        //customer
        
        databaseLoader.run();
        CustomerValueObjectDTO customerOneDTO = new CustomerValueObjectDTO();
        customerOneDTO.customerName = new CustomerName("XPTO, SA");
        customerOneDTO.country = new Country("portugal");
        customerOneDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("211267490");

        //initial profiles
        verify(profileService).createDefaultProfile("administrator");
        verify(profileService).createDefaultProfile("user");
        verify(profileService).createDefaultProfile("manager");
        verify(profileService).createDefaultProfile("director");
        
        //initial project typologies
        
        verify(projectTypologyService).createDefaultProjectTypology("Time and Materials");
        verify(projectTypologyService).createDefaultProjectTypology("Fixed-Cost");

        

        //add resource

        NewResourceDTO resourceDTO = new NewResourceDTO(
                "ms@mymail.com",
                "A1",
                "2022-01-04",
                "2022-07-31",
                "SCRUM_MASTER",
                "30",
                "25"
        );

        verify(resourceService).addResourceToProject(resourceDTO);

        NewResourceDTO resourceDTOTwo = new NewResourceDTO(
                "tc@mymail.com",
                "A1",
                "2022-02-01",
                "2022-07-31",
                "PROJECT_MANAGER",
                "20",
                "35"
        );

        verify(resourceService).addResourceToProject(resourceDTOTwo);

        NewResourceDTO resourceDTOThree = new NewResourceDTO(
                "js@mymail.com",
                "A1",
                "2022-01-03",
                "2022-07-31",
                "PRODUCT_OWNER",
                "20",
                "25"
        );

        NewResourceDTO resourceDTOFour = new NewResourceDTO(
                "ms@mymail.com",
                "A1",
                "2022-01-04",
                "2022-07-31",
                "SCRUM_MASTER",
                "30",
                "25"
        );

        NewResourceDTO resourceDTOFive = new NewResourceDTO(
                "xf@mymail.com",
                "A1",
                "2022-01-05",
                "2022-07-31",
                "TEAM_MEMBER",
                "100",
                "20"
        );

        verify(resourceService).addResourceToProject(resourceDTOFive);

        NewResourceDTO resourceDTOSix = new NewResourceDTO(
                "nel.m@mymail.com",
                "A1",
                "2022-01-07",
                "2022-07-31",
                "TEAM_MEMBER",
                "100",
                "20"
        );

        verify(resourceService).addResourceToProject(resourceDTOSix);

        NewResourceDTO resourceDTOSeven = new NewResourceDTO(
                "zb@mymail.com",
                "A1",
                "2022-01-08",
                "2022-06-20",
                "TEAM_MEMBER",
                "100",
                "20"
        );

        verify(resourceService).addResourceToProject(resourceDTOSeven);

        NewResourceDTO resourceDTOEight = new NewResourceDTO(
                "to.f@mymail.com",
                "A1",
                "2022-01-08",
                "2022-06-20",
                "TEAM_MEMBER",
                "100",
                "20");

        NewResourceDTO resourceDTONine = new NewResourceDTO(
                "qb@mymail.com",
                "A2",
                "2023-05-15",
                "2024-04-29",
                "PROJECT_MANAGER",
                "20",
                "42"
        );

        verify(resourceService).addResourceToProject(resourceDTONine);

        NewResourceDTO resourceDTOTen = new NewResourceDTO(
                "tg@mymail.com",
                "A2",
                "2023-05-15",
                "2024-04-29",
                "PRODUCT_OWNER",
                "20",
                "30"
        );

        verify(resourceService).addResourceToProject(resourceDTOTen);

        NewResourceDTO resourceDTOEleven = new NewResourceDTO(
                "zm@mymail.com",
                "A2",
                "2023-05-31",
                "2024-04-29",
                "TEAM_MEMBER",
                "100",
                "20"
        );

        verify(resourceService).addResourceToProject(resourceDTOEleven);

        NewResourceDTO resourceDTOTwelve = new NewResourceDTO(
                "as@mymail.com",
                "A2",
                "2023-05-31",
                "2024-04-29",
                "TEAM_MEMBER",
                "100",
                "18"
        );

        verify(resourceService).addResourceToProject(resourceDTOTwelve);


        // add sprint
        verify(customerService).createCustomer(customerOneDTO);

        NewSprintDTO sprintDTO = new NewSprintDTO();
        sprintDTO.projectCode = new ProjectCode("A1");
        sprintDTO.startDate = LocalDate.of(2022, 3, 22);

        verify(sprintService).createSprint(sprintDTO);


        //change projectStatus

        verify(projectService).changeProjectStatus(new ProjectCode("A1"), ProjectStatus.CLOSED);
        verify(projectService).changeProjectStatus(new ProjectCode("A2"), ProjectStatus.ELABORATION);
        verify(projectService).changeProjectStatus(new ProjectCode("666"), ProjectStatus.INCEPTION);

        //Add accounts

        final Integer JOAO_SILVA_PHONE_NUMBER = 915_879_652;
        verify(accountService).createAccount("João Silva", "js@mymail.com", null,
                JOAO_SILVA_PHONE_NUMBER);

        final Integer MANEL_COSTA_PHONE_NUMBER = 926_650_520;
        verify(accountService).createAccount("Manel Costa", "ms@mymail.com", null,
                MANEL_COSTA_PHONE_NUMBER);

        final Integer XICO_FERREIRA_PHONE_NUMBER = 963_650_532;
        verify(accountService).createAccount("Xico Ferreira", "xf@mymail.com", null,
                XICO_FERREIRA_PHONE_NUMBER);

        final Integer TIAGO_CANCADO_PHONE_NUMBER = 263_650_345;
        verify(accountService).createAccount("Tiago Cancado", "tc@mymail.com", null,
                TIAGO_CANCADO_PHONE_NUMBER);

        final Integer URBINO_DAS_URZES_PHONE_NUMBER = 962_547_891;
        verify(accountService).createAccount("Urbino das Urzes", "udu@mymail.com", null,
                URBINO_DAS_URZES_PHONE_NUMBER);

        final Integer ZE_DA_ESQUINA_PHONE_NUMBER = 212_349_016;
        verify(accountService).changeProfileOfUserAccount("udu@mymail.com", "administrator");
        verify(accountService).createAccount("Zé da Esquina", "ze@mymail.com", null,
                ZE_DA_ESQUINA_PHONE_NUMBER);

        final Integer NEL_MOLEIRO_PHONE_NUMBER = 930_123_456;
        verify(accountService).changeProfileOfUserAccount("ze@mymail.com", "director");
        verify(accountService).createAccount("Nel Moleiro", "nel.m@mymail.com", null,
                NEL_MOLEIRO_PHONE_NUMBER);

        final Integer ZE_DO_BENTO_PHONE_NUMBER = 921_458_791;
        verify(accountService).createAccount("Zé do Bento", "zb@mymail.com", null,
                ZE_DO_BENTO_PHONE_NUMBER);

        final Integer TO_FARRULO_PHONE_NUMBER = 921_458_795;
        verify(accountService).createAccount("Tó Farrulo", "to.f@mymail.com", null,
                TO_FARRULO_PHONE_NUMBER);

        final Integer TINO_DAS_CRUZES_PHONE_NUMBER = 921_458_799;
        verify(accountService).createAccount("Tino das Cruzes", "tdc@mymail.com", null,
                TINO_DAS_CRUZES_PHONE_NUMBER);

        final Integer QUIM_BARREIROS_PHONE_NUMBER = 921_458_803;
        verify(accountService).createAccount("Quim Barreiros", "qb@mymail.com", null,
                QUIM_BARREIROS_PHONE_NUMBER);

        final Integer TIAGO_GERINGONCA_PHONE_NUMBER = 921_458_807;
        verify(accountService).createAccount("Tiago Geringonça", "tg@mymail.com", null,
                TIAGO_GERINGONCA_PHONE_NUMBER);

        final Integer ZE_MANEL_PHONE_NUMBER = 921_458_811;
        verify(accountService).createAccount("Zé Manel", "zm@mymail.com", null,
                ZE_MANEL_PHONE_NUMBER);

        final Integer ANTONIO_SILVA_PHONE_NUMBER = 921_458815;
        verify(accountService).createAccount("António Silva", "as@mymail.com", null,
                ANTONIO_SILVA_PHONE_NUMBER);
        
        // change userstory status

     
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US01"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US02"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US03"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US04"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US05"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US06"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.CANCELLED,
                new UserStoryCode("US07"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US08"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US09"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.CANCELLED,
                new UserStoryCode("US10"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US11"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US12"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US13"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US14"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US15"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US16"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US17"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US18"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US19"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US20"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US21"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US22"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US23"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US24"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US25"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US26"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US27"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US28"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US29"), projectCode);
        verify(userStoryService).changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode("US30"), projectCode);



    }
}