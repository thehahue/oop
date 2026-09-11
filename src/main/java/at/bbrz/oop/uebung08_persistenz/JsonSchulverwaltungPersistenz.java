package at.bbrz.oop.uebung08_persistenz;

import at.bbrz.oop.uebung05_schulverwaltung.Direktor;
import at.bbrz.oop.uebung05_schulverwaltung.Kurs;
import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung05_schulverwaltung.Schulperson;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Speichert eine Schulverwaltung als lesbar formatiertes JSON.
 *
 * <p>Interne Datentransferobjekte halten das Dateiformat von den bestehenden
 * Klassen der Schulverwaltung getrennt.</p>
 */
public class JsonSchulverwaltungPersistenz implements SchulverwaltungPersistenz {
    private static final int FORMAT_VERSION = 1;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public void speichern(Schulverwaltung verwaltung, Path datei) throws IOException {
        if (verwaltung == null || datei == null) {
            throw new IllegalArgumentException(
                    "Verwaltung und Datei duerfen nicht null sein.");
        }

        Path ordner = datei.toAbsolutePath().getParent();
        if (ordner != null) {
            Files.createDirectories(ordner);
        }

        objectMapper.writeValue(datei.toFile(), inDatenUmwandeln(verwaltung));
    }

    @Override
    public Schulverwaltung laden(Path datei) throws IOException {
        if (datei == null) {
            throw new IllegalArgumentException("Die Datei darf nicht null sein.");
        }

        try {
            SchulverwaltungDaten daten = objectMapper.readValue(
                    datei.toFile(), SchulverwaltungDaten.class);
            return ausDatenErstellen(daten);
        } catch (RuntimeException exception) {
            throw new IOException("Ungueltige Daten in der JSON-Datei: "
                    + exception.getMessage(), exception);
        }
    }

    private SchulverwaltungDaten inDatenUmwandeln(Schulverwaltung verwaltung)
            throws IOException {
        List<PersonDaten> personen = new ArrayList<>();
        for (Schulperson person : verwaltung.getSchule().getPersonen()) {
            personen.add(inPersonDatenUmwandeln(person));
        }

        List<KursDaten> kurse = new ArrayList<>();
        for (Kursangebot angebot : verwaltung.getKursangebote().values()) {
            Kurs kurs = angebot.getKurs();
            List<Integer> teilnehmendeIds = kurs.getTeilnehmende().stream()
                    .map(Schueler::getId)
                    .toList();
            kurse.add(new KursDaten(kurs.getBezeichnung(),
                    kurs.getLehrkraft().getId(), angebot.getMaxTeilnehmende(),
                    teilnehmendeIds));
        }

        return new SchulverwaltungDaten(FORMAT_VERSION,
                verwaltung.getSchule().getName(), personen, kurse);
    }

    private PersonDaten inPersonDatenUmwandeln(Schulperson person) throws IOException {
        if (person instanceof Direktor) {
            return new PersonDaten("DIREKTOR", person.getId(), person.getName(),
                    null, null);
        }
        if (person instanceof Lehrkraft lehrkraft) {
            return new PersonDaten("LEHRKRAFT", lehrkraft.getId(),
                    lehrkraft.getName(), lehrkraft.getFach(), null);
        }
        if (person instanceof Schueler schueler) {
            return new PersonDaten("SCHUELER", schueler.getId(),
                    schueler.getName(), null, schueler.getKlasse());
        }
        throw new IOException("Unbekannter Personentyp: "
                + person.getClass().getName());
    }

    private Schulverwaltung ausDatenErstellen(SchulverwaltungDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Die JSON-Datei ist leer.");
        }
        if (daten.version() != FORMAT_VERSION) {
            throw new IllegalArgumentException(
                    "Unbekannte Formatversion: " + daten.version());
        }
        if (daten.schule() == null || daten.schule().isBlank()) {
            throw new IllegalArgumentException("Der Schulname darf nicht leer sein.");
        }
        if (daten.personen() == null || daten.kurse() == null) {
            throw new IllegalArgumentException(
                    "Die Listen personen und kurse muessen vorhanden sein.");
        }

        Schule schule = new Schule(daten.schule());
        for (PersonDaten person : daten.personen()) {
            personAufnehmen(schule, person);
        }

        Schulverwaltung verwaltung = new Schulverwaltung(schule);
        for (KursDaten kurs : daten.kurse()) {
            kursAnlegen(verwaltung, kurs);
        }
        return verwaltung;
    }

    private void personAufnehmen(Schule schule, PersonDaten daten) {
        if (daten == null || daten.typ() == null) {
            throw new IllegalArgumentException("Eine Person hat keinen Typ.");
        }

        switch (daten.typ()) {
            case "DIREKTOR" -> schule.direktorAufnehmen(
                    new Direktor(daten.id(), daten.name()));
            case "LEHRKRAFT" -> schule.personAufnehmen(
                    new Lehrkraft(daten.id(), daten.name(), daten.fach()));
            case "SCHUELER" -> schule.personAufnehmen(
                    new Schueler(daten.id(), daten.name(), daten.klasse()));
            default -> throw new IllegalArgumentException(
                    "Unbekannter Personentyp: " + daten.typ());
        }
    }

    private void kursAnlegen(Schulverwaltung verwaltung, KursDaten daten) {
        if (daten == null || daten.teilnehmendeIds() == null) {
            throw new IllegalArgumentException(
                    "Ein Kurs und seine Teilnehmerliste muessen vorhanden sein.");
        }

        verwaltung.kursAnlegen(daten.bezeichnung(), daten.lehrkraftId(),
                daten.maxTeilnehmende());
        for (int schuelerId : daten.teilnehmendeIds()) {
            if (!verwaltung.schuelerAnmelden(daten.bezeichnung(), schuelerId)) {
                throw new IllegalArgumentException(
                        "Anmeldung fuer Kurs " + daten.bezeichnung()
                                + " nicht moeglich.");
            }
        }
    }

    private record SchulverwaltungDaten(
            int version,
            String schule,
            List<PersonDaten> personen,
            List<KursDaten> kurse) {
    }

    private record PersonDaten(
            String typ,
            int id,
            String name,
            String fach,
            String klasse) {
    }

    private record KursDaten(
            String bezeichnung,
            int lehrkraftId,
            int maxTeilnehmende,
            List<Integer> teilnehmendeIds) {
    }
}
