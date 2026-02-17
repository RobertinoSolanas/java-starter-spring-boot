package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.ports.secondary.BuchRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA Repository Implementierung für Buch Objekte
 */
@Repository
@Profile("h2")
public class JpaBuchRepository implements BuchRepository {

    private final SpringDataJpaBuchRepository jpaRepository;
    private final JpaBuchMapper buchMapper;

    /**
     * JpaBuchRepository Konstruktor
     */
    public JpaBuchRepository(SpringDataJpaBuchRepository jpaRepository, JpaBuchMapper buchMapper) {
        this.jpaRepository = jpaRepository;
        this.buchMapper = buchMapper;
    }

    @Override
    public Buch save(Buch buch) {
        JpaBuchEntity entity = buchMapper.toJpaEntity(buch);
        JpaBuchEntity savedEntity = jpaRepository.save(entity);
        return buchMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Buch> findById(Long id) {
        return jpaRepository.findById(id)
                .map(buchMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Buch> findAllVerfuegbar() {
        return jpaRepository.findAll().stream()
                .filter(entity -> !entity.isAusgeliehen())
                .map(buchMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Buch> findAllAusgeliehen() {
        return jpaRepository.findAll().stream()
                .filter(JpaBuchEntity::isAusgeliehen)
                .map(buchMapper::toDomain)
                .collect(Collectors.toList());
    }
}