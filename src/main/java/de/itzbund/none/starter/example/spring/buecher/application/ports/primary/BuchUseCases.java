package de.itzbund.none.starter.example.spring.buecher.application.ports.primary;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.Optional;

/**
 * Primary Port für die Interaktion mit Buch Objekten
 */
@PrimaryPort
public interface BuchUseCases {
    /**
     * Neues Buch hinzufügen
     */
    Buch addBuch(Buch buch);

    /**
     * Buch mit geg. ID wird von User mit bestimmter ID ausgeliehen
     */
    void buchAusleihen(Long buchId, Long userId);

    /**
     * Aktualisiert einzelne Felder eines Buches
     */
    Buch patchBuch(Long id, Buch buch);

    /**
     * Löscht ein Buch anhand seiner ID
     */
    void removeBuch(Long buchId);

    /**
     * Ausgeliehenes Buch wird zurückgegeben
     */
    void buchZurueckgeben(Long buchId, Long userId);

    /**
     * Ersetzt ein bestehendes Buch vollständig
     */
    Buch updateBuch(Long id, Buch buch);

}
