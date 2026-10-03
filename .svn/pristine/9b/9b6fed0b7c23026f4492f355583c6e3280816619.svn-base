package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import util.DataTypeMapper;

/**
 * Izvršava upite i izmjene nad bazom podataka.
 */
public class DbManipulation {

    public static List<DbManipulation> dbManip = new Vector<>();
    private DbConnection dbConnection = null;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param type naziv tipa baze ili SQL tipa podatka
     * @param address adresa servera baze podataka
     * @param port port servera baze podataka
     * @param dbName naziv baze podataka
     * @param user korisničko ime za pristup bazi
     * @param password lozinka za pristup bazi
     */
    public DbManipulation(DatabaseType type, String address, String port, String dbName, String user, String password) {
        switch (type) {
            case MsSQL_JDBC:
                dbConnection = new MsSqlJdbcConnection();
                break;
            case MsSQL_JTDS:
                dbConnection = new MsSqlJtdsConnection();
                break;
            case MySQL:
                dbConnection = new MySqlConnection();
                break;
            default:
                break;
        }

        dbConnection.getConnection(address, port, dbName, user, password);
    }

    /**
     * Vraća postojeću važeću vezu sa istim parametrima ili otvara novu.
     *
     * @param type naziv tipa baze ili SQL tipa podatka
     * @param address adresa servera baze podataka
     * @param port port servera baze podataka
     * @param dbName naziv baze podataka
     * @param user korisničko ime za pristup bazi
     * @param password lozinka za pristup bazi
     * @return objekat za rad sa uspostavljenom vezom
     */
    public static DbManipulation createConnection(
        DatabaseType type,
        String address,
        String port,
        String dbName,
        String user,
        String password
    ) {
        if (!(dbManip.size() > 0)) {
            DbManipulation dbManipulation = new DbManipulation(type, address, port, dbName, user, password);
            dbManip.add(dbManipulation);

            return dbManipulation;
        } else {
            for (DbManipulation dbManipulation : dbManip) {
                if (dbManipulation.dataExists(type, address, port, dbName, user, password)) {
                    if (dbManipulation.isConnectionValid(5)) {
                        return dbManipulation;
                    } else {
                        dbManipulation.closeConnection();

                        DbManipulation manipulation = new DbManipulation(type, address, port, dbName, user, password);
                        dbManip.add(manipulation);

                        return manipulation;
                    }
                } else {
                    DbManipulation manipulation = new DbManipulation(type, address, port, dbName, user, password);
                    dbManip.add(manipulation);

                    return manipulation;
                }
            }
        }
        return null;
    }

    /**
     * Vraća aktivnu JDBC vezu.
     *
     * @return aktivnu JDBC vezu
     */
    public Connection getConnection() {
        int index = dbManip.indexOf(this);
        if (dbManip.size() > 0 && index != -1) {
            dbConnection = dbManip.get(index).dbConnection;
        }
        if (dbConnection != null) {
            return dbConnection.getConnection();
        }
        return null;
    }

    /**
     * Izvršava proceduru i čita tražene kolone svih rezultujućih redova.
     *
     * @param procedure naziv uskladištene procedure
     * @param resultColumns nazivi kolona koje se čitaju iz rezultata
     * @return redovi rezultata u redoslijedu traženih kolona
     */
    public List<List<Object>> executeQuery(String procedure, List<String> resultColumns) {
        return executeQuery(procedure, Collections.emptyList(), resultColumns);
    }

    /**
     * Izvršava proceduru i čita tražene kolone svih rezultujućih redova.
     *
     * @param procedure naziv uskladištene procedure
     * @param parameters parametri koji se prosljeđuju proceduri
     * @param resultColumns nazivi kolona koje se čitaju iz rezultata
     * @return redovi rezultata u redoslijedu traženih kolona
     */
    public List<List<Object>> executeQuery(
        String procedure,
        List<DatabaseParameter> parameters,
        List<String> resultColumns
    ) {
        validateProcedure(procedure);
        if (parameters == null) throw new IllegalArgumentException("Database parameters are required.");
        if (resultColumns == null || resultColumns.isEmpty()) {
            throw new IllegalArgumentException("At least one result column is required.");
        }
        List<List<Object>> rows = new ArrayList<>();

        try (
            CallableStatement statement = requireConnection().prepareCall(createCall(procedure, parameters.size()))
        ) {
            bindParameters(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>();
                    for (String column : resultColumns) {
                        row.add(resultSet.getObject(column));
                    }
                    rows.add(row);
                }
            }
            return rows;
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException("Stored procedure failed: " + procedure, e);
        }
    }

    /**
     * Izvršava proceduru i vraća njen prvi rezultujući red.
     *
     * @param procedure naziv uskladištene procedure
     * @param parameters parametri koji se prosljeđuju proceduri
     * @param resultColumns nazivi kolona koje se čitaju iz rezultata
     * @return prvi red rezultata ili {@code null} ako rezultat nema redova
     */
    public List<Object> executeSingleRow(
        String procedure,
        List<DatabaseParameter> parameters,
        List<String> resultColumns
    ) {
        List<List<Object>> rows = executeQuery(procedure, parameters, resultColumns);
        return rows.isEmpty() ? null : rows.get(0);
    }

    /**
     * Izvršava proceduru i vraća prvu vrijednost prvog reda.
     *
     * @param procedure naziv uskladištene procedure
     * @param parameters parametri koji se prosljeđuju proceduri
     * @param resultColumn vrijednost koja se prosljeđuje metodi
     * @return prva vrijednost rezultata ili {@code null} ako rezultat nema vrijednosti
     */
    public Object executeSingleValue(
        String procedure,
        List<DatabaseParameter> parameters,
        String resultColumn
    ) {
        List<Object> row = executeSingleRow(procedure, parameters, Collections.singletonList(resultColumn));
        return row == null ? null : row.get(0);
    }

    /**
     * Izvršava proceduru koja mijenja podatke u bazi.
     *
     * @param procedure naziv uskladištene procedure
     * @param parameters parametri koji se prosljeđuju proceduri
     * @return {@code true} nakon uspješnog izvršavanja procedure
     */
    public boolean executeUpdate(String procedure, List<DatabaseParameter> parameters) {
        validateProcedure(procedure);
        if (parameters == null) throw new IllegalArgumentException("Database parameters are required.");

        try (
            CallableStatement statement = requireConnection().prepareCall(createCall(procedure, parameters.size()))
        ) {
            bindParameters(statement, parameters);
            statement.execute();
            return true;
        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException("Stored procedure failed: " + procedure, e);
        }
    }

    private Connection requireConnection() throws SQLException {
        Connection connection = getConnection();
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not available.");
        }
        return connection;
    }

    private void validateProcedure(String procedure) {
        if (procedure == null || procedure.trim().isEmpty()) {
            throw new IllegalArgumentException("Stored procedure name is required.");
        }
    }

    private String createCall(String procedure, int parameterCount) {
        if (parameterCount == 0 || procedure.contains("(")) return "{ call " + procedure + " }";
        String placeholders = java.util.stream.IntStream
            .range(0, parameterCount)
            .mapToObj(index -> "?")
            .collect(Collectors.joining(","));
        return "{ call " + procedure + "(" + placeholders + ") }";
    }

    private void bindParameters(CallableStatement statement, List<DatabaseParameter> parameters)
        throws SQLException {
        DataTypeMapper mapper = new DataTypeMapper();
        for (int index = 0; index < parameters.size(); index++) {
            DatabaseParameter parameter = parameters.get(index);
            Object value = parameter.getValue();
            mapper.mapDataToType(
                parameter.getType(),
                value == null ? null : value.toString(),
                statement,
                index + 1
            );
        }
    }

    private boolean dataExists(
        DatabaseType type,
        String address,
        String port,
        String dbName,
        String user,
        String password
    ) {
        if (
            dbConnection.getType().equals(type) &&
            dbConnection.getAddress() == address &&
            dbConnection.getPort() == port &&
            dbConnection.getUser() == user &&
            dbConnection.getPassword() == password
        ) {
            return true;
        }
        return false;
    }

    private boolean isConnectionValid(int timeout) {
        int index = dbManip.indexOf(this);

        if (dbManip.size() > 0 && index != -1) {
            dbConnection = dbManip.get(index).dbConnection;
        } else {
            return false;
        }

        if (dbConnection != null) {
            return dbConnection.isConnectionValid(timeout);
        }
        return false;
    }

    /**
     * Zatvara vezu sa bazom podataka.
     */
    public void closeConnection() {
        int index = dbManip.indexOf(this);
        if (dbManip.size() > 0 && index != -1) {
            dbConnection = dbManip.get(index).dbConnection;
        }
        if (dbConnection != null) {
            dbConnection.closeConnection();

            if (index != -1) {
                dbManip.remove(index);
            }
        }
    }

    /**
     * Vraća naziv baze podataka.
     *
     * @return naziv baze podataka
     */
    public String getDbName() {
        return dbConnection.getDbName();
    }
}
