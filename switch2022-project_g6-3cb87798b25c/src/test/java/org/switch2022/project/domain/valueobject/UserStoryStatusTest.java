package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;
import org.switch2022.project.utils.exception.InvalidOperationException;

import static org.junit.jupiter.api.Assertions.*;

class UserStoryStatusTest {

    @Test
    void fromString_CancelledStatus() {
        UserStoryStatus expected = UserStoryStatus.CANCELLED;

        UserStoryStatus result = UserStoryStatus.fromString("CANCELLED");

        assertEquals(expected, result);
    }

    @Test
    void fromString_OpenStatus() {
        UserStoryStatus expected = UserStoryStatus.OPEN;

        UserStoryStatus result = UserStoryStatus.fromString("OPEN");

        assertEquals(expected, result);
    }

    @Test
    void fromString_RunningStatus() {
        UserStoryStatus expected = UserStoryStatus.RUNNING;

        UserStoryStatus result = UserStoryStatus.fromString("RUNNING");

        assertEquals(expected, result);
    }

    @Test
    void fromString_FinishedStatus() {
        UserStoryStatus expected = UserStoryStatus.FINISHED;

        UserStoryStatus result = UserStoryStatus.fromString("FINISHED");

        assertEquals(expected, result);
    }

    @Test
    void fromString_InvalidStatusString() {
        String expected = "Invalid User Story Status";

        String result = assertThrows(InvalidConstructorArgumentException.class, () ->
                UserStoryStatus.fromString("INVALID_STATUS")).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void isFinished() {
        assertTrue(UserStoryStatus.FINISHED.isFinished());
    }

    @Test
    void isCancelled() {
        assertTrue(UserStoryStatus.CANCELLED.isCancelled());
    }
}