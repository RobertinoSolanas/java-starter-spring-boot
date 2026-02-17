package de.itzbund.none.starter.example.spring.bestellungen.application.ports.secondary;

import de.itzbund.none.starter.example.spring.bestellungen.application.domain.Bestellung;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.List;
import java.util.Optional;

/**
 * Secondary Port für die Interaktion mit Bestellung Objekten
 */
@SecondaryPort
public interface BestellungRepository {

    /**
     * Speichere/Aktualisiere eine Bestellung
     */
    Bestellung save(Bestellung bestellung);

    /**
     * Gibt alle Bestellungen zurück.
     */
    List<Bestellung> findAll();

    /**
     * Gibt eine Bestellung anhand ihrer ID zurück.
     * <p>
     * Default implementation searches the {@link #findAll()} list.
     * Concrete adapters can override this method for a more efficient implementation.
     * </p>
     */
    default Optional<Bestellung> findById(Long id) {
        return findAll().stream()
                .filter(b -> b.getId() != null && b.getId().equals(id))
                .findFirst();
    }

    /**
     * Löscht eine Bestellung anhand ihrer ID.
     * <p>
     * Default implementation does nothing. Concrete adapters should override
     * this method to perform actual deletion.
     * </p>
     */
    default void deleteById(Long id) {
        // no‑op default – concrete implementations should provide real deletion logic
    }
}
