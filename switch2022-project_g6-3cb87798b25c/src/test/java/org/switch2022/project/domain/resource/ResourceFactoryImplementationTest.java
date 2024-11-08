package org.switch2022.project.domain.resource;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.CostPerHour;
import org.switch2022.project.domain.valueobject.PercentageOfAllocation;
import org.switch2022.project.domain.valueobject.ProjectRole;
import org.switch2022.project.domain.valueobject.ResourceID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ResourceFactoryImplementationTest {

    @Test
    void createResource_HappyPath() {
        ResourceFactoryImplementation factoryTest =
                new ResourceFactoryImplementation();

        ResourceID id = mock(ResourceID.class);
        ProjectRole role = mock(ProjectRole.class);
        PercentageOfAllocation allocation = mock(PercentageOfAllocation.class);
        CostPerHour costPerHour = mock(CostPerHour.class);

        Resource result = factoryTest.createResource(id, role, allocation,
                costPerHour);

        ResourceID resultID = result.identity();
        ProjectRole resultRole = result.getRoleInProject();
        PercentageOfAllocation resultAllocation =
                result.getPercentageOfAllocation();
        CostPerHour resultCostPerHour = result.getCostPerHour();

        assertEquals(id, resultID);
        assertEquals(role, resultRole);
        assertEquals(allocation, resultAllocation);
        assertEquals(costPerHour, resultCostPerHour);
    }
}