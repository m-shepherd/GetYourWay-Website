package com.sky.GetYourWayWebsite.flights;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RestController
public class FlightLabsController {
    private final String API_ADDRESS = "https://app.goflightlabs.com/flights";
    private final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMGQ1M2VkODYyYzBmNzliYmEyZTFmMmI3ZTBmYTY2ZmEwZjMzNmYyODY4ZmUxYmFiMjJiZmFlNTAyM2Y2ZDdiYmVlNzhmOTIyZjc4MTZkMDQiLCJpYXQiOjE2NTgxNTU4OTYsIm5iZiI6MTY1ODE1NTg5NiwiZXhwIjoxNjg5NjkxODk2LCJzdWIiOiI4NzI0Iiwic2NvcGVzIjpbXX0.mmyCyRS-ia216FPFhkzWmKTtgA_ES2Ot5ZocmwWKWKNnS6KumPp6qKZwA6B0zbdlKoEUTBerinhpT08xNl9iqQ";

    @GetMapping("/currentFlights")
    public List<Flight> getFlights(String date, String departureAirport, String arrivalAirport) {
        FlightQuery query = new FlightQuery(date, departureAirport, arrivalAirport);
        String uri = API_ADDRESS + "?access_key=" + API_KEY + query.getSearchParameters();

        RestTemplate restTemplate = new RestTemplate();

        return readFlightData(restTemplate.getForObject(uri, Object.class));

    }

    private List<Flight> readFlightData(Object allFlights) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode flightJson = mapper.valueToTree(allFlights);

            List<Flight> flightList = new ArrayList<>();
            ArrayNode arrayNode = (ArrayNode) flightJson;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                Flight flight = parseFlight(arrayElement);

                flightList.add(flight);

            }

            return flightList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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