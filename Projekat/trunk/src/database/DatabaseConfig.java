package database;

/**
 * Čuva parametre za povezivanje sa bazom podataka.
 */
public class DatabaseConfig {

    private String address;
    private String port;
    private String dbName;
    private String user;
    private String password;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param address adresa servera baze podataka
     * @param port port servera baze podataka
     * @param dbName naziv baze podataka
     * @param user korisničko ime za pristup bazi
     * @param password lozinka za pristup bazi
     */
    public DatabaseConfig(String address, String port, String dbName, String user, String password) {
        this.address = address;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
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
     * Vraća port servera baze.
     *
     * @return port servera baze
     */
    public String getPort() {
        return port;
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
}
