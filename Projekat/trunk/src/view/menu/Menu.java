package view.menu;

/**
 * Definiše promjene menija za stanja aplikacije.
 */
public interface Menu {
    /**
     * Prilagođava kontrole stanju bez aktivne tabele.
     */
    public abstract void idleState();
    /**
     * Prilagođava kontrole stanju sa aktivnom tabelom.
     */
    public abstract void activeState();
    /**
     * Prilagođava kontrole unosu novog reda.
     */
    public abstract void createState();
    /**
     * Prilagođava kontrole izmjeni izabranog reda.
     */
    public abstract void editState();
}
