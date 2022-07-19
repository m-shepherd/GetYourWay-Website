package com.sky.GetYourWayWebsite.Flights;

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
public class FlightLabs {
    private final String API_ADDRESS = "https://app.goflightlabs.com/flights?";
    private final String API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMGQ1M2VkODYyYzBmNzliYmEyZTFmMmI3ZTBmYTY2ZmEwZjMzNmYyODY4ZmUxYmFiMjJiZmFlNTAyM2Y2ZDdiYmVlNzhmOTIyZjc4MTZkMDQiLCJpYXQiOjE2NTgxNTU4OTYsIm5iZiI6MTY1ODE1NTg5NiwiZXhwIjoxNjg5NjkxODk2LCJzdWIiOiI4NzI0Iiwic2NvcGVzIjpbXX0.mmyCyRS-ia216FPFhkzWmKTtgA_ES2Ot5ZocmwWKWKNnS6KumPp6qKZwA6B0zbdlKoEUTBerinhpT08xNl9iqQ";

    @GetMapping("/currentFlights")
    public List<Flight> getFlights() {
        String searchParameters = "&dep_iata=LHR&arr_iata=FRA&flight_status=scheduled&arr_scheduled_time_arr=2022-07-13";
        String uri = API_ADDRESS + "access_key=" + API_KEY + searchParameters;

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
        String departureAirport = arrayElement.at("/departure/airport").toString();
        LocalTime departureScheduled = LocalTime.parse(arrayElement.at("/departure/scheduled").toString().substring(12, 17), DateTimeFormatter.ofPattern("HH:mm"));
        String arrivalAirport = arrayElement.at("/arrival/airport").toString();
        LocalTime arrivalScheduled = LocalTime.parse(arrayElement.at("/arrival/scheduled").toString().substring(12, 17), DateTimeFormatter.ofPattern("HH:mm"));
        String airlineName = arrayElement.at("/airline/name").toString();
        String flightNumber = arrayElement.at("/flight/iata").toString();

        return new Flight(flightDate, departureAirport, departureScheduled, arrivalAirport, arrivalScheduled, airlineName, flightNumber);

    }

}