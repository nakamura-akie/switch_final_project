package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class ApiExceptionMessageTest {

    @Test
    public void GetMessage() {
        String message = "Test message";
        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage(message);
        assertEquals(message, apiExceptionMessage.getMessage());
    }

    @Test
    public void GetMessage_Null() {
        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage(null);
        assertNull(apiExceptionMessage.getMessage());
    }

    @Test
    public void GetMessage_EmptyString() {
        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage("");
        assertEquals("", apiExceptionMessage.getMessage());
    }
}