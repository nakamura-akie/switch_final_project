package org.switch2022.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.project.ProjectJPA;
import org.switch2022.project.datamodel.assembler.ProjectDomainDataAssembler;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.repository.jpa.ProjectRepositoryJPA;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectRepositoryPersistenceTest {

    private static Iterable<ProjectCode> projectCodeList;
    private static List<Project> projectDoubleList;
    private static ProjectRepositoryPersistence projectRepositoryPersistence;
    private static ProjectDomainDataAssembler projectDomainDataAssembler;
    private static ProjectJPA projectJPADouble;
    private static ProjectRepositoryJPA projectRepositoryJPADouble;

    @BeforeEach
    void init() {
        projectJPADouble = mock(ProjectJPA.class);
        projectRepositoryJPADouble = mock(ProjectRepositoryJPA.class);
        projectDomainDataAssembler = mock(ProjectDomainDataAssembler.class);
        projectRepositoryPersistence = new ProjectRepositoryPersistence(projectRepositoryJPADouble,
                projectDomainDataAssembler);

        ProjectCode projectCodeOne = mock(ProjectCode.class);
        ProjectCode projectCodeTwo = mock(ProjectCode.class);
        ProjectCode projectCodeThree = mock(ProjectCode.class);
        projectCodeList = List.of(projectCodeOne, projectCodeTwo, projectCodeThree);

        ProjectJPA projectJPADoubleOne = mock(ProjectJPA.class);
        ProjectJPA projectJPADoubleTwo = mock(ProjectJPA.class);
        ProjectJPA projectJPADoubleThree = mock(ProjectJPA.class);
        List<ProjectJPA> projectJPADoubleList = List.of(projectJPADoubleOne, projectJPADoubleTwo,
                projectJPADoubleThree);

        Project projectDoubleOne = mock(Project.class);
        Project projectDoubleTwo = mock(Project.class);
        Project projectDoubleThree = mock(Project.class);
        projectDoubleList = List.of(projectDoubleOne, projectDoubleTwo, projectDoubleThree);

        when(projectRepositoryJPADouble.findAll()).thenReturn(projectJPADoubleList);

        when(projectRepositoryJPADouble.findById(any())).thenReturn(Optional.of(projectJPADoubleOne));

        when(projectRepositoryJPADouble.findAllById(any())).thenReturn(projectJPADoubleList);

        when(projectCodeOne.getProjectCodeValue()).thenReturn("P001");
        when(projectRepositoryJPADouble.existsById("P001")).thenReturn(true);

        when(projectDomainDataAssembler.toDomain(projectJPADoubleOne)).thenReturn(projectDoubleOne);
        when(projectDomainDataAssembler.toDomain(projectJPADoubleTwo)).thenReturn(projectDoubleTwo);
        when(projectDomainDataAssembler.toDomain(projectJPADoubleThree)).thenReturn(projectDoubleThree);
    }

    @Test
    void save() {

        Project projectDouble = mock(Project.class);
        when(projectDomainDataAssembler.toData(projectDouble)).thenReturn(projectJPADouble);
        when(projectRepositoryJPADouble.save(projectJPADouble)).thenReturn(projectJPADouble);
        when(projectDomainDataAssembler.toDomain(projectJPADouble)).thenReturn(projectDouble);
        Project expected = projectDouble;
        Project result = projectRepositoryPersistence.save(projectDouble);

        assertEquals(expected, result);
    }

    @Test
    void findAll() {
        List<Project> expected = projectDoubleList;
        Iterable<Project> result = projectRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById() {
        ProjectCode projectCode = ((List<ProjectCode>) projectCodeList).get(0);

        Optional<Project> expected = Optional.of(projectDoubleList.get(0));
        Optional<Project> result = projectRepositoryPersistence.findById(projectCode);

        assertEquals(expected, result);
    }

    @Test
    void existsById() {
        ProjectCode existentProjectCode = ((List<ProjectCode>) projectCodeList).get(0);

        boolean result = projectRepositoryPersistence.existsById(existentProjectCode);

        assertTrue(result);
    }

    @Test
    void deleteAll() {
        projectRepositoryPersistence.deleteAll();
        verify(projectRepositoryJPADouble).deleteAll();
    }

    @Test
    void deleteById() {
        ProjectCode projectCode = mock(ProjectCode.class);
        projectRepositoryPersistence.deleteById(projectCode);
        verify(projectRepositoryJPADouble).deleteById(any());
    }
}