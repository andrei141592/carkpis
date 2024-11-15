package ro.andonie.carkpis.packages;

import java.io.File;
import java.sql.*;
import java.util.Scanner;

public class Initialize {
    public static boolean checkDb(Scanner scanner) {
        File dbFile = new File("dbcarkpis.db");
        if (dbFile.exists() && dbFile.isFile()) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:dbcarkpis.db")) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
                    boolean transactionsTable = false;
                    while (rs.next()) {
                        String tableName = rs.getString("TABLE_NAME");
                        if (tableName.equalsIgnoreCase("transactions")) {
                            transactionsTable = true;
                        }
                    }
                    if (!transactionsTable) {
                        return HandleUserInput.checkIfNewDb(scanner, "missing-tables");
                    }
                }
                conn.close();
            } catch (SQLException e) {
                return HandleUserInput.checkIfNewDb(scanner, "db-error");
            }

        } else {
            return HandleUserInput.checkIfNewDb(scanner, "no-db");
        }
        return true;
    }

}
