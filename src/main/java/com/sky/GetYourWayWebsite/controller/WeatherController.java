package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sky.GetYourWayWebsite.service.WeatherService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    private ResponseEntity<Object> getDataForEndpoint(String endpointName, String lat, String lon) {
        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            Object result;

            switch(endpointName) {
                case "currentWeather": result = weatherService.getCurrentWeather(latitude, longitude); break;
                case "hourlyWeather": result = weatherService.getHourlyWeather(latitude, longitude); break;
                case "dailyWeather": result = weatherService.getDailyWeather(latitude, longitude); break;
                default: result = new Object();
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NumberFormatException e) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode errorObject = mapper.createObjectNode();
            errorObject.put("error", "Longitude and latitude parameters must be valid numbers");
            return new ResponseEntity<>(errorObject, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/currentWeather")
    public ResponseEntity<Object> getCurrentWeather(@RequestParam String lat, @RequestParam String lon) {
        return getDataForEndpoint("currentWeather", lat, lon);
    }

    @GetMapping("/hourlyWeather")
    public Object getHourlyWeather(@RequestParam String lat, @RequestParam String lon) {
        return getDataForEndpoint("hourlyWeather", lat, lon);
    }

    @GetMapping("/dailyWeather")
    public Object getDailyWeather(@RequestParam String lat, @RequestParam String lon) {
        return getDataForEndpoint("dailyWeather", lat, lon);
    }

    @GetMapping("/getWeatherSymbolURL")
    public String getWeatherSymbolURL(@RequestParam String description) {
        return weatherService.getWeatherSymbolURL(description);
    }
}
