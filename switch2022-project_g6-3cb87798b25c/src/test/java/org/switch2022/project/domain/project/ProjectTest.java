package org.switch2022.project.domain.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectTest {
    private Project project;
    private ProjectCode projectCode;
    private Description description;
    private ProjectName projectName;
    private TaxIdentificationNumber customerID;
    private BusinessSectorName businessSectorName;
    private ProjectTypologyName projectTypologyName;
    private ProductBacklog productBacklog;
    private ProjectStatus projectStatus;
    private StatusHistory projectStatusHistory;
    private SprintDuration sprintDuration;
    private NumberPlannedSprints numberPlannedSprints;
    private Budget budget;
    private TimePeriod period;

    @BeforeEach
    void init() {
        this.projectCode = mock(ProjectCode.class);
        this.projectName = mock(ProjectName.class);
        this.description = mock(Description.class);
        this.customerID = mock(TaxIdentificationNumber.class);
        this.businessSectorName = mock(BusinessSectorName.class);
        this.projectTypologyName = mock(ProjectTypologyName.class);
        this.productBacklog = mock(ProductBacklog.class);
        this.projectStatus = mock(ProjectStatus.class);
        this.projectStatusHistory = mock(StatusHistory.class);
        this.sprintDuration = mock(SprintDuration.class);
        this.numberPlannedSprints = mock(NumberPlannedSprints.class);
        this.budget = mock(Budget.class);
        this.period = mock(TimePeriod.class);

        this.project = new Project(
                this.projectCode,
                this.projectName,
                this.description,
                this.customerID,
                this.businessSectorName,
                this.projectTypologyName,
                this.productBacklog,
                this.projectStatus,
                this.projectStatusHistory
        );

        this.project.setSprintDuration(this.sprintDuration);
        this.project.setNumberPlannedSprints(this.numberPlannedSprints);
        this.project.setBudget(this.budget);
        this.project.setPeriod(this.period);
    }

    @AfterEach
    void tearDown() {
        this.project = null;
        this.productBacklog = null;
        this.projectCode = null;
        this.description = null;
        this.projectName = null;
    }

    @Test
    void constructor_NullProjectCode_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        null,
                        this.projectName,
                        this.description,
                        this.customerID,
                        this.businessSectorName,
                        this.projectTypologyName,
                        this.productBacklog,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Project Code is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullProjectName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        null,
                        this.description,
                        this.customerID,
                        this.businessSectorName,
                        this.projectTypologyName,
                        this.productBacklog,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Project Name is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullDescription_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        null,
                        this.customerID,
                        this.businessSectorName,
                        this.projectTypologyName,
                        this.productBacklog,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Description is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullCustomerID_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        this.description,
                        null,
                        this.businessSectorName,
                        this.projectTypologyName,
                        this.productBacklog,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Customer Tax Identification Number is a required parameter for Project creation",
                exception.getMessage());
    }

    @Test
    void constructor_NullBusinessSectorName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        this.description,
                        this.customerID,
                        null,
                        this.projectTypologyName,
                        this.productBacklog,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Business Sector Name is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullProjectTypologyName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        this.description,
                        this.customerID,
                        this.businessSectorName,
                        null,
                        this.productBacklog,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Project Typology is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullProductBacklog_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        this.description,
                        this.customerID,
                        this.businessSectorName,
                        this.projectTypologyName,
                        null,
                        this.projectStatus,
                        this.projectStatusHistory
                ));
        assertEquals("Product Backlog is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullProjectStatus_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        this.description,
                        this.customerID,
                        this.businessSectorName,
                        this.projectTypologyName,
                        this.productBacklog,
                        null,
                        this.projectStatusHistory
                ));
        assertEquals("Project Status is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void constructor_NullStatusHistory_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Project(
                        this.projectCode,
                        this.projectName,
                        this.description,
                        this.customerID,
                        this.businessSectorName,
                        this.projectTypologyName,
                        this.productBacklog,
                        this.projectStatus,
                        null
                ));
        assertEquals("Status History is a required parameter for Project creation", exception.getMessage());
    }

    @Test
    void addUserStoryToProductBacklog_uniqueUserStoryCode_equals() {
        when(productBacklog.addUserStory(any())).thenReturn(true);
        boolean expected = true;
        boolean result = project.addUserStoryToProductBacklog(mock(UserStoryID.class));
        assertEquals(expected, result);
    }

    @Test
    void addUserStoryToProductBacklog_userStoryCodeThatAlreadyExists_false() {
        when(productBacklog.addUserStory(any())).thenReturn(false);
        boolean expected = false;
        boolean result = project.addUserStoryToProductBacklog(mock(UserStoryID.class));
        assertEquals(expected, result);
    }

    @Test
    void changeStatus() {
        ProjectStatus projectStatus = mock(ProjectStatus.class);

        this.project.changeStatus(projectStatus);

        verify(this.projectStatusHistory).record(projectStatus);
    }

    @Test
    void importStatusHistory() {
        ProjectStatus projectStatus = mock(ProjectStatus.class);
        LocalDate date = mock(LocalDate.class);
        Map<ProjectStatus, LocalDate> statusHistoryMap = Map.of(projectStatus, date);

        this.project.importStatusHistory(statusHistoryMap);

        Map<ProjectStatus, LocalDate> expected = statusHistoryMap;

        Map<ProjectStatus, LocalDate> result = this.project.getStatusHistory().getStatusHistoryMap();

        assertEquals(expected, result);
    }

    @Test
    void getUserStoryPriorityList_UserStoryPriorityList() {
        List<UserStoryID> userStoryIDList = List.of(mock(UserStoryID.class));

        when(this.productBacklog.getUserStorylist()).thenReturn(userStoryIDList);

        List<UserStoryID> expected = userStoryIDList;

        List<UserStoryID> result = this.project.getUserStoryPriorityList();

        assertEquals(expected, result);
    }

    @Test
    void getProjectCode_ProjectCode() {
        ProjectCode expected = this.projectCode;

        ProjectCode result = this.project.getProjectCode();

        assertEquals(expected, result);
    }

    @Test
    void getProjectProjectName_ProjectName() {
        ProjectName expected = this.projectName;

        ProjectName result = this.project.getProjectName();

        assertEquals(expected, result);
    }

    @Test
    void getProjectDescription_Description() {
        Description expected = this.description;

        Description result = this.project.getDescription();

        assertEquals(expected, result);
    }

    @Test
    void getCustomerID_TaxIdentificationNumber() {
        TaxIdentificationNumber expected = this.customerID;

        TaxIdentificationNumber result = this.project.getCustomerID();

        assertEquals(expected, result);
    }

    @Test
    void getProjectTypologyName_ProjectTypologyName() {
        ProjectTypologyName expected = this.projectTypologyName;

        ProjectTypologyName result = this.project.getProjectTypologyName();

        assertEquals(expected, result);
    }

    @Test
    void getBusinessSectorName_BusinessSectorName() {
        BusinessSectorName expected = this.businessSectorName;

        BusinessSectorName result = this.project.getBusinessSectorName();

        assertEquals(expected, result);
    }

    @Test
    void getProjectStatus_Status() {
        ProjectStatus expected = this.projectStatus;

        ProjectStatus result = this.project.getProjectStatus();

        assertEquals(expected, result);
    }

    @Test
    void getSprintDuration_SprintDuration() {
        Optional<SprintDuration> expected = Optional.of(this.sprintDuration);

        Optional<SprintDuration> result = this.project.getSprintDuration();

        assertEquals(expected, result);
    }

    @Test
    void getNumberPlannedSprints_NumberPlannedSprints() {
        Optional<NumberPlannedSprints> expected = Optional.of(this.numberPlannedSprints);

        Optional<NumberPlannedSprints> result = this.project.getNumberPlannedSprints();

        assertEquals(expected, result);
    }

    @Test
    void getBudget_Budget() {
        Optional<Budget> expected = Optional.of(this.budget);

        Optional<Budget> result = this.project.getBudget();

        assertEquals(expected, result);
    }

    @Test
    void getPeriod_Period() {
        Optional<TimePeriod> expected = Optional.of(this.period);

        Optional<TimePeriod> result = this.project.getPeriod();

        assertEquals(expected, result);
    }

    @Test
    void getUserStoryPriorityList_EmptyBacklog_EmptyList() {
        List<UserStoryID> userStoryIDList = new ArrayList<>();
        when(this.productBacklog.getUserStorylist()).thenReturn(userStoryIDList);

        List<UserStoryID> expected = userStoryIDList;

        List<UserStoryID> result = this.project.getProductBacklog().getUserStorylist();

        assertEquals(expected, result);
    }

    @Test
    void getUserStoryPriorityList_UserStoryCodeList() {
        UserStoryID userStoryIDOne = mock(UserStoryID.class);
        UserStoryID userStoryIDTwo = mock(UserStoryID.class);
        UserStoryID userStoryIDThree = mock(UserStoryID.class);
        List<UserStoryID> userStoryIDList = Arrays.asList(userStoryIDOne, userStoryIDTwo, userStoryIDThree);

        when(this.productBacklog.getUserStorylist()).thenReturn(userStoryIDList);

        List<UserStoryID> expected = userStoryIDList;

        List<UserStoryID> result = this.project.getProductBacklog().getUserStorylist();

        assertEquals(expected, result);
    }

    @Test
    void identity_equalProjectCode_Equals() {
        ProjectCode expected = projectCode;
        ProjectCode result = project.identity();
        assertEquals(expected, result);
    }

    @Test
    void identity_notEqualProjectCode_NotEquals() {
        ProjectCode expected = mock(ProjectCode.class);
        ProjectCode result = project.identity();
        assertNotEquals(expected, result);
    }

    @Test
    void sameAs_SameAttributes_True() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypology = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = this.sprintDuration;
        NumberPlannedSprints numberPlannedSprints = this.numberPlannedSprints;
        Budget budget = this.budget;
        TimePeriod period = this.period;

        Project projectWithDifferentName = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentName.setSprintDuration(sprintDuration);
        projectWithDifferentName.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentName.setBudget(budget);
        projectWithDifferentName.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentName);

        assertTrue(result);
    }

    @Test
    void sameAs_DifferentProjectName_False() {
        ProjectCode projectCode = mock(ProjectCode.class);
        ProjectName projectName = mock(ProjectName.class);
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypology = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = this.sprintDuration;
        NumberPlannedSprints numberPlannedSprints = this.numberPlannedSprints;
        Budget budget = this.budget;
        TimePeriod period = this.period;

        Project projectWithDifferentName = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentName.setSprintDuration(sprintDuration);
        projectWithDifferentName.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentName.setBudget(budget);
        projectWithDifferentName.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentName);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentProjectDescription_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = mock(Description.class);
        TaxIdentificationNumber customerID = mock(TaxIdentificationNumber.class);
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);
        ProjectTypologyName projectTypology = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentDescription = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentDescription.setSprintDuration(sprintDuration);
        projectWithDifferentDescription.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentDescription.setBudget(budget);
        projectWithDifferentDescription.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentDescription);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentCustomerID_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = mock(TaxIdentificationNumber.class);
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);
        ProjectTypologyName projectTypology = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentCustomerID = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentCustomerID.setSprintDuration(sprintDuration);
        projectWithDifferentCustomerID.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentCustomerID.setBudget(budget);
        projectWithDifferentCustomerID.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentCustomerID);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentBusinessSectorName_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);
        ProjectTypologyName projectTypology = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentBusinessSectorName = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentBusinessSectorName.setSprintDuration(sprintDuration);
        projectWithDifferentBusinessSectorName.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentBusinessSectorName.setBudget(budget);
        projectWithDifferentBusinessSectorName.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentBusinessSectorName);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentProjectTypologyName_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentProjectTypologyName = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentProjectTypologyName.setSprintDuration(sprintDuration);
        projectWithDifferentProjectTypologyName.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentProjectTypologyName.setBudget(budget);
        projectWithDifferentProjectTypologyName.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentProjectTypologyName);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentProductBacklog_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentProductBacklog = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentProductBacklog.setSprintDuration(sprintDuration);
        projectWithDifferentProductBacklog.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentProductBacklog.setBudget(budget);
        projectWithDifferentProductBacklog.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentProductBacklog);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentStatus_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = mock(ProjectStatus.class);
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentProductBacklog = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentProductBacklog.setSprintDuration(sprintDuration);
        projectWithDifferentProductBacklog.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentProductBacklog.setBudget(budget);
        projectWithDifferentProductBacklog.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentProductBacklog);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentStatusHistory_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = mock(StatusHistory.class);
        SprintDuration sprintDuration = this.sprintDuration;
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentProductBacklog = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentProductBacklog.setSprintDuration(sprintDuration);
        projectWithDifferentProductBacklog.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentProductBacklog.setBudget(budget);
        projectWithDifferentProductBacklog.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentProductBacklog);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentSprintDuration_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = mock(SprintDuration.class);
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentProductBacklog = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentProductBacklog.setSprintDuration(sprintDuration);
        projectWithDifferentProductBacklog.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentProductBacklog.setBudget(budget);
        projectWithDifferentProductBacklog.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentProductBacklog);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentNumberPlannedSprints_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = this.sprintDuration;
        NumberPlannedSprints numberPlannedSprints = mock(NumberPlannedSprints.class);
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentNumberPlannedSprints = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentNumberPlannedSprints.setSprintDuration(sprintDuration);
        projectWithDifferentNumberPlannedSprints.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentNumberPlannedSprints.setBudget(budget);
        projectWithDifferentNumberPlannedSprints.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentNumberPlannedSprints);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentBudget_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = this.sprintDuration;
        NumberPlannedSprints numberPlannedSprints = this.numberPlannedSprints;
        Budget budget = mock(Budget.class);
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentBudget = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentBudget.setSprintDuration(sprintDuration);
        projectWithDifferentBudget.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentBudget.setBudget(budget);
        projectWithDifferentBudget.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentBudget);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentPeriod_False() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = this.projectName;
        Description description = this.description;
        TaxIdentificationNumber customerID = this.customerID;
        BusinessSectorName businessSectorName = this.businessSectorName;
        ProjectTypologyName projectTypologyName = this.projectTypologyName;
        ProductBacklog productBacklog = this.productBacklog;
        ProjectStatus projectStatus = this.projectStatus;
        StatusHistory projectStatusHistory = this.projectStatusHistory;
        SprintDuration sprintDuration = this.sprintDuration;
        NumberPlannedSprints numberPlannedSprints = this.numberPlannedSprints;
        Budget budget = this.budget;
        TimePeriod period = mock(TimePeriod.class);

        Project projectWithDifferentPeriod = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        projectWithDifferentPeriod.setSprintDuration(sprintDuration);
        projectWithDifferentPeriod.setNumberPlannedSprints(numberPlannedSprints);
        projectWithDifferentPeriod.setBudget(budget);
        projectWithDifferentPeriod.setPeriod(period);

        boolean result = project.sameAs(projectWithDifferentPeriod);

        assertFalse(result);
    }

    @Test
    void sameAS_instanceOfDifferentClass_False() {
        Object object = new Object();
        boolean result = project.sameAs(object);
        assertFalse(result);
    }

    @Test
    void equals_SameObject_True() {
        boolean result = project.equals(project);

        assertTrue(result);
    }

    @Test
    void testEquals_DifferentProjectCode_NotEqual() {
        ProjectCode projectCode = mock(ProjectCode.class);
        ProjectName projectName = mock(ProjectName.class);
        Description description = mock(Description.class);
        TaxIdentificationNumber customerID = mock(TaxIdentificationNumber.class);
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);
        ProjectTypologyName projectTypology = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = mock(ProjectStatus.class);
        StatusHistory projectStatusHistory = mock(StatusHistory.class);

        Project projectWithDifferentProjectCode = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        assertNotEquals(project, projectWithDifferentProjectCode);
    }

    @Test
    void testEquals_SameProjectCode_Equal() {
        ProjectCode projectCode = this.projectCode;
        ProjectName projectName = mock(ProjectName.class);
        Description description = mock(Description.class);
        TaxIdentificationNumber customerID = mock(TaxIdentificationNumber.class);
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);
        ProjectTypologyName projectTypology = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = mock(ProjectStatus.class);
        StatusHistory projectStatusHistory = mock(StatusHistory.class);

        Project projectWithSameProjectCode = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        boolean result = this.project.equals(projectWithSameProjectCode);

        assertTrue(result);
    }

    @Test
    void testEquals_NullObjects_NotEquals() {
        boolean result = project.equals(null);

        assertFalse(result);
    }

    @Test
    void testEquals_DifferentObjects_NotEquals() {
        boolean result = project.equals("project");

        assertFalse(result);
    }


    @Test
    void testHashCode_SameProjectCode_Equals() {
        int expected = Objects.hash(this.projectCode);
        int result = this.project.hashCode();
        assertEquals(expected, result);
    }

    @Test
    void testHashCode_NotEquals() {
        ProjectCode projectCode = mock(ProjectCode.class);
        ProjectName projectName = mock(ProjectName.class);
        Description description = mock(Description.class);
        TaxIdentificationNumber customerID = mock(TaxIdentificationNumber.class);
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);
        ProjectTypologyName projectTypology = mock(ProjectTypologyName.class);
        ProductBacklog productBacklog = mock(ProductBacklog.class);
        ProjectStatus projectStatus = mock(ProjectStatus.class);
        StatusHistory projectStatusHistory = mock(StatusHistory.class);

        Project differentProject = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypology,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        int projectHasCode = project.hashCode();
        int differentProjectHashCode = differentProject.hashCode();

        assertNotEquals(projectHasCode, differentProjectHashCode);
    }

    @Test
    void addUserStoryToProductBacklog() {
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        project.addUserStoryToProductBacklog(userStoryIdDouble);

        verify(productBacklog).addUserStory(userStoryIdDouble);
    }

    @Test
    void removeUserStoryFromProductBacklog() {
        UserStoryID userStoryIdDouble = mock(UserStoryID.class);
        project.removeUserStoryFromProductBacklog(userStoryIdDouble);

        verify(productBacklog).removeUserStory(userStoryIdDouble);
    }

    @Test
    void isDateBeforeProjectStartDate_ResourceStartDateBeforeProjectStartDate_True() {
        Map<ProjectStatus, LocalDate> map = new HashMap<>();
        map.put(ProjectStatus.PLANNED, LocalDate.of(2022, 1, 1));

        when(projectStatusHistory.getStatusHistoryMap()).thenReturn(map);

        boolean result = project.isDateBeforeProjectCreationDate(LocalDate.of(2021, 1, 1));

        assertTrue(result);
    }

    @Test
    void isDateBeforeProjectStartDate_ResourceStartDateAfterProjectStartDate_False() {
        Map<ProjectStatus, LocalDate> map = new HashMap<>();
        map.put(ProjectStatus.PLANNED, LocalDate.of(2022, 1, 1));
        when(projectStatusHistory.getStatusHistoryMap()).thenReturn(map);

        boolean result = project.isDateBeforeProjectCreationDate(LocalDate.of(2023, 1, 1));

        assertFalse(result);
    }

    @Test
    void isDateAfterProjectEndDate_PeriodIsNull_False() {
        project.setPeriod(null);

        boolean result = project.isDateAfterProjectEndDate(LocalDate.of(2021, 1, 1));

        assertFalse(result);
    }

    @Test
    void isDateAfterProjectEndDate_ResourceEndDatesAfterProjectEndDate_True() {
        when(period.getEndDate()).thenReturn(LocalDate.of(2023, 1, 1));

        boolean result = project.isDateAfterProjectEndDate(LocalDate.of(2024, 1, 1));

        assertTrue(result);
    }

    @Test
    void isDateAfterProjectEndDate_ResourceEndDatesBeforeProjectEndDate_False() {
        when(period.getEndDate()).thenReturn(LocalDate.of(2023, 1, 1));

        boolean result = project.isDateAfterProjectEndDate(LocalDate.of(2022, 1, 1));

        assertFalse(result);
    }

    @Test
    void isDateAfterProjectEndDate_ResourceEndDatesIsNull_False() {
        when(this.period.getEndDate()).thenReturn(null);

        boolean result = project.isDateAfterProjectEndDate(LocalDate.of(2022, 1, 1));

        assertFalse(result);
    }
}