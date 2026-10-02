package at.bbrz.oop.uebung12_observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sammelt ein einfaches Audit-Log im Speicher.
 */
public class AuditLogListener implements KursAnmeldungListener {
    private final List<String> eintraege = new ArrayList<>();

    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {
        String eintrag = "%s: Schueler %d zu '%s' angemeldet".formatted(
                event.zeitpunkt(),
                event.schuelerId(),
                event.kursbezeichnung());
        eintraege.add(eintrag);
        System.out.println("AUDIT: " + eintrag);
    }

    public List<String> getEintraege() {
        return Collections.unmodifiableList(eintraege);
    }
}
