# Java-Starter – Spring Boot 3 (Hexagonal Architecture)

Willkommen im **java-starter-spring-boot**-Repository.

Diese Vorlage bietet einen klar strukturierten Startpunkt für Java-Teams, die mit **Spring Boot 3** und dem Prinzip der **Hexagonalen Architektur** (Ports & Adapters) arbeiten möchten.

> **Motivation:**  
> Der Java-Starter wurde entwickelt, um die **Architekturvorgaben des ITZBund** von Anfang an zu berücksichtigen – inklusive Trennung von Domäne und Infrastruktur, Testbarkeit und klaren Erweiterungspunkten. Teams sparen sich die Grundkonfiguration und können direkt fachlich einsteigen.

## Warum dieses Repo?

✅ **Bewährter Stack** – Spring Boot 3.4.4, JDK 21 (LTS)  
✅ **Saubere Architektur** – strikt nach Ports & Adapters (Hexagonal)  
✅ **Sofort lauffähig** – In-Memory-H2-DB, Dev-Profil, Demo-Daten  
✅ **Produktivitäts-Booster** – MapStruct, Lombok, OpenAPI out-of-the-box  
✅ **CI-tauglich** – Maven Wrapper, reproducible builds, Vorbereitung für Containerisierung  

## Inhalt

1. [Vorbereitung](#vorbereitung)  
2. [Schnellstart](#schnellstart)  
3. [Build & Tests](#build--tests)  
4. [Architekturübersicht](#architekturübersicht)  
5. [Wichtige Dependencies](#wichtige-dependencies)  
6. [API-Dokumentation](#api-dokumentation)  
7. [Lokal entwickeln](#lokal-entwickeln)  
8. [Weiterentwicklung](#weiterentwicklung)  
9. [Troubleshooting](#troubleshooting)  
10. [Ansprechpartner](#ansprechpartner)

## Vorbereitung

| Tool       | Empfohlene Version | Zweck                   |
| ---------- | ------------------ | ----------------------- |
| **JDK**    | 21 (LTS)           | Laufzeit & Kompilierung |
| **Maven**  | ≥ 3.9              | Build-Tool              |
| **Docker** | optional           | Container-Ausführung    |

> Kein Maven installiert? Nutze den **Maven Wrapper** (`./mvnw`, `mvnw.cmd`) – alle Befehle funktionieren identisch.

## Schnellstart

```bash
# 1. Repository klonen
git clone https://bcepublic.cloud.bcsv.in.bund.de/scm/repo/itzbund-devops/java-starter-spring-boot.git 
cd java-starter-spring-boot

# 2. Anwendung im Dev-Profil starten
./mvnw spring-boot:run -Dspring-boot.run.profiles=in-memory
````

🟢 Die App läuft nun unter [http://localhost:8080/](http://localhost:8080/)
🗄️ H2-In-Memory-Datenbank ist aktiv – keine externe Infrastruktur nötig.

## Build & Tests

```bash
# Kompilieren, Testen, Analysieren
./mvnw clean verify
```

➡️ Erzeugt ein **Fat JAR** mit allen Abhängigkeiten:

```bash
java -jar target/java-starter-spring-boot-*.jar
```

## Architekturübersicht

Diese Vorlage implementiert das [**Hexagonal Architecture Pattern**](https://alistair.cockburn.us/hexagonal-architecture/), auch bekannt als **Ports-and-Adapters**.

```
┌──────────────────────────────── Application Core ────────────────────────────────┐
│                           (framework-frei, testbar)                              │
│  ┌──────────── Domain ────────────┐     ┌──────────── Use-Cases ───────────┐      │
│  │  Aggregates, Entities, Value   │ ⇆  │  Business-Flows, Orchestrierung   │      │
│  │  Objects, Domain Events        │     └───────────────┬──────────────────┘      │
│  └────────────────────────────────┘                     │ Ports (Interfaces)      │
└─────────────────────────────────────────────────────────┴──────────────────────────┘
                 ▲ Inbound Ports                  ▼ Outbound Ports
┌───────────────────────┐                   ┌────────────────────────────┐
│ REST Adapter (Spring) │                   │ JPA Adapter (Hibernate/H2) │
└───────────────────────┘                   └────────────────────────────┘
```

### Bausteine:

* **Domain** – reine Geschäftslogik, 100 % framework-frei
* **Application** – orchestrierende Use-Cases + Ports
* **Inbound Adapter** – REST, Messaging, CLI, etc.
* **Outbound Adapter** – JPA, Feign, externe Dienste

Annotationen aus `jmolecules-hexagonal-architecture` markieren Ports und Adapter → ideal für Diagramm-Generierung.

## Wichtige Dependencies

| Artifact                              | Zweck                                    |
| ------------------------------------- | ---------------------------------------- |
| `spring-boot-starter-web`             | REST-API, Embedded Tomcat                |
| `spring-boot-starter-data-jpa`        | JPA/Hibernate Integration                |
| `h2`                                  | In-Memory-Datenbank (Dev & Tests)        |
| `jmolecules-hexagonal-architecture`   | Marker-Annotationen für Ports/Adapter    |
| `mapstruct` + `mapstruct-processor`   | Typ-sicheres DTO ↔ Domain-Mapping        |
| `lombok` + `lombok-mapstruct-binding` | Boilerplate-Reduktion, MapStruct-Support |
| `springdoc-openapi-starter-webmvc-ui` | Automatische OpenAPI-Generierung         |

Alle Versionen sind zentral in der `pom.xml` gepflegt.

## API-Dokumentation

Beim Start wird automatisch eine OpenAPI-Spezifikation erzeugt:

* **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* **JSON-Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Lokal entwickeln

* **Live Reload** – via `spring-boot-devtools` im `in-memory`-Profil aktivierbar
* **H2-Konsole:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

  * JDBC-URL: `jdbc:h2:mem:testdb`
* **Profile:**

  * `in-memory` – Default, H2 + Debug-Logging
  * `prod` – für produktionsnahe Konfigurationen (z. B. PostgreSQL)

## Weiterentwicklung

1. **Use-Case & Ports** definieren (Interface im Application Layer)
2. **Domäne implementieren** (Entities, Value Objects, Business-Logik)
3. **Adapter bauen**

   * inbound: REST, Messaging, CLI …
   * outbound: JPA, HTTP, Feign …
4. **MapStruct-Mapping** implementieren
5. **H2 durch Produktions-DB ersetzen**
6. **Tests schreiben:**

   * Unit-Tests für Domain & Use-Cases
   * SpringBootTests für Endpunkte & Datenbankzugriffe

## Troubleshooting

| Problem              | Mögliche Ursache & Lösung                                         |
| -------------------- | ----------------------------------------------------------------- |
| Port 8080 belegt     | `SERVER_PORT=8081 ./mvnw spring-boot:run …`                       |
| H2-Konsole 404       | `spring.h2.console.enabled=true` im Profil oder `application.yml` |
| Lombok-Fehler in IDE | Lombok-Plugin installieren & Annotation-Processing aktivieren     |

## Ansprechpartner

Bei Fragen, Feedback oder Vorschlägen:

📧 **Kontakt:**
`Mattermost` - https://mm.itz.in.bund.de/itzbund/channels/jsts-support

🧩 **Hinweis zu Docker:**
Ein `Dockerfile` ist aktuell **nicht enthalten**. Die Containerisierung ist in Planung und wird in Abstimmung mit den beteiligten Teams erarbeitet. Bei Bedarf bitte Rücksprache mit den DevOps- oder Architekturverantwortlichen halten.


# /ask create a simple gui only using html css javascript for all provided service operations it is included in the service and starts with the service 
# go ahead