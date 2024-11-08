package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullConstructorArgumentExceptionTest {

    @Test
    public void testConstructor() {
        String message = "Test message";
        NullConstructorArgumentException exception = new NullConstructorArgumentException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testConstructor_NullMessage() {
        NullConstructorArgumentException exception = new NullConstructorArgumentException(null);
        assertNull(exception.getMessage());
    }
}