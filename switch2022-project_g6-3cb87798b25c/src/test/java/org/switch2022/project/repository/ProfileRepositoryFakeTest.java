package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.valueobject.ProfileName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProfileRepositoryFakeTest {

    private ProfileRepositoryFake profileRepository;
    private List<Profile> profileList;
    private ProfileName professorID;
    private Profile professor;

    @BeforeEach
    void init() {
        profileList = new ArrayList<>();
        profileRepository = new ProfileRepositoryFake();

        professor = mock(Profile.class);
        professorID = new ProfileName("professor");
        when(professor.identity()).thenReturn(professorID);
    }

    @AfterEach
    void tearDown() {
        professor = null;
        professorID = null;
        profileList.clear();
        profileList = null;
        profileRepository.deleteAll();
        profileRepository = null;
    }

    @Test
    void save_CreateNewProfile_CreatedAndSaved() {
        Profile result = profileRepository.save(professor);

        assertEquals(professor, result);
    }

    @Test
    void save_TryToCreateExistentProfile_ThrowsException() {
        profileRepository.save(professor);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                profileRepository.save(professor));

        assertEquals("Profile already exists",
                exception.getMessage());
    }

    @Test
    void findAll_ReturnsListOfProfiles() {
        profileList.add(professor);
        profileRepository.save(professor);

        Iterable<Profile> result = profileRepository.findAll();

        assertEquals(profileList, result);
    }

    @Test
    void findAll_ReturnsEmptyList() {
        Iterable<Profile> result = profileRepository.findAll();

        assertEquals(profileList, result);
    }

    @Test
    void findByID_ExistentProfile_Equals() {
        profileRepository.save(professor);

        Optional<Profile> expected = Optional.of(professor);

        Optional<Profile> result = profileRepository.findById(professorID);

        assertEquals(expected, result);
    }

    @Test
    void findByID_EmptyRepository_Equals() {
        ProfileName profileName = new ProfileName("a new profile name");

        Optional<Profile> expected = Optional.empty();

        Optional<Profile> result = profileRepository.findById(profileName);

        assertEquals(expected, result);
    }

    @Test
    void findByID_ProfileDoesNotExist_Equals() {
        ProfileName profileID = new ProfileName("a new profile name");

        Optional<Profile> expected = Optional.empty();

        Optional<Profile> result = profileRepository.findById(profileID);

        assertEquals(expected, result);
    }

    @Test
    void existsByID_ExistentProfile_True() {
        profileRepository.save(professor);

        boolean result = profileRepository.existsById(professorID);

        assertTrue(result);
    }

    @Test
    void existsByID_EmptyRepository_False() {
        ProfileName otherProfile = new ProfileName("other profile");

        boolean result = profileRepository.existsById(otherProfile);

        assertFalse(result);
    }

    @Test
    void existsByID_ProfileDoesNotExist_Equals() {
        ProfileName profileID = new ProfileName("a new profile name");
        Profile profile = mock(Profile.class);
        profileRepository.save(profile);
        when(profile.identity()).thenReturn(profileID);

        boolean result = profileRepository.existsById(professorID);

        assertFalse(result);
    }

    @Test
    void deleteAll() {
        profileRepository.save(professor);
        profileRepository.deleteAll();

        Iterable<Profile> expected = new ArrayList<>();

        Iterable<Profile> result = profileRepository.findAll();

        assertEquals(expected, result);
    }

    @Test
    void deleteByID_RemovesProfile_Equals() {
        ProfileName profileID = new ProfileName("a new profile name");
        Profile profile = mock(Profile.class);
        profileRepository.save(profile);
        when(profile.identity()).thenReturn(profileID);
        
        List<Profile> expected = new ArrayList<>();

        profileRepository.deleteById(profileID);

        Iterable<Profile> result = profileRepository.findAll();

        assertEquals(expected, result);
    }
}