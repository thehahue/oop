package at.bbrz.oop.uebung11_strategy;

import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.SchulFabric;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import java.util.List;
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

        KursAnmeldeService abendklassenService = new KursAnmeldeService(
                verwaltung,
                new NurAbendklassenZulassung());
        ausgeben(abendklassenService.anmelden("Backend-Grundlagen-Abend", 3));
        ausgeben(abendklassenService.anmelden("Backend-Grundlagen-Abend", 5));

        KursAnmeldeService praefixService = new KursAnmeldeService(
                verwaltung,
                new KlassenpraefixZulassung(Set.of("Abend-", "DATA-")));
        ausgeben(praefixService.anmelden("Backend-Grundlagen", 3));
        ausgeben(praefixService.anmelden("Backend-Grundlagen", 5));

        // Fuer den Abendkurs muessen beide Regeln erfuellt sein:
        // Abendklasse und eine auf Programmieren ausgerichtete JAVA-Klasse.
        KursAnmeldeService kombinierterService = new KursAnmeldeService(
                verwaltung,
                new KombinierteZulassung(List.of(
                        new NurAbendklassenZulassung(),
                        new KlassenpraefixZulassung(Set.of("Abend-JAVA-")))));
        ausgeben(kombinierterService.anmelden(
                "Backend-Grundlagen-Abend", 6));
        ausgeben(kombinierterService.anmelden(
                "Backend-Grundlagen-Abend", 7));

        System.out.println();
        System.out.println(verwaltung.getKursuebersicht());
    }

    public static Schulverwaltung beispielVerwaltungErstellen() {
        Schule schule = SchulFabric.createSchule("BBRZ Schule", "Franz Hofer");
        schule.personAufnehmen(new Lehrkraft(9, "Frau Novak", "Programmieren"));
        schule.personAufnehmen(new Schueler(2, "Mia", "JAVA-1"));
        schule.personAufnehmen(new Schueler(3, "Leon", "JAVA-1"));
        schule.personAufnehmen(new Schueler(4, "Sara", "JAVA-2"));
        schule.personAufnehmen(new Schueler(5, "Nora", "Abend-JAVA-1"));
        schule.personAufnehmen(new Schueler(6, "David", "Abend-DATA-1"));
        schule.personAufnehmen(new Schueler(7, "Eva", "Abend-JAVA-2"));

        Schulverwaltung verwaltung = new Schulverwaltung(schule);
        verwaltung.kursAnlegen("Backend-Grundlagen", 9, 3);
        verwaltung.kursAnlegen("Backend-Grundlagen-Abend", 9, 5);
        return verwaltung;
    }

    private static void ausgeben(Anmeldeergebnis ergebnis) {
        String status = ergebnis.erfolgreich() ? "ERFOLG" : "ABGELEHNT";
        System.out.println(status + ": " + ergebnis.nachricht());
    }
}
