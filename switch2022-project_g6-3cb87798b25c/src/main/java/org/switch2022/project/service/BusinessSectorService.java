package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.businesssector.BusinessSector;
import org.switch2022.project.domain.businesssector.BusinessSectorFactory;
import org.switch2022.project.domain.valueobject.BusinessSectorName;
import org.switch2022.project.utils.assembler.CreatedBusinessSectorAssembler;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;
import org.switch2022.project.utils.dto.NewBusinessSectorDTO;

@Service
public class BusinessSectorService {

    private final Repository<BusinessSector, BusinessSectorName> businessSectorRepository;

    private final BusinessSectorFactory businessSectorFactory;

    /**
     * Constructs a new instance of the BusinessSectorService class with the specified parameters.
     *
     * @param businessSectorRepository The repository used for accessing and managing business sector data.
     *                                 Must not be null.
     * @param businessSectorFactory    The factory used for creating new instances of BusinessSector objects.
     *                                 Must not be null.
     * @throws IllegalArgumentException If either businessSectorRepository or businessSectorFactory is null.
     */
    @Autowired
    public BusinessSectorService(Repository<BusinessSector, BusinessSectorName> businessSectorRepository,
                                 BusinessSectorFactory businessSectorFactory) {
        if (businessSectorRepository == null) {
            throw new IllegalArgumentException("Business Sector Repository cannot be null.");
        }
        if (businessSectorFactory == null) {
            throw new IllegalArgumentException("Business Sector Factory cannot be null.");
        }

        this.businessSectorRepository = businessSectorRepository;
        this.businessSectorFactory = businessSectorFactory;
    }

    /**
     * Creates a new business sector based on the provided information and returns a DTO (Data Transfer Object)
     * representing the created business sector.
     *
     * @param newBusinessSectorDTO The initial DTO containing the information for the new business sector.
     * @return The output DTO representing the created business sector.
     * @throws IllegalArgumentException If the business sector with the same name already exists.
     */
    public CreatedBusinessSectorDTO createBusinessSector(NewBusinessSectorDTO newBusinessSectorDTO) {
        BusinessSectorName businessSectorName = newBusinessSectorDTO.businessSectorName;

        boolean businessSectorExists = businessSectorRepository.existsById(businessSectorName);

        if (businessSectorExists) {
            throw new IllegalArgumentException("Business Sector already exists.");
        }

        BusinessSector createdBusinessSector = businessSectorFactory.createBusinessSector(businessSectorName);
        BusinessSector savedBusinessSector = businessSectorRepository.save(createdBusinessSector);

        return CreatedBusinessSectorAssembler.generateBusinessSectorDTO(savedBusinessSector);
    }
}
