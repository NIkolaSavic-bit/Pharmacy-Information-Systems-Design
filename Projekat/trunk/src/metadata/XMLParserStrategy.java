package metadata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import metadata.TableColumnModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Čita model podataka i CRUD operacije iz XML metapodataka.
 */
public class XMLParserStrategy implements Strategy {

    private File xmlFile;
    private Document document;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param xmlFile vrijednost koja se prosljeđuje metodi
     * @throws ParserConfigurationException ako operacija ne može biti izvršena
     * @throws SAXException ako operacija ne može biti izvršena
     * @throws IOException ako operacija ne može biti izvršena
     */
    public XMLParserStrategy(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        this.xmlFile = xmlFile;
        this.parseDocument();
    }

    /**
     * Učitava i obrađuje XML dokument.
     *
     * @throws ParserConfigurationException ako operacija ne može biti izvršena
     * @throws SAXException ako operacija ne može biti izvršena
     * @throws IOException ako operacija ne može biti izvršena
     */
    public void parseDocument() throws ParserConfigurationException, SAXException, IOException {
        this.document = loadXmlDocument(xmlFile);
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

    /**
     * Čita metapodatke kolona zadate tabele iz XML dokumenta.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @return lista metapodataka kolona; prazna lista ako tabela nije pronađena
     */
    public List<TableColumnModel> parseDataModel(String tableName) {
        Element database = document.getDocumentElement();
        NodeList databaseNodes = database.getChildNodes();

        for (int i = 0; i < databaseNodes.getLength(); i++) {
            Node node = databaseNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("package")) {
                List<TableColumnModel> result = processPackageForDataModel(node, tableName);
                if (result != null) {
                    return result;
                }
            }
        }

        System.err.println("Error: Table '" + tableName + "' not found in XML.");
        return new Vector<TableColumnModel>();
    }

    private List<TableColumnModel> processPackageForDataModel(Node packageNode, String tableName) {
        NodeList childNodes = packageNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;

            if (node.getNodeName().equals("package")) {
                List<TableColumnModel> result = processPackageForDataModel(node, tableName);
                if (result != null) {
                    return result;
                }
            } else if (node.getNodeName().equals("table")) {
                Element tableElement = (Element) node;
                String code = tableElement.getAttribute("code");
                if (code != null && code.equals(tableName)) {
                    return processDataModel(tableElement);
                }
            }
        }
        return null;
    }

    private List<TableColumnModel> processDataModel(Element tableElement) {
        List<TableColumnModel> columnList = new Vector<TableColumnModel>();
        NodeList tableChildren = tableElement.getChildNodes();

        for (int i = 0; i < tableChildren.getLength(); i++) {
            Node columnNode = tableChildren.item(i);
            if (columnNode.getNodeType() == Node.ELEMENT_NODE && columnNode.getNodeName().equals("column")) {
                Element columnElement = (Element) columnNode;
                String columnCode = columnElement.getAttribute("code");
                String columnLabel = columnElement.getAttribute("name");
                String columnIsPK = columnElement.getAttribute("primary");
                String isAutoGenerated = columnElement.getAttribute("isAutoGenerated");
                String columnType = columnElement.getAttribute("type");
                String isNullableString = columnElement.getAttribute("nullable");
                String isVisibleString = columnElement.getAttribute("visible");
                String refColumn = "";
                String refTable = "";

                NodeList columnChildren = columnNode.getChildNodes();
                for (int j = 0; j < columnChildren.getLength(); j++) {
                    Node referenceNode = columnChildren.item(j);
                    if (
                        referenceNode.getNodeType() == Node.ELEMENT_NODE &&
                        referenceNode.getNodeName().equals("references")
                    ) {
                        Element referenceElement = (Element) referenceNode;
                        refColumn = referenceElement.getAttribute("refColumn");
                        refTable = referenceElement.getAttribute("refTable");
                    }
                }

                if (columnCode != null && !columnCode.isEmpty()) {
                    TableColumnModel dataModel = new TableColumnModel(
                        columnCode,
                        columnType,
                        columnLabel,
                        Boolean.parseBoolean(columnIsPK),
                        Boolean.parseBoolean(isAutoGenerated),
                        Boolean.parseBoolean(isNullableString),
                        Boolean.parseBoolean(isVisibleString),
                        refColumn,
                        refTable
                    );
                    columnList.add(dataModel);
                } else {
                    System.err.println("Error: Column found without a valid 'code' attribute.");
                }
            }
        }
        return columnList;
    }

    /**
     * Čita nazive CRUD procedura zadate tabele iz XML dokumenta.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @return mapa operacija i naziva procedura; prazna mapa ako tabela nije pronađena
     */
    public HashMap<String, String> parseCrudOperations(String tableName) {
        Element database = document.getDocumentElement();
        NodeList databaseNodes = database.getChildNodes();

        for (int i = 0; i < databaseNodes.getLength(); i++) {
            Node node = databaseNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("package")) {
                HashMap<String, String> result = processPackageForCRUD(node, tableName);
                if (result != null) {
                    return result;
                }
            }
        }

        System.err.println("Error: Table '" + tableName + "' not found in XML for CRUD operations.");
        return new HashMap<String, String>();
    }

    private HashMap<String, String> processPackageForCRUD(Node packageNode, String tableName) {
        NodeList childNodes = packageNode.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;

            if (node.getNodeName().equals("package")) {
                HashMap<String, String> result = processPackageForCRUD(node, tableName);
                if (result != null) {
                    return result;
                }
            } else if (node.getNodeName().equals("table")) {
                Element tableElement = (Element) node;
                String code = tableElement.getAttribute("code");
                if (code != null && code.equals(tableName)) {
                    return processCRUD(tableElement);
                }
            }
        }
        return null;
    }

    private HashMap<String, String> processCRUD(Element tableElement) {
        HashMap<String, String> crudMethods = new HashMap<String, String>();
        NodeList tableChildren = tableElement.getChildNodes();

        for (int i = 0; i < tableChildren.getLength(); i++) {
            Node crudNode = tableChildren.item(i);
            if (crudNode.getNodeType() == Node.ELEMENT_NODE && crudNode.getNodeName().equals("crud")) {
                NodeList crudOperations = crudNode.getChildNodes();
                for (int j = 0; j < crudOperations.getLength(); j++) {
                    Node operationNode = crudOperations.item(j);
                    if (operationNode.getNodeType() == Node.ELEMENT_NODE) {
                        String operationName = operationNode.getNodeName();
                        String methodName = operationName;

                        if (operationNode.hasAttributes()) {
                            NamedNodeMap attributes = operationNode.getAttributes();
                            Node nameAttribute = attributes.getNamedItem("name");
                            if (nameAttribute != null) {
                                methodName = nameAttribute.getNodeValue();
                            }
                        }

                        crudMethods.put(operationName, methodName);
                    }
                }
            }
        }
        return crudMethods;
    }
}
