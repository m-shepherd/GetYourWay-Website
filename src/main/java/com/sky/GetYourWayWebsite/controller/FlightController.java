package com.sky.GetYourWayWebsite.controller;

import com.sky.GetYourWayWebsite.flights.Flight;
import com.sky.GetYourWayWebsite.flights.FlightQuery;
import com.sky.GetYourWayWebsite.flights.FlightUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://3.10.61.220:3000"})
public class FlightController {
    private final String API_ADDRESS = "https://app.goflightlabs.com/flights";
    private final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMTA0NTdmZDU1YTM4NjA3OWE0YjA3ZjIzMjVhZTJiMjRlYzA1M2MzMGE3ODJkZTFjMjY3ZmEwZmExMzBjYjBkMzQ1MTViMjVhMDIzMmY0NDAiLCJpYXQiOjE2NTg0MTI0NTksIm5iZiI6MTY1ODQxMjQ1OSwiZXhwIjoxNjg5OTQ4NDU5LCJzdWIiOiI5MDA0Iiwic2NvcGVzIjpbXX0.efxsoJCpmQQDhxvdAj2Kc4LnZ1paHKoji4jm9x6ikMr2Ile1lsBkMjvj0XuMxMSqNh8GSQdOUwHIKG-X0Q5JJQ";
    @GetMapping("/flights")
    public List<Flight> getFlights(@RequestParam String date, @RequestParam String dep, @RequestParam String arr) {
        try {
            FlightQuery query = new FlightQuery(date, dep, arr);
            String uri = API_ADDRESS + "?access_key=" + API_KEY + query.getSearchParameters();

            RestTemplate restTemplate = new RestTemplate();

//            return readFlightData(restTemplate.getForObject(uri, Object.class));

            return readFlightData(FlightUtils.read("src/main/resources/example-flight-data.txt"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private List<Flight> readFlightData(Object allFlights) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode flightJson = mapper.valueToTree(allFlights);

            return getFlights((ArrayNode) flightJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Flight> readFlightData(String allFlights) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode flightJson = mapper.readTree(allFlights);

            return getFlights((ArrayNode) flightJson);

        } catch (Exception e) {
            e.printStackTrace();
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
        LocalDate flightDate = LocalDate.parse(arrayElement.get("flight_date").toString().substring(1, arrayElement.get("flight_date").toString().length() - 1));
        String departureAirport = arrayElement.at("/departure/airport").toString().substring(1, arrayElement.at("/departure/airport").toString().length() - 1);
        LocalTime departureScheduled = LocalTime.parse(arrayElement.at("/departure/scheduled").toString().substring(12, 17), DateTimeFormatter.ofPattern("HH:mm"));
        String arrivalAirport = arrayElement.at("/arrival/airport").toString().substring(1, arrayElement.at("/arrival/airport").toString().length() - 1);
        LocalTime arrivalScheduled = LocalTime.parse(arrayElement.at("/arrival/scheduled").toString().substring(12, 17), DateTimeFormatter.ofPattern("HH:mm"));
        String airlineName = arrayElement.at("/airline/name").toString().substring(1, arrayElement.at("/airline/name").toString().length() - 1);
        String flightNumber = arrayElement.at("/flight/iata").toString().substring(1, arrayElement.at("/flight/iata").toString().length() - 1);

        return new Flight(flightDate, departureAirport, departureScheduled, arrivalAirport, arrivalScheduled, airlineName, flightNumber);

    }

}