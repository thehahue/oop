# OOP-Einstieg mit Java – Übungen rund um die Schule

Dieses Maven-Projekt enthält sieben aufeinander aufbauende Übungen für den Einstieg in die objektorientierte Programmierung (OOP). Alle Beispiele sind ausführbar und enthalten `TODO`-Kommentare zum selbstständigen Weiterarbeiten.

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
- [Übung 6 – Kursverwaltung](diagramme/uebung06_kursverwaltung.drawio)
- [Übung 7 – Benutzeroberfläche](diagramme/uebung07_benutzeroberflaeche.drawio)

Die Startklassen `Uebung01` bis `Uebung07` sind als Abhängigkeiten eingezeichnet. Vererbung wird mit einer durchgezogenen Linie und einer leeren Dreiecksspitze dargestellt; die Implementierung eines Interfaces zusätzlich mit einer gestrichelten Linie.

## Übung 6 – Kursverwaltung mit begrenzten Plätzen

Übung 6 verwendet die Klassen `Schule`, `Schulperson`, `Lehrkraft`, `Schueler`, `Kurs` und `SchulFabric` aus Übung 5 weiter. Neu sind eine zentrale Verwaltung mehrerer Kurse, begrenzte Kursplätze und die Suche nach Personen über ihre ID.

Lernziele:

- vorhandene Klassen durch Komposition wiederverwenden
- Kursangebote in einer `Map` verwalten
- mit `instanceof` zwischen Schulpersonen unterscheiden
- ungültige Verwaltungsoperationen mit Exceptions behandeln
- Rückgabewerte für erfolgreiche und abgelehnte Anmeldungen auswerten

Starte die Übung über `at.bbrz.oop.uebung06_kursverwaltung.Uebung06`. Die Zusatzaufgaben stehen am Ende der `main`-Methode.

## Übung 7 – Eine einfache Benutzeroberfläche

Übung 7 baut mit Java Swing ein kleines Fenster für die Kursverwaltung. Ein Kurs kann ausgewählt und eine Schüler-ID eingegeben werden. Die Buttons melden den Schüler an oder ab und geben die aktuelle Kursübersicht aus; das Protokoll zeigt das Ergebnis und verständliche Fehlermeldungen an. Die eigentliche Fachlogik bleibt dabei in der `Schulverwaltung` aus Übung 6.

Lernziele:

- ein Fenster mit `JFrame` erstellen
- Eingabefelder, Buttons und ein Ausgabefeld anordnen
- mit Action Listenern auf Klicks reagieren
- Texteingaben in Zahlen umwandeln und Fehler behandeln
- Benutzeroberfläche und Fachlogik voneinander trennen

Starte die Übung über `at.bbrz.oop.uebung07_benutzeroberflaeche.Uebung07`. Die Zusatzaufgaben stehen in der Klasse `SchulverwaltungFenster`.
