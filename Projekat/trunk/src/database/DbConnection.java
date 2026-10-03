package database;

import java.sql.*;

/**
 * Definiše zajedničko upravljanje vezom sa bazom podataka.
 */
public abstract class DbConnection {

    protected Connection conn = null;

    DatabaseType type = null;
    String address = null;
    String fullAddress = null;
    String dbName = null;
    String port = null;
    String driver = null;
    String user = null;
    String password = null;
    String url = null;
















    /**
     * Vraća aktivnu JDBC vezu.
     *
     * @param address adresa servera baze podataka
     * @param port port servera baze podataka
     * @param dbName naziv baze podataka
     * @param user korisničko ime za pristup bazi
     * @param password lozinka za pristup bazi
     * @return aktivnu JDBC vezu
     */
    public abstract Connection getConnection(String address, String port, String dbName, String user, String password);

    /**
     * Vraća aktivnu JDBC vezu.
     *
     * @return aktivnu JDBC vezu
     */
    public Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        return null;
    }





    /**
     * Zatvara vezu sa bazom podataka.
     */
    public void closeConnection() {
        if (conn == null) return;
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DatabaseException("Could not close database connection.", e);
        } finally {
            conn = null;
        }
    }






    /**
     * Provjerava da li JDBC veza postoji i nije zatvorena.
     *
     * @return {@code true} ako je veza otvorena
     */
    public boolean isConnectionOpen() {
        return conn != null;
    }






    /**
     * Provjerava da li je veza sa bazom i dalje važeća.
     *
     * @param timeout najduže vrijeme provjere veze u sekundama
     * @return {@code true} ako veza prođe provjeru u zadatom roku
     */
    public boolean isConnectionValid(int timeout) {
        if (conn != null) {
            try {
                return conn.isValid(timeout);
            } catch (SQLException e) {
                throw new DatabaseException("Could not validate database connection.", e);
            }
        }
        return false;
    }

    /**
     * Vraća aktivnu JDBC vezu.
     *
     * @return aktivnu JDBC vezu
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * Vraća tip podatka ili baze.
     *
     * @return tip podatka ili baze
     */
    public DatabaseType getType() {
        return type;
    }

    /**
     * Vraća adresu servera baze.
     *
     * @return adresu servera baze
     */
    public String getAddress() {
        return address;
    }

    /**
     * Vraća adresu servera sa portom.
     *
     * @return adresu servera sa portom
     */
    public String getFullAddress() {
        return fullAddress;
    }

    /**
     * Vraća naziv baze podataka.
     *
     * @return naziv baze podataka
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Vraća port servera baze.
     *
     * @return port servera baze
     */
    public String getPort() {
        return port;
    }

    /**
     * Vraća naziv JDBC drajvera.
     *
     * @return naziv JDBC drajvera
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Vraća korisničko ime za vezu sa bazom.
     *
     * @return korisničko ime za vezu sa bazom
     */
    public String getUser() {
        return user;
    }

    /**
     * Vraća lozinku za vezu sa bazom.
     *
     * @return lozinku za vezu sa bazom
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vraća JDBC URL veze.
     *
     * @return JDBC URL veze
     */
    public String getUrl() {
        return url;
    }
}
