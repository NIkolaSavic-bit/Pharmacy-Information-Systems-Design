package model;

import java.util.List;
import java.util.Vector;
import observer.Subject;
import observer.Subscriber;
import state.ApplicationState;
import state.IdleState;

/**
 * Čuva stanje aplikacije i obavještava pretplatnike o promjenama.
 */
public class ApplicationModel implements Subject {

    private ApplicationState applicationState;
    private ApplicationContext context;
    private List<Subscriber> subscribers;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     */
    public ApplicationModel() {
        setContext(new ApplicationContext());
        setApplicationState(new IdleState(this));
        this.subscribers = new Vector<Subscriber>();
    }

    /**
     * Obavještava pretplatnike o promjeni.
     */
    @Override
    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(this);
        }
    }

    /**
     * Vraća kontekst sa aktivnim modelima.
     *
     * @return kontekst sa aktivnim modelima
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     * Postavlja kontekst sa aktivnim modelima.
     *
     * @param context novi kontekst aplikacije
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Vraća trenutno stanje aplikacije.
     *
     * @return trenutno stanje aplikacije
     */
    public ApplicationState getApplicationState() {
        return applicationState;
    }

    private void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    /**
     * Postavlja novo stanje i obavještava pretplatnike.
     *
     * @param applicationState novo stanje aplikacije
     */
    public void changeState(ApplicationState applicationState) {
        setApplicationState(applicationState);
        notifySubscribers();
    }

    /**
     * Registruje komponentu koja prati promjene modela.
     *
     * @param sub pretplatnik koji se registruje
     */
    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }
}
