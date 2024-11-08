package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;

/**
 * The CreatedBusinessSectorAssembler class is responsible for converting BusinessSector objects
 * into CreatedBusinessSectorDTO object.
 */
public final class CreatedBusinessSectorAssembler {

    /**
     * Protected constructor to prevent instantiation of the CreatedBusinessSectorAssembler class.
     */
    protected CreatedBusinessSectorAssembler() {
    }

    /**
     * Generates a CreatedBusinessSectorDTO object from the given BusinessSector object.
     *
     * @param businessSector the BusinessSector object to convert
     * @return the generated CreatedBusinessSectorDTO object
     */
    public static CreatedBusinessSectorDTO generateBusinessSectorDTO(BusinessSector businessSector) {
        BusinessSectorName businessSectorName = businessSector.identity();
        String businessSectorNameValue = businessSectorName.getBusinessSectorNameValue();

        CreatedBusinessSectorDTO createdBusinessSectorDTO = new CreatedBusinessSectorDTO();
        createdBusinessSectorDTO.businessSectorNameValue = businessSectorNameValue;

        return createdBusinessSectorDTO;
    }
}
