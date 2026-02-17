package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Buch Entity
 */
@Entity
@Table(name = "buch")
@Getter
@Setter
public class JpaBuchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titel;
    private String autor;
    private String isbn;
    @Column(name = "jahr")
    private int jahr;

    @Column(name = "ausgeliehen")
    private boolean ausgeliehen;

    @Column(name = "ausgeliehen_von")
    private Long ausgeliehenVon;

    public JpaBuchEntity() {
    }

    public JpaBuchEntity(Long id, String titel, String autor, String isbn, int jahr, boolean ausgeliehen,
            Long ausgeliehenVon) {
        this.id = id;
        this.titel = titel;
        this.autor = autor;
        this.isbn = isbn;
        this.jahr = jahr;
        this.ausgeliehen = ausgeliehen;
        this.ausgeliehenVon = ausgeliehenVon;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }
}