package at.bbrz.oop.uebung07_benutzeroberflaeche;

import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.SchulFabric;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import javax.swing.SwingUtilities;

public class Uebung07 {
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
        verwaltung.kursAnlegen("Swing-Grundlagen", 10, 1);

        SwingUtilities.invokeLater(() -> {
            SchulverwaltungFenster fenster =
                    new SchulverwaltungFenster(verwaltung);
            fenster.setVisible(true);
        });
    }
}
