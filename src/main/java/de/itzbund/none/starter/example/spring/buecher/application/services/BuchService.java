package de.itzbund.none.starter.example.spring.buecher.application.services;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.BusinessException;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.NotFoundException;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.UnauthorizedException;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchQueries;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases;
import de.itzbund.none.starter.example.spring.buecher.application.ports.secondary.BuchRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Domain Service für die Verwaltung von Buch Objekten
 */
@Service
@Transactional
public class BuchService implements BuchUseCases, BuchQueries {

    private final BuchRepository buchRepository;

    /**
     * BuchService Konstruktor
     */
    public BuchService(BuchRepository buchRepository) {
        this.buchRepository = buchRepository;
    }

    @Override
    public Buch addBuch(Buch buch) {
        // Ensure ID is null for new book
        // But Buch is immutable-ish (except setters now).
        // We can create a new one or just save it if ID is null.
        // The repository might generate ID.
        // Let's ensure new state.
        Buch newBuch = new Buch(null, buch.getTitel(), buch.getAutor(), buch.getIsbn(), buch.getJahr(), false,
                Optional.empty());
        return buchRepository.save(newBuch);
    }

    @Override
    public void buchZurueckgeben(Long buchId, Long userId) {
        Buch buch = buchRepository.findById(buchId)
                .orElseThrow(() -> new NotFoundException(buchId));

        if (buch.zurueckgeben(userId)) {
            buchRepository.save(buch);
        } else {
            throw new UnauthorizedException(buchId, userId, "return");
        }
    }

    @Override
    public void buchAusleihen(Long buchId, Long userId) {
        Buch buch = buchRepository.findById(buchId)
                .orElseThrow(() -> new NotFoundException(buchId));

        if (buch.ausleihen(userId)) {
            buchRepository.save(buch);
        } else {
            throw new BusinessException(buchId, "Book is already borrowed", "BOOK_ALREADY_BORROWED");
        }
    }

    @Override
    public List<Buch> getVerfuegbareBuecher() {
        return buchRepository.findAllVerfuegbar();
    }

    @Override
    public List<Buch> getAusgelieheneBuecher() {
        return buchRepository.findAllAusgeliehen();
    }

    @Override
    public List<Buch> getAllBuecher() {
        return buchRepository.findAll();
    }

    @Override
    public void removeBuch(Long buchId) {
        Buch buch = buchRepository.findById(buchId)
                .orElseThrow(() -> new NotFoundException(buchId));

        if (buch.isAusgeliehen()) {
            throw new BusinessException(buchId, "Cannot remove a borrowed book", "BOOK_IS_BORROWED");
        }

        buchRepository.deleteById(buchId);
    }

    @Override
    public Buch getBuchById(Long id) {
        return buchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Buch patchBuch(Long id, Buch updateInfo) {
        Buch buch = buchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        if (updateInfo.getTitel() != null)
            buch.setTitel(updateInfo.getTitel());
        if (updateInfo.getAutor() != null)
            buch.setAutor(updateInfo.getAutor());
        if (updateInfo.getIsbn() != null)
            buch.setIsbn(updateInfo.getIsbn());
        // For int jahr, we can't easily check 'null' unless we use Integer or a
        // specific flag.
        // Assuming 0 means "not set" or "don't update" if it's a primitive.
        // But if the user wants to set year 0, that's an issue.
        // For now, let's assume we update year if it's > 0.
        if (updateInfo.getJahr() > 0)
            buch.setJahr(updateInfo.getJahr());

        return buchRepository.save(buch);
    }

    @Override
    public Buch updateBuch(Long id, Buch updateInfo) {
        Buch buch = buchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        buch.setTitel(updateInfo.getTitel());
        buch.setAutor(updateInfo.getAutor());
        buch.setIsbn(updateInfo.getIsbn());
        buch.setJahr(updateInfo.getJahr());
        return buchRepository.save(buch);
    }
}
