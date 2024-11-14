package ro.andonie.carkpis.packages;

import java.sql.*;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

public class TableDisplay {
    public static void printTable(int columnCount, ResultSet resultSet, ResultSetMetaData metaData)
            throws SQLException {
        AsciiTable at = new AsciiTable();
        at.getRenderer().setCWC(new CWC_LongestLine().add(10, 50)); // Auto-adjust column width with a max width of 30
        at.addRule();
        String[] header = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            header[i - 1] = metaData.getColumnName(i);
        }
        at.addRow((Object[]) header);
        at.addRule();
        while (resultSet.next()) {
            String[] row = new String[columnCount];
            for (int j = 1; j <= columnCount; j++) {
                row[j - 1] = resultSet.getString(j);
            }
            at.addRow((Object[]) row);
            at.addRule();
        }
        System.out.println(at.render());
    }
}
