# OOP-Einstieg mit Java – Übungen rund um die Schule

Dieses Maven-Projekt enthält neun Übungen für den Einstieg in die objektorientierte Programmierung (OOP). Alle Beispiele sind ausführbar und enthalten `TODO`-Kommentare zum selbstständigen Weiterarbeiten.

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
- [Übung 8 – Persistenz](diagramme/uebung08_persistenz.drawio)

Die Startklassen `Uebung01` bis `Uebung08` sind als Abhängigkeiten eingezeichnet. Vererbung wird mit einer durchgezogenen Linie und einer leeren Dreiecksspitze dargestellt; die Implementierung eines Interfaces zusätzlich mit einer gestrichelten Linie.

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

Übung 7 baut mit Java Swing ein kleines Fenster für die Kursverwaltung. Kurse, Schüler und Lehrkräfte werden in ComboBoxen ausgewählt, sodass keine IDs eingegeben werden müssen. Ein modales Dialogfenster ermöglicht das Anlegen eines Kurses mit Lehrkraft und maximaler Teilnehmerzahl. Die Buttons melden Schüler an oder ab, legen Kurse an und geben die aktuelle Kursübersicht aus; das Protokoll zeigt das Ergebnis und verständliche Fehlermeldungen an. Über weitere Buttons kann die Schulverwaltung mit einem Dateiauswahldialog gespeichert und geladen werden. Die Dateiverarbeitung verwendet dabei das Persistenz-Interface aus Übung 8. Die eigentliche Fachlogik bleibt in der `Schulverwaltung` aus Übung 6.

Lernziele:

- ein Fenster mit `JFrame` erstellen
- Eingabefelder, ComboBoxen, Buttons und ein Ausgabefeld anordnen
- mit Action Listenern auf Klicks reagieren
- Texteingaben in Zahlen umwandeln und Fehler behandeln
- mit `JFileChooser` Dateien zum Speichern und Laden auswählen
- die Oberfläche nach dem Laden neuer Daten aktualisieren
- Benutzeroberfläche und Fachlogik voneinander trennen

Starte die Übung über `at.bbrz.oop.uebung07_benutzeroberflaeche.Uebung07`. Die Zusatzaufgaben stehen in der Klasse `SchulverwaltungFenster`.

## Übung 8 – Schulverwaltung dauerhaft speichern

Übung 8 führt das Interface `SchulverwaltungPersistenz` ein. Die Implementierung `DateiSchulverwaltungPersistenz` speichert Schule, Personen, Kurse und Anmeldungen in einer Textdatei und kann daraus wieder eine vollständige `Schulverwaltung` erzeugen. Dadurch hängt die Anwendung nur vom Interface ab; später könnte die Dateispeicherung beispielsweise durch eine Datenbank ersetzt werden. Die Klassen aus Übung 5 und 6 bleiben dabei nahezu unverändert und erhalten lediglich einige lesende Getter.

Lernziele:

- Persistenz hinter einem Interface kapseln
- eine Interface-Implementierung austauschbar verwenden
- Textdateien mit `Files`, `Path` und UTF-8 schreiben und lesen
- Objektbeziehungen beim Laden wiederherstellen
- Fehler mit `IOException` behandeln

Starte die Übung über `at.bbrz.oop.uebung08_persistenz.Uebung08`. Das Beispiel schreibt nach `daten/schulverwaltung.txt`, lädt die Datei wieder und gibt die geladene Kursübersicht aus. Die Zusatzaufgaben stehen am Ende der `main`-Methode.

## Übung 9 – Polymorphie statt switch-case

Übung 9 verwaltet unterschiedliche Gegenstände über das gemeinsame Interface
`Item`. Ein Buch, ein Laptop und ein Rucksack befinden sich gemeinsam in einer
`List<Item>`. Die Ausgabeschleife kennt nur das Interface und ruft für jeden
Gegenstand `getDescription()` auf. Durch Polymorphie wird automatisch die
Implementierung der jeweiligen Klasse ausgeführt. `ItemJsonPersistenz` speichert
die in `GameItems` enthaltene Liste mit Jackson in `daten/uebung9.json` und lädt
sie anschließend wieder. Die konkrete Klasse jedes Gegenstands steht direkt im
JSON. Deshalb ist keine zentrale Typregistrierung notwendig. Durch die eigene
Containerklasse ist beim Laden keine `TypeReference` erforderlich.

Lernziele:

- unterschiedliche Klassen über ein gemeinsames Interface verwenden
- polymorphe Methodenaufrufe in einer Liste verstehen
- Typprüfungen mit `switch` oder `instanceof` vermeiden
- neue Typen ergänzen, ohne die bestehende Ausgabeschleife zu verändern
- eine polymorphe Liste als JSON speichern und laden

Starte die Übung über
`at.bbrz.oop.uebung09_polymorphie_ohne_switch.Uebung09`. Die nachträglich
ergänzte Klasse `WaterBottle` zeigt, dass ein neuer Gegenstand nur `Item`
implementieren und direkt zur Liste hinzugefügt werden muss. Wie die vorhandenen
Gegenstände und `GameItems` ist sie als Record umgesetzt. Jackson kann die
Record-Komponenten ohne zusätzliche Konstruktor-, Getter- oder Setter-Methoden
speichern und laden. Eine zentrale Anmeldung oder Registrierung des neuen Typs
ist nicht notwendig. `Phone` ist als weiteres Beispiel auf dieselbe Weise
ergänzt. Der umfangreichere Record `SmartWatch` zeigt außerdem, dass Records
Validierung und eigene Methoden enthalten können. Operationen wie
`recordSteps()` und `charge()` geben eine neue Smartwatch zurück, weil die Werte
eines Records nach seiner Erzeugung unveränderlich sind.
