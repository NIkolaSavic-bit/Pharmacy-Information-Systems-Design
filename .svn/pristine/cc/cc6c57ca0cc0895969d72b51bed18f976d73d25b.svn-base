package command;

import model.ApplicationModel;

/**
 * Definiše osnovu za komande nad modelom aplikacije.
 */
public abstract class Command {

    protected ApplicationModel applicationModel;

    /**
     * Izvršava komandu.
     *
     * @return {@code true} ako je komanda uspješno izvršena, inače {@code false}
     */
    public abstract boolean execute();

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param applicationModel centralni model aplikacije
     */
    public Command(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

}
