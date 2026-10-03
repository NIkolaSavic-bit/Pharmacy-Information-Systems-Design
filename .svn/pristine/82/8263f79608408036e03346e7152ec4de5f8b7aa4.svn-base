package command;

import java.util.HashMap;
import java.util.Map;
import model.ApplicationModel;

/**
 * Izvršava komandu za dodavanje novog reda.
 */
public class CreateCommand extends Command {

    private HashMap<String, String> newData;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     * @param newData vrijednosti novog reda, indeksirane nazivom kolone
     */
    public CreateCommand(ApplicationModel applicationModel, Map<String, String> newData) {
        super(applicationModel);
        this.newData = new HashMap<String, String>(newData);
    }

    /**
     * Izvršava komandu.
     *
     * @return {@code true} ako je komanda uspješno izvršena, inače {@code false}
     */
    @Override
    public boolean execute() {
        boolean created = this.applicationModel.getApplicationState().addRow(newData);
        return created;
    }
}
