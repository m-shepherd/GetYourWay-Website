package com.sky.GetYourWayWebsite.Flights;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Flight {
    private final LocalDate date;
    private final String departureAirport;
    private final LocalTime departureTime;
    private final String arrivalAirport;
    private final LocalTime arrivalTime;
    private final String airlineName;
    private final String flightNumber;

    public Flight(LocalDate date, String departureAirport, LocalTime departureTime, String arrivalAirport, LocalTime arrivalTime,
                  String airlineName, String airlineNumber) {
        this.date = date;
        this.departureAirport = departureAirport;
        this.departureTime = departureTime;
        this.arrivalAirport = arrivalAirport;
        this.arrivalTime = arrivalTime;
        this.airlineName = airlineName;
        this.flightNumber = airlineNumber;
    }

    @Override
    public String toString() {
        return "Flight Details:\n" +
                "Date -> " + date.toString() + "\n" +
                "Departure Airport -> " + departureAirport + "\n" +
                "Departure Time -> " + departureTime.toString() + "\n" +
                "Arrival Airport -> " + arrivalAirport + "\n" +
                "Arrival Time -> " + arrivalTime.toString() + "\n" +
                "Airline Name -> " + airlineName+ "\n" +
                "Flight Number -> " + flightNumber;

    }

    public LocalDate getDate() {
        return date;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
