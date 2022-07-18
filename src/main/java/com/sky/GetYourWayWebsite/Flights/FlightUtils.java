package com.sky.GetYourWayWebsite.Flights;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FlightUtils {

    private static void write(String filename, String flightData) {
        try(PrintWriter writer = new PrintWriter(filename)) {
            writer.print(flightData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String read(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
