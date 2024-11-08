package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;

import static org.junit.jupiter.api.Assertions.*;

class UserStoryIdJPATest {
    private static UserStoryIdJPA userStoryIdJPA;

    @BeforeAll
    static void setup() {
        userStoryIdJPA = new UserStoryIdJPA("P1", "US001");
    }

    @AfterAll
    static void tearDown() {
        userStoryIdJPA = null;
    }

    @Test
    void getProjectCode_ReturnsCorrectProjectCode_True() {
        String result = userStoryIdJPA.getProjectCodeValue();

        assertEquals("P1", result);
    }

    @Test
    void getUserStoryCode_ReturnsCorrectUserStoryCode_True() {
        String result = userStoryIdJPA.getUserStoryCode();

        assertEquals("US001", result);
    }

    @Test
    void setProjectCode_SetsProjectCode_True() {
        UserStoryIdJPA newUserStoryIdJPA = new UserStoryIdJPA();
        newUserStoryIdJPA.setProjectCode("P2");

        String result = newUserStoryIdJPA.getProjectCodeValue();
        assertEquals("P2", result);
    }

    @Test
    void setUserStoryCode_SetsUserStoryCode_True() {
        UserStoryIdJPA newUserStoryIdJPA = new UserStoryIdJPA();
        newUserStoryIdJPA.setUserStoryCode("US002");

        String result = newUserStoryIdJPA.getUserStoryCode();
        assertEquals("US002", result);
    }

    @Test
    void equals_SameObject_True() {
        UserStoryIdJPA anotherUserStoryIdJPA = userStoryIdJPA;

        boolean result = userStoryIdJPA.equals(anotherUserStoryIdJPA);

        assertTrue(result);
    }

    @Test
    void equals_EqualObject_True() {
        UserStoryIdJPA anotherUserStoryIdJPA = new UserStoryIdJPA("P1", "US001");

        boolean result = userStoryIdJPA.equals(anotherUserStoryIdJPA);

        assertTrue(result);
    }

    @Test
    void equals_DifferentProjectCodeObject_False() {
        UserStoryIdJPA anotherUserStoryIdJPA = new UserStoryIdJPA("P2", "US001");

        boolean result = userStoryIdJPA.equals(anotherUserStoryIdJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentUserStoryCodeObject_False() {
        UserStoryIdJPA anotherUserStoryIdJPA = new UserStoryIdJPA("P1", "US002");

        boolean result = userStoryIdJPA.equals(anotherUserStoryIdJPA);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentType_False() {
        String anotherUserStoryIdJPA = "not an id";

        boolean result = userStoryIdJPA.equals(anotherUserStoryIdJPA);

        assertFalse(result);

    }

    @Test
    void equals_NullObject_False() {

        boolean result = userStoryIdJPA.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        UserStoryIdJPA anotherUserStoryIdJPA = userStoryIdJPA;

        int firstHash = userStoryIdJPA.hashCode();
        int secondHash = anotherUserStoryIdJPA.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        UserStoryIdJPA anotherUserStoryIdJPA = new UserStoryIdJPA("P2", "US002");

        int firstHash = userStoryIdJPA.hashCode();
        int secondHash = anotherUserStoryIdJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}