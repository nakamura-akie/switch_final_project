package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.domain.valueobject.UserStoryStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserStoryRepositoryFakeTest {

    private UserStoryRepositoryFake userStoryRepositoryFake;
    private List<UserStory> userStoryList;
    private List<ProjectCode> projectCodeList;
    private UserStory userStoryDoubleOne;
    private UserStoryID pairDoubleOne;
    private UserStoryID pairDoubleTwo;

    @BeforeEach
    void init() {
        userStoryRepositoryFake = new UserStoryRepositoryFake();

        UserStory userStoryDoubleOne = mock(UserStory.class);
        UserStory userStoryDoubleTwo = mock(UserStory.class);
        UserStory userStoryDoubleThree = mock(UserStory.class);
        UserStory userStoryDoubleFour = mock(UserStory.class);
        UserStory userStoryDoubleFive = mock(UserStory.class);
        UserStory userStoryDoubleSix = mock(UserStory.class);
        this.userStoryList = Arrays.asList(
                userStoryDoubleOne,
                userStoryDoubleTwo,
                userStoryDoubleThree,
                userStoryDoubleFour,
                userStoryDoubleFive,
                userStoryDoubleSix);

        this.userStoryRepositoryFake.save(userStoryDoubleOne);
        this.userStoryRepositoryFake.save(userStoryDoubleTwo);
        this.userStoryRepositoryFake.save(userStoryDoubleThree);
        this.userStoryRepositoryFake.save(userStoryDoubleFour);
        this.userStoryRepositoryFake.save(userStoryDoubleFive);
        this.userStoryRepositoryFake.save(userStoryDoubleSix);

        ProjectCode projectCodeDoubleOne = mock(ProjectCode.class);
        ProjectCode projectCodeDoubleTwo = mock(ProjectCode.class);
        this.projectCodeList = Arrays.asList(projectCodeDoubleOne, projectCodeDoubleTwo);

        UserStoryID pairDoubleOne = mock(UserStoryID.class);
        UserStoryID pairDoubleTwo = mock(UserStoryID.class);

        when(userStoryDoubleOne.identity()).thenReturn(pairDoubleOne);
        when(userStoryDoubleTwo.identity()).thenReturn(pairDoubleOne);
        when(userStoryDoubleThree.identity()).thenReturn(pairDoubleOne);
        when(userStoryDoubleFour.identity()).thenReturn(pairDoubleOne);
        when(userStoryDoubleFive.identity()).thenReturn(pairDoubleTwo);
        when(userStoryDoubleSix.identity()).thenReturn(pairDoubleTwo);

        when(userStoryDoubleOne.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStoryDoubleOne.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryDoubleTwo.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStoryDoubleTwo.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryDoubleThree.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStoryDoubleThree.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryDoubleFour.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStoryDoubleFour.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryDoubleFive.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStoryDoubleFive.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);
        when(userStoryDoubleSix.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStoryDoubleSix.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);

        when(pairDoubleOne.getProjectCode()).thenReturn(projectCodeDoubleOne);
        when(pairDoubleTwo.getProjectCode()).thenReturn(projectCodeDoubleTwo);

        when(userStoryDoubleFour.hasStatus(any())).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        userStoryRepositoryFake.deleteAll();
        userStoryRepositoryFake = null;
        userStoryList = null;
        projectCodeList = null;
        userStoryDoubleOne = null;
    }

    @Test
    void save_receivesUniqueProjectAndSavesSuccessfully_True() {
        UserStory userStory = mock(UserStory.class);

        UserStory result = userStoryRepositoryFake.save(userStory);

        assertEquals(userStory, result);
    }

    @Test
    void findAll_returnsListOfExistingUserStories() {
        Iterable<UserStory> expected = userStoryList;
        Iterable<UserStory> result = userStoryRepositoryFake.findAll();
        assertEquals(expected, result);
    }

    @Test
    void findByID() {
        //Arrange
        ProjectCode projectCodeDouble = mock(ProjectCode.class);
        UserStoryCode userStoryCodeDouble = mock(UserStoryCode.class);
        UserStoryID userStoryID = mock(UserStoryID.class);

        UserStory userStory = this.userStoryList.get(0);
        when(userStory.identity()).thenReturn(userStoryID);
        UserStoryID expected = userStoryID;

        //Act
        UserStoryID result = userStory.identity();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void findByID_ReceivesIDofExistingUserStory_returnsUserStory() {
        UserStory userStory = this.userStoryList.get(0);

        Optional<UserStory> expected = Optional.of(userStory);
        UserStoryID id = mock(UserStoryID.class);
        when(userStory.identity()).thenReturn(id);
        Optional<UserStory> result = userStoryRepositoryFake.findById(id);
        assertEquals(expected, result);
    }

    @Test
    void findByID_ReceivesIDofNonExistingUserStory_returnsEmptyOptional() {
        Optional<UserStory> expected = Optional.empty();
        UserStoryID id = mock(UserStoryID.class);
        UserStory userStory = this.userStoryList.get(0);
        Optional<UserStory> result = userStoryRepositoryFake.findById(id);
        assertEquals(expected, result);
    }

    @Test
    void existsByID_receivesExistingID_True() {
        UserStoryID id = mock(UserStoryID.class);
        UserStory userStory = this.userStoryList.get(0);
        when(userStory.identity()).thenReturn(id);
        boolean result = userStoryRepositoryFake.existsById(id);
        assertTrue(result);
    }

    @Test
    void existsByID_receivesNonExistingID_False() {
        UserStoryID id = mock(UserStoryID.class);
        boolean result = userStoryRepositoryFake.existsById(id);
        assertFalse(result);
    }

    @Test
    void findAll_UserStoryList() {
        ProjectCode projectCode = new ProjectCode("P1");
        UserStoryCode userStoryCode = new UserStoryCode("US001");
        UserStoryID userStoryID = new UserStoryID(projectCode, userStoryCode);
        UserStory userStory = mock(UserStory.class);
        userStoryRepositoryFake.save(userStory);
        when(userStory.identity()).thenReturn(userStoryID);

        userStoryRepositoryFake.deleteById(userStoryID);

        Iterable<UserStory> result = userStoryRepositoryFake.findAll();

        assertEquals(userStoryList, result);
    }

    @Test
    void findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot_DifferentStatus() {
        UserStory userStory = mock(UserStory.class);
        userStoryRepositoryFake.save(userStory);

        UserStoryID userStoryID = new UserStoryID(new ProjectCode("P1"), new UserStoryCode("US002"));
        when(userStory.identity()).thenReturn(userStoryID);


        when(userStory.getProjectCode()).thenReturn(new ProjectCode("P1"));
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);

        List<UserStory> test = new ArrayList<>();
        test.add(userStory);

        Iterable<UserStory> result = userStoryRepositoryFake.
                findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(new ProjectCode("P1"),
                        UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertEquals(test, result);
    }

    @Test
    void findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot_SameAsFirstStatus() {
        UserStory userStory = mock(UserStory.class);

        when(userStory.getProjectCode()).thenReturn(new ProjectCode("P1"));
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);

        List<UserStory> userStoryList = new ArrayList<>();

        Iterable<UserStory> result = userStoryRepositoryFake.
                findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(new ProjectCode("P1"),
                        UserStoryStatus.OPEN, UserStoryStatus.FINISHED);

        assertEquals(userStoryList, result);
    }

    @Test
    void findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot_SameAsSecondStatus() {
        UserStory userStory = mock(UserStory.class);

        when(userStory.getProjectCode()).thenReturn(new ProjectCode("P1"));
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.CANCELLED);

        List<UserStory> userStoryList = new ArrayList<>();

        Iterable<UserStory> result = userStoryRepositoryFake.
                findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(new ProjectCode("P1"),
                        UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertEquals(userStoryList, result);
    }

    @Test
    void findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot_DifferentProjectCode() {
        UserStory userStory = mock(UserStory.class);

        when(userStory.getProjectCode()).thenReturn(new ProjectCode("P2"));
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);

        List<UserStory> userStoryList = new ArrayList<>();

        Iterable<UserStory> result = userStoryRepositoryFake.
                findByProjectCodeAndUserStoryStatusNotAndUserStoryStatusNot(new ProjectCode("P1"),
                        UserStoryStatus.RUNNING, UserStoryStatus.CANCELLED);

        assertEquals(userStoryList, result);
    }

    @Test
    void deleteAll_deletesAllUserStories_EmptyList() {
        UserStory userStory = mock(UserStory.class);
        userStoryRepositoryFake.save(userStory);

        List<UserStory> expectedList = new ArrayList<>();
        Iterable<UserStory> expected = expectedList;

        userStoryRepositoryFake.deleteAll();
        Iterable<UserStory> result = userStoryRepositoryFake.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findAllById_ReturnsAllUserStories() {
        UserStory newUserStory = mock(UserStory.class);
        UserStoryID newUserStoryID = new UserStoryID(new ProjectCode("P1"), new UserStoryCode("US001"));
        when(newUserStory.identity()).thenReturn(newUserStoryID);

        List<UserStory> expected = new ArrayList<>();
        expected.add(newUserStory);

        List<UserStoryID> userStoryIDList = new ArrayList<>();
        userStoryIDList.add(newUserStoryID);

        userStoryRepositoryFake.save(newUserStory);
        Iterable<UserStory> result = userStoryRepositoryFake.findAllById(userStoryIDList);

        assertEquals(expected, result);
    }

    @Test
    void existsByUserStoryIdAndStatusNotLike_UserStoryExists() {
        UserStory userStory = mock(UserStory.class);
        UserStoryID userStoryID = mock(UserStoryID.class);
        userStoryRepositoryFake.save(userStory);

        when(userStory.identity()).thenReturn(userStoryID);
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);

        boolean result = userStoryRepositoryFake
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertTrue(result);
    }

    @Test
    void existsByUserStoryIdAndStatusNotLike_UserStoryDoesNotExist() {
        UserStory userStory = mock(UserStory.class);
        UserStoryID userStoryID = mock(UserStoryID.class);

        when(userStory.identity()).thenReturn(userStoryID);
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.OPEN);

        boolean result = userStoryRepositoryFake
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertFalse(result);
    }

    @Test
    void existsByUserStoryIdAndStatusNotLike_FinishedUserStory() {
        UserStory userStory = mock(UserStory.class);
        UserStoryID userStoryID = mock(UserStoryID.class);
        userStoryRepositoryFake.save(userStory);

        when(userStory.identity()).thenReturn(userStoryID);
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.FINISHED);

        boolean result = userStoryRepositoryFake
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertFalse(result);
    }

    @Test
    void existsByUserStoryIdAndStatusNotLike_CancelledUserStory() {
        UserStory userStory = mock(UserStory.class);
        UserStoryID userStoryID = mock(UserStoryID.class);
        userStoryRepositoryFake.save(userStory);

        when(userStory.identity()).thenReturn(userStoryID);
        when(userStory.getUserStoryStatus()).thenReturn(UserStoryStatus.CANCELLED);

        boolean result = userStoryRepositoryFake
                .existsByUserStoryIdAndUserStoryStatusNotAndUserStoryStatusNot
                        (userStoryID, UserStoryStatus.FINISHED, UserStoryStatus.CANCELLED);

        assertFalse(result);
    }
}