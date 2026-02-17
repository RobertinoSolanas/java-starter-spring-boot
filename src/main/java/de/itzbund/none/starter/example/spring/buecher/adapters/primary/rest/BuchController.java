package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest;

import de.itzbund.openapi.example_api.BuchAnlegenRequest;
import de.itzbund.openapi.example_api.BuchApi;
import de.itzbund.openapi.example_api.BuchBearbeitenRequest;
import de.itzbund.openapi.example_api.BuchKomplettBearbeitenRequest;
import de.itzbund.openapi.example_api.GetBuchResponse;
import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchQueries;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Primärer REST-Adapter für Bücher.
 *
 * Die Pfade werden ausschließlich über das generierte {@code BuchApi}-Interface
 * definiert. Deshalb wird hier **keine** Klassen‑Level {@code @RequestMapping}
 * Annotation verwendet – ein doppeltes Präfix (z. B. {@code /api/v1/buecher/buecher})
 * würde zu falschen URLs führen und Spring würde die Anfrage fälschlicherweise
 * als Aufruf einer statischen Ressource behandeln.
 *
 * Wir fügen hier jedoch ein einheitliches Präfix {@code /api} hinzu, damit die
 * vom Front‑End erwarteten URLs (z. B. {@code /api/buecher}) mit den generierten
 * Pfaden zusammenpassen.
 */
@RestController
@RequestMapping("/api")   // <-- adds the "/api" prefix to every BuchApi endpoint
public class BuchController implements BuchApi {

    private final BuchDtoMapper mapper;
    private final BuchUseCases useCases;
    private final BuchQueries queries;

    /**
     * Konstruktor für den BuchController.
     */
    public BuchController(BuchDtoMapper mapper, BuchUseCases useCases, BuchQueries queries) {
        this.mapper = mapper;
        this.useCases = useCases;
        this.queries = queries;
    }

    /**
     * Erstellt ein neues Buch.
     */
    @Override
    public ResponseEntity<GetBuchResponse> createBuch(BuchAnlegenRequest request) {
        Buch createdBuch = useCases.addBuch(mapper.toCommand(request));
        return new ResponseEntity<>(mapper.toResponse(createdBuch), HttpStatus.CREATED);
    }

    /**
     * Gibt ein Buch anhand seiner ID zurück.
     */
    @Override
    public ResponseEntity<GetBuchResponse> getBuch(Long id) {
        Buch book = queries.getBuchById(id);
        return new ResponseEntity<>(mapper.toResponse(book), HttpStatus.OK);
    }

    /**
     * Gibt eine Liste aller Bücher zurück.
     */
    @Override
    public ResponseEntity<List<GetBuchResponse>> getBuecher() {
        List<Buch> buchList = queries.getVerfuegbareBuecher();
        List<GetBuchResponse> responses = buchList.stream()
                .map(buch -> mapper.toResponse(buch))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Ersetzt ein bestehendes Buch vollständig.
     */
    @Override
    public ResponseEntity<GetBuchResponse> updateBuch(Long id, BuchKomplettBearbeitenRequest request) {
        Buch updatedBuch = useCases.updateBuch(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(updatedBuch));
    }

    /**
     * Aktualisiert einzelne Felder eines Buches.
     */
    @Override
    public ResponseEntity<GetBuchResponse> patchBuch(Long id, BuchBearbeitenRequest request) {
        Buch updatedBuch = useCases.patchBuch(mapper.toCommand(id, request));
        return ResponseEntity.ok(mapper.toResponse(updatedBuch));
    }

    /**
     * Löscht ein Buch anhand seiner ID.
     */
    @Override
    public ResponseEntity<Void> deleteBuch(Long id) {
        useCases.removeBuch(id);
        return ResponseEntity.noContent().build();
    }
}
