package org.switch2022.project.datamodel.jpa.projecttypology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTypologyJPATest {
    private static ProjectTypologyJPA projectTypologyJPA;

    @BeforeAll
    static void init() {
        projectTypologyJPA = new ProjectTypologyJPA("TestTypology");
    }

    @Test
    void emptyConstructor() {
        ProjectTypologyJPA projectTypologyJPA = new ProjectTypologyJPA();
        assertNotNull(projectTypologyJPA);
    }

    @Test
    void getProjectTypologyName_ProjectTypologyName() {
        String expected = "TestTypology";
        String result = projectTypologyJPA.getProjectTypologyName();

        assertEquals(expected, result);
    }

    @Test
    void testEquals_SameObjects_True() {
        ProjectTypologyJPA sameObject = projectTypologyJPA;
        assertTrue(projectTypologyJPA.equals(sameObject));
    }

    @Test
    void testEquals_NullObject_NotEquals() {
        assertFalse(projectTypologyJPA.equals(null));
    }

    @Test
    void testEquals_ObjectOfDifferentClass_False() {
        String objectOfDifferentClass = "ProjectTypologyTest";
        assertFalse(projectTypologyJPA.equals(objectOfDifferentClass));
    }

    @Test
    void testEquals_ObjectOfSameClassButDifferentTypologyName_False() {
        ProjectTypologyJPA differentProjectTypologyName = new ProjectTypologyJPA("DifferentName");
        assertFalse(projectTypologyJPA.equals(differentProjectTypologyName));
    }

    @Test
    void testEquals_ObjectOfSameClassAndSameAttributes_True() {
        ProjectTypologyJPA differentProjectJPA = new ProjectTypologyJPA("TestTypology");
        assertTrue(projectTypologyJPA.equals(differentProjectJPA));
    }


    @Test
    void testHashCode() {
        int expected = Objects.hash(
                projectTypologyJPA.getProjectTypologyName()
        );
        int result = projectTypologyJPA.hashCode();

        assertEquals(expected, result);
    }
}