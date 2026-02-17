package de.itzbund.none.starter.example.spring.bestellungen.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Modellklasse für eine Bestellung
 */
@AllArgsConstructor
@Getter
@Setter
public class Bestellung {
    private Long id;
    private Long produktId;
    private int menge;
    private BigDecimal preis;
    private String beschreibung;
}
