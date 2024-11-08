package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

/**
 * The OutputProjectTypologyAssembler class is responsible for converting Project Typology objects
 * into OutputProjectTypologyDTOs.
 */
public final class OutputProjectTypologyAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputProjectTypologyAssembler class.
     */
    private OutputProjectTypologyAssembler() {
    }

    /**
     * Creates an OutputProjectTypologyDTO object from a given Profile Typology object.
     *
     * @param projectTypology the project typology object
     * @return the generated OutputProjectTypologyDTO
     */
    public static OutputProjectTypologyDTO generateProjectTypologyDTO(ProjectTypology projectTypology) {
        ProjectTypologyName projectTypologyName = projectTypology.identity();

        String projectTypologyValue = projectTypologyName.getProjectTypologyNameValue();

        OutputProjectTypologyDTO outputProjectTypologyDTO = new OutputProjectTypologyDTO();

        outputProjectTypologyDTO.projectTypologyValue = projectTypologyValue;

        return outputProjectTypologyDTO;
    }
}
