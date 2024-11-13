package ro.andonie.carkpis.packages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class importDb {
    public static void fromDrivvo() {
        String filePath = "Drivvo.csv";
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSplitBy);

                String[] lineElements = new String[6];

                int rowNumber = 0;
                for (String element : data) {

                    if (rowNumber > 5) {
                        break;
                    }

                    if (element.length() < 2 && rowNumber == 0) {
                        break;
                    }

                    element = element.substring(1, element.length() - 1);

                    if (rowNumber == 0) {
                        try {
                            Integer.parseInt(element);
                        } catch (NumberFormatException e) {
                            // System.out.println("Invalid row number: " + element);
                            break;
                        }
                    }

                    lineElements[rowNumber] = element;
                    // System.out.print(element + " ");
                    rowNumber++;
                }
                if ("Gasoline".equals(lineElements[2])) {
                    lineElements[1] = lineElements[1].substring(0, lineElements[1].length() - 9);

                    handleDb.addNewTransaction(lineElements[1], Integer.parseInt(lineElements[0]),
                            Float.parseFloat(lineElements[4]), Float.parseFloat(lineElements[5]), "Fuel", "Refueling",
                            "Refueling");
                    // System.out.println("line added in db");
                }

                // System.out.println();

            }
        } catch (IOException e) {
            System.err.println("readCsvFile: " + e.getMessage());
        }
    }
}
