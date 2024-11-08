package org.switch2022.project.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The ApiExceptionHandler class is a controller advice class that handles exceptions thrown
 by the API endpoints in the application.
 * It provides exception handling methods for different types of custom exceptions.
 */
@ControllerAdvice
public class ApiExceptionHandler {

    /**
     * Handles NullConstructorArgumentException, EmptyConstructorArgumentException and
     * InvalidConstructorArgumentException exceptions.
     *
     * @param e the AbstractApiException instance
     * @return a ResponseEntity containing the error message and the HTTP status code
     */
    @ExceptionHandler(value = {
            NullConstructorArgumentException.class,
            EmptyConstructorArgumentException.class,
            InvalidConstructorArgumentException.class
    })
    public ResponseEntity<ApiExceptionMessage> handleConstructorArgumentException(AbstractApiException e) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage(
                e.getMessage()
        );

        return new ResponseEntity<>(apiExceptionMessage, httpStatus);
    }

    /**
     * Handles EntityNotFoundException exceptions.
     *
     * @param e the EntityNotFoundException instance
     * @return a ResponseEntity containing the error message and the HTTP status code
     */
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiExceptionMessage> handleEntityNotFoundException(EntityNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage(
                e.getMessage()
        );

        return new ResponseEntity<>(apiExceptionMessage, httpStatus);
    }

    /**
     * Handles DataValidationException exceptions.
     *
     * @param e the DataValidationException instance
     * @return a ResponseEntity containing the error message and the HTTP status code
     */
    @ExceptionHandler(value = {DataValidationException.class})
    public ResponseEntity<ApiExceptionMessage> handleDataValidationException(DataValidationException e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage(
                e.getMessage()
        );

        return new ResponseEntity<>(apiExceptionMessage, httpStatus);
    }

    /**
     * Handles InvalidOperationException exceptions.
     *
     * @param e the InvalidOperationException instance
     * @return a ResponseEntity containing the error message and the HTTP status code
     */
    @ExceptionHandler(value = {InvalidOperationException.class})
    public ResponseEntity<ApiExceptionMessage> handleInvalidOperationException(InvalidOperationException e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiExceptionMessage apiExceptionMessage = new ApiExceptionMessage(
                e.getMessage()
        );

        return new ResponseEntity<>(apiExceptionMessage, httpStatus);
    }
}
