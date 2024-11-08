package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.dto.OutputProjectDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The OutputProjectAssembler class is responsible for converting projects into OutputProjectDTOs.
 */
public final class OutputProjectAssembler {

    /**
     * Private constructor to prevent instantiation of the OutputProjectAssembler class.
     */
    private OutputProjectAssembler() {
    }

    /**
     * Generates an OutputProjectDTO object from the given Project object.
     * If the sprint duration, number of planned sprints, budget, start date or end date are not defined in
     * the project object, the corresponding attributes will be set as null in the OutputProjectDTO
     *
     * @param project the Project object
     * @return the OutputProjectDTO object
     */
    public static OutputProjectDTO generateDTO(Project project) {

        String projectCode = project.getProjectCode().getProjectCodeValue();

        String projectName = project.getProjectName().getProjectNameValue();

        String description = project.getDescription().getDescriptionValue();

        String customerID = project.getCustomerID().getTaxIdentificationNumber();

        String businessSectorName = project.getBusinessSectorName().getBusinessSectorNameValue();

        String projectTypologyName = project.getProjectTypologyName().getProjectTypologyNameValue();

        List<String> productBacklog = project.getProductBacklog().getUserStorylist().stream()
                .map(UserStoryID::getUserStoryCode)
                .map(UserStoryCode::getUserStoryCodeValue)
                .collect(Collectors.toList());

        String status = project.getProjectStatus().toString();

        Map<String, String> statusHistory = project.getStatusHistory().getStatusHistoryMap().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));

        Integer sprintDuration =
                project.getSprintDuration().map(SprintDuration::getSprintDurationValue).orElse(null);

        Integer numberPlannedSprints =
                project.getNumberPlannedSprints().map(NumberPlannedSprints::getNumberOfPlannedSprintsValue)
                        .orElse(null);

        Double budget = project.getBudget().map(Budget::getBudgetValue).orElse(null);

        LocalDate projectStartDate = project.getPeriod().map(TimePeriod::getStartDate).orElse(null);

        LocalDate projectEndDate = project.getPeriod()
                .map(TimePeriod::getEndDate)
                .filter(endDate -> !endDate.equals(LocalDate.MAX))
                .orElse(null);

        return new OutputProjectDTO(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                status,
                statusHistory,
                sprintDuration,
                numberPlannedSprints,
                budget,
                projectStartDate,
                projectEndDate
        );
    }
}
