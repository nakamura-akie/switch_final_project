package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryJPA;

import static org.junit.jupiter.api.Assertions.*;
class UserStoryJPATest {

    private static UserStoryIdJPA userStoryIdJPA;
    private static UserStoryJPA userStoryJPA;

    @BeforeAll
    static void init() {
        String projectCode = "P1";
        String userStoryCode = "US001";
        String description = "Some description";
        String status = "PLANNED";

        userStoryIdJPA = new UserStoryIdJPA(projectCode, userStoryCode);
        userStoryJPA = new UserStoryJPA(userStoryIdJPA, description, status);
    }

    @AfterAll
    static void tearDown() {
        userStoryIdJPA = null;
        userStoryJPA = null;
    }

    @Test
    void getUserStoryIdJpa_UserStoryIdJPA() {

        UserStoryIdJPA result = userStoryJPA.getuserStoryIdJpa();

        assertEquals(userStoryIdJPA, result);
    }

    @Test
    void getDescription_Description() {
        String expected = "Some description";

        String result = userStoryJPA.getDescription();

        assertEquals(expected, result);
    }

    @Test
    void getStatus_Status() {
        String expected = "PLANNED";

        String result = userStoryJPA.getUserStoryStatus();

        assertEquals(expected, result);
    }

    @Test
    void equals_SameObject_True() {
        UserStoryJPA anotherUserStoryJPA = userStoryJPA;

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertTrue(result);
    }

    @Test
    void equals_EqualObject_True() {
        String description = "Some description";
        String status = "PLANNED";

        UserStoryJPA anotherUserStoryJPA = new UserStoryJPA(userStoryIdJPA, description, status);

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertTrue(result);
    }

    @Test
    void equals_DifferentProjectCodeObject_False() {
        String projectCode = "P2";
        String userStoryCode = "US001";

        UserStoryIdJPA userStoryID = new UserStoryIdJPA(projectCode, userStoryCode);
        String description = "Some description";
        String status = "PLANNED";

        UserStoryJPA anotherUserStoryJPA = new UserStoryJPA(userStoryID, description, status);

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentUserStoryCodeObject_False() {
        String projectCode = "P1";
        String userStoryCode = "US002";

        UserStoryIdJPA userStoryID = new UserStoryIdJPA(projectCode, userStoryCode);
        String description = "Some description";
        String status = "PLANNED";

        UserStoryJPA anotherUserStoryJPA = new UserStoryJPA(userStoryID, description, status);

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentDescriptionObject_True() {
        String description = "Another description";
        String status = "PLANNED";

        UserStoryJPA anotherUserStoryJPA = new UserStoryJPA(userStoryIdJPA, description, status);

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertTrue(result);
    }

    @Test
    void equals_DifferentStatusObject_True() {
        String description = "Some description";
        String status = "FINISHED";

        UserStoryJPA anotherUserStoryJPA = new UserStoryJPA(userStoryIdJPA, description, status);

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertTrue(result);
    }

    @Test
    void equals_ObjectOfDifferentType_False() {
        String anotherUserStoryJPA = "not a user story";

        boolean result = userStoryJPA.equals(anotherUserStoryJPA);

        assertFalse(result);

    }

    @Test
    void equals_NullObject_False() {

        boolean result = userStoryJPA.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        UserStoryJPA anotherUserStoryJPA = userStoryJPA;

        int firstHash = userStoryJPA.hashCode();
        int secondHash = anotherUserStoryJPA.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        String projectCode = "P1";
        String userStoryCode = "US002";

        UserStoryIdJPA userStoryID = new UserStoryIdJPA(projectCode, userStoryCode);
        String description = "Some description";
        String status = "FINISHED";

        UserStoryJPA anotherUserStoryJPA = new UserStoryJPA(userStoryID, description, status);

        int firstHash = userStoryJPA.hashCode();
        int secondHash = anotherUserStoryJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}