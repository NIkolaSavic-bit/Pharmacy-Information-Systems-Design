package state;

import java.util.HashMap;
import model.ApplicationModel;

/**
 * Predstavlja stanje sa aktivno izabranom tabelom.
 */
public class ActiveState extends ApplicationState {

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     */
    public ActiveState(ApplicationModel applicationModel) {
        super(applicationModel);
    }

    /**
     * Vraća naziv stanja namijenjen prikazu u interfejsu.
     *
     * @return naziv trenutnog stanja
     */
    @Override
    public String getDisplayName() {
        return "Aktivno";
    }

    /**
     * Briše red određen vrijednostima primarnog ključa.
     *
     * @param columnPKs nazivi i vrijednosti primarnih ključeva reda
     * @return {@code true} ako je red obrisan, inače {@code false}
     */
    @Override
    public boolean deleteRow(HashMap<String, Object> columnPKs) {
        return applicationModel.getContext().getDataModel().deleteRow(columnPKs);
    }
}
