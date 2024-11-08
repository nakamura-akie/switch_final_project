package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.SprintIdJPA;
import org.switch2022.project.datamodel.SprintJPA;
import org.switch2022.project.datamodel.assembler.SprintDomainDataAssembler;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;
import org.switch2022.project.repository.jpa.SprintRepositoryJPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SprintRepositoryPersistenceTest {

    static private SprintRepositoryPersistence sprintRepositoryPersistence;
    static private Sprint sprintOneDouble;
    static private Sprint sprintTwoDouble;
    static private SprintJPA sprintJPAOneDouble;
    static private SprintJPA sprintJPATwoDouble;
    static private SprintDomainDataAssembler assembler;
    static private SprintRepositoryJPA jpaRepositoryDouble;
    static private ProjectCode projectCodeOne;
    static private SprintCode sprintCodeOne;
    static private SprintID sprintIDOne;
    static private SprintIdJPA sprintIdJPA;

    @BeforeAll
    static void setup() {
        jpaRepositoryDouble = mock(SprintRepositoryJPA.class);
        assembler = mock(SprintDomainDataAssembler.class);
        sprintRepositoryPersistence = new SprintRepositoryPersistence(jpaRepositoryDouble, assembler);

        sprintOneDouble = mock(Sprint.class);
        sprintTwoDouble = mock(Sprint.class);
        sprintJPAOneDouble = mock(SprintJPA.class);
        sprintJPATwoDouble = mock(SprintJPA.class);

        projectCodeOne = new ProjectCode("P1");
        sprintCodeOne = new SprintCode("S1");

        sprintIDOne = new SprintID(projectCodeOne, sprintCodeOne);
        sprintIdJPA = new SprintIdJPA("P1", "S1");

        when(assembler.toData(sprintOneDouble)).thenReturn(sprintJPAOneDouble);
        when(assembler.toDomain(sprintJPAOneDouble)).thenReturn(sprintOneDouble);

        when(assembler.toData(sprintTwoDouble)).thenReturn(sprintJPATwoDouble);
        when(assembler.toDomain(sprintJPATwoDouble)).thenReturn(sprintTwoDouble);
    }

    @AfterAll
    static void tearDown() {
        sprintRepositoryPersistence.deleteAll();
    }

    @Test
    void SprintRepositoryPersistence_NullJPARepository() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SprintRepositoryPersistence(null, assembler));
        assertEquals("Cannot have null fields.",
                exception.getMessage());
    }

    @Test
    void SprintRepositoryPersistence_NullAssembler() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new SprintRepositoryPersistence(jpaRepositoryDouble, null));
        assertEquals("Cannot have null fields.",
                exception.getMessage());
    }

    @Test
    void save_SavesSprint_Equals() {
        when(jpaRepositoryDouble.save(sprintJPAOneDouble)).thenReturn(sprintJPAOneDouble);
        when(assembler.toDomain(sprintJPAOneDouble)).thenReturn(sprintOneDouble);

        Sprint result = sprintRepositoryPersistence.save(sprintOneDouble);

        assertEquals(sprintOneDouble, result);
    }

    @Test
    void findAll_ReturnsAll_True() {
        List<Sprint> sprintList = new ArrayList<>();
        sprintList.add(sprintOneDouble);
        sprintList.add(sprintTwoDouble);

        Iterable<Sprint> expected = sprintList;

        sprintRepositoryPersistence.save(sprintOneDouble);
        sprintRepositoryPersistence.save(sprintTwoDouble);

        List<SprintJPA> sprintJPAList = new ArrayList<>();
        sprintJPAList.add(sprintJPAOneDouble);
        sprintJPAList.add(sprintJPATwoDouble);

        when(jpaRepositoryDouble.findAll()).thenReturn(sprintJPAList);

        Iterable<Sprint> result = sprintRepositoryPersistence.findAll();

        List<Sprint> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(((List<Sprint>) expected).size(), resultList.size());
    }

    @Test
    void findById_FindsSprint_Equals() {
        when(jpaRepositoryDouble.findById(sprintIdJPA)).thenReturn(Optional.of(sprintJPAOneDouble));

        Optional<Sprint> expected = Optional.of(sprintOneDouble);

        Optional<Sprint> result = sprintRepositoryPersistence.findById(sprintIDOne);

        assertEquals(expected, result);
    }

    @Test
    void findById_DoesNotFindsSprint_EmptyOptional() {
        when(jpaRepositoryDouble.findById(sprintIdJPA)).thenReturn(Optional.empty());

        Optional<Sprint> expected = Optional.empty();

        Optional<Sprint> result = sprintRepositoryPersistence.findById(sprintIDOne);

        assertEquals(expected, result);
    }

    @Test
    void existsById_SprintExists_True() {
        when(jpaRepositoryDouble.existsById(sprintIdJPA)).thenReturn(true);

        boolean result = sprintRepositoryPersistence.existsById(sprintIDOne);

        assertTrue(result);
    }

    @Test
    void findByProjectCode() {
        ProjectCode projectCode = new ProjectCode("P1");
        List<SprintJPA> sprintJpaList = new ArrayList<>();
        sprintJpaList.add(sprintJPAOneDouble);
        sprintJpaList.add(sprintJPATwoDouble);

        when(jpaRepositoryDouble.findBySprintIdJPA_ProjectCode(projectCode.getProjectCodeValue())).thenReturn(sprintJpaList);

        List<Sprint> expectedSprints = new ArrayList<>();
        expectedSprints.add(sprintOneDouble);
        expectedSprints.add(sprintTwoDouble);

        when(assembler.toDomain(any(SprintJPA.class))).thenReturn(expectedSprints.get(0), expectedSprints.get(1));

        Iterable<Sprint> result = sprintRepositoryPersistence.findByProjectCode(projectCode);

        assertEquals(expectedSprints, result);
    }

    @Test
    void findBySprintIdJPA_ProjectCodeAndStartDate() {
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.of(2023, 5, 5);

        SprintJPA sprintJPADouble = mock(SprintJPA.class);
        Optional<SprintJPA> optionalSprintJPADouble = Optional.of(sprintJPADouble);

        when(jpaRepositoryDouble.findBySprintIdJPA_ProjectCodeAndStartDate(projectCode.getProjectCodeValue(), startDate.toString())).thenReturn(optionalSprintJPADouble);

        Sprint sprintDouble = mock(Sprint.class);
        Optional<Sprint> optionalSprintDouble = Optional.of(sprintDouble);

        when(assembler.toDomain(sprintJPADouble)).thenReturn(sprintDouble);

        Optional<Sprint> result = sprintRepositoryPersistence.findBySprintIdJPA_ProjectCodeAndStartDate(projectCode, startDate);

        assertEquals(optionalSprintDouble, result);
    }

    @Test
    void findBySprintIdJPA_ProjectCodeAndStartDate_ReturnsEmpty_WhenNotFound() {
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.of(2023, 5, 5);

        when(jpaRepositoryDouble.findBySprintIdJPA_ProjectCodeAndStartDate(eq(projectCode.getProjectCodeValue()), eq(startDate.toString())))
                .thenReturn(Optional.empty());

        Optional<Sprint> result = sprintRepositoryPersistence.findBySprintIdJPA_ProjectCodeAndStartDate(projectCode, startDate);

        verify(jpaRepositoryDouble).findBySprintIdJPA_ProjectCodeAndStartDate(eq(projectCode.getProjectCodeValue()), eq(startDate.toString()));

        assertFalse(result.isPresent());
    }

    @Test
    void deleteAll() {
        sprintRepositoryPersistence.deleteAll();
        verify(jpaRepositoryDouble).deleteAll();
    }

    @Test
    void deleteById() {
        sprintRepositoryPersistence.deleteById(sprintIDOne);
        verify(jpaRepositoryDouble).deleteById(sprintIdJPA);
    }

    @Test
    void findBySprintIdAndSprintStatusLike_FindsSprint() {
        SprintIdJPA sprintID = new SprintIdJPA(
                new ProjectCode("P1").getProjectCodeValue(),
                new SprintCode("S1").getSprintCodeValue()
        );

        String sprintStatus = SprintStatus.OPENED.name();

        SprintJPA sprintJPADouble = mock(SprintJPA.class);
        Optional<SprintJPA> optionalSprintJPADouble = Optional.of(sprintJPADouble);
        Sprint sprint = mock(Sprint.class);
        Optional<Sprint> optionalSprint = Optional.of(sprint);

        when(jpaRepositoryDouble.findBySprintIdJPAAndSprintStatusLike(sprintID, sprintStatus))
                .thenReturn(optionalSprintJPADouble);
        when(assembler.toDomain(sprintJPADouble)).thenReturn(sprint);

        SprintID sprintToSearch = new SprintID(
                new ProjectCode("P1"),
                new SprintCode("S1")
        );

        Optional<Sprint> result = sprintRepositoryPersistence
                .findBySprintIdAndSprintStatusLike(sprintToSearch, SprintStatus.OPENED);

        assertEquals(optionalSprint, result);
    }

    @Test
    void findBySprintIdAndSprintStatusLike_DoesNotFindSprint() {
        SprintIdJPA sprintID = new SprintIdJPA(
                new ProjectCode("P1").getProjectCodeValue(),
                new SprintCode("S1").getSprintCodeValue()
        );

        String sprintStatus = SprintStatus.OPENED.name();

        SprintJPA sprintJPADouble = mock(SprintJPA.class);
        Optional<SprintJPA> optionalSprintJPADouble = Optional.of(sprintJPADouble);
        Sprint sprint = mock(Sprint.class);
        Optional<Sprint> optionalSprint = Optional.empty();

        when(jpaRepositoryDouble.findBySprintIdJPAAndSprintStatusLike(sprintID, sprintStatus))
                .thenReturn(optionalSprintJPADouble);
        when(assembler.toDomain(sprintJPADouble)).thenReturn(sprint);

        SprintID sprintToSearch = new SprintID(
                new ProjectCode("P1"),
                new SprintCode("S1")
        );

        Optional<Sprint> result = sprintRepositoryPersistence
                .findBySprintIdAndSprintStatusLike(sprintToSearch, SprintStatus.CLOSED);

        assertEquals(optionalSprint, result);
    }

    @Test
    void findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc() {
        ProjectCode projectCode = new ProjectCode("P1");

        SprintJPA sprintJPADouble = mock(SprintJPA.class);
        Optional<SprintJPA> optionalSprintJPADouble = Optional.of(sprintJPADouble);

        when(jpaRepositoryDouble.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(projectCode.getProjectCodeValue())).thenReturn(optionalSprintJPADouble);

        Sprint sprintDouble = mock(Sprint.class);
        Optional<Sprint> optionalSprintDouble = Optional.of(sprintDouble);

        when(assembler.toDomain(sprintJPADouble)).thenReturn(sprintDouble);

        Optional<Sprint> result = sprintRepositoryPersistence.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(projectCode);

        assertTrue(result.isPresent());
        assertEquals(optionalSprintDouble, result);
    }
    @Test
    void findFirstBySprintIdJPA_EmptyList() {
        ProjectCode projectCode = new ProjectCode("P1");

        Optional<SprintJPA> optionalSprintJPADouble = Optional.empty();

        when(jpaRepositoryDouble.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(projectCode.getProjectCodeValue()))
                .thenReturn(optionalSprintJPADouble);

        Optional<Sprint> result = sprintRepositoryPersistence.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(projectCode);

        assertFalse(result.isPresent());
    }

    @Test
    void findOpenSprint_SprintExists_ReturnsSprint() {
        when(jpaRepositoryDouble.findBySprintIdJPA_ProjectCodeAndSprintStatus("P1", "OPENED"))
                .thenReturn(Optional.of(sprintJPAOneDouble));

        when(assembler.toDomain(sprintJPAOneDouble)).thenReturn(sprintOneDouble);

        ProjectCode projectCode = new ProjectCode("P1");

        Optional<Sprint> result = sprintRepositoryPersistence.findOpenSprint(projectCode);

        assertEquals(Optional.of(sprintOneDouble), result);
    }


    @Test
    void findOpenSprint_NoOpenSprints_ReturnsEmptyOptional() {
        when(jpaRepositoryDouble.findBySprintIdJPA_ProjectCodeAndSprintStatus("P1", "OPENED"))
                .thenReturn(Optional.empty());

        ProjectCode projectCode = new ProjectCode("P1");

        Optional<Sprint> result = sprintRepositoryPersistence.findOpenSprint(projectCode);

        assertEquals(Optional.empty(), result);
    }

}
