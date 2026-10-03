package main;

import model.ApplicationModel;
import view.ViewInitializer;

/**
 * Sadrži ulaznu tačku aplikacije.
 */
public class MainClass {

    /**
     * Pokreće aplikaciju.
     *
     * @param args argumenti komandne linije; aplikacija ih trenutno ne koristi
     */
    public static void main(String[] args) {
        ApplicationModel applicationModel = new ApplicationModel();
        new ViewInitializer(applicationModel);
    }
}
