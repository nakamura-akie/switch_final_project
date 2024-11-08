package org.switch2022.project.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.service.ResourceService;
import org.switch2022.project.utils.dto.OutputResourceDTO;
import org.switch2022.project.utils.dto.NewResourceDTO;
import org.switch2022.project.utils.dto.ResourceDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
public class ResourceController {
    private final ResourceService resourceService;

    private static final String RESOURCE_NAME = "resources";

    /**
     * Instantiates a new ResourceController.
     *
     * @param resourceService the resource Service.
     * @throws IllegalArgumentException when resource service is null.
     */
    public ResourceController(ResourceService resourceService) {
        if (resourceService == null) {
            throw new IllegalArgumentException("Resource Service cannot be null");
        }
        this.resourceService = resourceService;
    }

    /**
     * Creates a new Resource
     *
     * @param info is a {@link NewResourceDTO} with the information needed to allocate
     *             a user to a project, assuming a role on it,during the
     *             specified time window.
     * @return ResponseEntity with the HTTP status CREATED (201) and
     * {@link OutputResourceDTO} of the resource newly created if successful.
     * @throws ApiException if an error occurs while adding a resource to a project.
     */
    @PostMapping("/resources")
    public ResponseEntity<Object> addResourceToProject(@RequestBody NewResourceDTO info) {

        OutputResourceDTO createdResourceDTO = resourceService.addResourceToProject(info);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                createdResourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(
                        info.getEmail(),
                        info.getProjectCode(),
                        info.getStartDate(),
                        info.getEndDate())).withSelfRel(),
                linkTo(methodOn(ResourceController.class).addResourceToProject(info)).withRel(RESOURCE_NAME)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Retrieves a list of resources currently in a project.
     *
     * @param projectCode A path variable representing the code of the project for which the resources
     *                    are being requested.
     * @param dateOfToday The date at the time of the request, in the format "yyyy-MM-dd".
     * @return EntityModelList of {@link ResourceDTO} of the retrieved Resources.
     * @throws ApiException if an error occurs during the request process.
     */
    @GetMapping("/resources/{projectCode}/{dateOfToday}")
    public CollectionModel<EntityModel<ResourceDTO>> getCurrentResourcesInProjectList(
            @PathVariable ProjectCode projectCode,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfToday) {

        List<ResourceDTO> resourcesDTOList = resourceService
                .createHumanResourcesInProjectList(projectCode, dateOfToday);

        List<EntityModel<ResourceDTO>> entityModelList = resourcesDTOList.stream()
                .map(resourceDTO -> {
                    LocalDate startDate = LocalDate.parse(resourceDTO.startDate);
                    LocalDate endDate = LocalDate.parse(resourceDTO.endDate);
                    Email resourceEmail = new Email(resourceDTO.email);
                    ProjectCode resourceProjectCode = new ProjectCode(resourceDTO.projectCode);

                    return EntityModel.of(
                            resourceDTO,
                            linkTo(methodOn(ResourceController.class)
                                    .findResourceByID(resourceEmail, resourceProjectCode, startDate, endDate))
                                    .withSelfRel());
                })
                .collect(Collectors.toList());

        return CollectionModel.of(
                entityModelList,
                linkTo(methodOn(ResourceController.class)
                        .getCurrentResourcesInProjectList(projectCode, dateOfToday))
                        .withRel(IanaLinkRelations.SELF));
    }

    /**
     * Retrieves a resource by its ID within a specific time period.
     *
     * @param resourceEmail The email of the resource.
     * @param projectCode   The code of the project.
     * @param startDate     The start date of the time period (in the format yyyy-mm-dd).
     * @param endDate       The end date of the time period (in the format yyyy-mm-dd).
     * @return A ResponseEntity with the HTTP status OK (200) containing an {@link OutputResourceDTO}
     * of the resource, if successful.
     */
    @GetMapping("/resources/{resourceEmail}/{projectCode}/{startDate}/{endDate}")
    public ResponseEntity<Object> findResourceByID(@PathVariable Email resourceEmail,
                                                   @PathVariable ProjectCode projectCode,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                   LocalDate startDate,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                   LocalDate endDate) {

        TimePeriod resourceTimePeriod = new TimePeriod(startDate, endDate);
        ResourceID resourceID = new ResourceID(resourceEmail, projectCode, resourceTimePeriod);

        OutputResourceDTO resourceDTO = resourceService.findResourceByID(resourceID);

        EntityModel<OutputResourceDTO> entityModel = EntityModel.of(
                resourceDTO,
                linkTo(methodOn(ResourceController.class).findResourceByID(resourceEmail,
                        projectCode, startDate, endDate)).withSelfRel(),
                linkTo(ResourceController.class).slash(RESOURCE_NAME).withRel(RESOURCE_NAME)
        );

        return ResponseEntity.ok(entityModel);
    }
}