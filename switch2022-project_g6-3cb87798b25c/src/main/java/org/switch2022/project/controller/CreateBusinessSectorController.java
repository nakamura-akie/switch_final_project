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
import org.switch2022.project.service.BusinessSectorService;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewBusinessSectorDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/business-sectors")
public class CreateBusinessSectorController {
    private final BusinessSectorService businessSectorService;

    /**
     * Instantiates a new CreateBusinessSectorController
     *
     * @param businessSectorService which cannot be null.
     * @throws IllegalArgumentException if businessSectorService is null.
     */
    @Autowired
    public CreateBusinessSectorController(BusinessSectorService businessSectorService) {
        if (businessSectorService == null) {
            throw new IllegalArgumentException("Business Sector Service cannot be null.");
        }

        this.businessSectorService = businessSectorService;
    }

    /**
     * Creates a new Business Sector.
     *
     * @param newBusinessSectorDTO The DTO containing the information for
     *                             the new business sector.
     * @return ResponseEntity with the HTTP status CREATED (201) and
     * CreatedBusinessSectorDTO of the newly created Business Sector
     * if successful.
     * <p>
     * Returns ResponseEntity with the HTTP status CONFLICT (409) and the
     * error message if a RuntimeException is caught.
     */
    @PostMapping("")
    public ResponseEntity<Object> createBusinessSector(@RequestBody NewBusinessSectorDTO newBusinessSectorDTO) {

        try {
            CreatedBusinessSectorDTO createdBusinessSectorDTO =
                    businessSectorService.createBusinessSector(newBusinessSectorDTO);

            EntityModel<CreatedBusinessSectorDTO> entityModel = EntityModel.of(
                    createdBusinessSectorDTO,
                    linkTo(methodOn(CreateBusinessSectorController.class).createBusinessSector(newBusinessSectorDTO))
                            .withSelfRel()
            );
            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageResponse(e.getMessage()));
        }

    }
}
