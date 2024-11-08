package org.switch2022.project.domain.sprint;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SprintBacklogTest {

    private SprintBacklog sprintBacklog;
    private SprintID sprintID;
    private List<UserStoryID> userStoryIDList;
    private List<UserStoryID> emptyList;
    private UserStoryID userStoryID;

    @BeforeEach
    void init() {
        sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S1"));
        sprintBacklog = new SprintBacklog(sprintID);
        userStoryIDList = new LinkedList<>();
        emptyList = new LinkedList<>();

        userStoryID = new UserStoryID(new ProjectCode("P1"), new UserStoryCode("US001"));
        userStoryIDList.add(userStoryID);
    }

    @AfterEach
    void tearDown() {
        sprintBacklog = null;
        sprintID = null;
        userStoryIDList.clear();
        userStoryIDList = null;
        emptyList = null;
        userStoryID = null;
    }

    @Test
    void constructor_NullProjectCode_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintBacklog(null));
        assertEquals("Sprint ID cannot be null.", exception.getMessage());
    }

    @Test
    void constructorWithList_NullProjectCode_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintBacklog(null, userStoryIDList));
        assertEquals("Sprint ID cannot be null.", exception.getMessage());
    }

    @Test
    void addUserStory_NonExistent_True() {
        UserStoryID anotherUserStoryID = new UserStoryID(new ProjectCode("P1"),
                new UserStoryCode("US002"));

        boolean result = sprintBacklog.addUserStory(anotherUserStoryID);

        assertTrue(result);
    }

    @Test
    void addUserStory_AlreadyExistent_False() {
        sprintBacklog.addUserStory(userStoryID);

        Exception e = assertThrows(IllegalArgumentException.class, ()
                -> sprintBacklog.addUserStory(userStoryID)
        );

        assertEquals("The selected User Story already exists in the Sprint Backlog", e.getMessage());
    }

    @Test
    void getUserStoryCodeList_ReturnsListWithCodes() {
        sprintBacklog.addUserStory(userStoryID);

        List<UserStoryID> result = sprintBacklog.getUserStoryCodeList();

        assertEquals(userStoryIDList, result);
    }

    @Test
    void getUserStoryCodeList_ReturnsEmptyList() {

        List<UserStoryID> result = sprintBacklog.getUserStoryCodeList();

        assertEquals(emptyList, result);
    }

    @Test
    void identity_HasSameIdentity_ReturnEquals() {

        SprintID result = sprintBacklog.identity();

        assertEquals(sprintID, result);
    }

    @Test
    void identity_DoesntHaveSameIdentity_ReturnNotEquals() {
        SprintID anotherSprintID = new SprintID(new ProjectCode("P2"),
                new SprintCode("S1"));

        SprintID result = sprintBacklog.identity();

        assertNotEquals(anotherSprintID, result);
    }

    @Test
    void sameAs_SameObject() {
        SprintBacklog anotherSprintBacklog = new SprintBacklog(
                sprintID,
                emptyList
        );

        boolean test = sprintBacklog.sameAs(anotherSprintBacklog);

        assertTrue(test);
    }

    @Test
    void sameAs_DifferentObject() {
        List<UserStoryID> newList = new LinkedList<>();
        newList.add(
                new UserStoryID(
                        new ProjectCode("P9"),
                        new UserStoryCode("US09")
                )
        );

        SprintBacklog anotherSprintBacklog = new SprintBacklog(
                sprintID,
                newList
        );

        boolean test = sprintBacklog.sameAs(anotherSprintBacklog);

        assertFalse(test);
    }

    @Test
    void sameAs_ObjectsOfDifferentClasses_ReturnFalse() {
        String test = "yes";
        boolean result = sprintBacklog.sameAs(test);

        assertFalse(result);
    }

    @Test
    void equals_SameObject_True() {
        SprintBacklog anotherSprintBacklog = sprintBacklog;

        boolean result = sprintBacklog.equals(anotherSprintBacklog);

        assertTrue(result);
    }

    @Test
    void equals_EqualObject_True() {
        SprintBacklog anotherSprintBacklog = new SprintBacklog(
                sprintID, userStoryIDList);

        boolean result = sprintBacklog.equals(anotherSprintBacklog);

        assertTrue(result);
    }
    @Test
    void equals_ObjectOfDifferentType_False() {
        String notASprintBacklog = "not a sprint backlog";

        boolean result = sprintBacklog.equals(notASprintBacklog);

        assertFalse(result);

    }

    @Test
    void equals_NullObject_False() {

        boolean result = sprintBacklog.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        SprintBacklog anotherSprintBacklog = sprintBacklog;

        int firstHash = sprintBacklog.hashCode();
        int secondHash = anotherSprintBacklog.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        SprintID anotherSprintID = new SprintID(new ProjectCode("P2"),
                new SprintCode("S1"));

        SprintBacklog anotherSprintBacklog = new SprintBacklog(
                anotherSprintID, emptyList);

        int firstHash = sprintBacklog.hashCode();
        int secondHash = anotherSprintBacklog.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}