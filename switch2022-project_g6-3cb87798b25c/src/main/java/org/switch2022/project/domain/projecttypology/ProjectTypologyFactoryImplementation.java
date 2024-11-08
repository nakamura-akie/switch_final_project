package org.switch2022.project.domain.projecttypology;

import org.springframework.stereotype.Component;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;

/**
 * The ProjectTypologyFactoryImplementation class is an implementation of the ProjectTypologyFactory interface.
 */
@Component
public class ProjectTypologyFactoryImplementation implements ProjectTypologyFactory {

    /**
     * Creates a new Project Typology instance.
     *
     * @param projectTypologyName the project typology name
     * @return the created Project Typology instance
     */
    @Override
    public ProjectTypology createProjectTypology(ProjectTypologyName projectTypologyName) {
        return new ProjectTypology(projectTypologyName);
    }
}
