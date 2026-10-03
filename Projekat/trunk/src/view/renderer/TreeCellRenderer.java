package view.renderer;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Prilagođava prikaz elemenata stabla metapodataka.
 */
public class TreeCellRenderer extends DefaultTreeCellRenderer {

    /**
     * Vraća vrijednost koju čuva objekat.
     *
     * @param tree vrijednost koja se prosljeđuje metodi
     * @param value tekstualna vrijednost koja se upisuje
     * @param selected vrijednost koja se prosljeđuje metodi
     * @param expanded vrijednost koja se prosljeđuje metodi
     * @param leaf vrijednost koja se prosljeđuje metodi
     * @param row vrijednost koja se prosljeđuje metodi
     * @param hasFocus vrijednost koja se prosljeđuje metodi
     * @return komponenta podešena za prikaz zadatog čvora
     */
    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus
    ) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        String nodeText = value.toString();
        Image imageRegion = new ImageIcon("src/resources/icons/" + nodeText + ".png").getImage();
        Image scaledImageRegion = imageRegion.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon regionIcon = new ImageIcon(scaledImageRegion);
        setIcon(regionIcon);

        return this;
    }
}
