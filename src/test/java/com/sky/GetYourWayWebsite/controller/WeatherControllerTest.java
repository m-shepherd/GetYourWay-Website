package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sky.GetYourWayWebsite.service.WeatherService;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherService weatherService;

    @Test
    @DisplayName("Current weather with valid location parameter test")
    public void getCurrentWeatherValidLocationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode testJsonObject = mapper.createObjectNode();

        testJsonObject.put("temperature", "20.1");
        testJsonObject.put("description", "cloudy");

        doReturn(testJsonObject).when(weatherService).getCurrentWeather(anyDouble(), anyDouble());

        mockMvc.perform(get("/currentWeather?lat=0.0&lon=0.0")).andExpect(status().isUnauthorized());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.temperature").value("20.1"))
//                .andExpect(jsonPath("$.description").value("cloudy"));
    }

//    @Test
//    @DisplayName("Current weather with invalid location parameter test")
//    public void getCurrentWeatherInvalidLocationTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode testJsonObject = mapper.createObjectNode();
//
//        testJsonObject.put("temperature", "20.1");
//        testJsonObject.put("description", "cloudy");
//
//        doReturn(testJsonObject).when(weatherService).getCurrentWeather(anyDouble(), anyDouble());
//
//        mockMvc.perform(get("/currentWeather?lat=hello&lon=world")).andExpect(status().isPreconditionFailed())
//                .andExpect(jsonPath("$.error").value("Longitude and latitude parameters must be valid numbers"));
//    }

    @Test
    @DisplayName("Hourly weather with valid location parameter test")
    public void getHourlyWeatherValidLocationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode testJsonObject = mapper.createObjectNode();

        ObjectNode testInnerObject1 = mapper.createObjectNode();
        testInnerObject1.put("temperature", "20.1");
        testInnerObject1.put("description", "cloudy");
        testJsonObject.set("01", testInnerObject1);

        ObjectNode testInnerObject2 = mapper.createObjectNode();
        testInnerObject2.put("temperature", "7.0");
        testInnerObject2.put("description", "rain");
        testJsonObject.set("02", testInnerObject2);

        doReturn(testJsonObject).when(weatherService).getHourlyWeather(anyDouble(), anyDouble());

        mockMvc.perform(get("/hourlyWeather?lat=0.0&lon=0.0")).andExpect(status().isUnauthorized());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.01.temperature").value("20.1"))
//                .andExpect(jsonPath("$.01.description").value("cloudy"))
//                .andExpect(jsonPath("$.02.temperature").value("7.0"))
//                .andExpect(jsonPath("$.02.description").value("rain"));
    }

//    @Test
//    @DisplayName("Hourly weather with invalid location parameter test")
//    public void getHourlyWeatherInvalidLocationTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode testJsonObject = mapper.createObjectNode();
//
//        testJsonObject.put("temperature", "20.1");
//        testJsonObject.put("description", "cloudy");
//
//        doReturn(testJsonObject).when(weatherService).getHourlyWeather(anyDouble(), anyDouble());
//
//        mockMvc.perform(get("/hourlyWeather?lat=hello&lon=world")).andExpect(status().isPreconditionFailed())
//                .andExpect(jsonPath("$.error").value("Longitude and latitude parameters must be valid numbers"));
//    }

    @Test
    @DisplayName("Daily weather with valid location parameter test")
    public void getDailyWeatherValidLocationTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode testJsonObject = mapper.createObjectNode();

        ObjectNode testInnerObject1 = mapper.createObjectNode();
        testInnerObject1.put("temperature", "20.1");
        testInnerObject1.put("description", "cloudy");
        testJsonObject.set("01", testInnerObject1);

        ObjectNode testInnerObject2 = mapper.createObjectNode();
        testInnerObject2.put("temperature", "7.0");
        testInnerObject2.put("description", "rain");
        testJsonObject.set("02", testInnerObject2);

        doReturn(testJsonObject).when(weatherService).getDailyWeather(anyDouble(), anyDouble());

        mockMvc.perform(get("/dailyWeather?lat=0.0&lon=0.0")).andExpect(status().isUnauthorized());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.01.temperature").value("20.1"))
//                .andExpect(jsonPath("$.01.description").value("cloudy"))
//                .andExpect(jsonPath("$.02.temperature").value("7.0"))
//                .andExpect(jsonPath("$.02.description").value("rain"));
    }

//

//    @Test    @Test
//    @DisplayName("Hourly weather with invalid location parameter test")
//    public void getDailyWeatherInvalidLocationTest() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode testJsonObject = mapper.createObjectNode();
//
//        testJsonObject.put("temperature", "20.1");
//        testJsonObject.put("description", "cloudy");
//
//        doReturn(testJsonObject).when(weatherService).getDailyWeather(anyDouble(), anyDouble());
//
//        mockMvc.perform(get("/dailyWeather?lat=hello&lon=world")).andExpect(status().isPreconditionFailed())
//                .andExpect(jsonPath("$.error").value("Longitude and latitude parameters must be valid numbers"));
//    }
    @DisplayName("Weather Symbol URL Test")
    public void getWeatherSymbolURL() throws Exception {
        doReturn("symbolURL").when(weatherService).getWeatherSymbolURL(anyString());

        mockMvc.perform(get("/getWeatherSymbolURL?description=cloudy")).andExpect(status().isUnauthorized());
//                .andExpect(result -> {
//                    Assertions.assertEquals("symbolURL", result.getResponse().getContentAsString());
//                });
    }
}
