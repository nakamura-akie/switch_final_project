package org.switch2022.project.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin
public class RootController {

    protected RootController() {
    }

    /**
     * Handles the root URL and returns a RepresentationModel containing links to  the various resources.
     *
     * @return links to projects, resources, user stories, customers,
     * sprints, and profiles.
     */
    @GetMapping("")
    public RepresentationModel<EntityModel<Links>> index() {

        RepresentationModel<EntityModel<Links>> rootModel = new RepresentationModel<>();

        rootModel.add(linkTo(ProjectController.class).withRel("projects"));
        rootModel.add(linkTo(ResourceController.class).slash("resources").withRel("resources"));
        rootModel.add(linkTo(UserStoryController.class).slash("user-stories").withRel("user-stories"));
        rootModel.add(linkTo(CustomerController.class).withRel("customers"));
        rootModel.add(linkTo(SprintController.class).slash("sprints").withRel("sprints"));
        rootModel.add(linkTo(ProfileController.class).withRel("profiles"));

        return rootModel;
    }
}
