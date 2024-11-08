package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.jpa.project.ProjectJPA;

/**
 * Repository interface for managing ProjectJPA entities in the database.
 */
public interface ProjectRepositoryJPA extends CrudRepository<ProjectJPA, String> {
}
