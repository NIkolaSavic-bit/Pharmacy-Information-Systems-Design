package view.config;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Učitava i čuva osnovna podešavanja aplikacije.
 */
public class AppConfig {

    private static AppConfig instance;

    private String name;
    private int width;
    private int height;

    private static final String XML_PATH = "/config/config.xml";

    private AppConfig() throws Exception {
        loadConfig(XML_PATH);
    }

    /**
     * Vraća jedinu instancu konfiguracije aplikacije.
     *
     * @return jedinu instancu konfiguracije aplikacije
     */
    public static AppConfig getInstance() {
        try {
            if (instance == null) {
                instance = new AppConfig();
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadConfig(String xmlFileName) throws Exception {
        InputStream xmlStream = AppConfig.class.getResourceAsStream(xmlFileName);

        if (xmlStream == null) {
            throw new RuntimeException("Configuration file not found: " + xmlFileName);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlStream);
        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        this.name = getTagValue(root, "name");
        this.width = Integer.parseInt(getTagValue(root, "width"));
        this.height = Integer.parseInt(getTagValue(root, "height"));
    }

    private String getTagValue(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }

    /**
     * Vraća naziv elementa.
     *
     * @return naziv elementa
     */
    public String getName() {
        return name;
    }

    /**
     * Vraća širinu prozora.
     *
     * @return širinu prozora
     */
    public int getWidth() {
        return width;
    }

    /**
     * Vraća visinu prozora.
     *
     * @return visinu prozora
     */
    public int getHeight() {
        return height;
    }

    /**
     * Vraća tekstualni prikaz objekta.
     *
     * @return tekstualni prikaz objekta
     */
    @Override
    public String toString() {
        return "AppConfig{" + "name='" + name + '\'' + ", width=" + width + ", height=" + height + '}';
    }
}
