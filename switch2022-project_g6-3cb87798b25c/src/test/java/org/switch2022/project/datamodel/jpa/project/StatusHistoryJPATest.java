package org.switch2022.project.datamodel.jpa.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusHistoryJPATest {

    @Test
    void getProjectCode_ProjectCode() {
        StatusHistoryJPA statusHistoryJPA = new StatusHistoryJPA("P001", "PLANNED", "2023-01-01");

        String expected = "P001";

        String result = statusHistoryJPA.getProjectCode();

        assertEquals(expected, result);
    }

    @Test
    void getStatus_Status() {
        StatusHistoryJPA statusHistoryJPA = new StatusHistoryJPA("P001", "PLANNED", "2023-01-01");

        String expected = "PLANNED";

        String result = statusHistoryJPA.getStatus();

        assertEquals(expected, result);
    }

    @Test
    void getDate_Date() {
        StatusHistoryJPA statusHistoryJPA = new StatusHistoryJPA("P001", "PLANNED", "2023-01-01");

        String expected = "2023-01-01";

        String result = statusHistoryJPA.getDate();

        assertEquals(expected, result);
    }
}