package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.switch2022.project.datamodel.jpa.resource.ResourceIdJPA;
import org.switch2022.project.datamodel.jpa.resource.ResourceJPA;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.valueobject.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResourceDomainDataAssemblerTest {

    private static ResourceDomainDataAssembler resourceDomainDataAssembler;
    private static Resource resourceDouble;
    private static ResourceJPA resourceJPA;

    @BeforeAll
    public static void init() {
        ResourceFactory resourceFactoryDouble = mock(ResourceFactory.class);

        resourceDouble = mock(Resource.class);
        resourceDomainDataAssembler = new ResourceDomainDataAssembler(resourceFactoryDouble);


        String emailJPA = "test@test.com";
        String projectCodeJPA = "P1";
        String startDateJPA = "2022-03-01";
        String endDateJPA = "2022-04-01";

        ResourceID resourceID = new ResourceID(
                new Email(emailJPA),
                new ProjectCode(projectCodeJPA),
                new TimePeriod(
                        LocalDate.parse(startDateJPA),
                        LocalDate.parse(endDateJPA))
        );

        ResourceIdJPA resourceIdJPA = new ResourceIdJPA(
                emailJPA,
                projectCodeJPA,
                startDateJPA,
                endDateJPA);

        resourceJPA = new ResourceJPA(
                resourceIdJPA,
                "TEAM_MEMBER",
                50,
                5.5);

        ProjectRole resourceProjectRoleDouble = mock(ProjectRole.class);
        PercentageOfAllocation resourceAllocationDouble = mock(PercentageOfAllocation.class);
        CostPerHour resourceCostPerHourDouble = mock(CostPerHour.class);

        when(resourceDouble.identity()).thenReturn(resourceID);


        when(resourceDouble.getRoleInProject()).thenReturn(resourceProjectRoleDouble);
        when(resourceProjectRoleDouble.name()).thenReturn("TEAM_MEMBER");

        when(resourceDouble.getPercentageOfAllocation()).thenReturn(resourceAllocationDouble);
        when(resourceAllocationDouble.getPercentageValue()).thenReturn(50);

        when(resourceDouble.getCostPerHour()).thenReturn(resourceCostPerHourDouble);
        when(resourceCostPerHourDouble.getCostPerHourValue()).thenReturn(5.5);

        when(resourceFactoryDouble.createResource(any(), any(), any(),
                any())).thenReturn(resourceDouble);
    }

    @Test
    void constructor_NullResourceFactory_ThrowsException() {
        String expected = "Resource Factory cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ResourceDomainDataAssembler(null);}).getMessage();

        assertEquals(expected, result);
    }


    @Test
    void toData_MapResourceToResourceJPA_Equals() {
        ResourceJPA result = resourceDomainDataAssembler.toData(resourceDouble);

        assertEquals(resourceJPA, result);
    }

    @Test
    void toDomain_MapResourceJpaToResource_Equals() {
        Resource result = resourceDomainDataAssembler.toDomain(resourceJPA);

        assertEquals(resourceDouble, result);
    }
}