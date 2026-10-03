package model;

import database.DBResultSet;
import database.DatabaseException;
import database.DatabaseParameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import metadata.Strategy;

/**
 * Upravlja podacima i prijavljivanjem korisnika.
 */
public class UserModel extends GeneralTableModel {

    private String userId = "";
    private String role = "";

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @param strategy strategija za učitavanje metapodataka
     * @param dBResultSet objekat koji obezbjeđuje pristup bazi
     */
    public UserModel(String tableName, Strategy strategy, DBResultSet dBResultSet) {
        super(tableName, strategy, dBResultSet);
    }

    /**
     * Vraća identifikator prijavljenog korisnika.
     *
     * @return identifikator prijavljenog korisnika
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Postavlja identifikator prijavljenog korisnika.
     *
     * @param userId identifikator prijavljenog korisnika
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Vraća ulogu prijavljenog korisnika.
     *
     * @return ulogu prijavljenog korisnika
     */
    public String getRole() {
        return role;
    }

    /**
     * Postavlja ulogu prijavljenog korisnika.
     *
     * @param role uloga prijavljenog korisnika
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Provjerava korisničke podatke i učitava identifikator i ulogu korisnika.
     *
     * @param username korisničko ime
     * @param password lozinka za pristup bazi
     * @return {@code true} ako su podaci za prijavu ispravni
     */
    public boolean logIn(String username, String password) {
        this.userId = "";
        this.role = "";
        this.data.clear();

        try {
            Object resolvedUserId = getDatabase().executeSingleValue(
                crudOps.get("retrieveUserId"),
                Arrays.asList(
                    new DatabaseParameter("varchar", username),
                    new DatabaseParameter("varchar", password)
                ),
                "User ID"
            );
            if (resolvedUserId != null) this.setUserId(resolvedUserId.toString());

            if (!this.userId.isEmpty()) {
                Object resolvedRole = getDatabase().executeSingleValue(
                    crudOps.get("retrieveUserRole"),
                    Collections.singletonList(new DatabaseParameter("varchar", this.userId)),
                    "Oznaka Role"
                );
                if (resolvedRole != null) this.setRole(resolvedRole.toString());
            }
        } catch (DatabaseException e) {
            throw e;
        }

        if (!this.userId.isEmpty() && !this.role.isEmpty()) {
            this.data.add(getUserById((Object) this.userId));
            return true;
        }
        return false;
    }

    /**
     * Učitava jedan korisnički nalog prema identifikatoru.
     *
     * @param rowId identifikator traženog reda
     * @return vrijednosti korisničkog naloga ili {@code null} ako nalog nije pronađen
     */
    public List<Object> getUserById(Object rowId) {
        String Id = (String) rowId;
        this.data.clear();
        try {
            List<String> columnNames = new ArrayList<>();
            for (metadata.TableColumnModel column : columns) {
                columnNames.add(column.getColumName());
            }

            return getDatabase().executeSingleRow(
                crudOps.get("retrieveUserById"),
                Collections.singletonList(new DatabaseParameter("varchar", Id)),
                columnNames
            );
        } catch (DatabaseException e) {
            throw e;
        }
    }

    /**
     * Učitava kodove tabela kojima korisnička uloga nema pristup.
     *
     * @return skup kodova zabranjenih tabela
     */
    public Set<String> getRestrictedTables() {
        Set<String> restrictedTables = new HashSet<>();
        List<List<Object>> rows = getDatabase().executeQuery(
            crudOps.get("retrieveTableRestrictions"),
            Collections.singletonList(new DatabaseParameter("varchar", role)),
            Collections.singletonList("Tabela")
        );

        for (List<Object> row : rows) {
            if (!row.isEmpty() && row.get(0) != null) {
                restrictedTables.add(row.get(0).toString().trim());
            }
        }
        return restrictedTables;
    }

    /**
     * Vraća tekstualni prikaz objekta.
     *
     * @return tekstualni prikaz objekta
     */
    @Override
    public String toString() {
        return "UserID: " + userId + " role: " + role;
    }
}
