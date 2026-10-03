package util;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;
import java.util.regex.*;

/**
 * Pretvara tekstualne vrijednosti u odgovarajuće SQL tipove.
 */
public class DataTypeMapper {

    private String typeName;
    private String numbers;
    private String[] partsString;
    private List<Integer> partsNumber = new Vector<Integer>();

    /**
     * Izdvaja osnovni naziv SQL tipa, bez veličine i preciznosti.
     *
     * @param type naziv tipa baze ili SQL tipa podatka
     * @return osnovni naziv SQL tipa
     */
    public String extractTypeName(String type) {
        typeName = null;
        numbers = null;
        partsString = new String[0];
        partsNumber.clear();

        Pattern pattern = Pattern.compile("^([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(type);

        if (matcher.find()) {
            typeName = matcher.group(1);









        }

        return typeName;
    }

    /**
     * Pretvara tekstualnu vrijednost i postavlja je kao odgovarajući SQL parametar.
     *
     * @param type naziv tipa baze ili SQL tipa podatka
     * @param value tekstualna vrijednost koja se upisuje
     * @param statement SQL naredba kojoj se dodaje parametar
     * @param index indeks elementa ili SQL parametra
     * @throws NumberFormatException ako operacija ne može biti izvršena
     * @throws SQLException ako operacija ne može biti izvršena
     */
    public void mapDataToType(String type, String value, CallableStatement statement, int index)
        throws NumberFormatException, SQLException {
        String resolvedType = extractTypeName(type).toLowerCase();

        if (value == null || value.isEmpty()) {
            switch (resolvedType) {
                case "int":
                case "smallint":
                case "bigint":
                    statement.setNull(index, java.sql.Types.INTEGER);
                    break;
                case "numeric":
                case "decimal":
                case "float":
                case "real":
                    statement.setNull(index, java.sql.Types.NUMERIC);
                    break;
                case "nvarchar":
                case "varchar":
                case "char":
                case "text":
                    statement.setNull(index, java.sql.Types.VARCHAR);
                    break;
                case "datetime":
                case "timestamp":
                    statement.setNull(index, java.sql.Types.TIMESTAMP);
                    break;
                case "bit":
                case "boolean":
                    statement.setNull(index, java.sql.Types.BOOLEAN);
                    break;
                case "image":
                case "varbinary":
                case "vbin":
                    statement.setNull(index, java.sql.Types.VARBINARY);
                    System.out.println(index + ": " + java.sql.Types.VARBINARY);
                    break;
                default:
                    statement.setNull(index, java.sql.Types.VARCHAR);
                    break;
            }
            return;
        }


        switch (resolvedType) {
            case "int":
            case "smallint":
            case "bigint":
                statement.setInt(index, Integer.parseInt(value));
                break;
            case "numeric":
            case "decimal":
            case "float":
            case "real":
                java.math.BigDecimal bd = new java.math.BigDecimal(value.trim());
                statement.setBigDecimal(index, bd);
                break;
            case "nvarchar":
            case "varchar":
            case "char":
            case "text":
                statement.setString(index, value);
                break;
            case "datetime":
            case "timestamp":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dt = LocalDateTime.parse(value, formatter);
                statement.setObject(index, dt);
                break;
            case "bit":
            case "boolean":
                boolean boolValue = value.equals("1") || value.equalsIgnoreCase("true");
                statement.setBoolean(index, boolValue);
                break;
            case "image":
            case "varbinary":
            case "vbin":
                statement.setBytes(index, value.getBytes());
                break;
            default:
                statement.setString(index, value);
                break;
        }
    }
}
