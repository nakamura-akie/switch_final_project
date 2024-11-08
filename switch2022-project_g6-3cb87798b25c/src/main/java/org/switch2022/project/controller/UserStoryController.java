package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.service.UserStoryService;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewUserStoryDTO;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserStoryController {

    private final UserStoryService userStoryService;

    /**
     * Instantiates a new UserStoryController
     *
     * @param userStoryService UserStoryService instance.
     * @throws NullConstructorArgumentException if userStoryService is null.
     */
    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        if (userStoryService == null) {
            throw new NullConstructorArgumentException("User Story Service cannot be null.");
        }
        this.userStoryService = userStoryService;
    }

    /**
     * Creates a user story
     *
     * @param newUserStoryDTO the newUserStoryDTO which contains the information for the user story
     *                        to be created.
     * @return ResponseEntity with the HTTP status CREATED (201) and {@link OutputUserStoryDTO}
     * of the userStory created if successful.
     * <p>
     * Returns ResponseEntity with the HTTP status CONFLICT (409) and the error message when it fails.
     */

    @PostMapping("/user-stories")
    public ResponseEntity<Object> createUserStory(@RequestBody NewUserStoryDTO newUserStoryDTO) {
        try {
            UserStoryDTO userStoryDTO = userStoryService.createUserStory(newUserStoryDTO);

            EntityModel<UserStoryDTO> entityModel = EntityModel.of(userStoryDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Updates the status of a user story.
     *
     * @param projectCode          The code of the project containing the user story.
     * @param userStoryCode        The code of the user story to be updated.
     * @param inputUserStoryStatus The new status of the user story.
     * @return A ResponseEntity containing the HTTP status OK (200) and the updated
     * {@link OutputUserStoryDTO}.
     * <p>
     * Returns ResponseEntity with the HTTP status BAD_REQUEST (400) and the
     * error message when it fails.
     */
    @PatchMapping("/projects/{projectCode}/user-stories/{userStoryCode}")
    public ResponseEntity<Object> updateUserStoryStatus(@PathVariable ProjectCode projectCode,
                                                        @PathVariable UserStoryCode userStoryCode,
                                                        @RequestBody UserStoryStatus inputUserStoryStatus) {

        try {
            OutputUserStoryDTO outputUserStoryDTO = userStoryService
                    .changeUserStoryStatus(inputUserStoryStatus, userStoryCode, projectCode);

            EntityModel<OutputUserStoryDTO> entityModel = EntityModel.of(
                    outputUserStoryDTO,
                    linkTo(methodOn(ProductBacklogController.class)
                            .findUserStoryByID(projectCode, userStoryCode))
                            .withSelfRel());

            return ResponseEntity.ok(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
}
