package at.bbrz.oop.uebung08_persistenz;

import at.bbrz.oop.uebung05_schulverwaltung.Direktor;
import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung05_schulverwaltung.Schule;
import at.bbrz.oop.uebung05_schulverwaltung.Schulperson;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * JSON-Persistenz, bei der jeder Datensatz seine Umwandlungslogik selbst kapselt.
 */
public class RefactoredJsonSchulverwaltungPersistenz
        implements SchulverwaltungPersistenz {
    private static final int FORMAT_VERSION = 1;

    /**
     * Zentrale Registrierung der unterstuetzten Personentypen. Fuer einen neuen
     * Typ muss hier nur der Konverter des neuen Records ergaenzt werden.
     */
    private static final List<PersonDatenTyp<?>> PERSONEN_TYPEN = List.of(
            DirektorDaten.TYP,
            LehrkraftDaten.TYP,
            SchuelerDaten.TYP);

    private final ObjectMapper objectMapper;

    public RefactoredJsonSchulverwaltungPersistenz() {
        objectMapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        for (PersonDatenTyp<?> personenTyp : PERSONEN_TYPEN) {
            objectMapper.registerSubtypes(personenTyp.datenKlasse());
        }
    }

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

        List<PersonDaten> personen = new ArrayList<>();
        for (Schulperson person : verwaltung.getSchule().getPersonen()) {
            personen.add(inPersonDatenUmwandeln(person));
        }

        SchulverwaltungDaten daten = SchulverwaltungDaten.aus(
                verwaltung, personen);
        objectMapper.writeValue(datei.toFile(), daten);
    }

    @Override
    public Schulverwaltung laden(Path datei) throws IOException {
        if (datei == null) {
            throw new IllegalArgumentException("Die Datei darf nicht null sein.");
        }

        try {
            SchulverwaltungDaten daten = objectMapper.readValue(
                    datei.toFile(), SchulverwaltungDaten.class);
            return daten.schulverwaltungErstellen();
        } catch (RuntimeException exception) {
            throw new IOException("Ungueltige Daten in der JSON-Datei: "
                    + exception.getMessage(), exception);
        }
    }

    private PersonDaten inPersonDatenUmwandeln(Schulperson person)
            throws IOException {
        return PERSONEN_TYPEN.stream()
                .filter(personenTyp -> personenTyp.unterstuetzt(person))
                .findFirst()
                .map(personenTyp -> personenTyp.inDatenUmwandeln(person))
                .orElseThrow(() -> new IOException(
                        "Unbekannter Personentyp: " + person.getClass().getName()));
    }

    private record SchulverwaltungDaten(
            int version,
            String schule,
            List<PersonDaten> personen,
            List<KursDaten> kurse) {

        private SchulverwaltungDaten {
            if (version != FORMAT_VERSION) {
                throw new IllegalArgumentException(
                        "Unbekannte Formatversion: " + version);
            }
            if (schule == null || schule.isBlank()) {
                throw new IllegalArgumentException(
                        "Der Schulname darf nicht leer sein.");
            }
            if (personen == null || kurse == null) {
                throw new IllegalArgumentException(
                        "Die Listen personen und kurse muessen vorhanden sein.");
            }
        }

        private static SchulverwaltungDaten aus(
                Schulverwaltung verwaltung, List<PersonDaten> personen) {
            List<KursDaten> kurse = verwaltung.getKursangebote().values().stream()
                    .map(KursDaten::aus)
                    .toList();

            return new SchulverwaltungDaten(FORMAT_VERSION,
                    verwaltung.getSchule().getName(), personen, kurse);
        }

        private Schulverwaltung schulverwaltungErstellen() {
            Schule neueSchule = new Schule(schule);
            personen.forEach(person -> person.inSchuleAufnehmen(neueSchule));

            Schulverwaltung verwaltung = new Schulverwaltung(neueSchule);
            kurse.forEach(kurs -> kurs.inVerwaltungAnlegen(verwaltung));
            return verwaltung;
        }
    }

    /**
     * Jackson schreibt den Namen des konkreten Records in das Feld "typ" und
     * waehlt beim Laden anhand dieses Feldes wieder den passenden Record aus.
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "typ")
    private interface PersonDaten {
        void inSchuleAufnehmen(Schule schule);
    }

    /**
     * Verbindet einen Domain-Typ mit seinem Daten-Record und dessen Konverter.
     */
    private record PersonDatenTyp<T extends Schulperson>(
            Class<T> domainKlasse,
            Class<? extends PersonDaten> datenKlasse,
            Function<T, ? extends PersonDaten> konverter) {

        private boolean unterstuetzt(Schulperson person) {
            return domainKlasse.isInstance(person);
        }

        private PersonDaten inDatenUmwandeln(Schulperson person) {
            return konverter.apply(domainKlasse.cast(person));
        }
    }

    @JsonTypeName("DIREKTOR")
    private record DirektorDaten(int id, String name) implements PersonDaten {
        private static final PersonDatenTyp<Direktor> TYP =
                new PersonDatenTyp<>(Direktor.class, DirektorDaten.class,
                        DirektorDaten::aus);

        private static DirektorDaten aus(Direktor direktor) {
            return new DirektorDaten(direktor.getId(), direktor.getName());
        }

        @Override
        public void inSchuleAufnehmen(Schule schule) {
            schule.direktorAufnehmen(new Direktor(id, name));
        }
    }

    @JsonTypeName("LEHRKRAFT")
    private record LehrkraftDaten(
            int id, String name, String fach) implements PersonDaten {
        private static final PersonDatenTyp<Lehrkraft> TYP =
                new PersonDatenTyp<>(Lehrkraft.class, LehrkraftDaten.class,
                        LehrkraftDaten::aus);

        private static LehrkraftDaten aus(Lehrkraft lehrkraft) {
            return new LehrkraftDaten(lehrkraft.getId(), lehrkraft.getName(),
                    lehrkraft.getFach());
        }

        @Override
        public void inSchuleAufnehmen(Schule schule) {
            schule.personAufnehmen(new Lehrkraft(id, name, fach));
        }
    }

    @JsonTypeName("SCHUELER")
    private record SchuelerDaten(
            int id, String name, String klasse) implements PersonDaten {
        private static final PersonDatenTyp<Schueler> TYP =
                new PersonDatenTyp<>(Schueler.class, SchuelerDaten.class,
                        SchuelerDaten::aus);

        private static SchuelerDaten aus(Schueler schueler) {
            return new SchuelerDaten(schueler.getId(), schueler.getName(),
                    schueler.getKlasse());
        }

        @Override
        public void inSchuleAufnehmen(Schule schule) {
            schule.personAufnehmen(new Schueler(id, name, klasse));
        }
    }

    private record KursDaten(
            String bezeichnung,
            int lehrkraftId,
            int maxTeilnehmende,
            List<Integer> teilnehmendeIds) {

        private KursDaten {
            if (teilnehmendeIds == null) {
                throw new IllegalArgumentException(
                        "Die Teilnehmerliste muss vorhanden sein.");
            }
        }

        private static KursDaten aus(Kursangebot angebot) {
            return new KursDaten(
                    angebot.getKurs().getBezeichnung(),
                    angebot.getKurs().getLehrkraft().getId(),
                    angebot.getMaxTeilnehmende(),
                    angebot.getKurs().getTeilnehmende().stream()
                            .map(Schueler::getId)
                            .toList());
        }

        private void inVerwaltungAnlegen(Schulverwaltung verwaltung) {
            verwaltung.kursAnlegen(
                    bezeichnung, lehrkraftId, maxTeilnehmende);

            for (int schuelerId : teilnehmendeIds) {
                if (!verwaltung.schuelerAnmelden(bezeichnung, schuelerId)) {
                    throw new IllegalArgumentException(
                            "Anmeldung fuer Kurs " + bezeichnung
                                    + " nicht moeglich.");
                }
            }
        }
    }
}
