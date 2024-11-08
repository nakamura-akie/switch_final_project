package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutputResourceDTOTest {
    static OutputResourceDTO dtoOne;

    @BeforeAll
    static void init() {
        dtoOne = new OutputResourceDTO(
                "pedro@mail.com",
                "P1",
                "2020-01-01",
                "2020-09-09",
                "TEAM_MEMBER",
                100,
                20.0);
    }

    @Test
    public void testConstructor() {
        String email = "pedro@mail.com";
        String projectCode = "P1";
        String startDate = "2020-01-01";
        String endDate = "2020-09-09";
        String projectRole = "TEAM_MEMBER";
        Integer percentageOfAllocation = 100;
        Double costPerHour = 20.0;

        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(email, projectCode, startDate, endDate,
                projectRole, percentageOfAllocation, costPerHour);

        assertNotNull(outputResourceDTO);
        assertEquals(email, outputResourceDTO.getEmail());
        assertEquals(projectCode, outputResourceDTO.getProjectCode());
        assertEquals(startDate, outputResourceDTO.getStartDate());
        assertEquals(endDate, outputResourceDTO.getEndDate());
        assertEquals(projectRole, outputResourceDTO.getProjectRole());
        assertEquals(percentageOfAllocation, outputResourceDTO.getPercentageOfAllocation());
        assertEquals(costPerHour, outputResourceDTO.getCostPerHour());
    }


    @Test
    void testEquals_sameObject_Equals() {
        OutputResourceDTO otherDto = dtoOne;

        assertEquals(dtoOne, otherDto);
    }

    @Test
    void testEquals_NullObject_NotEquals() {
        assertFalse(dtoOne.equals(null));
    }


    @Test
    void equals_ObjectOfDifferentClass_NotEqual() {
        String testObject = "DifferentClass";

        assertFalse(dtoOne.equals(testObject));
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentEmail_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "ines@mail.com",
        "P1",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        assertNotEquals(dtoTwo, dtoOne);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectCode_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P2",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        assertNotEquals(dtoTwo, dtoOne);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentStartDate_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P1",
        "2020-01-15" + "",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        assertNotEquals(dtoTwo, dtoOne);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentEndDate_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P1",
        "2020-01-01",
        "2020-10-10",
        "TEAM_MEMBER",
        100,
        20.0);

        assertNotEquals(dtoTwo, dtoOne);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectRole_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P2",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        assertNotEquals(dtoTwo, dtoOne);
    }


    @Test
    void equals_ObjectsOfSameClassButDifferentCostPerHour_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P1",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        10.0);

        assertNotEquals(dtoTwo, dtoOne);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_Equals() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P1",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        assertEquals(dtoTwo, dtoOne);
    }

    @Test
    void testHashCode_Equal() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "pedro@mail.com",
        "P1",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        int one = dtoOne.hashCode();
        int two = dtoTwo.hashCode();

        assertEquals(one, two);
    }

    @Test
    void testHashCode_NotEqual() {
        OutputResourceDTO dtoTwo = new OutputResourceDTO(
        "ines@mail.com",
        "P1",
        "2020-01-01",
        "2020-09-09",
        "TEAM_MEMBER",
        100,
        20.0);

        int one = dtoOne.hashCode();
        int two = dtoTwo.hashCode();

        assertNotEquals(one, two);
    }

    @Test
    public void getters() {
        String email = "pedro@mail.com";
        String projectCode = "P1";
        String startDate = "2020-01-01";
        String endDate = "2020-09-09";
        String projectRole = "TEAM_MEMBER";
        Integer percentageOfAllocation = 100;
        Double costPerHour = 20.0;

        assertEquals(email, dtoOne.getEmail());
        assertEquals(projectCode, dtoOne.getProjectCode());
        assertEquals(startDate, dtoOne.getStartDate());
        assertEquals(endDate, dtoOne.getEndDate());
        assertEquals(projectRole, dtoOne.getProjectRole());
        assertEquals(percentageOfAllocation, dtoOne.getPercentageOfAllocation());
        assertEquals(costPerHour, dtoOne.getCostPerHour());
    }

    @Test
    public void testSetters() {
        OutputResourceDTO outputResourceDTO = new OutputResourceDTO(
                "test@example.com",
                "PROJ-001",
                "2023-01-01",
                "2023-12-31",
                "Developer",
                80,
                50.0);

        String newEmail = "pedro@mail.com";
        outputResourceDTO.setEmail(newEmail);
        assertEquals(newEmail, outputResourceDTO.getEmail());

        String newProjectCode = "P1";
        outputResourceDTO.setProjectCode(newProjectCode);
        assertEquals(newProjectCode, outputResourceDTO.getProjectCode());

        String newStartDate = "2020-01-01";
        outputResourceDTO.setStartDate(newStartDate);
        assertEquals(newStartDate, outputResourceDTO.getStartDate());

        String newEndDate = "2020-09-09";
        outputResourceDTO.setEndDate(newEndDate);
        assertEquals(newEndDate, outputResourceDTO.getEndDate());

        String newProjectRole = "TEAM_MEMBER";
        outputResourceDTO.setProjectRole(newProjectRole);
        assertEquals(newProjectRole, outputResourceDTO.getProjectRole());

        Integer newPercentageOfAllocation = 100;
        outputResourceDTO.setPercentageOfAllocation(newPercentageOfAllocation);
        assertEquals(newPercentageOfAllocation, outputResourceDTO.getPercentageOfAllocation());

        Double newCostPerHour = 20.0;
        outputResourceDTO.setCostPerHour(newCostPerHour);
        assertEquals(newCostPerHour, outputResourceDTO.getCostPerHour());
    }
}