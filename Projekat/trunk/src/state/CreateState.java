package state;

import java.util.HashMap;
import model.ApplicationModel;

/**
 * Predstavlja stanje kreiranja novog reda.
 */
public class CreateState extends ApplicationState {

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     */
    public CreateState(ApplicationModel applicationModel) {
        super(applicationModel);
    }

    /**
     * Vraća naziv stanja namijenjen prikazu u interfejsu.
     *
     * @return naziv trenutnog stanja
     */
    @Override
    public String getDisplayName() {
        return "Kreiranje";
    }

    /**
     * Dodaje novi red u aktivnu tabelu.
     *
     * @param params vrijednosti reda, indeksirane nazivom kolone
     * @return {@code true} ako je red dodat, inače {@code false}
     */
    @Override
    public boolean addRow(HashMap<String, String> params) {
        return applicationModel.getContext().getDataModel().addRow(params);
    }
}
