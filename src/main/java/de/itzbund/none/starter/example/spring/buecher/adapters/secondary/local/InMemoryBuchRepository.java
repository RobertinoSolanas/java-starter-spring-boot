package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.local;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.ports.secondary.BuchRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * In-Memory Repository für Buch Objekte
 */
@Repository
@Profile("in-memory")
public class InMemoryBuchRepository implements BuchRepository {

        private final ConcurrentMap<Long, Buch> buchStore = new ConcurrentHashMap<>();
        private long currentId = 0L;

        /**
         * InMemoryBuchRepository Konstruktor
         */
        public InMemoryBuchRepository() {
                super();
                initialize();
        }

        @Override
        public Buch save(Buch buch) {
                if (buch.getId() == null) {
                        currentId++;
                }
                Buch persistedBuch = new Buch(buch.getId() == null ? currentId : buch.getId(), buch.getTitel(),
                                buch.getAutor(), buch.getIsbn(), buch.getJahr(), buch.isAusgeliehen(),
                                buch.getAusgeliehenVon());
                buchStore.put(persistedBuch.getId(), persistedBuch);
                return persistedBuch;
        }

        @Override
        public Optional<Buch> findById(Long id) {
                return Optional.ofNullable(buchStore.get(id));
        }

        @Override
        public void deleteById(Long id) {
                buchStore.remove(id);
        }

        @Override
        public List<Buch> findAllVerfuegbar() {
                return buchStore.values().stream()
                                .filter(buch -> !buch.isAusgeliehen())
                                .collect(Collectors.toList());
        }

        @Override
        public List<Buch> findAllAusgeliehen() {
                return buchStore.values().stream()
                                .filter(Buch::isAusgeliehen)
                                .collect(Collectors.toList());
        }

        @Override
        public List<Buch> findAll() {
                return buchStore.values().stream()
                                .collect(Collectors.toList());
        }

        private void initialize() {
                currentId++;
                buchStore.put(currentId,
                                new Buch(currentId, "Die 13½ Leben des Käpt’n Blaubär", "Walter Moers",
                                                "978-3-328-60120-3", 1999,
                                                false, Optional.empty()));
                currentId++;
                buchStore.put(currentId,
                                new Buch(currentId, "Die kleine Raupe Nimmersatt", "Eric Carle", "978-3-7891-6764-5",
                                                1969, false,
                                                Optional.empty()));
                currentId++;
                buchStore.put(currentId,
                                new Buch(currentId, "Der Räuber Hotzenplotz", "Otfried Preußler", "978-3-7891-2025-1",
                                                1962, false,
                                                Optional.empty()));
                currentId++;
                buchStore.put(currentId,
                                new Buch(currentId, "Das Sams", "Paul Maar", "978-3-7891-2026-8", 1973, false,
                                                Optional.empty()));
                currentId++;
                buchStore.put(currentId,
                                new Buch(currentId, "Pippi Langstrumpf", "Astrid Lindgren", "978-3-7891-2027-5", 1945,
                                                false,
                                                Optional.empty()));
                currentId++;
                buchStore.put(currentId,
                                new Buch(currentId, "Die wilden Kerle", "Joachim Masannek", "978-3-401-02260-7", 2003,
                                                false,
                                                Optional.empty()));
        }
}
