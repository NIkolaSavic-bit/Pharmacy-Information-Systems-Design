package view.component;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.JPanel;
import model.ApplicationModel;
import observer.Subject;
import observer.Subscriber;

/**
 * Prikazuje tabelarne podatke aktivnog modela.
 */
public class Table extends JPanel implements Subscriber {

    private CustomTable customTable;
    private ApplicationModel applicationModel;
    private Runnable selectionChangeListener = () -> {};
    private String displayedTableName = "";

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     */
    public Table(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
        setLayout(new BorderLayout());
    }

    /**
     * Osvježava prikaz podacima aktivnog modela.
     *
     * @param applicationModel centralni model aplikacije
     */
    public void showTable(ApplicationModel applicationModel) {
        String newTableName = applicationModel.getContext().getDataModel().getTableName();
        if (customTable != null) {
            remove(customTable);

            int selectedIndex = Objects.equals(displayedTableName, newTableName)
                ? customTable.getSelectedRowIndex()
                : -1;
            customTable = new CustomTable(
                applicationModel.getContext().getDataModel(),
                applicationModel,
                selectionChangeListener
            );
            if (selectedIndex >= 0) customTable.setSelectedRowIndex(selectedIndex);
        } else {
            customTable = new CustomTable(
                applicationModel.getContext().getDataModel(),
                applicationModel,
                selectionChangeListener
            );
        }
        displayedTableName = newTableName;
        List<List<Object>> data = applicationModel.getContext().getDataModel().getData();
        boolean hasData = data.stream().anyMatch(col -> col != null && !col.isEmpty());

        if (hasData) {
            add(customTable, BorderLayout.CENTER);
            setVisible(true);
            customTable.setVisible(true);
        } else {
            setVisible(false);
            customTable.setVisible(false);
        }

        revalidate();
        repaint();
    }

    /**
     * Vraća indeks izabranog reda.
     *
     * @return indeks izabranog reda
     */
    public int getSelectedRow() {
        if (customTable != null) return customTable.getSelectedRowIndex();
        return -1;
    }

    /**
     * Mijenja izbor reda u tabeli.
     */
    public void selectNextRow() {
        if (customTable != null) customTable.selectNextRow();
    }

    /**
     * Mijenja izbor reda u tabeli.
     */
    public void selectPreviousRow() {
        if (customTable != null) customTable.selectPreviousRow();
    }

    /**
     * Mijenja izbor reda u tabeli.
     */
    public void selectFirstRow() {
        if (customTable != null) customTable.selectFirstRow();
    }

    /**
     * Mijenja izbor reda u tabeli.
     */
    public void selectLastRow() {
        if (customTable != null) customTable.selectLastRow();
    }

    /**
     * Vraća komponentu koja prikazuje podatke.
     *
     * @return komponentu koja prikazuje podatke
     */
    public CustomTable getCustomTable() {
        return customTable;
    }

    /**
     * Vraća broj redova modela.
     *
     * @return broj redova modela
     */
    public int getRowCount() {
        List<List<Object>> data = applicationModel.getContext().getDataModel().getData();
        return data.stream().anyMatch(row -> row != null && row.stream().anyMatch(Objects::nonNull)) ? data.size() : 0;
    }

    /**
     * Vraća naziv trenutno prikazane tabele.
     *
     * @return naziv trenutno prikazane tabele
     */
    public String getSelectedTableName() {
        return applicationModel.getContext().getDataModel().getTableName();
    }

    /**
     * Postavlja novu vrijednost svojstva objekta.
     *
     * @param selectionChangeListener akcija koja se poziva nakon promjene izbora
     */
    public void setSelectionChangeListener(Runnable selectionChangeListener) {
        this.selectionChangeListener = selectionChangeListener == null ? () -> {} : selectionChangeListener;
        if (customTable != null) customTable.setSelectionChangeListener(this.selectionChangeListener);
    }

    /**
     * Obrađuje obavještenje o promjeni.
     *
     * @param applicationModel centralni model aplikacije
     */
    @Override
    public void update(Subject applicationModel) {
        this.applicationModel = (ApplicationModel) applicationModel;
        showTable(this.applicationModel);

        revalidate();
        repaint();
    }
}
