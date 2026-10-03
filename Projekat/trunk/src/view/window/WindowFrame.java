package view.window;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import view.config.AppConfig;

/**
 * Predstavlja osnovni prozor aplikacije.
 */
public class WindowFrame extends JFrame {

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     */
    public WindowFrame() {
        AppConfig config = AppConfig.getInstance();
        setTitle(config.getName());
        setSize(config.getWidth(), config.getHeight());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/resources/icons/icon.png");
        setIconImage(icon.getImage());
        setVisible(true);
    }
}
