package model;

import database.DBResultSet;
import database.DatabaseException;
import database.DatabaseParameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import metadata.Strategy;
import metadata.TableColumnModel;

/**
 * Upravlja podacima izabrane poslovne tabele.
 */
public class DataModel extends GeneralTableModel {

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @param strategy strategija za učitavanje metapodataka
     * @param dBResultSet objekat koji obezbjeđuje pristup bazi
     */
    public DataModel(String tableName, Strategy strategy, DBResultSet dBResultSet) {
        super(tableName, strategy, dBResultSet);
        if (!tableName.isEmpty()) this.getAllData();
    }

    /**
     * Vraća tekstualni prikaz objekta.
     *
     * @return tekstualni prikaz objekta
     */
    @Override
    public String toString() {
        String returnString = "";
        for (int i = 0; i < columns.size(); i++) {
            TableColumnModel column = columns.get(i);

            returnString += column.getColumName() + ": [";

            for (List<Object> tableObject : data) {
                returnString += tableObject.get(i).toString() + ", ";
            }
            returnString += "]\n";
        }
        return returnString;
    }
}
