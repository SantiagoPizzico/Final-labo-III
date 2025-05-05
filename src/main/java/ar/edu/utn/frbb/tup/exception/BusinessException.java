package ar.edu.utn.frbb.tup.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción base para todas las reglas de negocio y validaciones de la aplicación.
 * Permite asociar un código y un estado HTTP a cada error.
 */
public abstract class BusinessException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    protected BusinessException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}