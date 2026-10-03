package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Uspostavlja vezu sa SQL Server bazom putem jTDS drajvera.
 */
public class MsSqlJtdsConnection extends DbConnection {

    /**
     * Vraća aktivnu JDBC vezu.
     *
     * @param adress adresa servera baze podataka
     * @param port port servera baze podataka
     * @param dbName naziv baze podataka
     * @param user korisničko ime za pristup bazi
     * @param password lozinka za pristup bazi
     * @return aktivnu JDBC vezu
     */
    public Connection getConnection(String adress, String port, String dbName, String user, String password) {
        this.type = DatabaseType.MsSQL_JTDS;
        this.driver = "net.sourceforge.jtds.jdbc.Driver";
        this.address = adress.trim();
        this.port = port;
        this.dbName = dbName.trim();

        if (this.port != null) {
            this.fullAddress = this.address + ":" + port.trim();
        } else {
            this.fullAddress = this.address;
        }

        this.url = "jdbc:jtds:sqlserver://" + this.fullAddress + "/";

        if (!isConnectionOpen()) {
            this.user = user.trim();
            this.password = password.trim();
            this.url += dbName.trim();
            try {
                Class.forName(this.driver);

                Properties properties = new Properties();
                properties.put("user", this.user);
                properties.put("password", this.password);
                properties.put("charset", "UTF-8");

                conn = DriverManager.getConnection(this.url, properties);

                return conn;
            } catch (ClassNotFoundException | SQLException e) {
                throw new DatabaseException("Could not connect using the jTDS SQL Server driver.", e);
            }
        }

        return conn;
    }
}
