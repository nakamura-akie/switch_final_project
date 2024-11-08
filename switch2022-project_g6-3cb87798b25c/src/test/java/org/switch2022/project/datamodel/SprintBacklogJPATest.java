package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SprintBacklogJPATest {

    private static SprintIdJPA sprintIdJPA;
    private static String userStoryCodeList;
    private static SprintBacklogJPA sprintBacklogJPA;

    @BeforeAll
    static void init() {
        sprintIdJPA = new SprintIdJPA("P1", "S1");
        userStoryCodeList = "this is a list";
        sprintBacklogJPA = new SprintBacklogJPA(sprintIdJPA, userStoryCodeList);
    }

    @AfterAll
    static void tearDown() {
        sprintIdJPA = null;
        userStoryCodeList = null;
        sprintBacklogJPA = null;
    }

    @Test
    void getSprintIdJPA_ReturnsSpringIdJPA() {

        SprintIdJPA result = sprintBacklogJPA.getSprintIdJPA();

        assertEquals(sprintIdJPA, result);
    }

    @Test
    void getUserStoryCodeList_ReturnsUserStoryCodeList() {

        String result = sprintBacklogJPA.getUserStoryCodeList();

        assertEquals(userStoryCodeList, result);
    }

    @Test
    void equals_SameObject_True() {
        SprintBacklogJPA anotherSprintBacklogJPA = sprintBacklogJPA;

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertTrue(result);
    }

    @Test
    void equals_EqualObject_True() {
        SprintBacklogJPA anotherSprintBacklogJPA = new SprintBacklogJPA(sprintIdJPA, userStoryCodeList);

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertTrue(result);
    }

    @Test
    void equals_DifferentObject_False() {
        SprintIdJPA anotherIdJPA = new SprintIdJPA("P2", "S2");
        String anotherList = "this is another list";

        SprintBacklogJPA anotherSprintBacklogJPA = new SprintBacklogJPA(anotherIdJPA, anotherList);

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjectWithDifferentProjectCode_False() {
        SprintIdJPA anotherIdJPA = new SprintIdJPA("P2", "S1");

        SprintBacklogJPA anotherSprintBacklogJPA = new SprintBacklogJPA(anotherIdJPA, userStoryCodeList);

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjectWithDifferentSprintCode_False() {
        SprintIdJPA anotherIdJPA = new SprintIdJPA("P1", "S2");

        SprintBacklogJPA anotherSprintBacklogJPA = new SprintBacklogJPA(anotherIdJPA, userStoryCodeList);

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjectWithDifferentUserStoryCodeList_False() {
        String anotherList = "this is another list";

        SprintBacklogJPA anotherSprintBacklogJPA = new SprintBacklogJPA(sprintIdJPA, anotherList);

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentType_False() {
        String anotherSprintBacklogJPA = "not a sprint backlog";

        boolean result = sprintBacklogJPA.equals(anotherSprintBacklogJPA);

        assertFalse(result);
    }

    @Test
    void equals_NullObject_False() {

        boolean result = sprintBacklogJPA.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        SprintBacklogJPA anotherSprintBacklogJPA = sprintBacklogJPA;

        int firstHash = sprintBacklogJPA.hashCode();
        int secondHash = anotherSprintBacklogJPA.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        SprintIdJPA sprintIdJPA = new SprintIdJPA("P3", "S6");
        String anotherUserStoryList = "a list to test";
        SprintBacklogJPA anotherSprintBacklogJPA = new SprintBacklogJPA(sprintIdJPA, anotherUserStoryList);

        int firstHash = sprintBacklogJPA.hashCode();
        int secondHash = anotherSprintBacklogJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}