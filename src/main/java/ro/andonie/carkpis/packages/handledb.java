package ro.andonie.carkpis.packages;

import java.sql.*;
import java.util.Scanner;

public class HandleDb {

    private static String userInputBuffer = "";

    private static String dbName = "dbcarkpis";

    public static boolean createDb() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS transactions ("
                            + "id INTEGER PRIMARY KEY, "
                            + "date TEXT, "
                            + "kms INTEGER, "
                            + "cost REAL, "
                            + "amount REAL, "
                            + "category1 TEXT, "
                            + "category2 TEXT, "
                            + "details TEXT)");
            System.out.println("Database created successfully.");
            return true;
        } catch (Exception e) {
            System.out.println("Database creation failed." + e);
            return false;
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
                    "INSERT INTO transactions (date, kms, cost, amount, category1, category2, details) VALUES ("
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
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            System.err.println("Exception in closing the db: {}" + e.getMessage());
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
            System.out.println();

            TableDisplay.printTable(columnCount, resultSet, metaData);

        } catch (Exception e) {
            System.err.println("ShowDataBase: {}" + e.getMessage());
        } finally {
            closeResources(statement, connection);
        }
    }

    public static String ShowLastXLines(Scanner scanner) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            System.out.print("Enter the number of lines to show: ");

            userInputBuffer = HandleUserInput.inputValidation(scanner, "int");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;
            int lines = Integer.parseInt(userInputBuffer);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions ORDER BY id DESC LIMIT " + lines);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            TableDisplay.printTable(columnCount, resultSet, metaData);

        } catch (Exception e) {
            System.err.println("ShowLastXLines: {}" + e.getMessage());
        } finally {
            closeResources(statement, connection);
        }
        return "exit";
    }

    public static String deleteLine(Scanner scanner) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            statement = connection.createStatement();
            System.out.print("Enter the id of the line to delete: ");
            userInputBuffer = HandleUserInput.inputValidation(scanner, "int");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;
            int id = Integer.parseInt(userInputBuffer);

            ResultSet resultSet = statement.executeQuery("SELECT id FROM transactions WHERE id = " + id);
            if (!resultSet.next()) {
                System.out.println("\u001B[31mNo record found with id: \u001B[0m" + id);
                return "back";
            }
            statement.executeUpdate("DELETE FROM transactions WHERE id = " + id);
        } catch (Exception e) {
            System.err.println("deleteLine: {}" + e.getMessage());
        } finally {
            closeResources(statement, connection);
        }
        return "exit";
    }
}