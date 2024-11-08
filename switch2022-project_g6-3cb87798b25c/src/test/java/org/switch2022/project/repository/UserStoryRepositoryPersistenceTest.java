package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.assembler.UserStoryDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryJPA;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.repository.jpa.UserStoryRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserStoryRepositoryPersistenceTest {

    static private UserStoryRepositoryPersistence userStoryRepositoryPersistence;
    static private UserStory userStoryOneDouble;
    static private UserStory userStoryTwoDouble;
    static private UserStoryJPA userStoryJPAOneDouble;
    static private UserStoryJPA userStoryJPATwoDouble;
    static private UserStoryDomainDataAssembler assembler;
    static private UserStoryRepositoryJPA jpaRepositoryDouble;
    static private ProjectCode projectCodeOne;
    static private UserStoryCode userStoryCodeOne;
    static private UserStoryID userStoryIDOne;
    static private UserStoryIdJPA userStoryIdJPA;

    @BeforeAll
    static void setup() {
        jpaRepositoryDouble = mock(UserStoryRepositoryJPA.class);
        assembler = mock(UserStoryDomainDataAssembler.class);
        userStoryRepositoryPersistence = new UserStoryRepositoryPersistence(jpaRepositoryDouble, assembler);

        userStoryOneDouble = mock(UserStory.class);
        userStoryTwoDouble = mock(UserStory.class);
        userStoryJPAOneDouble = mock(UserStoryJPA.class);
        userStoryJPATwoDouble = mock(UserStoryJPA.class);

        projectCodeOne = new ProjectCode("P1");
        userStoryCodeOne = new UserStoryCode("US001");

        userStoryIDOne = new UserStoryID(projectCodeOne, userStoryCodeOne);
        userStoryIdJPA = new UserStoryIdJPA("P1", "US001");

        when(assembler.toData(userStoryOneDouble)).thenReturn(userStoryJPAOneDouble);
        when(assembler.toDomain(userStoryJPAOneDouble)).thenReturn(userStoryOneDouble);

        when(assembler.toData(userStoryTwoDouble)).thenReturn(userStoryJPATwoDouble);
        when(assembler.toDomain(userStoryJPATwoDouble)).thenReturn(userStoryTwoDouble);


    }

    @AfterAll
    static void tearDown() {
        userStoryRepositoryPersistence = null;
    }

    @Test
    void UserStoryRepositoryPersistence_NullJPARepository() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserStoryRepositoryPersistence(null, assembler));
        assertEquals("Cannot have null fields.",
                exception.getMessage());
    }

    @Test
    void UserStoryRepositoryPersistence_NullAssembler() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserStoryRepositoryPersistence(jpaRepositoryDouble, null));
        assertEquals("Cannot have null fields.",
                exception.getMessage());
    }

    @Test
    void save_SavesUserStory_Equals() {
        when(jpaRepositoryDouble.save(userStoryJPAOneDouble)).thenReturn(userStoryJPAOneDouble);

        UserStory result = userStoryRepositoryPersistence.save(userStoryOneDouble);

        assertEquals(userStoryOneDouble, result);
    }

    @Test
    void findAll_ReturnsAll_True() {
        List<UserStory> userStoryList = new ArrayList<>();
        userStoryList.add(userStoryOneDouble);
        userStoryList.add(userStoryTwoDouble);

        Iterable<UserStory> expected = userStoryList;

        List<UserStoryJPA> userStoryJPAList = new ArrayList<>();
        userStoryJPAList.add(userStoryJPAOneDouble);
        userStoryJPAList.add(userStoryJPATwoDouble);

        when(jpaRepositoryDouble.findAll()).thenReturn(userStoryJPAList);

        Iterable<UserStory> result = userStoryRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById_FindsUserStory_Equals() {
        when(jpaRepositoryDouble.findById(userStoryIdJPA)).thenReturn(Optional.of(userStoryJPAOneDouble));

        Optional<UserStory> expected = Optional.of(userStoryOneDouble);

        Optional<UserStory> result = userStoryRepositoryPersistence.findById(userStoryIDOne);

        assertEquals(expected, result);
    }

    @Test
    void findById_DoesNotFindsUserStory_EmptyOptional() {
        when(jpaRepositoryDouble.findById(userStoryIdJPA)).thenReturn(Optional.empty());

        Optional<UserStory> expected = Optional.empty();

        Optional<UserStory> result = userStoryRepositoryPersistence.findById(userStoryIDOne);

        assertEquals(expected, result);
    }

    @Test
    void existsById_UserStoryExists_True() {
        when(jpaRepositoryDouble.existsById(userStoryIdJPA)).thenReturn(true);

        boolean result = userStoryRepositoryPersistence.existsById(userStoryIDOne);

        assertTrue(result);
    }

    @Test
    void findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot_returnsList() {
        List<UserStory> userStoryList = new ArrayList<>();
        userStoryList.add(userStoryOneDouble);
        userStoryList.add(userStoryTwoDouble);

        Iterable<UserStory> expected = userStoryList;

        List<UserStoryJPA> userStoryJPAList = new ArrayList<>();
        userStoryJPAList.add(userStoryJPAOneDouble);
        userStoryJPAList.add(userStoryJPATwoDouble);

        when(jpaRepositoryDouble.findByUserStoryIdJpaProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot
                ("P1", "FINISHED", "CANCELLED")).thenReturn(userStoryJPAList);

        Iterable<UserStory> result = userStoryRepositoryPersistence.
                findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot
                        (new ProjectCode("P1"), UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertEquals(expected, result);
    }

    @Test
    void deleteAll() {
        userStoryRepositoryPersistence.deleteAll();
        verify(jpaRepositoryDouble).deleteAll();
    }

    @Test
    void findAllById_EmptyBacklog_True() {
        Iterable<UserStoryID> listOfId = new ArrayList<>();
        Iterable<UserStoryJPA> listOfUserStoryJPAs = new ArrayList<>();
        List<UserStoryIdJPA> listOfUserStoryIdJPA = new ArrayList<>();
        Iterable<UserStory> listOfUserStories = new ArrayList<>();

        when(assembler.listOfIdsToData(listOfId)).thenReturn(listOfUserStoryIdJPA);
        when(jpaRepositoryDouble.findAllById(listOfUserStoryIdJPA)).thenReturn(listOfUserStoryJPAs);
        when(assembler.userStoryJPAListToDomain(listOfUserStoryJPAs)).thenReturn(listOfUserStories);

        Iterable<UserStory> result = userStoryRepositoryPersistence.findAllById(listOfId);

        assertEquals(listOfUserStories, result);
    }

    @Test
    void findAllById_ReturnsCorrespondingList_True() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        UserStoryID userStoryIDTwo = new UserStoryID(projectCodeTwo, userStoryCodeTwo);

        UserStoryIdJPA userStoryIdJPATwoDouble = mock(UserStoryIdJPA.class);

        List<UserStoryID> listOfId = new ArrayList<>();
        listOfId.add(userStoryIDOne);
        listOfId.add(userStoryIDTwo);

        List<UserStoryJPA> listOfUserStoryJPAs = new ArrayList<>();
        listOfUserStoryJPAs.add(userStoryJPAOneDouble);
        listOfUserStoryJPAs.add(userStoryJPATwoDouble);

        List<UserStoryIdJPA> listOfUserStoryIdJPA = new ArrayList<>();
        listOfUserStoryIdJPA.add(userStoryIdJPA);
        listOfUserStoryIdJPA.add(userStoryIdJPATwoDouble);

        List<UserStory> listOfUserStories = new ArrayList<>();
        listOfUserStories.add(userStoryOneDouble);
        listOfUserStories.add(userStoryTwoDouble);

        when(assembler.listOfIdsToData(listOfId)).thenReturn(listOfUserStoryIdJPA);
        when(jpaRepositoryDouble.findAllById(listOfUserStoryIdJPA)).thenReturn(listOfUserStoryJPAs);
        when(assembler.userStoryJPAListToDomain(listOfUserStoryJPAs)).thenReturn(listOfUserStories);

        Iterable<UserStory> result = userStoryRepositoryPersistence.findAllById(listOfId);

        assertEquals(listOfUserStories, result);
    }

    @Test
    void deleteById() {
        userStoryRepositoryPersistence.deleteById(userStoryIDOne);
        verify(jpaRepositoryDouble).deleteById(userStoryIdJPA);
    }

    @Test
    void existsByUserStoryIdJpaAndUserStoryStatusNotAndUserStoryStatusNot_UserStoryExists() {
        when(jpaRepositoryDouble.existsByUserStoryIdJpaAndUserStoryStatusNotAndUserStoryStatusNot
                (userStoryIdJPA, "FINISHED", "CANCELLED"))
                .thenReturn(true);

        boolean result = userStoryRepositoryPersistence
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryIDOne, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertTrue(result);
    }

    @Test
    void existsByUserStoryIdAndStatusNotLike_UserStoryDoesNotExist() {
        when(jpaRepositoryDouble.existsByUserStoryIdJpaAndUserStoryStatusNotAndUserStoryStatusNot
                (userStoryIdJPA, "FINISHED", "CANCELLED"))
                .thenReturn(false);

        boolean result = userStoryRepositoryPersistence
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryIDOne, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertFalse(result);
    }

}

