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
    public Buch addBuch(AddBuchCommand command) {
        Buch buch = new Buch(null, command.titel(), command.autor(), command.isbn(), false, Optional.empty());
        return buchRepository.save(buch);
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
    public Buch patchBuch(PatchBuchCommand command) {
        Buch buch = buchRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException(command.id()));

        command.titel().ifPresent(t -> buch.setTitel(t));
        command.autor().ifPresent(a -> buch.setAutor(a));
        command.isbn().ifPresent(i -> buch.setIsbn(i));
        return buchRepository.save(buch);
    }

    @Override
    public Buch updateBuch(UpdateBuchCommand command) {
        Buch buch = buchRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException(command.id()));

        buch.setTitel(command.titel());
        buch.setAutor(command.autor());
        buch.setIsbn(command.isbn());
        return buchRepository.save(buch);
    }
}
