package command;

import java.util.HashMap;
import java.util.Map;
import model.ApplicationModel;

/**
 * Izvršava komandu za izmjenu izabranog reda.
 */
public class UpdateCommand extends Command {

    private HashMap<String, String> updateData;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @param updateData izmijenjene vrijednosti reda, indeksirane nazivom kolone
     */
    public UpdateCommand(ApplicationModel applicationModel, Map<String, String> updateData) {
        super(applicationModel);
        this.updateData = new HashMap<String, String>(updateData);
    }

    /**
     * Izvršava komandu.
     *
     * @return {@code true} ako je komanda uspješno izvršena, inače {@code false}
     */
    @Override
    public boolean execute() {
        boolean updated = this.applicationModel.getApplicationState().editRow(updateData);
        if (updated) this.applicationModel.notifySubscribers();
        return updated;
    }
}
