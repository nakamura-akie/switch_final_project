package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.*;

import org.switch2022.project.datamodel.jpa.profile.ProfileJPA;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.profile.ProfileFactory;
import org.switch2022.project.domain.valueobject.ProfileName;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProfileDomainDataAssemblerTest {

    private static ProfileDomainDataAssembler profileDomainDataAssembler;
    private static Profile profileDouble;
    private static ProfileJPA profileJPA;

    @BeforeAll
    static void init() {
        ProfileFactory profileFactoryDouble = mock(ProfileFactory.class);
        profileDomainDataAssembler = new ProfileDomainDataAssembler(profileFactoryDouble);

        String profileID = "a new profile";
        ProfileName profileName = new ProfileName(profileID);

        profileDouble = mock(Profile.class);
        when(profileDouble.identity()).thenReturn(profileName);

        profileJPA = new ProfileJPA(profileID);
        when(profileFactoryDouble.createProfile(profileName)).thenReturn(profileDouble);
    }

    @AfterAll
    static void tearDown() {
        profileDomainDataAssembler = null;
        profileDouble = null;
        profileJPA = null;
    }

    @Test
    void constructor_NullProfileFactory_ThrowsException() {
        String expected = "Profile Factory cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ProfileDomainDataAssembler(null);}).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void toData() {

        ProfileJPA result = profileDomainDataAssembler.toData(profileDouble);

        assertEquals(profileJPA, result);
    }

    @Test
    void toDomain() {

        Profile result = profileDomainDataAssembler.toDomain(profileJPA);

        assertEquals(profileDouble, result);
    }

}