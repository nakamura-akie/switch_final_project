package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;

/**
 * The ViewSprintBacklogDTO class represents a data transfer object that holds information to find a sprint
 * to view its sprint backlog.
 * It includes the project code and sprint code.
 */
public class ViewSprintBacklogDTO {

    public ProjectCode projectCode;
    public SprintCode sprintCode;
}
