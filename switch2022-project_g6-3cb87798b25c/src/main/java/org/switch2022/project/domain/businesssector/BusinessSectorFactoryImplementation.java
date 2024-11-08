package org.switch2022.project.domain.businesssector;

import org.springframework.stereotype.Service;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

/**
 * The BusinessSectorFactoryImplementation class is an implementation of the BusinessSectorFactory interface.
 */
@Service
public class BusinessSectorFactoryImplementation implements BusinessSectorFactory {

    /**
     * Creates a BusinessSector object with the specified parameters.
     *
     * @param businessSectorName the business sector name
     */
    @Override
    public BusinessSector createBusinessSector(BusinessSectorName businessSectorName) {
        return new BusinessSector(businessSectorName);
    }
}
