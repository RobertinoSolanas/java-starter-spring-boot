package de.itzbund.none.starter.example.spring.bestellungen.application.ports.secondary;

import de.itzbund.none.starter.example.spring.bestellungen.application.domain.Bestellung;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.List;

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
}
