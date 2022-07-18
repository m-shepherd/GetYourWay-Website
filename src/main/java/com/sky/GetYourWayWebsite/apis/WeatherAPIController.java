package com.sky.GetYourWayWebsite.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherAPIController {
    private final String WEATHER_API_ADDRESS = "https://api.openweathermap.org/data/2.5/onecall?";
    private final String WEATHER_API_KEY = "b4057c94ecd30c1ccb944addfb79d2bc";

    @GetMapping("/currentWeather")
    public Object getCurrentWeather() {
        String uri = WEATHER_API_ADDRESS + "lat=51.5085&lon=-0.1257&exclude=minutely,hourly,daily,alerts"+
                "&units=metric&appid=" + WEATHER_API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        Object weatherObject = restTemplate.getForObject(uri, Object.class);
        return weatherObject;
    }
}
