package ro.andonie.carkpis;

import java.util.Scanner;
import ro.andonie.carkpis.packages.*;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String userInputBuffer = "";

        int loop = 0;
        while (Initialize.checkDb(scanner) && loop < 10 && userInputBuffer != "exit") {
            System.out.print("\n\u001B[34m\u001B[1m========== CarKPIs ==========\u001B[0m\n");
            System.out.print("\"exit\" to close the program\n \n");
            System.out.print("Options:\n");
            System.out.print("1 - Add a refueling log\n");
            System.out.print("2 - Manage database\n");
            System.out.print("3 - Show Key Performance Indicators\n");
            System.out.print("Selected option: ");
            String userInput = "";
            try {
                userInput = scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine(); // clear the invalid input
            }

            switch (userInput) {
                case "1" -> userInputBuffer = HandleUserInput.addRefueling(scanner);
                case "2" -> userInputBuffer = HandleUserInput.manageDb(scanner);
                case "3" -> userInputBuffer = HandleUserInput.overallConsumtion();

                case "exit" -> loop = 10;
                default -> System.out.println("\u001B[31mInvalid option selected.\u001B[0m\nTry again.\n");
            }
            loop++;
        }

        if (loop == 10) {
            System.out.println("The limit for retry have been reached.");
        }

        scanner.close();

    }
}