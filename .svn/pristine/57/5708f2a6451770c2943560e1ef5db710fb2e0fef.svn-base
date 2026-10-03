package view.component;

import javax.swing.tree.DefaultTreeModel;
import metadata.TreeElement;

/**
 * Prilagođava metapodatke Swing modelu stabla.
 */
public class MetadataTreeModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;
    private final TreeElement.Package rootPackage;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param rootPackage korijenski paket metapodataka
     */
    public MetadataTreeModel(TreeElement.Package rootPackage) {
        super(null);
        this.rootPackage = rootPackage;
    }

    /**
     * Vraća dijete paketa na zadatom indeksu.
     *
     * @param parent roditeljski čvor stabla
     * @param index indeks elementa ili SQL parametra
     * @return dijete na zadatom indeksu ili {@code null} ako roditelj nije paket
     */
    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof TreeElement.Package) {
            return ((TreeElement.Package) parent).getElementAt(index);
        }
        return null;
    }

    /**
     * Vraća broj neposredne djece paketa.
     *
     * @param parent roditeljski čvor stabla
     * @return broj djece ili {@code 0} ako roditelj nije paket
     */
    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof TreeElement.Package) {
            return ((TreeElement.Package) parent).getAllElements().size();
        }
        return 0;
    }

    /**
     * Vraća indeks djeteta unutar roditeljskog paketa.
     *
     * @param parent roditeljski čvor stabla
     * @param child dijete čiji se indeks traži
     * @return indeks djeteta ili {@code -1} ako elementi nisu odgovarajućeg tipa
     */
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if (parent instanceof TreeElement.Package && child instanceof TreeElement) {
            return ((TreeElement.Package) parent).getIndexOfElement((TreeElement) child);
        }
        return -1;
    }

    /**
     * Vraća korijenski paket stabla metapodataka.
     *
     * @return korijenski paket metapodataka
     */
    @Override
    public Object getRoot() {
        return rootPackage;
    }

    /**
     * Provjerava da li čvor predstavlja tabelu.
     *
     * @param node čvor koji se provjerava
     * @return {@code true} ako čvor predstavlja tabelu
     */
    @Override
    public boolean isLeaf(Object node) {
        return node instanceof TreeElement.Table;
    }
}
