package org.switch2022.project.domain.sprint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SprintTest {

    private ProjectCode projectCodeOne;
    private ProjectCode projectCodeTwo;
    private SprintCode sprintCodeOne;
    private SprintCode sprintCodeTwo;
    private SprintID sprintID;
    private Sprint sprintOne;
    private SprintBacklog sprintBacklog;
    private SprintDuration sprintDuration;

    private LocalDate startDate;
    private LocalDate endDate;
    private SprintStatus sprintStatus = SprintStatus.PLANNED;

    @BeforeEach
    void init() {
        startDate = LocalDate.of(2024, 5, 1);
        endDate = startDate.plusDays(21);

        projectCodeOne = new ProjectCode("P001");
        projectCodeTwo = new ProjectCode("P002");
        sprintCodeOne = new SprintCode("SPT001");
        sprintCodeTwo = new SprintCode("SPT002");
        sprintID = new SprintID(projectCodeOne, sprintCodeOne);
        sprintDuration = new SprintDuration(3);
        sprintBacklog = mock(SprintBacklog.class);
        sprintOne = new Sprint(sprintID, startDate, sprintDuration, endDate, sprintBacklog);
    }

    @Test
    void constructorWithSprintStatus_createValidSprint_True() {
        Sprint expected = sprintOne;
        Sprint result = new Sprint(sprintID, startDate, sprintDuration, endDate, sprintBacklog, sprintStatus);

        assertEquals(expected, result);
    }

    @Test
    void getStartDate_returnsStartDate_True() {
        LocalDate actualStartDate = sprintOne.getStartDate();

        assertEquals(startDate, actualStartDate);
    }

    @Test
    void getEndDate_returnsEndDate_True() {
        LocalDate actualEndDate = sprintOne.getEndDate();

        assertEquals(endDate, actualEndDate);
    }

    @Test
    void getSprintDuration_returnsSprintDuration_True() {
        SprintDuration actualSprintDuration = sprintOne.getSprintDuration();

        assertEquals(sprintDuration, actualSprintDuration);
    }

    @Test
    void getProjectCode_returnsProjectCode_True() {
        ProjectCode actualProjectCode = sprintID.getProjectCode();

        assertEquals(sprintID.getProjectCode(), actualProjectCode);
    }

    @Test
    void getSprintCode_returnsSprintCode_True() {
        SprintCode actualSprintCode = sprintID.getSprintCode();

        assertEquals(sprintID.getSprintCode(), actualSprintCode);
    }

    @Test
    void getSprintBacklog_returnsUserStoryIdList_True() {
        List<UserStoryID> expected = sprintBacklog.getUserStoryCodeList();
        List<UserStoryID> result = sprintOne.getIDsOfUserStoriesInSprintBacklog();

        assertEquals(expected, result);
    }

    @Test
    void constructorWithSprintStatus_createSprintWithWithNullStartDate_ThrowsException() {
        String expected = "Invalid start date";
        String result = assertThrows(IllegalArgumentException.class, () ->
                new Sprint(sprintID, null, sprintDuration, endDate, sprintBacklog, sprintStatus)).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void identity_equalProjectAndSprintCodes_true() {
        SprintID expected = new SprintID(projectCodeOne, sprintCodeOne);
        SprintID result = sprintOne.identity();
        assertEquals(expected, result);
    }

    @Test
    void identity_differentProjectCode_false() {
        SprintID expected = new SprintID(projectCodeTwo, sprintCodeOne);
        SprintID result = sprintOne.identity();
        assertNotEquals(expected, result);
    }

    @Test
    void identity_differentSprintCode_false() {
        SprintID expected = new SprintID(projectCodeOne, sprintCodeTwo);
        SprintID result = sprintOne.identity();
        assertNotEquals(expected, result);
    }

    @Test
    void sameAS_sameSprintBacklog_True() {
        Sprint newSprint = new Sprint(sprintID, LocalDate.of(2024, 5, 1), sprintDuration,
                LocalDate.now().plusDays(21), sprintBacklog);
        boolean result = sprintOne.sameAs(newSprint);
        assertTrue(result);
    }

    @Test
    void sameAS_differentSprintBacklog_False() {
        SprintID newSprintID = new SprintID(projectCodeOne, sprintCodeTwo);
        Sprint newSprint = new Sprint(newSprintID, LocalDate.now(), sprintDuration, LocalDate.now().plusDays(21),
                sprintBacklog);
        UserStoryID userStoryID = mock(UserStoryID.class);
        newSprint.addToSprintBacklog(userStoryID);
        boolean result = sprintOne.sameAs(newSprint);
        assertFalse(result);
    }

    @Test
    void sameAS_instanceOfDifferentClass_False() {
        Object object = new Object();
        boolean result = sprintOne.sameAs(object);
        assertFalse(result);
    }

    @Test
    void testEquals_sameObject_true() {
        assertEquals(sprintOne, sprintOne);
    }

    @Test
    void testEquals_equalObject_equals() {
        Sprint newSprint = new Sprint(sprintID, LocalDate.of(2024, 05, 04), sprintDuration,
                LocalDate.now().plusDays(21), sprintBacklog);
        assertEquals(sprintOne, newSprint);
    }

    @Test
    void testEquals_differentInstancesWitDifferentProjectCode_false() {
        SprintID newSprintID = new SprintID(projectCodeTwo, sprintCodeOne);
        Sprint newSprint = new Sprint(newSprintID, LocalDate.of(2024, 05, 04),
                sprintDuration, LocalDate.now().plusDays(21), sprintBacklog);
        assertNotEquals(sprintOne, newSprint);
    }

    @Test
    void testEquals_differentInstancesWitDifferentSprintCodes_false() {
        SprintID newSprintID = new SprintID(projectCodeOne, sprintCodeTwo);
        Sprint newSprint = new Sprint(newSprintID, LocalDate.now(), sprintDuration,
                LocalDate.now().plusWeeks(3), sprintBacklog);
        assertNotEquals(sprintOne, newSprint);
    }

    @Test
    void testEquals_objectsOfDifferentType_false() {
        String newObject = "A string";
        assertFalse(sprintOne.equals(newObject));
    }

    @Test
    void testEquals_CompareWithNullObject_false() {
        Sprint nullObject = null;

        boolean result = sprintOne.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void testHashCode_sameHashCodes() {
        Sprint newSprint = new Sprint(sprintID, LocalDate.of(2024, 05, 04), sprintDuration,
                LocalDate.now().plusWeeks(3), sprintBacklog);
        int hashOne = sprintOne.hashCode();
        int hashTwo = newSprint.hashCode();
        assertEquals(hashOne, hashTwo);
    }

    @Test
    void testHashCode_differentHashCodes() {
        SprintID newSprintID = new SprintID(projectCodeTwo, sprintCodeOne);
        Sprint newSprint = new Sprint(newSprintID, startDate, sprintDuration,
                LocalDate.now().plusDays(21), sprintBacklog);
        assertNotEquals(sprintOne.hashCode(), newSprint.hashCode());
    }

    @Test
    void addToSprintBacklog() {
        UserStoryID userStoryID = new UserStoryID(projectCodeOne, new UserStoryCode("US001"));
        when(sprintBacklog.addUserStory(any())).thenReturn(true);
        boolean result = sprintOne.addToSprintBacklog(userStoryID);
        assertTrue(result);

    }

    @Test
    void addToSprintBacklog_False() {
        UserStoryID userStoryID = new UserStoryID(projectCodeOne, new UserStoryCode("US001"));
        sprintOne.addToSprintBacklog(userStoryID);
        boolean result = sprintOne.addToSprintBacklog(userStoryID);
        assertFalse(result);
    }

    @Test
    void getIDsOfUserStoriesInSprintBacklog_getsListOfIDs_Equal() {
        UserStoryID userStoryID = new UserStoryID(projectCodeOne, new UserStoryCode("US001"));
        sprintOne.addToSprintBacklog(userStoryID);
        List<UserStoryID> expected = List.of(userStoryID);
        when(sprintBacklog.getUserStoryCodeList()).thenReturn(List.of(userStoryID));
        List<UserStoryID> result = sprintOne.getIDsOfUserStoriesInSprintBacklog();
        assertEquals(expected, result);
    }

    @Test
    void getIDsOfUserStoriesInSprintBacklog_emptyBacklog_emptyList() {
        List<UserStoryID> expected = new ArrayList<>();
        when(sprintBacklog.getUserStoryCodeList()).thenReturn(new ArrayList<>());
        List<UserStoryID> result = sprintOne.getIDsOfUserStoriesInSprintBacklog();
        assertEquals(expected, result);
    }
}