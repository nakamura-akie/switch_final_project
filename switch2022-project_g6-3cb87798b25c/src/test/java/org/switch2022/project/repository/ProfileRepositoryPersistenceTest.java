package org.switch2022.project.repository;

import org.junit.jupiter.api.*;
import org.switch2022.project.datamodel.jpa.profile.ProfileJPA;
import org.switch2022.project.datamodel.assembler.ProfileDomainDataAssembler;
import org.switch2022.project.domain.profile.Profile;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.jpa.ProfileRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileRepositoryPersistenceTest {

    private ProfileRepositoryPersistence profileRepository;
    private ProfileDomainDataAssembler profileAssembler;
    private ProfileRepositoryJPA repositoryJPA;
    private List<Profile> profileList;
    private ProfileName administratorID;
    private Profile administrator;
    private ProfileJPA administratorJPA;

    @BeforeEach
    void init() {
        profileList = new ArrayList<>();
        repositoryJPA = mock(ProfileRepositoryJPA.class);
        profileAssembler = mock(ProfileDomainDataAssembler.class);
        profileRepository = new ProfileRepositoryPersistence(repositoryJPA, profileAssembler);

        administrator = mock(Profile.class);
        administratorJPA = mock(ProfileJPA.class);
        administratorID = new ProfileName("administrator");
        when(administrator.identity()).thenReturn(administratorID);
    }

    @AfterEach
    void tearDown() {
        profileRepository.deleteAll();
        profileRepository = null;
        profileAssembler = null;
        repositoryJPA = null;
        profileList.clear();
        profileList = null;
        administratorID = null;
        administrator = null;
    }

    @Test
    void ProfileRepositoryPersistence_NullProfileJPARepository() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ProfileRepositoryPersistence(null, profileAssembler));
        assertEquals("Profile Repository JPA cannot be null",
                exception.getMessage());
    }

    @Test
    void ProfileRepositoryPersistence_NullProfileAssembler() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new ProfileRepositoryPersistence(repositoryJPA, null));
        assertEquals("Profile Assembler cannot be null",
                exception.getMessage());
    }

    @Test
    void save_CreateNewProfile_CreatedAndSaved() {
        when(profileAssembler.toData(administrator)).thenReturn(administratorJPA);
        when(repositoryJPA.save(administratorJPA)).thenReturn(administratorJPA);
        when(profileAssembler.toDomain(administratorJPA)).thenReturn(administrator);

        Profile result = profileRepository.save(administrator);

        assertEquals(administrator, result);
    }

    @Test
    void findAll_ReturnsListOfProfiles() {
        profileList.add(administrator);

        List<ProfileJPA> profileJpaList = new ArrayList<>();
        profileJpaList.add(administratorJPA);

        when(profileAssembler.toDomain(administratorJPA)).thenReturn(administrator);
        when(repositoryJPA.findAll()).thenReturn(profileJpaList);

        Iterable<Profile> result = profileRepository.findAll();

        assertEquals(profileList, result);
    }

    @Test
    void findAll_ReturnsEmptyList() {
        when(repositoryJPA.findAll()).thenReturn(new ArrayList<>());

        Iterable<Profile> result = profileRepository.findAll();

        assertEquals(profileList, result);
    }

    @Test
    void findById_ExistentProfile_Equals() {
        String id = administratorID.getProfileNameValue();
        when(repositoryJPA.findById(id)).thenReturn(Optional.ofNullable(administratorJPA));
        when(profileAssembler.toDomain(administratorJPA)).thenReturn(administrator);

        Optional<Profile> expected = Optional.of(administrator);
        Optional<Profile> result = profileRepository.findById(administratorID);

        assertEquals(expected, result);
    }

    @Test
    void findByID_EmptyRepository_Equals() {
        ProfileName profileName = new ProfileName("a new profile name");
        String id = profileName.getProfileNameValue();
        when(repositoryJPA.findById(id)).thenReturn(Optional.empty());

        Optional<Profile> expected = Optional.empty();
        Optional<Profile> result = profileRepository.findById(administratorID);

        assertEquals(expected, result);
    }

    @Test
    void findByID_ProfileDoesNotExist_Equals() {
        ProfileName profileID = new ProfileName("a new profile name");
        String id = profileID.getProfileNameValue();
        when(administratorJPA.getProfileName()).thenReturn(id);

        String jpaId = administratorID.getProfileNameValue();
        when(repositoryJPA.findById(jpaId)).thenReturn(Optional.empty());

        Optional<Profile> expected = Optional.empty();

        Optional<Profile> result = profileRepository.findById(administratorID);

        assertEquals(expected, result);
    }

    @Test
    void existsById_ExistentProfile_True() {
        String id = administratorID.getProfileNameValue();
        when(repositoryJPA.existsById(id)).thenReturn(true);

        boolean result = profileRepository.existsById(administratorID);

        assertTrue(result);
    }

    @Test
    void existsByID_EmptyRepository_False() {
        ProfileName profileName = new ProfileName("a new profile name");
        String id = profileName.getProfileNameValue();
        when(repositoryJPA.existsById(id)).thenReturn(false);

        boolean result = profileRepository.existsById(profileName);

        assertFalse(result);
    }

    @Test
    void existsByID_ProfileDoesNotExist_Equals() {
        ProfileName profileID = new ProfileName("a new profile name");
        String id = profileID.getProfileNameValue();
        when(administratorJPA.getProfileName()).thenReturn(id);

        String jpaId = administratorID.getProfileNameValue();
        when(repositoryJPA.existsById(jpaId)).thenReturn(false);

        boolean result = profileRepository.existsById(administratorID);

        assertFalse(result);
    }


    @Test
    void deleteAll() {
        profileRepository.deleteAll();
        verify(repositoryJPA).deleteAll();
    }

    @Test
    void deleteById() {
        String id = administratorID.getProfileNameValue();
        profileRepository.deleteById(administratorID);
        verify(repositoryJPA).deleteById(id);
    }
}