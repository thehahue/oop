package at.bbrz.oop.uebung12_observer;

import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import at.bbrz.oop.uebung11_strategy.Anmeldeergebnis;
import at.bbrz.oop.uebung11_strategy.KursAnmeldeService;
import at.bbrz.oop.uebung11_strategy.OffeneZulassung;
import at.bbrz.oop.uebung11_strategy.Uebung11;

public class Uebung12 {
    public static void main(String[] args) {
        Schulverwaltung verwaltung = Uebung11.beispielVerwaltungErstellen();

        KursAnmeldeService fachService = new KursAnmeldeService(
                verwaltung,
                new OffeneZulassung());
        BeobachtbarerKursAnmeldeService service =
                new BeobachtbarerKursAnmeldeService(fachService, verwaltung);

        AuditLogListener auditLog = new AuditLogListener();
        StatistikListener statistik = new StatistikListener();

        service.listenerHinzufuegen(new BestaetigungsListener());
        service.listenerHinzufuegen(auditLog);
        service.listenerHinzufuegen(statistik);
        service.listenerHinzufuegen(new AuslastungsWarnungListener(0.66));

        ausgeben(service.anmelden("Backend-Grundlagen", 2));
        ausgeben(service.anmelden("Backend-Grundlagen", 3));
        ausgeben(service.anmelden("Backend-Grundlagen", 4));

        // Eine abgelehnte doppelte Anmeldung erzeugt bewusst kein Ereignis.
        ausgeben(service.anmelden("Backend-Grundlagen", 2));

        System.out.println();
        System.out.println("Audit-Eintraege: " + auditLog.getEintraege().size());
        System.out.println("Statistik: " + statistik.getAnmeldungenJeKurs());

        // Zusatzaufgaben:
        // TODO 1: Entferne einen Listener und beobachte die naechste Anmeldung.
        // TODO 2: Erstelle einen Listener fuer eine simulierte Warteliste.
    }

    private static void ausgeben(Anmeldeergebnis ergebnis) {
        String status = ergebnis.erfolgreich() ? "ERFOLG" : "ABGELEHNT";
        System.out.println(status + ": " + ergebnis.nachricht());
        System.out.println();
    }
}
