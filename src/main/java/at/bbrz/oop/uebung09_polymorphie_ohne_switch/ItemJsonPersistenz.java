package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Speichert und laedt die Gegenstaende immer in derselben JSON-Datei.
 */
public class ItemJsonPersistenz {
    private static final Path DATEI = Path.of("daten", "uebung9.json");

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void speichern(GameItems gameItems) throws IOException {
        if (gameItems == null) {
            throw new IllegalArgumentException("GameItems darf nicht null sein.");
        }

        Files.createDirectories(DATEI.toAbsolutePath().getParent());
        objectMapper.writeValue(DATEI.toFile(), gameItems);
    }

    public GameItems laden() throws IOException {
        return objectMapper.readValue(DATEI.toFile(), GameItems.class);
    }
}
