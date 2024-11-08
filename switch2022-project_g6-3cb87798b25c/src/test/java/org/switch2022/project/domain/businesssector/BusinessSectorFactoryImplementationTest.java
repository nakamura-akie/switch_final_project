package org.switch2022.project.domain.businesssector;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class BusinessSectorFactoryImplementationTest {

    @Test
    void createBusinessSector_Null_ThrowsException() {
        BusinessSectorFactoryImplementation factoryImplementation = new BusinessSectorFactoryImplementation();
        BusinessSectorName businessSectorName = mock(BusinessSectorName.class);

        BusinessSector result = factoryImplementation.createBusinessSector(businessSectorName);
        BusinessSectorName resultName = result.identity();

        assertEquals(businessSectorName, resultName);
    }
}