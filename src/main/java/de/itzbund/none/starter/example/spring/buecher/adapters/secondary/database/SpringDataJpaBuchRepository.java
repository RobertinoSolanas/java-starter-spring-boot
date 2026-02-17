package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SpringDataJpa Repository für Buch Objekte
 */
public interface SpringDataJpaBuchRepository extends JpaRepository<JpaBuchEntity, Long> {
    // Leer, da lediglich Standard-CRUD Operationen verwendet werden
}