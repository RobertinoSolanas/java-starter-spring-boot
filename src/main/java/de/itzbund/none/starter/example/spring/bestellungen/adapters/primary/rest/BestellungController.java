package de.itzbund.none.starter.example.spring.bestellungen.adapters.primary.rest;

import de.itzbund.none.starter.example.spring.bestellungen.adapters.primary.rest.dto.BestellungRequest;
import de.itzbund.none.starter.example.spring.bestellungen.adapters.primary.rest.dto.BestellungResponse;
import de.itzbund.none.starter.example.spring.bestellungen.application.domain.Bestellung;
import de.itzbund.none.starter.example.spring.bestellungen.application.ports.primary.BestellungService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Primärer REST-Adapter für Bestellungen
 */
@RestController
@RequestMapping("/api/order")
public class BestellungController {

    private final BestellungService bestellungService;

    /**
     * BestellungController Konstruktor
     */
    public BestellungController(BestellungService bestellungService) {
        this.bestellungService = bestellungService;
    }

    /**
     * Legt eine neue Bestellung an
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BestellungResponse createBestellung(@RequestBody BestellungRequest request) {
        Bestellung created = bestellungService.createBestellung(
                request.getProduktId(),
                request.getMenge(),
                request.getPreis(),
                request.getBeschreibung()
        );
        return new BestellungResponse(
            created.getId(),
                created.getProduktId(),
                created.getMenge(),
                created.getPreis(),
                created.getBeschreibung()
        );
    }

    /**
     * Gibt eine Liste aller Bestellungen zurück
     */
    @GetMapping
    public List<BestellungResponse> getAllBestellungen() {
        List<Bestellung> bestellungen = bestellungService.getAllBestellungen();
        return bestellungen.stream()
                .map(o -> new BestellungResponse(
                    o.getId(),
                        o.getProduktId(),
                        o.getMenge(),
                        o.getPreis(),
                        o.getBeschreibung()
                ))
                .collect(Collectors.toList());
    }
}