package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectRole;
import org.switch2022.project.domain.valueobject.ResourceID;
import org.switch2022.project.repository.interfaces.ResourceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceRepositoryFakeTest {
    private final ResourceRepository resourceRepositoryFake = new ResourceRepositoryFake();

    @AfterEach
    void tearDown() {
        resourceRepositoryFake.deleteAll();
    }

    @Test
    void save_AddResourceToRepository_Equals() {
        Resource newResource = mock(Resource.class);

        Resource result = resourceRepositoryFake.save(newResource);

        assertEquals(newResource, result);
    }

    @Test
    void findAll_ReturnsAllResourcesInRepository_Equals() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        Iterable<Resource> expected = Arrays.asList(firstResource, secondResource);
        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);

        Iterable<Resource> result = resourceRepositoryFake.findAll();

        assertEquals(expected, result);
    }


    @Test
    void findAll_ReturnsEmptyIterable_Equals() {
        Iterable<Resource> expected = List.of();

        Iterable<Resource> result = resourceRepositoryFake.findAll();

        assertEquals(expected, result);
    }


    @Test
    void findByID_ReturnsOptionalOfResourceWithID_Equals() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);

        when(firstResource.identity()).thenReturn(firstResourceID);
        when(secondResource.identity()).thenReturn(secondResourceID);

        Optional<Resource> expected = Optional.of(secondResource);
        Optional<Resource> result = resourceRepositoryFake.findById(secondResourceID);

        assertEquals(expected, result);
    }

    @Test
    void findByID_ReturnsEmptyOptionalOfResource_Equals() {
        Resource firstResource = mock(Resource.class);
        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        resourceRepositoryFake.save(firstResource);

        when(firstResource.identity()).thenReturn(firstResourceID);

        Optional<Resource> expected = Optional.empty();
        Optional<Resource> result = resourceRepositoryFake.findById(secondResourceID);

        assertEquals(expected, result);
    }


    @Test
    void existsByID_ResourceExists_True() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);

        when(firstResource.identity()).thenReturn(firstResourceID);
        when(secondResource.identity()).thenReturn(secondResourceID);

        boolean result = resourceRepositoryFake.existsById(secondResourceID);

        assertTrue(result);
    }

    @Test
    void existsByID_ResourceDoesNotExist_False() {
        Resource firstResource = mock(Resource.class);
        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        resourceRepositoryFake.save(firstResource);

        when(firstResource.identity()).thenReturn(firstResourceID);

        boolean result = resourceRepositoryFake.existsById(secondResourceID);

        assertFalse(result);
    }


    @Test
    void findByProjectCodeAndProjectRole_IterableOfResourceInProjectWithProductOwnerRole_Equals() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        Resource thirdResource = mock(Resource.class);
        Resource fourthResource = mock(Resource.class);

        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);
        resourceRepositoryFake.save(thirdResource);
        resourceRepositoryFake.save(fourthResource);

        ProjectCode firstProjectCodeDouble = mock(ProjectCode.class);
        ProjectCode secondProjectCodeDouble = mock(ProjectCode.class);

        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        ResourceID thirdResourceID = mock(ResourceID.class);
        ResourceID fourthResourceID = mock(ResourceID.class);

        when(firstResource.identity()).thenReturn(firstResourceID);
        when(secondResource.identity()).thenReturn(secondResourceID);
        when(thirdResource.identity()).thenReturn(thirdResourceID);
        when(fourthResource.identity()).thenReturn(fourthResourceID);

        when(firstResourceID.getProjectCode()).thenReturn(secondProjectCodeDouble);
        when(secondResourceID.getProjectCode()).thenReturn(firstProjectCodeDouble);
        when(thirdResourceID.getProjectCode()).thenReturn(secondProjectCodeDouble);
        when(fourthResourceID.getProjectCode()).thenReturn(firstProjectCodeDouble);

        when(firstResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);
        when(secondResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);
        when(thirdResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);
        when(fourthResource.getRoleInProject()).thenReturn(ProjectRole.TEAM_MEMBER);

        Iterable<Resource> expected = Arrays.asList(secondResource, fourthResource);
        Iterable<Resource> result = resourceRepositoryFake.findByProjectCodeAndProjectRole(firstProjectCodeDouble,
                ProjectRole.TEAM_MEMBER);

        assertEquals(expected, result);
    }

    @Test
    void findByEmail_IterableOfResourceWithGivenEmail_Equals() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        Resource thirdResource = mock(Resource.class);
        Resource fourthResource = mock(Resource.class);

        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);
        resourceRepositoryFake.save(thirdResource);
        resourceRepositoryFake.save(fourthResource);

        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        ResourceID thirdResourceID = mock(ResourceID.class);
        ResourceID fourthResourceID = mock(ResourceID.class);

        Email firstResourceEmail = mock(Email.class);
        Email secondResourceEmail = mock(Email.class);
        Email fourthResourceEmail = mock(Email.class);


        when(firstResource.identity()).thenReturn(firstResourceID);
        when(secondResource.identity()).thenReturn(secondResourceID);
        when(thirdResource.identity()).thenReturn(thirdResourceID);
        when(fourthResource.identity()).thenReturn(fourthResourceID);

        when(firstResourceID.hasEmail(firstResourceEmail)).thenReturn(true);
        when(secondResourceID.hasEmail(secondResourceEmail)).thenReturn(false);
        when(thirdResourceID.hasEmail(firstResourceEmail)).thenReturn(true);
        when(fourthResourceID.hasEmail(fourthResourceEmail)).thenReturn(false);

        Iterable<Resource> expected = Arrays.asList(firstResource, thirdResource);
        Iterable<Resource> result = resourceRepositoryFake.findByEmail(firstResourceEmail);

        assertEquals(expected, result);
    }

    @Test
    void findByProjectCode_IterableOfResourceWithGivenProjectCode_Equals() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        Resource thirdResource = mock(Resource.class);
        Resource fourthResource = mock(Resource.class);

        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);
        resourceRepositoryFake.save(thirdResource);
        resourceRepositoryFake.save(fourthResource);

        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        ResourceID thirdResourceID = mock(ResourceID.class);
        ResourceID fourthResourceID = mock(ResourceID.class);

        ProjectCode firstProjectCodeDouble = mock(ProjectCode.class);
        ProjectCode secondProjectCodeDouble = mock(ProjectCode.class);

        when(firstResource.identity()).thenReturn(firstResourceID);
        when(secondResource.identity()).thenReturn(secondResourceID);
        when(thirdResource.identity()).thenReturn(thirdResourceID);
        when(fourthResource.identity()).thenReturn(fourthResourceID);

        when(firstResourceID.getProjectCode()).thenReturn(secondProjectCodeDouble);
        when(secondResourceID.getProjectCode()).thenReturn(firstProjectCodeDouble);
        when(thirdResourceID.getProjectCode()).thenReturn(secondProjectCodeDouble);
        when(fourthResourceID.getProjectCode()).thenReturn(firstProjectCodeDouble);

        Iterable<Resource> expected = Arrays.asList(secondResource, fourthResource);
        Iterable<Resource> result = resourceRepositoryFake.findByProjectCode(firstProjectCodeDouble);

        assertEquals(expected, result);
    }

    @Test
    void findByProjectCodeAndIsAllocatedNow() {
        Resource firstResource = mock(Resource.class);
        Resource secondResource = mock(Resource.class);
        Resource thirdResource = mock(Resource.class);
        Resource fourthResource = mock(Resource.class);

        resourceRepositoryFake.save(firstResource);
        resourceRepositoryFake.save(secondResource);
        resourceRepositoryFake.save(thirdResource);
        resourceRepositoryFake.save(fourthResource);

        ResourceID firstResourceID = mock(ResourceID.class);
        ResourceID secondResourceID = mock(ResourceID.class);
        ResourceID thirdResourceID = mock(ResourceID.class);
        ResourceID fourthResourceID = mock(ResourceID.class);

        ProjectCode firstProjectCodeDouble = mock(ProjectCode.class);
        ProjectCode secondProjectCodeDouble = mock(ProjectCode.class);

        when(firstResource.identity()).thenReturn(firstResourceID);
        when(secondResource.identity()).thenReturn(secondResourceID);
        when(thirdResource.identity()).thenReturn(thirdResourceID);
        when(fourthResource.identity()).thenReturn(fourthResourceID);

        when(firstResourceID.getProjectCode()).thenReturn(secondProjectCodeDouble);
        when(secondResourceID.getProjectCode()).thenReturn(firstProjectCodeDouble);
        when(thirdResourceID.getProjectCode()).thenReturn(secondProjectCodeDouble);
        when(fourthResourceID.getProjectCode()).thenReturn(firstProjectCodeDouble);

        LocalDate searchDate = LocalDate.now();

        when(firstResource.isAllocated(searchDate)).thenReturn(true);
        when(secondResource.isAllocated(searchDate)).thenReturn(true);
        when(thirdResource.isAllocated(searchDate)).thenReturn(false);
        when(fourthResource.isAllocated(searchDate)).thenReturn(false);


        Iterable<Resource> expected = List.of(secondResource);
        Iterable<Resource> result = resourceRepositoryFake.findByProjectCodeAndIsAllocatedNow(firstProjectCodeDouble,
                searchDate);
        assertEquals(expected, result);
    }

    @Test
    void findByAccountEmailAndCurrentDate_ReturnsEmptyListWhenNoMatchingResources() {
        Email accountEmail = new Email("test@test.com");
        LocalDate currentDate = LocalDate.of(2023, 1, 1);

        Iterable<Resource> result = resourceRepositoryFake.findByAccountEmailAndCurrentDate(accountEmail, currentDate);

        List<Resource> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(0, resultList.size());
    }
}
