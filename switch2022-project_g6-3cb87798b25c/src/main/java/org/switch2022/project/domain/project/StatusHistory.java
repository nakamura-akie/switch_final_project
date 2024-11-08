package org.switch2022.project.domain.project;

import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectStatus;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

/**
 * The StatusHistory class represents the status history of a project.
 */
public class StatusHistory {

    private final ProjectCode projectCode;
    private final Map<ProjectStatus, LocalDate> statusHistoryMap;

    /**
     * Constructs a StatusHistory object with the specified project code.
     *
     * @param projectCode the project code
     * @throws IllegalArgumentException if the project code is null
     */
    public StatusHistory(ProjectCode projectCode) {
        if (projectCode == null) {
            throw new IllegalArgumentException("Project Code cannot be null");
        }

        this.projectCode = projectCode;
        this.statusHistoryMap = new EnumMap<>(ProjectStatus.class);
    }

    /**
     * Constructs a StatusHistory object with the specified project code and status history map.
     *
     *  @param projectCode the project code
     * @param statusHistoryMap the status history map
     * @throws IllegalArgumentException if the project code or status history map is null
     */
    public StatusHistory(ProjectCode projectCode, Map<ProjectStatus, LocalDate> statusHistoryMap) {
        if (projectCode == null) {
            throw new IllegalArgumentException("Project Code cannot be null");
        }

        if (statusHistoryMap == null) {
            throw new IllegalArgumentException("Status History Map cannot be null");
        }

        this.projectCode = projectCode;
        this.statusHistoryMap = statusHistoryMap;

    }

    /**
     * Records the specified project status with the current date.
     *
     * @param status the project status to record
     */
    public void record(ProjectStatus status) {
        statusHistoryMap.put(status, LocalDate.now());
    }

    /**
     * Returns the status history map.
     *
     * @return the status history map
     */
    public Map<ProjectStatus, LocalDate> getStatusHistoryMap() {
        return statusHistoryMap;
    }
}
