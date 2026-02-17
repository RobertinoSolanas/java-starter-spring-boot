package de.itzbund.none.starter.example.spring.buecher.application.exceptions;

/**
 * Exception, die geworfen wird, wenn eine angeforderte Entität nicht gefunden werden kann.
 * 
 * <p>Diese Exception wird typischerweise in Service-Schichten verwendet, wenn eine 
 * Abfrage nach einer Entität mit einer bestimmten ID kein Ergebnis liefert.</p>
 * 
 * <p><strong>Anwendungsbeispiele:</strong></p>
 * <ul>
 *   <li>Buch mit ID nicht in der Datenbank vorhanden</li>
 *   <li>Bestellung mit angegebener ID existiert nicht</li>
 *   <li>Referenzierte Entität wurde bereits gelöscht</li>
 * </ul>
 * 
 * <p>Diese Exception führt zu einer HTTP 404 (Not Found) Antwort.</p>
 * 
 * @see de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.exception.GlobalExceptionHandler#handleNotFoundException
 */
public class NotFoundException extends RuntimeException {

    /** Die ID der nicht gefundenen Entität. */
    private final Long entityId;

    /**
     * Erstellt eine neue NotFoundException für eine nicht gefundene Entität.
     * 
     * <p>Die Exception-Message wird automatisch im Format 
     * "Entity with ID {id} not found" generiert.</p>
     *
     * @param entityId die ID der nicht gefundenen Entität (darf nicht null sein)
     * @throws IllegalArgumentException wenn entityId null ist
     */
    public NotFoundException(Long entityId) {
        super(String.format("Entity with ID %d not found", entityId));
        if (entityId == null) {
            throw new IllegalArgumentException("Entity ID darf nicht null sein");
        }
        this.entityId = entityId;
    }

    /**
     * Gibt die ID der nicht gefundenen Entität zurück.
     * 
     * @return die ID der nicht gefundenen Entität (niemals null)
     */
    public Long getEntityId() {
        return entityId;
    }
}
