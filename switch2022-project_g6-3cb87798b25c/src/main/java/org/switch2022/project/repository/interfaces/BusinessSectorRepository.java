package org.switch2022.project.repository.interfaces;

import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

/**
 * Repository interface for managing Business Sector entities.
 */
public interface BusinessSectorRepository extends Repository<BusinessSector, BusinessSectorName> {
}
