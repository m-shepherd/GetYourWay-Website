package com.sky.GetYourWayWebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.sky.GetYourWayWebsite.domain.dto.Users;
import com.sky.GetYourWayWebsite.service.UserDetailsServiceImpl;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import java.util.*;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @Value("${secret.key}")
    private String secretKey;

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
    public HttpStatus login(@RequestBody String token) {
        token = token.substring(1, token.length() - 1);
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String login = new String(decoder.decode(chunks[1]));

        SignatureAlgorithm signatureAlgorithm = HS256;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), signatureAlgorithm.getJcaName());

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(signatureAlgorithm, secretKeySpec);

        if (!validator.isValid(tokenWithoutSignature, signature)) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return verifyLogin(login);
        }

    }

    private HttpStatus verifyLogin(String login) {
        ObjectNode json;
        try {
            json = parseLogin(login);
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }

        String username;
        String password;
        try {
            username = json.get("username").toString().substring(1, json.get("username").toString().length() - 1);
            password = json.get("password").toString().substring(1, json.get("password").toString().length() - 1);
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

    private ObjectNode parseLogin(String login) {
        login = login.replaceAll("\\\\", "");
        login = login.replaceAll("\\{", "");
        login = login.replaceAll("}", "");
        login = login.substring(1, login.length() - 1);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

        String[] fields = login.split(",");
        for (String field : fields) {
            String[] jsonEntry = field.split(":");
            String key = jsonEntry[0].substring(1, jsonEntry[0].length() - 1);
            String value = jsonEntry[1].substring(1, jsonEntry[1].length() - 1);
            json.put(key, value);
        }

        return json;
    }

}