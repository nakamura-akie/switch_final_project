package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.repository.interfaces.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link ProjectRepository} interface that uses an in-memory list to store
 * {@link Project} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class ProjectRepositoryFake implements ProjectRepository {

    private static final List<Project> projectList = new ArrayList<>();

    /**
     * Saves a project to the repository, if it is not already present.
     *
     * @param project the project to be saved
     * @return the saved project
     */
    @Override
    public Project save(Project project) {
        if (!projectList.contains(project)) {
            projectList.add(project);
        }
        return project;
    }

    /**
     * Retrieves all projects from the repository.
     *
     * @return An iterable collection of all projects
     */
    @Override
    public Iterable<Project> findAll() {
        return new ArrayList<>(projectList);
    }

    /**
     * Retrieves a project with the given project code.
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the project if found, an empty optional otherwise
     */
    @Override
    public Optional<Project> findById(ProjectCode projectCode) {
        for (Project project : projectList) {
            if (project.identity().equals(projectCode)) {
                return Optional.of(project);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a project exists with the given project code.
     *
     * @param projectCode the project code to search for
     * @return true if project exists, false otherwise
     */
    @Override
    public boolean existsById(ProjectCode projectCode) {
        for (Project project : projectList) {
            if (project.identity().equals(projectCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all projects from the repository.
     */
    @Override
    public void deleteAll() {
        projectList.clear();
    }

    /**
     * Deletes a project with a given project code.
     *
     * @param id the project code (id) to search for
     * @throws UnsupportedOperationException when method is called because this method is not yet supported by the
     *                                       Project Repository.
     */
    @Override
    public void deleteById(ProjectCode id) {
        throw new UnsupportedOperationException("Project Repository doesn't support the deleteByID method yet");
    }
}


