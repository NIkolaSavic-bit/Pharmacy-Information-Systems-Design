package view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.ApplicationModel;
import observer.Subject;
import observer.Subscriber;

/**
 * Prikazuje trenutno stanje i izbor u aplikaciji.
 */
public class StatusBar extends JPanel implements Subscriber {

    private static final long serialVersionUID = 1L;
    private ApplicationModel applicationModel;
    private Table table;
    private JLabel selectedTableLabel;
    private JLabel stateLabel;
    private JLabel currentRowLabel;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @param table komponenta tabele povezana sa prikazom
     */
    public StatusBar(ApplicationModel applicationModel, Table table) {
        this.applicationModel = applicationModel;
        this.table = table;
        setLayout(new GridLayout(1, 3));
        setPreferredSize(new Dimension(0, 25));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        selectedTableLabel = createSection(SwingConstants.LEFT);
        stateLabel = createSection(SwingConstants.CENTER);
        currentRowLabel = createSection(SwingConstants.RIGHT);
        add(selectedTableLabel);
        add(stateLabel);
        add(currentRowLabel);
        refresh();
    }

    private JLabel createSection(int alignment) {
        JLabel label = new JLabel(" ", alignment);
        label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        return label;
    }

    /**
     * Osvježava tekst statusne trake prema trenutnom izboru i stanju.
     */
    public void refresh() {
        String tableName = table.getSelectedTableName();
        selectedTableLabel.setText("Odabrana tabela: " + (tableName == null || tableName.isEmpty() ? "" : tableName));

        stateLabel.setText("Stanje: " + applicationModel.getApplicationState().getDisplayName());

        int totalRows = table.getRowCount();
        int selectedRow = table.getSelectedRow();
        currentRowLabel.setText("Trenutni red: " + (selectedRow < 0 ? 0 : selectedRow + 1) + "/" + totalRows);
    }

    /**
     * Obrađuje obavještenje o promjeni.
     *
     * @param subject objekat koji je poslao obavještenje
     */
    @Override
    public void update(Subject subject) {
        applicationModel = (ApplicationModel) subject;
        refresh();
    }
}
