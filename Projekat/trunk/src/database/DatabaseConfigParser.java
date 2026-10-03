package database;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Učitava podešavanja baze podataka iz konfiguracionog fajla.
 */
public class DatabaseConfigParser {

    private static final String NAMESPACE = "http://www.example.org/xml-db";

    /**
     * Čita parametre veze sa bazom iz XML konfiguracije.
     *
     * @param filePath putanja do konfiguracionog XML fajla
     * @return učitana konfiguracija veze sa bazom
     */
    public static DatabaseConfig parseConfig(String filePath) {
        try {
            File xmlFile = new File(filePath);
            Document doc = loadXmlDocument(xmlFile);

            NodeList connections = doc.getElementsByTagNameNS(NAMESPACE, "connection");

            if (connections.getLength() > 0) {
                Element connElement = (Element) connections.item(0);

                String address = connElement.getAttribute("address");
                String port = connElement.getAttribute("port");
                String dbName = connElement.getAttribute("database");
                String user = connElement.getAttribute("username");
                String password = connElement.getAttribute("password");

                return new DatabaseConfig(address, port, dbName, user, password);
            } else {
                throw new RuntimeException("No <connection> element found in the XML.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parsira konfiguracioni XML fajl u DOM dokument.
     *
     * @param file XML fajl koji se učitava
     * @return parsirani DOM dokument
     * @throws ParserConfigurationException ako XML parser nije moguće kreirati
     * @throws SAXException ako sadržaj fajla nije ispravan XML
     * @throws IOException ako fajl nije moguće pročitati
     */
    private static Document loadXmlDocument(File file)
        throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file.getCanonicalFile());
    }
}
