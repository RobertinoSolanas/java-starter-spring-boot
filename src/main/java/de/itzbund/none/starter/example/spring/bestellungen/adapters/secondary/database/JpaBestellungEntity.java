package de.itzbund.none.starter.example.spring.bestellungen.adapters.secondary.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Bestellung Entity
 */
@Entity
@Table(name = "bestellung")
@Getter
@Setter
public class JpaBestellungEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;
    private BigDecimal price;
    private String description;

    /**
     * InMemoryBuchRepository Konstruktor
     */
    public JpaBestellungEntity() {
    }

    /**
     * InMemoryBuchRepository Konstruktor
     */
    public JpaBestellungEntity(Long productId, int quantity, BigDecimal price, String description) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }
}