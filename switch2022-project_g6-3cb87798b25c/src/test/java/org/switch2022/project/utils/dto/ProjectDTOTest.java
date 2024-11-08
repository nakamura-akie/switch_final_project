package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.ProjectDTO;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDTOTest {

    private ProjectDTO firstProjectDTO;

    @BeforeEach
    void init() {
        firstProjectDTO = new ProjectDTO();
        firstProjectDTO.projectCode = "P1";
        firstProjectDTO.projectName = "First Project";
        firstProjectDTO.projectCustomer = "145234343";
        firstProjectDTO.projectStatus = "PLANNED";
    }

    @AfterEach
    void tearDown() {
        firstProjectDTO = null;
    }

    @Test
    void equals_NullObject_False() {
        boolean result = firstProjectDTO.equals(null);

        assertFalse(result);
    }

    @Test
    void equals_SameObject_True() {
        ProjectDTO anotherProjectDTO = firstProjectDTO;

        boolean result = firstProjectDTO.equals(anotherProjectDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsOfDifferentClasses_False() {
        String project = "This is not a project";

        boolean result = firstProjectDTO.equals(project);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithSameAttributes_True() {
        ProjectDTO thirdProjectDTO = new ProjectDTO();
        thirdProjectDTO.projectCode = "P1";
        thirdProjectDTO.projectName = "First Project";
        thirdProjectDTO.projectCustomer = "145234345";
        thirdProjectDTO.projectStatus = "PLANNED";

        boolean result = firstProjectDTO.equals(thirdProjectDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentProjectCode_False() {
        ProjectDTO secondProjectDTO = new ProjectDTO();
        secondProjectDTO.projectCode = "P2";
        secondProjectDTO.projectName = "First Project";
        secondProjectDTO.projectCustomer = "145234343";
        secondProjectDTO.projectStatus = "PLANNED";

        boolean result = firstProjectDTO.equals(secondProjectDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithDifferentProjectName_True() {
        ProjectDTO fourthProjectDTO = new ProjectDTO();
        fourthProjectDTO.projectCode = "P1";
        fourthProjectDTO.projectName = "Fourth Project";
        fourthProjectDTO.projectCustomer = "145234343";
        fourthProjectDTO.projectStatus = "PLANNED";

        boolean result = firstProjectDTO.equals(fourthProjectDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentProjectCustomer_True() {
        ProjectDTO fifthProjectDTO = new ProjectDTO();
        fifthProjectDTO.projectCode = "P1";
        fifthProjectDTO.projectName = "First Project";
        fifthProjectDTO.projectCustomer = "145234343";
        fifthProjectDTO.projectStatus = "PLANNED";

        boolean result = firstProjectDTO.equals(fifthProjectDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentProjectStatus_True() {
        ProjectDTO sixthProjectDTO = new ProjectDTO();
        sixthProjectDTO.projectCode = "P1";
        sixthProjectDTO.projectName = "First Project";
        sixthProjectDTO.projectCustomer = "145234343";
        sixthProjectDTO.projectStatus = "BLOCKED";

        boolean result = firstProjectDTO.equals(sixthProjectDTO);

        assertTrue(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        ProjectDTO anotherProjectDTO = firstProjectDTO;

        int firstHashCode = firstProjectDTO.hashCode();
        int secondHashCode = anotherProjectDTO.hashCode();

        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        ProjectDTO secondProjectDTO = new ProjectDTO();
        secondProjectDTO.projectCode = "P2";
        secondProjectDTO.projectName = "First Project";
        secondProjectDTO.projectCustomer = "145234343";
        secondProjectDTO.projectStatus = "PLANNED";

        int firstHashCode = firstProjectDTO.hashCode();
        int secondHashCode = secondProjectDTO.hashCode();

        assertNotEquals(firstHashCode, secondHashCode);
    }

    @Test
    void testToString() {
        ProjectDTO testProjectDTO = new ProjectDTO();
        testProjectDTO.projectCode = "P2";
        testProjectDTO.projectName = "First Project";
        testProjectDTO.projectCustomer = "145234343";
        testProjectDTO.projectStatus = "PLANNED";

        String expected = "ProjectDTO{" +
                "projectCode='P2'" +
                ", projectName='First Project'" +
                ", projectCustomer='145234343'" +
                ", projectStatus='PLANNED'" +
                '}';

        String result = testProjectDTO.toString();

        assertEquals(expected,result);
    }
}