package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountStatusTest {

    private static AccountStatus active;
    private static AccountStatus inactive;

    @BeforeAll
    public static void init(){
       active = new AccountStatus(true);
       inactive = new AccountStatus(false);
    }
    @Test
    void equals_sameObject_Equals() {
        AccountStatus active = new AccountStatus(true);
        assertEquals(active, active);
    }

    @Test
    void equals_differentObjectsDifferentContent_NotEquals() {
        assertNotEquals(active,inactive);
    }


    @Test
    void equals_differentObjectsButSameContent_Equals() {
        AccountStatus inactiveTwo = new AccountStatus(false);
        assertEquals(inactive,inactiveTwo);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        String differentObject = "P001";
        assertNotEquals(active,differentObject);
    }

    @Test
    void testHashCode_Equal() {
        AccountStatus activeTwo = new AccountStatus(true);
        int expected = active.hashCode();
        int result = activeTwo.hashCode();
        assertEquals(expected,result);
    }
    @Test
    void testHashCode_NotEqual() {
        AccountStatus inactiveTwo = new AccountStatus(false);
        int expected = active.hashCode();
        int result = inactiveTwo.hashCode();
        assertNotEquals(expected,result);
    }
    @Test
    void testGetBooleanValue_ValueIsFalse_False() {
        AccountStatus inactiveStatus = new AccountStatus(false);
        boolean result = inactiveStatus.isStatusActive();
        assertFalse(result);
    }
    @Test
    void testGetBooleanValue_ValueIsFalse_True() {
        AccountStatus inactiveStatus = new AccountStatus(true);
        boolean result = inactiveStatus.isStatusActive();
        assertTrue(result);
    }

    @Test
    void testEquals_compareWithNull_False() {
        AccountStatus inactiveTwo = null;
        assertNotEquals(inactive,inactiveTwo);
    }
}