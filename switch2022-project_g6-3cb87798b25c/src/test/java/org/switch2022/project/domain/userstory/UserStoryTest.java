package org.switch2022.project.domain.userstory;

import org.junit.jupiter.api.*;
import org.switch2022.project.domain.valueobject.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class UserStoryTest {
    private static UserStory firstUserStory;
    private static UserStory secondUserStory;
    private static UserStory thirdUserStory;
    private static UserStory fourthUserStory;
    private static UserStory fifthUserStory;
    private static ProjectCode projectCodeOne;
    private static UserStoryCode userStoryCodeOne;
    private static UserStoryCode userStoryCodeTwo;
    private static ProjectCode projectCodeDouble;
    private static UserStoryCode userStoryCodeDouble;
    private static Description descriptionDouble;
    private static Description description;


    @BeforeAll
    static void init() {
        projectCodeOne = new ProjectCode("01");
        userStoryCodeTwo = new UserStoryCode("US300");
        ProjectCode projectCodeTwo = new ProjectCode("02");
        description = new Description("this is a description");

        userStoryCodeOne = new UserStoryCode("US001");
        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        firstUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeOne), description);
        secondUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeTwo), description);
        thirdUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeOne), description);
        fourthUserStory = new UserStory(new UserStoryID(projectCodeTwo, userStoryCodeOne), description);
        fifthUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeOne), description);


        projectCodeDouble = mock(ProjectCode.class);
        userStoryCodeDouble = mock(UserStoryCode.class);
        descriptionDouble = mock(Description.class);
    }

    @AfterAll
    static void tearDown() {
        firstUserStory = null;
        secondUserStory = null;
        thirdUserStory = null;
        fourthUserStory = null;
        fifthUserStory = null;
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenUserStoryIDIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new UserStory(null, descriptionDouble));
        assertEquals("User Story ID cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenDescriptionIsNull() {
        UserStoryID id = new UserStoryID(projectCodeDouble, userStoryCodeDouble);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new UserStory(id, null));
        assertEquals("User Story Description cannot be null.", exception.getMessage());
    }

    @Test
    void setFinishedAndHasStatusIsWorkingAsIntended_True() {
        //Arrange
        fifthUserStory.setFinished();

        //Act
        boolean result = fifthUserStory.hasStatus(UserStoryStatus.FINISHED);

        //Assert
        assertTrue(result);
    }

    @Test
    void StatusIsWorkingAsIntendedInAPlannedUserStory_True() {
        //Act
        boolean result = firstUserStory.hasStatus(UserStoryStatus.OPEN);

        //Assert
        assertTrue(result);
    }

    @Test
    void identity_equalProjectAndUserStoryCodes_true() {
        UserStoryID expected = new UserStoryID(projectCodeOne, userStoryCodeOne);
        UserStoryID result = firstUserStory.identity();
        assertEquals(expected, result);
    }

    @Test
    void identity_differentProjectCode_false() {
        UserStoryID expected = new UserStoryID(projectCodeOne, userStoryCodeOne);
        UserStoryID result = fourthUserStory.identity();
        assertNotEquals(expected, result);
    }

    @Test
    void identity_differentUserStoryCode_false() {
        UserStoryID expected = new UserStoryID(projectCodeOne, userStoryCodeOne);
        UserStoryID result = secondUserStory.identity();
        assertNotEquals(expected, result);
    }

    @Test
    void equals_NullObject_False() {
        boolean result = firstUserStory.equals(null);

        assertFalse(result);
    }

    @Test
    void testEquals_sameObject_true() {
        assertEquals(firstUserStory, firstUserStory);
    }

    @Test
    void testEquals_equalObject_equals() {
        assertEquals(firstUserStory, thirdUserStory);
    }

    @Test
    void testEquals_differentInstancesWitDifferentProjectCode_false() {

        assertNotEquals(firstUserStory, fourthUserStory);
    }

    @Test
    void testEquals_differentInstancesWitDifferentUserStoryCodeCode_false() {

        assertNotEquals(firstUserStory, secondUserStory);
    }

    @Test
    void testEquals_differentInstancesWitDifferentProjectAndUserStoryCodeCode_false() {

        assertNotEquals(secondUserStory, fourthUserStory);
    }

    @Test
    void testEquals_differentInstancesWitSameProjectAndUserStoryCodeCode_true() {

        assertEquals(firstUserStory, fifthUserStory);
    }

    @Test
    void testEquals_objectsOfDifferentType_false() {
        String newObject = "A string";
        assertFalse(firstUserStory.equals(newObject));
    }

    @Test
    void testHashCode_sameHashCodes() {
        int hashOne = firstUserStory.hashCode();
        int hashTwo = thirdUserStory.hashCode();
        assertEquals(hashOne, hashTwo);
    }

    @Test
    void testHashCode_differentHashCodes() {
        assertNotEquals(firstUserStory.hashCode(), fourthUserStory.hashCode());
    }

    @Test
    void sameAS_sameUserStoryCodeAndProjectCode_True() {
        UserStory newUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeOne), description);
        boolean result = firstUserStory.sameAs(newUserStory);
        assertTrue(result);
    }

    @Test
    void sameAS_differentUserStoryCodeSameProjectCode_False() {
        UserStory newUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeTwo), description);
        boolean result = firstUserStory.sameAs(newUserStory);
        assertFalse(result);
    }

    @Test
    void sameAS_differentProjectCodeSameUserStoryCode_False() {
        ProjectCode projectCodeTwo = new ProjectCode("P785");
        UserStory newUserStory = new UserStory(new UserStoryID(projectCodeTwo, userStoryCodeOne), description);
        boolean result = firstUserStory.sameAs(newUserStory);
        assertFalse(result);
    }

    @Test
    void sameAS_DifferentDescription_False() {
        Description differentDescription = new Description("A new description");
        UserStory newUserStory = new UserStory(new UserStoryID(projectCodeOne, userStoryCodeOne), differentDescription);
        boolean result = firstUserStory.sameAs(newUserStory);
        assertFalse(result);
    }

    @Test
    void sameAS_sameStatus_False() {
        ProjectCode projectCode = new ProjectCode("P99");
        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        UserStory newUserStory = new UserStory(new UserStoryID(projectCode, userStoryCodeTwo), descriptionDouble);

        boolean result = firstUserStory.sameAs(newUserStory);
        assertFalse(result);
    }

    @Test
    void sameAS_differentStatus_False() {
        UserStory newUserStory = new UserStory(new UserStoryID(projectCodeDouble, userStoryCodeOne), description);
        newUserStory.changeStatus(UserStoryStatus.CANCELLED);

        boolean result = firstUserStory.sameAs(newUserStory);
        assertFalse(result);
    }

    @Test
    void sameAS_EverythingDifferent_False() {
        ProjectCode projectCode = new ProjectCode("P99");
        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        Description differentDescription = new Description("different Description!!!!");
        UserStory newUserStory = new UserStory(new UserStoryID(projectCode, userStoryCodeTwo), differentDescription);
        newUserStory.changeStatus(UserStoryStatus.CANCELLED);

        boolean result = firstUserStory.sameAs(newUserStory);
        assertFalse(result);
    }

    @Test
    void sameAS_instanceOfDifferentClass_False() {
        Object object = new Object();
        boolean result = firstUserStory.sameAs(object);
        assertFalse(result);
    }

    @Test
    void getUserStoryCode_UserStoryCodeExists_UserStoryCode() {
        UserStory userStory = new UserStory(new UserStoryID(projectCodeDouble, userStoryCodeDouble), descriptionDouble);

        UserStoryCode userStoryCode = userStory.getUserStoryCode();

        boolean result = userStoryCode.equals(userStoryCodeDouble);

        assertTrue(result);
    }

    @Test
    void getDescription_DescriptionExists_Description() {
        Description description = new Description("This is a description.");
        Description sameDescription = new Description("This is a description.");
        UserStory userStory = new UserStory(new UserStoryID(projectCodeDouble, userStoryCodeDouble), description);

        Description userStoryDescription = userStory.getDescription();

        boolean result = userStoryDescription.equals(sameDescription);

        assertTrue(result);
    }

    @Test
    void getProjectCode_ReturnsProjectCode_ProjectCode() {
        ProjectCode projectCode = firstUserStory.getProjectCode();

        boolean result = projectCode.equals(projectCodeOne);

        assertTrue(result);
    }

    @Test
    void getStatus_ReturnsStatus_Status() {
        UserStoryStatus status = firstUserStory.getUserStoryStatus();

        boolean result = status.equals(UserStoryStatus.OPEN);

        assertTrue(result);
    }

    @Test
    public void testDefineEffort() {
        UserStoryID userStoryID = new UserStoryID(new ProjectCode("P1"), new UserStoryCode("US01"));
        Description description = new Description("description");
        UserStory userStory = new UserStory(userStoryID, description);

        Effort effortValue = Effort.THREE;
        userStory.defineEffort(effortValue);

        assertEquals(effortValue, userStory.getEffort());
    }
}

