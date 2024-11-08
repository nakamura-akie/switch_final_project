package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.profile.ProfileJPA;

import static org.junit.jupiter.api.Assertions.*;

class ProfileJPATest {

    private static String profileID;
    private static ProfileJPA profileJPA;

    @BeforeAll
    static void init() {
        profileID = "a new profile";
        profileJPA = new ProfileJPA(profileID);
    }

    @AfterAll
    static void tearDown() {
        profileID = null;
        profileJPA = null;
    }

    @Test
    void getProfileName_ProfileName() {

        String result = profileJPA.getProfileName();

        assertEquals(profileID, result);
    }

    @Test
    void equals_SameObject_True() {
        ProfileJPA anotherProfileJPA = profileJPA;

        boolean result = profileJPA.equals(anotherProfileJPA);

        assertTrue(result);
    }

    @Test
    void equals_EqualObject_True() {
        ProfileJPA anotherProfileJPA = new ProfileJPA(profileID);

        boolean result = profileJPA.equals(anotherProfileJPA);

        assertTrue(result);
    }

    @Test
    void equals_DifferentObject_False() {
        String anotherProfileID = "not a profile";
        ProfileJPA anotherProfileJPA = new ProfileJPA(anotherProfileID);

        boolean result = profileJPA.equals(anotherProfileJPA);

        assertFalse(result);

    }

    @Test
    void equals_ObjectOfDifferentType_False() {
        String anotherProfileJPA = "not a profile";

        boolean result = profileJPA.equals(anotherProfileJPA);

        assertFalse(result);

    }

    @Test
    void equals_NullObject_False() {

        boolean result = profileJPA.equals(null);

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        ProfileJPA anotherProfileJPA = profileJPA;

        int firstHash = profileJPA.hashCode();
        int secondHash = anotherProfileJPA.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        String anotherProfileID = "not a profile";
        ProfileJPA anotherProfileJPA = new ProfileJPA(anotherProfileID);

        int firstHash = profileJPA.hashCode();
        int secondHash = anotherProfileJPA.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}