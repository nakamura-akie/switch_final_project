package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.service.SprintService;
import org.switch2022.project.utils.*;
import org.switch2022.project.utils.dto.CreatedSprintDTO;
import org.switch2022.project.utils.dto.NewSprintDTO;
import org.switch2022.project.utils.dto.SprintStatusDTO;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SprintController {

    private final SprintService sprintService;

    /**
     * Instantiates a new SprintController.
     *
     * @param sprintService SprintService instance.
     * @throws IllegalArgumentException if sprintService is null.
     */
    @Autowired
    public SprintController(SprintService sprintService) {
        if (sprintService == null) {
            throw new IllegalArgumentException("Sprint Service cannot be null.");
        }
        this.sprintService = sprintService;
    }

    /**
     * Creates a Sprint.
     *
     * @param newSprintDTO The DTO containing the information for the sprint
     *                     to be created.
     * @return ResponseEntity with the HTTP status CREATED (201) and
     * {@link CreatedSprintDTO} of the newly created Sprint if successful.
     * <p>
     * Returns ResponseEntity with the HTTP status CONFLICT (409) and the
     * error message if a RuntimeException is caught.
     */
    @PostMapping("/sprints")
    public ResponseEntity<Object> createSprintREST(@RequestBody NewSprintDTO newSprintDTO) {
        try {
            CreatedSprintDTO createdSprintDTO = sprintService.createSprint(newSprintDTO);

            EntityModel<CreatedSprintDTO> entityModel = EntityModel.of(createdSprintDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Changes the status of a sprint.
     *
     * @param sprintStatusDTO The {@link SprintStatusDTO} containing the info of the sprint to be updated.
     * @return ResponseEntity with the HTTP status OK (200) and CreatedSprintDTO of the updated Business
     * Sector if successful.
     * <p>
     * Returns ResponseEntity with the HTTP status CONFLICT (409) and the error message if a
     * RuntimeException is caught.
     */
    @PatchMapping("/sprints")
    public ResponseEntity<Object> changeSprintStatusREST(@RequestBody SprintStatusDTO sprintStatusDTO) {
        try {
            CreatedSprintDTO createdSprintDTO = sprintService.changeSprintStatus(sprintStatusDTO);

            EntityModel<CreatedSprintDTO> entityModel = EntityModel.of(createdSprintDTO);

            return ResponseEntity.status(HttpStatus.OK).body(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves the open sprint for the specified project.
     *
     * @param projectCodeValue The code of the project.
     * @return ResponseEntity with the HTTP status OK (200) and
     * OpenSprintOutputDTO of current open sprint if successful.
     * <p>
     * Returns HTTP status NOT FOUND (404) if a RuntimeException is caught.
     */
    @GetMapping("projects/{projectCodeValue}/sprints/open")
    public ResponseEntity<Object> getOpenSprint(@PathVariable String projectCodeValue) {
        try {
            ProjectCode projectCode = new ProjectCode(projectCodeValue);

            OpenSprintOutputDTO openSprintDTO = sprintService.getOpenSprint(projectCode);

            EntityModel<?> entityModel = EntityModel.of(
                    openSprintDTO);

            return ResponseEntity.ok(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Creates a list of open sprints for a specific project.
     *
     * @param projectCodeValue The code of the project.
     * @return ResponseEntity with the HTTP status OK (200) and a list of {@link OpenSprintOutputDTO}
     * representing the open sprints for the project, if successful.
     * <p>
     * Returns HTTP status CONFLICT (409) if a RuntimeException is caught.
     */
    @GetMapping("projects/{projectCodeValue}/sprints")
    public ResponseEntity<Object> createSprintList(@PathVariable String projectCodeValue) {
        try {
            ProjectCode projectCodeVO = new ProjectCode(projectCodeValue);

            List<OpenSprintOutputDTO> listOfSprintDTOs =
                    sprintService.createSprintList(projectCodeVO);

            CollectionModel<OpenSprintOutputDTO> collectionModel = CollectionModel.of(listOfSprintDTOs);

            return ResponseEntity.ok(collectionModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

}
