package org.switch2022.project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.assembler.ProjectTypologyDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.projecttypology.ProjectTypologyJPA;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.repository.interfaces.ProjectTypologyRepository;
import org.switch2022.project.repository.jpa.ProjectTypologyRepositoryJPA;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence implementation of the {@link ProjectTypologyRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link ProjectTypologyRepositoryJPA} interface to store project typologies.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class ProjectTypologyRepositoryPersistence implements ProjectTypologyRepository {

    private final ProjectTypologyRepositoryJPA projectTypologyRepositoryJPA;
    private final ProjectTypologyDomainDataAssembler projectTypologyDomainDataAssembler;

    /**
     * Instantiates a ProjectTypologyRepositoryPersistence.
     *
     * @param projectTypologyRepositoryJPA the project typology JPA repository
     * @param projectTypologyDomainDataAssembler the project typology domain data assembler
     */
    @Autowired
    public ProjectTypologyRepositoryPersistence(ProjectTypologyRepositoryJPA projectTypologyRepositoryJPA,
                                                ProjectTypologyDomainDataAssembler projectTypologyDomainDataAssembler) {
        this.projectTypologyRepositoryJPA = projectTypologyRepositoryJPA;
        this.projectTypologyDomainDataAssembler = projectTypologyDomainDataAssembler;
    }

    /**
     * Sagves a project typology.
     *
     * @param projectTypology the project typology to be saved
     * @return the saved project typology
     */
    @Override
    public ProjectTypology save(ProjectTypology projectTypology) {
        ProjectTypologyJPA projectTypologyJPA = projectTypologyDomainDataAssembler.toData(projectTypology);

        ProjectTypologyJPA savedProjectTypologyJPA = projectTypologyRepositoryJPA.save(projectTypologyJPA);

        return projectTypologyDomainDataAssembler.toDomain(savedProjectTypologyJPA);
    }

    /**
     * Retrieves all project typologies in the JPA repository.
     *
     * @return An iterable collection of all project typologies in the repository
     */
    @Override
    public List<ProjectTypology> findAll() {
        List<ProjectTypologyJPA> projectTypologyJPAList =
                (List<ProjectTypologyJPA>) this.projectTypologyRepositoryJPA.findAll();

        return projectTypologyJPAList.stream()
                .map(projectTypologyDomainDataAssembler::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a project typology with the given id, in this case, the project typology name.
     *
     * @param projectTypologyName the project typology name to search for
     * @return An optional of the project typology if found, an empty optional otherwise
     */
    @Override
    public Optional<ProjectTypology> findById(ProjectTypologyName projectTypologyName) {
        return this.projectTypologyRepositoryJPA.findById(projectTypologyName.getProjectTypologyNameValue())
                .map(projectTypologyDomainDataAssembler::toDomain);
    }

    /**
     * Confirms if a project typology exists with the given id, in this case a project typology name.
     *
     * @param projectTypologyName the project typology to search for
     * @return true if project typology exists, false otherwise
     */
    @Override
    public boolean existsById(ProjectTypologyName projectTypologyName) {
        return this.projectTypologyRepositoryJPA.existsById(projectTypologyName.getProjectTypologyNameValue());
    }

    /**
     * Deletes all project typologies from the JPA repository
     */
    @Override
    public void deleteAll() {
        this.projectTypologyRepositoryJPA.deleteAll();
    }

    /**
     * Deletes a project typology with the given id, in this case a project typology name.
     *
     * @param id the project typology name to search for
     */
    @Override
    public void deleteById(ProjectTypologyName id) {
        this.projectTypologyRepositoryJPA.deleteById(id.getProjectTypologyNameValue());
    }
}

