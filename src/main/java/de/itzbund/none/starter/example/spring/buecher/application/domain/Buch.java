package de.itzbund.none.starter.example.spring.buecher.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Modellklasse für ein Buch
 */
@Getter
@Setter
@AllArgsConstructor
public class Buch {
    private final Long id;
    private String titel;
    private String autor;
    private String isbn;
    private boolean ausgeliehen;
    private Optional<Long> ausgeliehenVon;

    /**
     * Buch ausleihen
     */
    public boolean ausleihen(Long userId) {
        if (ausgeliehenVon.isPresent()) {
            return false;
        }
        ausgeliehenVon = Optional.of(userId);
        ausgeliehen = true;
        return true;
    }

    /**
     * Buch zurückgeben
     */
    public boolean zurueckgeben(Long userId) {
        if (ausgeliehenVon.isEmpty() || !ausgeliehenVon.get().equals(userId)) {
            return false;
        }
        ausgeliehenVon = Optional.empty();
        ausgeliehen = false;
        return true;
    }
}
