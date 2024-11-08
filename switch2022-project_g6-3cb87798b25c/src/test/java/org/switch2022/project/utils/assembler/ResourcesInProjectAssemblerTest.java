package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.assembler.ResourcesInProjectAssembler;
import org.switch2022.project.utils.dto.ResourceDTO;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourcesInProjectAssemblerTest {

    ProjectCode projectCodeOne;
    Resource mariaResource;
    Resource anaResource;
    ResourceDTO resourceDTOMaria;
    ResourceDTO resourceDTOAna;


    @BeforeEach
    void init() {

        this.mariaResource = mock(Resource.class);
        this.anaResource = mock(Resource.class);

        ResourceID mariaResourceID = mock(ResourceID.class);
        ResourceID anaResourceID = mock(ResourceID.class);

        Email mariaEmail = mock(Email.class);
        Email anaEmail = mock(Email.class);
        when(mariaEmail.getEmailValue()).thenReturn("email1@email.com");
        when(anaEmail.getEmailValue()).thenReturn("email2@email.com");

        projectCodeOne = mock(ProjectCode.class);
        when(projectCodeOne.getProjectCodeValue()).thenReturn("P001");

        ProjectRole projectRoleSM = mock(ProjectRole.class);
        ProjectRole projectRoleTM = mock(ProjectRole.class);
        when(projectRoleSM.toString()).thenReturn("sm");
        when(projectRoleTM.toString()).thenReturn("tm");

        TimePeriod mariaTimePeriodDouble = mock(TimePeriod.class);
        TimePeriod anaTimePeriodDouble = mock(TimePeriod.class);

        LocalDate mariaStartDate = LocalDate.of(2020,1,1);
        LocalDate mariaEndDate = LocalDate.of(2021,1,1);
        when(mariaResourceID.getPeriodOfAllocation()).thenReturn(mariaTimePeriodDouble);
        when(mariaTimePeriodDouble.getStartDate()).thenReturn(mariaStartDate);
        when(mariaTimePeriodDouble.getEndDate()).thenReturn(mariaEndDate);

        LocalDate anaStartDate = LocalDate.of(2020,1,1);
        LocalDate anaEndDate = LocalDate.of(2021,1,1);
        when(anaResourceID.getPeriodOfAllocation()).thenReturn(anaTimePeriodDouble);
        when(anaTimePeriodDouble.getStartDate()).thenReturn(anaStartDate);
        when(anaTimePeriodDouble.getEndDate()).thenReturn(anaEndDate);

        when(mariaResourceID.getResourceEmail()).thenReturn(mariaEmail);
        when(anaResourceID.getResourceEmail()).thenReturn(anaEmail);

        when(mariaResource.identity()).thenReturn(mariaResourceID);
        when(anaResource.identity()).thenReturn(anaResourceID);


        when(mariaResource.identity().getResourceEmail()).thenReturn(mariaEmail);
        when(mariaResource.identity().getProjectCode()).thenReturn(projectCodeOne);
        when(mariaResource.getRoleInProject()).thenReturn(projectRoleSM);


        when(anaResource.identity().getResourceEmail()).thenReturn(anaEmail);
        when(anaResource.identity().getProjectCode()).thenReturn(projectCodeOne);
        when(anaResource.getRoleInProject()).thenReturn(projectRoleTM);

        this.resourceDTOMaria = new ResourceDTO();
        resourceDTOMaria.projectRole = "sm";
        resourceDTOMaria.projectCode = "P001";
        resourceDTOMaria.email = "email1@email.com";
        resourceDTOMaria.startDate = "2020-01-01";
        resourceDTOMaria.endDate = "2021-01-01";

        this.resourceDTOAna = new ResourceDTO();
        resourceDTOAna.projectRole = "tm";
        resourceDTOAna.projectCode = "P001";
        resourceDTOAna.email = "email2@email.com";
        resourceDTOAna.startDate = "2020-01-01";
        resourceDTOAna.endDate = "2021-01-01";

    }

    @Test
    void resourceInProjectList_ReturnsList() {

        List<Resource> resourceList = Arrays.asList(mariaResource, anaResource);
        Iterator<Resource> resourceIterator = resourceList.iterator();

        List<ResourceDTO> resourceDTOList = Arrays.asList(resourceDTOMaria, resourceDTOAna);

        List<ResourceDTO> result = ResourcesInProjectAssembler.resourceInProjectList(resourceIterator);

        assertEquals(resourceDTOList, result);
    }

    @Test
    void resourceIProjectList_DifferentList_ReturnFalse() {
        List<Resource> emptyList = new ArrayList<>();
        Iterator<Resource> resourceIterator = emptyList.iterator();

        List<ResourceDTO> resourceDTOList = Arrays.asList(resourceDTOMaria, resourceDTOAna);

        List<ResourceDTO> result = ResourcesInProjectAssembler.resourceInProjectList(resourceIterator);

        assertNotEquals(resourceDTOList, result);
    }

    @Test
    void resourceInProjectList_EmptyIterator_ReturnsEmptyList() {
        List<Resource> resourceList = new ArrayList<>();

        Iterator<Resource> resourceIterator = resourceList.iterator();

        List<ResourceDTO> resourceDTOList = new ArrayList<>();

        List<ResourceDTO> result = ResourcesInProjectAssembler.resourceInProjectList(resourceIterator);

        assertEquals(resourceDTOList, result);
    }

}