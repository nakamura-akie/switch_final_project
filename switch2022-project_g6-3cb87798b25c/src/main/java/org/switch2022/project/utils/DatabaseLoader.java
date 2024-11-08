package org.switch2022.project.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.service.*;
import org.switch2022.project.utils.dto.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * The DatabaseLoader class is used to populate the persistence database every time the
 * application is initialized.
 * It implements the CommandLineRunner interface to execute the data loading logic.
 */
@Component
@org.springframework.context.annotation.Profile("!test && !integration_test")
public class DatabaseLoader implements CommandLineRunner {

    private static final String FIXED_COST = "Fixed-Cost";
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
    private final ProfileService profileService;
    private final ProjectTypologyService projectTypologyService;
    private final CustomerService customerService;
    private final BusinessSectorService businessSectorService;
    private final ProjectService projectService;
    private final AccountService accountService;
    private final UserStoryService userStoryService;
    private final SprintService sprintService;
    private final ResourceService resourceService;

    /**
     * Initializes a DatabaseLoader object.
     * It uses @Autowire to inject the various services required.
     *
     * @param profileService         the profile service that will be used to create profiles
     * @param projectTypologyService the project typology service that will be used to create project typologies
     * @param customerService        the customer service that will be used to create customers
     * @param businessSectorService  the business sector service that will be used to create business sectors
     * @param projectService         the project service that will be used to create projects
     * @param accountService         the account service that will be used to create accounts
     * @param userStoryService       the user story service that will be used to create user stories
     * @param sprintService          the sprint service that will be used to create sprints
     * @param resourceService        the resource service that will be used to create resources
     * @throws IllegalArgumentException if any of the parameters are null
     */
    @Autowired
    public DatabaseLoader(ProfileService profileService,
                          ProjectTypologyService projectTypologyService,
                          CustomerService customerService,
                          BusinessSectorService businessSectorService,
                          ProjectService projectService,
                          AccountService accountService,
                          UserStoryService userStoryService,
                          SprintService sprintService,
                          ResourceService resourceService
    ) {
        if (profileService == null) {
            throw new IllegalArgumentException("Profile Service cannot be null");
        }
        if (projectTypologyService == null) {
            throw new IllegalArgumentException("Project Typology Service cannot be null");
        }
        if (businessSectorService == null) {
            throw new IllegalArgumentException("Business Sector Service cannot be null");
        }
        if (customerService == null) {
            throw new IllegalArgumentException("Customer Service cannot be null");
        }
        if (projectService == null) {
            throw new IllegalArgumentException("Project Service cannot be null");
        }
        if (accountService == null) {
            throw new IllegalArgumentException("Account Service cannot be null");
        }
        if (userStoryService == null) {
            throw new IllegalArgumentException("User Story Service cannot be null");
        }
        if (sprintService == null) {
            throw new IllegalArgumentException("Sprint Service cannot be null");
        }
        if (resourceService == null) {
            throw new IllegalArgumentException("Resource Service cannot be null");
        }
        this.profileService = profileService;
        this.projectTypologyService = projectTypologyService;
        this.customerService = customerService;
        this.businessSectorService = businessSectorService;
        this.projectService = projectService;
        this.accountService = accountService;
        this.userStoryService = userStoryService;
        this.sprintService = sprintService;
        this.resourceService = resourceService;
    }

    /**
     * The run method from the CommandLineRunner interface, responsible for executing the data loading logic.
     *
     * @param args the command line arguments
     * @throws Exception if an exception occurs during the data loading process
     */
    @Override
    public void run(String... args) throws Exception {
        createInitialProfiles();
        createInitialProjectTypologies();

        addCustomers();
        addBusinessSectors();
        addProjects();
        addAccounts();
        addUserStories();
        addSprints();
        addResources();
        changeProjectStatus();
    }

    private void createInitialProfiles() {
        profileService.createDefaultProfile("administrator");
        profileService.createDefaultProfile("user");
        profileService.createDefaultProfile("manager");
        profileService.createDefaultProfile("director");
    }

    private void createInitialProjectTypologies() {
        projectTypologyService.createDefaultProjectTypology("Time and Materials");
        projectTypologyService.createDefaultProjectTypology(FIXED_COST);
    }

    private void addResources() {
        ProjectCode projectCode = new ProjectCode("A1");

        addResource("tc@mymail.com", ProjectRole.PROJECT_MANAGER,
                LocalDate.of(TWENTY_TWO, TWO, ONE), LocalDate.of(TWENTY_TWO, SEVEN, THIRTY_ONE),
                THIRTY_FIVE, projectCode, TWENTY);

        addResource("js@mymail.com", ProjectRole.PRODUCT_OWNER,
                LocalDate.of(TWENTY_TWO, ONE, THREE), LocalDate.of(TWENTY_TWO, SEVEN, THIRTY_ONE),
                NUMBER_TWENTY_FIVE, projectCode, TWENTY);

        addResource("ms@mymail.com", ProjectRole.SCRUM_MASTER,
                LocalDate.of(TWENTY_TWO, ONE, FOUR), LocalDate.of(TWENTY_TWO, SEVEN, THIRTY_ONE),
                NUMBER_TWENTY_FIVE, projectCode, THIRTY);

        addResource("xf@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_TWO, ONE, FIVE), LocalDate.of(TWENTY_TWO, SEVEN, THIRTY_ONE),
                TWENTY, projectCode, ONE_HUNDRED);

        addResource("nel.m@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_TWO, ONE, SEVEN), LocalDate.of(TWENTY_TWO, SEVEN, THIRTY_ONE),
                TWENTY, projectCode, ONE_HUNDRED);

        addResource("zb@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_TWO, ONE, EIGHT), LocalDate.of(TWENTY_TWO, SIX, TWENTY),
                TWENTY, projectCode, ONE_HUNDRED);

        addResource("to.f@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_TWO, ONE, EIGHT), LocalDate.of(TWENTY_TWO, SIX, TWENTY),
                TWENTY, projectCode, ONE_HUNDRED);

        addResource("tdc@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_TWO, ONE, TEN), LocalDate.of(TWENTY_TWO, SIX, TWENTY),
                TWENTY, projectCode, ONE_HUNDRED);


        // project A2

        ProjectCode projectCodeTwo = new ProjectCode("A2");


        addResource("qb@mymail.com", ProjectRole.PROJECT_MANAGER,
                LocalDate.of(TWENTY_THREE, FIVE, FIFTEEN), LocalDate.of(TWENTY_FOUR, FOUR, TWENTY_NINE),
                FORTY_TWO, projectCodeTwo, TWENTY);

        addResource("tg@mymail.com", ProjectRole.PRODUCT_OWNER,
                LocalDate.of(TWENTY_THREE, FIVE, FIFTEEN), LocalDate.of(TWENTY_FOUR, FOUR, TWENTY_NINE),
                THIRTY, projectCodeTwo, TWENTY);

        addResource("zm@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_THREE, FIVE, THIRTY_ONE), LocalDate.of(TWENTY_FOUR, FOUR, TWENTY_NINE),
                TWENTY, projectCodeTwo, ONE_HUNDRED);

        addResource("as@mymail.com", ProjectRole.TEAM_MEMBER,
                LocalDate.of(TWENTY_THREE, FIVE, THIRTY_ONE), LocalDate.of(TWENTY_FOUR, FOUR, TWENTY_NINE),
                EIGHTEEN, projectCodeTwo, ONE_HUNDRED);

    }

    private void changeProjectStatus() {

        projectService.changeProjectStatus(new ProjectCode("A1"), ProjectStatus.CLOSED);
        projectService.changeProjectStatus(new ProjectCode("A2"), ProjectStatus.ELABORATION);
        projectService.changeProjectStatus(new ProjectCode("666"), ProjectStatus.INCEPTION);
    }

    private void addSprints() {
        ProjectCode projectCode = new ProjectCode("A1");

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, THREE, NUMBER_TWENTY_TWO));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, FOUR, FIVE));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, FOUR, NUMBER_TWENTY_SIX));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, FIVE, TEN));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, FIVE, NUMBER_TWENTY_FOUR));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, SIX, SEVEN));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, SIX, NUMBER_TWENTY_ONE));

        createSprint(projectCode, LocalDate.of(TWENTY_TWO, SEVEN, NINETEEN));

        //add user stories to sprint backlog

        SprintCode sprintCodeOne = new SprintCode("S1");
        changeSprintStatus(projectCode, sprintCodeOne, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeOne, "US01");
        addUserStoryToSprintBacklog(projectCode, sprintCodeOne, "US02");
        addUserStoryToSprintBacklog(projectCode, sprintCodeOne, "US03");
        addUserStoryToSprintBacklog(projectCode, sprintCodeOne, "US04");
        addUserStoryToSprintBacklog(projectCode, sprintCodeOne, "US05");

        changeSprintStatus(projectCode, sprintCodeOne, SprintStatus.CLOSED);


        //Sprint 2 Project A1
        SprintCode sprintCodeTwo = new SprintCode("S2");

        changeSprintStatus(projectCode, sprintCodeTwo, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeTwo, "US06");
        addUserStoryToSprintBacklog(projectCode, sprintCodeTwo, "US08");
        addUserStoryToSprintBacklog(projectCode, sprintCodeTwo, "US09");

        changeSprintStatus(projectCode, sprintCodeTwo, SprintStatus.CLOSED);

        // Sprint 3

        SprintCode sprintCodeThree = new SprintCode("S3");

        changeSprintStatus(projectCode, sprintCodeThree, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeThree, "US08");
        addUserStoryToSprintBacklog(projectCode, sprintCodeThree, "US09");
        addUserStoryToSprintBacklog(projectCode, sprintCodeThree, "US11");
        addUserStoryToSprintBacklog(projectCode, sprintCodeThree, "US12");
        addUserStoryToSprintBacklog(projectCode, sprintCodeThree, "US13");

        changeSprintStatus(projectCode, sprintCodeThree, SprintStatus.CLOSED);

        //Sprint 4 14,15,20,21,25,26

        SprintCode sprintCodeFour = new SprintCode("S4");

        changeSprintStatus(projectCode, sprintCodeFour, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeFour, "US14");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFour, "US15");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFour, "US20");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFour, "US21");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFour, "US25");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFour, "US26");

        changeSprintStatus(projectCode, sprintCodeFour, SprintStatus.CLOSED);

        //Sprint 5 16,17,18,19

        SprintCode sprintCodeFive = new SprintCode("S5");

        changeSprintStatus(projectCode, sprintCodeFive, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeFive, "US16");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFive, "US17");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFive, "US18");
        addUserStoryToSprintBacklog(projectCode, sprintCodeFive, "US19");

        changeSprintStatus(projectCode, sprintCodeFive, SprintStatus.CLOSED);

        //Sprint 6 22,23,24,26

        SprintCode sprintCodeSix = new SprintCode("S6");

        changeSprintStatus(projectCode, sprintCodeSix, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeSix, "US22");
        addUserStoryToSprintBacklog(projectCode, sprintCodeSix, "US23");
        addUserStoryToSprintBacklog(projectCode, sprintCodeSix, "US26");

        changeSprintStatus(projectCode, sprintCodeSix, SprintStatus.CLOSED);

        //Sprint 7 27,28,29,30

        SprintCode sprintCodeSeven = new SprintCode("S7");

        changeSprintStatus(projectCode, sprintCodeSeven, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeSeven, "US27");
        addUserStoryToSprintBacklog(projectCode, sprintCodeSeven, "US28");
        addUserStoryToSprintBacklog(projectCode, sprintCodeSeven, "US29");
        addUserStoryToSprintBacklog(projectCode, sprintCodeSeven, "US30");

        changeSprintStatus(projectCode, sprintCodeSeven, SprintStatus.CLOSED);


        //Sprint 8 28,29,30

        SprintCode sprintCodeEight = new SprintCode("S8");

        changeSprintStatus(projectCode, sprintCodeEight, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCode, sprintCodeEight, "US28");
        addUserStoryToSprintBacklog(projectCode, sprintCodeEight, "US29");
        addUserStoryToSprintBacklog(projectCode, sprintCodeEight, "US30");

        changeSprintStatus(projectCode, sprintCodeEight, SprintStatus.CLOSED);


        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US01"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US02"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US03"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US04"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US05"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US06"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.CANCELLED, new UserStoryCode("US07"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US08"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US09"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.CANCELLED, new UserStoryCode("US10"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US11"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US12"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US13"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US14"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US15"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US16"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US17"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US18"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US19"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US20"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US21"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US22"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US23"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US24"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US25"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US26"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US27"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US28"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US29"), projectCode);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED, new UserStoryCode("US30"), projectCode);


        // Project A2

        ProjectCode projectCodeTwo = new ProjectCode("A2");

        createSprint(projectCodeTwo, LocalDate.of(TWENTY_THREE, FIVE, ONE));
        createSprint(projectCodeTwo, LocalDate.of(TWENTY_THREE, FIVE, NUMBER_TWENTY_TWO));
        createSprint(projectCodeTwo, LocalDate.of(TWENTY_THREE, SIX, TWELVE));
        createSprint(projectCodeTwo, LocalDate.of(TWENTY_THREE, SEVEN, THREE));
        createSprint(projectCodeTwo, LocalDate.of(TWENTY_THREE, SEVEN, NUMBER_TWENTY_FOUR));
        createSprint(projectCodeTwo, LocalDate.of(TWENTY_THREE, EIGHT, FOURTEEN));


        // UserStory Codes
        String userStoryCodeOne = "US001";
        String userStoryCodeTwo = "US002";
        String userStoryCodeThree = "US003";
        String userStoryCodeFour = "US004";
        String userStoryCodeFive = "US005";
        String userStoryCodeSix = "US006";
        String userStoryCodeSeven = "US007";
        String userStoryCodeEight = "US008";
        String userStoryCodeNine = "US009";
        String userStoryCodeTen = "US010";
        String userStoryCodeEleven = "US011";
        String userStoryCodeTwelve = "US012";
        String userStoryCodeThirteen = "US013";
        String userStoryCodeFourteen = "US014";
        String userStoryCodeFifteen = "US015";


        // S1
        changeSprintStatus(projectCodeTwo, sprintCodeOne, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeOne, userStoryCodeOne);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeOne, userStoryCodeTwo);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeOne, userStoryCodeThree);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeOne, userStoryCodeFour);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeOne, userStoryCodeFive);

        changeSprintStatus(projectCodeTwo, sprintCodeOne, SprintStatus.CLOSED);

        // S2
        changeSprintStatus(projectCodeTwo, sprintCodeTwo, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeTwo, userStoryCodeSix);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeTwo, userStoryCodeSeven);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeTwo, userStoryCodeEight);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeTwo, userStoryCodeNine);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeTwo, userStoryCodeTen);

        changeSprintStatus(projectCodeTwo, sprintCodeTwo, SprintStatus.CLOSED);

        //S3
        changeSprintStatus(projectCodeTwo, sprintCodeThree, SprintStatus.OPENED);

        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeThree, userStoryCodeEleven);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeThree, userStoryCodeTwelve);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeThree, userStoryCodeThirteen);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeThree, userStoryCodeFourteen);
        addUserStoryToSprintBacklog(projectCodeTwo, sprintCodeThree, userStoryCodeFifteen);

        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeOne), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeTwo), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeThree), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeFour), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeFive), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeSix), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeSeven), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeEight), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeNine), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeTen), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.RUNNING,
                new UserStoryCode(userStoryCodeEleven), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.RUNNING,
                new UserStoryCode(userStoryCodeThirteen), projectCodeTwo);
        userStoryService.changeUserStoryStatus(UserStoryStatus.FINISHED,
                new UserStoryCode(userStoryCodeFifteen), projectCodeTwo);

    }

    private void addUserStories() {
        ProjectCode projectCode = new ProjectCode("A1");

        addUSerStory(projectCode, "US03", "Dummy 03");
        addUSerStory(projectCode, "US02", "Dummy 02");
        addUSerStory(projectCode, "US01", "Dummy 01");
        addUSerStory(projectCode, "US04", "Dummy 04");
        addUSerStory(projectCode, "US05", "Dummy 05");
        addUSerStory(projectCode, "US06", "Dummy 06");
        addUSerStory(projectCode, "US07", "Dummy 07");
        addUSerStory(projectCode, "US08", "Dummy 08");
        addUSerStory(projectCode, "US09", "Dummy 09");
        addUSerStory(projectCode, "US10", "Dummy 10");
        addUSerStory(projectCode, "US11", "Dummy 11");
        addUSerStory(projectCode, "US12", "Dummy 12");
        addUSerStory(projectCode, "US13", "Dummy 13");
        addUSerStory(projectCode, "US14", "Dummy 14");
        addUSerStory(projectCode, "US15", "Dummy 15");
        addUSerStory(projectCode, "US16", "Dummy 16");
        addUSerStory(projectCode, "US17", "Dummy 17");
        addUSerStory(projectCode, "US18", "Dummy 18");
        addUSerStory(projectCode, "US19", "Dummy 19");
        addUSerStory(projectCode, "US20", "Dummy 20");
        addUSerStory(projectCode, "US21", "Dummy 21");
        addUSerStory(projectCode, "US22", "Dummy 22");
        addUSerStory(projectCode, "US23", "Dummy 23");
        addUSerStory(projectCode, "US24", "Dummy 24");
        addUSerStory(projectCode, "US25", "Dummy 25");
        addUSerStory(projectCode, "US26", "Dummy 26");
        addUSerStory(projectCode, "US27", "Dummy 27");
        addUSerStory(projectCode, "US28", "Dummy 28");
        addUSerStory(projectCode, "US29", "Dummy 29");
        addUSerStory(projectCode, "US30", "Dummy 30");

        //project A2

        ProjectCode projectCodeTwo = new ProjectCode("A2");

        addUSerStory(projectCodeTwo, "US001", " A2 dummy 001");
        addUSerStory(projectCodeTwo, "US002", " A2 dummy 002");
        addUSerStory(projectCodeTwo, "US003", " A2 dummy 003");
        addUSerStory(projectCodeTwo, "US004", " A2 dummy 004");
        addUSerStory(projectCodeTwo, "US005", " A2 dummy 005");
        addUSerStory(projectCodeTwo, "US006", " A2 dummy 006");
        addUSerStory(projectCodeTwo, "US007", " A2 dummy 007");
        addUSerStory(projectCodeTwo, "US008", " A2 dummy 008");
        addUSerStory(projectCodeTwo, "US009", " A2 dummy 009");
        addUSerStory(projectCodeTwo, "US010", " A2 dummy 010");
        addUSerStory(projectCodeTwo, "US011", " A2 dummy 011");
        addUSerStory(projectCodeTwo, "US012", " A2 dummy 012");
        addUSerStory(projectCodeTwo, "US013", " A2 dummy 013");
        addUSerStory(projectCodeTwo, "US014", " A2 dummy 014");
        addUSerStory(projectCodeTwo, "US015", " A2 dummy 015");
        addUSerStory(projectCodeTwo, "US016", " A2 dummy 016");
        addUSerStory(projectCodeTwo, "US017", " A2 dummy 017");
        addUSerStory(projectCodeTwo, "US018", " A2 dummy 018");
        addUSerStory(projectCodeTwo, "US019", " A2 dummy 019");
        addUSerStory(projectCodeTwo, "US020", " A2 dummy 020");
        addUSerStory(projectCodeTwo, "US021", " A2 dummy 021");
        addUSerStory(projectCodeTwo, "US022", " A2 dummy 022");
        addUSerStory(projectCodeTwo, "US023", " A2 dummy 023");
        addUSerStory(projectCodeTwo, "US024", " A2 dummy 024");
        addUSerStory(projectCodeTwo, "US025", " A2 dummy 025");
        addUSerStory(projectCodeTwo, "US026", " A2 dummy 026");
        addUSerStory(projectCodeTwo, "US027", " A2 dummy 027");
        addUSerStory(projectCodeTwo, "US028", " A2 dummy 028");
        addUSerStory(projectCodeTwo, "US029", " A2 dummy 029");
        addUSerStory(projectCodeTwo, "US030", " A2 dummy 030");
        addUSerStory(projectCodeTwo, "US031", " A2 dummy 031");
        addUSerStory(projectCodeTwo, "US032", " A2 dummy 032");
        addUSerStory(projectCodeTwo, "US033", " A2 dummy 033");
        addUSerStory(projectCodeTwo, "US034", " A2 dummy 034");
        addUSerStory(projectCodeTwo, "US035", " A2 dummy 035");
        addUSerStory(projectCodeTwo, "US036", " A2 dummy 036");
        addUSerStory(projectCodeTwo, "US037", " A2 dummy 037");
        addUSerStory(projectCodeTwo, "US038", " A2 dummy 038");
        addUSerStory(projectCodeTwo, "US039", " A2 dummy 039");
        addUSerStory(projectCodeTwo, "US040", " A2 dummy 040");
    }

    private void addAccounts() {
        final Integer JOAO_SILVA_PHONE_NUMBER = 915_879_652;
        accountService.createAccount("João Silva", "js@mymail.com", null,
                JOAO_SILVA_PHONE_NUMBER);

        final Integer MANEL_COSTA_PHONE_NUMBER = 926_650_520;
        accountService.createAccount("Manel Costa", "ms@mymail.com", null,
                MANEL_COSTA_PHONE_NUMBER);

        final Integer XICO_FERREIRA_PHONE_NUMBER = 963_650_532;
        accountService.createAccount("Xico Ferreira", "xf@mymail.com", null,
                XICO_FERREIRA_PHONE_NUMBER);

        final Integer TIAGO_CANCADO_PHONE_NUMBER = 263_650_345;
        accountService.createAccount("Tiago Cancado", "tc@mymail.com", null,
                TIAGO_CANCADO_PHONE_NUMBER);

        final Integer URBINO_DAS_URZES_PHONE_NUMBER = 962_547_891;
        accountService.createAccount("Urbino das Urzes", "udu@mymail.com", null,
                URBINO_DAS_URZES_PHONE_NUMBER);

        final Integer ZE_DA_ESQUINA_PHONE_NUMBER = 212_349_016;
        accountService.changeProfileOfUserAccount("udu@mymail.com", "administrator");
        accountService.createAccount("Zé da Esquina", "ze@mymail.com", null,
                ZE_DA_ESQUINA_PHONE_NUMBER);

        final Integer NEL_MOLEIRO_PHONE_NUMBER = 930_123_456;
        accountService.changeProfileOfUserAccount("ze@mymail.com", "director");
        accountService.createAccount("Nel Moleiro", "nel.m@mymail.com", null,
                NEL_MOLEIRO_PHONE_NUMBER);

        final Integer ZE_DO_BENTO_PHONE_NUMBER = 921_458_791;
        accountService.createAccount("Zé do Bento", "zb@mymail.com", null,
                ZE_DO_BENTO_PHONE_NUMBER);

        final Integer TO_FARRULO_PHONE_NUMBER = 921_458_795;
        accountService.createAccount("Tó Farrulo", "to.f@mymail.com", null,
                TO_FARRULO_PHONE_NUMBER);

        final Integer TINO_DAS_CRUZES_PHONE_NUMBER = 921_458_799;
        accountService.createAccount("Tino das Cruzes", "tdc@mymail.com", null,
                TINO_DAS_CRUZES_PHONE_NUMBER);

        final Integer QUIM_BARREIROS_PHONE_NUMBER = 921_458_803;
        accountService.createAccount("Quim Barreiros", "qb@mymail.com", null,
                QUIM_BARREIROS_PHONE_NUMBER);

        final Integer TIAGO_GERINGONCA_PHONE_NUMBER = 921_458_807;
        accountService.createAccount("Tiago Geringonça", "tg@mymail.com", null,
                TIAGO_GERINGONCA_PHONE_NUMBER);

        final Integer ZE_MANEL_PHONE_NUMBER = 921_458_811;
        accountService.createAccount("Zé Manel", "zm@mymail.com", null,
                ZE_MANEL_PHONE_NUMBER);

        final Integer ANTONIO_SILVA_PHONE_NUMBER = 921_458815;
        accountService.createAccount("António Silva", "as@mymail.com", null,
                ANTONIO_SILVA_PHONE_NUMBER);

    }

    private void addProjects() {
        NewProjectDTO projectOneDTO = new NewProjectDTO(
                "A1",
                "Dummy 01",
                "Just a dummy project",
                "211267490",
                "Technology",
                FIXED_COST,
                TWO,
                EIGHT,
                BUDGET_ONE,
                "2022-03-01",
                "2022-08-31"
        );
        projectService.createProject(projectOneDTO);
        Map<ProjectStatus, LocalDate> projectOneStatusHistoryMap = Map.of(
                ProjectStatus.PLANNED, LocalDate.parse("2022-01-01"),
                ProjectStatus.INCEPTION, LocalDate.parse("2022-03-01"),
                ProjectStatus.ELABORATION, LocalDate.parse("2022-03-22"),
                ProjectStatus.CONSTRUCTION, LocalDate.parse("2022-04-26"),
                ProjectStatus.CLOSED, LocalDate.parse("2022-07-31")
        );
        projectService.importProjectStatusHistory(projectOneDTO.getProjectCode(), projectOneStatusHistoryMap);

        NewProjectDTO projectTwoDTO = new NewProjectDTO(
                "A2",
                "Dummy 02",
                "Just another dummy project",
                "272983330",
                "Marketing",
                "Fixed-Cost",
                THREE,
                TWELVE,
                BUDGET_TWO,
                "2023-02-20",
                "2024-04-29"
        );
        projectService.createProject(projectTwoDTO);
        Map<ProjectStatus, LocalDate> projectTwoStatusHistoryMap = Map.of(
                ProjectStatus.PLANNED, LocalDate.parse("2023-02-20"),
                ProjectStatus.INCEPTION, LocalDate.parse("2023-03-15"),
                ProjectStatus.ELABORATION, LocalDate.parse("2023-05-01")

        );
        projectService.importProjectStatusHistory(projectTwoDTO.getProjectCode(), projectTwoStatusHistoryMap);

        NewProjectDTO projectThreeDTO = new NewProjectDTO(
                "666",
                "Inevitable Nightmare",
                "Doomed from the start",
                "235140236",
                "Hospitality industry",
                "Time and Materials",
                FOUR,
                TWELVE,
                BUDGET_THREE,
                "2023-03-10",
                null
        );
        projectService.createProject(projectThreeDTO);
        Map<ProjectStatus, LocalDate> projectThreeStatusHistoryMap = Map.of(
                ProjectStatus.PLANNED, LocalDate.parse("2023-02-05"),
                ProjectStatus.INCEPTION, LocalDate.parse("2023-03-10")
        );
        projectService.importProjectStatusHistory(projectThreeDTO.getProjectCode(), projectThreeStatusHistoryMap);
    }

    private void addBusinessSectors() {
        NewBusinessSectorDTO businessSectorDTOOne = new NewBusinessSectorDTO();
        businessSectorDTOOne.businessSectorName = new BusinessSectorName("Hospitality industry");

        NewBusinessSectorDTO businessSectorDTOTwo = new NewBusinessSectorDTO();
        businessSectorDTOTwo.businessSectorName = new BusinessSectorName("Technology");

        NewBusinessSectorDTO businessSectorDTOThree = new NewBusinessSectorDTO();
        businessSectorDTOThree.businessSectorName = new BusinessSectorName("Marketing");

        businessSectorService.createBusinessSector(businessSectorDTOOne);
        businessSectorService.createBusinessSector(businessSectorDTOTwo);
        businessSectorService.createBusinessSector(businessSectorDTOThree);
    }

    private void addCustomers() {
        CustomerValueObjectDTO customerOneDTO = new CustomerValueObjectDTO();
        customerOneDTO.customerName = new CustomerName("XPTO, SA");
        customerOneDTO.country = new Country(PORTUGAL);
        customerOneDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("211267490");
        customerService.createCustomer(customerOneDTO);

        CustomerValueObjectDTO customerTwoDTO = new CustomerValueObjectDTO();
        customerTwoDTO.customerName = new CustomerName("XYZ, Lda");
        customerTwoDTO.country = new Country(PORTUGAL);
        customerTwoDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("272983330");
        customerService.createCustomer(customerTwoDTO);

        CustomerValueObjectDTO customerThreeDTO = new CustomerValueObjectDTO();
        customerThreeDTO.customerName = new CustomerName("Hell, LLC");
        customerThreeDTO.country = new Country(PORTUGAL);
        customerThreeDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("235140236");
        customerService.createCustomer(customerThreeDTO);
    }

    private void createSprint(ProjectCode projectCode, LocalDate startDate) {
        NewSprintDTO sprint = new NewSprintDTO();
        sprint.projectCode = projectCode;
        sprint.startDate = startDate;
        sprintService.createSprint(sprint);
    }


    private void changeSprintStatus(ProjectCode projectCode, SprintCode sprintCode, SprintStatus sprintStatus) {
        SprintStatusDTO sprintStatusDTO = new SprintStatusDTO();
        sprintStatusDTO.sprintID = new SprintID(projectCode, sprintCode);
        sprintStatusDTO.sprintStatus = sprintStatus;
        sprintService.changeSprintStatus(sprintStatusDTO);
    }

    private void addUserStoryToSprintBacklog(ProjectCode projectCode, SprintCode sprintCode, String userStoryCode) {
        NewSprintBacklogUserStoryDTO newSprintBacklogUserStoryDTO = new NewSprintBacklogUserStoryDTO();
        newSprintBacklogUserStoryDTO.userStoryCode = new UserStoryCode(userStoryCode);
        sprintService.addUserStoryToSprintBacklog(projectCode, sprintCode, newSprintBacklogUserStoryDTO);
    }

    private void addResource(String email, ProjectRole projectRole, LocalDate startDate, LocalDate endDate,
                             Integer costPerHour, ProjectCode projectCode, Integer percentageOfAllocation) {
        NewResourceDTO resourceDTO = new NewResourceDTO(
                email,
                projectCode.getProjectCodeValue(),
                startDate.toString(),
                endDate.toString(),
                projectRole.name(),
                percentageOfAllocation.toString(),
                costPerHour.toString()
        );
        resourceService.addResourceToProject(resourceDTO);
    }

    private void addUSerStory(ProjectCode projectCode, String userStoryCode, String description) {
        NewUserStoryDTO userStory = new NewUserStoryDTO();
        userStory.projectCode = projectCode;
        userStory.userStoryCode = new UserStoryCode(userStoryCode);
        userStory.description = new Description(description);
        userStoryService.createUserStory(userStory);

    }
}
