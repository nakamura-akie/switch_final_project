package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.service.ProjectService;
import org.switch2022.project.service.UserStoryService;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductBacklogController {

    private final ProjectService projectService;
    private final UserStoryService userStoryService;

    /**
     * Constructor of Product Backlog Controller class.
     * Has a redundant `@Autowired` annotation to indicate that instances of `ProjectService`
     * and `UserStoryService` are injected into the controller.
     *
     * @param projectService   Project Service.
     * @param userStoryService User Story Service.
     * @throws NullConstructorArgumentException when productBacklogService is null.
     */
    @Autowired
    public ProductBacklogController(ProjectService projectService, UserStoryService userStoryService) {
        if (projectService == null) {
            throw new IllegalArgumentException("Product Backlog Service cannot be null");
        }
        this.projectService = projectService;
        this.userStoryService = userStoryService;
    }

    /**
     * Returns a list of User Story DTO.
     *
     * @param projectCode The project code is used to identify the project from
     *                    which the user stories need to be retrieved.
     * @return ResponseEntity with HTTP status OK (200) and collection of User Story DTO.
     * <p>
     * Returns a ResponseEntity with HTTP status CONFLICT (409) in case of a RuntimeException.
     */
    @GetMapping("/projects/{projectCode}/user-stories")
    public ResponseEntity<Object> createUserStoryList(@PathVariable String projectCode) {
        try {
            ProjectCode projectCodeVO = new ProjectCode(projectCode);

            List<UserStoryDTO> listOfUserStoryDTOs =
                    projectService.createUserStoryList(projectCodeVO);

            CollectionModel<UserStoryDTO> collectionModel = CollectionModel.of(listOfUserStoryDTOs);

            return ResponseEntity.ok(collectionModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves a user story.
     *
     * @param projectCode   The project's code to which the user story belongs to.
     * @param userStoryCode The code of the user story to be retrieved.
     * @return A ResponseEntity containing the user story and
     * HTTP status OK (200)
     * <p>
     * Returns ResponseEntity with the HTTP status NOT FOUND (404) in case
     * a RuntimeException is thrown.
     */
    @GetMapping("/projects/{projectCode}/user-stories/{userStoryCode}")
    public ResponseEntity<Object> findUserStoryByID(
            @PathVariable ProjectCode projectCode,
            @PathVariable UserStoryCode userStoryCode) {
        try {

            UserStoryID userStoryID = new UserStoryID(projectCode, userStoryCode);

            OutputUserStoryDTO userStoryDTO = userStoryService.findUserStoryByID(userStoryID);

            EntityModel<?> entityModel = EntityModel.of(
                    userStoryDTO,
                    linkTo(methodOn(ProductBacklogController.class)
                            .findUserStoryByID(projectCode, userStoryCode)).withSelfRel(),
                    linkTo(methodOn(ProductBacklogController.class)
                            .createUserStoryList(projectCode.getProjectCodeValue()))
                            .withRel("/projects/" + projectCode + "/user-stories")
            );
            return ResponseEntity.ok(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }
}
