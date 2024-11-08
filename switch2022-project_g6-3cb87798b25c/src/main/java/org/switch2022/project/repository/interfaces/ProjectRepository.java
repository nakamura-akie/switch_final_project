package org.switch2022.project.repository.interfaces;

import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;

/**
 * Repository interface for managing Project entities.
 */
public interface ProjectRepository extends Repository<Project, ProjectCode> {
}
