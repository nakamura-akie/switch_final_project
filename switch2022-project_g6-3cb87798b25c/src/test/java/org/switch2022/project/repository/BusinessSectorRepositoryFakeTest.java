package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessSectorRepositoryFakeTest {

    List<BusinessSector> businessSectorList;
    private BusinessSector financeSector;
    private BusinessSector technologySector;
    private BusinessSectorName financeSectorName;
    private BusinessSectorName technologySectorName;
    private BusinessSectorRepositoryFake businessSectorRepository;

    @BeforeEach
    void setUp() {
        this.financeSector = mock(BusinessSector.class);
        this.technologySector = mock(BusinessSector.class);

        this.financeSectorName = new BusinessSectorName("Finance");
        when(financeSector.identity()).thenReturn(financeSectorName);

        this.technologySectorName = new BusinessSectorName("Technology");
        when(technologySector.identity()).thenReturn(technologySectorName);

        this.businessSectorList = new ArrayList<>();
        businessSectorList.add(financeSector);

        businessSectorRepository = new BusinessSectorRepositoryFake();
        this.businessSectorRepository.save(financeSector);
    }

    @AfterEach
    void tearDown() {
        businessSectorList = null;

        financeSector = null;
        financeSectorName = null;

        technologySector = null;
        technologySectorName = null;

        businessSectorRepository.deleteAll();
        businessSectorRepository = null;
    }

    @Test
    void save_saveBusinessSector_True() {
        BusinessSector result = businessSectorRepository.save(technologySector);

        assertEquals(technologySector, result);
    }

    @Test
    void save_saveBusinessSectorAlreadyPresentInRepo_ThrowsException() {


        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                businessSectorRepository.save(financeSector));

        assertEquals("Business Sector already exists.", exception.getMessage());
    }

    @Test
    void findAll_returnListWithAllBusinessSectors_Equals() {
        Iterable<BusinessSector> expected = businessSectorList;
        Iterable<BusinessSector> result = businessSectorRepository.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById_returnBusinessSectorByValidId_Equals() {
        Optional<BusinessSector> expected = Optional.of(financeSector);
        Optional<BusinessSector> result = businessSectorRepository.findById(financeSectorName);

        assertEquals(expected, result);
    }

    @Test
    void findById_returnsEmptyOptionalByInvalidId_Equals() {
        Optional<BusinessSector> expected = Optional.empty();
        Optional<BusinessSector> result = businessSectorRepository.findById(technologySectorName);

        assertEquals(expected, result);
    }

    @Test
    void existsById_searchBusinessSectorByValidId_Equals() {
        boolean result = businessSectorRepository.existsById(financeSectorName);

        assertTrue(result);
    }

    @Test
    void existsById_businessSectorValidId_NotEquals() {
        boolean result = businessSectorRepository.existsById(technologySectorName);

        assertFalse(result);
    }

    @Test
    void deleteAll_RepoIsEmptyAfterDeleteAll_True() {
        businessSectorRepository.deleteAll();

        Iterable<BusinessSector> expected = new ArrayList<>();
        Iterable<BusinessSector> result = businessSectorRepository.findAll();

        assertEquals(expected, result);
        /*List<BusinessSector> populatedList = (List<BusinessSector>) businessSectorRepository.findAll();
        assertFalse(populatedList.isEmpty());
        businessSectorList.clear();
        businessSectorRepository.deleteAll();
        List<BusinessSector> emptyList = (List<BusinessSector>) businessSectorRepository.findAll();
        assertTrue(emptyList.isEmpty());*/
    }

    @Test
    void deleteById_deleteWithUnsupportedMethod_Throws() {
        BusinessSectorName id = financeSectorName;
        Exception exception = assertThrows(UnsupportedOperationException.class, () ->
                businessSectorRepository.deleteById(id));

        assertEquals("Business Sector Repository doesn't support the deleteByID method.", exception.getMessage());
    }
}






