package de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * SpringDataJpa Repository für Bestellung Objekte
 */
public interface SpringDataJpaBestellungRepository extends JpaRepository<JpaBestellungEntity, Long> {
    // Leer, da lediglich Standard-CRUD Operationen verwendet werden
}