package ro.andonie.carkpis.packages;

import java.util.Scanner;

public class handleUserInput {
    public static void AddRefueling() {
        System.out.println("\n===== 1 - Refueling log =====");
        // System.out.println("\"back\" to return | \"exit\" to close the program\n");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the date of refueling (YYYY-MM-DD) - leave empty for today's date: ");
        String date = scanner.nextLine();
        if (date.isEmpty()) {
            date = java.time.LocalDate.now().toString();
        }

        System.out.print("Enter the number of km: ");
        int kms = scanner.nextInt();

        System.out.print("Enter the cost of fuel [lei]: ");
        float cost = scanner.nextFloat();

        System.out.print("Enter the amount of fuel (l): ");
        float amount = scanner.nextFloat();

        handledb.addNewTransaction(date, kms, cost, amount, "Fuel", "Refueling", "Refueling");
        System.out.println("Refueling recorded: Date: " + date + ", Amount: " + amount + " liters, Cost: " + cost);

        scanner.close();
    }
}
