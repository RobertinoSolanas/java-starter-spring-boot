package de.itzbund.none.starter.example.spring.bestellungen.adapters.primary.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * DTO mit Informationen zu einer Bestellung
 */
@Getter
@AllArgsConstructor
public class BestellungResponse {
    private Long id;
    private Long produktId;
    private int menge;
    private BigDecimal preis;
    private String beschreibung;
}
