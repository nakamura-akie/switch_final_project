package org.switch2022.project.utils.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiExceptionHandlerTest {
    @Test
    public void handleConstructorArgumentException() {
        NullConstructorArgumentException nullConstructorArgumentException =
                mock(NullConstructorArgumentException.class);
        when(nullConstructorArgumentException.getMessage()).thenReturn("Test message");

        ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
        ResponseEntity<ApiExceptionMessage> response =
                apiExceptionHandler.handleConstructorArgumentException(nullConstructorArgumentException);
        ApiExceptionMessage responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Test message", response.getBody().getMessage());
    }

    @Test
    public void handleEntityNotFoundException() {
        EntityNotFoundException notFoundException = mock(EntityNotFoundException.class);
        when(notFoundException.getMessage()).thenReturn("Test message");

        ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
        ResponseEntity<ApiExceptionMessage> response = apiExceptionHandler.handleEntityNotFoundException(notFoundException);
        ApiExceptionMessage responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Test message", response.getBody().getMessage());
    }

    @Test
    public void handleDataValidationException() {
        DataValidationException dataValidationException = mock(DataValidationException.class);
        when(dataValidationException.getMessage()).thenReturn("Test message");

        ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
        ResponseEntity<ApiExceptionMessage> response =
                apiExceptionHandler.handleDataValidationException(dataValidationException);
        ApiExceptionMessage responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Test message", response.getBody().getMessage());
    }

    @Test
    public void handleInvalidOperationException() {
        InvalidOperationException invalidOperationException = mock(InvalidOperationException.class);
        when(invalidOperationException.getMessage()).thenReturn("Test message");

        ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
        ResponseEntity<ApiExceptionMessage> response =
                apiExceptionHandler.handleInvalidOperationException(invalidOperationException);
        ApiExceptionMessage responseBody = response.getBody();

        assertNotNull(responseBody);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Test message", response.getBody().getMessage());
    }
}
