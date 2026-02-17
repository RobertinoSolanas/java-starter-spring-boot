package de.itzbund.none.starter.example.spring.bestellungen.application.ports.primary;

import de.itzbund.none.starter.example.spring.bestellungen.application.domain.Bestellung;
import de.itzbund.none.starter.example.spring.bestellungen.application.ports.secondary.BestellungRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Primary Port für die Interaktion mit Bestellung Objekten
 */
@Service
@PrimaryPort
@Transactional
public class BestellungService {

    private final BestellungRepository repository;
    private Long nextId = 1L;

    /**
     * BestellungService Konstruktor
     */
    public BestellungService(BestellungRepository repository) {
        this.repository = repository;
    }

    /**
     * Neue Bestellung anlegen
     */
    public Bestellung createBestellung(Long productId, int quantity, BigDecimal price, String description) {
        nextId++;
        Bestellung bestellung = new Bestellung(nextId, productId, quantity, price, description);
        return repository.save(bestellung);
    }

    /**
     * Gibt eine Liste aller Bestellungen zurück.
     */
    public List<Bestellung> getAllBestellungen() {
        return repository.findAll();
    }
}