package com.sky.GetYourWayWebsite;

import com.sky.GetYourWayWebsite.domain.dao.UserRepository;
import com.sky.GetYourWayWebsite.domain.dto.User;
import com.sky.GetYourWayWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class GetYourWayWebsiteApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(GetYourWayWebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<User> users = userService.getAllUsers();

		for (User u : users) {
			System.out.println(u.getFirstName() + " " + u.getLastName());
		}
	}
}
