package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T19:45:14+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class JpaBuchMapperImpl implements JpaBuchMapper {

    @Override
    public JpaBuchEntity toJpaEntity(Buch buch) {
        if ( buch == null ) {
            return null;
        }

        JpaBuchEntity jpaBuchEntity = new JpaBuchEntity();

        jpaBuchEntity.setAusgeliehenVon( mapOptionalToLong( buch.getAusgeliehenVon() ) );
        jpaBuchEntity.setId( buch.getId() );
        jpaBuchEntity.setTitel( buch.getTitel() );
        jpaBuchEntity.setAutor( buch.getAutor() );
        jpaBuchEntity.setIsbn( buch.getIsbn() );
        jpaBuchEntity.setAusgeliehen( buch.isAusgeliehen() );

        return jpaBuchEntity;
    }

    @Override
    public Buch toDomain(JpaBuchEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String titel = null;
        String autor = null;
        String isbn = null;
        boolean ausgeliehen = false;
        Long id = null;

        titel = entity.getTitel();
        autor = entity.getAutor();
        isbn = entity.getIsbn();
        ausgeliehen = entity.isAusgeliehen();
        id = entity.getId();

        Optional<Long> ausgeliehenVon = entity.isAusgeliehen() ? java.util.Optional.of(entity.getAusgeliehenVon()) : java.util.Optional.empty();

        Buch buch = new Buch( id, titel, autor, isbn, ausgeliehen, ausgeliehenVon );

        return buch;
    }
}
