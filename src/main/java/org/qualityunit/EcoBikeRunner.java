package org.qualityunit;

import org.qualityunit.services.FileService;
import org.qualityunit.services.UserService;

import java.util.Scanner;

/**
 * EcoBike application
 */
public class EcoBikeRunner {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String filePath = FileService.checkFilePath(args);
        FileService.readFile(filePath);
        UserService.showMenu();
    }
}
