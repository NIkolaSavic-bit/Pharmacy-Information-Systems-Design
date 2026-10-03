package view.component;

import javax.swing.JTree;
import javax.swing.tree.TreePath;
import metadata.TreeElement;
import view.renderer.TreeCellRenderer;

/**
 * Prilagođava Swing stablo potrebama aplikacije.
 */
public class CustomTree extends JTree {

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param model model čiji se podaci prikazuju
     */
    public CustomTree(MetadataTreeModel model) {
        super(model);
        setRootVisible(false);
        setShowsRootHandles(true);
        setRowHeight(30);
        setCellRenderer(new TreeCellRenderer());

        TreeElement.Package root = (TreeElement.Package) model.getRoot();
        for (TreeElement child : root.getAllElements()) {
            expandPath(new TreePath(new Object[] { root, child }));
        }
    }

    /**
     * Vraća da li stablo prati širinu vidljivog prostora.
     *
     * @return da li stablo prati širinu vidljivog prostora
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        if (getPreferredSize().width < getParent().getWidth()) {
            return true;
        } else {
            return false;
        }
    }
}
