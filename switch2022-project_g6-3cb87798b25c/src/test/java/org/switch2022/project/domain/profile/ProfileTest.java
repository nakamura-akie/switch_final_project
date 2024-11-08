package org.switch2022.project.domain.profile;

import org.junit.jupiter.api.*;
import org.switch2022.project.domain.valueobject.ProfileName;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private static ProfileName managerID;
    private static Profile manager;

    @BeforeAll
    static void init() {
        managerID = new ProfileName("manager");
        manager = new Profile(managerID);
    }

    @AfterAll
    static void tearDown() {
        managerID = null;
        manager = null;
    }

    @Test
    void constructor_NullProfileName_ThrowsException() {
        String expected = "Profile name cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new Profile(null);}).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void identity_SameProfileName_True() {
        Profile anotherManager = new Profile(managerID);

        ProfileName result = anotherManager.identity();

        assertEquals(managerID, result);
    }

    @Test
    void identity_DifferentProfileName_False() {
        ProfileName userID = new ProfileName("user");
        Profile user = new Profile(userID);

        ProfileName result = user.identity();

        assertNotEquals(managerID, result);
    }

    @Test
    void sameAs_SameProfileName_True() {
        Profile anotherManager = new Profile(managerID);

        boolean result = manager.sameAs(anotherManager);

        assertTrue(result);
    }
    @Test
    void sameAs_DifferentProfileName_False() {
        ProfileName userID = new ProfileName("user");
        Profile user = new Profile(userID);

        boolean result = manager.sameAs(user);

        assertFalse(result);
    }

    @Test
    void sameAs_ObjectsOfDifferentClasses_False() {
        String profile = "This is not a profile";

        boolean result = manager.sameAs(profile);

        assertFalse(result);
    }

    @Test
    void equals_NullObject_False() {
        boolean result = manager.equals(null);

        assertFalse(result);
    }

    @Test
    void equals_SameObject_True() {
        Profile anotherManager = manager;

        boolean result = manager.equals(anotherManager);

        assertTrue(result);
    }

    @Test
    void equals_SameObjectUpperCase_True() {
        ProfileName upperCaseManager = new ProfileName("Manager");
        Profile anotherManager = new Profile(upperCaseManager);

        boolean result = manager.equals(anotherManager);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsOfDifferentClasses_False() {
        String someString = "This is not a profile";

        boolean result = manager.equals(someString);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithSameProfileName_True() {
        Profile anotherManager = new Profile(managerID);

        boolean result = manager.equals(anotherManager);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentProfileName_False() {
        ProfileName userID = new ProfileName("user");
        Profile user = new Profile(userID);

        boolean result = manager.equals(user);

        assertFalse(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        Profile anotherManager = new Profile(managerID);

        int firstHashCode = manager.hashCode();
        int secondHashCode = anotherManager.hashCode();

        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        ProfileName userID = new ProfileName("user");
        Profile user = new Profile(userID);

        int firstHashCode = manager.hashCode();
        int secondHashCode = user.hashCode();

        assertNotEquals(firstHashCode, secondHashCode);
    }

}