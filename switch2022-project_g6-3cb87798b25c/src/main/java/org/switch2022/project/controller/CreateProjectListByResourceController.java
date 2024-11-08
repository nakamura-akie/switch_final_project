package org.switch2022.project.controller;

import org.springframework.stereotype.Controller;
import org.switch2022.project.service.ResourceService;
import org.switch2022.project.utils.dto.ProjectByResourceDTO;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CreateProjectListByResourceController {
    private final ResourceService resourceService;

    /**
     * Instantiates a new CreateProjectListByResourceController
     *
     * @param resourceService which cannot be null.
     * @throws IllegalArgumentException if resourceService is null.
     */
    public CreateProjectListByResourceController(ResourceService resourceService) {
        if (resourceService == null) {
            throw new IllegalArgumentException("Resource Service cannot be null.");
        }

        this.resourceService = resourceService;
    }

    /**
     * Creates a list of projects, filtering by resource (based on the given email)
     * and current date.
     *
     * @param email       Email address associated to a specific resource.
     * @param currentDate The currentDate parameter is a LocalDate object that represents the current
     *                    date. It is used for filtering projects
     * @return List of ProjectByResourceDTO objects.
     */
    public List<ProjectByResourceDTO> createProjectListByResource(String email, LocalDate currentDate) {
        return resourceService.createProjectListByResource(email, currentDate);
    }
}
