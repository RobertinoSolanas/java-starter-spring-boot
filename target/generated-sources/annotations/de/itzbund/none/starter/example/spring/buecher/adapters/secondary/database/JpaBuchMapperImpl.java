package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T18:15:18+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.45.0.v20260128-0750, environment: Java 21.0.9 (Eclipse Adoptium)"
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
        jpaBuchEntity.setAusgeliehen( buch.isAusgeliehen() );
        jpaBuchEntity.setAutor( buch.getAutor() );
        jpaBuchEntity.setId( buch.getId() );
        jpaBuchEntity.setIsbn( buch.getIsbn() );
        jpaBuchEntity.setTitel( buch.getTitel() );

        return jpaBuchEntity;
    }

    @Override
    public Buch toDomain(JpaBuchEntity entity) {
        if ( entity == null ) {
            return null;
        }

        boolean ausgeliehen = false;
        String autor = null;
        String isbn = null;
        String titel = null;
        Long id = null;

        ausgeliehen = entity.isAusgeliehen();
        autor = entity.getAutor();
        isbn = entity.getIsbn();
        titel = entity.getTitel();
        id = entity.getId();

        Optional<Long> ausgeliehenVon = entity.isAusgeliehen() ? java.util.Optional.of(entity.getAusgeliehenVon()) : java.util.Optional.empty();

        Buch buch = new Buch( id, titel, autor, isbn, ausgeliehen, ausgeliehenVon );

        return buch;
    }
}
