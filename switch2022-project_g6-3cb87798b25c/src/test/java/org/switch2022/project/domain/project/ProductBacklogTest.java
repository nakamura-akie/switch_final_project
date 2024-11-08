package org.switch2022.project.domain.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryID;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class
ProductBacklogTest {

    private ProductBacklog productBacklogOne;
    private ProductBacklog productBacklogTwo;
    private ProductBacklog productBacklogThree;
    private ProjectCode projectCodeDoubleOne;
    private ProjectCode projectCodeDoubleTwo;
    private LinkedList<UserStoryID> userStoryIDDoubleList;

    @BeforeEach
    void init() {
        this.projectCodeDoubleOne = mock(ProjectCode.class);
        this.projectCodeDoubleTwo = mock(ProjectCode.class);

        this.productBacklogTwo = new ProductBacklog(projectCodeDoubleTwo);
        this.productBacklogThree = new ProductBacklog(projectCodeDoubleOne);

        UserStoryID userStoryIdDoubleOne = mock(UserStoryID.class);
        UserStoryID userStoryIdDoubleTwo = mock(UserStoryID.class);
        UserStoryID userStoryIdDoubleThree = mock(UserStoryID.class);
        UserStoryID userStoryIdDoubleFour = mock(UserStoryID.class);
        this.userStoryIDDoubleList = new LinkedList<>(List.of(userStoryIdDoubleOne, userStoryIdDoubleTwo,
                userStoryIdDoubleThree, userStoryIdDoubleFour));

        this.productBacklogOne = new ProductBacklog(projectCodeDoubleOne, userStoryIDDoubleList);

        this.productBacklogTwo.addUserStory(userStoryIdDoubleOne);

        this.productBacklogThree.addUserStory(userStoryIdDoubleOne);
    }

    @AfterEach
    void tearDown() {
        this.projectCodeDoubleOne = null;
        this.projectCodeDoubleTwo = null;
        this.productBacklogOne = null;
        this.productBacklogTwo = null;
        this.productBacklogThree = null;
        this.userStoryIDDoubleList = null;
    }

    @Test
    void constructor_NullProjectCode_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProductBacklog(null));
        assertEquals("Project Code can not be null", exception.getMessage());
    }

    @Test
    void constructorWithTwoParameters_NullProjectCode_ThrowsException() {
        LinkedList<UserStoryID> list = new LinkedList<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProductBacklog(null, list));
        assertEquals("Project Code can not be null", exception.getMessage());
    }

    @Test
    void constructorWithTwoParameters_NullProductBacklog_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProductBacklog(mock(ProjectCode.class), null));

        assertEquals("Product Backlog can not be null", exception.getMessage());
    }

    @Test
    void addUserStory_UserStoryAdded_ReturnTrue() {
        UserStoryID userStoryIDTest = mock(UserStoryID.class);

        boolean result = productBacklogOne.addUserStory(userStoryIDTest);

        assertTrue(result);
    }

    @Test
    void addUserStory_UserStoryAlreadyExists_ReturnFalse() {
        UserStoryID userStoryIDDouble = userStoryIDDoubleList.get(0);

        boolean result = productBacklogOne.addUserStory(userStoryIDDouble);

        assertFalse(result);
    }

    @Test
    void removeUserStory_ExistingUserStoryID_ShorterUserStoryList() {
        UserStoryID userStoryIDToRemove = userStoryIDDoubleList.get(3);

        List<UserStoryID> expected = List.of(userStoryIDDoubleList.get(0), userStoryIDDoubleList.get(1),userStoryIDDoubleList.get(2));

        productBacklogOne.removeUserStory(userStoryIDToRemove);
        List<UserStoryID> result = productBacklogOne.getUserStorylist();

        assertEquals(expected, result);
    }

    @Test
    void removeUserStory_NonexistentUserStoryID_CompleteUserStoryList() {
        UserStoryID userStoryIDToRemove = mock(UserStoryID.class);

        List<UserStoryID> expected = List.of(userStoryIDDoubleList.get(0), userStoryIDDoubleList.get(1),
                userStoryIDDoubleList.get(2), userStoryIDDoubleList.get(3));

        productBacklogOne.removeUserStory(userStoryIDToRemove);
        List<UserStoryID> result = productBacklogOne.getUserStorylist();

        assertEquals(expected, result);
    }

    @Test
    void getUserStoryList_EmptyUserStoryList_ReturnEmptyList() {
        List<UserStoryID> expected = Collections.emptyList();

        ProductBacklog productBacklog = new ProductBacklog(mock(ProjectCode.class));

        List<UserStoryID> result = productBacklog.getUserStorylist();

        assertEquals(expected, result);
    }

    @Test
    void getUserStoryList_UserStoryListWithUserStories_UserStoryList() {
        List<UserStoryID> expected = this.userStoryIDDoubleList;

        List<UserStoryID> result = this.productBacklogOne.getUserStorylist();

        assertEquals(expected, result);
    }

    @Test
    void getUserStoryPriorityList_DifferentUserStoryList_ReturnNotEquals() {
        UserStoryID userStoryIDDoubleOne = userStoryIDDoubleList.get(0);
        UserStoryID userStoryIDDoubleTwo = userStoryIDDoubleList.get(1);

        List<UserStoryID> expected = Arrays.asList(userStoryIDDoubleOne, userStoryIDDoubleTwo);

        List<UserStoryID> result = productBacklogTwo.getUserStorylist();

        assertNotEquals(expected, result);
    }

    @Test
    void identity_HasSameIdentity_ReturnEquals() {
        ProjectCode result = productBacklogOne.identity();

        assertEquals(projectCodeDoubleOne, result);
    }

    @Test
    void identity_DoesntHaveSameIdentity_ReturnNotEquals() {
        ProjectCode result = productBacklogOne.identity();

        assertNotEquals(projectCodeDoubleTwo, result);
    }


    @Test
    void sameAs_ProductBacklogAlreadyExists_ReturnNotEquals() {
        boolean test = productBacklogOne.sameAs(productBacklogTwo);

        assertFalse(test);
    }

    @Test
    void sameAs_ProductBacklogAlreadyExists_Equals() {
        boolean test = productBacklogTwo.sameAs(productBacklogThree);

        assertTrue(test);
    }

    @Test
    void sameAs_ObjectsOfDifferentClasses_ReturnFalse() {
        String test = "yes";
        boolean result = productBacklogOne.sameAs(test);

        assertFalse(result);
    }

    @Test
    void equals_SameObject_ReturnEquals() {
        assertEquals(productBacklogOne, productBacklogOne);
    }

    @Test
    void equals_ProductBacklogAlreadyExists_Equals() {
        assertEquals(productBacklogOne, productBacklogThree);
    }

    @Test
    void equals_ProductBacklogDoesntExist_NotEquals() {
        assertNotEquals(productBacklogOne, productBacklogTwo);
    }

    @Test
    void equals_ObjectIsNull_NotEquals() {
        ProductBacklog result = null;
        assertNotEquals(productBacklogOne, result);
    }

    @Test
    void equals_DifferentClass_NotEquals() {
        String result = "not a product backlog";
        assertNotEquals(productBacklogOne, result);
    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(productBacklogOne);
        int result = productBacklogOne.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void addUserStory() {
        UserStoryID userStoryID = mock(UserStoryID.class);
        List<UserStoryID> expected = Arrays.asList(
                userStoryIDDoubleList.get(0),
                userStoryIDDoubleList.get(1),
                userStoryIDDoubleList.get(2),
                userStoryIDDoubleList.get(3),
                userStoryID);

        productBacklogOne.addUserStory(userStoryID);

        assertEquals(expected, productBacklogOne.getUserStorylist());
    }

    @Test
    void removeUserStory() {
        UserStoryID userStoryIdToRemove = userStoryIDDoubleList.get(0);
        List<UserStoryID> expected = Arrays.asList(
                userStoryIDDoubleList.get(1),
                userStoryIDDoubleList.get(2),
                userStoryIDDoubleList.get(3));

        productBacklogOne.removeUserStory(userStoryIdToRemove);

        assertEquals(expected, productBacklogOne.getUserStorylist());
    }
}