package com.sky.GetYourWayWebsite.flights;

import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class Flight {
//    private final LocalDate date;
    private final List<String> airports;
    private final List<LocalTime> times;
    private final double price;
    private final List<String> durations;


    public Flight(List<String> airports, List<LocalTime> times, double price, List<String> durations) {
        this.airports = airports;
        this.times = times;
        this.price = price;
        this.durations = durations;
    }

}
