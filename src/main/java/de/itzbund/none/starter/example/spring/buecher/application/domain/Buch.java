package de.itzbund.none.starter.example.spring.buecher.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Modellklasse für ein Buch
 */
public class Buch {
    private final Long id;
    private String titel;
    private String autor;
    private String isbn;
    private int jahr;
    private boolean ausgeliehen;
    private Optional<Long> ausgeliehenVon;

    public Buch(Long id, String titel, String autor, String isbn, int jahr, boolean ausgeliehen,
            Optional<Long> ausgeliehenVon) {
        this.id = id;
        this.titel = titel;
        this.autor = autor;
        this.isbn = isbn;
        this.jahr = jahr;
        this.ausgeliehen = ausgeliehen;
        this.ausgeliehenVon = ausgeliehenVon;
    }

    public Long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public boolean isAusgeliehen() {
        return ausgeliehen;
    }

    public Optional<Long> getAusgeliehenVon() {
        return ausgeliehenVon;
    }

    /**
     * Buch ausleihen
     */
    public boolean ausleihen(Long userId) {
        if (ausgeliehen) {
            return false;
        }
        ausgeliehen = true;
        ausgeliehenVon = Optional.of(userId);
        return true;
    }

    /**
     * Buch zurückgeben
     */
    public boolean zurueckgeben(Long userId) {
        if (!ausgeliehen || ausgeliehenVon.isEmpty() || !ausgeliehenVon.get().equals(userId)) {
            return false;
        }
        ausgeliehen = false;
        ausgeliehenVon = Optional.empty();
        return true;
    }
}
