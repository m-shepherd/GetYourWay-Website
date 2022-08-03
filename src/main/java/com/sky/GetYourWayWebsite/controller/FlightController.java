package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sky.GetYourWayWebsite.flights.Flight;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://3.10.61.220:3000"})
public class FlightController {
    private final String API_ADDRESS = "https://test.api.amadeus.com";
    private final String CLIENT_ID = "EtXMCRf8Z4CeoOTAV2IQZE6sxxasLHex";
    private final String CLIENT_SECRET = "mcLogSpqvZosETpS";


    @GetMapping("/flights/nearest")
    public JsonNode getNearestAirports(@RequestParam String latitude, @RequestParam String longitude) {
        String authorisationToken = getAuthorisationToken();
        if (authorisationToken == null) {
            return null;
        } else {
            try {
                URL url = new URL(API_ADDRESS +"/v1/reference-data/locations/airports?" +
                        "latitude=" + latitude +
                        "&longitude=" + longitude +
                        "&radius=250&page%5Blimit%5D=5&page%5Boffset%5D=0&sort=relevance");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setRequestProperty("Authorization", authorisationToken);

                return readResponse(http);

            } catch (Exception e) {
                return null;
            }
        }
    }

    @GetMapping("/flights")
    public List<Flight> getFlights(@RequestParam String date, @RequestParam String dep, @RequestParam String arr, @RequestParam String direct) {
        String authorisationToken = getAuthorisationToken();
        if (authorisationToken == null) {
            return null;
        } else {
            try {
                URL url = new URL(API_ADDRESS +"/v2/shopping/flight-offers?" +
                        "originLocationCode=" + dep +
                        "&destinationLocationCode=" + arr +
                        "&departureDate=" + date +
                        "&adults=1&" +
                        "nonStop=" + direct +
                        "&max=10");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setRequestProperty("Authorization", authorisationToken);

                JsonNode flightData = readResponse(http);

                if (flightData.get("data").size() > 0) {
                    return getFlights((ArrayNode) flightData.get("data"));
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    private List<Flight> getFlights(ArrayNode flightJson) {
        List<Flight> flightList = new ArrayList<>();
        for (int i = 0; i < flightJson.size(); i++) {
            JsonNode arrayElement = flightJson.get(i);
            Flight flight = parseFlight(arrayElement);

            flightList.add(flight);
        }

        return flightList;
    }

    private Flight parseFlight(JsonNode arrayElement) {
        JsonNode flightItinerary = arrayElement.get("itineraries");
        List<String> flightsForTrip = getFlightsForTrip((ArrayNode) flightItinerary);
        List<LocalTime> timesForTrip = getTimesForTrip((ArrayNode) flightItinerary);
        double price = Double.parseDouble(arrayElement.at("/price/grandTotal").toString().substring(1, arrayElement.at("/price/grandTotal").toString().length() - 1));
        List<String> durations = getDurationsForTrip((ArrayNode) flightItinerary);

        return new Flight(flightsForTrip, timesForTrip, price, durations);
    }

    private List<String> getFlightsForTrip(ArrayNode flightItinerary) {
        List<String> flightsForTrip = new ArrayList<>();
        for (int i = 0; i < flightItinerary.size(); i++) {
            JsonNode arrayElement = flightItinerary.get(i);
            JsonNode segments = arrayElement.get("segments");
            for (int j = 0; j < segments.size(); j++) {
                JsonNode segment = segments.get(j);
                String departureAirport = segment.at("/departure/iataCode").toString().substring(1, segment.at("/departure/iataCode").toString().length() - 1);
                String arrivalAirport = segment.at("/arrival/iataCode").toString().substring(1, segment.at("/arrival/iataCode").toString().length() - 1);
                if (j > 0) {
                    flightsForTrip.add(arrivalAirport);
                } else {
                    flightsForTrip.add(departureAirport);
                    flightsForTrip.add(arrivalAirport);
                }
            }
        }
        return flightsForTrip;
    }

    private List<String> getDurationsForTrip(ArrayNode flightItinerary) {
        List<String> durationsForTrip = new ArrayList<>();
        System.out.println(flightItinerary.get(0).get("duration"));
        String totalDuration = flightItinerary.get(0).get("duration").toString().substring(1, flightItinerary.get(0).get("duration").toString().length() - 1);
        durationsForTrip.add(totalDuration);
        for (int i = 0; i < flightItinerary.size(); i++) {
            JsonNode arrayElement = flightItinerary.get(i);
            JsonNode segments = arrayElement.get("segments");
            for (int j = 0; j < segments.size(); j++) {
                JsonNode segment = segments.get(j);
                String duration = segment.at("/duration").toString().substring(1, segment.at("/duration").toString().length() - 1);
                durationsForTrip.add(duration);
            }
        }
        return durationsForTrip;
    }

    private List<LocalTime> getTimesForTrip(ArrayNode flightItinerary) {
        List<LocalTime> TimesForTrip = new ArrayList<>();
        for (int i = 0; i < flightItinerary.size(); i++) {
            JsonNode arrayElement = flightItinerary.get(i);
            JsonNode segments = arrayElement.get("segments");
            for (int j = 0; j < segments.size(); j++) {
                JsonNode segment = segments.get(j);
                LocalTime departureTime = LocalTime.parse(segment.at("/departure/at").toString().substring(12, segment.at("/departure/at").toString().length() - 4), DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime arrivalTime = LocalTime.parse(segment.at("/arrival/at").toString().substring(12, segment.at("/arrival/at").toString().length() - 4), DateTimeFormatter.ofPattern("HH:mm"));
                TimesForTrip.add(departureTime);
                TimesForTrip.add(arrivalTime);
            }
        }
        return TimesForTrip;
    }

    private JsonNode readResponse(HttpURLConnection http) throws IOException {
        BufferedReader br = null;
        if (100 <= http.getResponseCode() && http.getResponseCode() <= 399) {
            br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s=br.readLine())!=null)
        {
            stringBuilder.append(s);
        }
        http.disconnect();

        String responseJsonString = stringBuilder.toString().replaceAll("\\s+","");
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(responseJsonString);
    }

    private String getAuthorisationToken() {
        try {
            URL url = new URL(API_ADDRESS +"/v1/security/oauth2/token");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String data = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;

            byte[] out = data.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = http.getOutputStream();
            stream.write(out);

            JsonNode jsonNode = readResponse(http);

            return jsonNode.get("token_type").toString().substring(1, jsonNode.get("token_type").toString().length() - 1)
                    + " " + jsonNode.get("access_token").toString().substring(1, jsonNode.get("access_token").toString().length() - 1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}