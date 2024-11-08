package org.switch2022.project.controller;

import org.springframework.stereotype.Controller;
import org.switch2022.project.domain.valueobject.Effort;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.domain.valueobject.UserStoryID;
import org.switch2022.project.service.UserStoryService;

@Controller
public class EstimateEffortController {

    private final UserStoryService userStoryService;

    /**
     * Instantiates a new EstimateEffort Controller.
     *
     * @param userStoryService the user story Service.
     * @throws IllegalArgumentException when userStoryService is null.
     */
    public EstimateEffortController(UserStoryService userStoryService) {

        if (userStoryService == null) {
            throw new IllegalArgumentException("User Story Service cannot be null.");
        }
        this.userStoryService = userStoryService;

    }

    /**
     * Estimates the effort for a user story of a project.
     *
     * @param projectCode   The identifier code of the project to which the user story belongs.
     * @param userStoryCode The identifier of the user story whose effort is to be estimated/updated.
     * @param effortValue   Represents the amount of work required to complete a user story. The
     *                      "effortValue" parameter
     *                      is an instance of this data type.
     * @return true if a user story with that effort value exists.
     */
    public boolean estimateEffort(String projectCode, String userStoryCode, Effort effortValue) {
        final UserStoryID userStoryID = new UserStoryID(new ProjectCode(projectCode), new UserStoryCode(userStoryCode));
        return userStoryService.estimateEffort(userStoryID, effortValue);
    }
}
