package de.itzbund.none.starter.example.spring.buecher.application.ports.secondary;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.List;
import java.util.Optional;

/**
 * Secondary Port für die Interaktion mit Buch Objekten
 */
@SecondaryPort
public interface BuchRepository {

    /**
     * Speichere/Aktualisiere ein Buch
     */
    Buch save(Buch buch);

    /**
     * Gibt ein Buch anhand seiner ID zurück.
     */
    Optional<Buch> findById(Long id);

    /**
     * Löscht ein Buch anhand seiner ID.
     */
    void deleteById(Long id);

    /**
     * Gibt alle verfügbaren Bücher zurück.
     */
    List<Buch> findAllVerfuegbar();

    /**
     * Gibt alle ausgeliehenen Bücher zurück.
     */
    List<Buch> findAllAusgeliehen();

    /**
     * Gibt alle Bücher zurück.
     */
    List<Buch> findAll();
}
