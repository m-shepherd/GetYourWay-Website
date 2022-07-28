package com.sky.GetYourWayWebsite.controller;

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

    @PutMapping("/users/updatePassword")
    public HttpStatus changePassword(@RequestParam String email, @RequestParam String password) {
        Optional<Users> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            user.setPassword(password);
            return editUserDetails(user);
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
    public HttpStatus login(@RequestBody Users login) {
        Optional<Users> possibleUser = userService.findByUsername(login.getUsername());
        Users user = null;
        if (possibleUser.isPresent()) {
            user = possibleUser.get();
        }
        if (user != null) {
            if (user.getPassword().equals(login.getPassword())) {
                return HttpStatus.OK;
            } else {
                return HttpStatus.UNAUTHORIZED;
            }
        } else {
            return HttpStatus.UNAUTHORIZED;
        }

    }

}