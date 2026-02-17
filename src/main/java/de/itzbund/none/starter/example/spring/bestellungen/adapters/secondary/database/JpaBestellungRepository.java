package de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database;

import de.itzbund.none.starter.example.spring.bestellungen.application.domain.Bestellung;
import de.itzbund.none.starter.example.spring.bestellungen.application.ports.secondary.BestellungRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA Repository Implementierung für Bestellung Objekte
 */
@Repository
@Profile("h2")
public class JpaBestellungRepository implements BestellungRepository {

    private final SpringDataJpaBestellungRepository jpaRepository;

    /**
     * JpaBestellungRepository Konstruktor
     */
    public JpaBestellungRepository(SpringDataJpaBestellungRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Bestellung save(Bestellung bestellung) {
        JpaBestellungEntity entity = new JpaBestellungEntity(
                bestellung.getProduktId(),
                bestellung.getMenge(),
                bestellung.getPreis(),
                bestellung.getBeschreibung()
        );
        JpaBestellungEntity saved = jpaRepository.save(entity);
        return new Bestellung(saved.getId(), saved.getProductId(), saved.getQuantity(), saved.getPrice(), saved.getDescription());
    }

    @Override
    public List<Bestellung> findAll() {
        return jpaRepository.findAll().stream()
                .map(e -> new Bestellung(e.getId(), e.getProductId(), e.getQuantity(), e.getPrice(), e.getDescription()))
            .collect(Collectors.toList());
    }
}