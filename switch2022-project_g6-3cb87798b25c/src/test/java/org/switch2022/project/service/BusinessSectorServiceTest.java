package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.businesssector.BusinessSectorFactory;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.repository.BusinessSectorRepositoryFake;
import org.switch2022.project.utils.assembler.CreatedBusinessSectorAssembler;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;
import org.switch2022.project.utils.dto.NewBusinessSectorDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class BusinessSectorServiceTest {

    @Autowired
    BusinessSectorService businessSectorService;

    @MockBean
    private BusinessSectorRepositoryFake businessSectorRepositoryFakeDouble;

    @MockBean
    private BusinessSectorFactory businessSectorFactoryDouble;

    MockedStatic<CreatedBusinessSectorAssembler> businessSectorAssembler;

    @BeforeEach
    void setUp() {
        this.businessSectorAssembler = mockStatic(CreatedBusinessSectorAssembler.class);
    }

    @AfterEach
    void tearDown() {
        this.businessSectorAssembler.close();
    }

    @Test
    void constructor_NullBusinessSectorRepository_ThrowsException() {
        String expected = "Business Sector Repository cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new BusinessSectorService(null, businessSectorFactoryDouble);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullBusinessSectorFactory_ThrowsException() {
        String expected = "Business Sector Factory cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new BusinessSectorService(businessSectorRepositoryFakeDouble, null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void createBusinessSector_ValidNewBusinessSector_True() {

        BusinessSectorName businessSectorName = new BusinessSectorName("Technology");

         NewBusinessSectorDTO newBusinessSectorDTO = new NewBusinessSectorDTO();
         newBusinessSectorDTO.businessSectorName = businessSectorName;

        BusinessSector businessSector = mock(BusinessSector.class);
        when(businessSector.identity()).thenReturn(businessSectorName);

        CreatedBusinessSectorDTO savedBusinessSectorDTO = new CreatedBusinessSectorDTO();
        savedBusinessSectorDTO.businessSectorNameValue = "Technology";

        when(businessSectorRepositoryFakeDouble.existsById(businessSectorName)).thenReturn(false);
        when(businessSectorFactoryDouble.createBusinessSector(businessSectorName)).thenReturn(businessSector);
        when(businessSectorRepositoryFakeDouble.save(businessSector)).thenReturn(businessSector);
        businessSectorAssembler.when(() -> CreatedBusinessSectorAssembler.generateBusinessSectorDTO(businessSector)).thenReturn(savedBusinessSectorDTO);

        CreatedBusinessSectorDTO expected = savedBusinessSectorDTO;
        CreatedBusinessSectorDTO result = businessSectorService.createBusinessSector(newBusinessSectorDTO);

        assertEquals(expected, result);
    }

    @Test
    void createBusinessSector_ExistentBusinessSector_ThrowsException() {
        BusinessSectorName existentBusinessSector = new BusinessSectorName("Education");
        when(businessSectorRepositoryFakeDouble.existsById(existentBusinessSector)).thenReturn(true);

        NewBusinessSectorDTO newBusinessSectorDTO = new NewBusinessSectorDTO();
        newBusinessSectorDTO.businessSectorName = existentBusinessSector;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                businessSectorService.createBusinessSector(newBusinessSectorDTO));

        assertEquals("Business Sector already exists.", exception.getMessage());
    }


}







