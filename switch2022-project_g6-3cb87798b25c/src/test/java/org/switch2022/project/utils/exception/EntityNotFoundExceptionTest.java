package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityNotFoundExceptionTest {

    @Test
    public void Constructor() {
        String message = "Test message";
        EntityNotFoundException notFoundException = new EntityNotFoundException(message);
        assertEquals(message, notFoundException.getMessage());
    }

    @Test
    public void Constructor_NullMessage() {
        EntityNotFoundException notFoundException = new EntityNotFoundException(null);
        assertNull(notFoundException.getMessage());
    }
}