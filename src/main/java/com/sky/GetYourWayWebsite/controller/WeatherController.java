package com.sky.GetYourWayWebsite.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {
    private final String WEATHER_API_ADDRESS = "https://api.openweathermap.org/data/2.5/onecall?";
    private final String WEATHER_API_KEY = "b4057c94ecd30c1ccb944addfb79d2bc";

    @GetMapping("/currentWeather")
    public Object getCurrentWeather(@RequestParam String lat, @RequestParam String lon) {
        System.out.println("currentWeather");
        String uri = WEATHER_API_ADDRESS + "lat=" + lat + "&lon=" + lon + "&exclude=minutely,hourly,daily,alerts" +
                "&units=metric&appid=" + WEATHER_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    @GetMapping("/hourlyWeather")
    public Object getHourlyWeather(@RequestParam String lat, @RequestParam String lon) {
        String uri = WEATHER_API_ADDRESS + "lat=" + lat + "&lon=" + lon + "&exclude=minutely,current,daily,alerts" +
                "&units=metric&appid=" + WEATHER_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    @GetMapping("/dailyWeather")
    public Object getDailyWeather(@RequestParam String lat, @RequestParam String lon) {
        String uri = WEATHER_API_ADDRESS + "lat=" + lat + "&lon=" + lon + "&exclude=minutely,current,hourly,alerts" +
                "&units=metric&appid=" + WEATHER_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    @GetMapping("/weatherAlerts")
    public Object getWeatherAlerts(@RequestParam String lat, @RequestParam String lon) {
        String uri = WEATHER_API_ADDRESS + "lat=" + lat + "&lon=" + lon + "&exclude=minutely,current,hourly,daily" +
                "&units=metric&appid=" + WEATHER_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    @GetMapping("/getWeatherSymbolURL")
    public String getWeatherSymbolURL(@RequestParam String description) {
        String fileName = "";

        if (description.contains("clear")) {
            fileName = "01d";
        } else if (description.contains("clouds")) {
            fileName = "02d";
        } else if (description.contains("rain")) {
            fileName = "09d";
        } else if (description.contains("thunderstorm")) {
            fileName = "11d";
        } else if (description.contains("snow")) {
            fileName = "13d";
        } else if (description.contains("mist")) {
            fileName = "50d";
        } else {
            fileName = "01d";
        }

        return  "https://openweathermap.org/img/wn/" + fileName + "@2x.png";
    }
}
