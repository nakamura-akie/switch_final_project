package org.switch2022.project.datamodel.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.projecttypology.ProjectTypologyJPA;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.projecttypology.ProjectTypologyFactory;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;

@Component
public class ProjectTypologyDomainDataAssembler {

    ProjectTypologyFactory projectTypologyFactory;

    /**
     * Instantiates a new Project Typology Domain Data Assembler.
     *
     * @param projectTypologyFactory the project typology factory
     */
    @Autowired
    public ProjectTypologyDomainDataAssembler(ProjectTypologyFactory projectTypologyFactory) {
        this.projectTypologyFactory = projectTypologyFactory;

    }

    /**
     * Assemble ProjectTypology Object into ProjectTypologyJPA Object.
     *
     * @param projectTypology the project typology
     * @return the project typology jpa
     */
    public ProjectTypologyJPA toData(ProjectTypology projectTypology) {
        ProjectTypologyName projectTypologyName = projectTypology.identity();
        String projectTypologyNameValue = projectTypologyName.getProjectTypologyNameValue();
        return new ProjectTypologyJPA(projectTypologyNameValue);
    }


    /**
     * Assemble ProjectTypologyJPA Object into ProjectTypology Object.
     *
     * @param projectTypologyJpa the project typology jpa
     * @return the project typology
     */
    public ProjectTypology toDomain(ProjectTypologyJPA projectTypologyJpa) {

        ProjectTypologyName projectTypologyName = new ProjectTypologyName(projectTypologyJpa.getProjectTypologyName());
        return projectTypologyFactory.createProjectTypology(projectTypologyName);
    }
}
