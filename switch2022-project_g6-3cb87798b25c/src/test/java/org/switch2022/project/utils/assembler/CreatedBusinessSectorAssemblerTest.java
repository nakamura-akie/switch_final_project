package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreatedBusinessSectorAssemblerTest {

    private static BusinessSector businessSector;
    private static BusinessSectorName businessSectorName;

    @BeforeAll
    static void setUp() {
        businessSector = mock(BusinessSector.class);
        businessSectorName = new BusinessSectorName("Hospitality");

        when(businessSector.identity()).thenReturn(businessSectorName);
    }

    @AfterAll
    static void tearDown() {
        businessSector = null;
        businessSectorName = null;
    }

    @Test
    void protectedConstructor_ValidEmptyConstructor_True() {
        CreatedBusinessSectorAssembler businessSectorAssembler = new CreatedBusinessSectorAssembler();
        assertTrue(businessSectorAssembler instanceof CreatedBusinessSectorAssembler);
    }

    @Test
    void generateBusinessSectorDTO_CreatesBusinessSectorDTO_True() {
        CreatedBusinessSectorDTO createdBusinessSectorDTO = new CreatedBusinessSectorDTO();
        createdBusinessSectorDTO.businessSectorNameValue = businessSectorName.getBusinessSectorNameValue();

        CreatedBusinessSectorDTO result = CreatedBusinessSectorAssembler.generateBusinessSectorDTO(businessSector);

        assertEquals(createdBusinessSectorDTO, result);
    }
}