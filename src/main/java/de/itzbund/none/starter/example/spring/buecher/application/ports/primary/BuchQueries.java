package de.itzbund.none.starter.example.spring.buecher.application.ports.primary;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;

/**
 * Primary Port für die Interaktion mit Buch Objekten
 */
@PrimaryPort
public interface BuchQueries {

    /**
     * Gibt ein Buch anhand seiner ID zurück.
     */
    Buch getBuchById(Long id);

    /**
     * Gibt eine Liste aller verfügbaren Bücher zurück.
     */
    List<Buch> getVerfuegbareBuecher();

    /**
     * Gibt eine Liste aller ausgeliehenen Bücher zurück.
     */
    List<Buch> getAusgelieheneBuecher();

    /**
     * Gibt eine Liste aller Bücher zurück.
     */
    List<Buch> getAllBuecher();
}
