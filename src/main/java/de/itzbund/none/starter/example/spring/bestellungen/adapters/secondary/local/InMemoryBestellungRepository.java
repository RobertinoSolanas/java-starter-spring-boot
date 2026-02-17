package de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.local;

import de.itzbund.none.starter.example.spring.bestellungen.application.domain.Bestellung;
import de.itzbund.none.starter.example.spring.bestellungen.application.ports.secondary.BestellungRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * In-Memory Repository für Bestellung Objekte
 */
@Repository
@Profile("in-memory")
public class InMemoryBestellungRepository implements BestellungRepository {
    private final List<Bestellung> bestellungen = new ArrayList<>();

    /**
     * InMemoryBestellungRepository Konstruktor
     */
    public InMemoryBestellungRepository() {
        super();

        bestellungen.add(new Bestellung(1L, 1L, 2, BigDecimal.valueOf(20.0), "Test Bestellung 1"));
        bestellungen.add(new Bestellung(2L, 2L, 1, BigDecimal.valueOf(10.0), "Test Bestellung 2"));
        bestellungen.add(new Bestellung(3L, 3L, 5, BigDecimal.valueOf(50.0), "Test Bestellung 3"));
    }

    @Override
    public synchronized Bestellung save(Bestellung bestellung) {
        bestellungen.add(bestellung);
        return bestellung;
    }

    @Override
    public synchronized List<Bestellung> findAll() {
        return Collections.unmodifiableList(bestellungen);
    }
}