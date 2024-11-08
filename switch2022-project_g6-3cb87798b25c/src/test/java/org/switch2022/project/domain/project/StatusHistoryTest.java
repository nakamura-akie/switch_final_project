package org.switch2022.project.domain.project;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class StatusHistoryTest {

    @Test
    void constructor_NullProjectCode_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new StatusHistory(null));

        assertEquals("Project Code cannot be null", exception.getMessage());
    }

    @Test
    void constructorWithTwoArguments_NullProjectCode_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new StatusHistory(null, Collections.emptyMap()));

        assertEquals("Project Code cannot be null", exception.getMessage());
    }

    @Test
    void constructorWithTwoArguments_NullStatusHistoryMap_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new StatusHistory(mock(ProjectCode.class), null));

        assertEquals("Status History Map cannot be null", exception.getMessage());
    }

    @Test
    void getStatusHistoryMap_EmptyStatusHistory_EmptyList() {
        StatusHistory statusHistory = new StatusHistory(mock(ProjectCode.class));

        Map<ProjectStatus, LocalDate> expected = new HashMap<>();

        Map<ProjectStatus, LocalDate> result = statusHistory.getStatusHistoryMap();

        assertEquals(expected, result);
    }

    @Test
    void getStatusHistoryMap_StatusHistoryWithEntries_EmptyList() {
        Map<ProjectStatus, LocalDate> statusHistoryMap = Map.of(mock(ProjectStatus.class), mock(LocalDate.class));
        StatusHistory statusHistory = new StatusHistory(mock(ProjectCode.class), statusHistoryMap);

        Map<ProjectStatus, LocalDate> expected = statusHistoryMap;

        Map<ProjectStatus, LocalDate> result = statusHistory.getStatusHistoryMap();

        assertEquals(expected, result);
    }

    @Test
    void record() {
        Map<ProjectStatus, LocalDate> statusHistoryMap = Map.of(ProjectStatus.PLANNED, LocalDate.now());

        Map<ProjectStatus, LocalDate> expected = statusHistoryMap;

        StatusHistory statusHistory = new StatusHistory(mock(ProjectCode.class));
        statusHistory.record(ProjectStatus.PLANNED);
        Map<ProjectStatus, LocalDate> result = statusHistory.getStatusHistoryMap();

        assertEquals(expected, result);
    }

}