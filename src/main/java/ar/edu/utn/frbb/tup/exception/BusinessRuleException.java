package ar.edu.utn.frbb.tup.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para violaciones de reglas de negocio (409).
 */
public class BusinessRuleException extends BusinessException {
    public BusinessRuleException(String message) {
        super(
            message,
            "BUSINESS_RULE_VIOLATION",
            HttpStatus.CONFLICT
        );
    }
}