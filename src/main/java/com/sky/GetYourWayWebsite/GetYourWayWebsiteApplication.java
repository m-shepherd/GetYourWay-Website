package com.sky.GetYourWayWebsite;

import com.sky.GetYourWayWebsite.service.ShowServiceImpl;
import com.sky.GetYourWayWebsite.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GetYourWayWebsiteApplication {
	@Autowired
	UserDetailsServiceImpl userService;

	public static void main(String[] args) {
		SpringApplication.run(GetYourWayWebsiteApplication.class, args);
	}

}
