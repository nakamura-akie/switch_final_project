package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStoryIDTest {

    private ProjectCode projectCode;
    private UserStoryCode userStoryCode;
    private UserStoryID userStoryID;

    private ProjectCode projectCodeTwo;
    private UserStoryCode userStoryCodeTwo;

    @BeforeEach
    void init(){
        projectCode = new ProjectCode("P001");
        userStoryCode = new UserStoryCode("US001");
        userStoryID = new UserStoryID(projectCode,userStoryCode);
        projectCodeTwo = new ProjectCode("P002");
        userStoryCodeTwo = new UserStoryCode("US002");
    }

    @Test
    void constructor_NullProjectCode_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new UserStoryID(null, userStoryCode));
        assertEquals("Invalid Project Code.", exception.getMessage());
    }

    @Test
    void constructor_NullUserStoryCode_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new UserStoryID(projectCode, null));
        assertEquals("The inserted User Story code is invalid", exception.getMessage());
    }

    @Test
    void equals_sameObject_True() {
        assertEquals(userStoryID, userStoryID);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        UserStoryID userStoryIDTwo = new UserStoryID(projectCode,userStoryCode);
        assertEquals(userStoryID,userStoryIDTwo);
    }

    @Test
    void equals_differentProjectCode_False() {
        UserStoryID userStoryIDTwo = new UserStoryID(projectCodeTwo,userStoryCode);
        assertNotEquals(userStoryID,userStoryIDTwo);
    }
    @Test
    void equals_differentSprintCode_False() {
        UserStoryID userStoryIDTwo = new UserStoryID(projectCode,userStoryCodeTwo);
        assertNotEquals(userStoryID,userStoryIDTwo);
    }
    @Test
    void equals_differentSprintCodeAndDifferentProjectCode_False() {
        UserStoryID userStoryIDTwo = new UserStoryID(projectCodeTwo,userStoryCodeTwo);
        assertNotEquals(userStoryID,userStoryIDTwo);    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        String differentObject = "P001";
        assertNotEquals(userStoryID,differentObject);
    }

    @Test
    void equals_compareWithNull_False() {
        UserStoryID userStoryIDTwo = null;
        assertNotEquals(userStoryID,userStoryIDTwo);
    }

    @Test
    void testHashCode_True() {
        UserStoryID userStoryIDTwo = new UserStoryID(projectCode,userStoryCode);
        int expected = userStoryIDTwo.hashCode();
        int result = userStoryID.hashCode();
        assertEquals(expected,result);
    }
    @Test
    void testHashCode_False() {
        UserStoryID userStoryIDTwo = new UserStoryID(projectCodeTwo,userStoryCodeTwo);
        int expected = userStoryIDTwo.hashCode();
        int result = userStoryID.hashCode();
        assertNotEquals(expected,result);
    }
}