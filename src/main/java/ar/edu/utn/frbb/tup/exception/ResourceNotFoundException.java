package ar.edu.utn.frbb.tup.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para recursos no encontrados (404).
 */
public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resource, String id) {
        super(
            String.format("%s con id %s no encontrado", resource, id),
            "RESOURCE_NOT_FOUND",
            HttpStatus.NOT_FOUND
        );
    }
}