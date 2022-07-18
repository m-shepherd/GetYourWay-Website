package com.sky.GetYourWayWebsite.Flights;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class FlightLabs {
    private static final String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMGQ1M2VkODYyYzBmNzliYmEyZTFmMmI3ZTBmYTY2ZmEwZjMzNmYyODY4ZmUxYmFiMjJiZmFlNTAyM2Y2ZDdiYmVlNzhmOTIyZjc4MTZkMDQiLCJpYXQiOjE2NTgxNTU4OTYsIm5iZiI6MTY1ODE1NTg5NiwiZXhwIjoxNjg5NjkxODk2LCJzdWIiOiI4NzI0Iiwic2NvcGVzIjpbXX0.mmyCyRS-ia216FPFhkzWmKTtgA_ES2Ot5ZocmwWKWKNnS6KumPp6qKZwA6B0zbdlKoEUTBerinhpT08xNl9iqQ";

    public static void main(String[] args) {
        try {

            URL url = new URL("https://app.goflightlabs.com/flights?access_key=" + apiKey + "&dep_iata=LHR&arr_iata=FRA&flight_status=scheduled&arr_scheduled_time_arr=2022-07-13");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responseCode = conn.getResponseCode();
//                int responseCode = 200;
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder response = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();

                String inline = response.toString();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode flightJson = mapper.readTree(inline);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                List<Flight> flightList = new ArrayList<Flight>();
                ArrayNode arrayNode = (ArrayNode) flightJson;
                for(int i = 0; i < arrayNode.size(); i++) {
                    JsonNode arrayElement = arrayNode.get(i);
                    Date flightDate = sdf.parse(arrayElement.get("flight_date").toString().substring(1, arrayElement.get("flight_date").toString().length()-1));
                    String departureAirport = arrayElement.at("/departure/airport").toString();
                    LocalTime departureScheduled = LocalTime.parse(arrayElement.at("/departure/scheduled").toString().substring(12, 17), DateTimeFormatter.ofPattern("HH:mm"));
                    String arrivalAirport = arrayElement.at("/arrival/airport").toString();
                    LocalTime arrivalScheduled = LocalTime.parse(arrayElement.at("/arrival/scheduled").toString().substring(12, 17), DateTimeFormatter.ofPattern("HH:mm"));
                    String airlineName = arrayElement.at("/airline/name").toString();
                    String flightNumber = arrayElement.at("/flight/iata").toString();

                    flightList.add(new Flight(flightDate, departureAirport, departureScheduled, arrivalAirport, arrivalScheduled, airlineName, flightNumber));

                }

                for (Flight flight : flightList) {
                    System.out.println(flight);
                    System.out.println();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
