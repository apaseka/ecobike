package org.qualityunit;

import org.junit.Before;
import org.junit.Test;
import org.qualityunit.model.FoldingBike;
import org.qualityunit.services.FileService;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileServiceTest {

    private static final String FILE = "test.txt";

    @Before
    public void initFile() throws IOException {
        FileWriter fw = new FileWriter(FILE);
        fw.write("SPEEDELEC AirWheel; 40; 7700; true; 11600; orange; 1319\n" +
                "SPEEDELEC AirWheel; 35; 9800; false; 10900; rose; 155\n" +
                "FOLDING BIKE Author; 20; 9; 11900; true; brown; 209\n" +
                "FOLDING BIKE Author; 24; 27; 13200; false; flame; 325\n" +
                "FOLDING BIKE Author; 14; 9; 9100; false; lemon; 529\n" +
                "FOLDING BIKE Author; 20; 8; 9300; true; emerald; 1355\n" +
                "FOLDING BIKE Author; 20; 21; 14000; false; grenadine; 1075\n" +
                "FOLDING BIKE Author; 24; 7; 11900; true; red; 685\n" +
                "FOLDING BIKE Author; 26; 24; 10200; true; black; 1599\n" +
                "FOLDING BIKE Author; 18; 24; 13900; false; brown; 1229\n" +
                "FOLDING BIKE Author; 18; 9; 11600; false; coral; 1605");
        fw.close();
    }

    @Test
    public void readFileSuccess() {
        FileService.readFile(FILE);
        assertEquals(11, FileService.INITIAL_CATALOG_SIZE);
    }

    @Test
    public void objectsCreatedCorrectly() {
        FileService.readFile(FILE);
        assertTrue(FileService.bikes.contains(new FoldingBike("Author", 11900, true, "brown", 209, 20, 9)));
    }

    @Test
    public void bikeAddedAndFileSavedCorrectly() {
        FileService.readFile(FILE);
        FileService.bikes.add(new FoldingBike("Author2", 13900, true, "brown", 209, 20, 9));
        FileService.writeToFile(FileService.bikes, FILE);
        FileService.bikes.clear();

        FileService.readFile(FILE);
        assertEquals(12, FileService.INITIAL_CATALOG_SIZE);
        assertTrue(FileService.bikes.contains(new FoldingBike("Author2", 13900, true, "brown", 209, 20, 9)));
    }
}
