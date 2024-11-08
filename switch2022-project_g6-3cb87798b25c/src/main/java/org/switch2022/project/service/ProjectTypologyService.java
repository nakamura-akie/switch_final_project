package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.projecttypology.ProjectTypologyFactory;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.utils.assembler.OutputProjectTypologyAssembler;
import org.switch2022.project.utils.dto.NewProjectTypologyDTO;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

@Service
public class ProjectTypologyService {

    private final Repository<ProjectTypology, ProjectTypologyName> projectTypologyRepository;

    private final ProjectTypologyFactory projectTypologyFactory;


    /**
     * Constructs a ProjectTypologyService with the specified project typology repository and project typology factory.
     *
     * @param projectTypologyRepository The repository for managing project typologies.
     * @param projectTypologyFactory    The factory for creating project typology objects.
     * @throws IllegalArgumentException If either the project typology repository or the project typology factory
     * is null.
     */
    @Autowired
    public ProjectTypologyService(Repository<ProjectTypology, ProjectTypologyName> projectTypologyRepository,
                                  ProjectTypologyFactory projectTypologyFactory) {

        if (projectTypologyRepository == null) {
            throw new IllegalArgumentException("Project Typology Repository cannot be null");
        }
        if (projectTypologyFactory == null) {
            throw new IllegalArgumentException("Project Typology Factory cannot be null");
        }
        this.projectTypologyRepository = projectTypologyRepository;
        this.projectTypologyFactory = projectTypologyFactory;
    }

    /**
     * Creates a default project typology with the given project typology value.
     *
     * @param projectTypologyValue The value of the project typology.
     */
    public void createDefaultProjectTypology(String projectTypologyValue) {
        ProjectTypologyName projectTypologyName = new ProjectTypologyName(projectTypologyValue);

        ProjectTypology defaultProjectTypology = projectTypologyFactory.createProjectTypology(projectTypologyName);
        projectTypologyRepository.save(defaultProjectTypology);
    }

    /**
     * Creates a project typology based on the provided new project typology DTO.
     *
     * @param newProjectTypologyDTO The DTO containing the details of the new project typology.
     * @return The DTO representation of the created project typology.
     * @throws IllegalArgumentException If the project typology already exists.
     */
    public OutputProjectTypologyDTO createProjectTypology(NewProjectTypologyDTO newProjectTypologyDTO) {

        ProjectTypologyName projectTypologyName = newProjectTypologyDTO.projectTypologyName;

        boolean projectTypologyExists = projectTypologyRepository.existsById(projectTypologyName);

        if (projectTypologyExists) {
            throw new IllegalArgumentException("Project Typology already exists");
        }

        ProjectTypology newProjectTypology = projectTypologyFactory.createProjectTypology(projectTypologyName);

        ProjectTypology savedProjectTypology = projectTypologyRepository.save(newProjectTypology);

        return OutputProjectTypologyAssembler.generateProjectTypologyDTO(savedProjectTypology);
    }
}