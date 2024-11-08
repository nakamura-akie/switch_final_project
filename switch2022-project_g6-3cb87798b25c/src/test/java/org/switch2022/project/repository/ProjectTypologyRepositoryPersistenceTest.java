package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.switch2022.project.datamodel.assembler.ProjectTypologyDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.projecttypology.ProjectTypologyJPA;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.repository.jpa.ProjectTypologyRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ProjectTypologyRepositoryPersistenceTest {

    private ProjectTypologyRepositoryPersistence projectTypologyRepositoryPersistence;
    private ProjectTypologyRepositoryJPA projectTypologyRepositoryJPA;
    ProjectTypologyJPA projectTypologyJPA;
    private ProjectTypologyDomainDataAssembler assembler;
    private ProjectTypology projectTypologyTimeAndMaterials;

    private ProjectTypologyName timeAndMaterials;

    @BeforeEach
    void init() {

        this.timeAndMaterials = new ProjectTypologyName("timeAndMaterials");

        this.projectTypologyJPA = mock(ProjectTypologyJPA.class);
        this.assembler = mock(ProjectTypologyDomainDataAssembler.class);
        this.projectTypologyRepositoryJPA = mock(ProjectTypologyRepositoryJPA.class);
        this.projectTypologyRepositoryPersistence = new ProjectTypologyRepositoryPersistence(projectTypologyRepositoryJPA, assembler);
        this.projectTypologyTimeAndMaterials = mock(ProjectTypology.class);


        when(projectTypologyTimeAndMaterials.identity()).thenReturn(timeAndMaterials);

        projectTypologyRepositoryPersistence.save(projectTypologyTimeAndMaterials);
        projectTypologyRepositoryJPA.save(projectTypologyJPA);


        when(assembler.toData(projectTypologyTimeAndMaterials)).thenReturn(projectTypologyJPA);
        when(projectTypologyRepositoryJPA.save(projectTypologyJPA)).thenReturn(projectTypologyJPA);
        when(assembler.toDomain(projectTypologyJPA)).thenReturn(projectTypologyTimeAndMaterials);

    }

    @AfterEach
    void TearDown() {

        this.projectTypologyTimeAndMaterials = null;
        this.projectTypologyRepositoryPersistence.deleteAll();
        this.timeAndMaterials = null;
        this.assembler = null;
        this.projectTypologyRepositoryJPA.deleteAll();
        this.projectTypologyJPA = null;

    }

    @Test
    void save_SuccessfullySavedTypology() {

        ProjectTypology result = projectTypologyRepositoryPersistence.save(projectTypologyTimeAndMaterials);
        assertEquals(projectTypologyTimeAndMaterials, result);

    }

    @Test
    void findAll_SuccessfullyReturnsAllTypologies_ReturnTrue() {

        List<ProjectTypologyJPA> projectTypologyJPAList = new ArrayList<>();
        projectTypologyJPAList.add(projectTypologyJPA);
        Iterable<ProjectTypologyJPA> list = projectTypologyJPAList;
        when(projectTypologyRepositoryJPA.findAll()).thenReturn(list);

        List<ProjectTypology> emptyList = new ArrayList<>();
        emptyList.add(projectTypologyTimeAndMaterials);
        Iterable<ProjectTypology> expected = emptyList;

        when(assembler.toDomain(projectTypologyJPA)).thenReturn(projectTypologyTimeAndMaterials);

        Iterable<ProjectTypology> result = projectTypologyRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findByID_SuccessfullyReturnsProjectTypology() {

        when(projectTypologyRepositoryJPA.findById("timeAndMaterials")).thenReturn(Optional.of(projectTypologyJPA));

        Optional<ProjectTypology> expected = Optional.of(projectTypologyTimeAndMaterials);
        Optional<ProjectTypology> result = projectTypologyRepositoryPersistence.findById(timeAndMaterials);

        assertEquals(expected, result);
    }

    @Test
    void findByID_ProjectTypologyDoesNotExist_ReturnsEmptyOptional() {

        ProjectTypologyName construction = new ProjectTypologyName("construction");
        Optional<ProjectTypology> emptyRepository = Optional.empty();

        Optional<ProjectTypology> projectTypology = projectTypologyRepositoryPersistence.findById(construction);

        assertEquals(emptyRepository, projectTypology);
    }

    @Test
    void existsByID_ProjectTypologyExists_ReturnTrue() {

        when(projectTypologyRepositoryJPA.existsById(timeAndMaterials.getProjectTypologyNameValue())).thenReturn(true);

        boolean result = projectTypologyRepositoryPersistence.existsById(timeAndMaterials);
        assertTrue(result);
    }

    @Test
    void existsByID_ProjectTypologyDoesntExist_ReturnFalse() {

        ProjectTypologyName projectTypologyName = new ProjectTypologyName("no");

        boolean result = projectTypologyRepositoryPersistence.existsById(projectTypologyName);
        assertFalse(result);
    }

    @Test
    void deleteAll_ProjectTypologyRepositoryEmpties_ReturnTrue() {

        List<ProjectTypology> emptyList = new ArrayList<>();
        Iterable<ProjectTypology> expected = emptyList;

        projectTypologyRepositoryPersistence.deleteAll();
        Iterable<ProjectTypology> result = projectTypologyRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void deleteAll_VerifyDeleteAll() {

        projectTypologyRepositoryPersistence.deleteAll();

        verify(projectTypologyRepositoryJPA).deleteAll();
    }

    @Test
    void deleteByID_VerifyDeleteByID() {

        ProjectTypologyName projectTypologyName = new ProjectTypologyName("Yes");

        projectTypologyRepositoryPersistence.deleteById(projectTypologyName);

        verify(projectTypologyRepositoryJPA).deleteById("Yes");
    }

}