package model;

import database.DBResultSet;
import database.DatabaseException;
import database.DatabaseParameter;
import database.DbManipulation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import metadata.Strategy;
import metadata.TableColumnModel;

/**
 * Obezbjeđuje zajedničke operacije nad tabelarnim podacima.
 */
public abstract class GeneralTableModel {

    protected List<TableColumnModel> columns;
    protected List<List<Object>> data;
    protected final DBResultSet dBResultSet;
    protected final Strategy strategy;
    private final String tableName;

    HashMap<String, String> crudOps = new HashMap<>();

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @param strategy strategija za učitavanje metapodataka
     * @param dBResultSet objekat koji obezbjeđuje pristup bazi
     */
    public GeneralTableModel(String tableName, Strategy strategy, DBResultSet dBResultSet) {
        if (strategy == null) throw new IllegalArgumentException("Metadata strategy is required.");
        if (dBResultSet == null) throw new IllegalArgumentException("Database access is required.");

        this.strategy = strategy;
        this.tableName = tableName;
        this.dBResultSet = dBResultSet;
        if (!tableName.isEmpty()) {
            this.columns = strategy.parseDataModel(tableName);
            crudOps = strategy.parseCrudOperations(tableName);
        } else {
            this.columns = new Vector<>();
        }
        this.data = new Vector<>();

        ensureDummyRow();
    }

    /**
     * Vraća objekat za izvršavanje operacija nad bazom.
     *
     * @return objekat za izvršavanje operacija nad bazom
     */
    protected DbManipulation getDatabase() {
        return dBResultSet.getDbManipulation();
    }

    /**
     * Vraća broj redova modela.
     *
     * @return broj redova modela
     */
    public int getRowCount() {
        return this.data.size();
    }

    /**
     * Vraća kod aktivne tabele.
     *
     * @return kod aktivne tabele
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Vraća broj kolona modela.
     *
     * @return broj kolona modela
     */
    public int getColumnCount() {
        return this.columns.size();
    }

    /**
     * Vraća naziv kolone na zadatom indeksu.
     *
     * @param column indeks ili opis kolone
     * @return naziv kolone na zadatom indeksu
     */
    public String getColumnName(int column) {
        return this.columns.get(column).getColumName();
    }

    /**
     * Vraća vrijednost ćelije na zadatim indeksima.
     *
     * @param rowIndex indeks reda
     * @param columnIndex indeks kolone
     * @return vrijednost ćelije ili {@code null} za nevažeće indekse
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (data.isEmpty() || rowIndex >= data.size() || columnIndex >= columns.size()) {
            return null;
        }
        return this.data.get(rowIndex).get(columnIndex);
    }

    /**
     * Vraća metapodatke kolona.
     *
     * @return metapodatke kolona
     */
    public List<TableColumnModel> getColumns() {
        return columns;
    }

    /**
     * Postavlja metapodatke kolona.
     *
     * @param columns metapodaci kolona tabele
     */
    public void setColumns(List<TableColumnModel> columns) {
        this.columns = columns;
        ensureDummyRow();
    }

    /**
     * Vraća učitane redove podataka.
     *
     * @return učitane redove podataka
     */
    public List<List<Object>> getData() {
        return data;
    }

    /**
     * Postavlja učitane redove podataka.
     *
     * @param data redovi podataka koji se postavljaju u model
     */
    public void setData(List<List<Object>> data) {
        this.data = data;
        if (data.isEmpty()) {
            ensureDummyRow();
        }
    }

    /**
     * Pronalazi najveću cjelobrojnu vrijednost primarnog ključa u učitanim redovima.
     *
     * @return najveći primarni ključ ili {@code null} ako ga nije moguće odrediti
     */
    public Integer getHighestPrimaryKey() {
        if (columns == null || data == null || data.isEmpty()) return null;

        int pkIndex = -1;
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).isPrimaryKey()) {
                pkIndex = i;
                break;
            }
        }

        if (pkIndex == -1) {
            return null;
        }

        Integer max = null;
        for (List<Object> row : data) {
            Object value = row.get(pkIndex);
            if (value instanceof Number) {
                int intValue = ((Number) value).intValue();
                if (max == null || intValue > max) {
                    max = intValue;
                }
            }
        }

        return max;
    }

    private void ensureDummyRow() {
        if (data.isEmpty() && columns != null && !columns.isEmpty()) {
            List<Object> emptyRow = new Vector<>();
            for (int i = 0; i < columns.size(); i++) {
                emptyRow.add(null);
            }
            data.add(emptyRow);
        }
    }

    /**
     * Učitava sve redove aktivne tabele iz baze.
     *
     * @return {@code true} ako su podaci učitani, inače {@code false}
     */
    public boolean getAllData() {
        this.data.clear();
        try {
            this.data.addAll(getDatabase().executeQuery(crudOps.get("retrieve"), getColumnNames()));
            return true;
        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Briše red određen vrijednostima primarnog ključa.
     *
     * @param columnPKs nazivi i vrijednosti primarnih ključeva reda
     * @return {@code true} ako je red obrisan, inače {@code false}
     */
    public boolean deleteRow(HashMap<String, Object> columnPKs) {
        try {
            return getDatabase().executeUpdate(crudOps.get("delete"), toParameters(columnPKs));
        } catch (DatabaseException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Vraća red određen vrijednostima primarnog ključa.
     *
     * @param columnPKs nazivi i vrijednosti primarnih ključeva reda
     * @return red određen vrijednostima primarnog ključa
     */
    public List<Object> getRowById(HashMap<String, Object> columnPKs) {
        try {
            return getDatabase().executeSingleRow(
                crudOps.get("retrieveById"),
                toParameters(columnPKs),
                getColumnNames()
            );
        } catch (DatabaseException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Dodaje novi red u aktivnu tabelu.
     *
     * @param params vrijednosti reda, indeksirane nazivom kolone
     * @return {@code true} ako je red dodat, inače {@code false}
     */
    public boolean addRow(HashMap<String, String> params) {
        try {
            getDatabase().executeUpdate(crudOps.get("create"), toColumnParameters(params));
            return this.getAllData();
        } catch (DatabaseException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mijenja postojeći red aktivne tabele.
     *
     * @param params vrijednosti reda, indeksirane nazivom kolone
     * @return {@code true} ako je red izmijenjen, inače {@code false}
     */
    public boolean editRow(HashMap<String, String> params) {
        try {
            getDatabase().executeUpdate(crudOps.get("update"), toColumnParameters(params));
            return this.getAllData();
        } catch (DatabaseException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<String> getColumnNames() {
        List<String> names = new ArrayList<>();
        for (TableColumnModel column : columns) {
            names.add(column.getColumName());
        }
        return names;
    }

    private List<DatabaseParameter> toParameters(Map<String, Object> values) {
        List<DatabaseParameter> parameters = new ArrayList<>();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            parameters.add(new DatabaseParameter(entry.getKey(), entry.getValue()));
        }
        return parameters;
    }

    private List<DatabaseParameter> toColumnParameters(Map<String, String> values) {
        List<DatabaseParameter> parameters = new ArrayList<>();
        for (TableColumnModel column : columns) {
            parameters.add(new DatabaseParameter(column.getColumType(), values.get(column.getColumName())));
        }
        return parameters;
    }
}
