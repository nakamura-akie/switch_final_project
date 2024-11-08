package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.dto.OutputResourceDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputResourceAssemblerTest {

    @Test
    void generateOutputResourceDTO() {
        Resource resourceDouble = mock(Resource.class);

        Email resourceEmail = new Email("john@gmail.com");
        ProjectCode projectCode = new ProjectCode("P1");
        TimePeriod periodOfAllocation = new TimePeriod(
                LocalDate.parse("2022-03-01"),
                LocalDate.parse("2022-04-01"));
        ResourceID resourceID = new ResourceID(resourceEmail,projectCode,periodOfAllocation);
        ProjectRole role = ProjectRole.TEAM_MEMBER;
        PercentageOfAllocation percentageOfAllocation = new PercentageOfAllocation(50);
        CostPerHour costPerHour = new CostPerHour(7.5);

        when(resourceDouble.identity()).thenReturn(resourceID);
        when(resourceDouble.getRoleInProject()).thenReturn(role);
        when(resourceDouble.getPercentageOfAllocation()).thenReturn(percentageOfAllocation);
        when(resourceDouble.getCostPerHour()).thenReturn(costPerHour);

        OutputResourceDTO expected = new OutputResourceDTO(
        "john@gmail.com",
        "P1",
        "2022-03-01",
        "2022-04-01",
        "TEAM_MEMBER",
        50,
        7.5);

        OutputResourceDTO result = OutputResourceAssembler.generateOutputResourceDTO(resourceDouble);

        assertEquals(expected,result);
    }
}