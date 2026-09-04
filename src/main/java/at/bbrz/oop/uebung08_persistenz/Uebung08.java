package at.bbrz.oop.uebung08_persistenz;

import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.SchulFabric;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import java.io.IOException;
import java.nio.file.Path;

public class Uebung08 {
    public static void main(String[] args) {
        Schulverwaltung verwaltung = beispielVerwaltungErstellen();

        // Die Anwendung kennt nur das Interface. Die Implementierung ist austauschbar.
        SchulverwaltungPersistenz persistenz = new DateiSchulverwaltungPersistenz();
        Path datei = Path.of("daten", "schulverwaltung.txt");

        try {
            persistenz.speichern(verwaltung, datei);
            System.out.println("Gespeichert in: " + datei.toAbsolutePath());

            Schulverwaltung geladeneVerwaltung = persistenz.laden(datei);
            System.out.println();
            System.out.println("Erfolgreich aus der Datei geladen:");
            System.out.println(geladeneVerwaltung.getKursuebersicht());
        } catch (IOException exception) {
            System.out.println("Fehler beim Speichern oder Laden: " + exception.getMessage());
        }

        // Zusatzaufgaben:
        // TODO 1: Speichere die Daten nach einer weiteren Kursanmeldung erneut.
        // TODO 2: Gib nach dem Laden auch alle Schulpersonen aus.
        // TODO 3: Erstelle eine zweite Implementierung des Interfaces, die nur
        //         die Kursuebersicht in eine Datei schreibt.
        // TODO 4: Teste das Verhalten bei einer nicht vorhandenen oder
        //         absichtlich beschaedigten Datei.
    }

    private static Schulverwaltung beispielVerwaltungErstellen() {
        Schule schule = SchulFabric.createSchule("BBRZ Schule", "Franz Hofer");
        schule.personAufnehmen(new Lehrkraft(9, "Frau Novak", "Programmieren"));
        schule.personAufnehmen(new Lehrkraft(10, "Herr Berger", "Datenbanken"));
        schule.personAufnehmen(new Schueler(2, "Mia", "JAVA-1"));
        schule.personAufnehmen(new Schueler(3, "Leon", "JAVA-1"));
        schule.personAufnehmen(new Schueler(4, "Sara", "JAVA-2"));

        Schulverwaltung verwaltung = new Schulverwaltung(schule);
        verwaltung.kursAnlegen("Objektorientierte Programmierung", 9, 2);
        verwaltung.kursAnlegen("SQL-Grundlagen", 10, 3);
        verwaltung.schuelerAnmelden("Objektorientierte Programmierung", 2);
        verwaltung.schuelerAnmelden("Objektorientierte Programmierung", 3);
        verwaltung.schuelerAnmelden("SQL-Grundlagen", 4);
        return verwaltung;
    }
}
