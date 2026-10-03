package command;

import model.ApplicationModel;
import state.ActiveState;
import state.IdleState;

/**
 * Izvršava komandu za učitavanje podataka tabele.
 */
public class ReadCommand extends Command {

    private String tableName;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @param tableName kod tabele iz XML metapodataka
     */
    public ReadCommand(ApplicationModel applicationModel, String tableName) {
        super(applicationModel);
        this.tableName = tableName;
    }

    /**
     * Izvršava komandu.
     *
     * @return {@code true} ako je komanda uspješno izvršena, inače {@code false}
     */
    @Override
    public boolean execute() {
        this.applicationModel.changeState(new IdleState(this.applicationModel));
        try {
            this.applicationModel.getContext().setDataModel(tableName);
            boolean loaded = this.applicationModel.getContext().getDataModel().getAllData();
            if (loaded) this.applicationModel.changeState(new ActiveState(this.applicationModel));
            return loaded;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
