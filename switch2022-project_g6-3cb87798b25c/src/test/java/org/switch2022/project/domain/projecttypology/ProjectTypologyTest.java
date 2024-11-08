package org.switch2022.project.domain.projecttypology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTypologyTest {

    ProjectTypology projectTypologyOne;
    ProjectTypology projectTypologyTwo;

    ProjectTypologyName projectTypologyNameOne;
    ProjectTypologyName projectTypologyNameTwo;

    @BeforeEach
    void init() {

        projectTypologyNameOne = new ProjectTypologyName("One");
        projectTypologyNameTwo = new ProjectTypologyName("Two");

        projectTypologyOne = new ProjectTypology(projectTypologyNameOne);
        projectTypologyTwo = new ProjectTypology(projectTypologyNameTwo);

    }

    @AfterEach
    void tearDown() {
        projectTypologyOne = null;
        projectTypologyTwo = null;
        projectTypologyNameOne = null;
        projectTypologyNameTwo = null;
    }

    @Test
    void constructor_CheckIfExceptionIsThrownWhenNameIsNull_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProjectTypology(null));

        assertEquals("Invalid Project Typology AccountName.", exception.getMessage());
    }


    @Test
    void identity_SameProjectTypologyName_Equals() {
        ProjectTypologyName result = projectTypologyOne.identity();
        assertEquals(projectTypologyNameOne, result);
    }

    @Test
    void identity_DifferentProjectTypologyName_NotEquals() {
        ProjectTypologyName result = projectTypologyTwo.identity();
        assertNotEquals(projectTypologyNameOne, result);
    }

    @Test
    void sameAs_SameProjectTypologyName_ReturnTrue() {
        ProjectTypology projectTypology = new ProjectTypology(projectTypologyNameOne);
        boolean result = projectTypologyOne.sameAs(projectTypology);

        assertTrue(result);
    }

    @Test
    void sameAs_DifferentProjectTypologyName_ReturnFalse() {
        boolean result = projectTypologyOne.sameAs(projectTypologyTwo);
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentObjects_ReturnFalse() {
        String projectTypology = "Not";

        boolean result = projectTypologyOne.sameAs(projectTypology);
        assertFalse(result);
    }

    @Test
    void equals_SameObjects_ReturnsTrue() {
        assertEquals(projectTypologyOne, projectTypologyOne);
    }

    @Test
    void equals_SameTypeOfObjectButDifferentAttributes_ReturnsFalse() {
        assertNotEquals(projectTypologyOne, projectTypologyTwo);
    }

    @Test
    void equals_ObjectsWithSameName_ReturnsTrue() {
        ProjectTypology projectTypology = new ProjectTypology(projectTypologyNameOne);
        boolean result = projectTypologyOne.equals(projectTypology);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentNames_ReturnFalse() {
        boolean result = projectTypologyOne.equals(projectTypologyTwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjects_ReturnsFalse() {
        boolean result = projectTypologyOne.equals(projectTypologyNameOne);
        assertFalse(result);
    }

    @Test
    void equals_CompareObjectWithNull_ReturnFalse() {
        ProjectTypology nullProjectTypology = null;

        boolean result = projectTypologyOne.equals(nullProjectTypology);
        assertFalse(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        int hashCodeOne = projectTypologyOne.hashCode();
        int hashCodeTwo = projectTypologyOne.hashCode();

        assertEquals(hashCodeOne, hashCodeTwo);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        int hashCodeOne = projectTypologyOne.hashCode();
        int hashCodeTwo = projectTypologyTwo.hashCode();

        assertNotEquals(hashCodeOne, hashCodeTwo);
    }

    @Test
    void getProjectTypologyName_ReturnsCorrectName() {
        String typologyName = "Project Typology Name";
        ProjectTypologyName projectTypologyName = new ProjectTypologyName(typologyName);
        ProjectTypology projectTypology = new ProjectTypology(projectTypologyName);

        assertEquals(typologyName, projectTypology.getProjectTypologyName());
    }

}