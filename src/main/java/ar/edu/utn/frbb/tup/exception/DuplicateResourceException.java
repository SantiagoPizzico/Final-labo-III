package ar.edu.utn.frbb.tup.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para indicar que un recurso ya existe (409 Conflict).
 */
public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String resource, String field, String value) {
        super(
            String.format("%s con %s '%s' ya existe.", resource, field, value),
            "DUPLICATE_RESOURCE",
            HttpStatus.CONFLICT
        );
    }
}