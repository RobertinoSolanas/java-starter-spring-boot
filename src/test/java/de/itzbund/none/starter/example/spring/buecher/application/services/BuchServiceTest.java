package de.itzbund.none.starter.example.spring.buecher.application.services;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.BusinessException;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.NotFoundException;
import de.itzbund.none.starter.example.spring.buecher.application.exceptions.UnauthorizedException;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases.AddBuchCommand;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases.UpdateBuchCommand;
import de.itzbund.none.starter.example.spring.buecher.application.ports.secondary.BuchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * BuchService Test
 */
@ExtendWith(MockitoExtension.class)
class BuchServiceTest {

    @Mock
    private BuchRepository buchRepository;

    private BuchService buchService;

    @BeforeEach
    void setUp() {
        buchService = new BuchService(buchRepository);
    }

    @Test
    void addBuch() {
        // Given
        String titel = "Clean Code";
        String autor = "Robert C. Martin";
        String isbn = "9780132350884";
        AddBuchCommand command = new AddBuchCommand(titel, autor, isbn);

        Buch savedBuch = new Buch(1L, titel, autor, isbn, false, Optional.empty());
        when(buchRepository.save(any(Buch.class))).thenReturn(savedBuch);

        // When
        Buch result = buchService.addBuch(command);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(titel, result.getTitel());
        assertEquals(autor, result.getAutor());
        assertEquals(isbn, result.getIsbn());

        ArgumentCaptor<Buch> buchCaptor = ArgumentCaptor.forClass(Buch.class);
        verify(buchRepository).save(buchCaptor.capture());
        Buch capturedBuch = buchCaptor.getValue();

        assertNull(capturedBuch.getId());
        assertEquals(titel, capturedBuch.getTitel());
        assertEquals(autor, capturedBuch.getAutor());
        assertEquals(isbn, capturedBuch.getIsbn());
        assertFalse(capturedBuch.isAusgeliehen());
        assertTrue(capturedBuch.getAusgeliehenVon().isEmpty());
    }

    @Test
    void buchAusleihen_whenVerfuegbar() {
        // Given
        Long buchId = 1L;
        Long userId = 100L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884", false, Optional.empty());

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));
        when(buchRepository.save(any(Buch.class))).thenReturn(buch);

        // When
        buchService.buchAusleihen(buchId, userId);

        // Then
        ArgumentCaptor<Buch> buchCaptor = ArgumentCaptor.forClass(Buch.class);
        verify(buchRepository).save(buchCaptor.capture());
        Buch capturedBuch = buchCaptor.getValue();

        assertTrue(capturedBuch.isAusgeliehen());
        assertEquals(userId, capturedBuch.getAusgeliehenVon().get());
    }

    @Test
    void buchAusleihen_whenAlreadyAusgeliehen() {
        // Given
        Long buchId = 1L;
        Long userId = 100L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884",
                true, Optional.of(200L));

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));

        // When & Then
        Exception exception = assertThrows(BusinessException.class,
                () -> buchService.buchAusleihen(buchId, userId));

        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    void buchZurueckgeben_whenAusgeliehenVonSameUser() {
        // Given
        Long buchId = 1L;
        Long userId = 100L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884",
                true, Optional.of(userId));

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));
        when(buchRepository.save(any(Buch.class))).thenReturn(buch);

        // When
        buchService.buchZurueckgeben(buchId, userId);

        // Then
        ArgumentCaptor<Buch> buchCaptor = ArgumentCaptor.forClass(Buch.class);
        verify(buchRepository).save(buchCaptor.capture());
        Buch capturedBuch = buchCaptor.getValue();

        assertFalse(capturedBuch.isAusgeliehen());
        assertTrue(capturedBuch.getAusgeliehenVon().isEmpty());
    }

    @Test
    void buchZurueckgeben_whenAusgeliehenVonDifferentUser() {
        // Given
        Long buchId = 1L;
        Long buchAusgeliehenVonUserId = 200L;
        Long attemptingUserId = 100L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884",
                true, Optional.of(buchAusgeliehenVonUserId));

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));

        // When & Then
        Exception exception = assertThrows(UnauthorizedException.class,
                () -> buchService.buchZurueckgeben(buchId, attemptingUserId));

        assertEquals("User 100 is not authorized to return entity 1", exception.getMessage());
    }

    @Test
    void getVerfuegbareBuecher() {
        // Given
        List<Buch> verfuegbareBuecher = Arrays.asList(
                new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", false, Optional.empty()),
                new Buch(2L, "Clean Architecture", "Robert C. Martin", "9780134494166", false, Optional.empty()));

        when(buchRepository.findAllVerfuegbar()).thenReturn(verfuegbareBuecher);

        // When
        List<Buch> result = buchService.getVerfuegbareBuecher();

        // Then
        assertEquals(2, result.size());
        verify(buchRepository).findAllVerfuegbar();
    }

    @Test
    void getAusgelieheneBuecher() {
        // Given
        List<Buch> ausgelieheneBuecher = Arrays.asList(
                new Buch(1L, "Clean Code", "Robert C. Martin", "9780132350884", true, Optional.of(100L)),
                new Buch(2L, "Clean Architecture", "Robert C. Martin", "9780134494166", true, Optional.of(200L)));

        when(buchRepository.findAllAusgeliehen()).thenReturn(ausgelieheneBuecher);

        // When
        List<Buch> result = buchService.getAusgelieheneBuecher();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.get(0).isAusgeliehen());
        assertTrue(result.get(1).isAusgeliehen());
        verify(buchRepository).findAllAusgeliehen();
    }

    @Test
    void removeBuch_whenVerfuegbar() {
        // Given
        Long buchId = 1L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884", false, Optional.empty());

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));

        // When
        buchService.removeBuch(buchId);

        // Then
        verify(buchRepository).deleteById(buchId);
    }

    @Test
    void removeBuch_whenAusgeliehen() {
        // Given
        Long buchId = 1L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884",
                true, Optional.of(100L));

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));

        // When & Then
        Exception exception = assertThrows(BusinessException.class, () -> buchService.removeBuch(buchId));

        assertEquals("Cannot remove a borrowed book", exception.getMessage());
    }

    @Test
    void getBuchById() {
        // Given
        Long buchId = 1L;
        Buch buch = new Buch(buchId, "Clean Code", "Robert C. Martin", "9780132350884", false, Optional.empty());

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(buch));

        // When
        Buch result = buchService.getBuchById(buchId);

        // Then
        assertNotNull(result);
        assertEquals(buchId, result.getId());
    }

    @Test
    void getBuchById_whenNotFound() {
        // Given
        Long buchId = 1L;
        when(buchRepository.findById(buchId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(NotFoundException.class, () -> buchService.getBuchById(buchId));

        assertTrue(exception.getMessage().contains("Entity with ID 1 not found"));
    }

    @Test
    void patchBuch() {
        // Given
        Long buchId = 1L;
        String originalTitel = "Clean Code";
        String originalAutor = "Robert C. Martin";
        String originalIsbn = "9780132350884";

        String newTitel = "Clean Code: A Handbook of Agile Software Craftsmanship";

        Buch existingBuch = new Buch(buchId, originalTitel, originalAutor, originalIsbn, false, Optional.empty());
        Buch updatedBuch = new Buch(buchId, newTitel, originalAutor, originalIsbn, false, Optional.empty());

        BuchUseCases.PatchBuchCommand command = new BuchUseCases.PatchBuchCommand(buchId, Optional.of(newTitel),
                Optional.empty(), Optional.empty());

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(existingBuch));
        when(buchRepository.save(any(Buch.class))).thenReturn(updatedBuch);

        // When
        Buch result = buchService.patchBuch(command);

        // Then
        assertNotNull(result);
        assertEquals(newTitel, result.getTitel());
        assertEquals(originalAutor, result.getAutor());
        assertEquals(originalIsbn, result.getIsbn());

        ArgumentCaptor<Buch> buchCaptor = ArgumentCaptor.forClass(Buch.class);
        verify(buchRepository).save(buchCaptor.capture());
        Buch capturedBuch = buchCaptor.getValue();

        assertEquals(newTitel, capturedBuch.getTitel());
        assertEquals(originalAutor, capturedBuch.getAutor());
        assertEquals(originalIsbn, capturedBuch.getIsbn());
    }

    @Test
    void updateBuch() {
        // Given
        Long buchId = 1L;
        String originalTitel = "Clean Code";
        String originalAutor = "Robert C. Martin";
        String originalIsbn = "9780132350884";

        String newTitel = "Clean Code: A Handbook";
        String newAutor = "Robert Cecil Martin";
        String newIsbn = "9780132350885";

        Buch existingBuch = new Buch(buchId, originalTitel, originalAutor, originalIsbn, false, Optional.empty());
        Buch updatedBuch = new Buch(buchId, newTitel, newAutor, newIsbn, false, Optional.empty());

        BuchUseCases.UpdateBuchCommand command = new UpdateBuchCommand(buchId, newTitel, newAutor, newIsbn);

        when(buchRepository.findById(buchId)).thenReturn(Optional.of(existingBuch));
        when(buchRepository.save(any(Buch.class))).thenReturn(updatedBuch);

        // When
        Buch result = buchService.updateBuch(command);

        // Then
        assertNotNull(result);
        assertEquals(newTitel, result.getTitel());
        assertEquals(newAutor, result.getAutor());
        assertEquals(newIsbn, result.getIsbn());

        ArgumentCaptor<Buch> buchCaptor = ArgumentCaptor.forClass(Buch.class);
        verify(buchRepository).save(buchCaptor.capture());
        Buch capturedBuch = buchCaptor.getValue();

        assertEquals(newTitel, capturedBuch.getTitel());
        assertEquals(newAutor, capturedBuch.getAutor());
        assertEquals(newIsbn, capturedBuch.getIsbn());
    }
}
