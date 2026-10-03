package command;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import model.ApplicationModel;

/**
 * Izvršava komandu za brisanje izabranog reda.
 */
public class DeleteCommand extends Command {

    private final HashMap<String, Object> primaryKeys;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @param primaryKeys nazivi i vrijednosti primarnih ključeva reda
     */
    public DeleteCommand(ApplicationModel applicationModel, Map<String, Object> primaryKeys) {
        super(applicationModel);
        this.primaryKeys = new LinkedHashMap<String, Object>(primaryKeys);
    }

    /**
     * Izvršava komandu.
     *
     * @return {@code true} ako je komanda uspješno izvršena, inače {@code false}
     */
    @Override
    public boolean execute() {
        if (primaryKeys.isEmpty()) return false;

        boolean deleted = this.applicationModel.getApplicationState().deleteRow(primaryKeys);
        if (!deleted) return false;

        this.applicationModel.getApplicationState().getAllData();
        this.applicationModel.notifySubscribers();
        return true;
    }
}
