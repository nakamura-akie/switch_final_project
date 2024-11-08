package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataValidationExceptionTest {

    @Test
    public void testConstructor() {
        String message = "Test message";
        DataValidationException dataValidationException = new DataValidationException(message);
        assertEquals(message, dataValidationException.getMessage());
    }

    @Test
    public void testConstructor_NullMessage() {
        DataValidationException dataValidationException = new DataValidationException(null);
        assertNull(dataValidationException.getMessage());
    }
}