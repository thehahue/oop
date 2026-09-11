package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Speichert und laedt die Gegenstaende immer in derselben JSON-Datei.
 */
public class ItemJsonPersistenz {
    private static final Path DATEI = Path.of("daten", "uebung9.json");
    private static final TypeReference<List<Item>> ITEM_LISTEN_TYP =
            new TypeReference<>() {
            };

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void speichern(List<Item> items) throws IOException {
        if (items == null) {
            throw new IllegalArgumentException("Die Item-Liste darf nicht null sein.");
        }

        Files.createDirectories(DATEI.toAbsolutePath().getParent());
        objectMapper.writerFor(ITEM_LISTEN_TYP)
                .writeValue(DATEI.toFile(), items);
    }

    public List<Item> laden() throws IOException {
        return objectMapper.readerFor(ITEM_LISTEN_TYP)
                .readValue(DATEI.toFile());
    }
}
