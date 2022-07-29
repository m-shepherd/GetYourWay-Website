package com.sky.GetYourWayWebsite;

import com.sky.GetYourWayWebsite.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class GetYourWayWebsiteApplication extends SpringBootServletInitializer {
	@Autowired
	UserDetailsServiceImpl userService;

	public static void main(String[] args) {
		SpringApplication.run(GetYourWayWebsiteApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GetYourWayWebsiteApplication.class);
	}
}
