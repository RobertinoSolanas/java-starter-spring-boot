# Projektstruktur je Bounded Context

- adapters
  - primary
    - rest [**Buch**Controller]
    - [package-info]
  - secondary
    - database [**Buch**Mapper] [H2**Buch**Repository] [Jpa**Buch**Entity] [SpringDataJpa**Buch**Repository]
    - local [InMemory**Buch**Repository]
    - [package-info]
- application
  - domain
    - [**Buch**]
  - ports
    - primary [UseCases]
    - secondary [**Buch**Repository]
  - services [**Buch**Service]
  - [package-info]

# Code-snippets

## Application

```java
@org.jmolecules.architecture.hexagonal.Application
package de.itzbund.none.starter.example.spring.buecher.application;
```

### Domain

```java
public class Buch {
```

### Ports

#### UseCase (Primary Port)

```java
@PrimaryPort
public record AddBuchCommand(String titel, String autor) {
}
```

#### Buch-Repository (Secondary Port)

```java
@SecondaryPort
public interface BuchRepository {
```

### Service

```java
@Service
public class BuchService implements BuchUseCases {
```

## Adapter

### Primary Adapter

```java
@org.jmolecules.architecture.hexagonal.PrimaryAdapter
package de.itzbund.none.starter.example.spring.buecher.adapters.primary;
```

#### Rest

##### Controller

```java
@RestController
@RequestMapping("/api/buecher")
public class BuchController {
    @PostMapping
    public ResponseEntity<BuchResponse> addBuch(@RequestBody CreateBuchRequest request) {
      Buch createdBuch = addBuchUseCase.addBuch(new AddBuchCommand(request.getTitel(), request.getAutor()));
      // return new ResponseEntity<>(BuchResponse.from(createdBuch), HttpStatus.CREATED);
      return new ResponseEntity<>(mapper.toResponse(createdBuch), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BuchResponse>> listVerfuegbareBuecher() {
      List<Buch> buchList = getBuchsQuery.getVerfuegbareBuecher();
      List<BuchResponse> responses = buchList.stream().map(buch -> mapper.toResponse(buch)).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
```

##### DTO-Mapper

```java

import de.itzbund.none.starter.example.spring.Buch;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuchDtoMapper {

  @Mapping(target = "ausgeliehenVon", qualifiedByName = "mapOptionalToLong")
  BuchResponse toResponse(Buch buch);

  @Named("mapOptionalToLong")
  default Long mapOptionalToLong(java.util.Optional<Long> value) {
    return value.orElse(null);
  }
}
```

### Secondary Adapter

```java
@org.jmolecules.architecture.hexagonal.SecondaryAdapter
package de.itzbund.none.starter.example.spring.buecher.adapters.secondary;
```

#### Database

##### JPA-Entity

```java
@Entity
@Table(name = "buch")
public class JpaBuchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

##### DTO-Mapper

```java
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JpaBuchMapper {

  @Mapping(target = "ausgeliehenVon", qualifiedByName = "mapOptionalToLong")
  JpaBuchEntity toJpaEntity(Buch buch);

    @Named("mapOptionalToLong")
    default Long mapOptionalToLong(java.util.Optional<Long> value) {
        return value.orElse(0L);
    }

  @Mapping(target = "ausgeliehenVon", expression = "java(entity.isAusgeliehen() ? java.util.Optional.of(entity.getAusgeliehenVon()) : java.util.Optional.empty())")
  Buch toDomain(JpaBuchEntity entity);
}
```

##### H2-Repository

```java

@Repository
@Profile("h2")
public class H2BuchRepository implements BuchRepository {
  private final SpringDataJpaBuchRepository jpaRepository;
  private final JpaBuchMapper buchMapper;

  public H2BuchRepository(SpringDataJpaBuchRepository jpaRepository, JpaBuchMapper buchMapper) {
        this.jpaRepository = jpaRepository;
    this.buchMapper = buchMapper;
    }
```

##### SpringDataJpaRepository

```java
public interface SpringDataJpaBuchRepository extends JpaRepository<JpaBuchEntity, Long> {
}
```

#### Local

##### InMemory-Repository

```java
@Repository
@Profile("in-memory")
public class InMemoryBuchRepository implements BuchRepository {
  private final Map<Long, Buch> buchStore = new HashMap<>();
```