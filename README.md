# OOP-Einstieg mit Java – Übungen rund um die Schule

Dieses Maven-Projekt enthält fünf aufeinander aufbauende Übungen für den Einstieg in die objektorientierte Programmierung (OOP). Alle Beispiele sind ausführbar und enthalten `TODO`-Kommentare zum selbstständigen Weiterarbeiten.

## Lerngrundlage

Als begleitendes Skript dient [Einführung in das Programmieren mit Java 21](https://bebagoe.de/wp-content/uploads/java21.pdf) von Bernhard Baltes-Götz und Johannes Götz. Besonders passend sind:

- Kapitel 5 „Klassen und Objekte“ (ab Seite 209), insbesondere Datenkapselung ab Seite 210
- Kapitel 8 „Vererbung und Polymorphie“ (ab Seite 423)
- Kapitel 10 „Interfaces“ (ab Seite 483)

Die Seitenzahlen beziehen sich auf die im Inhaltsverzeichnis des Skripts angegebenen Seiten, nicht zwingend auf die PDF-Seitenanzeige.

## Voraussetzungen und Start

- JDK 21
- Maven 3

Projekt kompilieren:

```bash
mvn compile
```

## Klassendiagramme

Die Diagramme lassen sich mit [draw.io / diagrams.net](https://app.diagrams.net/) öffnen und bearbeiten:

- [Übung 1 – Datenkapselung](diagramme/uebung01_datenkapselung.drawio)
- [Übung 2 – Vererbung](diagramme/uebung02_vererbung.drawio)
- [Übung 3 – Polymorphie](diagramme/uebung03_polymorphie.drawio)
- [Übung 4 – Interfaces](diagramme/uebung04_interfaces.drawio)
- [Übung 5 – Schulverwaltung](diagramme/uebung05_schulverwaltung.drawio)

Die Startklassen `Uebung01` bis `Uebung05` sind als Abhängigkeiten eingezeichnet. Vererbung wird mit einer durchgezogenen Linie und einer leeren Dreiecksspitze dargestellt; die Implementierung eines Interfaces zusätzlich mit einer gestrichelten Linie.
