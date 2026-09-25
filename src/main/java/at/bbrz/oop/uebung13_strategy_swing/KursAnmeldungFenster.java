package at.bbrz.oop.uebung13_strategy_swing;

import at.bbrz.oop.uebung05_schulverwaltung.Schueler;
import at.bbrz.oop.uebung06_kursverwaltung.Kursangebot;
import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import at.bbrz.oop.uebung11_strategy.Anmeldeergebnis;
import at.bbrz.oop.uebung11_strategy.KlassenZulassung;
import at.bbrz.oop.uebung11_strategy.KursAnmeldeService;
import at.bbrz.oop.uebung11_strategy.OffeneZulassung;
import at.bbrz.oop.uebung11_strategy.Zulassungsstrategie;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Einfache Swing-Oberflaeche fuer den Backend-Service aus Uebung 11.
 */
public class KursAnmeldungFenster extends JFrame {
    private static final String KURSBEZEICHNUNG = "Backend-Grundlagen";
    private static final Color ERFOLG_FARBE = new Color(20, 120, 65);
    private static final Color FEHLER_FARBE = new Color(180, 45, 45);

    private final Schulverwaltung schulverwaltung;
    private final JComboBox<Schueler> schuelerAuswahl;
    private final JComboBox<StrategieAuswahl> strategieAuswahl;
    private final JLabel ergebnisAnzeige = new JLabel("Bereit zur Anmeldung");
    private final JProgressBar auslastungAnzeige = new JProgressBar();
    private final JTextArea teilnehmendeAnzeige = new JTextArea();
    private final JTextArea protokoll = new JTextArea();

    public KursAnmeldungFenster(Schulverwaltung schulverwaltung) {
        if (schulverwaltung == null) {
            throw new IllegalArgumentException(
                    "Die Schulverwaltung darf nicht null sein.");
        }
        this.schulverwaltung = schulverwaltung;
        this.schuelerAuswahl = new JComboBox<>(schulverwaltung.getAlleSchueler());
        this.strategieAuswahl = new JComboBox<>(new StrategieAuswahl[]{
                new StrategieAuswahl(
                        "Offene Zulassung",
                        OffeneZulassung::new),
                new StrategieAuswahl(
                        "Nur Klasse JAVA-1",
                        () -> new KlassenZulassung(Set.of("JAVA-1"))),
                new StrategieAuswahl(
                        "Nur Klasse JAVA-2",
                        () -> new KlassenZulassung(Set.of("JAVA-2")))
        });

        setTitle("Uebung 13 - Strategy mit Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 560));
        setContentPane(erstelleInhalt());
        schuelerAuswahl.setRenderer(new SchuelerRenderer());
        kursanzeigeAktualisieren();
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel erstelleInhalt() {
        JPanel inhalt = new JPanel(new BorderLayout(14, 14));
        inhalt.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        inhalt.add(erstelleKopfbereich(), BorderLayout.NORTH);
        inhalt.add(erstelleFormular(), BorderLayout.CENTER);
        inhalt.add(erstelleProtokollbereich(), BorderLayout.SOUTH);
        return inhalt;
    }

    private JPanel erstelleKopfbereich() {
        JPanel kopf = new JPanel(new BorderLayout(8, 8));

        JLabel titel = new JLabel("Kursanmeldung");
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 24f));
        kopf.add(titel, BorderLayout.NORTH);

        JLabel erklaerung = new JLabel(
                "Die Oberflaeche waehlt eine Strategy; der Service entscheidet.");
        kopf.add(erklaerung, BorderLayout.CENTER);
        return kopf;
    }

    private JPanel erstelleFormular() {
        JPanel formular = new JPanel(new GridBagLayout());
        formular.setBorder(BorderFactory.createTitledBorder(
                "Kurs: " + KURSBEZEICHNUNG));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(7, 8, 7, 8);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        formular.add(new JLabel("Schueler/in:"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        formular.add(schuelerAuswahl, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0;
        formular.add(new JLabel("Zulassungsstrategie:"), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        formular.add(strategieAuswahl, constraints);

        JButton anmeldenButton = new JButton("Zum Kurs anmelden");
        anmeldenButton.addActionListener(event -> anmeldungDurchfuehren());
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        formular.add(anmeldenButton, constraints);

        ergebnisAnzeige.setHorizontalAlignment(SwingConstants.CENTER);
        ergebnisAnzeige.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        constraints.gridy = 3;
        formular.add(ergebnisAnzeige, constraints);

        auslastungAnzeige.setStringPainted(true);
        constraints.gridy = 4;
        formular.add(auslastungAnzeige, constraints);

        teilnehmendeAnzeige.setEditable(false);
        teilnehmendeAnzeige.setRows(4);
        teilnehmendeAnzeige.setLineWrap(true);
        teilnehmendeAnzeige.setWrapStyleWord(true);
        constraints.gridy = 5;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        formular.add(new JScrollPane(teilnehmendeAnzeige), constraints);

        return formular;
    }

    private JPanel erstelleProtokollbereich() {
        JPanel bereich = new JPanel(new BorderLayout());
        bereich.setBorder(BorderFactory.createTitledBorder("Anmeldeprotokoll"));

        protokoll.setEditable(false);
        protokoll.setRows(5);
        protokoll.setLineWrap(true);
        protokoll.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(protokoll);
        scrollPane.setPreferredSize(new Dimension(620, 125));
        bereich.add(scrollPane, BorderLayout.CENTER);
        return bereich;
    }

    private void anmeldungDurchfuehren() {
        Schueler schueler = (Schueler) schuelerAuswahl.getSelectedItem();
        StrategieAuswahl auswahl =
                (StrategieAuswahl) strategieAuswahl.getSelectedItem();
        if (schueler == null || auswahl == null) {
            ergebnisAnzeigen(false, "Bitte Schueler und Strategy auswaehlen.");
            return;
        }

        // Die UI setzt die Abhaengigkeiten zusammen, kennt aber keine
        // fachlichen Zulassungsregeln.
        KursAnmeldeService service = new KursAnmeldeService(
                schulverwaltung,
                auswahl.neueStrategie());

        try {
            Anmeldeergebnis ergebnis =
                    service.anmelden(KURSBEZEICHNUNG, schueler.getId());
            ergebnisAnzeigen(ergebnis.erfolgreich(), ergebnis.nachricht());
            protokoll.append("[%s] %s%n".formatted(auswahl, ergebnis.nachricht()));
            kursanzeigeAktualisieren();
        } catch (IllegalArgumentException exception) {
            ergebnisAnzeigen(false, exception.getMessage());
            protokoll.append("FEHLER: " + exception.getMessage()
                    + System.lineSeparator());
        }
    }

    private void ergebnisAnzeigen(boolean erfolgreich, String nachricht) {
        ergebnisAnzeige.setForeground(erfolgreich ? ERFOLG_FARBE : FEHLER_FARBE);
        ergebnisAnzeige.setText(nachricht);
    }

    private void kursanzeigeAktualisieren() {
        Kursangebot kursangebot =
                schulverwaltung.getKursangebote().get(KURSBEZEICHNUNG);
        int belegt = kursangebot.getAnzahlTeilnehmende();
        int maximal = kursangebot.getMaxTeilnehmende();

        auslastungAnzeige.setMinimum(0);
        auslastungAnzeige.setMaximum(maximal);
        auslastungAnzeige.setValue(belegt);
        auslastungAnzeige.setString("%d von %d Plaetzen belegt".formatted(
                belegt, maximal));

        String namen = kursangebot.getKurs().getTeilnehmende().stream()
                .map(schueler -> "%s (%s)".formatted(
                        schueler.getName(), schueler.getKlasse()))
                .collect(Collectors.joining(System.lineSeparator()));
        teilnehmendeAnzeige.setText(namen.isBlank()
                ? "Noch keine Teilnehmenden"
                : namen);
    }

    private record StrategieAuswahl(
            String bezeichnung,
            Supplier<Zulassungsstrategie> fabrik) {

        private Zulassungsstrategie neueStrategie() {
            return fabrik.get();
        }

        @Override
        public String toString() {
            return bezeichnung;
        }
    }

    private static class SchuelerRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            if (value instanceof Schueler schueler) {
                setText("%d - %s (%s)".formatted(
                        schueler.getId(),
                        schueler.getName(),
                        schueler.getKlasse()));
            }
            return component;
        }
    }
}
