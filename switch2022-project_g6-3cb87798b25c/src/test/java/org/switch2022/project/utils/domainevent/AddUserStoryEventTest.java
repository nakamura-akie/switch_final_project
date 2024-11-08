package org.switch2022.project.utils.domainevent;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AddUserStoryEventTest {

    @Test
    void getUserStoryID_UserStoryID() {
        UserStoryID userStoryID = new UserStoryID(new ProjectCode("P001"), new UserStoryCode("US001"));

        AddUserStoryEvent addUserStoryEvent = new AddUserStoryEvent(userStoryID);

        UserStoryID expected = userStoryID;

        UserStoryID result = addUserStoryEvent.getUserStoryID();

        assertEquals(expected, result);
    }

    @Test
    void equals_SameObject_True() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        AddUserStoryEvent event = new AddUserStoryEvent(userStoryID);
        AddUserStoryEvent anotherEvent = event;

        boolean result = event.equals(anotherEvent);

        assertTrue(result);
    }

    @Test
    void equals_NullObject_False() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        AddUserStoryEvent event = new AddUserStoryEvent(userStoryID);

        boolean result = event.equals(null);

        assertFalse(result);
    }

    @Test
    void equals_DifferentClass_False() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        AddUserStoryEvent event = new AddUserStoryEvent(userStoryID);
        String anotherEvent = "An event";

        boolean result = event.equals(anotherEvent);

        assertFalse(result);
    }

    @Test
    void equals_SameClassSameAttribute_False() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        AddUserStoryEvent event = new AddUserStoryEvent(userStoryID);
        AddUserStoryEvent anotherEvent = new AddUserStoryEvent(userStoryID);

        boolean result = event.equals(anotherEvent);

        assertTrue(result);
    }

    @Test
    void equals_SameClassDifferentAttribute_False() {
        AddUserStoryEvent event = new AddUserStoryEvent(mock(UserStoryID.class));
        AddUserStoryEvent anotherEvent = new AddUserStoryEvent(mock(UserStoryID.class));

        boolean result = event.equals(anotherEvent);

        assertFalse(result);
    }

    @Test
    void hasCode_SameAttribute_Integer() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        AddUserStoryEvent event = new AddUserStoryEvent(userStoryID);

        int expected = Objects.hash(userStoryID);

        int result = event.hashCode();

        assertEquals(expected, result);
    }
}