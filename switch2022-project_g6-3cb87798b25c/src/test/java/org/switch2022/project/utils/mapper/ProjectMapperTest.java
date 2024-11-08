package org.switch2022.project.utils.mapper;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.utils.dto.NewProjectDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectMapperTest {

    @Test
    void createDomainObject_Project() {
        String projectCode = "P001";
        String projectName = "Project One";
        String description = "Project description";
        String customerID = "235140236";
        String businessSectorName = "Technology";
        String projectTypologyName = "Fixed-Cost";

        NewProjectDTO newProjectDTO = new NewProjectDTO(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                null,
                null,
                null,
                null,
                null
        );

        ProjectFactory projectFactoryDouble = mock(ProjectFactory.class);

        Project projectDouble = mock(Project.class);

        when(projectFactoryDouble.createProject(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(projectDouble);

        Project expected = projectDouble;

        Project result = ProjectMapper.createDomainObject(projectFactoryDouble, newProjectDTO);

        assertEquals(expected, result);
    }

}