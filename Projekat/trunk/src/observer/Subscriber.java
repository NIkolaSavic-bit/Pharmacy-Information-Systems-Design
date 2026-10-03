package observer;

/**
 * Definiše objekat koji prima obavještenja o promjenama.
 */
public interface Subscriber {

    /**
     * Ažurira pretplatnika nakon promjene posmatranog objekta.
    *
     * @param applicationModel objekat čija je promjena izazvala obavještenje
     */
    void update(Subject applicationModel);
}
