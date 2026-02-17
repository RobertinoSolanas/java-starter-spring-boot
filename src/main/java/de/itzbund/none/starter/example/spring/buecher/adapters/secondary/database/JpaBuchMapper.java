package de.itzbund.none.starter.example.spring.buecher.adapters.secondary.database;

import de.itzbund.none.starter.example.spring.buecher.application.domain.Buch;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

/**
 * Entity-Mapper für Buch Objekte
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JpaBuchMapper {

    /**
     * Mappt ein Buch auf eine JpaBuchEntity
     */
    @Mapping(target = "ausgeliehenVon", qualifiedByName = "mapOptionalToLong")
    JpaBuchEntity toJpaEntity(Buch buch);


    /**
     * Hilfsmethode zum Mappen von Long auf ein passendes Optional
     */
    @Named("mapOptionalToLong")
    default Long mapOptionalToLong(java.util.Optional<Long> value) {
        return value.orElse(null);
    }

    /**
     * Mappt eine JpaBuchEntity auf ein Buch
     */
    @Mapping(target = "ausgeliehenVon", expression = """
            java(entity.isAusgeliehen() ? java.util.Optional.of(entity.getAusgeliehenVon()) : java.util.Optional.empty())""")
    @BeanMapping(ignoreUnmappedSourceProperties = "ausgeliehenVon")
    Buch toDomain(JpaBuchEntity entity);
}