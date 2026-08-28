package at.bbrz.oop.uebung07_benutzeroberflaeche;

import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

/**
 * Eine einfache Swing-Oberflaeche fuer die Schulverwaltung aus Uebung 6.
 */
public class SchulverwaltungFenster extends JFrame {
    private final Schulverwaltung verwaltung;
    private final JComboBox<String> kursAuswahl;
    private final JTextField schuelerIdFeld = new JTextField(8);
    private final JTextArea protokoll = new JTextArea(10, 45);
    private final JLabel statusMeldung = new JLabel("Bereit");

    public SchulverwaltungFenster(Schulverwaltung verwaltung) {
        this.verwaltung = verwaltung;
        kursAuswahl = new JComboBox<>(verwaltung.getKursnamen());

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
        protokoll.setText("Waehle einen Kurs und gib eine Schueler-ID ein.\n"
                + "Beispiel-IDs: 2 (Mia), 3 (Leon), 4 (Sara)\n");

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel erstelleEingabebereich() {
        JPanel felder = new JPanel(new GridLayout(2, 2, 5, 5));
        felder.add(new JLabel("Kurs:"));
        felder.add(kursAuswahl);
        felder.add(new JLabel("Schueler-ID:"));
        felder.add(schuelerIdFeld);

        JButton anmeldenButton = new JButton("Anmelden");
        anmeldenButton.addActionListener(event -> schuelerAnmelden());

        JButton abmeldenButton = new JButton("Abmelden");
        abmeldenButton.addActionListener(event -> schuelerAbmelden());

        JButton kurseAusgebenButton = new JButton("Kurse ausgeben");
        kurseAusgebenButton.addActionListener(event -> kurseAusgeben());

        JButton protokollLeerenButton = new JButton("Protokoll leeren");
        protokollLeerenButton.addActionListener(event -> {
            protokoll.setText("");
            statusMeldung.setText("Protokoll wurde geleert.");
            statusMeldung.setForeground(Color.GREEN.darker());
        });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(anmeldenButton);
        buttons.add(abmeldenButton);
        buttons.add(kurseAusgebenButton);
        buttons.add(protokollLeerenButton);

        JPanel eingabe = new JPanel(new BorderLayout(5, 5));
        eingabe.add(felder, BorderLayout.CENTER);
        eingabe.add(buttons, BorderLayout.SOUTH);
        return eingabe;
    }

    private void schuelerAnmelden() {
        try {
            String kurs = getAusgewaehlterKurs();
            int schuelerId = getEingegebeneSchuelerId();
            boolean erfolgreich = verwaltung.schuelerAnmelden(kurs, schuelerId);

            if (erfolgreich) {
                erfolgAusgeben("Schueler " + schuelerId + " wurde bei " + kurs + " angemeldet.");
                schuelerIdFeld.setText("");
            } else {
                fehlerAusgeben("Anmeldung nicht moeglich: Kurs voll oder Schueler bereits angemeldet.");
            }
        } catch (NumberFormatException exception) {
            fehlerAusgeben("Bitte eine gueltige Schueler-ID eingeben.");
        } catch (IllegalArgumentException exception) {
            fehlerAusgeben("Fehler: " + exception.getMessage());
        }
    }

    private void schuelerAbmelden() {
        try {
            String kurs = getAusgewaehlterKurs();
            int schuelerId = getEingegebeneSchuelerId();
            boolean erfolgreich = verwaltung.schuelerAbmelden(kurs, schuelerId);

            if (erfolgreich) {
                erfolgAusgeben("Schueler " + schuelerId + " wurde von " + kurs + " abgemeldet.");
            } else {
                fehlerAusgeben("Der Schueler war in diesem Kurs nicht angemeldet.");
            }
        } catch (NumberFormatException exception) {
            fehlerAusgeben("Bitte eine gueltige Schueler-ID eingeben.");
        } catch (IllegalArgumentException exception) {
            fehlerAusgeben("Fehler: " + exception.getMessage());
        }
    }

    private void kurseAusgeben() {
        protokoll.append(verwaltung.getKursuebersicht() + System.lineSeparator());
        erfolgAusgeben("Kursuebersicht wurde ausgegeben.");
    }

    private String getAusgewaehlterKurs() {
        return (String) kursAuswahl.getSelectedItem();
    }

    private int getEingegebeneSchuelerId() {
        return Integer.parseInt(schuelerIdFeld.getText().trim());
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
    // TODO 1: Leere das ID-Feld nach einer erfolgreichen Anmeldung.
    // TODO 2: Fuege einen Button hinzu, der das Protokoll leert.
    // TODO 3: Zeige erfolgreiche Meldungen gruen und Fehlermeldungen rot an.
    // TODO 4: Erweitere die Oberflaeche um das Anlegen neuer Kurse.
}
