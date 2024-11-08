package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;
import org.switch2022.project.service.SprintService;
import org.switch2022.project.utils.*;
import org.switch2022.project.utils.dto.NewSprintBacklogUserStoryDTO;
import org.switch2022.project.utils.dto.OutputSprintDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.dto.ViewSprintBacklogDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.List;

@RestController
@RequestMapping("projects/{projectCode}/sprints/{sprintCode}/backlog")
@CrossOrigin(origins = "http://localhost:3000")
public class SprintBacklogController {
    private final SprintService sprintService;

    /**
     * Instantiates a new SprintBacklogController.
     *
     * @param sprintService SprintService instance.
     * @throws NullConstructorArgumentException if sprintService is null.
     */
    @Autowired
    public SprintBacklogController(SprintService sprintService) {
        if (sprintService == null) {
            throw new NullConstructorArgumentException("Sprint Service cannot be null.");
        }
        this.sprintService = sprintService;
    }

    /**
     * Adds new user story to a sprint backlog in a project and returns the
     * updated sprint backlog.
     *
     * @param projectCode     The code of the project containing the user story.
     * @param sprintCode      The code of the sprint that will contain the new user story.
     * @param newUserStoryDTO the newUserStoryDTO which contains the information
     *                        for the user story to be added.
     * @return ResponseEntity with {@link OutputSprintDTO} and HTTP status OK (200) if the
     * operation is successful.
     * <p>
     * Returns a ResponseEntity with HTTP status CONFLICT (409) in case a
     * RuntimeException is thrown.
     */
    @PatchMapping("")
    public ResponseEntity<Object> addUserStoryToSprintBacklog(
            @PathVariable ProjectCode projectCode,
            @PathVariable SprintCode sprintCode,
            @RequestBody NewSprintBacklogUserStoryDTO newUserStoryDTO) {
        try {
            OutputSprintDTO sprintBacklogDTO = sprintService
                    .addUserStoryToSprintBacklog(projectCode, sprintCode, newUserStoryDTO);

            EntityModel<OutputSprintDTO> entityModel = EntityModel.of(sprintBacklogDTO);

            return ResponseEntity.status(HttpStatus.OK).body(entityModel);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves a list of user stories of a specific sprint with their status (the business term
     * for this collection being "ScrumBoard").
     *
     * @param projectCode The project's code to which the sprint belongs to.
     * @param sprintCode  The code of a sprint in a project.
     * @return ResponseEntity with HTTP status OK (200) and collection of {@link UserStoryDTO}
     * with corresponding status.
     * <p>
     * Returns a ResponseEntity with HTTP status CONFLICT (409) in case of a RuntimeException.
     */
    @GetMapping("")
    public ResponseEntity<Object> showScrumBoard
    (@PathVariable String projectCode,
     @PathVariable String sprintCode) {

        try {
            ProjectCode projectCodeValueObject = new ProjectCode(projectCode);
            SprintCode sprintCodeValueObject = new SprintCode(sprintCode);

            ViewSprintBacklogDTO sprintBacklogDTO = new ViewSprintBacklogDTO();
            sprintBacklogDTO.projectCode = projectCodeValueObject;
            sprintBacklogDTO.sprintCode = sprintCodeValueObject;

            List<UserStoryDTO> listOfUserStoryDTOsWithStatus = sprintService.showScrumBoard(sprintBacklogDTO);

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(listOfUserStoryDTOsWithStatus);

            return ResponseEntity.ok(collectionModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

}
