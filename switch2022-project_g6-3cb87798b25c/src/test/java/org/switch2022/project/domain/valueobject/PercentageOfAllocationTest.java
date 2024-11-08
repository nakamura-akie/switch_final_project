package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class PercentageOfAllocationTest {

    @Test
    void PercentageOfAllocation_PercentageOfAllocationUnderZero() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new PercentageOfAllocation(-1));
        assertEquals("Invalid percentage of allocation.", exception.getMessage());
    }


    @Test
    void PercentageOfAllocation_StringPercentageOfAllocationInvalid() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new PercentageOfAllocation("a"));
        assertEquals("Invalid percentage of allocation.", exception.getMessage());
    }

    @Test
    void PercentageOfAllocation_PercentageOfAllocationAboveOneHundred() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new PercentageOfAllocation(104));
        assertEquals("Invalid percentage of allocation.", exception.getMessage());
    }

    @Test
    void PercentageOfAllocation_PercentageOfAllocationEqualToZeroPercent() {
        assertNotNull(new PercentageOfAllocation(0));
    }

    @Test
    void PercentageOfAllocation_PercentageOfAllocationStringFormat() {
        assertNotNull(new PercentageOfAllocation("50"));
    }

    @Test
    void PercentageOfAllocation_PercentageOfAllocationEqualToOneHundredPercent() {
        assertNotNull(new PercentageOfAllocation(100));
    }

    @Test
    void getPercentageValue_PercentageValueReturn_Integer() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);

        Integer expected = 50;
        Integer result = allocation.getPercentageValue();
        assertEquals(expected, result);
    }


    @Test
    void equals_sameObject_Equal() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        assertEquals(allocation, allocation);
    }

    @Test
    void equals_differentObjectsButSameContent_Equal() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        PercentageOfAllocation sameAllocation = new PercentageOfAllocation(50);
        assertEquals(allocation, sameAllocation);
    }

    @Test
    void equals_differentObjectSameClass_NotEqual() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        PercentageOfAllocation differentAllocation = new PercentageOfAllocation(3);
        assertNotEquals(allocation, differentAllocation);
    }

    @Test
    void equals_differentObjectDifferentClass_NotEqual() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        String differentObject = "50";

        assertNotEquals(allocation, differentObject);
    }

    @Test
    void equals_compareWithNull_NotEqual() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        PercentageOfAllocation nullObject = null;
        assertNotEquals(allocation, nullObject);
    }

    @Test
    void hashCode_hashCodeCreation_Equals() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        int result = allocation.hashCode();
        PercentageOfAllocation sameAllocation = new PercentageOfAllocation(50);
        int secondResult = sameAllocation.hashCode();
        assertEquals(result, secondResult);
    }

    @Test
    void hashCode_hashCodeCreation_NotEquals() {
        PercentageOfAllocation allocation = new PercentageOfAllocation(50);
        int result = allocation.hashCode();
        PercentageOfAllocation differentAllocation = new PercentageOfAllocation(3);
        int secondResult = differentAllocation.hashCode();
        assertNotEquals(result, secondResult);
    }

}