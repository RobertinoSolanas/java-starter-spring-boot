package de.itzbund.none.starter.example.spring.bestellungen.adapters.primary.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO zum Erstellen einer Bestellung
 */
@Getter
@Setter
public class BestellungRequest {
    private Long produktId;
    private int menge;
    private BigDecimal preis;
    private String beschreibung;
}
