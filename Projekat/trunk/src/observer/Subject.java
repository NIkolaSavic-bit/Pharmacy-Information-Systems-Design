package observer;

/**
 * Definiše objekat koji obavještava svoje pretplatnike.
 */
public interface Subject {
    /**
     * Obavještava sve registrovane pretplatnike da je stanje promijenjeno.
     */
    void notifySubscribers();
}
