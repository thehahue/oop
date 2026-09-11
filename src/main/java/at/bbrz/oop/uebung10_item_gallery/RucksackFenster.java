package at.bbrz.oop.uebung10_item_gallery;

import at.bbrz.oop.uebung09_polymorphie_ohne_switch.Backpack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Locale;

/**
 * Eine kleine visuelle Inventar-App fuer den Rucksack aus Uebung 9.
 */
public class RucksackFenster extends JFrame {
    private static final Color HINTERGRUND = new Color(8, 20, 38);
    private static final Color KARTE = new Color(17, 43, 64);
    private static final Color AKZENT = new Color(84, 214, 255);
    private static final Color ORANGE = new Color(255, 138, 61);
    private static final Color TEXT = new Color(235, 245, 255);
    private static final Color TEXT_SEKUNDAER = new Color(170, 197, 214);

    private final Backpack backpack;
    private final DefaultListModel<ItemMitBild> itemModell = new DefaultListModel<>();
    private final JList<ItemMitBild> itemListe = new JList<>(itemModell);
    private final JLabel zusammenfassung = new JLabel();
    private final JProgressBar gewichtAnzeige = new JProgressBar(0, 1000);

    public RucksackFenster(
            Backpack backpack,
            ItemMitBild rucksackMitBild,
            List<ItemMitBild> items) {
        if (backpack == null || rucksackMitBild == null || items == null) {
            throw new IllegalArgumentException("Rucksack, Bild und Items duerfen nicht null sein.");
        }
        this.backpack = backpack;
        items.forEach(itemModell::addElement);

        setTitle("Pack & Go - dein visueller Rucksack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 650));
        setContentPane(erstelleInhalt(rucksackMitBild));

        itemListe.setCellRenderer(new ItemRenderer());
        itemListe.setFixedCellHeight(112);
        itemListe.setBackground(HINTERGRUND);
        zusammenfassungAktualisieren();
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel erstelleInhalt(ItemMitBild rucksackMitBild) {
        JPanel inhalt = new JPanel(new BorderLayout(18, 18));
        inhalt.setBackground(HINTERGRUND);
        inhalt.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        inhalt.add(erstelleKopfbereich(), BorderLayout.NORTH);

        JSplitPane bereiche = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                erstelleRucksackKarte(rucksackMitBild),
                erstelleInventarBereich());
        bereiche.setBorder(null);
        bereiche.setDividerSize(14);
        bereiche.setResizeWeight(0.38);
        bereiche.setBackground(HINTERGRUND);
        inhalt.add(bereiche, BorderLayout.CENTER);
        return inhalt;
    }

    private JPanel erstelleKopfbereich() {
        JPanel kopf = new JPanel(new BorderLayout());
        kopf.setOpaque(false);

        JLabel titel = new JLabel("PACK & GO");
        titel.setForeground(TEXT);
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 30f));
        kopf.add(titel, BorderLayout.WEST);

        JLabel untertitel = new JLabel("Uebung 10  /  Wrapper trifft Swing");
        untertitel.setForeground(AKZENT);
        untertitel.setFont(untertitel.getFont().deriveFont(Font.BOLD, 14f));
        kopf.add(untertitel, BorderLayout.EAST);
        return kopf;
    }

    private JPanel erstelleRucksackKarte(ItemMitBild rucksackMitBild) {
        JPanel karte = new JPanel();
        karte.setLayout(new BoxLayout(karte, BoxLayout.Y_AXIS));
        karte.setBackground(KARTE);
        karte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(35, 78, 104), 2, true),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel bild = new JLabel(rucksackMitBild.getBild(360, 225));
        bild.setAlignmentX(Component.CENTER_ALIGNMENT);
        karte.add(bild);
        karte.add(Box.createVerticalStrut(15));

        JLabel titel = new JLabel("DEIN ABENTEUER-RUCKSACK");
        titel.setForeground(ORANGE);
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 17f));
        titel.setAlignmentX(Component.CENTER_ALIGNMENT);
        karte.add(titel);
        karte.add(Box.createVerticalStrut(12));

        zusammenfassung.setForeground(TEXT);
        zusammenfassung.setAlignmentX(Component.CENTER_ALIGNMENT);
        zusammenfassung.setHorizontalAlignment(SwingConstants.CENTER);
        karte.add(zusammenfassung);
        karte.add(Box.createVerticalStrut(15));

        gewichtAnzeige.setStringPainted(true);
        gewichtAnzeige.setForeground(ORANGE);
        gewichtAnzeige.setBackground(HINTERGRUND);
        gewichtAnzeige.setBorderPainted(false);
        gewichtAnzeige.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        karte.add(gewichtAnzeige);
        karte.add(Box.createVerticalStrut(12));

        JLabel hinweis = new JLabel(
                "<html><center>Jeder Gegenstand behaelt seine Fachlogik.<br>"
                        + "Der Wrapper schenkt ihm nur ein Gesicht.</center></html>");
        hinweis.setForeground(TEXT_SEKUNDAER);
        hinweis.setAlignmentX(Component.CENTER_ALIGNMENT);
        hinweis.setHorizontalAlignment(SwingConstants.CENTER);
        karte.add(hinweis);
        return karte;
    }

    private JPanel erstelleInventarBereich() {
        JPanel inventar = new JPanel(new BorderLayout(12, 12));
        inventar.setOpaque(false);

        JLabel titel = new JLabel("INVENTAR");
        titel.setForeground(TEXT);
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 19f));
        inventar.add(titel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(itemListe);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(35, 78, 104)));
        scrollPane.getViewport().setBackground(HINTERGRUND);
        inventar.add(scrollPane, BorderLayout.CENTER);
        return inventar;
    }

    private void zusammenfassungAktualisieren() {
        double inhaltGewicht = backpack.contentWeightInKg();
        double maxGewicht = backpack.getMaxWeightInKg();
        int fortschritt = (int) Math.round(inhaltGewicht / maxGewicht * 1000);
        gewichtAnzeige.setValue(fortschritt);
        gewichtAnzeige.setString("%.2f / %.2f kg Inhalt".formatted(inhaltGewicht, maxGewicht));

        zusammenfassung.setText(("<html><center>%d Gegenstaende<br>"
                + "Gesamtwert: <b>%.2f EUR</b><br>"
                + "Mit Rucksack: %.2f kg</center></html>").formatted(
                itemModell.size(), backpack.priceInEur(), backpack.weightInKg()));
    }

    private static final class ItemRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            ItemMitBild item = (ItemMitBild) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(item.getBild(136, 85));
            label.setIconTextGap(16);
            label.setText(("<html><b>%s</b><br><font color='#AAD0E5'>"
                    + "%.2f kg &nbsp;&middot;&nbsp; %s EUR</font></html>").formatted(
                    item.getDescription(),
                    item.weightInKg(),
                    String.format(Locale.GERMANY, "%.2f", item.priceInEur())));
            label.setForeground(TEXT);
            label.setBackground(isSelected ? new Color(28, 78, 101) : KARTE);
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 5, 0, HINTERGRUND),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            return label;
        }
    }
}
