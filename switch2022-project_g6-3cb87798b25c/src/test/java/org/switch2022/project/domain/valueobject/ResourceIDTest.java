package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ResourceIDTest {

    @Test
    void ResourceID_EmailNull_Exception() {
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ResourceID(null, code, period));
        assertEquals("Resource Email cannot be null", exception.getMessage());
    }


    @Test
    void ResourceID_ProjectCodeNull_Exception() {
        Email email = mock(Email.class);
        TimePeriod period = mock(TimePeriod.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ResourceID(email, null, period));
        assertEquals("Project code cannot be null", exception.getMessage());
    }

    @Test
    void ResourceID_TimePeriodNull_Exception() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ResourceID(email, code, null));
        assertEquals("Period of allocation cannot be null", exception.getMessage());
    }

    @Test
    void getResourceEmail() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID resourceID = new ResourceID(email,code,period);
        Email result = resourceID.getResourceEmail();

        assertEquals(email,result);
    }

    @Test
    void getProjectCodeValue() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID resourceID = new ResourceID(email,code,period);
        ProjectCode result = resourceID.getProjectCode();

        assertEquals(code,result);
    }

    @Test
    void getPeriodOfAllocation() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID resourceID = new ResourceID(email,code,period);
        TimePeriod result = resourceID.getPeriodOfAllocation();

        assertEquals(period,result);
    }

    @Test
    void hasEmail() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID resourceID = new ResourceID(email,code,period);
        boolean result = resourceID.hasEmail(email);

        assertTrue(result);
    }

    @Test
    void equals_sameObject_Equal() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID id = new ResourceID(email,code,period);
        assertEquals(id,id);
    }

    @Test
    void equals_differentObjectsButSameContent_Equal() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);
        ResourceID id = new ResourceID(email,code,period);
        ResourceID sameCost = new ResourceID(email,code,period);
        assertEquals(id,sameCost);
    }

    @Test
    void equals_differentObjectSameClass_NotEqual() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);
        Email anotherEmail = mock(Email.class);

        ResourceID id = new ResourceID(email,code,period);
        ResourceID differentCost = new ResourceID(anotherEmail,code,period);
        assertNotEquals(id,differentCost);
    }

    @Test
    void equals_differentObjectDifferentClass_NotEqual() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);
        ResourceID id = new ResourceID(email,code,period);
        String differentObject = "id";

        assertNotEquals(id,differentObject);
    }

    @Test
    void equals_compareWithNull_NotEqual() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID id = new ResourceID(email,code,period);
        ResourceID nullObject = null;
        assertNotEquals(id,nullObject);
    }

    @Test
    void hashCode_hashCodeCreation_Equals() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);

        ResourceID id = new ResourceID(email,code,period);
        int result = id.hashCode();
        ResourceID sameCost = new ResourceID(email,code,period);
        int secondResult = sameCost.hashCode();
        assertEquals(result, secondResult);
    }

    @Test
    void hashCode_hashCodeCreation_NotEquals() {
        Email email = mock(Email.class);
        ProjectCode code = mock(ProjectCode.class);
        TimePeriod period = mock(TimePeriod.class);
        Email anotherEmail = mock(Email.class);

        ResourceID id = new ResourceID(email,code,period);
        int result =id.hashCode();
        ResourceID differentCost = new ResourceID(anotherEmail,code,period);
        int secondResult = differentCost.hashCode();
        assertNotEquals(result, secondResult);
    }

    @Test
    void testToString() {
        Email email = new Email("ana@ana.com");
        ProjectCode code = new ProjectCode("P1");
        LocalDate startDate = LocalDate.of(2020,02,02);
        LocalDate endDate = LocalDate.of(2021,01,01);
        TimePeriod period = new TimePeriod(startDate,endDate);
        ResourceID resourceID = new ResourceID(email, code, period);
        String result = resourceID.toString();
        String expected = "ResourceID{resourceEmail=ana@ana.com" +
                ", projectCode=P1" +
                ", periodOfAllocation=2020-02-02 - 2021-01-01" +
                '}';
        assertEquals(expected,result);
    }
}