package state;

import java.util.HashMap;
import model.ApplicationModel;

/**
 * Predstavlja stanje izmjene postojećeg reda.
 */
public class EditState extends ApplicationState {

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     */
    public EditState(ApplicationModel applicationModel) {
        super(applicationModel);
    }

    /**
     * Vraća naziv stanja namijenjen prikazu u interfejsu.
     *
     * @return naziv trenutnog stanja
     */
    @Override
    public String getDisplayName() {
        return "Izmjena";
    }

    /**
     * Mijenja postojeći red aktivne tabele.
     *
     * @param params vrijednosti reda, indeksirane nazivom kolone
     * @return {@code true} ako je red izmijenjen, inače {@code false}
     */
    @Override
    public boolean editRow(HashMap<String, String> params) {
        return applicationModel.getContext().getDataModel().editRow(params);
    }
}
