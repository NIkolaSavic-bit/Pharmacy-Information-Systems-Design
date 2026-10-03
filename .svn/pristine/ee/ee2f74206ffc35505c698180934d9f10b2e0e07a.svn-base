package view.component;

import command.Command;
import command.ReadCommand;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import metadata.TreeElement;
import metadata.XMLTreeBuilder;
import model.ApplicationModel;
import util.ResourcePaths;

/**
 * Prikazuje stablo dostupnih tabela iz metapodataka.
 */
public class Tree extends JPanel {

    private CustomTree customTree = null;
    private ApplicationModel applicationModel;
    private Form form;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @param form forma povezana sa komponentom
     */
    public Tree(ApplicationModel applicationModel, Form form) {
        this.applicationModel = applicationModel;
        this.form = form;
        MetadataTreeModel model = createTreeModel(applicationModel);
        customTree = new CustomTree(model);
        JScrollPane scrollPane = new JScrollPane(customTree);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setPreferredSize(new Dimension(370, 0));
        setLayout(new BorderLayout());
        setBackground(new Color(173, 216, 230));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        customTree.addTreeSelectionListener(e -> {
            Object selectedNode = customTree.getLastSelectedPathComponent();
            if (selectedNode instanceof TreeElement.Table) {
                this.form.setVisible(false);
                Command readCommand = new ReadCommand(applicationModel, selectedNode.toString());
                if (!readCommand.execute()) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Učitavanje podataka nije uspjelo.",
                        "Greška",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        add(scrollPane, BorderLayout.CENTER);
    }

    private MetadataTreeModel createTreeModel(ApplicationModel applicationModel) {
        try {
            XMLTreeBuilder builder = new XMLTreeBuilder(
                ResourcePaths.databaseMetadataFile(),
                applicationModel.getContext().getUser().getRestrictedTables()
            );
            return new MetadataTreeModel(builder.build());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Učitavanje strukture tabela nije uspjelo.",
                "Greška",
                JOptionPane.ERROR_MESSAGE
            );
            return new MetadataTreeModel(new TreeElement.Package("Tabele baze podataka"));
        }
    }
}
