package com.sky.GetYourWayWebsite.Flights;


public class FlightQuery {
    private final String date;
    private final String departureAirport;
    private final String arrivalAirport;

    public FlightQuery(String date, String departureAirport, String arrivalAirport) {
        this.date = date;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    public String getSearchParameters() {
        return "&dep_iata=" + departureAirport +"&arr_iata=" + arrivalAirport +
                "&flight_status=scheduled&arr_scheduled_time_arr=" + date;
    }

}
