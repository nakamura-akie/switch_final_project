package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.*;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryIdJPA;
import org.switch2022.project.datamodel.jpa.userstory.UserStoryJPA;
import org.switch2022.project.domain.userstory.UserStory;
import org.switch2022.project.domain.userstory.UserStoryFactory;
import org.switch2022.project.domain.valueobject.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserStoryDomainDataAssemblerTest {

    private UserStoryDomainDataAssembler userStoryDomainDataAssembler;
    private UserStory userStoryDouble;
    private UserStory userStoryTwoDouble;
    private UserStoryJPA userStoryJPA;
    private UserStoryID userStoryID;
    private UserStoryID userStoryIDTwo;

    @BeforeEach
    void init() {
        UserStoryFactory userStoryFactoryDouble = mock(UserStoryFactory.class);
        userStoryDomainDataAssembler = new UserStoryDomainDataAssembler(userStoryFactoryDouble);

        ProjectCode projectCode = new ProjectCode("P1");
        UserStoryCode userStoryCode = new UserStoryCode("US001");
        Description description = new Description("Some description");
        UserStoryStatus status = UserStoryStatus.OPEN;

        userStoryID = new UserStoryID(projectCode, userStoryCode);

        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        userStoryIDTwo = new UserStoryID(projectCode, userStoryCodeTwo);

        String projectCodeJPA = userStoryID.getProjectCode().getProjectCodeValue();
        String userStoryCodeJPA = userStoryID.getUserStoryCode().getUserStoryCodeValue();

        UserStoryIdJPA userStoryIdJPA = new UserStoryIdJPA(projectCodeJPA, userStoryCodeJPA);

        userStoryDouble = mock(UserStory.class);
        when(userStoryDouble.identity()).thenReturn(userStoryID);
        when(userStoryDouble.getDescription()).thenReturn(description);
        when(userStoryDouble.getUserStoryStatus()).thenReturn(status);

        userStoryTwoDouble = mock(UserStory.class);
        when(userStoryTwoDouble.identity()).thenReturn(userStoryIDTwo);
        when(userStoryTwoDouble.getDescription()).thenReturn(description);
        when(userStoryTwoDouble.getUserStoryStatus()).thenReturn(status);

        userStoryJPA = new UserStoryJPA(userStoryIdJPA, description.getDescriptionValue(), status.toString());

        when(userStoryFactoryDouble.createUserStory(userStoryID, description)).thenReturn(userStoryDouble);
        when(userStoryFactoryDouble.createUserStory(userStoryIDTwo, description)).thenReturn(userStoryTwoDouble);
    }

    @AfterEach
    void tearDown() {
        userStoryDomainDataAssembler = null;
        userStoryDouble = null;
        userStoryJPA = null;
        userStoryID = null;
    }

    @Test
    void constructor_NullUserStoryFactory_ThrowsException() {
        String expected = "User Story Factory cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new UserStoryDomainDataAssembler(null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void toData() {

        UserStoryJPA result = userStoryDomainDataAssembler.toData(userStoryDouble);

        assertEquals(userStoryJPA, result);
    }

    @Test
    void toDomain() {

        UserStory result = userStoryDomainDataAssembler.toDomain(userStoryJPA);
        verify(userStoryDouble, times(1)).changeStatus(UserStoryStatus.OPEN);
        assertEquals(userStoryDouble, result);
    }

    @Test
    void listOfIdsToData_correctlyConvertsList_True() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        UserStoryCode userStoryCodeTwo = new UserStoryCode("US002");
        UserStoryID userStoryIDTwo = new UserStoryID(projectCodeTwo, userStoryCodeTwo);

        List<UserStoryID> listOfIds = new ArrayList<>();
        listOfIds.add(userStoryID);
        listOfIds.add(userStoryIDTwo);

        List<UserStoryIdJPA> expected = new ArrayList<>();
        expected.add(new UserStoryIdJPA("P1", "US001"));
        expected.add(new UserStoryIdJPA("P1", "US002"));

        List<UserStoryIdJPA> result = userStoryDomainDataAssembler.listOfIdsToData(listOfIds);

        assertEquals(expected, result);
    }

    @Test
    void userStoryJPAListToDomain_correctlyConvertsList_True() {
        UserStoryIdJPA userStoryIDJPATwo = new UserStoryIdJPA("P1", "US002");

        UserStoryJPA userStoryJPATwo = new UserStoryJPA(userStoryIDJPATwo, "Some description", "OPEN");

        List<UserStoryJPA> listOfUserStoryJPAs = new ArrayList<>();
        listOfUserStoryJPAs.add(userStoryJPA);
        listOfUserStoryJPAs.add(userStoryJPATwo);

        List<UserStory> expected = new ArrayList<>();
        expected.add(userStoryDouble);
        expected.add(userStoryTwoDouble);

        Iterable<UserStory> result = userStoryDomainDataAssembler.userStoryJPAListToDomain(listOfUserStoryJPAs);

        assertEquals(expected, result);
    }

}