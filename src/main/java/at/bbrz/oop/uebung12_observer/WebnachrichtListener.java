package at.bbrz.oop.uebung12_observer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Simuliert den Versand einer JSON-Webnachricht in einem eigenen Thread.
 */
public class WebnachrichtListener implements KursAnmeldungListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void anmeldungErfolgt(KursAnmeldungEvent event) {
        versandStarten(event);
    }

    @Override
    public void anmeldungFehlerhaft(KursAnmeldungEvent event) {
        versandStarten(event);
    }

    private void versandStarten(KursAnmeldungEvent event) {
        Thread versandThread = new Thread(
                () -> webnachrichtVersenden(event),
                "webnachricht-" + event.schuelerId());
        versandThread.start();
    }

    private void webnachrichtVersenden(KursAnmeldungEvent event) {
        try {
            // Simulierte Netzwerkwartezeit blockiert nur diesen Versandthread.
            Thread.sleep(500);

            // Jackson uebernimmt das Escaping, etwa von Anfuehrungszeichen.
            // Instant wird ohne zusaetzliches Jackson-Modul als ISO-Text uebergeben.
            String json = objectMapper.createObjectNode()
                    .put("kursbezeichnung", event.kursbezeichnung())
                    .put("schuelerId", event.schuelerId())
                    .put("schuelerName", event.schuelerName())
                    .put("belegtePlaetze", event.belegtePlaetze())
                    .put("maxTeilnehmende", event.maxTeilnehmende())
                    .put("zeitpunkt", event.zeitpunkt().toString())
                    .put("klasse", event.klasse())
                    .put("anmeldungErfolgreich", event.anmeldungErfolgreich())
                    .put("anmeldeErgebnisNachricht", event.anmeldeErgebnisNachricht())
                    .toString();

            // Fake: Es wird keine HTTP-Verbindung aufgebaut.
            System.out.printf(
                    "WEBNACHRICHT [%s]: POST https://example.invalid/anmeldungen"
                            + " (Content-Type: application/json) %s%n",
                    Thread.currentThread().getName(), json);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("WEBNACHRICHT: Versand wurde unterbrochen.");
        }
    }
}
