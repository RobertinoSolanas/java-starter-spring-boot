package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.dto;

public record BuchResponse(Long id, String titel, String autor, String isbn, int jahr, boolean ausgeliehen,
        Long ausgeliehenVon) {
}
