package ar.edu.utn.frbb.tup.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para errores de validación de datos de entrada (400).
 */
public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(
            message,
            "VALIDATION_ERROR",
            HttpStatus.BAD_REQUEST
        );
    }
}