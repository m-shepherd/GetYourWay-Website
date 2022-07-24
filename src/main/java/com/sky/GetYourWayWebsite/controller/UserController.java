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
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    private boolean isUserPresent(String username) {
        Optional<User> possibleUser = userService.findByUsername(username);
        return possibleUser.isPresent();
    }

    @PostMapping("/users/getUserByEmail")
    public HttpStatus checkUserPresentByEmail(@RequestBody User user){
        String email = user.getEmail();
        if (userService.findByEmail(email).isPresent()){
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
    
    @PostMapping("/users")
    public HttpStatus createUser(@RequestBody User newUser) {
        if (!isUserPresent(newUser.getUsername())) {
            return editUserDetails(newUser);
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping("/users")
    public HttpStatus updateUser(@RequestBody User newUser) {
        if (isUserPresent(newUser.getUsername())) {
            return editUserDetails(newUser);
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private HttpStatus editUserDetails(@RequestBody User newUser) {
        User result;
        try {
            result = userService.addUser(newUser);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User Information Provided");
        }
        if (result == null) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.CREATED;
        }
    }

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
