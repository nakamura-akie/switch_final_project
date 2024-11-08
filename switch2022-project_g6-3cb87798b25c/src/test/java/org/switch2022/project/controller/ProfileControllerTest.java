package org.switch2022.project.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.valueobject.ProfileName;
import org.switch2022.project.repository.ProfileRepositoryFake;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewProfileDTO;
import org.switch2022.project.utils.dto.OutputProfileDTO;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
class ProfileControllerTest {

    @Autowired
    private ProfileController controller;
    private NewProfileDTO newProfileDTO;
    private OutputProfileDTO outputProfileDTO;
    @Autowired
    private ProfileRepositoryFake profileRepositoryFake;

    @BeforeEach
    void init() {
        newProfileDTO = new NewProfileDTO();
        outputProfileDTO = new OutputProfileDTO();
    }

    @AfterEach
    void tearDown() {
        newProfileDTO = null;
        outputProfileDTO = null;
        profileRepositoryFake.deleteAll();
    }

    @Test
    void constructor_NullProfileService_ThrowsException() {
        String expected = "Profile Service cannot be null";

        String result = assertThrows(NullConstructorArgumentException.class, () -> {
            new ProfileController(null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void createProfile_CreatesNewProfile_True() {
        ProfileName profileName = new ProfileName("a new profile");
        newProfileDTO.profileName = profileName;
        outputProfileDTO.profileName = profileName.getProfileNameValue();

        EntityModel<OutputProfileDTO> entityModel = EntityModel.of(
                outputProfileDTO,
                linkTo(methodOn(ProfileController.class).createProfile(
                        newProfileDTO))
                        .withRel("collection"),
                linkTo(methodOn(ProfileController.class).getProfile(
                        newProfileDTO.profileName.getProfileNameValue()))
                        .withSelfRel());

        ResponseEntity<Object> expected = ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

        ResponseEntity<Object> result = controller.createProfile(newProfileDTO);

        assertEquals(expected, result);
    }

    @Test
    void createProfile_TryToCreateExistentProfile_False() {
        newProfileDTO.profileName = new ProfileName("another new profile");

        controller.createProfile(newProfileDTO);

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body
                (new MessageResponse("Profile already exists"));

        ResponseEntity<Object> result = controller.createProfile(newProfileDTO);

        assertEquals(expected, result);
    }

    @Test
    void getProfile_GetExistentProfile_True() {
        ProfileName profileName = new ProfileName("another new profile");
        newProfileDTO.profileName = profileName;
        outputProfileDTO.profileName = profileName.getProfileNameValue();

        controller.createProfile(newProfileDTO);

        outputProfileDTO.profileName = profileName.getProfileNameValue();

        EntityModel<OutputProfileDTO> entityModel = EntityModel.of(
                outputProfileDTO,
                linkTo(methodOn(ProfileController.class)
                        .getProfile("another new profile")).withSelfRel());

        ResponseEntity<Object> expected = ResponseEntity.ok(
                entityModel);

        ResponseEntity<Object> result = controller.getProfile("another new profile");

        assertEquals(expected, result);
    }

    @Test
    void getProfile_GetNonExistentProfile_ThrowsException() {
        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body
                (new MessageResponse("Profile not found"));

        ResponseEntity<Object> result = controller.getProfile("non existent profile");

        assertEquals(expected, result);
    }

}