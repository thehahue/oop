package at.bbrz.oop.uebung13_strategy_swing;

import at.bbrz.oop.uebung06_kursverwaltung.Schulverwaltung;
import at.bbrz.oop.uebung11_strategy.Uebung11;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Uebung13 {
    public static void main(String[] args) {
        Schulverwaltung verwaltung = Uebung11.beispielVerwaltungErstellen();

        SwingUtilities.invokeLater(() -> {
            systemDesignVerwenden();
            KursAnmeldungFenster fenster =
                    new KursAnmeldungFenster(verwaltung);
            fenster.setVisible(true);
        });
    }

    private static void systemDesignVerwenden() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            // Das plattformunabhaengige Swing-Design bleibt als Rueckfall aktiv.
        }
    }
}
