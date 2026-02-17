package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest;

import de.itzbund.openapi.example_api.BuchAnlegenRequest;
import de.itzbund.openapi.example_api.BuchBearbeitenRequest;
import de.itzbund.openapi.example_api.BuchKomplettBearbeitenRequest;
import de.itzbund.openapi.example_api.GetBuchResponse;
import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases.PatchBuchCommand;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Optional;

/**
 * DTO-Mapper für Buch Objekte
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING) // konfigurierbar
public interface BuchDtoMapper {

    /**
     * Mappt ein Buch auf eine GetBuchResponse
     */
    @Mapping(target = "titel", source = "titel")
    @Mapping(target = "autor", source = "autor")
    @BeanMapping(ignoreUnmappedSourceProperties = {"ausgeliehen", "ausgeliehenVon"})
    GetBuchResponse toResponse(Buch buch);

    /**
     * Mappt ein BuchAnlegenRequest auf einen AddBuchCommand
     */
    @Mapping(target = "titel", source = "titel")
    @Mapping(target = "autor", source = "autor")
    BuchUseCases.AddBuchCommand toCommand(BuchAnlegenRequest request);

    /**
     * Mappt ein BuchBearbeitenRequest auf einen PatchBuchCommand
     */
    @Mapping(target = "titel", source = "request.titel", qualifiedByName = "toOptionalString")
    @Mapping(target = "autor", source = "request.autor", qualifiedByName = "toOptionalString")
    @Mapping(target = "isbn", source = "request.isbn", qualifiedByName = "toOptionalString")
    PatchBuchCommand toCommand(Long id, BuchBearbeitenRequest request);

    /**
     * Mappt einen BuchKomplettBearbeitenRequest auf einen UpdateBuchCommand
     */
    @Mapping(target = "titel", source = "request.titel")
    @Mapping(target = "autor", source = "request.autor")
    BuchUseCases.UpdateBuchCommand toCommand(Long id, BuchKomplettBearbeitenRequest request);

    /**
     * Hilfsmethode zum Mappen von Strings auf passende Optionals
     */
    @Named("toOptionalString")
    default Optional<String> toOptionalString(String value) {
        return Optional.ofNullable(value);
    }
}
