package at.bbrz.oop.uebung08_persistenz;

import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Beschreibt, wie eine Schulverwaltung dauerhaft gespeichert und geladen wird.
 * Die konkrete Art der Speicherung wird von einer Implementierung festgelegt.
 */
public interface SchulverwaltungPersistenz {
    void speichern(Schulverwaltung verwaltung, Path datei) throws IOException;

    Schulverwaltung laden(Path datei) throws IOException;
}
