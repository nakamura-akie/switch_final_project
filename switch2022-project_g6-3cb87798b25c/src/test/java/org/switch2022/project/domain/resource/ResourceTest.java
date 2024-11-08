
package org.switch2022.project.domain.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;

import java.time.Clock;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceTest {
    private TimePeriod periodOfAllocation;
    private ProjectRole roleInProject;
    private PercentageOfAllocation percentageOfAllocation;
    private CostPerHour costPerHour;
    private ResourceID resourceID;
    private Resource testResource;

    @BeforeEach
    void init() {
        periodOfAllocation = mock(TimePeriod.class);
        roleInProject = mock(ProjectRole.class);
        percentageOfAllocation = mock(PercentageOfAllocation.class);
        costPerHour = mock(CostPerHour.class);
        resourceID = mock(ResourceID.class);

        testResource = new Resource(
                resourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour);
    }


    @Test
    void hasAccount_SameEmail_True() {
        Email testeEmail = mock(Email.class);

        when(resourceID.hasEmail(testeEmail)).thenReturn(true);

        boolean result = testResource.hasAccount(testeEmail);
        assertTrue(result);
    }

    @Test
    void hasAccount_DifferentEmail_False() {
        Email testeEmail = mock(Email.class);

        when(resourceID.hasEmail(testeEmail)).thenReturn(false);

        boolean result = testResource.hasAccount(testeEmail);
        assertFalse(result);
    }

    @Test
    void getPercentageOfAllocation_ReturnPercentageOfAllocation_PercentageOfAllocation() {

        PercentageOfAllocation expected = percentageOfAllocation;
        PercentageOfAllocation result = testResource.getPercentageOfAllocation();

        assertEquals(expected, result);
    }

    @Test
    void identity_ReturnResourceID_ResourceID() {
        ResourceID expected = resourceID;

        ResourceID result = testResource.identity();

        assertEquals(expected, result);
    }


    @Test
    void isTimePeriodOverlap_TimePeriodOverlapsResourceTimePeriod_True() {
        LocalDate startDate = mock(LocalDate.class);
        LocalDate endDate = mock(LocalDate.class);
        TimePeriod testTimePeriod = mock(TimePeriod.class);

        when(testTimePeriod.getStartDate()).thenReturn(startDate);
        when(testTimePeriod.getEndDate()).thenReturn(endDate);
        when(periodOfAllocation.isOverlap(startDate, endDate)).thenReturn(true);
        when(resourceID.getPeriodOfAllocation()).thenReturn(periodOfAllocation);

        boolean result = testResource.isPeriodOverlapping(testTimePeriod);

        assertTrue(result);
    }

    @Test
    void isTimePeriodOverlap_TimePeriodOverlapsResourceTimePeriod_False() {
        LocalDate startDate = mock(LocalDate.class);
        LocalDate endDate = mock(LocalDate.class);
        TimePeriod testTimePeriod = mock(TimePeriod.class);

        when(testTimePeriod.getStartDate()).thenReturn(startDate);
        when(testTimePeriod.getEndDate()).thenReturn(endDate);
        when(periodOfAllocation.isOverlap(startDate, endDate)).thenReturn(false);
        when(resourceID.getPeriodOfAllocation()).thenReturn(periodOfAllocation);

        boolean result = testResource.isPeriodOverlapping(testTimePeriod);

        assertFalse(result);
    }


    @Test
    void sameAs_SameAttributes_True() {
        ResourceID anotherResourceID = mock(ResourceID.class);
        Resource secondTestResource = new Resource(anotherResourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour);


        boolean result = secondTestResource.sameAs(testResource);
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentAttributes_False() {
        ResourceID anotherResourceID = mock(ResourceID.class);
        TimePeriod anotherPeriodOfAllocation = mock(TimePeriod.class);
        ProjectRole anotherRoleInProject = mock(ProjectRole.class);
        PercentageOfAllocation anotherPercentageOfAllocation = mock(PercentageOfAllocation.class);
        CostPerHour anotherCostPerHour = mock(CostPerHour.class);

        Resource secondTestResource = new Resource(
                anotherResourceID,
                anotherRoleInProject,
                anotherPercentageOfAllocation,
                anotherCostPerHour);


        boolean result = secondTestResource.sameAs(testResource);
        assertFalse(result);
    }
    @Test
    void sameAs_NotAnInstanceOfResource_False() {
        String anotherResource = "this is not a resource";
        boolean result = testResource.sameAs(anotherResource);
        assertFalse(result);
    }
    @Test
    void testEquals_SameResource_Equals() {
        Resource anotherResource = testResource;

        assertEquals(testResource, anotherResource);
    }

    @Test
    void testEquals_DifferentResourceID_NotEquals() {
        ResourceID anotherResourceID = mock(ResourceID.class);

        Resource anotherTestResource = new Resource(
                anotherResourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour);

        boolean result = testResource.equals(anotherTestResource);
        assertFalse(result);
    }

    @Test
    void testEquals_SameResourceID_Equals() {
        Resource anotherTestResource = new Resource(
                resourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour);

        boolean result = testResource.equals(anotherTestResource);
        assertTrue(result);
    }

    @Test
    void testEquals_DifferentTypesOfObject_NotEquals() {
        String resourceString = "resource";
        assertNotEquals(testResource, resourceString);
    }

    @Test
    void testHashCode_SameResource_Equals() {
        Resource anotherTestResource = new Resource(
                resourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour);

        Integer expected = testResource.hashCode();
        Integer result = anotherTestResource.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void testHashCode_SameResource_NotEquals() {
        ResourceID anotherResourceID = mock(ResourceID.class);

        Resource anotherTestResource = new Resource(
                anotherResourceID,
                roleInProject,
                percentageOfAllocation,
                costPerHour);

        Integer expected = testResource.hashCode();
        Integer result = anotherTestResource.hashCode();

        assertNotEquals(expected, result);
    }
    @Test
    void testResourceVerificationOfDate_dateWithinAllocationPeriod_True() {

        when(resourceID.getPeriodOfAllocation()).thenReturn(periodOfAllocation);
        LocalDate dateWithinPeriod = LocalDate.of(2023, 1, 1);
        when(periodOfAllocation.contains(dateWithinPeriod)).thenReturn(true);
        assertTrue(testResource.resourceVerificationOfDate(dateWithinPeriod));
    }

    @Test
    void testResourceVerificationOfDate_dateWithinAllocationPeriod_False() {

        when(resourceID.getPeriodOfAllocation()).thenReturn(periodOfAllocation);
        LocalDate dateWithOutPeriod = LocalDate.of(2023, 1, 1);
        when(periodOfAllocation.contains(dateWithOutPeriod)).thenReturn(false);
        assertFalse(testResource.resourceVerificationOfDate(dateWithOutPeriod));
    }


    @Test
    void getCostPerHour() {
        CostPerHour result = testResource.getCostPerHour();
        assertEquals(costPerHour, result);

    }

    @Test
    void isPeriodOverlapping_True() {
        LocalDate startDate = LocalDate.of(2020,02,10);
        LocalDate endDate = LocalDate.of(2021, 02, 10);
        TimePeriod timePeriodOfNewResource = new TimePeriod(startDate, endDate);
        LocalDate secondStartDate = LocalDate.of(2020,02,10);
        LocalDate secondEendDate = LocalDate.of(2021, 02, 10);
        TimePeriod timePeriodOfResource = new TimePeriod(secondStartDate, secondEendDate);
        when(resourceID.getPeriodOfAllocation()).thenReturn(timePeriodOfResource);
        when(periodOfAllocation.isOverlap(startDate, endDate)).thenReturn(true);
        boolean result = testResource.isPeriodOverlapping(timePeriodOfNewResource);
        assertTrue(result);
    }

    @Test
    void isPeriodOverlapping_False() {
        LocalDate startDate = LocalDate.of(2020,02,10);
        LocalDate endDate = LocalDate.of(2021, 02, 10);
        TimePeriod timePeriodOfNewResource = new TimePeriod(startDate, endDate);
        LocalDate secondStartDate = LocalDate.of(2019,02,10);
        LocalDate secondEendDate = LocalDate.of(2020, 01, 10);
        TimePeriod timePeriodOfResource = new TimePeriod(secondStartDate, secondEendDate);
        when(resourceID.getPeriodOfAllocation()).thenReturn(timePeriodOfResource);
        when(periodOfAllocation.isOverlap(startDate, endDate)).thenReturn(false);
        boolean result = testResource.isPeriodOverlapping(timePeriodOfNewResource);
        assertFalse(result);
    }

    @Test
    void isAllocated_true() {
        when(resourceID.getPeriodOfAllocation()).thenReturn(periodOfAllocation);
        when(periodOfAllocation.contains(any())).thenReturn(true);
        boolean result = testResource.isAllocated(LocalDate.now());
        assertTrue(result);
    }
    @Test
    void isAllocated_false() {
        when(resourceID.getPeriodOfAllocation()).thenReturn(periodOfAllocation);
        when(periodOfAllocation.contains(LocalDate.now())).thenReturn(false);
        boolean result = testResource.isAllocated(LocalDate.now());
        assertFalse(result);
    }
}