package com.sky.GetYourWayWebsite.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final String WEATHER_API_ADDRESS = "https://api.openweathermap.org/data/2.5/onecall?";
    private final String WEATHER_API_KEY = "af7405f35b82dd110671a7224307530d";

    private String getAPIEndpoint(double latitude, double longitude, String exclude) {
        return WEATHER_API_ADDRESS + "lat=" + latitude + "&lon=" + longitude + "&exclude=" + exclude +
                "&units=metric&appid=" + WEATHER_API_KEY;
    }

    public Object getCurrentWeather(double latitude, double longitude) {
        String uri = getAPIEndpoint(latitude, longitude, "minutely,hourly,daily,alerts");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    public Object getHourlyWeather(double latitude, double longitude) {
        String uri = getAPIEndpoint(latitude, longitude, "minutely,current,daily,alerts");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    public Object getDailyWeather(double latitude, double longitude) {
        String uri = getAPIEndpoint(latitude, longitude, "minutely,current,hourly,alerts");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Object.class);
    }

    public String getWeatherSymbolURL(String description) {
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
