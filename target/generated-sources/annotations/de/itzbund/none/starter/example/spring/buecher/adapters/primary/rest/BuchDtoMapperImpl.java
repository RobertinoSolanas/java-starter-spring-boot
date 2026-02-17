package de.itzbund.none.starter.example.spring.buecher.adapters.primary.rest;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import de.itzbund.none.starter.example.spring.buecher.application.ports.primary.BuchUseCases;
import de.itzbund.openapi.example_api.BuchAnlegenRequest;
import de.itzbund.openapi.example_api.BuchBearbeitenRequest;
import de.itzbund.openapi.example_api.BuchKomplettBearbeitenRequest;
import de.itzbund.openapi.example_api.GetBuchResponse;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T19:45:14+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class BuchDtoMapperImpl implements BuchDtoMapper {

    @Override
    public GetBuchResponse toResponse(Buch buch) {
        if ( buch == null ) {
            return null;
        }

        GetBuchResponse getBuchResponse = new GetBuchResponse();

        getBuchResponse.setTitel( buch.getTitel() );
        getBuchResponse.setAutor( buch.getAutor() );
        getBuchResponse.setId( buch.getId() );
        getBuchResponse.setIsbn( buch.getIsbn() );

        return getBuchResponse;
    }

    @Override
    public BuchUseCases.AddBuchCommand toCommand(BuchAnlegenRequest request) {
        if ( request == null ) {
            return null;
        }

        String titel = null;
        String autor = null;
        String isbn = null;

        titel = request.getTitel();
        autor = request.getAutor();
        isbn = request.getIsbn();

        BuchUseCases.AddBuchCommand addBuchCommand = new BuchUseCases.AddBuchCommand( titel, autor, isbn );

        return addBuchCommand;
    }

    @Override
    public BuchUseCases.PatchBuchCommand toCommand(Long id, BuchBearbeitenRequest request) {
        if ( id == null && request == null ) {
            return null;
        }

        Optional<String> titel = null;
        Optional<String> autor = null;
        Optional<String> isbn = null;
        if ( request != null ) {
            titel = toOptionalString( request.getTitel() );
            autor = toOptionalString( request.getAutor() );
            isbn = toOptionalString( request.getIsbn() );
        }
        Long id1 = null;
        id1 = id;

        BuchUseCases.PatchBuchCommand patchBuchCommand = new BuchUseCases.PatchBuchCommand( id1, titel, autor, isbn );

        return patchBuchCommand;
    }

    @Override
    public BuchUseCases.UpdateBuchCommand toCommand(Long id, BuchKomplettBearbeitenRequest request) {
        if ( id == null && request == null ) {
            return null;
        }

        String titel = null;
        String autor = null;
        String isbn = null;
        if ( request != null ) {
            titel = request.getTitel();
            autor = request.getAutor();
            isbn = request.getIsbn();
        }
        Long id1 = null;
        id1 = id;

        BuchUseCases.UpdateBuchCommand updateBuchCommand = new BuchUseCases.UpdateBuchCommand( id1, titel, autor, isbn );

        return updateBuchCommand;
    }
}
