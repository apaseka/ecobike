package org.qualityunit.services;

import org.apache.commons.lang3.ArrayUtils;
import org.qualityunit.model.Bike;
import org.qualityunit.model.BikeType;
import org.qualityunit.model.ElectricBike;
import org.qualityunit.model.FoldingBike;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.qualityunit.EcoBikeRunner.scanner;
import static org.qualityunit.services.FileService.bikes;

public class BikeService {

    private static final String TO_SKIP_FILTER = "-";

    //bike property indexes in txt catalog
    public static final int BRAND = 0;
    private static final int MAX_SPEED = 1;
    private static final int WEIGHT_EL = 2;
    private static final int LIGHTS_EL = 3;
    private static final int BATTERY_CAPACITY = 4;
    private static final int COLOR = 5;
    private static final int PRICE = 6;
    private static final int WHEEL_SIZE = 1;
    private static final int GEARS = 2;
    private static final int WEIGHT_FOLD = 3;
    private static final int LIGHTS_FOLD = 4;

    //link to "searching" thread worker to restrict user from adding bike while search is active
    private static Thread thread;

    private static boolean searchByBrandOnly;
    private static boolean searchFlag;

    //adding electric bike from catalog
    public static void addBike(List<Bike> bikeList, String[] bikeRow, BikeType bikeType, String brand) {
        bikeList.add(new ElectricBike(bikeType, brand, Integer.parseInt(bikeRow[WEIGHT_EL]), Boolean.parseBoolean(bikeRow[LIGHTS_EL]),
                bikeRow[COLOR], Integer.parseInt(bikeRow[PRICE]), Integer.parseInt(bikeRow[MAX_SPEED]), Integer.parseInt(bikeRow[BATTERY_CAPACITY])));
    }

    //adding folding bike from catalog
    public static void addBike(List<Bike> bikeList, String[] bikeRow) {
        bikeList.add(new FoldingBike(bikeRow[BRAND].replaceFirst("FOLDINGBIKE", ""), Integer.parseInt(bikeRow[WEIGHT_FOLD]), Boolean.parseBoolean(bikeRow[LIGHTS_FOLD]),
                bikeRow[COLOR], Integer.parseInt(bikeRow[PRICE]), Integer.parseInt(bikeRow[WHEEL_SIZE]), Integer.parseInt(bikeRow[GEARS])));
    }

    //adding new bike through console
    public static void addBike(BikeType bikeType) {
        Bike bike = createBikeInstance(bikeType);
        bikes.add(bike);
        System.out.println("\r\nAdded new " + bikeType.getTypeName() + ":\n" + bike.toString());
    }

    private static synchronized Bike createBikeInstance(BikeType bikeType) {
        //general bike features
        if (searchFlag)
            System.out.println("\r\nType < " + TO_SKIP_FILTER + " > to leave filter empty\r\n");

        System.out.println("\r\nEnter the brand:");
        String brand = scanner.next();

        Integer weight = intCheck("Enter the weight of the bike (in grams):");

        System.out.println("Enter the availability of lights at front and back (true/false):");
        String lights = scanner.next();
        while ((!lights.equalsIgnoreCase("true") && !lights.equalsIgnoreCase("false")) && (!searchFlag || !lights.equalsIgnoreCase(TO_SKIP_FILTER))) {
            if (searchFlag) {
                System.out.println("Type < " + TO_SKIP_FILTER + " >, TRUE or FALSE!");
            } else {
                System.out.println("Only TRUE or FALSE input allowed!");
            }
            lights = scanner.next();
        }
        Boolean lightsAvailable = lights.equalsIgnoreCase(TO_SKIP_FILTER) ? null : Boolean.parseBoolean(lights);

        System.out.println("Enter the color");
        String color = scanner.next();

        Integer price = intCheck("Enter the price");

        if (searchFlag) {
            if (color.equalsIgnoreCase(TO_SKIP_FILTER))
                color = null;
            if (brand.equalsIgnoreCase(TO_SKIP_FILTER))
                brand = null;
        }

        //special for electric or folding  bike features
        Bike bike;
        if (bikeType.isElectric()) {
            Integer maxSpeed = intCheck("Enter the maximum speed (in km/h):");
            Integer batteryCapacity = intCheck("Enter the battery capacity (in mAh):");
            bike = new ElectricBike(bikeType, brand, weight, lightsAvailable, color, price, maxSpeed, batteryCapacity);
        } else {
            Integer wheelSize = intCheck("Enter the size of the wheels (in inches):");
            Integer gears = intCheck("Enter the number of gears:");
            bike = new FoldingBike(brand, weight, lightsAvailable, color, price, wheelSize, gears);
        }
        return bike;
    }

    //type and valid value check
    private static Integer intCheck(String msg) {
        System.out.println(msg);
        int value = 0;
        while (value < 1) {
            try {
                value = scanner.nextInt();
                if (value < 1)
                    System.out.println("Value must be number greater then zero!");

            } catch (InputMismatchException e) {
                if (scanner.next().equalsIgnoreCase(TO_SKIP_FILTER) && searchFlag) {
                    return null;
                } else if (searchFlag) {
                    System.out.println("Type < " + TO_SKIP_FILTER + " > to leave empty filter or positive number!");
                } else {
                    System.out.println("Input must be a number!");
                    System.out.println(msg);
                    scanner.nextLine();
                }
            }
        }
        return value;
    }

    public static void searchBikeInCatalog() {
        System.out.println("\r\nPlease specify your search request:\r\n" +
                "1 - Find a " + BikeType.F_BIKE.getTypeName() + "\r\n" +
                "2 - Find a " + BikeType.S_ELEC.getTypeName() + "\r\n" +
                "3 - Find " + BikeType.E_BIKE.getTypeName() + "\r\n" +
                "4 - Find any bike by brand");

        Bike bike = null;
        boolean repeat = true;

        while (repeat) {
            repeat = false;
            String option = scanner.next();
            searchByBrandOnly = false;

            switch (option) {
                case "1":
                    bike = createBikeInstance(BikeType.F_BIKE);
                    break;
                case "2":
                    bike = createBikeInstance(BikeType.S_ELEC);
                    break;
                case "3":
                    bike = createBikeInstance(BikeType.E_BIKE);
                    break;
                case "4":
                    System.out.println("Enter the searching brand");
                    bike = new FoldingBike(scanner.next(), null, null, null, null, null, null);
                    searchByBrandOnly = true;
                    break;
                default:
                    System.out.println("Wrong input! Enter number from 1 to 4");
                    repeat = true;
                    break;
            }
        }
        searchFlag = false;
        System.out.println("\r\nSearching can take some time, the result will be shown when ready.");
        Bike filterBike = bike;
        CompletableFuture.runAsync(() -> search(filterBike));
    }

    private static void search(Bike filterBike) {
        Method[] methods = ArrayUtils.addAll(filterBike.getClass().getSuperclass().getDeclaredMethods(), filterBike.getClass().getDeclaredMethods());
        List<Function<Bike, Object>> comparingFields = new ArrayList<>();

        try {
            for (Method method : methods) {
                if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                    if (method.invoke(filterBike) != null)

                        comparingFields.add(b -> {
                            try {
                                return method.invoke(b);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        });
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (searchByBrandOnly) {
            Collections.sort(bikes);
            int i = Collections.binarySearch(bikes, filterBike);
            if (i > -1) {
                System.out.println("\r\nBike is found:\n" + bikes.get(i));
            } else {
                System.out.println("\r\nThere is no " + filterBike.brand() + " brand in catalog!");
            }
        } else {
            List<Bike> foundBikes = filter(filterBike, comparingFields);
            if (foundBikes.size() > 0) {
                System.out.println("\r\nSearching result:");
                foundBikes.forEach(System.out::println);
            } else {
                System.out.println("\r\nThere is no bikes with such parameters in catalog!");
            }
        }
    }

    private static List<Bike> filter(Bike filterBike,
                                     List<Function<Bike, Object>> comparingFields) {
        //returns list of filtered bikes
        return bikes.stream()
                .filter(bike -> filter(bike, filterBike, comparingFields))
                .collect(Collectors.toList());
    }

    private static boolean filter(Bike bike, Bike filterBike,
                                  List<Function<Bike, Object>> comparingFields) {
        //returns bike bike if all specified fields return true
        return comparingFields.stream()
                .allMatch(func -> func.apply(bike).equals(func.apply(filterBike)));
    }
}
