package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.SchulFabric;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import java.util.Set;

public class Uebung11 {
    public static void main(String[] args) {
        Schulverwaltung verwaltung = beispielVerwaltungErstellen();

        // In einer Spring-Anwendung wuerde Spring die passende Implementierung
        // ueber den Konstruktor injizieren.
        KursAnmeldeService klassenService = new KursAnmeldeService(
                verwaltung,
                new KlassenZulassung(Set.of("JAVA-1", "Graphentheorie")));

        ausgeben(klassenService.anmelden("Backend-Grundlagen", 2));
        ausgeben(klassenService.anmelden("Backend-Grundlagen", 4));

        // Derselbe Service arbeitet ohne Aenderung auch mit einer anderen
        // Geschaeftsregel.
        KursAnmeldeService offenerService = new KursAnmeldeService(
                verwaltung,
                new OffeneZulassung());
        ausgeben(offenerService.anmelden("Backend-Grundlagen", 4));

        System.out.println();
        System.out.println(verwaltung.getKursuebersicht());

        // Zusatzaufgaben:
        // TODO 1: Implementiere eine NurAbendklassenZulassung. (Einfacher Ansatz: Kursbezeichnung beginnt mit Abend-...)
        // TODO 2: Erlaube in einer neuen Strategy mehrere Klassenpraefixe.
        // TODO 3: Kombiniere mehrere Strategien, die alle zustimmen muessen.
    }

    public static Schulverwaltung beispielVerwaltungErstellen() {
        Schule schule = SchulFabric.createSchule("BBRZ Schule", "Franz Hofer");
        schule.personAufnehmen(new Lehrkraft(9, "Frau Novak", "Programmieren"));
        schule.personAufnehmen(new Schueler(2, "Mia", "JAVA-1"));
        schule.personAufnehmen(new Schueler(3, "Leon", "JAVA-1"));
        schule.personAufnehmen(new Schueler(4, "Sara", "JAVA-2"));

        Schulverwaltung verwaltung = new Schulverwaltung(schule);
        verwaltung.kursAnlegen("Backend-Grundlagen", 9, 3);
        return verwaltung;
    }

    private static void ausgeben(Anmeldeergebnis ergebnis) {
        String status = ergebnis.erfolgreich() ? "ERFOLG" : "ABGELEHNT";
        System.out.println(status + ": " + ergebnis.nachricht());
    }
}
