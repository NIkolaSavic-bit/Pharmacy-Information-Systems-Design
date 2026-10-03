package view.component;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import metadata.TableColumnModel;
import model.ApplicationModel;
import model.GeneralTableModel;
import state.CreateState;

/**
 * Prikazuje dijalog za izbor referencirane vrijednosti.
 */
public final class ReferencePickerDialog {

    private ReferencePickerDialog() {}

    /**
     * Otvara modalni dijalog i upisuje izabranu referencu u polje forme.
     *
     * @param owner forma kojoj pripada kreirano polje
     * @param applicationModel centralni model aplikacije
     * @param column indeks ili opis kolone
     * @param targetField vrijednost koja se prosljeđuje metodi
     */
    public static void open(
        Form owner,
        ApplicationModel applicationModel,
        TableColumnModel column,
        JTextField targetField
    ) {
        try {
            GeneralTableModel referenceModel = loadModel(column.getRefTable());
            List<TableColumnModel> visibleColumns = getVisibleColumns(referenceModel);
            String[] columnNames = visibleColumns
                .stream()
                .map(TableColumnModel::getColumName)
                .toArray(String[]::new);

            DefaultTableModel tableModel = new DefaultTableModel(
                toRows(referenceModel, visibleColumns),
                columnNames
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable table = new JTable(tableModel);
            JDialog dialog = createDialog(owner, column.getRefTable());

            JButton selectButton = new JButton("Izaberi");
            selectButton.addActionListener(event -> {
                copySelectedValue(table, columnNames, column.getRefColumn(), targetField);
                dialog.dispose();
            });

            JButton newRecordButton = new JButton("Novi zapis");
            newRecordButton.addActionListener(event -> {
                openCreateDialog(dialog, column.getRefTable());
                refreshTable(tableModel, column.getRefTable(), owner);
            });

            JPanel buttons = new JPanel(new GridLayout(1, 2));
            buttons.add(selectButton);
            buttons.add(newRecordButton);

            JPanel content = new JPanel(new BorderLayout());
            content.add(new JScrollPane(table), BorderLayout.CENTER);
            content.add(buttons, BorderLayout.SOUTH);
            dialog.setContentPane(content);
            dialog.setVisible(true);
        } catch (RuntimeException e) {
            showLoadError(owner, column.getRefTable());
        }
    }

    private static JDialog createDialog(Form owner, String tableName) {
        JDialog dialog = new JDialog(
            SwingUtilities.getWindowAncestor(owner),
            "Izaberi iz " + tableName,
            Dialog.ModalityType.APPLICATION_MODAL
        );
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/resources/icons/" + tableName + ".png");
        dialog.setIconImage(icon.getImage());
        return dialog;
    }

    private static void copySelectedValue(
        JTable table,
        String[] columnNames,
        String referenceColumn,
        JTextField targetField
    ) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) return;

        for (int index = 0; index < columnNames.length; index++) {
            if (columnNames[index].equals(referenceColumn)) {
                Object value = table.getValueAt(selectedRow, index);
                targetField.setText(value == null ? "" : value.toString());
                return;
            }
        }
    }

    private static void openCreateDialog(JDialog parent, String tableName) {
        JDialog dialog = new JDialog(parent, "Novi zapis u " + tableName, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);

        ApplicationModel model = new ApplicationModel();
        model.getContext().setDataModel(tableName);
        Form form = new Form(model);
        CreateState createState = new CreateState(model);
        model.changeState(createState);
        form.renderForm(model, createState, null, null, null);
        form.setVisible(true);

        dialog.setContentPane(form);
        dialog.setVisible(true);
    }

    private static void refreshTable(
        DefaultTableModel tableModel,
        String tableName,
        Form owner
    ) {
        try {
            GeneralTableModel refreshedModel = loadModel(tableName);
            List<TableColumnModel> visibleColumns = getVisibleColumns(refreshedModel);
            String[] columnNames = visibleColumns
                .stream()
                .map(TableColumnModel::getColumName)
                .toArray(String[]::new);
            tableModel.setDataVector(toRows(refreshedModel, visibleColumns), columnNames);
        } catch (RuntimeException e) {
            showLoadError(owner, tableName);
        }
    }

    private static GeneralTableModel loadModel(String tableName) {
        ApplicationModel model = new ApplicationModel();
        model.getContext().setDataModel(tableName);
        return model.getContext().getDataModel();
    }

    private static List<TableColumnModel> getVisibleColumns(GeneralTableModel model) {
        return model
            .getColumns()
            .stream()
            .filter(TableColumnModel::isVisible)
            .collect(java.util.stream.Collectors.toList());
    }

    private static Object[][] toRows(
        GeneralTableModel model,
        List<TableColumnModel> visibleColumns
    ) {
        return model
            .getData()
            .stream()
            .map(row ->
                visibleColumns
                    .stream()
                    .map(column -> row.get(model.getColumns().indexOf(column)))
                    .toArray(Object[]::new)
            )
            .toArray(Object[][]::new);
    }

    private static void showLoadError(Form owner, String tableName) {
        JOptionPane.showMessageDialog(
            null,
            "Učitavanje referentnih podataka nije uspjelo za tabelu: " + tableName,
            "Greška",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
