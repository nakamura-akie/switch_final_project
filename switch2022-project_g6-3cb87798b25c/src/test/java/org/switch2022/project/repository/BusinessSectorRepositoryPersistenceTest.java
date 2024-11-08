package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.BusinessSectorJPA;
import org.switch2022.project.datamodel.assembler.BusinessSectorDomainDataAssembler;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.repository.jpa.BusinessSectorRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusinessSectorRepositoryPersistenceTest {

    private static BusinessSectorRepositoryPersistence businessSectorRepositoryPersistence;
    private static BusinessSectorRepositoryJPA businessSectorRepositoryJPADouble;
    private static BusinessSectorDomainDataAssembler businessSectorDomainDataAssemblerDouble;
    private static BusinessSector businessSectorDouble;
    private static BusinessSectorJPA businessSectorJPADouble;
    private static BusinessSectorName businessSectorNameDouble;

    @BeforeEach
    void setUp() {
        businessSectorRepositoryJPADouble = mock(BusinessSectorRepositoryJPA.class);

        businessSectorDomainDataAssemblerDouble = mock(BusinessSectorDomainDataAssembler.class);

        businessSectorRepositoryPersistence =
                new BusinessSectorRepositoryPersistence(businessSectorRepositoryJPADouble,
                        businessSectorDomainDataAssemblerDouble);

        businessSectorDouble = mock(BusinessSector.class);

        businessSectorJPADouble = mock(BusinessSectorJPA.class);

        businessSectorNameDouble = mock(BusinessSectorName.class);

        when(businessSectorDomainDataAssemblerDouble.toData(businessSectorDouble)).thenReturn(businessSectorJPADouble);
        when(businessSectorDomainDataAssemblerDouble.toDomain(businessSectorJPADouble)).thenReturn(businessSectorDouble);

        when(businessSectorNameDouble.getBusinessSectorNameValue()).thenReturn("Finance");
    }

    @AfterEach
    void tearDown() {
        businessSectorRepositoryPersistence = null;
    }

    @Test
    void constructor_NullAssembler_ThrowsException() {
        String exception = assertThrows(IllegalArgumentException.class,
                () -> new BusinessSectorRepositoryPersistence(businessSectorRepositoryJPADouble, null)).getMessage();

        assertEquals("Business Sector Repository JPA and Business Sector Assembler cannot be null.", exception);
    }

    @Test
    void constructor_NullBusinessSectorJPARepository_ThrowsException() {
        String exception = assertThrows(IllegalArgumentException.class,
                () -> new BusinessSectorRepositoryPersistence(null, businessSectorDomainDataAssemblerDouble)).getMessage();

        assertEquals("Business Sector Repository JPA and Business Sector Assembler cannot be null.", exception);
    }

    @Test
    void save_SaveValidBusinessSector_True() {
        BusinessSector expected = businessSectorDouble;
        when(businessSectorRepositoryJPADouble.save(businessSectorJPADouble)).thenReturn(businessSectorJPADouble);

        BusinessSector result = businessSectorRepositoryPersistence.save(businessSectorDouble);


        assertEquals(expected, result);
    }

    @Test
    void findAll_ReturnAllBusinessSectors_True() {
        List<BusinessSectorJPA> businessSectorJPAList = new ArrayList<>();
        businessSectorJPAList.add(businessSectorJPADouble);
        when(businessSectorRepositoryJPADouble.findAll()).thenReturn(businessSectorJPAList);

        List<BusinessSector> businessSectorList = new ArrayList<>();
        businessSectorList.add(businessSectorDouble);

        Iterable<BusinessSector> expected = businessSectorList;
        Iterable<BusinessSector> result = businessSectorRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById_FindExistingBusinessSector_True() {
        when(businessSectorRepositoryJPADouble.findById("Finance")).thenReturn(Optional.of(businessSectorJPADouble));

        Optional<BusinessSector> expected = Optional.of(businessSectorDouble);
        Optional<BusinessSector> result = businessSectorRepositoryPersistence.findById(businessSectorNameDouble);

        assertEquals(expected, result);
    }

    @Test
    void findById_FindNonexistentBusinessSector_EmptyOptional() {
        BusinessSectorName technology = mock(BusinessSectorName.class);

        Optional<BusinessSector> expected = Optional.empty();
        Optional<BusinessSector> result = businessSectorRepositoryPersistence.findById(technology);

        assertEquals(expected, result);
    }

    @Test
    void existsById_SearchForExistingBusinessSector_True() {
        when(businessSectorRepositoryJPADouble.existsById("Finance")).thenReturn(true);

        boolean result = businessSectorRepositoryPersistence.existsById(businessSectorNameDouble);

        assertTrue(result);
    }

    @Test
    void existsById_SearchForNonexistentBusinessSector_False() {
        when(businessSectorRepositoryJPADouble.existsById("Technology")).thenReturn(false);

        boolean result = businessSectorRepositoryPersistence.existsById(businessSectorNameDouble);

        assertFalse(result);
    }

    @Test
    void deleteAll() {
        businessSectorRepositoryPersistence.deleteAll();
        verify(businessSectorRepositoryJPADouble).deleteAll();
    }

    @Test
    void deleteById() {
        BusinessSectorName businessSectorName = new BusinessSectorName("Delete me");

        businessSectorRepositoryPersistence.deleteById(businessSectorName);

        verify(businessSectorRepositoryJPADouble).deleteById("Delete me");
    }
}