package at.bbrz.oop.uebung06_kursverwaltung;

import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.SchulFabric;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;

public class Uebung06 {
    public static void main(String[] args) {
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
        boolean saraAngemeldet = verwaltung.schuelerAnmelden(
                "Objektorientierte Programmierung", 4);
        System.out.println("Sara wurde im OOP-Kurs angemeldet: " + saraAngemeldet);

        verwaltung.schuelerAnmelden("SQL-Grundlagen", 2);
        verwaltung.schuelerAnmelden("SQL-Grundlagen", 4);

        verwaltung.schuelerAbmelden("SQL-Grundlagen", 2);

        verwaltung.kursangeboteAusgeben();

        System.out.println();
        verwaltung.ausgebuchteKurseAusgeben();

        try {
            verwaltung.schuelerAnmelden("SQL-Grundlagen", 999);
        } catch (IllegalArgumentException exception) {
            System.out.println("Erwarteter Fehler: " + exception.getMessage());
        }

        // Zusatzaufgaben:
        // TODO 1: Fuege Kursangebot.getFreiePlaetze() hinzu.
        // TODO 2: Gib in kursangeboteAusgeben() die freien Plaetze aus.
        // TODO 3: Erstelle eine Methode, die nur ausgebuchte Kurse ausgibt.
        // TODO 4: Teste die Fehlermeldung bei einer unbekannten Personen-ID.
    }
}
