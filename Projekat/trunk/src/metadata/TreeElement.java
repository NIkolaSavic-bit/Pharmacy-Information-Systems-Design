package metadata;

import java.util.Vector;

/**
 * Predstavlja osnovni element stabla metapodataka.
 */
public abstract class TreeElement {

    protected String code = null;
    protected String name = null;
    protected String iconPath = null;

    private Vector<TreeElement> treeElements = new Vector<TreeElement>();

    /**
     * Vraća tekstualni prikaz objekta.
     *
     * @return tekstualni prikaz objekta
     */
    @Override
    public String toString() {

        return this.name;
    }

    /**
     * Vraća indeks elementa u listi djece.
     *
     * @param element element koji se dodaje u stablo
     * @return indeks elementa u listi djece
     */
    public int getIndexOfElement(TreeElement element) {
        return treeElements.indexOf(element);
    }

    /**
     * Dodaje element u listu djece ovog čvora.
     *
     * @param element element koji se dodaje u stablo
     */
    public void addElement(TreeElement element) {
        treeElements.add(element);
    }

    /**
     * Vraća sve neposredne elemente ovog čvora.
     *
     * @return sve neposredne elemente ovog čvora
     */
    public Vector<TreeElement> getAllElements() {
        return treeElements;
    }

    /**
     * Vraća element na zadatom indeksu.
     *
     * @param index indeks elementa ili SQL parametra
     * @return element na zadatom indeksu
     */
    public TreeElement getElementAt(int index) {
        return treeElements.get(index);
    }

    /**
     * Vraća naziv elementa.
     *
     * @return naziv elementa
     */
    public String getName() {
        return name;
    }

    /**
     * Vraća putanju do ikone elementa.
     *
     * @return putanju do ikone elementa
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Postavlja putanju do ikone elementa.
     *
     * @param iconPath putanja do ikone elementa
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }






    /**
     * Predstavlja paket u stablu metapodataka.
     */
    public static class Package extends TreeElement {

        /**
         * Inicijalizuje objekat za rad u aplikaciji.
         */
        public Package() {

        }

        /**
         * Inicijalizuje objekat za rad u aplikaciji.
         *
         * @param name naziv elementa
         */
        public Package(String name) {

            this.name = name;
        }
    }






    /**
     * Predstavlja tabelu u stablu metapodataka.
     */
    public static class Table extends TreeElement {

        private Vector<Table> refrences = new Vector<TreeElement.Table>();

        private String createSProc = null;
        private String retrieveSProc = null;
        private String updateSProc = null;
        private String deleteSProc = null;

        /**
         * Vraća naziv procedure za kreiranje.
         *
         * @return naziv procedure za kreiranje
         */
        public String getCreateSProc() {
            return createSProc;
        }

        /**
         * Postavlja naziv procedure za kreiranje.
         *
         * @param createSProc vrijednost koja se prosljeđuje metodi
         */
        public void setCreateSProc(String createSProc) {
            this.createSProc = createSProc;
        }

        /**
         * Vraća naziv procedure za čitanje.
         *
         * @return naziv procedure za čitanje
         */
        public String getRetrieveSProc() {
            return retrieveSProc;
        }

        /**
         * Postavlja naziv procedure za čitanje.
         *
         * @param retrieveSProc vrijednost koja se prosljeđuje metodi
         */
        public void setRetrieveSProc(String retrieveSProc) {
            this.retrieveSProc = retrieveSProc;
        }

        /**
         * Vraća naziv procedure za izmjenu.
         *
         * @return naziv procedure za izmjenu
         */
        public String getUpdateSProc() {
            return updateSProc;
        }

        /**
         * Postavlja naziv procedure za izmjenu.
         *
         * @param updateSProc vrijednost koja se prosljeđuje metodi
         */
        public void setUpdateSProc(String updateSProc) {
            this.updateSProc = updateSProc;
        }

        /**
         * Vraća naziv procedure za brisanje.
         *
         * @return naziv procedure za brisanje
         */
        public String getDeleteSProc() {
            return deleteSProc;
        }

        /**
         * Postavlja naziv procedure za brisanje.
         *
         * @param deleteSProc vrijednost koja se prosljeđuje metodi
         */
        public void setDeleteSProc(String deleteSProc) {
            this.deleteSProc = deleteSProc;
        }

        /**
         * Dodaje tabelu u listu referenci.
         *
         * @param reference vrijednost koja se prosljeđuje metodi
         */
        public void addReference(Table reference) {
            refrences.add(reference);
        }

        /**
         * Vraća sve tabele koje ovaj element referencira.
         *
         * @return sve tabele koje ovaj element referencira
         */
        public Vector<Table> getAllRefernces() {
            return refrences;
        }
    }






    /**
     * Predstavlja kolonu tabele iz metapodataka.
     */
    public static class Column extends TreeElement {

        private Boolean primary = null;
        private Boolean nullable = null;
        private Boolean autoIncrement = null;
        private int size;
        private String type;
        private int scale;
        private String refColumn;
        private String refTable;

        /**
         * Inicijalizuje objekat za rad u aplikaciji.
         */
        public Column() {}

        /**
         * Inicijalizuje objekat za rad u aplikaciji.
         *
         * @param name naziv elementa
         */
        public Column(String name) {
            this.name = name;
        }

        /**
         * Provjerava da li je kolona primarni ključ.
         *
         * @return {@code true} ako je kolona primarni ključ
         */
        public Boolean isPrimary() {
            return primary;
        }

        /**
         * Postavlja informaciju da li je kolona primarni ključ.
         *
         * @param primary da li je kolona primarni ključ
         */
        public void setPrimary(Boolean primary) {
            this.primary = primary;
        }

        /**
         * Provjerava da li kolona prihvata NULL vrijednost.
         *
         * @return {@code true} ako kolona prihvata NULL vrijednost
         */
        public Boolean isNullable() {
            return nullable;
        }

        /**
         * Postavlja informaciju da li kolona prihvata NULL vrijednost.
         *
         * @param nullable da li kolona prihvata NULL vrijednost
         */
        public void setNullable(Boolean nullable) {
            this.nullable = nullable;
        }

        /**
         * Postavlja informaciju da li se vrijednost automatski generiše.
         *
         * @param ai da li se vrijednost automatski generiše
         */
        public void setAutoIncrement(boolean ai) {

            autoIncrement = ai;
        }

        /**
         * Vraća da li se vrijednost automatski generiše.
         *
         * @return da li se vrijednost automatski generiše
         */
        public boolean getAutoIncrement() {
            return autoIncrement;
        }

        /**
         * Vraća maksimalnu veličinu vrijednosti kolone.
         *
         * @return maksimalnu veličinu vrijednosti kolone
         */
        public int getSize() {
            return size;
        }

        /**
         * Postavlja maksimalnu veličinu vrijednosti kolone.
         *
         * @param size maksimalna veličina kolone
         */
        public void setSize(int size) {
            this.size = size;
        }

        /**
         * Vraća tip podatka ili baze.
         *
         * @return tip podatka ili baze
         */
        public String getType() {
            return type;
        }

        /**
         * Postavlja tip podatka ili baze.
         *
         * @param type naziv tipa baze ili SQL tipa podatka
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Vraća broj decimalnih mjesta.
         *
         * @return broj decimalnih mjesta
         */
        public int getScale() {
            return scale;
        }

        /**
         * Postavlja broj decimalnih mjesta.
         *
         * @param scale broj decimalnih mjesta
         */
        public void setScale(int scale) {
            this.scale = scale;
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
    }
}
