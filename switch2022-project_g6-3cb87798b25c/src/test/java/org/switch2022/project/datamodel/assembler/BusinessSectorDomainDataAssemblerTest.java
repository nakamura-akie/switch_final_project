package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.*;
import org.switch2022.project.datamodel.BusinessSectorJPA;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.businesssector.BusinessSectorFactory;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessSectorDomainDataAssemblerTest {

    private static BusinessSectorDomainDataAssembler businessSectorDomainDataAssembler;
    private static BusinessSector businessSectorDouble;
    private static BusinessSectorJPA businessSectorJPA;

    @BeforeAll
    static void setUp() {

        businessSectorDouble = mock(BusinessSector.class);

        BusinessSectorFactory businessSectorFactoryDouble = mock(BusinessSectorFactory.class);

        businessSectorDomainDataAssembler = new BusinessSectorDomainDataAssembler(businessSectorFactoryDouble);

        String businessSectorNameString = "Finance";
        BusinessSectorName businessSectorName = new BusinessSectorName(businessSectorNameString);

        businessSectorJPA = new BusinessSectorJPA("Finance");

        when(businessSectorDouble.identity())
                .thenReturn(businessSectorName);

        when(businessSectorFactoryDouble.createBusinessSector(businessSectorName))
                .thenReturn(businessSectorDouble);
    }

    @AfterAll
    static void tearDown() {
        businessSectorDouble = null;
        businessSectorJPA = null;
        businessSectorDomainDataAssembler = null;
    }

    @Test
    void constructor_NullBusinessSectorFactory_Throws() {
        String expected = "Business Sector Factory cannot be null.";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new BusinessSectorDomainDataAssembler(null);
        }).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void toData() {
        BusinessSectorJPA result = businessSectorDomainDataAssembler.toData(businessSectorDouble);

        assertEquals(businessSectorJPA, result);
    }

    @Test
    void toDomain() {
        BusinessSector result = businessSectorDomainDataAssembler.toDomain(businessSectorJPA);

        assertEquals(businessSectorDouble, result);
    }
}