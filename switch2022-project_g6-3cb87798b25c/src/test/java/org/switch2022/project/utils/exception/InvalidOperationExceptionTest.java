package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidOperationExceptionTest {

    @Test
    public void Constructor() {
        String message = "Test message";
        InvalidOperationException notFoundException = new InvalidOperationException(message);
        assertEquals(message, notFoundException.getMessage());
    }

    @Test
    public void Constructor_NullMessage() {
        InvalidOperationException notFoundException = new InvalidOperationException(null);
        assertNull(notFoundException.getMessage());
    }
}