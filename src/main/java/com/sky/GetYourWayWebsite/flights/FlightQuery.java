package com.sky.GetYourWayWebsite.flights;


public class FlightQuery {
//    private final String date;
    private final String departureAirport;
    private final String arrivalAirport;

    public FlightQuery(String departureAirport, String arrivalAirport) {
//        this.date = date;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    public String getSearchParameters() {
//        return "&arr_scheduled_time_arr=" + date + "&dep_iata=" + departureAirport +"&arr_iata=" + arrivalAirport +
//                "&flight_status=scheduled";
        return "&dep_iata=" + departureAirport +"&arr_iata=" + arrivalAirport;
    }

}
