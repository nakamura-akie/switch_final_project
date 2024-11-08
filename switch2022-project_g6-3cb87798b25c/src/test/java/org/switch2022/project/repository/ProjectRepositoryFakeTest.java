package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectRepositoryFakeTest {

    private List<Project> projectList;
    private Project project;
    private Project projectTwo;
    private ProjectRepositoryFake projectRepositoryFake;

    @BeforeEach
    void init() {
        project = mock(Project.class);
        projectTwo = mock(Project.class);
        projectList = new ArrayList<>();
        projectList.add(projectTwo);
        projectRepositoryFake = new ProjectRepositoryFake();

        this.projectRepositoryFake.save(projectTwo);
    }

    @AfterEach
    void tearDown() {
        projectList = null;
        project = null;
        projectRepositoryFake.deleteAll();
        projectRepositoryFake = null;
    }

    @Test
    void save_receivesUniqueProjectAndSavesSuccessfully_True() {
        Project expected = this.project;

        Project result = projectRepositoryFake.save(project);

        assertEquals(expected, result);
    }

    @Test
    void findAll_returnsListOfExistingProjects() {
        Iterable<Project> expected = projectList;
        Iterable<Project> result = projectRepositoryFake.findAll();
        assertEquals(expected, result);
    }

    @Test
    void findByID_ReceivesIDofExistingProject_returnsProject() {
        Optional<Project> expected = Optional.of(projectTwo);
        ProjectCode projectCode = new ProjectCode("Code");
        when(projectTwo.identity()).thenReturn(projectCode);
        Optional<Project> result = projectRepositoryFake.findById(projectCode);
        assertEquals(expected, result);
    }

    @Test
    void findByID_ReceivesIDofNonExistingProject_returnsEmptyOptional() {
        Optional<Project> expected = Optional.empty();
        ProjectCode projectCode = new ProjectCode("Code");
        ProjectCode anotherProjectCode = new ProjectCode("Another code");
        when(projectTwo.identity()).thenReturn(projectCode);
        Optional<Project> result = projectRepositoryFake.findById(anotherProjectCode);
        assertEquals(expected, result);
    }

    @Test
    void existsByID_receivesExistingID_True() {
        ProjectCode projectCode = new ProjectCode("Code");
        when(projectTwo.identity()).thenReturn(projectCode);
        boolean result = projectRepositoryFake.existsById(projectCode);
        assertTrue(result);
    }

    @Test
    void existsByID_receivesNonExistingID_False() {
        ProjectCode projectCode = new ProjectCode("Code");
        ProjectCode projectCodeTwo = new ProjectCode("code two");
        when(projectTwo.identity()).thenReturn(projectCodeTwo);
        boolean result = projectRepositoryFake.existsById(projectCode);
        assertFalse(result);
    }

    @Test
    void deleteByID_ThrowsException() {
        ProjectCode projectCode = mock(ProjectCode.class);

        Exception exception = assertThrows(UnsupportedOperationException.class, () ->
                projectRepositoryFake.deleteById(projectCode));
        assertEquals("Project Repository doesn't support the deleteByID method yet", exception.getMessage());
    }
}