package com.sky.GetYourWayWebsite.controller;

import com.sky.GetYourWayWebsite.domain.dto.User;
import com.sky.GetYourWayWebsite.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable int id) {
        Optional<User> possibleUser = userService.getUserById(id);
        if (possibleUser.isPresent()) {
            return possibleUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request Type");
        }
    }

    @CrossOrigin
    @PostMapping("/users")
    public HttpStatus createUser(@RequestBody User user) {
        User result = null;
        try {
            result = userService.addUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User Information Provided");
        }
        if (result == null) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.CREATED;
        }
    }

    @CrossOrigin
    @PostMapping("/users/{username}/{password}")
    public HttpStatus login(@PathVariable String username, @PathVariable String password) {
        Optional<User> possibleUser = userService.findByUsername(username);
        User user = null;
        if (possibleUser.isPresent()) {
            user = possibleUser.get();
        }
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.UNAUTHORIZED;
            }
        } else {
            return HttpStatus.UNAUTHORIZED;
        }

    }


}
