package view.menu;

import javax.swing.JMenuBar;
import model.ApplicationModel;
import observer.Subject;
import observer.Subscriber;
import state.ActiveState;
import state.CreateState;
import state.EditState;
import view.component.Form;
import view.component.Table;

/**
 * Upravlja glavnom trakom menija i njenim stanjem.
 */
public class MenuBar extends JMenuBar implements Subscriber {

    private FileMenu fileMenu = null;
    private EditMenu editMenu = null;
    private HelpMenu helpMenu = null;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param table komponenta tabele povezana sa prikazom
     * @param applicationModel centralni model aplikacije
     * @param form forma povezana sa komponentom
     */
    public MenuBar(Table table, ApplicationModel applicationModel, Form form) {
        fileMenu = new FileMenu(applicationModel);
        add(fileMenu);

        editMenu = new EditMenu(table, applicationModel, form);
        add(editMenu);

        helpMenu = new HelpMenu();
        add(helpMenu);

        applyState(applicationModel);
        setVisible(true);
    }

    /**
     * Prilagođava kontrole stanju bez aktivne tabele.
     */
    public void idleState() {
        fileMenu.idleState();
        editMenu.idleState();
        helpMenu.idleState();
    }

    /**
     * Prilagođava kontrole stanju sa aktivnom tabelom.
     */
    public void activeState() {
        fileMenu.activeState();
        editMenu.activeState();
        helpMenu.activeState();
    }

    /**
     * Prilagođava kontrole unosu novog reda.
     */
    public void createState() {
        fileMenu.createState();
        editMenu.createState();
        helpMenu.createState();
    }

    /**
     * Prilagođava kontrole izmjeni izabranog reda.
     */
    public void editState() {
        fileMenu.editState();
        editMenu.editState();
        helpMenu.editState();
    }

    /**
     * Obrađuje obavještenje o promjeni.
     *
     * @param subject objekat koji je poslao obavještenje
     */
    @Override
    public void update(Subject subject) {
        applyState((ApplicationModel) subject);
    }

    private void applyState(ApplicationModel applicationModel) {
        if (applicationModel.getApplicationState() instanceof EditState) {
            editState();
        } else if (applicationModel.getApplicationState() instanceof CreateState) {
            createState();
        } else if (applicationModel.getApplicationState() instanceof ActiveState) {
            activeState();
        } else {
            idleState();
        }
    }
}
