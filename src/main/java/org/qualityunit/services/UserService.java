package org.qualityunit.services;

import org.qualityunit.model.BikeType;

import static org.qualityunit.EcoBikeRunner.scanner;
import static org.qualityunit.services.FileService.bikes;

public class UserService {

    private static final int BIKES_PER_PAGE = 31;

    public static void showMenu() {
        while (true) {
            System.out.println("\r\nPlease make your choice:\r\n" +
                    "1 - Show the entire EcoBike catalog\r\n" +
                    "2 - Add a new folding bike\r\n" +
                    "3 - Add a new speedelec\r\n" +
                    "4 - Add a new e-bike\r\n" +
                    "5 - Find the bike\r\n" +
                    "6 - Write to file\r\n" +
                    "7 - Stop the program");

            switch (scanner.next()) {
                case "1":
                    showCatalog();
                    break;
                case "2":
                    BikeService.addBike(BikeType.F_BIKE);
                    break;
                case "3":
                    BikeService.addBike(BikeType.S_ELEC);
                    break;
                case "4":
                    BikeService.addBike(BikeType.E_BIKE);
                    break;
                case "5":
                    BikeService.searchBikeInCatalog();
                    break;
                case "6":
                    FileService.writeToFile(bikes);
                    break;
                case "7":
                    if (FileService.INITIAL_CATALOG_SIZE < bikes.size()) {
                        System.out.println("You haven't saved new bikes added to catalog. You will be prompted to save");
                        FileService.writeToFile(bikes);
                    }
                    System.out.println("The program is stopped");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong input! Enter numbers from 1 to 7! Try again.");
                    break;
            }
        }
    }

    public static void showCatalog() {
        if (bikes.isEmpty()) {
            System.out.println("\r\nCatalog is empty.");
        } else if (bikes.size() > BIKES_PER_PAGE) {
            System.out.println("\r\nCatalog is huge, you will see page by page after pressing ENTER. \r\n Press ENTER to proceed, type M to show menu.");
            scanner.nextLine();
            printPages(0, 1);
        } else {
            printPages(0, 1);
        }
    }

    private static void printPages(int index, int page) {
        switch (scanner.nextLine().toUpperCase()) {
            case "M":
                showMenu();
                break;
            case "":
                try {
                    for (int i = 0; i < BIKES_PER_PAGE; i++) {
                        System.out.println(bikes.get(index++));
                    }
                    System.out.println("page " + page++ + " of " + (bikes.size() / BIKES_PER_PAGE + 1) + " pages");
                    System.out.println("\nPress ENTER to proceed, type M to show menu.");
                    printPages(index, page);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("page " + page++ + " of " + (bikes.size() / BIKES_PER_PAGE + 1) + " page(s)");
                    showMenu();
                }
            default:
                System.out.println("Wrong input!\r\nPress ENTER to proceed, type M to show menu.");
                printPages(index, page);
        }
    }
}
