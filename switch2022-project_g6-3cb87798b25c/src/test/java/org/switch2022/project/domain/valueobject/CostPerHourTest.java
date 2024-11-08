package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class CostPerHourTest {


    @Test
    void CostPerHour() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new CostPerHour(-1));
        assertEquals("Invalid cost per hour.", exception.getMessage());
    }

    @Test
    void Constructor_CostPerHourZero() {
        assertNotNull(new CostPerHour(0));
    }

    @Test
    void Constructor_ParseStringToDouble() {
        String hourlyCostString = "2.5";
        Double hourlyCostDouble = 2.5;

        CostPerHour result = new CostPerHour(hourlyCostString);
        assertNotNull(result);

        Double costPerHourValue = result.getCostPerHourValue();
        assertEquals(hourlyCostDouble,costPerHourValue);
    }

    @Test
    void Constructor_ParseIntegerToDouble() {
        Integer hourlyCostString = 2;
        Double hourlyCostDouble = 2.0;

        CostPerHour result = new CostPerHour(hourlyCostString);
        assertNotNull(result);

        Double costPerHourValue = result.getCostPerHourValue();
        assertEquals(hourlyCostDouble,costPerHourValue);
    }



    @Test
    void equals_sameObject_Equal() {
        CostPerHour cost = new CostPerHour(2.5);
        assertEquals(cost, cost);
    }

    @Test
    void equals_differentObjectsButSameContent_Equal() {
        CostPerHour cost = new CostPerHour(2.5);
        CostPerHour sameCost = new CostPerHour(2.5);
        assertEquals(cost,sameCost);
    }

    @Test
    void equals_differentObjectSameClass_NotEqual() {
        CostPerHour cost = new CostPerHour(2.5);
        CostPerHour differentCost = new CostPerHour(3);
        assertNotEquals(cost,differentCost);
    }

    @Test
    void equals_differentObjectDifferentClass_NotEqual() {
        CostPerHour cost = new CostPerHour(2.5);
        String differentObject = "2.5";

        assertNotEquals(cost,differentObject);
    }

    @Test
    void equals_compareWithNull_NotEqual() {
        CostPerHour cost = new CostPerHour(2.5);
        CostPerHour nullObject = null;
        assertNotEquals(cost,nullObject);
    }

    @Test
    void hashCode_hashCodeCreation_Equals() {
        CostPerHour cost = new CostPerHour(2.5);
        int result = cost.hashCode();
        CostPerHour sameCost = new CostPerHour(2.5);
        int secondResult = sameCost.hashCode();
        assertEquals(result, secondResult);
    }

    @Test
    void hashCode_hashCodeCreation_NotEquals() {
        CostPerHour cost = new CostPerHour(2.5);
        int result = cost.hashCode();
        CostPerHour differentCost = new CostPerHour(3);
        int secondResult = differentCost.hashCode();
        assertNotEquals(result, secondResult);
    }

    @Test
    void getCostPerHourValue() {
        CostPerHour cost = new CostPerHour(2.5);
        double expected = 2.5;
        double result = cost.getCostPerHourValue();
        assertEquals(expected, result);
    }
}