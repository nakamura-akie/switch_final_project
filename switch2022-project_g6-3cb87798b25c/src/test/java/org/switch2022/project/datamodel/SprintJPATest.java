package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;

import static org.junit.jupiter.api.Assertions.*;

class SprintJPATest {

    private static SprintIdJPA sprintIdJPA;
    private static SprintJPA sprintJPA;
    private static SprintBacklogJPA sprintBacklogJPA;

    @BeforeAll
    static void init() {
        String projectCode = "P1";
        String sprintCode = "S1";
        String startDate = "2022-01-01";
        Integer sprintDuration = 3;
        String endDate = "2022-01-21";
        sprintIdJPA = new SprintIdJPA(sprintCode, projectCode);
        String userStoryCodeList = "US01";
        sprintBacklogJPA = new SprintBacklogJPA(sprintIdJPA, userStoryCodeList);
        sprintJPA = new SprintJPA(sprintIdJPA, startDate, sprintDuration, endDate, sprintBacklogJPA);
    }

    @AfterAll
    static void tearDown() {
        sprintIdJPA = null;
        sprintJPA = null;
    }


    @Test
    void getSprintIdJPA_SprintIdJPA() {

        SprintIdJPA actualSprintIdJPA = sprintJPA.getSprintIdJPA();

        assertEquals(sprintIdJPA, actualSprintIdJPA);
    }

    @Test
    void getStartDate_StartDate() {

        String startDate = "2022-01-01";

        String actualStartDate = sprintJPA.getStartDate();

        assertEquals(startDate, actualStartDate);
    }


    @Test
    void getSprintDuration_SprintDuration() {
        long duration = 3;

        long actualDuration = sprintJPA.getSprintDuration();

        assertEquals(duration, actualDuration);
    }

    @Test
    void equals_EqualObject_True() {
        String startDate = "2029-01-01";
        String endDate = "2029-02-22";
        Integer sprintDuration = 6;

        SprintJPA sprintJPATwo = new SprintJPA(sprintIdJPA, startDate, sprintDuration, endDate, sprintBacklogJPA);

        boolean result = sprintJPA.equals(sprintJPATwo);

        assertTrue(result);
    }

    @Test
    void equals_DifferentProjectCodeObject_False() {
        String projectCode = "P2";
        String sprintCode = "S1";
        String startDate = "2022-01-01";
        String endDate = "2029-02-22";
        Integer sprintDuration = 3;

        SprintIdJPA sprintIdJPA = new SprintIdJPA(projectCode, sprintCode);

        SprintJPA sprintJPATwo = new SprintJPA(sprintIdJPA, startDate, sprintDuration, endDate, sprintBacklogJPA);

        boolean result = sprintJPA.equals(sprintJPATwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentSprintCodeObject_False() {
        String projectCode = "P1";
        String sprintCode = "S2";
        String startDate = "2022-01-01";
        String endDate = "2029-02-22";
        Integer sprintDuration = 3;

        SprintIdJPA sprintIdJPA = new SprintIdJPA(projectCode, sprintCode);

        SprintJPA sprintJPATwo = new SprintJPA(sprintIdJPA, startDate, sprintDuration, endDate, sprintBacklogJPA);

        boolean result = sprintJPA.equals(sprintJPATwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentStartDateAndSprintDuration_True() {
        String startDateTwo = "2020-01-01";
        String endDateTwo = "2029-02-22";
        Integer sprintDuration = 1;

        SprintJPA sprintJPATwo = new SprintJPA(sprintIdJPA, startDateTwo, sprintDuration, endDateTwo, sprintBacklogJPA);

        boolean result = sprintJPA.equals(sprintJPATwo);

        assertTrue(result);
    }

    @Test
    void equals_ObjectOfDifferentType_False() {
        String object = "not a sprint";

        boolean result = sprintJPA.equals(object);

        assertFalse(result);

    }

    @Test
    void equals_NullObject_False() {

        boolean result = sprintJPA.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        SprintJPA sprintJPATwo = sprintJPA;

        int firstHash = sprintJPA.hashCode();
        int secondHash = sprintJPATwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        String sprintCode = "S3";
        String projectCode = "P1";
        String startDateTwo = "2020-01-01";
        String endDateTwo = "2020-01-21";
        Integer sprintDuration = 1;

        SprintIdJPA sprintIdJPA = new SprintIdJPA(projectCode, sprintCode);

        SprintJPA sprintIdJPATwo = new SprintJPA(sprintIdJPA, startDateTwo, sprintDuration, endDateTwo, sprintBacklogJPA);

        int firstHash = sprintIdJPA.hashCode();
        int secondHash = sprintIdJPATwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void emptyConstructor_InitializesFieldsToDefaultValues() {
        SprintJPA sprintJPA = new SprintJPA();

        assertNull(sprintJPA.getSprintIdJPA());
        assertNull(sprintJPA.getStartDate());
        assertEquals(null, sprintJPA.getSprintDuration());
    }

    @Test
    void equals_WithSameObject_ReturnsTrue() {
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");
        SprintIdJPA sprintIdJPA = new SprintIdJPA(projectCode.toString(), sprintCode.toString());
        String startDate = "2023-05-31";
        Integer sprintDuration = 7;
        String endDate = "2023-06-07";

        SprintJPA sprintJPA1 = new SprintJPA(sprintIdJPA, startDate, sprintDuration, endDate, sprintBacklogJPA);
        SprintJPA sprintJPA2 = sprintJPA1;

        assertTrue(sprintJPA1.equals(sprintJPA2));
        assertTrue(sprintJPA2.equals(sprintJPA1));
    }
}