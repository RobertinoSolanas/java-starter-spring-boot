package de.itzbund.none.starter.example.spring.buecher.application.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Buch Test
 */
class BuchTest {

    @Test
    void buchCreation() {
        // Given
        Long id = 1L;
        String titel = "Clean Code";
        String autor = "Robert C. Martin";
        String isbn = "9780132350884";
        int jahr = 2008;

        // When
        Buch buch = new Buch(id, titel, autor, isbn, jahr, false, Optional.empty());

        // Then
        assertEquals(id, buch.getId());
        assertEquals(titel, buch.getTitel());
        assertEquals(autor, buch.getAutor());
        assertEquals(isbn, buch.getIsbn());
        assertEquals(jahr, buch.getJahr());
        assertFalse(buch.isAusgeliehen());
        assertTrue(buch.getAusgeliehenVon().isEmpty());
    }

    @Test
    void ausleihen_whenVerfuegbar_shouldSucceed() {
        // Given
        Buch buch = new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, false, Optional.empty());
        Long userId = 100L;

        // When
        boolean result = buch.ausleihen(userId);

        // Then
        assertTrue(result);
        assertTrue(buch.isAusgeliehen());
        assertEquals(userId, buch.getAusgeliehenVon().get());
    }

    @Test
    void ausleihen_whenAlreadyAusgeliehen_shouldFail() {
        // Given
        Buch buch = new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true, Optional.of(200L));
        Long userId = 100L;

        // When
        boolean result = buch.ausleihen(userId);

        // Then
        assertFalse(result);
        assertTrue(buch.isAusgeliehen());
        assertEquals(200L, buch.getAusgeliehenVon().get());
    }

    @Test
    void zurueckgeben_whenAusgeliehenVonSameUser_shouldSucceed() {
        // Given
        Long userId = 100L;
        Buch buch = new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true, Optional.of(userId));

        // When
        boolean result = buch.zurueckgeben(userId);

        // Then
        assertTrue(result);
        assertFalse(buch.isAusgeliehen());
        assertTrue(buch.getAusgeliehenVon().isEmpty());
    }

    @Test
    void zurueckgeben_whenAusgeliehenVonDifferentUser_shouldFail() {
        // Given
        Long buchAusgeliehenVonUserId = 200L;
        Long attemptingUserId = 100L;
        Buch buch = new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, true,
                Optional.of(buchAusgeliehenVonUserId));

        // When
        boolean result = buch.zurueckgeben(attemptingUserId);

        // Then
        assertFalse(result);
        assertTrue(buch.isAusgeliehen());
        assertEquals(buchAusgeliehenVonUserId, buch.getAusgeliehenVon().get());
    }

    @Test
    void zurueckgeben_whenNotAusgeliehen_shouldFail() {
        // Given
        Buch buch = new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", 2008, false, Optional.empty());
        Long userId = 100L;

        // When
        boolean result = buch.zurueckgeben(userId);

        // Then
        assertFalse(result);
        assertFalse(buch.isAusgeliehen());
        assertTrue(buch.getAusgeliehenVon().isEmpty());
    }
}
