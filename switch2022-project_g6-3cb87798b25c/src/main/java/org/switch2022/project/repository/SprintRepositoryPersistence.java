package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.SprintIdJPA;
import org.switch2022.project.datamodel.SprintJPA;
import org.switch2022.project.datamodel.assembler.SprintDomainDataAssembler;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;
import org.switch2022.project.repository.interfaces.SprintRepository;
import org.switch2022.project.repository.jpa.SprintRepositoryJPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence implementation of the {@link SprintRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link SprintRepositoryJPA} interface to store sprints.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class SprintRepositoryPersistence implements SprintRepository {

    private final SprintRepositoryJPA jpaRepository;

    private final SprintDomainDataAssembler jpaAssembler;


    /**
     * Instanciates a new SprintRepositoryPersistence.
     *
     * @param jpaRepository the sprint JPA repository
     * @param jpaAssembler  the sprint domain data assembler
     * @throws IllegalArgumentException when either the sprint JPA repository
     *                                  or the sprint domain data assembler are null
     */
    public SprintRepositoryPersistence(SprintRepositoryJPA jpaRepository,
                                       SprintDomainDataAssembler jpaAssembler) {
        if (jpaRepository == null || jpaAssembler == null) {
            throw new IllegalArgumentException("Cannot have null fields.");

        }

        this.jpaRepository = jpaRepository;
        this.jpaAssembler = jpaAssembler;

    }

    /**
     * Saves a sprint entity.
     *
     * @param domainSprint the sprint to be saved
     * @return the saved sprint
     */
    @Override
    public Sprint save(Sprint domainSprint) {
        SprintJPA sprintJPA = jpaAssembler.toData(domainSprint);
        SprintJPA savedSprintJPA = jpaRepository.save(sprintJPA);

        return jpaAssembler.toDomain(savedSprintJPA);
    }

    /**
     * Retrieves all sprints in the JPA repository.
     *
     * @return an iterable collection of all sprints in the repository
     */
    @Override
    public Iterable<Sprint> findAll() {
        Iterable<SprintJPA> listOfJpaSprints = jpaRepository.findAll();

        List<Sprint> listOfDomainSprints = new ArrayList<>();

        for (SprintJPA sprintJPA : listOfJpaSprints) {
            Sprint domainSprint = jpaAssembler.toDomain(sprintJPA);
            listOfDomainSprints.add(domainSprint);

        }
        return listOfDomainSprints;
    }

    /**
     * Retrieves a sprint with the given id, in this case, a SprintID
     *
     * @param id the sprintID to search for
     * @return An optional containing the sprint entity if found, an empty optional otherwise
     */
    @Override
    public Optional<Sprint> findById(SprintID id) {
        ProjectCode projectCode = id.getProjectCode();
        SprintCode sprintCode = id.getSprintCode();

        SprintIdJPA jpaId = new SprintIdJPA();
        jpaId.setProjectCode(projectCode.getProjectCodeValue());
        jpaId.setSprintCode(sprintCode.toString());

        Optional<SprintJPA> maybeSprintJpa = jpaRepository.findById(jpaId);

        if (maybeSprintJpa.isPresent()) {
            SprintJPA sprintJPA = maybeSprintJpa.get();
            Sprint domainSprint = jpaAssembler.toDomain(sprintJPA);
            return Optional.of(domainSprint);
        }

        return Optional.empty();
    }

    /**
     * Confirms if a sprint exists with the given id, in this case, the SprintID.
     *
     * @param id the sprint ID to search for
     * @return true if the sprint exists, false otherwise
     */
    @Override
    public boolean existsById(SprintID id) {
        ProjectCode projectCode = id.getProjectCode();
        SprintCode sprintCode = id.getSprintCode();

        SprintIdJPA jpaId = new SprintIdJPA();
        jpaId.setProjectCode(projectCode.getProjectCodeValue());
        jpaId.setSprintCode(sprintCode.toString());

        return jpaRepository.existsById(jpaId);

    }

    /**
     * Retrieves all sprints with a given project code
     *
     * @param projectCode the project code to search for
     * @return an iterable collection of matching sprints
     */
    @Override
    public Iterable<Sprint> findByProjectCode(ProjectCode projectCode) {
        List<SprintJPA> data = (List<SprintJPA>)
                jpaRepository.findBySprintIdJPA_ProjectCode(projectCode.getProjectCodeValue());

        return (data.stream().map(jpaAssembler::toDomain).collect(Collectors.toList()));
    }

    /**
     * Retrieves an optional of a Sprint object with the given project code and start date
     *
     * @param projectCode the project code to search for
     * @param startDate   the start date of the sprint to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findBySprintIdJPA_ProjectCodeAndStartDate(ProjectCode projectCode, LocalDate startDate) {

        Optional<SprintJPA> maybeSprintJpa = jpaRepository.findBySprintIdJPA_ProjectCodeAndStartDate
                (projectCode.getProjectCodeValue(), startDate.toString());

        if (maybeSprintJpa.isPresent()) {
            SprintJPA sprintJPA = maybeSprintJpa.get();
            Sprint domainSprint = jpaAssembler.toDomain(sprintJPA);
            return Optional.of(domainSprint);
        }
        return Optional.empty();
    }

    /**
     * Retrieves an optional of a Sprint object with the given sprint ID and sprint status
     *
     * @param sprintID     the sprint ID to search for
     * @param sprintStatus the sprint status to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findBySprintIdAndSprintStatusLike(SprintID sprintID, SprintStatus sprintStatus) {
        SprintIdJPA sprintIdJPA = new SprintIdJPA(
                sprintID.getProjectCode().getProjectCodeValue(),
                sprintID.getSprintCode().getSprintCodeValue()
        );

        String status = sprintStatus.name();

        Optional<SprintJPA> optionalSprint = jpaRepository.findBySprintIdJPAAndSprintStatusLike(sprintIdJPA,
                status);
        if (optionalSprint.isPresent()) {
            SprintJPA sprintJPA = optionalSprint.get();
            Sprint domainSprint = jpaAssembler.toDomain(sprintJPA);
            return Optional.of(domainSprint);
        }
        return Optional.empty();
    }

    /**
     * Retrieves a sprint with the given project code and with the "OPENED" status.
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findOpenSprint(ProjectCode projectCode) {

        Optional<SprintJPA> maybeSprintJpa = jpaRepository
                .findBySprintIdJPA_ProjectCodeAndSprintStatus(projectCode.getProjectCodeValue(), "OPENED");

        if (maybeSprintJpa.isPresent()) {
            Sprint domainSprint = jpaAssembler.toDomain(maybeSprintJpa.get());
            return Optional.of(domainSprint);
        }
        return Optional.empty();
    }

    /**
     * Retrieves an optional the first Sprint object with the specified project code,
     * from a list ordered by the end date in descending order.
     *
     * @param projectCode the project code to search for
     * @return an Optional containing the Sprint object when it is found, or an empty Optional otherwise
     */
    @Override
    public Optional<Sprint> findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc(ProjectCode projectCode) {
        Optional<SprintJPA> maybeSprintJpa = jpaRepository.findFirstBySprintIdJPA_ProjectCodeOrderByEndDateDesc
                (projectCode.getProjectCodeValue());

        if (maybeSprintJpa.isPresent()) {
            SprintJPA sprintJPA = maybeSprintJpa.get();
            Sprint domainSprint = jpaAssembler.toDomain(sprintJPA);
            return Optional.of(domainSprint);
        }
        return Optional.empty();
    }

    /**
     * Confirms if a sprint exists with the given project code and sprint status
     *
     * @param projectCode  the project code to search for
     * @param sprintStatus the sprint status to search for
     * @return true if the sprint exists, false otherwise
     */
    @Override
    public boolean existsBySprintIdJPAProjectCodeAndSprintStatusLike(ProjectCode projectCode,
                                                                     SprintStatus sprintStatus){
        return jpaRepository.existsBySprintIdJPAProjectCodeAndSprintStatusLike(projectCode.getProjectCodeValue(),
                sprintStatus.name());
    }

    /**
     * Deletes all sprints in the JPA repository.
     */
    @Override
    public void deleteAll() {
        jpaRepository.deleteAll();
    }

    /**
     * Deletes the sprint with the given id, in this case, a SprintID.
     * @param id the sprintID to search for
     */
    @Override
    public void deleteById(SprintID id) {
        ProjectCode projectCode = id.getProjectCode();
        SprintCode sprintCode = id.getSprintCode();

        SprintIdJPA jpaId = new SprintIdJPA();
        jpaId.setProjectCode(projectCode.getProjectCodeValue());
        jpaId.setSprintCode(sprintCode.toString());

        jpaRepository.deleteById(jpaId);
    }

}