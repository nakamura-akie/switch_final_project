package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SprintIDTest {

    private ProjectCode projectCode;
    private SprintCode sprintCode;

    private SprintID sprintID;
    private ProjectCode projectCodeTwo;
    private SprintCode sprintCodeTwo;

    @BeforeEach
    void init() {
        projectCode = new ProjectCode("P001");
        sprintCode = new SprintCode("SPT001");
        sprintID = new SprintID(projectCode, sprintCode);
        projectCodeTwo = new ProjectCode("P002");
        sprintCodeTwo = new SprintCode("SPT002");
    }

    @Test
    void equals_sameObject_True() {
        assertEquals(sprintID, sprintID);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        SprintID sprintIDTwo = new SprintID(projectCode, sprintCode);
        assertEquals(sprintID, sprintIDTwo);
    }

    @Test
    void equals_differentProjectCode_False() {
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCode);
        assertNotEquals(sprintID, sprintIDTwo);
    }

    @Test
    void equals_differentSprintCode_False() {
        SprintID sprintIDTwo = new SprintID(projectCode, sprintCodeTwo);
        assertNotEquals(sprintID, sprintIDTwo);
    }

    @Test
    void equals_differentSprintCodeAndDifferentProjectCode_False() {
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        assertNotEquals(sprintID, sprintIDTwo);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        String differentObject = "P001";
        assertNotEquals(sprintID, differentObject);
    }

    @Test
    void equals_compareWithNull_False() {
        SprintID sprintIDTwo = null;
        assertNotEquals(sprintID, sprintIDTwo);
    }

    @Test
    void testHashCode_True() {
        SprintID sprintIDTwo = new SprintID(projectCode, sprintCode);
        int expected = sprintIDTwo.hashCode();
        int result = sprintID.hashCode();
        assertEquals(expected, result);
    }

    @Test
    void testHashCode_False() {
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        int expected = sprintIDTwo.hashCode();
        int result = sprintID.hashCode();
        assertNotEquals(expected, result);
    }

    @Test
    void hasProjectCode_DifferentObjectWithSameProjectCode_True() {
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        ProjectCode sameProjectCode = new ProjectCode("P001");
        boolean result = sprintID.hasProjectCode(sameProjectCode);
        assertTrue(result);
    }

    @Test
    void hasProjectCode_SameObject_True() {
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        boolean result = sprintID.hasProjectCode(projectCode);
        assertTrue(result);
    }

    @Test
    void hasProjectCode_DifferentObjectWithDifferentProjectCode_False() {
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        ProjectCode sameProjectCode = new ProjectCode("P002");
        boolean result = sprintID.hasProjectCode(sameProjectCode);
        assertFalse(result);
    }

    @Test
    void getProjectCode_ReturnsProjectCode_True() {
        String expected = "P001";
        String result = sprintID.getProjectCode().getProjectCodeValue();

        assertEquals(expected, result);
    }

    @Test
    void getSprintCode_ReturnsSprintCode_True() {
        String expected = "SPT001";
        String result = sprintID.getSprintCode().getSprintCodeValue();

        assertEquals(expected, result);
    }
}