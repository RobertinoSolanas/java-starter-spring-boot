package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Buch Entity
 */
@Entity
@Table(name = "buch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JpaBuchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titel;
    private String autor;
    private String isbn;
    private boolean ausgeliehen;
    private Long ausgeliehenVon;
}