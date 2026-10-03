package util;

import java.io.File;

/**
 * Obezbjeđuje putanje do resursa aplikacije.
 */
public final class ResourcePaths {

    public static final String DATABASE_METADATA = "src/resources/metadata/scheme.xml";
    public static final String REPORT_TEMPLATE_HORIZONTAL = "src/resources/reports/report_template_horizontal.jrxml";
    public static final String REPORT_TEMPLATE_VERTICAL = "src/resources/reports/report_template_vertical.jrxml";
    public static final String REPORT_LOGO = "src/resources/icons/pharmease.png";

    private ResourcePaths() {}

    /**
     * Vraća fajl koji sadrži metapodatke baze.
     *
     * @return XML fajl sa metapodacima baze
     */
    public static File databaseMetadataFile() {
        return new File(DATABASE_METADATA);
    }
}
