package org.switch2022.project.domain.project;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectFactoryImplementationTest {

    @Test
    void createProject_AllParametersFilled() {
        ProjectFactoryImplementation factory = new ProjectFactoryImplementation();
        Project testProject = factory.createProject(
                new ProjectCode("P1"),
                new ProjectName("Project 1"),
                new Description("Description 1"),
                new TaxIdentificationNumberPortugalImplementation("123456789"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials"),
                new SprintDuration(15),
                new NumberPlannedSprints(20),
                new Budget("2500"),
                new TimePeriod(LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 2, 2)));

        assertEquals(testProject.getProductBacklog(),
                new ProductBacklog(new ProjectCode("P1")));
        assertEquals("P1", testProject.getProjectCode().getProjectCodeValue());
        assertEquals("Project 1",
                testProject.getProjectName().getProjectNameValue());
        assertEquals("Description 1",
                testProject.getDescription().getDescriptionValue());
        assertEquals("123456789",
                testProject.getCustomerID().getTaxIdentificationNumber());
        assertEquals("Technology",
                testProject.getBusinessSectorName().getBusinessSectorNameValue());
        assertEquals("Time and Materials",
                testProject.getProjectTypologyName().getProjectTypologyNameValue());
        assertEquals(15,
                testProject.getSprintDuration().get().getSprintDurationValue());
        assertEquals(20,
                testProject.getNumberPlannedSprints().get().getNumberOfPlannedSprintsValue());
        assertEquals(2500, testProject.getBudget().get().getBudgetValue());
        assertEquals("2022-01-01",
                testProject.getPeriod().get().getStartDate().toString());
        assertEquals("2022-02-02",
                testProject.getPeriod().get().getEndDate().toString());
        StatusHistory expected = new StatusHistory(new ProjectCode("P1"));
        expected.record(ProjectStatus.PLANNED);
        assertEquals(Map.of(ProjectStatus.PLANNED, LocalDate.now()), testProject.getStatusHistory().getStatusHistoryMap());
    }

    @Test
    void testCreateProject_OnlyDemandedParameters() {
        ProjectFactoryImplementation factory = new ProjectFactoryImplementation();
        Project testProject = factory.createProject(
                new ProjectCode("P1"),
                new ProjectName("Project 1"),
                new Description("Description 1"),
                new TaxIdentificationNumberPortugalImplementation("123456789"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials"));

        assertEquals(testProject.getProductBacklog(),
                new ProductBacklog(new ProjectCode("P1")));
        assertEquals("P1", testProject.getProjectCode().getProjectCodeValue());
        assertEquals("Project 1",
                testProject.getProjectName().getProjectNameValue());
        assertEquals("Description 1",
                testProject.getDescription().getDescriptionValue());
        assertEquals("123456789",
                testProject.getCustomerID().getTaxIdentificationNumber());
        assertEquals("Technology",
                testProject.getBusinessSectorName().getBusinessSectorNameValue());
        assertEquals("Time and Materials",
                testProject.getProjectTypologyName().getProjectTypologyNameValue());
        assertEquals(Map.of(ProjectStatus.PLANNED, LocalDate.now()), testProject.getStatusHistory().getStatusHistoryMap());
    }

    @Test
    void testCreateProject1() {
        ProjectFactoryImplementation factory = new ProjectFactoryImplementation();
        LinkedList<UserStoryID> userStoryList = new LinkedList<>();
        UserStoryID us1 = new UserStoryID(new ProjectCode("P001"), new UserStoryCode("US001"));
        UserStoryID us2 = new UserStoryID(new ProjectCode("P002"), new UserStoryCode("US002"));
        UserStoryID us3 = new UserStoryID(new ProjectCode("P003"), new UserStoryCode("US003"));
        userStoryList.add(us1);
        userStoryList.add(us2);
        userStoryList.add(us3);

        Map<ProjectStatus, LocalDate> projectStatusHistory = new HashMap<>();
        projectStatusHistory.put(ProjectStatus.PLANNED, LocalDate.of(2023, 1, 1));
        projectStatusHistory.put(ProjectStatus.INCEPTION, LocalDate.of(2023, 2, 1));
        projectStatusHistory.put(ProjectStatus.CLOSED, LocalDate.of(2023, 4, 1));


        Project testProject = factory.createProject(
                new ProjectCode("P1"),
                new ProjectName("Project 1"),
                new Description("Description 1"),
                new TaxIdentificationNumberPortugalImplementation("123456789"),
                new BusinessSectorName("Technology"),
                new ProjectTypologyName("Time and Materials"),
                userStoryList,
                ProjectStatus.PLANNED,
                projectStatusHistory,
                new SprintDuration(15),
                new NumberPlannedSprints(20),
                new Budget("2500"),
                new TimePeriod(LocalDate.of(2022, 1, 1),
                        LocalDate.of(2022, 2, 2)));

        assertEquals(testProject.getProductBacklog(),
                new ProductBacklog(new ProjectCode("P1"), userStoryList));
        assertEquals("P1", testProject.getProjectCode().getProjectCodeValue());
        assertEquals("Project 1",
                testProject.getProjectName().getProjectNameValue());
        assertEquals("Description 1",
                testProject.getDescription().getDescriptionValue());
        assertEquals("123456789",
                testProject.getCustomerID().getTaxIdentificationNumber());
        assertEquals("Technology",
                testProject.getBusinessSectorName().getBusinessSectorNameValue());
        assertEquals("Time and Materials",
                testProject.getProjectTypologyName().getProjectTypologyNameValue());
        assertEquals(15,
                testProject.getSprintDuration().get().getSprintDurationValue());
        assertEquals(20,
                testProject.getNumberPlannedSprints().get().getNumberOfPlannedSprintsValue());
        assertEquals(2500, testProject.getBudget().get().getBudgetValue());
        assertEquals("2022-01-01",
                testProject.getPeriod().get().getStartDate().toString());
        assertEquals("2022-02-02",
                testProject.getPeriod().get().getEndDate().toString());
        assertEquals(userStoryList,
                testProject.getProductBacklog().getUserStorylist());
        assertEquals(projectStatusHistory,
                testProject.getStatusHistory().getStatusHistoryMap());

        Map<ProjectStatus, LocalDate> expected = Map.of(
                ProjectStatus.INCEPTION, LocalDate.of(2023, 2, 1),
                ProjectStatus.CLOSED, LocalDate.of(2023, 4, 1),
                ProjectStatus.PLANNED, LocalDate.of(2023, 1, 1));
        assertEquals(expected, testProject.getStatusHistory().getStatusHistoryMap());

    }
}