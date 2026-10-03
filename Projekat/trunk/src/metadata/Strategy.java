package metadata;

import java.util.HashMap;
import java.util.List;
import metadata.TableColumnModel;

/**
 * Definiše način učitavanja metapodataka o tabelama.
 */
public interface Strategy {
    /**
     * Čita metapodatke kolona zadate tabele iz XML dokumenta.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @return lista metapodataka kolona; prazna lista ako tabela nije pronađena
     */
    public List<TableColumnModel> parseDataModel(String tableName);
    /**
     * Čita nazive CRUD procedura zadate tabele iz XML dokumenta.
     *
     * @param tableName kod tabele iz XML metapodataka
     * @return mapa operacija i naziva procedura; prazna mapa ako tabela nije pronađena
     */
    public HashMap<String, String> parseCrudOperations(String tableName);
}
