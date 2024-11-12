package ro.andonie.carkpis.packages;

import java.sql.*;

public class handledb {
    private static String dbName = "dbcarkpis";

    public static void createdb() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE IF NOT EXISTS transactions ("
                            + "id INTEGER PRIMARY KEY, "
                            + "date TEXT, "
                            + "kms INTEGER, "
                            + "price REAL, "
                            + "amount REAL, "
                            + "category1 TEXT, "
                            + "category2 TEXT, "
                            + "details TEXT)");

        } catch (Exception e) {
            System.err.println("createdb: " + e.getMessage());
        } finally {
            closeResources(statement, connection);

        }
    }

    public static void clearTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM transactions");
        } catch (Exception e) {
            System.err.println("clearTable: " + e.getMessage());
        } finally {
            closeResources(statement, connection);
        }
    }

    public static void addNewTransaction(String date, int kms, float price, float amount, String category1,
            String category2,
            String details) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            statement.executeUpdate(
                    "INSERT INTO transactions (date, kms, price, amount, category1, category2, details) VALUES ("
                            + "'" + date + "', "
                            + kms + ", "
                            + price + ", "
                            + amount + ", "
                            + "'" + category1 + "', "
                            + "'" + category2 + "', "
                            + "'" + details + "')");
        } catch (Exception e) {
            System.err.println("addNewTransaction: " + e.getMessage());
        } finally {
            closeResources(statement, connection);
        }
    }

    private static void closeResources(Statement statement, Connection connection) {
        try {
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.err.println("exception in closing the db: " + e.getMessage());
        }
    }

    public static void ShowDataBase() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + " ");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("ShowDataBase: " + e.getMessage());
        } finally {
            closeResources(statement, connection);
        }
    }
}
