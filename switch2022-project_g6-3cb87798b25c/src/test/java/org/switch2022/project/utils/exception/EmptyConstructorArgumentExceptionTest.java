package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyConstructorArgumentExceptionTest {

    @Test
    public void testConstructor() {
        String message = "Test message";
        EmptyConstructorArgumentException exception = new EmptyConstructorArgumentException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testConstructor_EmptyMessage() {
        EmptyConstructorArgumentException exception = new EmptyConstructorArgumentException(null);
        assertNull(exception.getMessage());
    }


}