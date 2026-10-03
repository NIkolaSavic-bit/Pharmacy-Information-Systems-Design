package database;

import util.ResourcePaths;

/**
 * Objedinjuje pristup bazi podataka za rezultate upita.
 */
public class DBResultSet {

    private DbManipulation dbManipulation = null;
    DatabaseConfig dbConfig;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     */
    public DBResultSet() {
        dbConfig = DatabaseConfigParser.parseConfig(ResourcePaths.DATABASE_METADATA);

        this.dbManipulation = DbManipulation.createConnection(
            DatabaseType.MsSQL_JDBC,
            dbConfig.getAddress(),
            dbConfig.getPort(),
            dbConfig.getDbName(),
            dbConfig.getUser(),
            dbConfig.getPassword()
        );
    }

    /**
     * Vraća objekat za izvršavanje operacija nad bazom.
     *
     * @return objekat za izvršavanje operacija nad bazom
     */
    public DbManipulation getDbManipulation() {
        return dbManipulation;
    }

}
