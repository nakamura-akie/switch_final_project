package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.repository.interfaces.ProjectTypologyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link ProjectTypologyRepository} interface that uses an in-memory list to store
 * {@link ProjectTypology} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class ProjectTypologyRepositoryFake implements ProjectTypologyRepository {

    private static final List<ProjectTypology> projectTypologyList = new ArrayList<>();

    /**
     * Saves a project typology to the repository, if it is not already present
     *
     * @param projectTypology the project typology to be saved
     * @return the saved project typology
     */
    @Override
    public ProjectTypology save(ProjectTypology projectTypology) {
        if (!projectTypologyList.contains(projectTypology)) {
            projectTypologyList.add(projectTypology);
        }
        return projectTypology;
    }

    /**
     * Retrieves all project typologies from the repository.
     *
     * @return An iterable collection of all project typologies
     */
    @Override
    public Iterable<ProjectTypology> findAll() {
        return new ArrayList<>(projectTypologyList);
    }

    /**
     * Retrieves a project typology with the given id, in this case, a project typology name.
     *
     * @param projectTypologyName the project typology name to search for
     * @return An optional containing the project typology if found, an empty optional otherwise
     */
    @Override
    public Optional<ProjectTypology> findById(ProjectTypologyName projectTypologyName) {
        for (ProjectTypology projectTypology : projectTypologyList) {
            if (projectTypology.identity().equals(projectTypologyName)) {
                return Optional.of(projectTypology);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a project typology exists with the given project typology name.
     *
     * @param projectTypologyName the name of the project typology to search for
     * @return true if the project typology exists, false otherwise
     */
    @Override
    public boolean existsById(ProjectTypologyName projectTypologyName) {
        for (ProjectTypology projectTypology : projectTypologyList) {
            if (projectTypology.identity().equals(projectTypologyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all project typologies in the repository
     */
    @Override
    public void deleteAll() {
        projectTypologyList.clear();
    }

    /**
     * Deletes the project typology with the given project typology name.
     * @param projectTypologyName the project typology name to search for
     */
    @Override
    public void deleteById(ProjectTypologyName projectTypologyName) {
        projectTypologyList.removeIf(profile -> profile.identity().equals(projectTypologyName));
    }

}

