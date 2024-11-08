package org.switch2022.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewBusinessSectorDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
class CreateBusinessSectorControllerTest {

    @Autowired
    CreateBusinessSectorController businessSectorController;

    @Test
    void constructor_createWithNullService_ThrowsException() {
        String expected = "Business Sector Service cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () ->
                new CreateBusinessSectorController(null)).getMessage();

        assertEquals(expected, result);
    }

    void createBusinessSector_ValidBusinessSector_True() {

        CreatedBusinessSectorDTO createdBusinessSectorDTO = new CreatedBusinessSectorDTO();
        createdBusinessSectorDTO.businessSectorNameValue = "Scientology";
        NewBusinessSectorDTO anotherBusinessSectorDTO = new NewBusinessSectorDTO();
        anotherBusinessSectorDTO.businessSectorName =
                new BusinessSectorName(createdBusinessSectorDTO.businessSectorNameValue);

        EntityModel<?> entityModel = EntityModel.of(
                createdBusinessSectorDTO,
                linkTo(methodOn(CreateBusinessSectorController.class).createBusinessSector(anotherBusinessSectorDTO)).withSelfRel()
        );

        ResponseEntity<?> expected =
                ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        NewBusinessSectorDTO newBusinessSectorDTO = new NewBusinessSectorDTO();
        newBusinessSectorDTO.businessSectorName = new BusinessSectorName("Scientology");

        ResponseEntity<?> result = businessSectorController.createBusinessSector(newBusinessSectorDTO);

        assertEquals(expected, result);
    }

    @Test
    void createBusinessSector_ExistentBusinessSector_False() {
        NewBusinessSectorDTO newBusinessSectorDTO = new NewBusinessSectorDTO();
        newBusinessSectorDTO.businessSectorName = new BusinessSectorName("Scientology");
        businessSectorController.createBusinessSector(newBusinessSectorDTO);

        ResponseEntity<?> expected =
                ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Business Sector already exists."));

        ResponseEntity<?> result = businessSectorController.createBusinessSector(newBusinessSectorDTO);

        assertEquals(expected, result);
    }
}

