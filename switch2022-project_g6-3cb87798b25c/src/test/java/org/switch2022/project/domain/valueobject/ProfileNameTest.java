package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ProfileNameTest {

    private static ProfileName managerID;

    @BeforeAll
    static void init() {
        managerID = new ProfileName("manager");
    }

    @AfterAll
    static void tearDown() {
        managerID = null;
    }

    @Test
    void constructor_NullProfileName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProfileName(null));
        assertEquals("Profile name cannot be null", exception.getMessage());
    }

    @Test
    void constructor_BlankProfileName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProfileName(""));
        assertEquals("Profile name cannot be blank", exception.getMessage());
    }

    @Test
    void getProfileNameValue() {
        String expected = "manager";

        String result = managerID.getProfileNameValue();

        assertEquals(expected, result);
    }

    @Test
    void equals_NullObject_False() {
        boolean result = managerID.equals(null);

        assertFalse(result);
    }


    @Test
    void equals_SameObject_True() {
        ProfileName anotherManagerID = managerID;

        boolean result = managerID.equals(anotherManagerID);

        assertTrue(result);
    }

    @Test
    void equals_SameObjectUpperCase_True() {
        ProfileName upperCaseManagerID = new ProfileName("Manager");

        boolean result = managerID.equals(upperCaseManagerID);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsOfDifferentClasses_False() {
        String profile = "This is not a profile";

        boolean result = managerID.equals(profile);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithSameAttributes_True() {
        ProfileName sameManagerID = new ProfileName("manager");

        boolean result = managerID.equals(sameManagerID);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentProfileName_False() {
        ProfileName userID = new ProfileName("user");

        boolean result = managerID.equals(userID);

        assertFalse(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        ProfileName anotherManagerID = managerID;

        int firstHashCode = managerID.hashCode();
        int secondHashCode = anotherManagerID.hashCode();

        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        ProfileName userID = new ProfileName("user");

        int firstHashCode = managerID.hashCode();
        int secondHashCode = userID.hashCode();

        assertNotEquals(firstHashCode, secondHashCode);
    }

}