package org.switch2022.project.utils.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.resource.ResourceFactory;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.utils.dto.NewResourceDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceMapperTest {

    private ResourceFactory resourceFactory;
    private NewResourceDTO newResourceDTO;
    private Resource newResource;

    @BeforeEach
    void init() {
        newResourceDTO = new NewResourceDTO(
                "john@john.com",
                "P1",
                "2022-01-01",
                "2022-02-02",
                "TEAM_MEMBER",
                "50",
                "2.5"
        );

        resourceFactory = mock(ResourceFactory.class);
        newResource = mock(Resource.class);

        when(resourceFactory.createResource(any(), any(), any(), any())).thenReturn(newResource);

    }

    @Test
    void createDomainObject() {
        Resource expected = newResource;
        Resource result = ResourceMapper.createDomainObject(newResourceDTO,
                resourceFactory);

        assertEquals(expected, result);
    }
}
