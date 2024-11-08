package org.switch2022.project.utils.domainevent;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CloseUserStoryEventTest {

    @Test
    void getUserStoryID_UserStoryID() {
        UserStoryID userStoryID = new UserStoryID(new ProjectCode("P001"), new UserStoryCode("US001"));

        FinishUserStoryEvent addUserStoryEvent = new FinishUserStoryEvent(userStoryID);

        UserStoryID expected = userStoryID;

        UserStoryID result = addUserStoryEvent.getUserStoryID();

        assertEquals(expected, result);
    }

    @Test
    void equals_SameObject_True() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        FinishUserStoryEvent event = new FinishUserStoryEvent(userStoryID);
        FinishUserStoryEvent anotherEvent = event;

        boolean result = event.equals(anotherEvent);

        assertTrue(result);
    }

    @Test
    void equals_NullObject_False() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        FinishUserStoryEvent event = new FinishUserStoryEvent(userStoryID);

        boolean result = event.equals(null);

        assertFalse(result);
    }

    @Test
    void equals_DifferentClass_False() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        FinishUserStoryEvent event = new FinishUserStoryEvent(userStoryID);
        String anotherEvent = "An event";

        boolean result = event.equals(anotherEvent);

        assertFalse(result);
    }

    @Test
    void equals_SameClassSameAttribute_False() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        FinishUserStoryEvent event = new FinishUserStoryEvent(userStoryID);
        FinishUserStoryEvent anotherEvent = new FinishUserStoryEvent(userStoryID);

        boolean result = event.equals(anotherEvent);

        assertTrue(result);
    }

    @Test
    void equals_SameClassDifferentAttribute_False() {
        FinishUserStoryEvent event = new FinishUserStoryEvent(mock(UserStoryID.class));
        FinishUserStoryEvent anotherEvent = new FinishUserStoryEvent(mock(UserStoryID.class));

        boolean result = event.equals(anotherEvent);

        assertFalse(result);
    }

    @Test
    void hasCode_SameAttribute_Integer() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        FinishUserStoryEvent event = new FinishUserStoryEvent(userStoryID);

        int expected = Objects.hash(userStoryID);

        int result = event.hashCode();

        assertEquals(expected, result);
    }
}
