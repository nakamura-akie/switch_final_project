package org.switch2022.project.utils.dto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.ProjectByResourceDTO;

import static org.junit.jupiter.api.Assertions.*;

class ProjectByResourceDTOTest {

    private ProjectByResourceDTO dto1;
    private ProjectByResourceDTO dto2;

    @BeforeEach
    void init() {

        dto1 = new ProjectByResourceDTO();
        dto1.projectCode = "P01";
        dto1.projectName = "Project 1";
        dto1.projectDescription = "Description 1";

        dto2 = new ProjectByResourceDTO();
        dto2.projectCode = "P02";
        dto2.projectName = "Project 2";
        dto2.projectDescription = "Description 2";
    }
    @AfterEach
    void tearDown() {
        dto1 = null;
        dto2 = null;
    }

    @Test
    void testEquals_SameObject_ReturnsTrue() {
        assertEquals(dto1, dto1);
    }

    @Test
    void testEquals_EqualObjects_ReturnsTrue() {
        ProjectByResourceDTO dto3 = new ProjectByResourceDTO();
        dto3.projectCode = "P01";
        dto3.projectName = "Project 1";
        dto3.projectDescription = "Description 1";
        assertEquals(dto1, dto3);
    }

    @Test
    void testEquals_DifferentObjects_ReturnsFalse() {
        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_NullObject_ReturnsFalse() {
        assertNotEquals(null, dto1);
    }

    @Test
    void testHashCode_EqualObjects_ReturnsSameHashCode() {
        ProjectByResourceDTO dto3 = new ProjectByResourceDTO();
        dto3.projectCode = "P01";
        dto3.projectName = "Project 1";
        dto3.projectDescription = "Description 1";

        assertEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects_ReturnsDifferentHashCodes() {
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void equals_NullObject_ReturnsFalse() {
        ProjectByResourceDTO dto = new ProjectByResourceDTO();
        dto.projectCode = "P1";
        dto.projectName = "Project Name";
        dto.projectDescription = "Description";

        assertFalse(dto.equals(null));
    }


}