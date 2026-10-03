package state;

import java.util.HashMap;
import model.ApplicationModel;

/**
 * Definiše zajedničko ponašanje stanja aplikacije.
 */
public abstract class ApplicationState {

    protected ApplicationModel applicationModel;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     */
    public ApplicationState(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    /**
     * Vraća naziv stanja namijenjen prikazu u interfejsu.
     *
     * @return naziv trenutnog stanja
     */
    public abstract String getDisplayName();

    /**
     * Učitava sve redove aktivne tabele iz baze.
     *
     * @return {@code true} ako su podaci učitani, inače {@code false}
     */
    public boolean getAllData() {
        return applicationModel.getContext().getDataModel().getAllData();
    }

    /**
     * Briše red određen vrijednostima primarnog ključa.
     *
     * @param columnPKs nazivi i vrijednosti primarnih ključeva reda
     * @return {@code true} ako je red obrisan, inače {@code false}
     */
    public boolean deleteRow(HashMap<String, Object> columnPKs) {
        return false;
    }

    /**
     * Dodaje novi red u aktivnu tabelu.
     *
     * @param params vrijednosti reda, indeksirane nazivom kolone
     * @return {@code true} ako je red dodat, inače {@code false}
     */
    public boolean addRow(HashMap<String, String> params) {
        return false;
    }

    /**
     * Mijenja postojeći red aktivne tabele.
     *
     * @param params vrijednosti reda, indeksirane nazivom kolone
     * @return {@code true} ako je red izmijenjen, inače {@code false}
     */
    public boolean editRow(HashMap<String, String> params) {
        return false;
    }
}
