package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sky.GetYourWayWebsite.domain.dto.Users;

import com.sky.GetYourWayWebsite.service.UserDetailsServiceImpl;
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
    UserDetailsServiceImpl userService;

    @GetMapping("/users")
    public List<Users> getUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/users/{username}")
    public Users getOneUser(@PathVariable String username){
        Optional<Users> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Could not find owner"
            );
        }
    }

    private boolean isUserPresent(String username) {
        Optional<Users> possibleUser = userService.findByUsername(username);
        return possibleUser.isPresent();
    }

    @GetMapping("/email/getUserByEmail")
    public HttpStatus checkUserPresentByEmail(@RequestParam String email){
        if (userService.findByEmail(email).isPresent()){
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PutMapping("/users")
    public HttpStatus updateUser(@RequestBody Users newUser) {
        if (isUserPresent(newUser.getUsername())) {
            return editUserDetails(newUser);
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/signUp")
    public HttpStatus createUser(@RequestBody Users newUser) {
        if (!isUserPresent(newUser.getUsername())) {
            return editUserDetails(newUser);
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private HttpStatus editUserDetails(@RequestBody Users newUser) {
        Users result;
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

    @PostMapping("/login")
    public HttpStatus login(@RequestBody JsonNode login) {
        String username;
        String password;
        try {
            username = login.get("username").toString().substring(1, login.get("username").toString().length() - 1);
            password = login.get("password").toString().substring(1, login.get("password").toString().length() - 1);
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
        Optional<Users> possibleUser = userService.findByUsername(username);
        Users user = null;
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