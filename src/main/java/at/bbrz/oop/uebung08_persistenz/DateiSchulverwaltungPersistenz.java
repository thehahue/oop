package at.bbrz.oop.uebung08_persistenz;

import at.bbrz.oop.uebung05_schulverwaltung.Direktor;
import at.bbrz.oop.uebung05_schulverwaltung.Kurs;
import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung05_schulverwaltung.Schulperson;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Speichert eine Schulverwaltung zeilenweise in einer UTF-8-Textdatei.
 */
public class DateiSchulverwaltungPersistenz implements SchulverwaltungPersistenz {
    private static final String DATEIKOPF = "SCHULVERWALTUNG;1";

    @Override
    public void speichern(Schulverwaltung verwaltung, Path datei) throws IOException {
        if (verwaltung == null || datei == null) {
            throw new IllegalArgumentException("Verwaltung und Datei duerfen nicht null sein.");
        }

        Path ordner = datei.toAbsolutePath().getParent();
        if (ordner != null) {
            Files.createDirectories(ordner);
        }

        // Try with resources https://www.baeldung.com/java-try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(datei, StandardCharsets.UTF_8)) {
            writer.write(DATEIKOPF);
            writer.newLine();
            zeileSchreiben(writer, "SCHULE", verwaltung.getSchule().getName());

            for (Schulperson person : verwaltung.getSchule().getPersonen()) {
                personSchreiben(writer, person);
            }

            for (Kursangebot angebot : verwaltung.getKursangebote().values()) {
                kursSchreiben(writer, angebot);
            }
        }
    }

    @Override
    public Schulverwaltung laden(Path datei) throws IOException {
        if (datei == null) {
            throw new IllegalArgumentException("Die Datei darf nicht null sein.");
        }

        List<String> zeilen = Files.readAllLines(datei, StandardCharsets.UTF_8);
        if (zeilen.isEmpty() || !DATEIKOPF.equals(zeilen.getFirst())) {
            throw new IOException("Unbekanntes Dateiformat.");
        }

        Schulverwaltung verwaltung = null;
        for (int index = 1; index < zeilen.size(); index++) {
            String[] felder = zeilen.get(index).split(";", -1);
            int zeilennummer = index + 1;

            try {
                if ("SCHULE".equals(felder[0])) {
                    felderPruefen(felder, 2);
                    if (verwaltung != null) {
                        throw new IllegalArgumentException(
                                "Die Datei darf nur eine Schule enthalten.");
                    }
                    verwaltung = new Schulverwaltung(new Schule(textDekodieren(felder[1])));
                } else {
                    if (verwaltung == null) {
                        throw new IllegalArgumentException("Die Schule fehlt.");
                    }
                    datensatzLaden(verwaltung, felder);
                }
            } catch (IllegalArgumentException exception) {
                throw new IOException("Ungueltige Daten in Zeile " + zeilennummer
                        + ": " + exception.getMessage(), exception);
            }
        }

        if (verwaltung == null) {
            throw new IOException("Die Datei enthaelt keine Schule.");
        }
        return verwaltung;
    }

    private void personSchreiben(BufferedWriter writer, Schulperson person) throws IOException {
        if (person instanceof Direktor) {
            zeileSchreiben(writer, "DIREKTOR",
                    Integer.toString(person.getId()), person.getName());
        } else if (person instanceof Lehrkraft lehrkraft) {
            zeileSchreiben(writer, "LEHRKRAFT", Integer.toString(lehrkraft.getId()),
                    lehrkraft.getName(), lehrkraft.getFach());
        } else if (person instanceof Schueler schueler) {
            zeileSchreiben(writer, "SCHUELER", Integer.toString(schueler.getId()),
                    schueler.getName(), schueler.getKlasse());
        } else {
            throw new IOException("Unbekannter Personentyp: " + person.getClass().getName());
        }
    }

    private void kursSchreiben(BufferedWriter writer, Kursangebot angebot) throws IOException {
        Kurs kurs = angebot.getKurs();
        String teilnehmendeIds = kurs.getTeilnehmende().stream()
                .map(schueler -> Integer.toString(schueler.getId()))
                .reduce((ersteId, weitereId) -> ersteId + "," + weitereId)
                .orElse("");

        zeileSchreiben(writer, "KURS", kurs.getBezeichnung(),
                Integer.toString(kurs.getLehrkraft().getId()),
                Integer.toString(angebot.getMaxTeilnehmende()), teilnehmendeIds);
    }

    private void datensatzLaden(Schulverwaltung verwaltung, String[] felder) {
        Schule schule = verwaltung.getSchule();

        switch (felder[0]) {
            case "DIREKTOR" -> {
                felderPruefen(felder, 3);
                schule.direktorAufnehmen(new Direktor(
                        zahlEinlesen(felder[1]), textDekodieren(felder[2])));
            }
            case "LEHRKRAFT" -> {
                felderPruefen(felder, 4);
                schule.personAufnehmen(new Lehrkraft(zahlEinlesen(felder[1]),
                        textDekodieren(felder[2]), textDekodieren(felder[3])));
            }
            case "SCHUELER" -> {
                felderPruefen(felder, 4);
                schule.personAufnehmen(new Schueler(zahlEinlesen(felder[1]),
                        textDekodieren(felder[2]), textDekodieren(felder[3])));
            }
            case "KURS" -> kursLaden(verwaltung, felder);
            default -> throw new IllegalArgumentException(
                    "Unbekannter Datensatztyp: " + felder[0]);
        }
    }

    private void kursLaden(Schulverwaltung verwaltung, String[] felder) {
        felderPruefen(felder, 5);
        String bezeichnung = textDekodieren(felder[1]);
        verwaltung.kursAnlegen(bezeichnung, zahlEinlesen(felder[2]),
                zahlEinlesen(felder[3]));

        String teilnehmendeIds = textDekodieren(felder[4]);
        if (!teilnehmendeIds.isBlank()) {
            for (String schuelerId : teilnehmendeIds.split(",")) {
                boolean angemeldet = verwaltung.schuelerAnmelden(
                        bezeichnung, zahlEinlesen(schuelerId));
                if (!angemeldet) {
                    throw new IllegalArgumentException(
                            "Anmeldung fuer Kurs " + bezeichnung + " nicht moeglich.");
                }
            }
        }
    }

    private void zeileSchreiben(BufferedWriter writer, String typ, String... werte)
            throws IOException {
        writer.write(typ);
        for (String wert : werte) {
            writer.write(';');
            writer.write(textKodieren(wert));
        }
        writer.newLine();
    }

    private String textKodieren(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    private String textDekodieren(String text) {
        return URLDecoder.decode(text, StandardCharsets.UTF_8);
    }

    private int zahlEinlesen(String text) {
        return Integer.parseInt(text);
    }

    private void felderPruefen(String[] felder, int erwarteteAnzahl) {
        if (felder.length != erwarteteAnzahl) {
            throw new IllegalArgumentException(
                    "Falsche Anzahl an Feldern fuer " + felder[0] + ".");
        }
    }
}
