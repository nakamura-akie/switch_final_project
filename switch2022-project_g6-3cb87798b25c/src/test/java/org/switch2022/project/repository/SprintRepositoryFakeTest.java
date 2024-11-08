package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintFactory;
import org.switch2022.project.domain.sprint.SprintFactoryImplementation;
import org.switch2022.project.domain.valueobject.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SprintRepositoryFakeTest {

    private Sprint sprintOne;
    private Sprint sprintTwo;
    private Sprint sprintThree;
    private SprintRepositoryFake sprintRepositoryFake;
    private List<Sprint> sprintList;

    @BeforeEach
    void init() {
        sprintOne = mock(Sprint.class);
        sprintTwo = mock(Sprint.class);
        sprintThree = mock(Sprint.class);

        sprintRepositoryFake = new SprintRepositoryFake();
        sprintList = new ArrayList<>();
        sprintList.add(sprintOne);

    }

    @AfterEach
    void tearDown() {

        sprintRepositoryFake.deleteAll();
        sprintList = null;
        sprintOne = null;
    }

    @Test
    void save_receivesUniqueProjectAndSavesSuccessfully_True() {
        Sprint sprintDouble = mock(Sprint.class);

        Sprint result = sprintRepositoryFake.save(sprintDouble);

        assertEquals(sprintDouble, result);
    }

    @Test
    void findAll_getsAllSprints_Equal() {
        Iterable<Sprint> expected = sprintList;
        sprintRepositoryFake.save(sprintOne);
        Iterable<Sprint> result = sprintRepositoryFake.findAll();
        assertEquals(expected, result);
    }

    @Test
    void findAll_getsEmptyList_Equal() {
        List<Sprint> emptySprintList = new ArrayList<>();
        Iterable<Sprint> result = sprintRepositoryFake.findAll();
        assertEquals(emptySprintList, result);
    }

    @Test
    void findByID_SprintExists_OptionalOfSprint() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(sprintID);
        Optional<Sprint> result = sprintRepositoryFake.findById(sprintID);
        Optional<Sprint> expected = Optional.of(sprintOne);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void findByID_SprintDoesNotExist_EmptyOptional() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        ProjectCode projectCodeTwo = new ProjectCode("P001");
        SprintCode sprintCodeTwo = new SprintCode("SPT002");
        SprintID secondSprintID = new SprintID(projectCodeTwo, sprintCodeTwo);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(secondSprintID);
        Optional<Sprint> result = sprintRepositoryFake.findById(sprintID);
        Optional<Sprint> expected = Optional.empty();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void findByID_EmptyRepository_EmptyOptional() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);

        Optional<Sprint> result = sprintRepositoryFake.findById(sprintID);
        Optional<Sprint> expected = Optional.empty();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void existsByID_SprintExits_True() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(sprintID);
        boolean result = sprintRepositoryFake.existsById(sprintID);

        assertTrue(result);
    }

    @Test
    void existsByID_SprintDoesNotExit_False() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        ProjectCode projectCodeTwo = new ProjectCode("P001");
        SprintCode sprintCodeTwo = new SprintCode("SPT002");
        SprintID secondSprintID = new SprintID(projectCodeTwo, sprintCodeTwo);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(secondSprintID);
        boolean result = sprintRepositoryFake.existsById(sprintID);

        assertFalse(result);
    }

    @Test
    void existsByID_EmptyRepository_False() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        when(sprintOne.identity()).thenReturn(sprintID);
        boolean result = sprintRepositoryFake.existsById(sprintID);

        assertFalse(result);
    }

    @Test
    void deleteAll() {
        Sprint sprintTwo = mock(Sprint.class);
        Sprint sprintThree = mock(Sprint.class);
        sprintRepositoryFake.save(sprintOne);
        sprintRepositoryFake.save(sprintTwo);
        sprintRepositoryFake.save(sprintThree);

        List<Sprint> sprintList = Arrays.asList(sprintOne, sprintTwo, sprintThree);
        sprintRepositoryFake.deleteAll();

        List<Sprint> emptySprintList = new ArrayList<>();
        Iterable<Sprint> result = sprintRepositoryFake.findAll();
        assertEquals(emptySprintList, result);
    }

    @Test
    void deleteByID_ThrowsException() {
        SprintID firstSprintID = mock(SprintID.class);

        Exception exception = assertThrows(UnsupportedOperationException.class, () ->
                sprintRepositoryFake.deleteById(firstSprintID));
        assertEquals("Sprint Repository doesn't support the deleteByID method yet", exception.getMessage());
    }

    @Test
    void findBySprintIdJPA_ProjectCodeAndStartDate_SprintFound_ReturnOptionalOfSprintTres() {
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.parse("2023-05-30");

        Sprint sprint = mock(Sprint.class);
        when(sprint.getProjectCode()).thenReturn(projectCode);
        when(sprint.getStartDate()).thenReturn(startDate);

        SprintRepositoryFake sprintRepositoryFake = new SprintRepositoryFake();
        sprintRepositoryFake.save(sprint);

        Optional<Sprint> result = sprintRepositoryFake.findBySprintIdJPA_ProjectCodeAndStartDate(projectCode,
                startDate);

        assertTrue(result.isPresent());
        assertEquals(sprint, result.get());
    }

    @Test
    void findBySprintIdJPA_ProjectCodeAndStartDate_SprintNotFound_ReturnEmptyOptional() {
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.parse("2023-05-30");

        Sprint sprint = mock(Sprint.class);
        when(sprint.getProjectCode()).thenReturn(projectCode);
        when(sprint.getStartDate()).thenReturn(startDate);

        SprintRepositoryFake sprintRepositoryFake = new SprintRepositoryFake();

        Optional<Sprint> result = sprintRepositoryFake.findBySprintIdJPA_ProjectCodeAndStartDate(projectCode,
                startDate);

        assertFalse(result.isPresent());
    }

    @Test
    void findBySprintIdJPA_ProjectCodeAndStartDate_SprintDoesNotExist_EmptyOptional() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        ProjectCode projectCodeTwo = new ProjectCode("P001");
        SprintCode sprintCodeTwo = new SprintCode("SPT002");
        SprintID secondSprintID = new SprintID(projectCodeTwo, sprintCodeTwo);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(secondSprintID);
        Optional<Sprint> result = sprintRepositoryFake.findById(sprintID);
        Optional<Sprint> expected = Optional.empty();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void findBySprintIdJPA_ProjectCodeAndStartDate_EmptyRepository_EmptyOptional() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);

        Optional<Sprint> result = sprintRepositoryFake.findById(sprintID);
        Optional<Sprint> expected = Optional.empty();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void findByProjectCode_ReturnsMatchingSprints() {
        ProjectCode projectCodeOne = new ProjectCode("P001");
        ProjectCode projectCodeTwo = new ProjectCode("P002");

        SprintID sprintIDOne = new SprintID(projectCodeOne, new SprintCode("SPT001"));
        SprintID sprintIDTwo = new SprintID(projectCodeOne, new SprintCode("SPT002"));
        SprintID sprintIDThree = new SprintID(projectCodeTwo, new SprintCode("SPT003"));

        SprintDuration sprintDuration = new SprintDuration(3);

        SprintFactory sprintFactory = new SprintFactoryImplementation();

        Sprint sprintOne = sprintFactory.createSprint(sprintIDOne, LocalDate.now(), sprintDuration,
                LocalDate.now().plusDays(21));
        Sprint sprintTwo = sprintFactory.createSprint(sprintIDTwo, LocalDate.now().plusDays(14), sprintDuration,
                LocalDate.now().plusDays(21));
        Sprint sprintThree = sprintFactory.createSprint(sprintIDThree, LocalDate.now().plusDays(28), sprintDuration,
                LocalDate.now().plusDays(21));

        SprintRepositoryFake sprintRepositoryFake = new SprintRepositoryFake();
        sprintRepositoryFake.save(sprintOne);
        sprintRepositoryFake.save(sprintTwo);
        sprintRepositoryFake.save(sprintThree);

        Iterable<Sprint> result = sprintRepositoryFake.findByProjectCode(projectCodeOne);

        List<Sprint> resultList = StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());

        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(sprintOne));
        assertTrue(resultList.contains(sprintTwo));
        assertFalse(resultList.contains(sprintThree));
    }

    @Test
    void findBySprintIdAndSprintStatusLike_FindsSprint() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(sprintID);
        when(sprintOne.getSprintStatus()).thenReturn(SprintStatus.OPENED);

        Optional<Sprint> result = sprintRepositoryFake
                .findBySprintIdAndSprintStatusLike(sprintID, SprintStatus.OPENED);

        assertEquals(Optional.of(sprintOne), result);
    }

    @Test
    void findBySprintIdAndSprintStatusLike_ClosedSprint() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        sprintRepositoryFake.save(sprintOne);

        when(sprintOne.identity()).thenReturn(sprintID);
        when(sprintOne.getSprintStatus()).thenReturn(SprintStatus.CLOSED);

        Optional<Sprint> result = sprintRepositoryFake
                .findBySprintIdAndSprintStatusLike(sprintID, SprintStatus.OPENED);

        assertEquals(Optional.empty(), result);
    }

    @Test
    void findBySprintIdAndSprintStatusLike_DoesNotFindSprint() {
        ProjectCode projectCode = new ProjectCode("P001");
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintID sprintID = new SprintID(projectCode, sprintCode);;

        when(sprintOne.identity()).thenReturn(sprintID);
        when(sprintOne.getSprintStatus()).thenReturn(SprintStatus.CLOSED);

        Optional<Sprint> result = sprintRepositoryFake
                .findBySprintIdAndSprintStatusLike(sprintID, SprintStatus.OPENED);

        assertEquals(Optional.empty(), result);
    }
    @Test
    void findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc_MostRecentSprint() {
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        sprintRepositoryFake.save(sprintOne);

        SprintCode sprintCodeTwo = new SprintCode("S2");
        SprintID sprintIDTwo = new SprintID(projectCode, sprintCodeTwo);
        sprintRepositoryFake.save(sprintTwo);

        SprintCode sprintCodeThree = new SprintCode("S3");
        SprintID sprintIDThree= new SprintID(projectCode, sprintCodeThree);
        sprintRepositoryFake.save(sprintThree);

        when(sprintOne.identity()).thenReturn(sprintID);
        when(sprintTwo.identity()).thenReturn(sprintIDTwo);
        when(sprintThree.identity()).thenReturn(sprintIDThree);

        when(sprintOne.getEndDate()).thenReturn(LocalDate.of(2023, 6, 1));
        when(sprintTwo.getEndDate()).thenReturn(LocalDate.of(2023, 6, 10));
        when(sprintThree.getEndDate()).thenReturn(LocalDate.of(2023, 6, 20));

        sprintRepositoryFake.save(sprintTwo);
        sprintRepositoryFake.save(sprintThree);

        Optional<Sprint> result = sprintRepositoryFake.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc
                (projectCode);

        assertTrue(result.isPresent());
        Sprint highestEndDateSprint = result.get();
        assertEquals(LocalDate.of(2023, 6, 20), highestEndDateSprint.getEndDate());
    }

    @Test
    void findOpenSprint_Exists_OptionOfSprint() {
        ProjectCode projectCode = new ProjectCode("P001");
        sprintRepositoryFake.save(sprintOne);
        sprintRepositoryFake.save(sprintTwo);

        when(sprintOne.getProjectCode()).thenReturn(projectCode);
        when(sprintOne.getSprintStatus()).thenReturn(SprintStatus.OPENED);

        Optional<Sprint> result = sprintRepositoryFake.findOpenSprint(projectCode);
        assertEquals(Optional.of(sprintOne), result);
    }

    @Test
    void findOpenSprint_DoesNotExist_EmptyOptional() {
        ProjectCode projectCode = new ProjectCode("P001");
        ProjectCode projectCodeTwo = new ProjectCode("P002");
        sprintRepositoryFake.save(sprintOne);
        sprintRepositoryFake.save(sprintTwo);

        when(sprintOne.getProjectCode()).thenReturn(projectCode);
        when(sprintOne.getSprintStatus()).thenReturn(SprintStatus.PLANNED);
        when(sprintTwo.getProjectCode()).thenReturn(projectCodeTwo);
        when(sprintTwo.getSprintStatus()).thenReturn(SprintStatus.CLOSED);

        Optional<Sprint> result = sprintRepositoryFake.findOpenSprint(projectCode);
        assertEquals(Optional.empty(), result);
    }
}