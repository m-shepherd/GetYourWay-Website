package com.sky.GetYourWayWebsite.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("Weather Symbol URL Valid Description")
    public void getWeatherSymbolURLValidDescription() {
        String url = weatherService.getWeatherSymbolURL("clouds");
        Assertions.assertEquals("https://openweathermap.org/img/wn/02d@2x.png", url);
    }

    @Test
    @DisplayName("Weather Symbol URL Valid Description")
    public void getWeatherSymbolURLInvalidDescription() {
        String url = weatherService.getWeatherSymbolURL("invalid");
        Assertions.assertEquals("https://openweathermap.org/img/wn/01d@2x.png", url);
    }
}
