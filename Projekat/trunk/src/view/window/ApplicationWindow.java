package view.window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import javax.swing.JLayeredPane;
import model.ApplicationModel;
import view.component.Form;
import view.component.StatusBar;
import view.component.Table;
import view.component.Toolbar;
import view.component.Tree;
import view.menu.MenuBar;

/**
 * Sastavlja i prikazuje glavni prozor aplikacije.
 */
public class ApplicationWindow {

    private WindowFrame windowFrame = null;
    private MenuBar menuBar = null;
    private Toolbar toolBar = null;
    private Tree tree = null;
    private StatusBar statusBar = null;
    private Table table = null;
    private Form form = null;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @throws HeadlessException ako operacija ne može biti izvršena
     */
    public ApplicationWindow(ApplicationModel applicationModel) throws HeadlessException {
        windowFrame = new WindowFrame();

        JLayeredPane rightPanelContainer = new JLayeredPane() {
            @Override
            public void doLayout() {
                super.doLayout();
                for (Component comp : getComponents()) {
                    comp.setBounds(0, 0, getWidth(), getHeight());
                }
            }
        };

        table = new Table(applicationModel);
        table.showTable(applicationModel);

        statusBar = new StatusBar(applicationModel, table);
        table.setSelectionChangeListener(statusBar::refresh);

        form = new Form(applicationModel);

        tree = new Tree(applicationModel, form);

        toolBar = new Toolbar(table, applicationModel, form);

        ((Component) table).setBounds(0, 0, 1100, 600);
        ((Component) form).setBounds(0, 0, 1100, 600);

        rightPanelContainer.add((Component) table, JLayeredPane.DEFAULT_LAYER);
        rightPanelContainer.add((Component) form, JLayeredPane.PALETTE_LAYER);

        menuBar = new MenuBar(table, applicationModel, form);

        windowFrame.setJMenuBar((MenuBar) menuBar);

        windowFrame.add((Toolbar) toolBar, BorderLayout.NORTH);

        windowFrame.add(tree, BorderLayout.WEST);

        windowFrame.add((StatusBar) statusBar, BorderLayout.SOUTH);

        windowFrame.add(rightPanelContainer, BorderLayout.CENTER);

        windowFrame.setVisible(true);

        applicationModel.addSubscriber(menuBar);
        applicationModel.addSubscriber(toolBar);
        applicationModel.addSubscriber(table);
        applicationModel.addSubscriber(statusBar);
    }
}
