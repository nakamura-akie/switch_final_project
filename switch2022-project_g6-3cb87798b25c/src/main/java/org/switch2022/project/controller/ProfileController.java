package org.switch2022.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.switch2022.project.service.ProfileService;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewProfileDTO;
import org.switch2022.project.utils.dto.OutputProfileDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Constructs a new instance of the ProfileController class.
     *
     * @param profileService the profile service to be injected
     * @throws NullConstructorArgumentException if the profileService parameter is null
     */
    @Autowired
    public ProfileController(ProfileService profileService) {
        if (profileService == null) {
            throw new NullConstructorArgumentException("Profile Service cannot be null");
        }
        this.profileService = profileService;
    }

    /**
     * Creates a new profile.
     *
     * @param newProfileDTO the DTO containing the profile information
     * @return a ResponseEntity with the created profile and appropriate HTTP status
     */
    @PostMapping("")
    public ResponseEntity<Object> createProfile(@RequestBody NewProfileDTO newProfileDTO) {
        try {
            OutputProfileDTO outputProfileDTO = profileService.createProfile(newProfileDTO);

            EntityModel<OutputProfileDTO> entityModel = EntityModel.of(
                    outputProfileDTO,
                    linkTo(methodOn(ProfileController.class).createProfile(newProfileDTO))
                            .withRel("collection"),
                    linkTo(methodOn(ProfileController.class).getProfile(
                            newProfileDTO.profileName.getProfileNameValue()))
                            .withSelfRel());

            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Retrieves a profile by its profileName.
     *
     * @param profileName the profileName parameter as a query parameter
     * @return a ResponseEntity with the retrieved profile and appropriate HTTP status
     */
    @GetMapping
    public ResponseEntity<Object> getProfile(@RequestParam("profileName") String profileName) {
        try {
            OutputProfileDTO outputProfileDTO = profileService.findProfile(profileName);

            EntityModel<OutputProfileDTO> entityModel = EntityModel.of(outputProfileDTO,
                    linkTo(methodOn(ProfileController.class).getProfile(profileName)).withSelfRel());

            return ResponseEntity.ok(entityModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }

}