package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.profile.ProfileFactory;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.interfaces.ProfileRepository;
import org.switch2022.project.utils.dto.NewProfileDTO;
import org.switch2022.project.utils.dto.OutputProfileDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles ("test")
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;
    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private ProfileFactory profileFactory;
    private ProfileName administratorID;
    private Profile administrator;

    @BeforeEach
    void init() {
        administratorID = new ProfileName("administrator");
        administrator = mock(Profile.class);
    }

    @AfterEach
    void tearDown() {
        profileService = null;
        profileRepository = null;
        profileFactory = null;
        administratorID = null;
        administrator = null;
    }

    @Test
    void constructor_NullProfileRepository_ThrowsException() {
        String expected = "Profile Repository cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ProfileService(null, profileFactory);}).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullProfileFactory_ThrowsException() {
        String expected = "Profile Factory cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ProfileService(profileRepository, null);}).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void createProfile_CreatesNewProfile_True() {
        when(profileRepository.existsById(administratorID)).thenReturn(false);
        when(profileFactory.createProfile(administratorID)).thenReturn(administrator);
        when(profileRepository.save(administrator)).thenReturn(administrator);
        when(administrator.identity()).thenReturn(administratorID);

        NewProfileDTO newProfileDTO = new NewProfileDTO();
        newProfileDTO.profileName = administratorID;

        OutputProfileDTO outputProfileDTO = new OutputProfileDTO();
        outputProfileDTO.profileName = "administrator";

        OutputProfileDTO result = profileService.createProfile(newProfileDTO);

        assertEquals(outputProfileDTO, result);
    }

    @Test
    void createProfile_TryToCreateExistentProfile_False() {
       when(profileRepository.existsById(administratorID)).thenReturn(true);

        NewProfileDTO newProfileDTO = new NewProfileDTO();
        newProfileDTO.profileName = administratorID;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                profileService.createProfile(newProfileDTO));

        assertEquals("Profile already exists",
                exception.getMessage());
    }

    @Test
    void createDefaultProfiles() {
        String profileNameValue = "profile";
        ProfileName profileID = new ProfileName(profileNameValue);
        Profile defaultProfile = mock(Profile.class);

        when(profileFactory.createProfile(profileID)).thenReturn(defaultProfile);

        profileService.createDefaultProfile(profileNameValue);

        verify(profileRepository).save(defaultProfile);
    }

    @Test
    void getProfile_GetExistentProfile_True() {
        when(profileRepository.findById(administratorID)).thenReturn(Optional.ofNullable(administrator));
        when(administrator.identity()).thenReturn(administratorID);

        OutputProfileDTO expected = new OutputProfileDTO();
        expected.profileName = administratorID.getProfileNameValue();

        OutputProfileDTO result = profileService.findProfile("administrator");

        assertEquals(expected, result);
    }

    @Test
    void getProfile_GetNonExistentProfile_ThrowsException() {
        when(profileRepository.findById(administratorID)).thenReturn(Optional.empty());
        String profileName = administratorID.getProfileNameValue();

        String expected = "Profile not found";

        String result = assertThrows(NoSuchElementException.class, () -> {
            profileService.findProfile(profileName);}).getMessage();

        assertEquals(expected, result);

        assertEquals(expected, result);
    }

}