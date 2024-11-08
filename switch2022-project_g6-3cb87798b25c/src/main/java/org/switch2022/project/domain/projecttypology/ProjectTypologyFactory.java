package org.switch2022.project.domain.projecttypology;

import org.switch2022.project.domain.valueobject.ProjectTypologyName;


/**
 * The ProjectTypologyFactory interface defines methods for creating Project Typology objects.
 */
public interface ProjectTypologyFactory {

    /**
     * Creates a new Project Typology instance.
     *
     * @param projectTypologyName the project typology name
     * @return the created Project Typology instance
     */
    ProjectTypology createProjectTypology(ProjectTypologyName projectTypologyName);
}
