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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users")
    public HttpStatus createUser(@RequestBody User user) {
        User result = userService.addUser(user);
        if (result == null) {
            return HttpStatus.NOT_ACCEPTABLE;
        } else {
            return HttpStatus.CREATED;
        }
    }


}
