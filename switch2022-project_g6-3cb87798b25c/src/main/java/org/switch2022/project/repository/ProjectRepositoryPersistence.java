package org.switch2022.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.jpa.project.ProjectJPA;
import org.switch2022.project.datamodel.assembler.ProjectDomainDataAssembler;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.repository.interfaces.ProjectRepository;
import org.switch2022.project.repository.jpa.ProjectRepositoryJPA;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence implementation of the {@link ProjectRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link ProjectRepositoryJPA} interface to store projects.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class ProjectRepositoryPersistence implements ProjectRepository {

    private final ProjectRepositoryJPA projectRepositoryJPA;
    private final ProjectDomainDataAssembler projectDomainDataAssembler;

    /**
     * Instantiates an ProjectRepositoryPersistence.
     *
     * @param projectRepositoryJPA       the project JPA repository
     * @param projectDomainDataAssembler the project domain data assembler
     */
    @Autowired
    public ProjectRepositoryPersistence(ProjectRepositoryJPA projectRepositoryJPA,
                                        ProjectDomainDataAssembler projectDomainDataAssembler) {
        this.projectRepositoryJPA = projectRepositoryJPA;
        this.projectDomainDataAssembler = projectDomainDataAssembler;
    }

    /**
     * Saves a project.
     *
     * @param project the project to be saved
     * @return the saved project
     */
    public Project save(Project project) {
        ProjectJPA projectJPA = this.projectDomainDataAssembler.toData(project);
        ProjectJPA savedProjectJPA = this.projectRepositoryJPA.save(projectJPA);
        return this.projectDomainDataAssembler.toDomain(savedProjectJPA);
    }

    /**
     * Retrieves all projects in the JPA repository.
     *
     * @return An iterable collection of all projects in the repository
     */
    @Override
    public Iterable<Project> findAll() {
        List<ProjectJPA> projectJPAList = (List<ProjectJPA>) this.projectRepositoryJPA.findAll();

        return projectJPAList.stream()
                .map(projectDomainDataAssembler::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a project with a given project code.
     *
     * @param projectCode the project code to search for
     * @return An optional containing the project entity if found, an empty optional otherwise
     */
    @Override
    public Optional<Project> findById(ProjectCode projectCode) {
        return this.projectRepositoryJPA.findById(projectCode.getProjectCodeValue())
                .map(projectDomainDataAssembler::toDomain);
    }

    /**
     * Confirms if a project exists with the given project code.
     *
     * @param projectCode the project code to search for
     * @return true if the project exists, false otherwise
     */
    @Override
    public boolean existsById(ProjectCode projectCode) {
        return this.projectRepositoryJPA.existsById(projectCode.getProjectCodeValue());
    }

    /**
     * Deletes all projects from the JPA repository.
     */
    @Override
    public void deleteAll() {
        this.projectRepositoryJPA.deleteAll();
    }

    /**
     * Deletes a project with the given id, in this case, a project code.
     *
     * @param id the project code to search for
     */
    @Override
    public void deleteById(ProjectCode id) {
        this.projectRepositoryJPA.deleteById(id.getProjectCodeValue());
    }
}


