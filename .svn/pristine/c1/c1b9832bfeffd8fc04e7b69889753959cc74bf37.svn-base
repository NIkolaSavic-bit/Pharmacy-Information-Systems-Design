package command;

import command.Command;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pokreće komandu kao odgovor na korisničku akciju.
 */
public class CommandActionListener implements ActionListener {

    private Command command;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param command komanda koja se izvršava nakon događaja
     */
    public CommandActionListener(Command command) {
        this.command = command;
    }

    /**
     * Obrađuje korisničku akciju.
     *
     * @param e događaj korisničke akcije
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        command.execute();
    }
}
