package ro.andonie.carkpis.packages;

import java.util.Scanner;

public class HandleUserInput {
    private static String userInputBuffer = "";

    public static String addRefueling(Scanner scanner) {
        int loop = 0;
        userInputBuffer = "";
        // Scanner scanner = new Scanner(System.in);
        while (loop < 100) {
            System.out.print("\n\u001B[34m\u001B[1m===== 1 - Refueling log =====\u001B[0m\n");
            System.out.print("\"back\" to return | \"exit\" to close the program\n");

            System.out.print("Enter the date of refueling (YYYY-MM-DD) - leave empty for today's date: ");
            userInputBuffer = inputValidation(scanner, "date");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;
            String date = userInputBuffer;

            System.out.print("Enter the number of km: ");
            userInputBuffer = inputValidation(scanner, "int");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;
            int kms = Integer.parseInt(userInputBuffer);

            System.out.print("Enter the cost of fuel [lei]: ");
            userInputBuffer = inputValidation(scanner, "float");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;
            float cost = Float.parseFloat(userInputBuffer);

            System.out.print("Enter the amount of fuel (l): ");
            userInputBuffer = inputValidation(scanner, "float");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;
            float amount = Float.parseFloat(userInputBuffer);

            HandleDb.addNewTransaction(date, kms, cost, amount, "Fuel", "Refueling",
                    "Refueling");
            System.out.println("Refueling recorded: Date: " + date + ", Amount: " + amount + " liters, Cost: " + cost);
            loop++;
        }
        System.out.println("The limit have been reached.");
        return "exit";

    }

    public static String manageDb(Scanner scanner) {
        int loop = 0;
        userInputBuffer = "";
        while (loop < 10 && userInputBuffer != "exit") {
            System.out.print("\n\u001B[34m\u001B[1m===== 2 - Manage database =====\u001B[0m\n");
            System.out.print("\"back\" to return | \"exit\" to close the program\n");

            System.out.print("Options:\n");
            System.out.print("1 - Show entire dataBase\n");
            System.out.print("2 - Show the last x lines from database\n");
            System.out.print("3 - Delete a line from database\n");
            System.out.print("Selected option: ");

            userInputBuffer = inputValidation(scanner, "int");
            if (userInputBuffer == "back" || userInputBuffer == "exit")
                return userInputBuffer;

            switch (userInputBuffer) {
                case "1" -> HandleDb.ShowDataBase();
                case "2" -> userInputBuffer = HandleDb.ShowLastXLines(scanner);
                case "3" -> userInputBuffer = HandleDb.deleteLine(scanner);
                default -> System.out.println("\u001B[31mInvalid option selected.\u001B[0m \nTry again.\n");
            }
            loop++;
        }
        if (userInputBuffer == "exit") {
            return "exit";
        }
        System.out.println("The limit have been reached.");
        return "exit";
    }

    public static String inputValidation(Scanner scanner, String inputType) {
        int trialLoops = 0;
        while (trialLoops < 5) {
            trialLoops++;

            String userInput = scanner.nextLine().trim().toLowerCase();
            switch (userInput) {
                case "back":
                    return "back";
                case "exit":
                    return "exit";
            }
            switch (inputType) {
                case "date":
                    if (!userInput.isEmpty()) {
                        try {
                            java.time.LocalDate.parse(userInput);
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println(
                                    "\u001B[31mInvalid date.\u001B[0m \nPlease enter a valid date in YYYY-MM-DD format.");
                            continue;
                        }
                        return userInput;
                    }

                    if (userInput.isEmpty()) {
                        userInput = java.time.LocalDate.now().toString();
                        System.out.println("Today's date added: " + userInput);
                        return userInput;
                    }
                    break;

                case "int":
                    try {
                        Integer.parseInt(userInput);
                        return userInput;
                    } catch (NumberFormatException e) {
                        System.out.print("\u001B[31mInvalid input.\u001B[0m \nPlease enter a valid number: ");
                        continue;
                    }
                case "float":
                    try {
                        Float.parseFloat(userInput);
                        return userInput;
                    } catch (NumberFormatException e) {
                        System.out.print("\u001B[31mInvalid input.\u001B[0m \nPlease enter a valid number: ");
                        continue;
                    }
                case "y/n":
                    if (userInput.equals("y") || userInput.equals("n")) {
                        return userInput;
                    } else {
                        System.out.print("\u001B[31mInvalid input.\u001B[0m \nPlease enter \"y\" or \"n\": ");
                        continue;
                    }

                default:
                    return "exit";
            }

        }
        if (trialLoops == 5) {
            System.out.println("\u001B[31mToo many invalid inputs. Closing the program.\u001B[0m");
            return "exit";
        }
        return "exit";

    }

    public static boolean checkIfNewDb(Scanner scanner, String checkDbResult) {
        userInputBuffer = "";
        System.out.print("\n\u001B[34m\u001B[1m========== CarKPIs ==========\u001B[0m\n");
        System.out.print("\"exit\" to close the program\n \n");

        if (checkDbResult.equals("no-db")) {
            System.out.println(
                    "The needed database file \"dbcarkpis.db\" can't be found in the same folder at the program.");
        } else if (checkDbResult.equals("missing-tables") || checkDbResult.equals("db-error")) {
            System.out.println("The needed database file \"dbcarkpis.db\" it can not be processed.");
        }

        System.out.print("Do you want to create a new database? (\"y\"/\"n\"): ");

        userInputBuffer = inputValidation(scanner, "y/n");
        if ("y".equals(userInputBuffer)) {
            return HandleDb.createDb();
        }
        return false;
    }
}
