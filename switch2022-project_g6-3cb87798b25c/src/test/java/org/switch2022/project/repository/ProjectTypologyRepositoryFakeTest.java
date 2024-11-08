package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectTypologyRepositoryFakeTest {

    List<ProjectTypology> projectTypologyList;
    private ProjectTypology projectTypologyTimeAndMaterials;
    private ProjectTypology projectTypologyConstruction;
    private ProjectTypologyName timeAndMaterials;
    private ProjectTypologyName construction;

    private ProjectTypologyRepositoryFake projectTypologyRepositoryFake;

    @BeforeEach
    void init() {
        this.projectTypologyTimeAndMaterials = mock(ProjectTypology.class);
        this.projectTypologyConstruction = mock(ProjectTypology.class);

        this.timeAndMaterials = new ProjectTypologyName("Time and Materials");
        when(projectTypologyTimeAndMaterials.identity()).thenReturn(timeAndMaterials);
        this.construction = new ProjectTypologyName("Constructions");
        when(projectTypologyConstruction.identity()).thenReturn(construction);

        this.projectTypologyList = new ArrayList<>();

        projectTypologyList.add(projectTypologyTimeAndMaterials);

        projectTypologyRepositoryFake = new ProjectTypologyRepositoryFake();

        this.projectTypologyRepositoryFake.save(projectTypologyTimeAndMaterials);
    }

    @AfterEach
    void tearDown() {
        projectTypologyList = null;
        projectTypologyTimeAndMaterials = null;
        projectTypologyRepositoryFake.deleteAll();
        projectTypologyRepositoryFake = null;
    }

    @Test
    void findAll_returnsListOfExistingProjectTypologies_ReturnsEquals() {
        Iterable<ProjectTypology> expected = projectTypologyList;
        Iterable<ProjectTypology> result = projectTypologyRepositoryFake.findAll();
        assertEquals(expected, result);
    }

    @Test
    void findByID_ReceivesIDofExistingProjectTypology_returnsProjectTypology() {
        Optional<ProjectTypology> expected = Optional.of(projectTypologyTimeAndMaterials);

        Optional<ProjectTypology> result = projectTypologyRepositoryFake.findById(timeAndMaterials);
        assertEquals(expected,result);
    }
    @Test
    void findByID_ReceivesIDofNonExistingProjectTypology_returnsEmptyOptional() {
        Optional<ProjectTypology> expected = Optional.empty();

        ProjectTypologyName anotherPTName = new ProjectTypologyName("Another Name");

        Optional<ProjectTypology> result = projectTypologyRepositoryFake.findById(anotherPTName);
        assertEquals(expected,result);
    }

    @Test
    void existsByID_receivesExistingID_True() {
        boolean result = projectTypologyRepositoryFake.existsById(timeAndMaterials);
        assertTrue(result);
    }
    @Test
    void existsByID_receivesNonExistingID_False() {

        ProjectTypologyName projectTypologyName = new ProjectTypologyName("Another");
        boolean result = projectTypologyRepositoryFake.existsById(projectTypologyName);
        assertFalse(result);
    }


    @Test
    void deleteAll_RepositoryIsEmptyAfterDeleteAll_True() {

        projectTypologyRepositoryFake.deleteAll();

        Iterable<ProjectTypology> expected = new ArrayList<>();

        Iterable<ProjectTypology> result = projectTypologyRepositoryFake.findAll();

        assertEquals(expected, result);
    }

    @Test
    void deleteByID_CheckIfGivenIDIsDeletedByComparingLists() {
        ProjectTypologyName projectTypologyName = new ProjectTypologyName("new");
        ProjectTypology projectTypology = mock(ProjectTypology.class);
        projectTypologyRepositoryFake.save(projectTypology);
        when(projectTypology.identity()).thenReturn(projectTypologyName);


        projectTypologyRepositoryFake.deleteById(projectTypologyName);

        Iterable<ProjectTypology> result = projectTypologyRepositoryFake.findAll();

        assertEquals(projectTypologyList, result);
    }
}