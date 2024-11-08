package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;

import static org.junit.jupiter.api.Assertions.*;

class SprintIdJPATest {

    private static SprintIdJPA sprintIdJPA;


    @BeforeAll
    static void init() {
        sprintIdJPA = new SprintIdJPA("P1", "S1");
    }

    @AfterAll
    static void tearDown() {
        sprintIdJPA = null;
    }

    @Test
    void getProjectCode_ProjectCode() {
       String projectCode = sprintIdJPA.getProjectCodeValue();

       assertEquals("P1", projectCode);
    }

    @Test
    void getSprintCode() {
        String sprintCode = sprintIdJPA.getSprintCode();

        assertEquals("S1", sprintCode);
    }

    @Test
    void setProjectCode_NewProjectCode() {
        sprintIdJPA.setProjectCode("P2");
        String newProjectCode = sprintIdJPA.getProjectCodeValue();

        assertEquals("P2", newProjectCode);
    }

    @Test
    void setSprintCode_NewSprintCode() {
        SprintIdJPA sprintIdJPATwo = new SprintIdJPA("P1", "S1");
        sprintIdJPATwo.setSprintCode("S2");
        String newSprintCode = sprintIdJPATwo.getSprintCode();

        assertEquals("S2", newSprintCode);

    }

    @Test
    void equals_EqualObject_True() {
        String projectCode = "P1";
        String sprintCode = "S1";

        SprintIdJPA sprintIdJPATwo = new SprintIdJPA(projectCode, sprintCode);

        boolean result = sprintIdJPA.equals(sprintIdJPATwo);

        assertTrue(result);
    }

   @Test
    void equals_DifferentProjectCodeObject_False() {
       String projectCode = "P2";
       String sprintCode = "S1";

       SprintIdJPA sprintIdJPATwo = new SprintIdJPA(projectCode, sprintCode);

       boolean result = sprintIdJPA.equals(sprintIdJPATwo);

       assertFalse(result);
    }

    @Test
    void equals_DifferentSprintCodeObject_False() {
        String projectCode = "P1";
        String sprintCode = "S2";

        SprintIdJPA sprintIdJPATwo = new SprintIdJPA(projectCode, sprintCode);

        boolean result = sprintIdJPA.equals(sprintIdJPATwo);

        assertFalse(result);
    }


    @Test
    void equals_ObjectOfDifferentType_False() {
        String object = "not a sprint ID";

        boolean result = sprintIdJPA.equals(object);

        assertFalse(result);

    }

    @Test
    void equals_NullObject_False() {

        boolean result = sprintIdJPA.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        SprintIdJPA sprintIdJPATwo = sprintIdJPA;

        int firstHash = sprintIdJPA.hashCode();
        int secondHash = sprintIdJPATwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        String sprintCode = "S3";
        String projectCode = "P1";
        String projectCodeTwo = "P2";

        SprintIdJPA sprintIdJPA = new SprintIdJPA(projectCode, sprintCode);

        SprintIdJPA sprintIdJPATwo = new SprintIdJPA(projectCodeTwo, sprintCode);

        int firstHash = sprintIdJPA.hashCode();
        int secondHash = sprintIdJPATwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }
}