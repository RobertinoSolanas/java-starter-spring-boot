package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest;

import de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.dto.BuchRequest;
import de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest.dto.BuchResponse;
import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.services.BuchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BuchController {

    private final BuchService buchService;
    private final BuchDtoMapper buchDtoMapper;

    @GetMapping("/books")
    public ResponseEntity<List<BuchResponse>> getAllBuecher() {
        List<Buch> buchList = buchService.getAllBuecher();
        List<BuchResponse> responses = buchList.stream()
                .map(buchDtoMapper::toResponse)
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/books")
    public ResponseEntity<BuchResponse> createBuch(@RequestBody BuchRequest buchRequest) {
        Buch buch = buchDtoMapper.toDomain(buchRequest);
        Buch savedBuch = buchService.addBuch(buch);
        BuchResponse response = buchDtoMapper.toResponse(savedBuch);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BuchResponse> getBuch(@PathVariable Long id) {
        return ResponseEntity.ok(buchDtoMapper.toResponse(buchService.getBuchById(id)));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBuch(@PathVariable Long id) {
        buchService.removeBuch(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<BuchResponse> patchBuch(@PathVariable Long id, @RequestBody BuchRequest buchRequest) {
        Buch buch = buchDtoMapper.toDomain(buchRequest);
        Buch updated = buchService.patchBuch(id, buch);
        return ResponseEntity.ok(buchDtoMapper.toResponse(updated));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BuchResponse> updateBuch(@PathVariable Long id, @RequestBody BuchRequest buchRequest) {
        Buch buch = buchDtoMapper.toDomain(buchRequest);
        Buch updated = buchService.updateBuch(id, buch);
        return ResponseEntity.ok(buchDtoMapper.toResponse(updated));
    }
}
