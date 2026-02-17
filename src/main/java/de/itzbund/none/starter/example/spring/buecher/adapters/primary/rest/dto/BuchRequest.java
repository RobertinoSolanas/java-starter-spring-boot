package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.dto;

public record BuchRequest(String titel, String autor, String isbn, int jahr) {
}
