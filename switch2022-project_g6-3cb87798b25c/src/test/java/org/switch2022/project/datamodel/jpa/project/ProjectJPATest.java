package org.switch2022.project.datamodel.jpa.project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProjectJPATest {

    private static ProjectJPA projectJPA;

    private static List<StatusHistoryJPA> statusHistoryJPAList;

    @BeforeAll
    static void init() {
        StatusHistoryJPA firstStatusHistoryDouble = mock(StatusHistoryJPA.class);
        StatusHistoryJPA secondStatusHistoryDouble = mock(StatusHistoryJPA.class);
        StatusHistoryJPA thirdStatusHistoryDouble = mock(StatusHistoryJPA.class);

        statusHistoryJPAList = Arrays.asList(
                firstStatusHistoryDouble,
                secondStatusHistoryDouble,
                thirdStatusHistoryDouble);


        projectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );
    }

    @Test
    void emptyConstructor() {
        ProjectJPA projectJPA = new ProjectJPA();
        assertNotNull(projectJPA);
    }

    @Test
    void getProjectCode_ProjectCode() {
        String expected = "P001";

        String result = projectJPA.getProjectCodeValue();

        assertEquals(expected, result);
    }

    @Test
    void getProjectName_ProjectName() {
        String expected = "Project One";

        String result = projectJPA.getProjectName();

        assertEquals(expected, result);
    }

    @Test
    void getDescription_Description() {
        String expected = "This is the description of the first project.";

        String result = projectJPA.getDescription();

        assertEquals(expected, result);
    }

    @Test
    void getCustomerID_CustomerID() {
        String expected = "233867724";

        String result = projectJPA.getCustomerID();

        assertEquals(expected, result);
    }

    @Test
    void getProjectTypologyName_ProjectTypology() {
        String expected = "Technology";

        String result = projectJPA.getBusinessSectorName();

        assertEquals(expected, result);
    }

    @Test
    void getBusinessSectorName_BusinessSectorName() {
        String expected = "Time and Materials";

        String result = projectJPA.getProjectTypologyName();

        assertEquals(expected, result);
    }

    @Test
    void getProductBacklog_ProductBacklog() {
        ProductBacklogJPA expected = new ProductBacklogJPA("P001", "{US001,US002,US003}");

        ProductBacklogJPA result = projectJPA.getProductBacklog();

        assertEquals(expected, result);
    }

    @Test
    void getProjectStatus_ProjectStatus() {
        String expected = "Finished";

        String result = projectJPA.getProjectStatus();

        assertEquals(expected, result);
    }

    @Test
    void getStatusHistory_StatusHistory() {
        List<StatusHistoryJPA> expected = statusHistoryJPAList;

        List<StatusHistoryJPA> result = projectJPA.getStatusHistory();

        assertEquals(expected, result);
    }

    @Test
    void getSprintDuration_SprintDuration() {
        Optional<Integer> expected = Optional.of(3);

        Optional<Integer> result = projectJPA.getSprintDuration();

        assertEquals(expected, result);
    }

    @Test
    void getNumberOfPlannedSprints_NumberOfPlannedSprints() {
        Optional<Integer> expected = Optional.of(10);

        Optional<Integer> result = projectJPA.getNumberOfPlannedSprints();

        assertEquals(expected, result);
    }

    @Test
    void getBudget_Budget() {
        Optional<Double> expected = Optional.of(1000.0);

        Optional<Double> result = projectJPA.getBudget();

        assertEquals(expected, result);
    }

    @Test
    void getStartDate_StartDate() {
        Optional<String> expected = Optional.of("2023-05-10");

        Optional<String> result = projectJPA.getStartDate();

        assertEquals(expected, result);
    }

    @Test
    void getEndDate_EndDate() {
        Optional<String> expected = Optional.of("2030-05-10");

        Optional<String> result = projectJPA.getEndDate();


        assertEquals(expected, result);
    }

    @Test
    void testEquals_SameObjects_True() {
        ProjectJPA sameObject = projectJPA;
        boolean result = projectJPA.equals(sameObject);

        assertTrue(result);
    }

    @Test
    void testEquals_NullObject_False() {
        boolean result = projectJPA.equals(null);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfDifferentClass_False() {
        String objectOfDifferentClass = "ProjectJPA";
        boolean result = projectJPA.equals(objectOfDifferentClass);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentProjectCode_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P002",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentProjectName_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project Two",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentDescription_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is a different description.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentCustomerID_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233864444",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentBusinessSectorName_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Health",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentProjectTypologyName_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Fixed Cost",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentProductBacklog_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentProjectStatus_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Planned",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentStatusHistory_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                new ArrayList<>(),
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentSprintDuration_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                4,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }


    @Test
    void testEquals_ObjectOfSameClassButDifferentNumberOfPlannedSprints_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                20,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentBudget_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                5000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentStartDate_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2020-01-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentEndDate_False() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2025-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertFalse(result);
    }
    @Test
    void testEquals_ObjectOfSameClassAndSameAttributes_True() {
        ProjectJPA differntProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "Finished",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        boolean result = projectJPA.equals(differntProjectJPA);

        assertTrue(result);
    }



    @Test
    void testHashCode() {
        int expected = Objects.hash(
                projectJPA.getProjectCodeValue(),
                projectJPA.getProjectName(),
                projectJPA.getDescription(),
                projectJPA.getCustomerID(),
                projectJPA.getBusinessSectorName(),
                projectJPA.getProjectTypologyName(),
                projectJPA.getProductBacklog(),
                projectJPA.getProjectStatus(),
                projectJPA.getStatusHistory(),
                projectJPA.getSprintDuration(),
                projectJPA.getNumberOfPlannedSprints(),
                projectJPA.getBudget(),
                projectJPA.getStartDate(),
                projectJPA.getEndDate()
        );

        int result = projectJPA.hashCode();

        assertEquals(expected, result);
    }
}