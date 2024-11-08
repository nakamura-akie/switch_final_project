package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.jpa.projecttypology.ProjectTypologyJPA;

/**
 * Repository interface for managing ProjectTypologyJPA entities in the database.
 */
public interface ProjectTypologyRepositoryJPA extends CrudRepository<ProjectTypologyJPA, String> {
}
