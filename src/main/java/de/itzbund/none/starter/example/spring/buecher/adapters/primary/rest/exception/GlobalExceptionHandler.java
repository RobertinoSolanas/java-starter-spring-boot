package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import de.itzbund.none.starter.example.spring.buecher.application.exceptions.BusinessException;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.NotFoundException;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.UnauthorizedException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Globaler Exception-Handler für die Anwendung.
 * Wandelt anwendungsdefinierte Ausnahmen in passende HTTP-Antworten um.
 *
 * Unterstützte Zuordnungen:
 * - NotFoundException -> 404 NOT_FOUND
 * - BusinessException -> 409 CONFLICT
 * - UnauthorizedException -> 403 FORBIDDEN
 * - IllegalArgumentException -> 400 BAD_REQUEST
 * - Exception (generisch) -> 500 INTERNAL_SERVER_ERROR
 *
 * Jede Antwort enthält allgemeine Metadaten wie Timestamp, Status, Error, Message und Path.
 * Zusätzlich werden spezifische Felder der jeweiligen Ausnahme (z. B. entityId,
 * businessRule, userId, operation) in die Antwort aufgenommen, falls vorhanden.
 *
 * Der Handler protokolliert alle Ausnahmen im Error-Log mit relevanten Details,
 * damit Fehler im Betrieb nachvollzogen werden können.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Behandelt NotFoundException und liefert eine 404-Antwort.
     * Die Antwort enthält: timestamp, status, error, message, entityId, path.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(
            NotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Entity Not Found");
        body.put("message", ex.getMessage());
        body.put("entityId", ex.getEntityId());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        LOGGER.error("Entity Not Found: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Behandelt BusinessException und liefert eine 409-Antwort.
     * Die Antwort enthält: timestamp, status, error, message, businessRule,
     * optional entityId und path.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(
            BusinessException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Business Rule Violation");
        body.put("message", ex.getMessage());
        body.put("businessRule", ex.getBusinessRule());
        if (ex.getEntityId() != null) {
            body.put("entityId", ex.getEntityId());
        }
        body.put("path", request.getDescription(false).replace("uri=", ""));

        LOGGER.error("Business Rule Violation: {}, Request Details: {}", ex.getBusinessRule(),
                request.getDescription(false), ex);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * Behandelt UnauthorizedException und liefert eine 403-Antwort.
     * Die Antwort enthält: timestamp, status, error, message, entityId, userId,
     * operation und path.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Unauthorized Operation");
        body.put("message", ex.getMessage());
        body.put("entityId", ex.getEntityId());
        body.put("userId", ex.getUserId());
        body.put("operation", ex.getOperation());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        LOGGER.error("Unauthorized operation: {}, Request Details: {}", ex.getOperation(),
                request.getDescription(false), ex);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    /**
     * Allgemeiner Fallback für nicht explizit behandelte Ausnahmen.
     * Liefert eine 500-Antwort (Internal Server Error) und protokolliert die Ausnahme.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred");
        body.put("path", request.getDescription(false).replace("uri=", ""));

        LOGGER.error("Exception occurred: {}, Request Details: {}", ex.getMessage(), request.getDescription(false), ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
