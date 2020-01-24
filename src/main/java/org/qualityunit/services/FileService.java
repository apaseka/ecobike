package org.qualityunit.services;

import org.qualityunit.model.Bike;
import org.qualityunit.model.BikeType;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static org.qualityunit.EcoBikeRunner.scanner;
import static org.qualityunit.services.BikeService.BRAND;

public class FileService {

    private static final int BIKE_TYPE = 0;
    private static final String TXT_FORMAT = "^.*\\.txt$";
    public static int INITIAL_CATALOG_SIZE;


    public static List<Bike> bikes;

    public static void readFile(String filePath) {
        try {
            bikes = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String row;

            while ((row = bufferedReader.readLine()) != null) {
                String[] bikeRow = row.replaceAll(" ", "").split(";");

                if (bikeRow[BIKE_TYPE].contains("SPEEDELEC")) {
                    BikeService.addBike(bikes, bikeRow, BikeType.S_ELEC, bikeRow[BRAND].replaceFirst("SPEEDELEC", ""));
                } else if (bikeRow[BIKE_TYPE].contains("E-BIKE")) {
                    BikeService.addBike(bikes, bikeRow, BikeType.E_BIKE, bikeRow[BRAND].replaceFirst("E-BIKE", ""));
                } else {
                    BikeService.addBike(bikes, bikeRow);
                }
            }
            INITIAL_CATALOG_SIZE = bikes.size();

        } catch (Exception e) {
            System.out.println("Wrong data structure in file. Try again with correct file");
            String path = checkFilePath(new String[]{});
            readFile(path);
        }
    }

    public static void writeToFile(List<Bike> bikes, String filePath) {
        boolean repeat = true;
        if (bikes.size() != INITIAL_CATALOG_SIZE) {
            while (repeat) {
                try {
                    if (filePath == null || filePath.isEmpty() || !filePath.matches(TXT_FORMAT)) {
                        System.out.println("\nSpecify file name for saving");
                        filePath = scanner.next();

                        if (!filePath.matches(TXT_FORMAT)) {
                            throw new InputMismatchException();
                        }
                    }

                    FileWriter fw = new FileWriter(filePath);
                    for (Bike bike : bikes) {
                        fw.write(bike.toWritableForm());
                    }
                    fw.close();

                    checkFilePath(filePath);
                    System.out.println("Catalog successfully saved");
                    repeat = false;
                    INITIAL_CATALOG_SIZE = bikes.size();
                } catch (InputMismatchException e) {
                    System.out.println("File not saved. Wrong file format. Must be TXT");
                } catch (FileNotFoundException e) {
                    System.out.println("File not saved. File path is not valid");
                } catch (Exception e) {
                    System.out.println("File not saved. Try again!");
                }
            }
        } else {
            System.out.println("No bikes added. No need to save catalog.");
        }
    }

    private static void checkFilePath(String filePath) throws FileNotFoundException {
    if (!new File(filePath).exists() || !filePath.matches(TXT_FORMAT))
            throw new FileNotFoundException();
    }

    public static String checkFilePath(String[] args) {
        boolean repeat = true;
        String fPath = "";

        while (repeat) {
            try {
                if (args == null || args.length < 1) {
                    System.out.println("Specify file path");
                    fPath = scanner.nextLine();
                } else {
                    fPath = args[0];
                }
                checkFilePath(fPath);
                repeat = false;
            } catch (FileNotFoundException e) {
                System.out.println("File path or format is wrong. Try again with correct input");
            }
        }
        return fPath;
    }
}
