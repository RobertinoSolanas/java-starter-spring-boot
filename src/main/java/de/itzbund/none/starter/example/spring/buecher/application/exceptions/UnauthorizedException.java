package de.itzbund.none.starter.example.spring.buecher.application.exceptions;

/**
 * Exception, die bei unautorisierten Zugriffsversuchen geworfen wird.
 * 
 * <p>Diese Exception signalisiert, dass ein Benutzer nicht die erforderlichen 
 * Berechtigungen für eine bestimmte Operation besitzt. Sie sollte verwendet werden,
 * wenn die Authentifizierung erfolgreich war, aber die Autorisierung fehlschlägt.</p>
 * 
 * <p><strong>Anwendungsbeispiele:</strong></p>
 * <ul>
 *   <li>Benutzer versucht ein Buch zu löschen, hat aber nur Leseberechtigung</li>
 *   <li>Kunde versucht eine fremde Bestellung einzusehen</li>
 *   <li>Normaler Benutzer versucht Admin-Funktionen zu nutzen</li>
 *   <li>Benutzer versucht auf Ressourcen außerhalb seines Mandanten zuzugreifen</li>
 * </ul>
 * 
 * <p>Diese Exception führt zu einer HTTP 403 (Forbidden) Antwort.</p>
 * 
 * @see de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.exception.GlobalExceptionHandler#handleUnauthorizedException
 */
public class UnauthorizedException extends RuntimeException {

    /** Die ID der Entität, auf die zugegriffen werden sollte. */
    private final Long entityId;
    
    /** Die ID des Benutzers, der den unautorisierten Zugriff versucht hat. */
    private final Long userId;
    
    /** Die fachliche Operation, die versucht wurde (z.B. "borrow", "return", "read"). */
    private final String operation;

    /**
     * Erstellt eine UnauthorizedException für einen unautorisierten Zugriff.
     * 
     * <p>Die Exception-Message wird automatisch im Format 
     * "User {userId} is not authorized to {operation} entity {entityId}" generiert.</p>
     *
     * @param entityId  die ID der Entität, auf die zugegriffen werden sollte (darf nicht null sein)
     * @param userId    die ID des Benutzers, der den Zugriff versucht hat (darf nicht null sein)
     * @param operation die versuchte fachliche Operation (z.B. "borrow", "return", "read", darf nicht null oder leer sein)
     * @throws IllegalArgumentException wenn einer der Parameter null oder operation leer ist
     */
    public UnauthorizedException(Long entityId, Long userId, String operation) {
        super(String.format("User %d is not authorized to %s entity %d", userId, operation, entityId));
        if (entityId == null || userId == null || operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("EntityId, UserId und Operation dürfen nicht null oder leer sein");
        }
        this.entityId = entityId;
        this.userId = userId;
        this.operation = operation;
    }

    /**
     * Gibt die ID der Entität zurück, auf die zugegriffen werden sollte.
     * 
     * @return die ID der betroffenen Entität (niemals null)
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Gibt die ID des Benutzers zurück, der den unautorisierten Zugriff versucht hat.
     * 
     * @return die ID des Benutzers (niemals null)
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Gibt die fachliche Operation zurück, die versucht wurde.
     * 
     * @return die versuchte fachliche Operation (niemals null oder leer)
     */

    public String getOperation() {
        return operation;
    }
}
