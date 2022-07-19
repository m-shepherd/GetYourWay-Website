package com.sky.GetYourWayWebsite;

import com.sky.GetYourWayWebsite.Flights.Flight;
import com.sky.GetYourWayWebsite.Flights.FlightLabs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class GetYourWayWebsiteApplication implements CommandLineRunner {
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(GetYourWayWebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		FlightLabs flightDetails = new FlightLabs();

		List<Flight> availableFlights = flightDetails.getFlights("2022-07-13", "LHR",
				"FRA");

		for (Flight flight : availableFlights) {
			System.out.println(flight);
			System.out.println();
		}

	}
}
