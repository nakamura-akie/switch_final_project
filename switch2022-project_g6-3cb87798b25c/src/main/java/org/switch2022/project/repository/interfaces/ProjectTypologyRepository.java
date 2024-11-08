package org.switch2022.project.repository.interfaces;

import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;

/**
 * Repository interface for managing Project Typology entities.
 */
public interface ProjectTypologyRepository extends Repository<ProjectTypology, ProjectTypologyName> {
}
