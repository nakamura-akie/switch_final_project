package org.switch2022.project.domain.businesssector;

import org.springframework.stereotype.Component;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

/**
 * The BusinessSectorFactory interface defines methods for creating BusinessSector objects.
 */
@Component
public interface BusinessSectorFactory {

    /**
     * Creates a BusinessSector object with the specified parameters.
     *
     * @param businessSectorName the business sector name
     */
    BusinessSector createBusinessSector(BusinessSectorName businessSectorName);
}
