package org.switch2022.project.datamodel.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.SprintBacklogJPA;
import org.switch2022.project.datamodel.SprintIdJPA;
import org.switch2022.project.datamodel.SprintJPA;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.UserStoryCodeListToString;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SprintDomainDataAssembler {
    private final SprintFactory sprintFactory;

    /**
     * Constructs a SprintDomainDataAssembler with the specified SprintFactory.
     *
     * @param sprintFactory the {@link SprintFactory} to be used for creating {@link Sprint} objects.
     * @throws IllegalArgumentException if the BusinessSectorFactory is null.
     */
    public SprintDomainDataAssembler(SprintFactory sprintFactory) {
        this.sprintFactory = sprintFactory;
    }

    /**
     * Converts a Sprint domain object to its corresponding {@link SprintJPA} entity.
     *
     * @param sprint the Sprint domain object to be converted.
     * @return the corresponding JPA entity.
     */
    public SprintJPA toData(Sprint sprint) {
        String projectCode = sprint.getProjectCode().getProjectCodeValue();
        String sprintCode = sprint.getSprintCode().getSprintCodeValue();

        SprintIdJPA sprintIdJPA = new SprintIdJPA(projectCode, sprintCode);

        SprintBacklogJPA sprintBacklogJPA = new SprintBacklogJPA(
                sprintIdJPA,
                sprint.getIDsOfUserStoriesInSprintBacklog().stream()
                        .map(UserStoryID::getUserStoryCode)
                        .map(UserStoryCode::getUserStoryCodeValue)
                        .collect(Collectors.joining(",", "{", "}"))
        );

        String startDate = sprint.getStartDate().toString();
        String endDate = sprint.getEndDate().toString();
        String sprintStatus = sprint.getSprintStatus().name();

        Integer sprintDuration = sprint.getSprintDuration().getSprintDurationValue();

        return new SprintJPA(sprintIdJPA, startDate, sprintDuration, endDate, sprintBacklogJPA, sprintStatus);
    }

    /**
     * Converts a SprintJPA entity to its corresponding {@link Sprint} domain object.
     *
     * @param sprintJPA the JPA entity to be converted.
     * @return the corresponding Sprint domain object.
     */
    public Sprint toDomain(SprintJPA sprintJPA) {
        ProjectCode projectCode = new ProjectCode(sprintJPA.getSprintIdJPA().getProjectCodeValue());
        SprintCode sprintCode = new SprintCode(sprintJPA.getSprintIdJPA().getSprintCode());

        SprintID sprintID = new SprintID(projectCode, sprintCode);

        LocalDate startDate = LocalDate.parse(sprintJPA.getStartDate());
        LocalDate endDate = LocalDate.parse(sprintJPA.getEndDate());
        SprintStatus sprintStatus = SprintStatus.valueOf(sprintJPA.getSprintStatus());

        SprintDuration sprintDuration = new SprintDuration(sprintJPA.getSprintDuration());

        List<UserStoryCode> userStoryCodeList =
                UserStoryCodeListToString.parseUserStoryCodeList(
                        sprintJPA.getSprintBacklogJPA().getUserStoryCodeList());

        return sprintFactory.createSprint(
                sprintID,
                startDate,
                sprintDuration,
                endDate,
                userStoryCodeList.stream()
                        .map(userStoryCode -> new UserStoryID(projectCode, userStoryCode))
                        .collect(Collectors.toCollection(LinkedList::new)),
                sprintStatus
        );

    }

}