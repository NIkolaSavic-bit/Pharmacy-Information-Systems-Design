package model;

import database.DBResultSet;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import metadata.Strategy;
import metadata.XMLParserStrategy;
import org.xml.sax.SAXException;
import util.ResourcePaths;

/**
 * Čuva trenutno aktivne modele aplikacije.
 */
public class ApplicationContext {

    private final Strategy metadataStrategy;
    private final DBResultSet databaseAccess;
    private DataModel dataModel;
    private UserModel user;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     */
    public ApplicationContext() {
        try {
            this.metadataStrategy = new XMLParserStrategy(ResourcePaths.databaseMetadataFile());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException("Could not initialize application metadata.", e);
        }
        this.databaseAccess = new DBResultSet();
        this.dataModel = new DataModel("", metadataStrategy, databaseAccess);
        this.user = new UserModel("Korisnički nalog", metadataStrategy, databaseAccess);
    }

    /**
     * Vraća model trenutno izabrane tabele.
     *
     * @return model trenutno izabrane tabele
     */
    public DataModel getDataModel() {
        return dataModel;
    }

    /**
     * Postavlja model trenutno izabrane tabele.
     *
     * @param tableName kod tabele iz XML metapodataka
     */
    public void setDataModel(String tableName) {
        if (tableName == null) throw new IllegalArgumentException("Table name must not be null.");
        this.dataModel = new DataModel(tableName, metadataStrategy, databaseAccess);
    }

    /**
     * Vraća korisničko ime za vezu sa bazom.
     *
     * @return korisničko ime za vezu sa bazom
     */
    public UserModel getUser() {
        return user;
    }
}
