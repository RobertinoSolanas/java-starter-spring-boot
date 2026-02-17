package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest;

import de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.dto.BuchRequest;
import de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.dto.BuchResponse;
import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BuchDtoMapper {

    public BuchResponse toResponse(Buch buch) {
        if (buch == null) {
            return null;
        }
        return new BuchResponse(
                buch.getId(),
                buch.getTitel(),
                buch.getAutor(),
                buch.getIsbn(),
                buch.getJahr(),
                buch.isAusgeliehen(),
                buch.getAusgeliehenVon().orElse(null));
    }

    public Buch toDomain(BuchRequest request) {
        if (request == null) {
            return null;
        }
        return new Buch(
                null,
                request.titel(),
                request.autor(),
                request.isbn(),
                request.jahr(),
                false,
                Optional.empty());
    }
}
