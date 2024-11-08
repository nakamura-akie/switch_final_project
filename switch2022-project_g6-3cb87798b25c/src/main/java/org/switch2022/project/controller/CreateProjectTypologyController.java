package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.switch2022.project.service.ProjectTypologyService;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewProjectTypologyDTO;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/projectTypologies")
public class CreateProjectTypologyController {

    private final ProjectTypologyService projectTypologyService;

    /**
     * Instantiates a new CreateProjectTypologyController
     *
     * @param projectTypologyService which cannot be null.
     * @throws IllegalArgumentException if projectTypologyService is null.
     */
    @Autowired
    public CreateProjectTypologyController(ProjectTypologyService projectTypologyService) {

        if (projectTypologyService == null) {
            throw new IllegalArgumentException("Project Typology Service cannot be null.");
        }
        this.projectTypologyService = projectTypologyService;
    }

    /**
     * Creates a new Project Typology.
     *
     * @param newProjectTypologyDTO DTO containing the information for the new project typology.
     * @return ResponseEntity with the HTTP status CREATED (201) and OutputProjectTypologyDTO
     * of the newly created resource if successful.
     * <p>
     * Returns ResponseEntity with the HTTP status CONFLICT (409) and the error message if
     * project typology alreadty exists.
     */
    @PostMapping("")
    public ResponseEntity<Object> createProjectTypology(@RequestBody NewProjectTypologyDTO newProjectTypologyDTO) {

        try {
            OutputProjectTypologyDTO outputProjectTypologyDTO =
                    projectTypologyService.createProjectTypology(newProjectTypologyDTO);

            EntityModel<OutputProjectTypologyDTO> entityModel = EntityModel.of(
                    outputProjectTypologyDTO,
                    linkTo(methodOn(CreateProjectTypologyController.class)
                            .createProjectTypology(newProjectTypologyDTO))
                            .withSelfRel()
            );

            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }
}