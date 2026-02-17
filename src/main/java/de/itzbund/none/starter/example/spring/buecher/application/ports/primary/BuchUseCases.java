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
     * Command für das Hinzufügen eines neuen Buchs
     */
    record AddBuchCommand(String titel, String autor, String isbn) {
    }

    /**
     * Command für das Aktualisieren einzelne Felder eines Buchs
     */
    record PatchBuchCommand(Long id, Optional<String> titel, Optional<String> autor, Optional<String> isbn) {
    }

    /**
     * Command für das vollständige Ersetzen eines Buchs
     */
    record UpdateBuchCommand(Long id, String titel, String autor, String isbn) {
    }

    /**
     * Neues Buch hinzufügen
     */
    Buch addBuch(AddBuchCommand command);

    /**
     * Buch mit geg. ID wird von User mit bestimmter ID ausgeliehen
     */
    void buchAusleihen(Long buchId, Long userId);

    /**
     * Aktualisiert einzelne Felder eines Buches
     */
    Buch patchBuch(PatchBuchCommand command);

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
    Buch updateBuch(UpdateBuchCommand command);

}
