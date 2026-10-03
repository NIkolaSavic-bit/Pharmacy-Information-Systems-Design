package view.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import metadata.TableColumnModel;
import model.ApplicationModel;
import util.DataTypeMapper;

/**
 * Kreira komponente forme prema tipu kolone.
 */
public class FormFieldFactory {

    private final Form owner;
    private final ApplicationModel applicationModel;
    private final DataTypeMapper dataTypeMapper = new DataTypeMapper();

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param owner forma kojoj pripada kreirano polje
     * @param applicationModel centralni model aplikacije
     */
    public FormFieldFactory(Form owner, ApplicationModel applicationModel) {
        this.owner = owner;
        this.applicationModel = applicationModel;
    }

    /**
     * Kreira prikaznu i ulaznu komponentu za zadatu kolonu.
     *
     * @param column indeks ili opis kolone
     * @return rezultat izvršene operacije
     */
    public FormField create(TableColumnModel column) {
        if (isReference(column)) return createReferenceField(column);

        String columnType = dataTypeMapper.extractTypeName(column.getColumType());
        if ("datetime".equalsIgnoreCase(columnType)) return createDateField();
        if ("bit".equalsIgnoreCase(columnType)) return createCheckBox();
        return createTextField(column, columnType);
    }

    private FormField createReferenceField(TableColumnModel column) {
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(120, 25));
        input.setEditable(false);

        JButton pickerButton = new JButton("...");
        pickerButton.setMargin(new Insets(2, 5, 2, 5));
        pickerButton.addActionListener(event -> ReferencePickerDialog.open(owner, applicationModel, column, input));

        JPanel display = new JPanel(new BorderLayout());
        display.add(input, BorderLayout.CENTER);
        display.add(pickerButton, BorderLayout.EAST);
        return new FormField(display, input);
    }

    private FormField createDateField() {
        JSpinner input = new JSpinner(new SpinnerDateModel());
        input.setEditor(new JSpinner.DateEditor(input, "yyyy-MM-dd HH:mm:ss"));
        input.setPreferredSize(new Dimension(150, 25));
        return new FormField(input, input);
    }

    private FormField createCheckBox() {
        JCheckBox input = new JCheckBox();
        input.setSelected(false);
        return new FormField(input, input);
    }

    private FormField createTextField(TableColumnModel column, String columnType) {
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(150, 25));

        if (column.isPrimaryKey() && column.isAutoIncrement()) {
            if ("varchar".equalsIgnoreCase(columnType)) {
                input.setEditable(false);
            } else if ("int".equalsIgnoreCase(columnType)) {
                Integer highestKey = applicationModel.getContext().getDataModel().getHighestPrimaryKey();
                input.setText(Integer.toString(highestKey == null ? 1 : highestKey + 1));
                input.setEditable(false);
            }
        }
        return new FormField(input, input);
    }

    private boolean isReference(TableColumnModel column) {
        return column.getRefColumn() != null &&
        !column.getRefColumn().isEmpty() &&
        column.getRefTable() != null &&
        !column.getRefTable().isEmpty();
    }

    /**
     * Objedinjuje prikaznu i ulaznu komponentu polja forme.
     */
    public static class FormField {

        private final JComponent displayComponent;
        private final JComponent inputComponent;

        /**
         * Inicijalizuje objekat za rad u aplikaciji.
         *
         * @param displayComponent komponenta koja prikazuje naziv polja
         * @param inputComponent komponenta za unos vrijednosti
         */
        public FormField(JComponent displayComponent, JComponent inputComponent) {
            this.displayComponent = displayComponent;
            this.inputComponent = inputComponent;
        }

        /**
         * Vraća komponentu za prikaz naziva polja.
         *
         * @return komponentu za prikaz naziva polja
         */
        public JComponent getDisplayComponent() {
            return displayComponent;
        }

        /**
         * Vraća komponentu za unos vrijednosti.
         *
         * @return komponentu za unos vrijednosti
         */
        public JComponent getInputComponent() {
            return inputComponent;
        }
    }
}
