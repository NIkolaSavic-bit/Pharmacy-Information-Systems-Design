package metadata;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Gradi stablo metapodataka iz XML dokumenta.
 */
public class XMLTreeBuilder {

    private final File metadataFile;
    private final Set<String> restrictedTables;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param metadataFile XML fajl sa metapodacima baze
     * @param restrictedTables kodovi tabela koje ne treba prikazati korisniku
     */
    public XMLTreeBuilder(File metadataFile, Set<String> restrictedTables) {
        this.metadataFile = metadataFile;
        this.restrictedTables = restrictedTables;
    }

    /**
     * Gradi stablo dozvoljenih tabela i paketa iz XML metapodataka.
     *
     * @return korijenski paket izgrađenog stabla
     * @throws ParserConfigurationException ako operacija ne može biti izvršena
     * @throws SAXException ako operacija ne može biti izvršena
     * @throws IOException ako operacija ne može biti izvršena
     */
    public TreeElement.Package build()
        throws ParserConfigurationException, SAXException, IOException {
        Document document = loadXmlDocument(metadataFile);
        TreeElement.Package root = new TreeElement.Package("Tabele baze podataka");
        addElements(document.getDocumentElement(), root);
        return root;
    }

    /**
     * Parsira XML fajl sa metapodacima u DOM dokument.
     *
     * @param file XML fajl koji se učitava
     * @return parsirani DOM dokument
     * @throws ParserConfigurationException ako XML parser nije moguće kreirati
     * @throws SAXException ako sadržaj fajla nije ispravan XML
     * @throws IOException ako fajl nije moguće pročitati
     */
    private Document loadXmlDocument(File file)
        throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file.getCanonicalFile());
    }

    private void addElements(Element parentElement, TreeElement.Package parentPackage) {
        NodeList childNodes = parentElement.getChildNodes();

        for (int index = 0; index < childNodes.getLength(); index++) {
            Node node = childNodes.item(index);
            if (!(node instanceof Element)) continue;

            Element element = (Element) node;
            if ("package".equals(element.getLocalName())) {
                TreeElement.Package childPackage = new TreeElement.Package(element.getAttribute("name"));
                addElements(element, childPackage);
                parentPackage.addElement(childPackage);
            } else if ("table".equals(element.getLocalName())) {
                addTable(element, parentPackage);
            }
        }
    }

    private void addTable(Element tableElement, TreeElement.Package parentPackage) {
        String tableCode = tableElement.getAttribute("code");
        if (restrictedTables.contains(tableCode)) return;

        TreeElement.Table table = new TreeElement.Table();
        table.code = tableCode;
        table.name = tableElement.getAttribute("name");
        parentPackage.addElement(table);
    }
}
