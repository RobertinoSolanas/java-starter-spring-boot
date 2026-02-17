package de.itzbund.none.starter.example.spring.buecher.application.exceptions;

/**
 * Exception, die bei Verletzung von Geschäftsregeln geworfen wird.
 * 
 * <p>Diese Exception signalisiert, dass eine Operation aufgrund einer verletzten 
 * Geschäftsregel nicht ausgeführt werden kann. Sie sollte verwendet werden, wenn
 * technisch alles korrekt ist, aber fachliche Validierungen fehlschlagen.</p>
 * 
 * <p><strong>Anwendungsbeispiele:</strong></p>
 * <ul>
 *   <li>Buch kann nicht gelöscht werden, da es noch ausgeliehen ist</li>
 *   <li>Bestellung kann nicht storniert werden, da sie bereits versendet wurde</li>
 *   <li>Mindestbestellwert wurde nicht erreicht</li>
 *   <li>Inventar ist nicht ausreichend für die gewünschte Menge</li>
 * </ul>
 * 
 * <p>Diese Exception führt zu einer HTTP 409 (Conflict) Antwort.</p>
 * 
 * @see de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.exception.GlobalExceptionHandler#handleBusinessException
 */
public class BusinessException extends RuntimeException {

    /** Die ID der betroffenen Entität (optional). */
    private final Long entityId;
    
    /** Eindeutige Kennung der verletzten Geschäftsregel. */
    private final String businessRule;

    /**
     * Erstellt eine BusinessException für eine spezifische Entität.
     * 
     * <p>Verwenden Sie diesen Konstruktor, wenn die Regelverletzung eine 
     * bestimmte Entität betrifft.</p>
     *
     * @param entityId     die ID der betroffenen Entität (darf nicht null sein)
     * @param message      aussagekräftige Fehlermeldung für den Benutzer
     * @param businessRule eindeutige Kennung der verletzten Regel (z.B. "BOOK_STILL_BORROWED")
     * @throws IllegalArgumentException wenn entityId, message oder businessRule null sind
     */
    public BusinessException(Long entityId, String message, String businessRule) {
        super(message);
        if (entityId == null || message == null || businessRule == null) {
            throw new IllegalArgumentException("EntityId, Message und BusinessRule dürfen nicht null sein");
        }
        this.entityId = entityId;
        this.businessRule = businessRule;
    }

    /**
     * Erstellt eine BusinessException ohne spezifische Entitäts-ID.
     * 
     * <p>Verwenden Sie diesen Konstruktor für allgemeine Regelverletzungen,
     * die nicht an eine bestimmte Entität gebunden sind.</p>
     *
     * @param message      aussagekräftige Fehlermeldung für den Benutzer
     * @param businessRule eindeutige Kennung der verletzten Regel (z.B. "MIN_ORDER_VALUE_NOT_REACHED")
     * @throws IllegalArgumentException wenn message oder businessRule null sind
     */
    public BusinessException(String message, String businessRule) {
        super(message);
        if (message == null || businessRule == null) {
            throw new IllegalArgumentException("Message und BusinessRule dürfen nicht null sein");
        }
        this.entityId = null;
        this.businessRule = businessRule;
    }

    /**
     * Gibt die ID der betroffenen Entität zurück.
     * 
     * @return die ID der betroffenen Entität oder null, wenn keine spezifische Entität betroffen ist
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * Gibt die eindeutige Kennung der verletzten Geschäftsregel zurück.
     * 
     * @return die Kennung der verletzten Geschäftsregel (niemals null)
     */
    public String getBusinessRule() {
        return businessRule;
    }
}
