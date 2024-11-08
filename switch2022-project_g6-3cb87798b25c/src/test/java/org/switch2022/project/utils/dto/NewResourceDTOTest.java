package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class NewResourceDTOTest {
    private static NewResourceDTO newResourceDTO;

    @BeforeAll
    static void setup() {
        newResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-02-04",
                "2022-04-06",
                "TEAM_MEMBER",
                "60",
                "20"
        );
    }

    @AfterAll
    static void tearDown() {
        newResourceDTO = null;
    }


    @Test
    void equals_SameObject_True() {
        NewResourceDTO sameResourceDTO = newResourceDTO;

        boolean result = newResourceDTO.equals(sameResourceDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        NewResourceDTO nullObject = null;

        boolean result = newResourceDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String differentClass = "not a new Customer DTO.";

        boolean result = newResourceDTO.equals(differentClass);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentEmail_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "john@ana.com",
                "A1",
                "2022-02-04",
                "2022-04-06",
                "TEAM_MEMBER",
                "60",
                "20"
        );
        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-02-04",
                "2022-04-06",
                "TEAM_MEMBER",
                "60",
                "20"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectCode_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A2",
                "2022-02-04",
                "2022-04-06",
                "TEAM_MEMBER",
                "60",
                "20"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentStartDate_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-01-01",
                "2022-04-06",
                "TEAM_MEMBER",
                "60",
                "20"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentEndDate_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-02-04",
                "2022-06-06",
                "TEAM_MEMBER",
                "60",
                "20"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentCostPerHour_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-02-04",
                "2022-04-06",
                "TEAM_MEMBER",
                "60",
                "15"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentPErcentageOfAllocation_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-02-04",
                "2022-04-06",
                "TEAM_MEMBER",
                "70",
                "20"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectRole_False() {
        NewResourceDTO anotherResourceDTO = new NewResourceDTO(
                "ana@ana.com",
                "A1",
                "2022-02-04",
                "2022-04-06",
                "PROJECT_MANAGER",
                "60",
                "20"
        );

        boolean result = newResourceDTO.equals(anotherResourceDTO);

        assertFalse(result);
    }


    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(newResourceDTO);
        int result = newResourceDTO.hashCode();

        assertEquals(expected, result);
    }

    @Test
    public void testConstructor() {
        String email = "test@example.com";
        String projectCode = "PRJ001";
        String startDate = "2023-01-01";
        String endDate = "2023-01-31";
        String projectRole = "TEAM_MEMBER";
        String percentageOfAllocation = "80";
        String costPerHour = "50";

        NewResourceDTO resourceDTO = new NewResourceDTO(email, projectCode, startDate, endDate,
                projectRole, percentageOfAllocation, costPerHour);

        assertEquals(email, resourceDTO.getEmail().getEmailValue());
        assertEquals(projectCode, resourceDTO.getProjectCode().getProjectCodeValue());
        assertEquals(startDate, resourceDTO.getStartDate().toString());
        assertEquals(endDate, resourceDTO.getEndDate().toString());
        assertEquals(projectRole, resourceDTO.getProjectRole().toString());
        assertEquals(80, resourceDTO.getPercentageOfAllocation().getPercentageValue());
        assertEquals(50, resourceDTO.getCostPerHour().getCostPerHourValue());
    }

    @Test
    public void getters() {
        NewResourceDTO resourceDTO = new NewResourceDTO("test@example.com", "PRJ001", "2023-01-01",
                "2023-01-31", "TEAM_MEMBER", "80", "50");

        assertEquals("test@example.com", resourceDTO.getEmail().toString());
        assertEquals("PRJ001", resourceDTO.getProjectCode().toString());
        assertEquals(LocalDate.parse("2023-01-01"), resourceDTO.getStartDate());
        assertEquals(LocalDate.parse("2023-01-31"), resourceDTO.getEndDate());
        assertEquals("TEAM_MEMBER", resourceDTO.getProjectRole().toString());
        assertEquals(80, resourceDTO.getPercentageOfAllocation().getPercentageValue());
        assertEquals(50, resourceDTO.getCostPerHour().getCostPerHourValue());
    }

    @Test
    public void setters() {
        NewResourceDTO resourceDTO = new NewResourceDTO(
                "test@example.com",
                "PRJ001",
                "2023-01-01",
                "2023-01-31",
                "TEAM_MEMBER",
                "80",
                "50");

        resourceDTO.setEmail(new Email("test@example.com"));
        assertEquals("test@example.com", resourceDTO.getEmail().getEmailValue());

        resourceDTO.setProjectCode(new ProjectCode("PRJ001"));
        assertEquals("PRJ001", resourceDTO.getProjectCode().getProjectCodeValue());

        resourceDTO.setStartDate(LocalDate.of(2023, 1, 1));
        assertEquals("2023-01-01", resourceDTO.getStartDate().toString());

        resourceDTO.setEndDate(LocalDate.of(2023, 1, 31));
        assertEquals("2023-01-31", resourceDTO.getEndDate().toString());

        resourceDTO.setProjectRole(ProjectRole.fromString("TEAM_MEMBER"));
        assertEquals("TEAM_MEMBER", resourceDTO.getProjectRole().toString());

        resourceDTO.setPercentageOfAllocation(new PercentageOfAllocation("80"));
        assertEquals(80, resourceDTO.getPercentageOfAllocation().getPercentageValue());

        resourceDTO.setCostPerHour(new CostPerHour("50"));
        assertEquals(50, resourceDTO.getCostPerHour().getCostPerHourValue());
    }
}