package at.bbrz.oop.uebung07_benutzeroberflaeche;

import at.bbrz.oop.uebung05_schulverwaltung.Lehrkraft;
import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import at.bbrz.oop.uebung08_persistenz.DateiSchulverwaltungPersistenz;
import at.bbrz.oop.uebung08_persistenz.SchulverwaltungPersistenz;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Eine einfache Swing-Oberflaeche fuer die Schulverwaltung aus Uebung 6.
 */
public class SchulverwaltungFenster extends JFrame {
    private Schulverwaltung verwaltung;
    private final SchulverwaltungPersistenz persistenz;
    private final JComboBox<String> kursAuswahl;
    private final JComboBox<Schueler> schuelerAuswahl;
    private final JTextArea protokoll = new JTextArea(10, 45);
    private final JLabel statusMeldung = new JLabel("Bereit");

    public SchulverwaltungFenster(Schulverwaltung verwaltung) {
        this(verwaltung, new DateiSchulverwaltungPersistenz());
    }

    public SchulverwaltungFenster(Schulverwaltung verwaltung,
                                  SchulverwaltungPersistenz persistenz) {
        if (verwaltung == null || persistenz == null) {
            throw new IllegalArgumentException(
                    "Verwaltung und Persistenz duerfen nicht null sein.");
        }
        this.verwaltung = verwaltung;
        this.persistenz = persistenz;
        kursAuswahl = new JComboBox<>(verwaltung.getKursnamen());
        schuelerAuswahl = new JComboBox<>(verwaltung.getAlleSchueler());

        setTitle("Schulverwaltung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inhalt = new JPanel(new BorderLayout(10, 10));
        inhalt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inhalt.add(erstelleEingabebereich(), BorderLayout.NORTH);
        inhalt.add(new JScrollPane(protokoll), BorderLayout.CENTER);
        inhalt.add(statusMeldung, BorderLayout.SOUTH);
        setContentPane(inhalt);

        protokoll.setEditable(false);
        protokoll.setLineWrap(true);
        protokoll.setWrapStyleWord(true);
        protokoll.setText("Waehle einen Kurs und einen Schueler aus.\n");

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel erstelleEingabebereich() {
        JPanel felder = new JPanel(new GridLayout(2, 2, 5, 5));
        felder.add(new JLabel("Kurs:"));
        felder.add(kursAuswahl);
        felder.add(new JLabel("Schueler:"));
        felder.add(schuelerAuswahl);

        JButton anmeldenButton = new JButton("Anmelden");
        anmeldenButton.addActionListener(event -> schuelerAnmelden());

        JButton abmeldenButton = new JButton("Abmelden");
        abmeldenButton.addActionListener(event -> schuelerAbmelden());

        JButton kurseAusgebenButton = new JButton("Kurse ausgeben");
        kurseAusgebenButton.addActionListener(event -> kurseAusgeben());

        JButton kursAnlegenButton = new JButton("Kurs anlegen");
        kursAnlegenButton.addActionListener(event -> kursAnlegen());

        JButton protokollLeerenButton = new JButton("Protokoll leeren");
        protokollLeerenButton.addActionListener(event -> {
            protokoll.setText("");
            statusMeldung.setText("Protokoll wurde geleert.");
            statusMeldung.setForeground(Color.GREEN.darker());
        });

        JButton speichernButton = new JButton("Speichern");
        speichernButton.addActionListener(event -> verwaltungSpeichern());

        JButton ladenButton = new JButton("Laden");
        ladenButton.addActionListener(event -> verwaltungLaden());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(anmeldenButton);
        buttons.add(abmeldenButton);
        buttons.add(kursAnlegenButton);
        buttons.add(kurseAusgebenButton);
        buttons.add(speichernButton);
        buttons.add(ladenButton);
        buttons.add(protokollLeerenButton);

        JPanel eingabe = new JPanel(new BorderLayout(5, 5));
        eingabe.add(felder, BorderLayout.CENTER);
        eingabe.add(buttons, BorderLayout.SOUTH);
        return eingabe;
    }

    private void schuelerAnmelden() {
        try {
            String kurs = getAusgewaehlterKurs();
            Schueler schueler = getAusgewaehlterSchueler();
            boolean erfolgreich = verwaltung.schuelerAnmelden(kurs, schueler.getId());

            if (erfolgreich) {
                erfolgAusgeben(schueler.getName() + " wurde bei " + kurs + " angemeldet.");
                schuelerAuswahl.setSelectedIndex(-1);
            } else {
                fehlerAusgeben("Anmeldung nicht moeglich: Kurs voll oder Schueler bereits angemeldet.");
            }
        } catch (IllegalArgumentException exception) {
            fehlerAusgeben("Fehler: " + exception.getMessage());
        }
    }

    private void schuelerAbmelden() {
        try {
            String kurs = getAusgewaehlterKurs();
            Schueler schueler = getAusgewaehlterSchueler();
            boolean erfolgreich = verwaltung.schuelerAbmelden(kurs, schueler.getId());

            if (erfolgreich) {
                erfolgAusgeben(schueler.getName() + " wurde von " + kurs + " abgemeldet.");
            } else {
                fehlerAusgeben("Der Schueler war in diesem Kurs nicht angemeldet.");
            }
        } catch (IllegalArgumentException exception) {
            fehlerAusgeben("Fehler: " + exception.getMessage());
        }
    }

    private void kurseAusgeben() {
        protokoll.append(verwaltung.getKursuebersicht() + System.lineSeparator());
        erfolgAusgeben("Kursuebersicht wurde ausgegeben.");
    }

    private void kursAnlegen() {
        JTextField kursnameFeld = new JTextField(20);
        JComboBox<Lehrkraft> lehrkraftAuswahl =
                new JComboBox<>(verwaltung.getAlleLehrkraefte());
        JTextField maxTeilnehmendeFeld = new JTextField(8);

        JPanel felder = new JPanel(new GridLayout(3, 2, 5, 5));
        felder.add(new JLabel("Kursname:"));
        felder.add(kursnameFeld);
        felder.add(new JLabel("Lehrkraft:"));
        felder.add(lehrkraftAuswahl);
        felder.add(new JLabel("Maximale Plaetze:"));
        felder.add(maxTeilnehmendeFeld);

        int auswahl = JOptionPane.showConfirmDialog(
                this,
                felder,
                "Neuen Kurs anlegen",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (auswahl != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String kursname = kursnameFeld.getText().trim();
            Lehrkraft lehrkraft = (Lehrkraft) lehrkraftAuswahl.getSelectedItem();
            int maxTeilnehmende = Integer.parseInt(maxTeilnehmendeFeld.getText().trim());

            if (lehrkraft == null) {
                throw new IllegalArgumentException("Bitte eine Lehrkraft auswaehlen.");
            }

            verwaltung.kursAnlegen(kursname, lehrkraft.getId(), maxTeilnehmende);
            kursAuswahlAktualisieren();
            kursAuswahl.setSelectedItem(kursname);

            erfolgAusgeben("Kurs " + kursname + " wurde angelegt.");
        } catch (NumberFormatException exception) {
            fehlerAusgeben("Die maximale Teilnehmerzahl muss eine ganze Zahl sein.");
        } catch (IllegalArgumentException exception) {
            fehlerAusgeben("Fehler: " + exception.getMessage());
        }
    }

    private void kursAuswahlAktualisieren() {
        kursAuswahl.removeAllItems();
        for (String kursname : verwaltung.getKursnamen()) {
            kursAuswahl.addItem(kursname);
        }
    }

    private void schuelerAuswahlAktualisieren() {
        schuelerAuswahl.removeAllItems();
        for (Schueler schueler : verwaltung.getAlleSchueler()) {
            schuelerAuswahl.addItem(schueler);
        }
    }

    private void verwaltungSpeichern() {
        JFileChooser dateiAuswahl = erstelleDateiAuswahl();
        if (dateiAuswahl.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path datei = dateiAuswahl.getSelectedFile().toPath();
        if (Files.exists(datei)) {
            int antwort = JOptionPane.showConfirmDialog(
                    this,
                    "Die Datei existiert bereits. Soll sie ueberschrieben werden?",
                    "Datei ueberschreiben",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (antwort != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            persistenz.speichern(verwaltung, datei);
            erfolgAusgeben("Schulverwaltung gespeichert: " + datei.toAbsolutePath());
        } catch (IOException exception) {
            fehlerAusgeben("Fehler beim Speichern: " + exception.getMessage());
        }
    }

    private void verwaltungLaden() {
        JFileChooser dateiAuswahl = erstelleDateiAuswahl();
        if (dateiAuswahl.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path datei = dateiAuswahl.getSelectedFile().toPath();
        try {
            verwaltung = persistenz.laden(datei);
            kursAuswahlAktualisieren();
            schuelerAuswahlAktualisieren();
            protokoll.append(verwaltung.getKursuebersicht() + System.lineSeparator());
            erfolgAusgeben("Schulverwaltung geladen: " + datei.toAbsolutePath());
        } catch (IOException exception) {
            fehlerAusgeben("Fehler beim Laden: " + exception.getMessage());
        }
    }

    private JFileChooser erstelleDateiAuswahl() {
        JFileChooser dateiAuswahl = new JFileChooser();
        Path datenOrdner = Path.of("daten");
        if (Files.isDirectory(datenOrdner)) {
            dateiAuswahl.setCurrentDirectory(datenOrdner.toFile());
        }
        dateiAuswahl.setFileFilter(
                new FileNameExtensionFilter("Schulverwaltung (*.txt)", "txt"));
        return dateiAuswahl;
    }

    private String getAusgewaehlterKurs() {
        return (String) kursAuswahl.getSelectedItem();
    }

    private Schueler getAusgewaehlterSchueler() {
        Schueler schueler = (Schueler) schuelerAuswahl.getSelectedItem();
        if (schueler == null) {
            throw new IllegalArgumentException("Bitte einen Schueler auswaehlen.");
        }
        return schueler;
    }

    private void erfolgAusgeben(String meldung) {
        meldungAusgeben(meldung, Color.GREEN.darker());
    }

    private void fehlerAusgeben(String meldung) {
        meldungAusgeben(meldung, Color.RED);
    }

    private void meldungAusgeben(String meldung, Color farbe) {
        protokoll.append(meldung + System.lineSeparator());

        statusMeldung.setText(meldung);
        statusMeldung.setForeground(farbe);
    }

    // Zusatzaufgaben:
    // TODO 1: Leere die Schuelerauswahl nach einer erfolgreichen Anmeldung.
    // TODO 2: Fuege einen Button hinzu, der das Protokoll leert.
    // TODO 3: Zeige erfolgreiche Meldungen gruen und Fehlermeldungen rot an.
    // TODO 4: Erweitere die Oberflaeche um das Anlegen neuer Kurse.
    // TODO 5: Fuege Speichern und Laden mit einer Dateiauswahl hinzu.
    // TODO 6: Aktualisiere nach dem Laden alle Auswahlfelder.
}
