package view.menu;

import command.DeleteCommand;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import metadata.TableColumnModel;
import model.ApplicationModel;
import state.ApplicationState;
import state.CreateState;
import state.EditState;
import view.report.JasperReportsViewer;
import view.component.Form;
import view.component.Form;
import view.component.Table;
import view.menu.MenuItem;

/**
 * Predstavlja meni za izmjenu podataka.
 */
public class EditMenu extends JMenu implements Menu {

    private MenuItem nextMenuItem = null;
    private MenuItem previousMenuItem = null;
    private MenuItem firstMenuItem = null;
    private MenuItem lastMenuItem = null;
    private MenuItem newMenuItem = null;
    private MenuItem editMenuItem = null;
    private MenuItem deleteMenuItem = null;
    private MenuItem acceptMenuItem = null;
    private MenuItem cancelMenuItem = null;
    private MenuItem reportMenuItem = null;
    private ApplicationModel applicationModel;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param table komponenta tabele povezana sa prikazom
     * @param applicationModel centralni model aplikacije
     * @param form forma povezana sa komponentom
     */
    public EditMenu(Table table, ApplicationModel applicationModel, Form form) {
        super("Izmjeni");

        this.applicationModel = applicationModel;
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        nextMenuItem = new MenuItem("Sledeće");
        Image nextIcon = toolkit.getImage("src/resources/icons/next.png");
        nextIcon = nextIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        nextMenuItem.setIcon(new ImageIcon(nextIcon));
        nextMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK));
        nextMenuItem.setMnemonic(KeyEvent.VK_RIGHT);
        nextMenuItem.addActionListener(e -> {
            if (table != null) table.selectNextRow();
        });
        add(nextMenuItem);

        previousMenuItem = new MenuItem("Prethodno");
        Image previousIcon = toolkit.getImage("src/resources/icons/previous.png");
        previousIcon = previousIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        previousMenuItem.setIcon(new ImageIcon(previousIcon));
        previousMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK));
        previousMenuItem.setMnemonic(KeyEvent.VK_LEFT);
        previousMenuItem.addActionListener(e -> {
            if (table != null) table.selectPreviousRow();
        });
        add(previousMenuItem);

        firstMenuItem = new MenuItem("Prvo");
        Image firstIcon = toolkit.getImage("src/resources/icons/first.png");
        firstIcon = firstIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        firstMenuItem.setIcon(new ImageIcon(firstIcon));
        firstMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK));
        firstMenuItem.setMnemonic(KeyEvent.VK_UP);
        firstMenuItem.addActionListener(e -> {
            if (table != null) table.selectFirstRow();
        });
        add(firstMenuItem);

        lastMenuItem = new MenuItem("Posljednje");
        Image lastIcon = toolkit.getImage("src/resources/icons/last.png");
        lastIcon = lastIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        lastMenuItem.setIcon(new ImageIcon(lastIcon));
        lastMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK));
        lastMenuItem.setMnemonic(KeyEvent.VK_DOWN);
        lastMenuItem.addActionListener(e -> {
            if (table != null) table.selectLastRow();
        });
        add(lastMenuItem);

        addSeparator();

        newMenuItem = new MenuItem("Novi zapis");
        Image newIcon = toolkit.getImage("src/resources/icons/new.png");
        newIcon = newIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        newMenuItem.setIcon(new ImageIcon(newIcon));
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        newMenuItem.addActionListener(e -> {
            if (form instanceof Form) {
                CreateState createState = new CreateState(applicationModel);
                applicationModel.changeState(createState);
                generateForm((Form) form, table, createState);
            }
        });
        add(newMenuItem);

        editMenuItem = new MenuItem("Uredi");
        Image editIcon = toolkit.getImage("src/resources/icons/edit.png");
        editIcon = editIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        editMenuItem.setIcon(new ImageIcon(editIcon));
        editMenuItem.setMnemonic(KeyEvent.VK_E);
        editMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
        editMenuItem.addActionListener(e -> {
            if (form instanceof Form) {
                EditState editState = new EditState(applicationModel);
                generateForm((Form) form, table, editState);
            }
        });
        add(editMenuItem);

        deleteMenuItem = new MenuItem("Izbriši");
        Image deleteIcon = toolkit.getImage("src/resources/icons/delete.png");
        deleteIcon = deleteIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        deleteMenuItem.setIcon(new ImageIcon(deleteIcon));
        deleteMenuItem.setMnemonic(KeyEvent.VK_DELETE);
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_DOWN_MASK));
        deleteMenuItem.addActionListener(e -> deleteSelectedRecord(table));
        add(deleteMenuItem);

        addSeparator();

        acceptMenuItem = new MenuItem("Prihvati");
        Image acceptIcon = toolkit.getImage("src/resources/icons/accept.png");
        acceptIcon = acceptIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        acceptMenuItem.setIcon(new ImageIcon(acceptIcon));
        acceptMenuItem.setMnemonic(KeyEvent.VK_ENTER);
        acceptMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK));
        acceptMenuItem.addActionListener(e -> {
            if (form instanceof Form) {
                ((Form) form).submit();
            }
        });
        add(acceptMenuItem);

        cancelMenuItem = new MenuItem("Otkaži");
        Image cancelIcon = toolkit.getImage("src/resources/icons/cancel.png");
        cancelIcon = cancelIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        cancelMenuItem.setIcon(new ImageIcon(cancelIcon));
        cancelMenuItem.setMnemonic(KeyEvent.VK_ESCAPE);
        cancelMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, KeyEvent.CTRL_DOWN_MASK));
        cancelMenuItem.addActionListener(e -> {
            if (form instanceof Form) {
                ((Form) form).cancel();
            }
        });
        add(cancelMenuItem);

        addSeparator();

        reportMenuItem = new MenuItem("Izvještaj");
        Image reportIcon = toolkit.getImage("src/resources/icons/report.png");
        reportIcon = reportIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        reportMenuItem.setIcon(new ImageIcon(reportIcon));
        reportMenuItem.setMnemonic(KeyEvent.VK_P);
        reportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
        reportMenuItem.addActionListener(e -> {
            if (table != null && table.getCustomTable() != null) {
                new JasperReportsViewer().showTableReport(
                    table.getCustomTable().getTable(),
                    table.getSelectedTableName()
                );
            }
        });
        add(reportMenuItem);

    }

    /**
     * Prilagođava kontrole stanju bez aktivne tabele.
     */
    @Override
    public void idleState() {
        nextMenuItem.setEnabled(false);
        previousMenuItem.setEnabled(false);
        firstMenuItem.setEnabled(false);
        lastMenuItem.setEnabled(false);
        newMenuItem.setEnabled(false);
        editMenuItem.setEnabled(false);
        deleteMenuItem.setEnabled(false);
        acceptMenuItem.setEnabled(false);
        cancelMenuItem.setEnabled(false);
        reportMenuItem.setEnabled(false);
    }

    /**
     * Prilagođava kontrole stanju sa aktivnom tabelom.
     */
    @Override
    public void activeState() {
        nextMenuItem.setEnabled(true);
        previousMenuItem.setEnabled(true);
        firstMenuItem.setEnabled(true);
        lastMenuItem.setEnabled(true);
        newMenuItem.setEnabled(true);
        editMenuItem.setEnabled(true);
        deleteMenuItem.setEnabled(true);
        acceptMenuItem.setEnabled(false);
        cancelMenuItem.setEnabled(false);
        reportMenuItem.setEnabled(true);
    }

    /**
     * Prilagođava kontrole unosu novog reda.
     */
    @Override
    public void createState() {
        nextMenuItem.setEnabled(false);
        previousMenuItem.setEnabled(false);
        firstMenuItem.setEnabled(false);
        lastMenuItem.setEnabled(false);
        newMenuItem.setEnabled(false);
        editMenuItem.setEnabled(false);
        deleteMenuItem.setEnabled(false);
        acceptMenuItem.setEnabled(true);
        cancelMenuItem.setEnabled(true);
        reportMenuItem.setEnabled(false);
    }

    /**
     * Prilagođava kontrole izmjeni izabranog reda.
     */
    @Override
    public void editState() {
        nextMenuItem.setEnabled(false);
        previousMenuItem.setEnabled(false);
        firstMenuItem.setEnabled(false);
        lastMenuItem.setEnabled(false);
        newMenuItem.setEnabled(false);
        editMenuItem.setEnabled(false);
        deleteMenuItem.setEnabled(false);
        acceptMenuItem.setEnabled(true);
        cancelMenuItem.setEnabled(true);
        reportMenuItem.setEnabled(false);
    }

    private void generateForm(Form form, Table table, ApplicationState state) {
        if (state instanceof CreateState) {
            form.setVisible(true);
            form.renderForm(this.applicationModel, state, null, null, table);
            return;
        }

        if (
            table == null ||
            table.getCustomTable() == null ||
            table.getSelectedRow() < 0 ||
            table.getSelectedRow() >= this.applicationModel.getContext().getDataModel().getData().size()
        ) {
            javax.swing.JOptionPane.showMessageDialog(null, "Izaberite zapis koji želite da uredite.");
            return;
        }

        applicationModel.changeState(state);
        form.setVisible(true);
        int rowIndex = table.getCustomTable().getSelectedRowIndex();
        List<TableColumnModel> tableColumns = this.applicationModel.getContext().getDataModel().getColumns();
        List<Object> tableData = this.applicationModel.getContext().getDataModel().getData().get(rowIndex);
        HashMap<String, Object> primaryKeys = new LinkedHashMap<String, Object>();

        for (int i = 0; i < tableColumns.size(); i++) {
            if (tableColumns.get(i).isPrimaryKey()) {
                primaryKeys.put(tableColumns.get(i).getColumType() + i, tableData.get(i));
            }
        }

        form.renderForm(
            this.applicationModel,
            state,
            rowIndex,
            primaryKeys.keySet() != null ? applicationModel.getContext().getDataModel().getRowById(primaryKeys) : null,
            table
        );
    }

    private void deleteSelectedRecord(Table table) {
        if (
            table == null ||
            table.getSelectedRow() < 0 ||
            table.getSelectedRow() >= applicationModel.getContext().getDataModel().getData().size()
        ) {
            JOptionPane.showMessageDialog(null, "Izaberite zapis koji želite da obrišete.");
            return;
        }

        int response = JOptionPane.showConfirmDialog(
            null,
            "Da li ste sigurni da želite da obrišete ovaj podatak?",
            "Potvrda brisanja",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        if (response != JOptionPane.YES_OPTION) return;

        int rowIndex = table.getSelectedRow();
        List<TableColumnModel> columns = applicationModel.getContext().getDataModel().getColumns();
        List<Object> row = applicationModel.getContext().getDataModel().getData().get(rowIndex);
        HashMap<String, Object> primaryKeys = new LinkedHashMap<>();

        for (int index = 0; index < columns.size(); index++) {
            if (columns.get(index).isPrimaryKey()) {
                primaryKeys.put(columns.get(index).getColumType() + index, row.get(index));
            }
        }

        if (new DeleteCommand(applicationModel, primaryKeys).execute()) {
            JOptionPane.showMessageDialog(null, "Podatak je uspješno obrisan!");
        } else {
            JOptionPane.showMessageDialog(null, "Brisanje podatka nije uspjelo.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
