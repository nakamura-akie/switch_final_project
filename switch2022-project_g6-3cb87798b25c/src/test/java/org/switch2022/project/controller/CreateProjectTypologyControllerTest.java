package org.switch2022.project.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.service.ProjectTypologyService;
import org.switch2022.project.utils.MessageResponse;
import org.switch2022.project.utils.dto.NewProjectTypologyDTO;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
class CreateProjectTypologyControllerTest {


    @Autowired
    private CreateProjectTypologyController controller;

    @Autowired
    private ProjectTypologyService projectTypologyService;

    @Test
    void constructor_checkIfExceptionIsThrownWhenServiceIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CreateProjectTypologyController(null));

        assertEquals("Project Typology Service cannot be null.", exception.getMessage());
    }

    @Test
    void createProjectTypology_HappyPath() {

        ProjectTypologyName projectTypologyName = new ProjectTypologyName("Construction");

        NewProjectTypologyDTO newProjectTypologyDTO = new NewProjectTypologyDTO();
        newProjectTypologyDTO.projectTypologyName = projectTypologyName;

        OutputProjectTypologyDTO PTDto = new OutputProjectTypologyDTO();
        PTDto.projectTypologyValue = "Construction";

        EntityModel<OutputProjectTypologyDTO> entityModel = EntityModel.of(
                PTDto,
                linkTo(methodOn(CreateProjectTypologyController.class).createProjectTypology(newProjectTypologyDTO)).withSelfRel()
        );
        ResponseEntity<Object> responseEntity = ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);

        ResponseEntity<Object> result = controller.createProjectTypology(newProjectTypologyDTO);

        assertEquals(responseEntity, result);
    }

    @Test
    void createProjectTypology_ProjectTypologyAlreadyExists_ThrowsException() {

        projectTypologyService.createDefaultProjectTypology("Time and Materials");
        projectTypologyService.createDefaultProjectTypology("Fixed-Cost");

        ProjectTypologyName projectTypologyName = new ProjectTypologyName("Time and Materials");

        NewProjectTypologyDTO newProjectTypologyDTO = new NewProjectTypologyDTO();
        newProjectTypologyDTO.projectTypologyName = projectTypologyName;

        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Project Typology already exists"));

        ResponseEntity<Object> result = controller.createProjectTypology(newProjectTypologyDTO);

        assertEquals(expected, result);
    }

}