package ro.andonie.carkpis;

import java.util.Scanner;
import ro.andonie.carkpis.packages.handleUserInput;
import ro.andonie.carkpis.packages.handledb;

public class App {
    public static void main(String[] args) {

        handledb.createdb();

        System.out.print("\n========== CarKPIs ==========\n");
        // System.out.print("\"exit\" to close the program\n \n");
        System.out.print("Select an option:\n");
        System.out.print("1 - Add a refueling log\n");
        System.out.print("2 - Show DataBase\n");
        System.out.print("Selected option: ");

        // new method?
        Scanner scanner = new Scanner(System.in);
        int option = 99;
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
        }

        switch (option) {
            case 1 -> {
                handleUserInput.AddRefueling();
            }
            case 2 -> {
                handledb.ShowDataBase();
            }
            default -> System.out.println("Invalid option selected.");
        }

        scanner.close();

    }
}