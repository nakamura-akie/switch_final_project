package org.switch2022.project.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.assembler.ResourceDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.resource.ResourceIdJPA;
import org.switch2022.project.datamodel.jpa.resource.ResourceJPA;
import org.switch2022.project.domain.resource.Resource;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.repository.interfaces.ResourceRepository;
import org.switch2022.project.repository.jpa.ResourceRepositoryJPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResourceRepositoryPersistenceTest {

    private static ResourceDomainDataAssembler resourceDomainDataAssemblerDouble;
    private static ResourceRepositoryJPA resourceRepositoryJPADouble;
    private static ResourceRepository resourceRepositoryPersistence;
    private static List<Resource> resourceDoubleList;
    private static List<ResourceID> resourceIdList;
    private static List<ResourceIdJPA> resourceIdJpaList;
    private static List<ResourceJPA> resourceJpaDoubleList;

    @BeforeAll
    public static void init() {

        resourceDomainDataAssemblerDouble = mock(ResourceDomainDataAssembler.class);
        resourceRepositoryJPADouble = mock(ResourceRepositoryJPA.class);
        resourceRepositoryPersistence = new ResourceRepositoryPersistence(
                resourceRepositoryJPADouble,
                resourceDomainDataAssemblerDouble);

        Resource firstResourceDouble = mock(Resource.class);
        Resource secondResourceDouble = mock(Resource.class);
        Resource thirdResourceDouble = mock(Resource.class);
        resourceDoubleList = List.of(firstResourceDouble, secondResourceDouble, thirdResourceDouble);

        ResourceJPA firstResourceJpaDouble = mock(ResourceJPA.class);
        ResourceJPA secondResourceJpaDouble = mock(ResourceJPA.class);
        ResourceJPA thirdResourceJpaDouble = mock(ResourceJPA.class);
        resourceJpaDoubleList = List.of(firstResourceJpaDouble, secondResourceJpaDouble,
                thirdResourceJpaDouble);

        String firstResourceEmail = "john@john.com";
        String secondResourceEmail = "jane@jane.com";
        String thirdResourceEmail = "jack@jack.com";
        String resourceProjectCode = "P1";
        String resourceAllocationStartDate = "2021-03-01";
        String resourceAllocationEndDate = "2022-04-01";

        ResourceID firstResourceID = new ResourceID(
                new Email(firstResourceEmail),
                new ProjectCode(resourceProjectCode),
                new TimePeriod(
                        LocalDate.parse(resourceAllocationStartDate),
                        LocalDate.parse(resourceAllocationEndDate)));

        ResourceID secondResourceID = new ResourceID(
                new Email(secondResourceEmail),
                new ProjectCode(resourceProjectCode),
                new TimePeriod(
                        LocalDate.parse(resourceAllocationStartDate),
                        LocalDate.parse(resourceAllocationEndDate)));

        ResourceID thirdResourceID = new ResourceID(
                new Email(thirdResourceEmail),
                new ProjectCode(resourceProjectCode),
                new TimePeriod(
                        LocalDate.parse(resourceAllocationStartDate),
                        LocalDate.parse(resourceAllocationEndDate)));
        resourceIdList = List.of(firstResourceID, secondResourceID, thirdResourceID);

        ResourceIdJPA firstResourceIdJPA = new ResourceIdJPA(
                firstResourceEmail,
                resourceProjectCode,
                resourceAllocationStartDate,
                resourceAllocationEndDate);

        ResourceIdJPA secondResourceIdJPA = new ResourceIdJPA(
                secondResourceEmail,
                resourceProjectCode,
                resourceAllocationStartDate,
                resourceAllocationEndDate);

        ResourceIdJPA thirdResourceIdJPA = new ResourceIdJPA(
                thirdResourceEmail,
                resourceProjectCode,
                resourceAllocationStartDate,
                resourceAllocationEndDate);
        resourceIdJpaList = List.of(firstResourceIdJPA, secondResourceIdJPA, thirdResourceIdJPA);

        when(resourceDomainDataAssemblerDouble.toData(resourceDoubleList.get(0))).thenReturn(resourceJpaDoubleList.get(0));
        when(resourceDomainDataAssemblerDouble.toData(resourceDoubleList.get(1))).thenReturn(resourceJpaDoubleList.get(1));
        when(resourceDomainDataAssemblerDouble.toData(resourceDoubleList.get(2))).thenReturn(resourceJpaDoubleList.get(2));

        when(resourceDomainDataAssemblerDouble.toDomain(resourceJpaDoubleList.get(0))).thenReturn(resourceDoubleList.get(0));
        when(resourceDomainDataAssemblerDouble.toDomain(resourceJpaDoubleList.get(1))).thenReturn(resourceDoubleList.get(1));
        when(resourceDomainDataAssemblerDouble.toDomain(resourceJpaDoubleList.get(2))).thenReturn(resourceDoubleList.get(2));


    }

    @Test
    void Constructor_ResourceDomainDataAssembler_ThrowsException() {
        String expected = "ResourceDomainDataAssembler cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ResourceRepositoryPersistence(resourceRepositoryJPADouble, null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void Constructor_ResourceRepositoryJPA_ThrowsException() {
        String expected = "ResourceRepositoryJPA cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ResourceRepositoryPersistence(null, resourceDomainDataAssemblerDouble);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void save_SuccessfullySaveToPersistence_Resource() {
        when(resourceRepositoryJPADouble.save(resourceJpaDoubleList.get(0))).thenReturn(resourceJpaDoubleList.get(0));

        Resource result = resourceRepositoryPersistence.save(resourceDoubleList.get(0));

        assertEquals(resourceDoubleList.get(0), result);
    }

    @Test
    void findAll_RetrieveAllResourcesInPersistence_Equals() {
        when(resourceRepositoryJPADouble.findAll()).thenReturn(resourceJpaDoubleList);

        Iterable<Resource> expected = resourceDoubleList;
        Iterable<Resource> result = resourceRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findAll_EmptyPersistence_Equals() {
        ResourceRepositoryPersistence emptyPersistenceRepository = mock(ResourceRepositoryPersistence.class);
        Iterable<Resource> expected = List.of();
        Iterable<Resource> result = emptyPersistenceRepository.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById_RetrieveResourceById_Equals() {
        when(resourceRepositoryJPADouble.findByResourceIDJpa(resourceIdJpaList.get(0))).thenReturn(Optional.of(resourceJpaDoubleList.get(0)));

        Optional<Resource> expected = Optional.of(resourceDoubleList.get(0));
        Optional<Resource> result = resourceRepositoryPersistence.findById(resourceIdList.get(0));

        assertEquals(expected, result);
    }

    @Test
    void findById_ResourceDoesNotExist_EmptyOptional() {
        when(resourceRepositoryJPADouble.findByResourceIDJpa(any())).thenReturn(Optional.empty());

        Optional<Resource> expected = Optional.empty();
        Optional<Resource> result = resourceRepositoryPersistence.findById(resourceIdList.get(2));

        assertEquals(expected, result);
    }

    @Test
    void existsById_ResourceExists_True() {
        when(resourceRepositoryJPADouble.existsByResourceIDJpa(resourceIdJpaList.get(1))).thenReturn(true);

        assertTrue(resourceRepositoryPersistence.existsById(resourceIdList.get(1)));
    }

    @Test
    void existsById_ResourceDoesNotExists_False() {
        when(resourceRepositoryJPADouble.existsByResourceIDJpa(any())).thenReturn(false);

        assertFalse(resourceRepositoryPersistence.existsById(resourceIdList.get(1)));
    }

    @Test
    void deleteAll_DeleteAllEntriesInResourcePersistence_Void() {
        resourceRepositoryPersistence.deleteAll();
        verify(resourceRepositoryJPADouble).deleteAll();
    }

    @Test
    void deleteById_DeleteResourceFromResourcePersistence_void() {
        resourceRepositoryPersistence.deleteById(resourceIdList.get(0));
        verify(resourceRepositoryJPADouble).deleteById(resourceIdJpaList.get(0));
    }

    @Test
    void findByEmail_RetrieveResourcesWithGivenEmail_Equals() {
        Email resourceEmail = new Email("jack@jack.com");
        List<ResourceJPA> listOfResourceJpaWithGivenEmail = new ArrayList<>();
        List<Resource> expected = new ArrayList<>();

        expected.add(resourceDoubleList.get(2));
        listOfResourceJpaWithGivenEmail.add(resourceJpaDoubleList.get(2));

        when(resourceRepositoryJPADouble
                .findByResourceIDJpaEmail(resourceEmail.getEmailValue()))
                .thenReturn(listOfResourceJpaWithGivenEmail);

        List<Resource> result = (List<Resource>) resourceRepositoryPersistence.findByEmail(resourceEmail);
        assertEquals(expected, result);
    }

    @Test
    void findByEmail_ResourceDoesNotExist_Equals() {
        Email resourceEmail = new Email("jasmin@jasmin.com");
        List<ResourceJPA> listOfResourceJpaWithGivenEmail = List.of();
        List<Resource> expected = List.of();

        when(resourceRepositoryJPADouble
                .findByResourceIDJpaEmail(resourceEmail.getEmailValue()))
                .thenReturn(listOfResourceJpaWithGivenEmail);

        List<Resource> result = (List<Resource>) resourceRepositoryPersistence.findByEmail(resourceEmail);
        assertEquals(expected, result);
    }

    @Test
    void findByProjectCodeAndProjectRole_RetrieveResourcesWithGivenProjectCodeAndProjectRole_Equals() {
        ProjectCode resourceProjectCode = new ProjectCode("P1");
        ProjectRole resourceProjectRole = ProjectRole.TEAM_MEMBER;

        List<ResourceJPA> listOfResourceJpaWithGivenProjectCodeAndProjectRole = new ArrayList<>();
        List<Resource> expected = new ArrayList<>();

        listOfResourceJpaWithGivenProjectCodeAndProjectRole.add(resourceJpaDoubleList.get(1));
        listOfResourceJpaWithGivenProjectCodeAndProjectRole.add(resourceJpaDoubleList.get(2));

        expected.add(resourceDoubleList.get(1));
        expected.add(resourceDoubleList.get(2));

        when(resourceRepositoryJPADouble
                .findByResourceIDJpaProjectCodeAndProjectRole(
                        resourceProjectCode.getProjectCodeValue(),
                        resourceProjectRole.name()))
                .thenReturn(listOfResourceJpaWithGivenProjectCodeAndProjectRole);

        List<Resource> result =
                (List<Resource>) resourceRepositoryPersistence
                        .findByProjectCodeAndProjectRole(
                                resourceProjectCode,
                                resourceProjectRole);

        assertEquals(expected, result);
    }

    @Test
    void findByProjectCodeAndProjectRole_NoResourceExistsWithGivenProjectCodeAndProjectRole_Equals() {
        ProjectCode resourceProjectCode = new ProjectCode("P5");
        ProjectRole resourceProjectRole = ProjectRole.TEAM_MEMBER;

        List<ResourceJPA> listOfResourceJpaWithGivenProjectCodeAndProjectRole = List.of();
        List<Resource> expected = List.of();

        when(resourceRepositoryJPADouble
                .findByResourceIDJpaProjectCodeAndProjectRole(
                        resourceProjectCode.getProjectCodeValue(),
                        resourceProjectRole.name()))
                .thenReturn(listOfResourceJpaWithGivenProjectCodeAndProjectRole);

        List<Resource> result =
                (List<Resource>) resourceRepositoryPersistence
                        .findByProjectCodeAndProjectRole(
                                resourceProjectCode,
                                resourceProjectRole);

        assertEquals(expected, result);
    }

    @Test
    void findByEmail_RetrieveResourcesWithProjectCode_Equals() {
        ProjectCode resourceProjectCode = new ProjectCode("P1");
        List<ResourceJPA> listOfResourceJpaWithGivenProjectCode = new ArrayList<>();
        List<Resource> expected = resourceDoubleList;

        listOfResourceJpaWithGivenProjectCode.add(resourceJpaDoubleList.get(0));
        listOfResourceJpaWithGivenProjectCode.add(resourceJpaDoubleList.get(1));
        listOfResourceJpaWithGivenProjectCode.add(resourceJpaDoubleList.get(2));

        when(resourceRepositoryJPADouble
                .findByResourceIDJpaProjectCode(resourceProjectCode.getProjectCodeValue()))
                .thenReturn(listOfResourceJpaWithGivenProjectCode);

        List<Resource> result = (List<Resource>) resourceRepositoryPersistence.findByProjectCode(resourceProjectCode);
        assertEquals(expected, result);
    }

    @Test
    void findByEmail_NoResourcesExistWithProjectCode_Equals() {
        ProjectCode resourceProjectCode = new ProjectCode("P5");
        List<ResourceJPA> listOfResourceJpaWithGivenProjectCode = List.of();
        List<Resource> expected = List.of();

        when(resourceRepositoryJPADouble
                .findByResourceIDJpaProjectCode(resourceProjectCode.getProjectCodeValue()))
                .thenReturn(listOfResourceJpaWithGivenProjectCode);

        List<Resource> result = (List<Resource>) resourceRepositoryPersistence.findByProjectCode(resourceProjectCode);
        assertEquals(expected, result);
    }

    @Test
    void findByProjectCodeAndIsAllocatedNow_Equal() {
        ProjectCode resourceProjectCode = new ProjectCode("P1");
        List<ResourceJPA> resourceJPAList = new ArrayList<>();

        LocalDate now = LocalDate.now();
        String nowString = now.toString();

        resourceJPAList.add(resourceJpaDoubleList.get(0));
        resourceJPAList.add(resourceJpaDoubleList.get(1));
        resourceJPAList.add(resourceJpaDoubleList.get(2));

        when(resourceRepositoryJPADouble
                .findAllocatedResourcesByProjectIdAndSearchTime("P1", nowString)).thenReturn(resourceJPAList);

        List<Resource> result =
                (List<Resource>) resourceRepositoryPersistence.findByProjectCodeAndIsAllocatedNow(resourceProjectCode
                        , now);
        assertEquals(resourceDoubleList, result);
    }


}