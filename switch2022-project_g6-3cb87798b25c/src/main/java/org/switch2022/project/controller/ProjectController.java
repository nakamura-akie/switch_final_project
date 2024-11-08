package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.service.ProjectService;
import org.switch2022.project.utils.dto.NewProjectDTO;
import org.switch2022.project.utils.dto.OutputProjectDTO;
import org.switch2022.project.utils.dto.ProjectDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;
    private static final String RESOURCE_NAME = "projects";

    /**
     * Instantiates a new Project Controller
     *
     * @param projectService the project Service
     * @throws IllegalArgumentException when projectService is null
     */
    @Autowired
    public ProjectController(ProjectService projectService) {
        if (projectService == null) {
            throw new IllegalArgumentException("Project Service cannot be null");
        }

        this.projectService = projectService;
    }

    /**
     * Creates a new project.
     *
     * @param newProjectDTO The DTO containing the information for the new project.
     * @return A ResponseEntity containing the created OutputProjectDTO,
     * as well as links to the created project and the list of projects.
     */
    @PostMapping("/projects")
    public ResponseEntity<EntityModel<OutputProjectDTO>> createProject(@RequestBody NewProjectDTO newProjectDTO) {
        OutputProjectDTO outputProjectDTO = this.projectService.createProject(newProjectDTO);

        EntityModel<OutputProjectDTO> entityModel = EntityModel.of(
                outputProjectDTO,
                linkTo(methodOn(ProjectController.class)
                        .findProjectById(new ProjectCode(outputProjectDTO.getProjectCode()))).withSelfRel(),
                linkTo(methodOn(ProjectController.class).createProjectList()).withRel(RESOURCE_NAME)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param projectCode Identifier of the project code that is being searched for.
     * @return ResponseEntity containing a DTO of the retrieved project, as well as
     * links to the fetched project and to the list of projects.
     */
    @GetMapping("/projects/{projectCode}")
    public ResponseEntity<EntityModel<OutputProjectDTO>> findProjectById(@PathVariable ProjectCode projectCode) {
        OutputProjectDTO projectDTO = projectService.findProjectById(projectCode);

        EntityModel<OutputProjectDTO> entityModel = EntityModel.of(
                projectDTO,
                linkTo(methodOn(ProjectController.class).findProjectById(new ProjectCode(projectDTO.getProjectCode())))
                        .withSelfRel(),
                linkTo(methodOn(ProjectController.class).createProjectList()).withRel(RESOURCE_NAME)
        );
        return ResponseEntity.ok(entityModel);
    }

    /**
     * Retrieves a collection of projects.
     *
     * @return CollectionModel containing a list of ProjectDTO.
     */
    @GetMapping("/projects")
    public CollectionModel<EntityModel<ProjectDTO>> createProjectList() {
        List<ProjectDTO> projectList = projectService.createProjectList();

        List<EntityModel<ProjectDTO>> entityModelList = projectList.stream()
                .map(projectDTO -> EntityModel.of(
                        projectDTO,
                        linkTo(methodOn(ProjectController.class)
                                .findProjectById(new ProjectCode(projectDTO.projectCode))).withSelfRel(),
                        linkTo(methodOn(ProjectController.class).createProjectList()).withRel(RESOURCE_NAME))
                )
                .collect(Collectors.toList());

        return CollectionModel.of(
                entityModelList,
                linkTo(methodOn(ProjectController.class).createProjectList()).withRel(IanaLinkRelations.SELF));
    }
}
