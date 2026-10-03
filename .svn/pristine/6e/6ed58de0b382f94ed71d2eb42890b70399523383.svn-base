package metadata;

import java.util.List;
import java.util.Vector;

/**
 * Čuva metapodatke jedne kolone tabele.
 */
public class TableColumnModel {

    private String columName;
    private String columType;
    private String columLabel;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;
    private boolean isNullable;
    private boolean isVisible;
    private String refColumn;
    private String refTable;
    private List<Object> fkData;

    /**
     * Inicijalizuje objekat za rad u aplikaciji.
     *
     * @param columnName naziv kolone
     * @param columnType SQL tip kolone
     * @param columLabel tekstualna oznaka kolone
     * @param isPrimaryKey da li je kolona primarni ključ
     * @param isAutoIncrement da li baza automatski generiše vrijednost
     * @param isNullable da li kolona prihvata NULL vrijednost
     * @param isVisible da li se kolona prikazuje korisniku
     * @param refColumn naziv referencirane kolone
     * @param refTable naziv referencirane tabele
     */
    public TableColumnModel(
        String columnName,
        String columnType,
        String columLabel,
        boolean isPrimaryKey,
        boolean isAutoIncrement,
        boolean isNullable,
        boolean isVisible,
        String refColumn,
        String refTable
    ) {
        this.columName = columnName;
        this.columType = columnType;
        this.columLabel = columLabel;
        this.isPrimaryKey = isPrimaryKey;
        this.isAutoIncrement = isAutoIncrement;
        this.isVisible = isVisible;
        this.refColumn = refColumn;
        this.refTable = refTable;
        this.isNullable = isNullable;
    }

    /**
     * Vraća naziv kolone.
     *
     * @return naziv kolone
     */
    public String getColumName() {
        return columName;
    }

    /**
     * Postavlja naziv kolone.
     *
     * @param columName naziv kolone
     */
    public void setColumName(String columName) {
        this.columName = columName;
    }

    /**
     * Vraća SQL tip kolone.
     *
     * @return SQL tip kolone
     */
    public String getColumType() {
        return columType;
    }

    /**
     * Postavlja SQL tip kolone.
     *
     * @param columType SQL tip kolone
     */
    public void setColumType(String columType) {
        this.columType = columType;
    }

    /**
     * Provjerava da li je kolona primarni ključ.
     *
     * @return {@code true} ako je kolona primarni ključ
     */
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    /**
     * Postavlja informaciju da li je kolona primarni ključ.
     *
     * @param isPrimaryKey da li je kolona primarni ključ
     */
    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * Provjerava da li baza automatski generiše vrijednost kolone.
     *
     * @return {@code true} ako se vrijednost automatski generiše
     */
    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    /**
     * Postavlja informaciju da li se vrijednost automatski generiše.
     *
     * @param isAutoIncrement da li baza automatski generiše vrijednost
     */
    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    /**
     * Provjerava da li kolona prihvata NULL vrijednost.
     *
     * @return {@code true} ako kolona prihvata NULL vrijednost
     */
    public boolean isNullable() {
        return isNullable;
    }

    /**
     * Postavlja informaciju da li kolona prihvata NULL vrijednost.
     *
     * @param isNullable da li kolona prihvata NULL vrijednost
     */
    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    /**
     * Provjerava da li se kolona prikazuje korisniku.
     *
     * @return {@code true} ako je kolona vidljiva
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Postavlja informaciju da li se kolona prikazuje.
     *
     * @param isVisible da li se kolona prikazuje korisniku
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Vraća naziv referencirane kolone.
     *
     * @return naziv referencirane kolone
     */
    public String getRefColumn() {
        return refColumn;
    }

    /**
     * Postavlja naziv referencirane kolone.
     *
     * @param refColumn naziv referencirane kolone
     */
    public void setRefColumn(String refColumn) {
        this.refColumn = refColumn;
    }

    /**
     * Vraća naziv referencirane tabele.
     *
     * @return naziv referencirane tabele
     */
    public String getRefTable() {
        return refTable;
    }

    /**
     * Postavlja naziv referencirane tabele.
     *
     * @param refTable naziv referencirane tabele
     */
    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    /**
     * Vraća vrijednosti dostupne za strani ključ.
     *
     * @return vrijednosti dostupne za strani ključ
     */
    public List<Object> getFkData() {
        return fkData;
    }

    /**
     * Vraća oznaku kolone za prikaz.
     *
     * @return oznaku kolone za prikaz
     */
    public String getColumLabel() {
        return columLabel;
    }

    /**
     * Postavlja oznaku kolone za prikaz.
     *
     * @param columLabel tekstualna oznaka kolone
     */
    public void setColumLabel(String columLabel) {
        this.columLabel = columLabel;
    }

    /**
     * Postavlja vrijednosti dostupne za strani ključ.
     *
     * @param fkData vrijednosti dostupne za strani ključ
     */
    public void setFkData(List<Object> fkData) {
        if (this.fkData == null) {
            this.fkData = new Vector<Object>();
            this.fkData = fkData;
        }
        this.fkData = fkData;
    }
}
